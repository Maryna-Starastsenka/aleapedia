// Auteurs :
// Maryna Starastsenka (20166402), Ahmed Braik (20139676)

// Utilitaires pour manipuler des fichiers
var fs = require("fs");

var readFile = function (path) {
    return fs.readFileSync(path).toString();
};

var writeFile = function (path, texte) {
    fs.writeFileSync(path, texte);
};

// ---------------------------------------------------------
//  Fonctions outils
// ---------------------------------------------------------

/*
 * Prend un nombre max en param et
 * retourne un nombre entier aléatoire entre [0, max[
 */
function getRandomInt(max) {
    return Math.floor(Math.random() * Math.floor(max));
}

/*
 * Prend du texte (string) en param
 * et retourne un tableau de mots (sans espaces et '\n')
 */
var texteEnMots = function(texte) {
    var phrases = [];
    // pour chaque ligne
    texte.split('\n').forEach(
        ligne => {
            var mots = ligne.split(' ');
            // on commence une phrase par ''
            mots.unshift('');
            // on construit un tableau de phrases
            phrases.push(mots);
        }
    );
    var mots = [];
    // pour chaque phrase
    phrases.forEach(
        phrase => {
            phrase.forEach(
                mot => {
                    // on construit un tableau de mots
                    mots.push(mot);
            })
    });

    return mots;
};

/*
 * Prend un tableau de dictionnaires et le mot suivant courant
 * en param et retourne le tableau de dictionnaires mis à jour
 */
var ajouterProchainMot = function(prochainsMots, motSuivant) {
    var idxMatch = -1;
    prochainsMots.forEach((dico, idx) => {
        if (dico.mot == motSuivant) {
            idxMatch = idx;
        }
    });
    // si le motSuivant suivant n'a jamais été vu
    // on l'ajoute. La proba sera calculée plus tard
    if (idxMatch == -1) {
        prochainsMots.push({mot: motSuivant, prob: 0, freq: 1});
    } else {
        // si le motSuivant suivant existe déjà, on incrémente
        // le nombre d'occurences
        prochainsMots[idxMatch].freq += 1;
    }

    return prochainsMots;
};

/*
 * Prend un modele et un tableau des nombres de mots suivants
 * ordonnés pour chaque mot du texte
 * et calcule les probabilités d'occurence de chaque mot suivant
 */
var calculerProb = function(modele, nbMotsSuivants) {
    modele.prochainsMots.forEach((tableauDico, idx) =>
        tableauDico.forEach(dico => {
            dico.prob = dico.freq / nbMotsSuivants[idx];
        }));
};

/*
 * Prend un modele en param
 * et supprime l'élément fréq de chaque dictionnaire
 * de mots suivants
 */
var supprimerNbOccDuModele = function(modele) {
    modele.prochainsMots.forEach((tableauDico) => {
        tableauDico.forEach((dico, idx) => {
            tableauDico[idx] = {mot: dico.mot, prob: dico.prob};
        });
    });
};

// ---------------------------------------------------------
//  Fonctions de
// ---------------------------------------------------------

/*
 * Prend un texte (string) en param et
 * retourne un modèle de génération probabiliste de texte
 */
var creerModele = function(texte) {
    // pour retrouver l'indice d'un mot déjà
    // visité, on créé un dictionnaire de cache qui
    // permet un accès plus rapide qu'avec un tableau
    var dicoCache = {};
    var mots = texteEnMots(texte);
    var modele = {dictionnaire: [], prochainsMots: []};
    var nbMotsSuivants = []; // pour calculer les probabilités

    var tailleMots = mots.length;
    mots.forEach((mot, idx) => {
        if (idx < tailleMots - 1) {
            // plus rapide que indexOf sur un tableau
            if (mot in dicoCache) { 
                var idxCache = dicoCache[mot];
                // on ajoute le mot suivant observé ou 
                // on incrémente sa fréq
                modele.prochainsMots[idxCache] = ajouterProchainMot(
                    modele.prochainsMots[idxCache], mots[idx + 1]);
                // on garde en mémoire le nombre de mots suivants
                // que présente le mot courant
                nbMotsSuivants[idxCache] += 1;
            } else {
                // on ajoute le mot jamais vu
                modele.dictionnaire.push(mot); 
                // et on retient son indice
                dicoCache[mot] = modele.dictionnaire.length - 1; 
                // le mot est nouveau donc il n'y a pas encore 
                // de mot suivant pour ce mot
                modele.prochainsMots.push([{mot: mots[idx + 1], 
                    prob: 1.0, freq: 1}]);
                nbMotsSuivants.push(1);
            }
        }
    });

    // on calcule les proba pour chaque mot suivant
    calculerProb(modele, nbMotsSuivants);
    // et on supprime les fréquences du modèle
    supprimerNbOccDuModele(modele);

    return modele;
};


/*
 * Prend un modele probabiliste et un mot en param
 * et retourne le mot suivant aléatoire
 */
var genererProchainMot = function(modele, motActuel) {
    var idx = modele.dictionnaire.indexOf(motActuel);
    var idxMotSuivant = -1;
    var somme = 0;
    var motsSuivants = modele.prochainsMots[idx];
    var rand = Math.random(); // nb aléatoire entre [0,1[
    // sélection du mot suivant basé sur les probabilités
    do {
        idxMotSuivant++;
        somme += motsSuivants[idxMotSuivant].prob;
    } while (rand > somme);

    return motsSuivants[idxMotSuivant].mot
};

/*
 * Prend un modèle et un nb de mots max en param
 * et retourne une phrase autogénérée avec au plus
 * maxNbMots mots
 */
var genererPhrase = function(modele, maxNbMots) {
    var nbMots = 0; // compteur des mots de la phrase
    var motCourant = ''; // le premier mot est vide
    var phrase = ''; // output
    while (nbMots < maxNbMots && !motCourant.includes('.')) {
        motCourant = genererProchainMot(modele, motCourant);
        if (nbMots == 0) {
            phrase += motCourant;
        } else {
            phrase += ' ' + motCourant;
        }
        nbMots++;
    }
    // on doit terminer la phrase par un point
    if (!motCourant.includes('.')) {
        phrase += '.';
    }

    return phrase;
};

/*
 * Prend en param un modèle, un nombre exact de paragraphes,
 * un nombre max de phrases par paragraphe et un nombre
 * max de mots par phrase
 * et retourne un tableau de paragraphes avec entre 1 et maxNbPhrases
 * par paragraphe.
 */
var genererParagraphes = function(modele, nbParagraphes, 
    maxNbPhrases, maxNbMots) {
    var nbParagraphesCourant = 0;
    var paragraphes = [];

    while (nbParagraphesCourant < nbParagraphes) {
        var nbPhrases = 0;
        var phrases = '';
        var phraseCourante = '';
        // entre 1 et maxNbPhrases phrases
        var randomMaxNbPhrases = getRandomInt(maxNbPhrases) + 1;

        while (nbPhrases < randomMaxNbPhrases) {
            phraseCourante = genererPhrase(modele, maxNbMots);
            if (nbPhrases == 0) {
                phrases += phraseCourante;
            } else {
                phrases += ' ' + phraseCourante;
            }
            nbPhrases++;
        }

        paragraphes.push(phrases);
        nbParagraphesCourant++;
    }

    return paragraphes;
};

// ---------------------------------------------------------
//  Tests
// ---------------------------------------------------------

var tests = function() {
    /* Les tests seront lancés automatiquement si vous appelez ce
    fichier avec : node markov.js */

    // ---------------------------------------------------------
    //  tableauxSontEgaux
    // ---------------------------------------------------------
    var tab1 = [
        '', 'A', 'B', 'C.',
        '', 'A', 'B', 'A.',
        '', 'C', 'B', 'A.'
    ];

    var tab2egal1 = [
        '', 'A', 'B', 'C.',
        '', 'A', 'B', 'A.',
        '', 'C', 'B', 'A.'
    ];

    var tab3diff1 = [
        '', 'A', 'B', 'C.',
    ];

    console.assert(tableauxSontEgaux(tab1, tab2egal1));
    console.assert(!tableauxSontEgaux(tab1, tab3diff1));

    // ---------------------------------------------------------
    //  texteEnMots
    // ---------------------------------------------------------
    var texteTrivial = readFile('corpus/trivial');
    var computedTrivial = texteEnMots(texteTrivial);
    var expectedTrivial = [
        '', 'A', 'B', 'C.',
        '', 'A', 'B', 'A.',
        '', 'C', 'B', 'A.'
    ];

    console.assert(
        tableauxSontEgaux(expectedTrivial, computedTrivial));

    var texteExemple = readFile('corpus/exemple');
    var computedExemple = texteEnMots(texteExemple);
    var expectedExemple = [
        '',           'Je',          "m'appelle",
        'Marguerite', 'Lafontaine.', '',
        'Je',         "m'appelle",   'ainsi',
        'parce',      'que',         'la',
        'Marguerite', 'est',         'la',
        'fleur',      'préférée',    'de',
        'ma',         'mère.',       '',
        'En',         'général,',    'on',
        "m'appelle",  'Marguerite',  'car',
        'tel',        'est',         'mon',
        'nom.',       '',            'Ma',
        'mère',       "m'appelle",   '"Petit-Chou".'
    ];
    console.assert(
        tableauxSontEgaux(expectedExemple, computedExemple));

    // ---------------------------------------------------------
    //  ajouterProchainMot
    // ---------------------------------------------------------
    var tabDico = [{mot: "Je", prob: 1.0, freq: 1}, 
    {mot: "m'appelle", prob: 1.0, freq: 1}];
    ajouterProchainMot(tabDico, "Je");
    console.assert(tabDico[0].freq == 2);

    ajouterProchainMot(tabDico, "Marguerite");
    console.assert(tabDico[2].mot == "Marguerite");

    // ---------------------------------------------------------
    //  calculerProb
    // ---------------------------------------------------------
    var modeleSimplifie = {
        prochainsMots: [
            [{mot: "Je", prob: 1.0, freq: 4}, 
            {mot: "m'appelle", prob: 1.0, freq: 1}],
            [{mot: "ainsi", prob: 1.0, freq: 1}, 
            {mot: "Je", prob: 1.0, freq: 2}, 
            {mot: "crois", prob: 1.0, freq: 1}]
        ]
    };
    var modeleSimplifieExpected = {
        prochainsMots: [
            [{mot: "Je", prob: 0.8, freq: 4}, 
            {mot: "m'appelle", prob: 0.2, freq: 1}],
            [{mot: "ainsi", prob: 0.25, freq: 1}, 
            {mot: "Je", prob: 0.5, freq: 2}, 
            {mot: "crois", prob: 0.25, freq: 1}]
        ]
    };
    var nbMotsSuivants = [5, 4];
    calculerProb(modeleSimplifie, nbMotsSuivants);
    console.assert(
        tableauxSontEgaux(modeleSimplifieExpected, modeleSimplifie));

    // ---------------------------------------------------------
    //  supprimerNbOccDuModele
    // ---------------------------------------------------------
    var modeleSimplifie2Expected = {
        prochainsMots: [
            [{mot: "Je", prob: 0.8}, {mot: "m'appelle", prob: 0.2}],
            [{mot: "ainsi", prob: 0.25}, 
            {mot: "Je", prob: 0.5}, {mot: "crois", prob: 0.25}]
        ]
    };
    supprimerNbOccDuModele(modeleSimplifie);
    console.assert(
        tableauxSontEgaux(modeleSimplifie2Expected, modeleSimplifie));

    // ---------------------------------------------------------
    //  creerModele
    // ---------------------------------------------------------
    var modeleTrivial = creerModele(texteTrivial);
    var modeleTrivialExpected = {
        dictionnaire:["","A","B","C.","A.","C"],
        prochainsMots:
            [
                [{"mot":"A", "prob":0.6666666666666666},
                    {"mot":"C", "prob":0.3333333333333333}],
                [{"mot":"B", "prob":1}],
                [{"mot":"C.", "prob":0.3333333333333333},
                    {"mot":"A.", "prob":0.6666666666666666}],
                [{"mot":"", "prob":1}],
                [{"mot":"", "prob":1}],
                [{"mot":"B", "prob":1}]
            ]};
    console.assert(
        tableauxSontEgaux(modeleTrivialExpected, modeleTrivial));

    var modeleExemple = creerModele(texteExemple);
    var modeleExempleExpected = {
        dictionnaire:[
            "","Je","m'appelle","Marguerite","Lafontaine.",
                "ainsi","parce","que","la","est","fleur","préférée",
                "de","ma","mère.","En","général,","on","car","tel",
                "mon","nom.","Ma","mère"],
        prochainsMots:[
            [{"mot":"Je","prob":0.5},
            {"mot":"En","prob":0.25},{"mot":"Ma","prob":0.25}],
            [{"mot":"m'appelle","prob":1}],
            [{"mot":"Marguerite","prob":0.5},
            {"mot":"ainsi","prob":0.25},
                {"mot":"\"Petit-Chou\".","prob":0.25}],
            [{"mot":"Lafontaine.","prob":0.3333333333333333},
                {"mot":"est","prob":0.3333333333333333},
                {"mot":"car","prob":0.3333333333333333}],
            [{"mot":"","prob":1}],[{"mot":"parce","prob":1}],
            [{"mot":"que","prob":1}],[{"mot":"la","prob":1}],
            [{"mot":"Marguerite","prob":0.5},
            {"mot":"fleur","prob":0.5}],
            [{"mot":"la","prob":0.5},{"mot":"mon","prob":0.5}],
            [{"mot":"préférée","prob":1}],[{"mot":"de","prob":1}],
            [{"mot":"ma","prob":1}],
            [{"mot":"mère.","prob":1}],[{"mot":"","prob":1}],
            [{"mot":"général,","prob":1}],[{"mot":"on","prob":1}],
            [{"mot":"m'appelle","prob":1}],[{"mot":"tel","prob":1}],
            [{"mot":"est","prob":1}],[{"mot":"nom.","prob":1}],
            [{"mot":"","prob":1}],
            [{"mot":"mère","prob":1}],[{"mot":"m'appelle","prob":1}]
        ]
    };
    console.assert(
        tableauxSontEgaux(modeleExempleExpected, modeleExemple));

    // ---------------------------------------------------------
    //  genererProchainMot
    // ---------------------------------------------------------
    var prochainMotTrivial = genererProchainMot(modeleTrivial, "");
    console.assert(['A', 'C'].includes(prochainMotTrivial));

    var prochainMotExemple = 
        genererProchainMot(modeleExemple, "m'appelle");
    console.assert(
        ['Marguerite', 'ainsi', "\"Petit-Chou\"."]
            .includes(prochainMotExemple));

    // ---------------------------------------------------------
    //  genererPhrase
    // ---------------------------------------------------------
    var phraseExemple;
    var longueur;
    for (var i = 0; i < 5; i++) {
        phraseExemple = genererPhrase(modeleExemple, 3);
        longueur = phraseExemple.split(' ').length;
        console.assert(longueur >= 1 && longueur <= 3);
    }

    // ---------------------------------------------------------
    //  genererParagraphes
    // ---------------------------------------------------------
    paragraphesExemple = genererParagraphes(modeleExemple, 3, 5, 10);
    console.assert(paragraphesExemple.length == 3);
};

var tableauxSontEgaux = function(tab1, tab2) {
    return JSON.stringify(tab1) == JSON.stringify(tab2);
};

if (require.main === module) {
    // Si on se trouve ici, alors le fichier 
    // est exécuté via : nodejs gen.js
    tests(); // On lance les tests
} else {
    /* Sinon, le fichier est inclus depuis index.js
       On exporte les fonctions importantes pour le serveur web */
    exports.creerModele = creerModele;
    exports.genererParagraphes = genererParagraphes;
}
