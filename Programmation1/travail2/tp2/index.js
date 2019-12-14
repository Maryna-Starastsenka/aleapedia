'use strict';

var http = require("http");
var fs = require('fs');
var urlParse = require('url').parse;
var pathParse = require('path').parse;
var querystring = require('querystring');
var crypto = require('crypto');
var request = require('sync-request');
var Entities = require('html-entities').AllHtmlEntities;
var entities = new Entities();

// Votre librairie est incluse ici
var markov = require('./markov.js');

// Fonctions exportées
var creerModele = markov.creerModele;
var genererParagraphes = markov.genererParagraphes;

// Liste de premières phrases possibles pour les articles
// Ajoutez-en si vous avez des idées!
var premieresPhrases = [
    "<strong>{{titre}}</strong> est un animal aquatique nocturne.",
    "<strong>{{titre}}</strong> (du grec ancien <em>\"{{titre-1}}\"</em> et <em>\"{{titre-2}}\"</em>), est le nom donné par Aristote à la vertu politique.",
    "<strong>{{titre}}</strong>, né le 30 août 1987 à Portland (Oregon), est un scénariste américain.",
    "<strong>{{titre}}</strong>, née le 30 septembre 1982 à Québec, est une femme politique québécoise.",
    "<strong>{{titre}}</strong> est défini comme « l'ensemble des règles imposées aux membres d'une société pour que leurs rapports sociaux échappent à l'arbitraire et à la violence des individus et soient conformes à l'éthique dominante ».",
    "<strong>{{titre}}</strong>, néologisme du XXe siècle, attesté en 1960, composite du grec ancien <em>{{titre-1}}</em> et du latin <em>{{titre-2}}</em>, est le principe déclencheur d'événements non liés à une cause connue.",
    "<strong>{{titre}}</strong> est une espèce fossile d'euryptérides ressemblant aux arachnides, appartenant à la famille des <em>{{titre-1}}</em>.",
    "<strong>{{titre}}</strong>, né le 25 juin 1805 à Lyon et mort le 12 février 1870 à Versailles, est un peintre animalier français.",
    "<strong>{{titre}}</strong> est le titre d'un épisode de la série télévisée d'animation Les Simpson. Il s'agit du quatre-vingt-dix-neuvième épisode de la soixante-huitième saison et du 8 615e épisode de la série.",
    "<strong>{{titre}}</strong>, composé de <em>{{titre-1}}</em>- et de -<em>{{titre-2}}</em>, consiste en l'étude d'une langue et de sa littérature à partir de documents écrits."
];

// --- Utilitaires ---
var readFile = function (path, binary) {
    if(!binary)
        return fs.readFileSync(path).toString('utf8');
    return fs.readFileSync(path, {encoding: 'binary'});
};

var writeFile = function (path, texte) {
    fs.writeFileSync(path, texte);
};

// ---------------------------------------------------------
//  Fonctions pour communiquer avec Wikipédia
//  (trouver des articles au hasard et extraire des images)
// ---------------------------------------------------------

/*
 * Requête *synchrone* pour obtenir du JSON depuis un API
 * quelconque.
 *
 * NOTEZ : ce code fait l'affaire pour ce TP, mais ne serait pas
 * acceptable dans un vrai serveur web. Pour simplifier le travail à
 * faire dans ce TP, on va néanmoins utiliser cette approximation, qui
 * serait beaucoup trop lente à exécuter sur un vrai site pour ne pas
 * que le site "laggue".
 */
var jsonRequestSync = function(url) {
    try {
        var response = request('GET', url);
    } catch(e) {
        return false;
    }

    if(response.statusCode != '200') {
        console.error(new Error("Page web invalide").stack);
        return false;
    }

    try {
        return JSON.parse(response.body.toString());
    } catch(e) {
        console.error(new Error("Page web invalide").stack);
    }
};

/*
 * Retourne un tableau contenant `n` titres de pages au hasard de
 * Wikipédia français
 */
var getRandomPageTitles = function(n) {
    var req = jsonRequestSync('https://fr.wikipedia.org/w/api.php?action=query&list=random&rnnamespace=0&rnlimit=' + n + '&format=json');

    if(!req) {
        return Array(n).fill("Pas d'internet");
    }

    return req.query.random.map(function(x) {
        return x.title;
    });
};

var md5 = function(data) {
    return crypto.createHash('md5').update(data).digest("hex");
};

/*
 * Découpe le nom de fichier donné par Wikipédia pour l'image et
 * retourne son URL
 */
var fileUrl = function(wikipediaName) {
    var filename = wikipediaName.slice('Fichier:'.length).split(' ').join('_');

    var hash = md5(filename).slice(0, 2);

    return "https://upload.wikimedia.org/wikipedia/commons/" + hash[0] + '/' + hash + '/' + filename;
};

/*
 * Retourne l'URL de la première image de l'article Wikipédia dont le
 * titre est title.
 */
var getPageFirstImage = function(title) {
    var encodedTitle = encodeURIComponent(title);

    var pageUrl = "https://fr.wikipedia.org/w/api.php?action=query&titles=" +
                  encodedTitle + "&format=json&prop=images&imlimit=30";

    var req = jsonRequestSync(pageUrl);

    if(!req) {
        return undefined;
    }

    var pages = req.query.pages;

    if(typeof(pages[-1]) === "undefined") {
        var page = Object.values(pages)[0];

        if(typeof(page.images) === 'undefined') {
            return false;
        }

        var images = page.images.map(function(img) {
            return img.title;
        });

        images = images.filter(function(x) {
            var parts = x.split('.');
            return ['jpg', 'png', 'jpeg', 'gif'].indexOf(parts[parts.length - 1]) !== -1;
        });

        if(images.length > 0)
            return images[0];
    }

    return false;
};

/*
 * Retourne une image de Wikipédia Français pour l'article nommé
 * title. Si l'article existe, et comporte des images, cette fonction
 * retourne la première image de l'article (selon l'ordre retourné par
 * l'API de Wikipédia), sinon cette fonction trouve une image au
 * hasard.
 */
var getImage = function(title) {

    var img = false;
    var url;
    do {

        if(typeof(title) !== 'undefined') {
            // 1. Vérifier si la page Wikipédia de "title" existe
            img = getPageFirstImage(title);
        }

        if(!img) {
            do {
                // 2. Lister 10 articles au hasard de Wikipédia
                var randomPages = getRandomPageTitles(10);

                for(var i=0; i<randomPages.length; i++) {
                    img = getPageFirstImage(randomPages[i]);
                    if(img !== false) {
                        break;
                    }
                }
            } while(img === false);
        }

        if(img === undefined) {
            // Pas d'internet
            return '/no-internet.png';
        }

        url = fileUrl(img);

        title = undefined;
        img = false;

        try {
            var response = request('HEAD', url);

            // Image trop petite, on en trouve une autre
            if(response.headers['content-length'] < 30000) {
                response = false;
                continue;
            }
        } catch(e) {
            continue;
        }

    } while(!response || response.statusCode != '200');

    return url;
};

// --------------------
//  Gestion du serveur
// --------------------
var port = 1337;
var hostUrl = 'http://localhost:'+port+'/';
var defaultPage = '/index.html';

var mimes = {
    '.html': 'text/html',
    '.css': 'text/css',
    '.js': 'text/javascript',
    '.png': 'image/png',
    '.jpg': 'image/jpg',
};

// --- Server handler ---
var redirect = function (reponse, path, query) {
    var newLocation = path + (query == null ? '' : '?' + query);
    reponse.writeHeader(302, {'Location' : newLocation });
    reponse.end('302 page déplacée');
};

var getDocument = function (url) {
    var pathname = url.pathname;
    var parsedPath = pathParse(url.pathname);
    var result = { data: null, status: 200, type: null };

    if(Object.keys(mimes).indexOf(parsedPath.ext) != -1) {
        result.type = mimes[parsedPath.ext];
    } else {
        result.type = 'text/plain';
    }

    try {
        if(['.png', '.jpg'].indexOf(parsedPath.ext) !== -1) {
            result.data = readFile('./public' + pathname, {encoding: 'binary'});
            result.encoding = 'binary';
        } else {
            result.data = readFile('./public' + pathname);
        }
        console.log('['+new Date().toLocaleString('iso') + "] GET " + url.path);
    } catch (e) {
        // File not found.
        console.log('['+new Date().toLocaleString('iso') + "] GET " +
                    url.path + ' not found');
        result.data = readFile('template/error404.html');
        result.type = 'text/html';
        result.status = 404;
    }

    return result;
};

var sendPage = function (reponse, page) {
    reponse.writeHeader(page.status, {'Content-Type' : page.type});
    reponse.end(page.data, page.encoding || 'utf8');
};



/*
-------------------------------------------------------------------------------
                           _____ ___  ____   ___
                          |_   _/ _ \|  _ \ / _ \
                            | || | | | | | | | | |
                            | || |_| | |_| | |_| |
                            |_| \___/|____/ \___/

                   Le code à compléter se trouve ci-dessous
-------------------------------------------------------------------------------
*/

// -------------------------------------
//  Logique de l'application ci-dessous
//    LE SEUL CODE QUE VOUS AVEZ À
//       MODIFIER EST CI-DESSOUS
// -------------------------------------

/*
 * Prend en param du texte et remplace des caractères spéciaux
 */
var escapeHtml = function (texte) {
    return texte
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
};

/*
 * Prend en param du texte, une étiquette et une valeur
 * et retourne le texte avec toutes les occurences de l'étiquette
 * remplacées par la valeur
 */
var substituerEtiquette = function (texte, etiquette, valeur) {
    while (texte.indexOf(etiquette) !== -1) {
        if (etiquette.substring(0, 3) === '{{{' &&
            etiquette.substring(etiquette.length - 3) === '}}}') {
            texte =  texte.substring(0, texte.indexOf(etiquette)) + valeur + texte.substring(texte.indexOf(etiquette) + etiquette.length);
        } else if (etiquette.substring(0, 2) === '{{' && etiquette.substring(etiquette.length - 2) === '}}') {
            texte =  texte.substring(0, texte.indexOf(etiquette)) + escapeHtml(valeur) + texte.substring(texte.indexOf(etiquette) + etiquette.length);
        } else {
            // la fonction retourne le meme texte
            // s'il y a une erreur d'etiquette
            return texte;
        }
    }
    return texte;
};

/*
 * Retourne le contenu du fichier template/index.html dont les étiquettes ont
 * été remplacées
 */
var getIndex = function () {
    const n = 20;
    const imageSrc = getImage('random');
    const articles = getRandomPageTitles(n);

    // récupérer le contenu du fichier index.html
    let page = readFile('template/index.html');

    // remplacer l'étiquette dans le contenu
    page = substituerEtiquette(page, '{{img}}', imageSrc);
    var articleHtml = '';

    // création d'une liste des liens récupérés
    for (let i = 0; i < n; i++) {
        articleHtml += '<li><a href="/article/' + articles[i] + '">' + articles[i] + '</a></li>\n';
    }

    // remplacer l'étiquette par la liste
    page = substituerEtiquette(page, '{{{articles-recents}}}', articleHtml);

    return page;
};

/*
 * Retourne le contenu du fichier template/article.html avec des substitutions
 */
var getArticle = function(titre) {
    const imageSrc = getImage(titre);
    //récupérer une phrase au hasard
    let premierePhrase = premieresPhrases[Math.floor(Math.random() * premieresPhrases.length)];

    // remplacer les étiquettes de la première phrase par les titres souhaités
    premierePhrase = substituerEtiquette(premierePhrase, '{{titre}}', titre);
    premierePhrase = substituerEtiquette(premierePhrase, '{{titre-1}}', titre.substring(0, titre.length / 2));
    premierePhrase = substituerEtiquette(premierePhrase, '{{titre-2}}', titre.substring(titre.length / 2));

    let modele = creerModele(readFile('corpus/wikipedia'));

    // génerer un article
    let contenu = [
        premierePhrase,
        // retourne un tableau qui sera transformé directement en éléments du tableau contenu
        ...genererParagraphes(modele,
            // nombre de paragraghes entre 4 et 7
            4 + Math.floor(Math.random() * 3),
            11, // chaque paragraphe contient entre 1 et 11 phrases 
            40) // une phrase est composée de maximum 40 mots 
    ];

    contenu = contenu.map(paragraphe => {
        // restructurer l'article à l'aide des tags <p>, <strong>, <em> et <a>.
        return '<p>' + paragraphe.split(' ').map(mot => {
            if (mot.length >=  7) {
                const prob = Math.random();
                
                if (prob <= 0.15) 
                    return '<strong>' + mot + '</strong>';
                else if (prob <= 0.30) 
                    return '<em>' + mot + '</em>';
                else if (prob <= 0.45) 
                    return '<a href="/article/' + mot + '">' + mot + '</a>';
                else return mot;
            }
            return mot;
        }).join(' ') + '</p>';
    }).join('');

    let page = readFile('template/article.html');

    page = substituerEtiquette(page, '{{titre}}', titre);
    page = substituerEtiquette(page, '{{img}}', imageSrc);
    page = substituerEtiquette(page, '{{{contenu}}}', contenu);

    return page;
};

// ---------------------------------------------------------
//  Tests
// ---------------------------------------------------------
var escapeHtml = function (texte) {
    return texte
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
};

var tests = function () {
    // ---------------------------------------------------------
    //  substituerEtiquette
    // ---------------------------------------------------------
    var texte1 = 'université de {{{toronto}}} est située à {{{toronto}}}';
    var etiquette1 = '{{{toronto}}}';
    var valeur1 = 'montréal';
    var expected1 = 'université de montréal est située à montréal';
    console.assert(expected1 == substituerEtiquette(texte1, etiquette1, valeur1));

    var texte2 = 'université de {{{toronto}}}';
    var etiquette2 = '{{{montreal}}}';
    var valeur2 = 'montréal';
    console.assert(texte2 == substituerEtiquette(texte2, etiquette2, valeur2));

    var texte3 = 'université de {{toronto}} est située à {{toronto}}';
    var etiquette3 = '{{toronto}}';
    var valeur3 = 'montréal';
    var expected3 = 'université de montréal est située à montréal';
    console.assert(expected3 == substituerEtiquette(texte3, etiquette3, valeur3));

    var texte4 = 'université de {{toronto}}';
    var etiquette4 = '{{montreal}}';
    var valeur4 = 'montréal';
    var expected4 = 'université de {{toronto}}';
    console.assert(expected4 == substituerEtiquette(texte4, etiquette4, valeur4));

    var texte5 = 'une inégalité de type {{eq}} est fausse';
    var etiquette5 = '{{eq}}';
    var valeur5 = '8 < 5';
    var expected5 = 'une inégalité de type 8 &lt; 5 est fausse';
    console.assert(expected5 == substituerEtiquette(texte5, etiquette5, valeur5));

    var texte6 = 'université de {toronto}';
    var etiquette6 = '{toronto}';
    var valeur6 = 'montréal';
    var expected6 = 'université de {toronto}';
    console.assert(expected6 == substituerEtiquette(texte6, etiquette6, valeur6));
};

/*
 * Création du serveur HTTP
 * Note : pas besoin de toucher au code ici (sauf peut-être si vous
 * faites les bonus)
 */
http.createServer(function (requete, reponse) {
    var url = urlParse(requete.url);

    // Redirect to index.html
    if (url.pathname == '/') {
        redirect(reponse, defaultPage);
        return;
    }

    var doc;

    if (url.pathname == defaultPage) {
        // Index
        doc = {status: 200, data: getIndex(), type: 'text/html'};
    } else if(url.pathname == '/random') {
        // Page au hasard
        redirect(reponse, '/article/' + encodeURIComponent(getRandomPageTitles(1)[0]));
        return;
    } else {
        var parsedPath = pathParse(url.pathname);

        if(parsedPath.dir == '/article') {
            var title;

            try {
                title = decodeURIComponent(parsedPath.base);
            } catch(e) {
                title = parsedPath.base.split('%20').join(' ');
            }

            // Force les articles à commencer avec une majuscule si c'est une lettre
            var capital = title.charAt(0).toUpperCase() + title.slice(1);
            if(capital != title) {
                redirect(reponse, encodeURIComponent(capital));
                return;
            }

            doc = {status: 200, data: getArticle(title), type: 'text/html'};
        } else {
            doc = getDocument(url);
        }
    }

    sendPage(reponse, doc);
}).listen(port);
