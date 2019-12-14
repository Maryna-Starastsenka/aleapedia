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
            if (mot in dicoCache) { // plus rapide que indexOf sur un tableau
                var idxCache = dicoCache[mot];
                // on ajoute le mot suivant observé ou on incrémente sa fréq
                modele.prochainsMots[idxCache] = ajouterProchainMot(
                    modele.prochainsMots[idxCache], mots[idx + 1]);
                // on garde en mémoire le nombre de mots suivants
                // que présente le mot courant
                nbMotsSuivants[idxCache] += 1;
            } else {
                modele.dictionnaire.push(mot); // on ajoute le mot jamais vu
                dicoCache[mot] = modele.dictionnaire.length - 1; // et on retient son indice
                // le mot est nouveau donc il n'y a pas encore de mot suivant pour ce mot
                modele.prochainsMots.push([{mot: mots[idx + 1], prob: 1.0, freq: 1}]);
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
 * Prend
 */
var genererProchainMot = function(modele, motActuel) {
    var rand = Math.random();
    var idx = modele.dictionnaire.indexOf(motActuel);
    var tableauDico = modele.prochainsMots[idx];
    var somme = 0;
    var idxMotSuivant = -1;
    do {
        idxMotSuivant++;
        somme += tableauDico[idxMotSuivant].prob;
    } while (rand > somme);
    return tableauDico[idxMotSuivant].mot
};

/*
 * Prend
 */
var genererPhrase = function(modele, maxNbMots) {
    var nbMots = 0;
    var motCourant = '';
    var phrase = '';
    while (nbMots < maxNbMots && !motCourant.includes('.')) {
        motCourant = genererProchainMot(modele, motCourant);
        if (nbMots == 0) {
            phrase += motCourant;
        } else {
            phrase += ' ' + motCourant;
        }
        nbMots++;
    }

    if (!motCourant.includes('.')) {
        phrase += '.';
    }
    return phrase;
};

/*
 * Prend
 */
var genererParagraphes = function(modele, nbParagraphes, maxNbPhrases, maxNbMots) {
    var nbParagraphesCourant = 0;
    var paragraphes = [];
    while (nbParagraphesCourant < nbParagraphes) {
        var nbPhrases = 0;
        var phrases = '';
        var phraseCourante = '';
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

    // Utilisez console.assert(a == b); pour faire des tests unitaires

    // texteEnMots
    var texteTrivial = readFile('corpus/trivial');
    var computedTrivial = texteEnMots(texteTrivial);
    console.log(texteTrivial);
    console.log(computedTrivial);

    var texteExemple = readFile('corpus/exemple');
    var computedExemple = texteEnMots(texteExemple);
    console.log(texteExemple);
    console.log(computedExemple);

     var modele = creerModele(texteExemple);
    // var dico = {"Je":0, "En":0, "Ma":0};
    // for (let i = 0; i < 1000000; i++) {
    //     var a = genererProchainMot(modele, "");
    //     dico[a] +=1;
    // }

    //var a = genererPhrase(modele, 20);
    var paragraphes = genererParagraphes(modele, 4, 6, 10);
    console.log(paragraphes);
};

if (require.main === module) {
    // Si on se trouve ici, alors le fichier est exécuté via : nodejs gen.js
    tests(); // On lance les tests
} else {
    /* Sinon, le fichier est inclus depuis index.js
       On exporte les fonctions importantes pour le serveur web */
    exports.creerModele = creerModele;
    exports.genererParagraphes = genererParagraphes;
}
