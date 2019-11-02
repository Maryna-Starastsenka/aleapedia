/* Auteurs: PERSONNE 1 (matricule), PERSONNE 2 (matricule)

   TODO : Remplacez ce commentaire par un commentaire expliquant
   l'utilité de ce programme
 */
function cloneImg(data) {
    return data.map(function(line) {
        return line.map(function(pix) {
            return Object.assign({}, pix);
        });
    });
}

function noirEtBlanc(imageOriginale) {
    var imageCopie = cloneImg(imageOriginale);
    for (var i = 0; i < imageCopie.length ; i++) {
        for (var j = 0; j < imageCopie[0].length ; j++) {
            var lum = luminance(imageCopie[i][j].r, imageCopie[i][j].g, imageCopie[i][j].b);
            imageCopie[i][j].r = lum;
            imageCopie[i][j].g = lum;
            imageCopie[i][j].b = lum;
        }
    }
    return imageCopie;
}

function luminance(r,g,b) {
    return r*0.2126+g*0.7152+b*0.0722;
}
function correctionClarte(imageOriginale, quantite) {
    var imageCopie = cloneImg(imageOriginale);
    for (var i = 0; i < imageCopie.length ; i++) {
        for (var j = 0; j < imageCopie[0].length ; j++) {
            imageCopie[i][j].r = corrClarteCouleur(imageCopie[i][j].r,quantite);
            imageCopie[i][j].g = corrClarteCouleur(imageCopie[i][j].g,quantite);
            imageCopie[i][j].b = corrClarteCouleur(imageCopie[i][j].b,quantite);
        }
    }
    return imageCopie; // Remplacer par la nouvelle image
}

function corrClarteCouleur(valeur,quantite) {
    return Math.pow(valeur/255,quantite)*255;
}

function flou(imageOriginale, taille) {
    console.log('TODO'); // TODO : Compléter cette fonction
    return imageOriginale; // Remplacer par la nouvelle image
}

function detectionContours(imageOriginale) {
    console.log('TODO'); // TODO : Compléter cette fonction
    return imageOriginale; // Remplacer par la nouvelle image
}

// ==> N'hésitez pas à ajouter vos propres fonctions pour vous aider <==
// function ...

function tests() {
    var test1Input = [[{r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}],
        [{r: 0, g: 0, b: 0}, {r: 255, g: 255, b: 255}, {r: 0, g: 0, b: 0}],
        [{r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}]];
    var test2Input = [[{r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}],
        [{r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}],
        [{r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}]];
    var test3Input = [[{r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}],
        [{r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}],
        [{r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}]];

    console.assert(areEqual(test1Input,test1Input));
    console.assert(!areEqual(test1Input,test2Input));
    console.assert(!areEqual(test2Input,test3Input));

    console.assert(areEqual(noirEtBlanc(test1Input),test1Input));

    var test4Input = [[{r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}],
            [{r: 0, g: 0, b: 0}, {r: 255, g: 255, b: 255}]];
    var test5Input = [[{r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}],
        [{r: 0, g: 0, b: 0}, {r: 255, g: 255, b: 255}]];

    console.assert(areEqual(correctionClarte(test4Input, 1.5),test5Input));

    var test6Input = [[{r: 100, g: 75, b: 3}, {r: 34, g: 134, b: 98}],
        [{r: 76, g: 0, b: 233}, {r: 25, g: 142, b: 78}]];
    var test7Input = [[{r: 63, g: 41, b: 0}, {r: 12, g: 97, b: 61}],
        [{r: 41, g: 0, b: 223}, {r: 8, g: 106, b: 43}]];

    console.assert(areEqual(correctionClarte(test6Input,1.5),test7Input));
}

function areEqual(tab1, tab2) {
    if (tab1.length == tab2.length && tab1.length >= 1
    && tab1[0].length == tab2[0].length) {
        for (var i = 0; i < tab1.length ; i++) {
            for (var j = 0; j < tab1[0].length ; j++) {
                if (Math.round(tab1[i][j].r) != Math.round(tab2[i][j].r) ||
                    Math.round(tab1[i][j].g) != Math.round(tab2[i][j].g) ||
                    Math.round(tab1[i][j].b) != Math.round(tab2[i][j].b)) {
                    return false;
                }
            }

        }
        return true;
    }
    return false;
}

