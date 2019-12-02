// Utilitaires pour manipuler des fichiers
var fs = require("fs");

var readFile = function (path) {
    return fs.readFileSync(path).toString();
};

var writeFile = function (path, texte) {
    fs.writeFileSync(path, texte);
};


/* ---- Fonctions outils --- */
var texteEnMots = function(texte) {
    var phrases = [];
    var bloc = texte.split('\n').forEach(
        (ligne) => {
            var mots = ligne.split(' ');
            mots.unshift('');
            phrases.push(mots);
        }
    );
    var mots = [];
    phrases.forEach((phrase) => {
        phrase.forEach((mot) => {
            mots.push(mot);
        })
    });
    return mots;
};

var ajouterProchainMot = function(tableauDico, mot) {
    var idxMatch = -1;
    tableauDico.forEach((dico, idx) => {
        if (dico.mot == mot) {
            idxMatch = idx;
        }
    });
    if (idxMatch == -1) {
        tableauDico.push({mot: mot, prob: 0, freq: 1});
    } else {
        tableauDico[idxMatch].freq += 1;
    }
    return tableauDico;
};

var calculerProb = function(modele, nbMotsSuivants) {
    modele.prochainsMots.forEach((tableauDico, idx) => {
        tableauDico.forEach((dico) => {
            dico.prob = dico.freq / nbMotsSuivants[idx];
        });
    });
};

var supprimerNbOccDuModele = function(modele) {
    modele.prochainsMots.forEach((tableauDico) => {
        tableauDico.forEach((dico, idx2) => {
            tableauDico[idx2] = {mot: dico.mot, prob: dico.prob};
        });
    });
};

/* ----        END       --- */

// TODO : compléter cette fonction
var creerModele = function(texte) {
    var mots = texteEnMots(texte);

    var modele = {dictionnaire: [], prochainsMots: []};
    var nbMotsSuivants = [];

    var tailleMots = mots.length;
    mots.forEach((mot, idx) => {
        if (idx < tailleMots - 1) {
            if (modele.dictionnaire.includes(mot)) {
                var idxDico = modele.dictionnaire.indexOf(mot);

                modele.prochainsMots[idxDico] = ajouterProchainMot(modele.prochainsMots[idxDico], mots[idx + 1]);
                nbMotsSuivants[idxDico] += 1;
            } else {
                modele.dictionnaire.push(mot);

                modele.prochainsMots.push([{mot: mots[idx + 1], prob: 1.0, freq: 1}]);
                nbMotsSuivants.push(1);
            }
        }
    });

    calculerProb(modele, nbMotsSuivants);
    supprimerNbOccDuModele(modele);
    return modele;
};


// TODO : compléter cette fonction
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

// TODO : compléter cette fonction
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

function getRandomInt(max) {
    return Math.floor(Math.random() * Math.floor(max));
}

// TODO : compléter cette fonction
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




var tests = function() {
    // TODO : Écrivez des tests ici

    /* Les tests seront lancés automatiquement si vous appelez ce
    fichier avec :

       node markov.js

     */

    // Utilisez console.assert(a == b); pour faire des tests unitaires
    console.log('TODO : exécuter des tests');

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
