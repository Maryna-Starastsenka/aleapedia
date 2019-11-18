/*
 Nom : Maryna Starastsenka, Matricule : 20166402
 Ce fichier a pour objectif de justifier du texte
*/

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
        // dépasse la largeur max, on le scinde pour essayer
        // d'en faire rentrer le maximum dans la ligne courante
        gererMotsComposes(mots, largeurMax, ligneCourante.length);

        ligneCourante += mots.shift(0);
        // la ligne courante incrémentée du mot courant et d'un espace
        // ne doit pas excéder la limite
        while ((mots.length != 0) && (ligneCourante.length +
            mots[0].split('-')[0].length + 1 <= largeurMax)) {
            gererMotsComposes(mots, largeurMax, ligneCourante.length);
            ligneCourante += ' ' + mots.shift(0);
        }

        texteJustifie += justifierLigne(ligneCourante, largeurMax);
        if (mots.length > 0) {
            texteJustifie += '\n';
        }
    }
    return texteJustifie;
};

// prend en paramètres une liste de mots par référence ([string]),
// une largeur max (int) et la largeur de la ligne courante (int)
// et modifie la liste de mots sur place
function gererMotsComposes(mots, largeurMax, largeurLigneCourante) {
    if (mots[0].includes('-')) {
        // si le mot est <= à largeurMax, on ne fait rien,
        // il rentre déjà tel quel
        if (largeurLigneCourante + mots[0].length > largeurMax) {
            var motsComposes = mots[0].split('-');
            var motOutput = '';
            // on compose le mot de la ligne courante
            while (largeurLigneCourante + motOutput.length +
            motsComposes[0].length + 1 <= largeurMax) {
                motOutput += motsComposes.shift(0) + '-';
            }
            // on retire le 1er mot composé pour le remplacer
            // par une version plus courte
            mots.shift(0);
            if (motsComposes.length > 1) {
                var motRaccourci = motsComposes.reduce(function (x, y) {
                    return x + '-' + y;
                });
                mots.unshift(motRaccourci);
            } else if (motsComposes.length == 1) {
                mots.unshift(motsComposes[0]);
            }
            // on évite la boucle infinie
            if (motOutput != '') mots.unshift(motOutput);
        }
    }
}

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
    // tests de 'gererMotsComposes'
    // mot composé dont on ne peut prendre que la 1ère partie en fin de ligne
    var mots = ['rez-de-chaussée'];
    gererMotsComposes(mots, 12, 7);
    console.assert(mots[0] == 'rez-' && mots[1] == 'de-chaussée'
        && mots.length == 2);
    // mot composé dont on peut prendre les 2 1ères parties
    mots = ['rez-de-chaussée'];
    gererMotsComposes(mots, 7, 0);
    console.assert(mots[0] == 'rez-de-' && mots[1] == 'chaussée'
        && mots.length == 2);
    // mot composé dont on ne peut prendre que la 1ère partie en début de ligne
    mots = ['rez-de-chaussée'];
    gererMotsComposes(mots, 4, 0);
    console.assert(mots[0] == 'rez-' && mots[1] == 'de-chaussée'
        && mots.length == 2);

    // tests de 'justifierLigne'
    // largeur trop courte pour la ligne
    console.assert(justifierLigne('Lorem ipsum dolor sit', 10) ==
        'Lorem ipsum dolor sit');
    // largeur identique à l'original
    console.assert(justifierLigne('Lorem ipsum dolor sit', 21) ==
        'Lorem ipsum dolor sit');
    // largeur supérieure à l'original avec espacement équitable possible
    console.assert(justifierLigne('Lorem ipsum dolor sit', 33) ==
        'Lorem     ipsum     dolor     sit');
    // largeur supérieure à l'original avec espacement équitable impossible
    console.assert(justifierLigne('Lorem ipsum dolor sit', 35) ==
        'Lorem      ipsum      dolor     sit');

    // tests de 'justifierTexte'
    // un mot trop long
    console.assert(justifierTexte(
        'UnMotBienTropLongPourRentrer et d\'autres plus petits', 15) ==
        'UnMotBienTropLongPourRentrer\net     d\'autres\nplus     petits');
    // une phrase type
    console.assert(justifierTexte(
        'the quick brown fox jumps over the lazy dog', 15) ==
        'the quick brown\nfox  jumps over\nthe   lazy  dog');
    // la même phrase avec des sauts de ligne
    console.assert(justifierTexte(
        'the quick\nbrown fox\njumps over the lazy\ndog', 15) ==
        'the quick brown\nfox  jumps over\nthe   lazy  dog');
    // long mot composé en 3 morceaux
    console.assert(justifierTexte('rez-de-chaussée', 6) ==
        'rez-\nde-\nchaussée');
    // long mot composé en 2 morceaux
    console.assert(justifierTexte('rez-de-chaussée', 7) ==
        'rez-de-\nchaussée');
    // long mot composé en fin de ligne
    console.assert(justifierTexte('Être au rez-de-chaussée', 12) ==
        'Être au rez-\nde-chaussée');
    // long mot composé en début de ligne
    console.assert(justifierTexte(
        'Terre-Neuve-et-Labrador est une province du Canada, située à ' +
        'l\'extrême Est du pays', 15) == 'Terre-Neuve-et-\nLabrador    est\n' +
        'une province du\nCanada,  située\nà l\'extrême Est\ndu         pays');
};

test();
justifierFichier("qbfjold.txt", "out.txt", 15);
justifierFichier("ipsum.txt", "out2.txt", 15);
/*print(justifierTexte('mamie-vol-au-vent', 10));
print(justifierTexte('vol-au-vent', 6));
print(justifierTexte('Être au rez-de-chaussée', 12));*/
