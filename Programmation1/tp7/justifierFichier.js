//

var fs = require("fs");

var readFile = function (path) {
    return fs.readFileSync(path).toString();
};

var writeFile = function (path, texte) {
    fs.writeFileSync(path, texte);
};

var print = function (x) {
    console.log(x);
};

// prend en paramètres le path du fichier d'entrée (string),
// le path du fichier de sortie (string) et le nombre de
// colonnes (int) et écrit dans la sortir le texte de l'entrée
// justifié au nombre de colonnes spécifié.
var justifierFichier = function (inputPath, outputPath, nbColonnes) {
    var texteOriginal = readFile(inputPath);
    writeFile(outputPath, justifierTexte(texteOriginal, nbColonnes));
};

// prend en paramètres un texte à justifier (string) ainsi
// qu'une largeur maximale [nb de colonnes] (int)
// et retourne le texte justifié (string)
var justifierTexte = function (texte, largeurMax) {
    // on créé un tableau composé uniquement de mots
    // (sans espace, sans saut de ligne)
    var mots = texte.split('\n').join(' ').split(' ');
    var texteJustifie = '';

    // tant que nous n'avons pas traité tous les mots du texte
    while(mots.length > 0) {
        var ligneCourante = '';
        // dans le cas où le mot comporte un trait d'union et qu'il
        // dépasse la largeur max, on le scinde récursivement
        // pour essayer d'en faire rentrer des sous-parties
        while (mots[0].length > largeurMax && mots[0].includes('-')) {
            var motsComposes = mots[0].split('-');
            // on retire le mot composé de notre liste de mots
            mots.shift(0);
            // on insère la fin du mot composé en début de notre liste
            mots.unshift(motsComposes.pop());
            // on crée un nouveau mot composé sans la fin...
            var reduced = motsComposes.reduce(function (x, y) {
                return x + '-' + y;
            });
            //...et on l'ajoute au début de notre liste de mots
            mots.unshift(reduced);
        }
        ligneCourante += mots.shift(0);
        // la ligne courante incrémentée du mot courant et d'un espace
        // ne doit pas excéder la limite
        while ((mots.length != 0) && (ligneCourante.length + mots[0].length + 1
            <= largeurMax)) {
            ligneCourante += ' ' + mots.shift(0);
        }

        texteJustifie += justifierLigne(ligneCourante, largeurMax) + '\n';
    }
    return texteJustifie;
};

// prend en paramètres une ligne (string) et une largeur
// maximale [nb de colonnes] (int)
// et retourne la ligne justifiée (string)
var justifierLigne = function (ligne, largeurMax) {
    // si la ligne fait déjà la largeur requise,
    // on la retourne directement
    if (ligne.length < largeurMax) {
        var mots = ligne.split(' ');
        // longueur des mots sans espace
        var longueurOutput = 0;

        mots.forEach( function (x) {
            longueurOutput += x.length;
        });

        // nombre de caractères à ajouter aux mots pour
        // atteindre la largeur demandée
        var nbCharARemplir = largeurMax - longueurOutput;
        // nombres d'emplacement où on peut ajouter des espaces
        var nbEspacesARemplacer = mots.length - 1;
        // nombre d'espaces à insérer entre 2 mots
        var espacement = 0;

        if (nbEspacesARemplacer == 0) return ligne;
        else if (nbCharARemplir % nbEspacesARemplacer == 0) {
            espacement = nbCharARemplir / nbEspacesARemplacer;
        } else {
            // on essaye d'avoir une répartitiom équitable des espaces
            var plafondEspacement = Math.ceil(
                nbCharARemplir / nbEspacesARemplacer);
            if (longueurOutput + plafondEspacement * (nbEspacesARemplacer-1)
                < largeurMax) {
                espacement = plafondEspacement;
            } else {
                espacement = Math.floor(nbCharARemplir / (nbEspacesARemplacer));
            }
        }

        var output = '';
        for (var i = 0; i < nbEspacesARemplacer - 1; i++) {
            output += mots[i];
            // on insère le nombre d'espaces définis précédemment
            // entre chaque mot
            output += Array(espacement).fill(' ').join('');
        }
        output += mots[mots.length - 2];
        if (nbCharARemplir % nbEspacesARemplacer == 0) {
            output += Array(espacement).fill(' ').join('');
        } else {
            // pour le dernier espace, si le partage ne peut être équitable,
            // on remplit simplement jusqu'à la limite de largeur
            output += Array(nbCharARemplir -
                espacement * (nbEspacesARemplacer-1)).fill(' ').join('');
        }
        output += mots[mots.length - 1];

        return output;
    }
    return ligne;
};

// tests unitaires
var test = function () {
    // tests de 'justifierLigne'
    console.assert(justifierLigne('Lorem ipsum dolor sit', 21) ==
        'Lorem ipsum dolor sit');
    console.assert(justifierLigne('Lorem ipsum dolor sit', 35) ==
        'Lorem      ipsum      dolor     sit');

    // tests de 'justifierTexte'
    console.assert(justifierTexte(
        'the quick brown fox jumps over the lazy dog', 15) ==
        'the quick brown\nfox  jumps over\nthe   lazy  dog\n');

};

test();
justifierFichier("qbfjold.txt", "out.txt", 15);