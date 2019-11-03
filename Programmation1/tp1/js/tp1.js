/* Auteurs: PERSONNE 1 (matricule), PERSONNE 2 (matricule)

   TODO : Remplacez ce commentaire par un commentaire expliquant
   l'utilité de ce programme
 */
//fonction du fichier img.js
//prend un tableau initial et retourne sa copie
function cloneImg(data) {
    return data.map(function(line) {
        return line.map(function(pix) {
            return Object.assign({}, pix);
        });
    });
}

//prend un tableau original est retourne une image en noir et blanc
function noirEtBlanc(imageOriginale) {
    var imageCopie = cloneImg(imageOriginale);

    for (var i = 0; i < imageCopie.length ; i++) {
        for (var j = 0; j < imageCopie[0].length ; j++) {
            //remplacement de valeur r, g, b de chaque pixel par la luminance
            var lum = luminance(imageCopie[i][j].r, imageCopie[i][j].g, imageCopie[i][j].b);
            imageCopie[i][j].r = lum;
            imageCopie[i][j].g = lum;
            imageCopie[i][j].b = lum;
        }
    }
    return imageCopie;
}

//prend des valeurs r, g, b de chaque pixel
//et retourne une moyenne pondérée
function luminance(r,g,b) {
    return r*0.2126+g*0.7152+b*0.0722;
}

//prend une image originale et une quantité
//et retourne une image avec la clarté augmentée/réduite
function correctionClarte(imageOriginale, quantite) {
    var imageCopie = cloneImg(imageOriginale);

    for (var i = 0; i < imageCopie.length ; i++) {
        for (var j = 0; j < imageCopie[0].length ; j++) {
            //remplacement de valeur de chaque pixel
            // par la valeure plus ou moins lumineuse
            imageCopie[i][j].r = corrClarteCouleur(imageCopie[i][j].r,quantite);
            imageCopie[i][j].g = corrClarteCouleur(imageCopie[i][j].g,quantite);
            imageCopie[i][j].b = corrClarteCouleur(imageCopie[i][j].b,quantite);
        }
    }
    return imageCopie;
}

//prend des valeurs r, g, b de chaque pixel et une quantité
//et retourne une nouvelle valeur
function corrClarteCouleur(valeur, quantite) {
    return Math.pow(valeur/255,quantite)*255;
}

//prend une valeur du tableau oribinale et une taille du voisinage
//et retourne une image floue
function flou(imageOriginale, taille) {
    var imageCopie = cloneImg(imageOriginale);

    for (var i = 0; i < imageCopie.length ; i++) {
        for (var j = 0; j < imageCopie[0].length ; j++) {
            var sousImg = sousImage(imageOriginale,i,j,taille);
            var pixelFlou = ponderationFlou(sousImg,taille);
            imageCopie[i][j].r = pixelFlou.ponderR;
            imageCopie[i][j].g = pixelFlou.ponderG;
            imageCopie[i][j].b = pixelFlou.ponderB;
        }
    }
    return imageCopie;
}

//prend la copie de l'image originale, le noméro de ligne et de colonne,
//la taille de voisinage et retourne les valeurs du voisinage de taille N
function sousImage(image,ligne,colonne,N) {
    var voisinage = Array(N);

    //creation d'un tableau avec le nombre N des colonnes
    for (var i = 0; i < N; i++) {
        voisinage[i] = Array(N);

        for (var j = 0; j < N; j++) {
            var indexI = ligne-Math.floor(N/2)+i;
            var indexJ = colonne-Math.floor(N/2)+j;

            if (indexI < 0 || indexI > image.length-1
                || indexJ < 0 || indexJ > image[0].length-1) {
                voisinage[i][j] = {r: 0, g: 0, b: 0};
            } else {
                voisinage[i][j] = image[indexI][indexJ];
            }
        }
    }
    return voisinage;
}

function ponderationFlou(soustableau,N) {
    var ponderR = 0;
    var ponderG = 0;
    var ponderB = 0;
    var coeff = 1/(N*N);
    for (var i = 0; i < soustableau.length ; i++) {
        for (var j = 0; j < soustableau[0].length ; j++) {
            ponderR += soustableau[i][j].r;
            ponderG += soustableau[i][j].g;
            ponderB += soustableau[i][j].b;
        }
    }
    return {ponderR: ponderR * coeff, ponderG: ponderG * coeff, ponderB: ponderB * coeff};
    //return {ponderR: Math.round(ponderR), ponderG: Math.round(ponderG), ponderB: Math.round(ponderB)};
}

function detectionContours(imageOriginale) {
    var cloneNoirEtBlanc = noirEtBlanc(imageOriginale);
    var imageCopie = cloneImg(cloneNoirEtBlanc);

    var ponderHoriz = [[-1, 0, 1], [-2, 0, 2], [-1, 0, 1]];

    var ponderVertic = [[-1, -2, -1], [0, 0, 0], [1, 2, 1]];

    for (var i = 0; i < imageCopie.length; i++) {
        for (var j = 0; j < imageCopie[0].length; j++) {

            var voisinage = sousImage(cloneNoirEtBlanc, i, j, 3);
            var intensiteContours = Math.max(Math.abs(ponderation(voisinage,ponderHoriz)),
                Math.abs(ponderation(voisinage,ponderVertic)));

            if (intensiteContours > 255) {
                imageCopie[i][j].r = 255;
                imageCopie[i][j].g = 255;
                imageCopie[i][j].b = 255;
            } else {
                imageCopie[i][j].r = intensiteContours;
                imageCopie[i][j].g = intensiteContours;
                imageCopie[i][j].b = intensiteContours;
            }
        }
    }
    return imageCopie;
}

function ponderation(soustableau,ponder) {
    var moyennePonderee = 0;
    for (var i = 0; i < soustableau.length; i++) {
        for (var j = 0; j < soustableau[0].length; j++) {
            moyennePonderee += soustableau[i][j].r * ponder[i][j];
        }
    }
    return moyennePonderee;
}

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

    var tableau = [[{r: 11, g: 11, b: 11}, {r: 12, g: 12, b: 12}, {r: 13, g: 13, b: 13}, {r: 14, g: 14, b: 14}],
        [{r: 21, g: 21, b: 21}, {r: 22, g: 22, b: 22}, {r: 23, g: 23, b: 23}, {r: 24, g: 24, b: 24}],
        [{r: 31, g: 31, b: 31}, {r: 32, g: 32, b: 32}, {r: 33, g: 33, b: 33}, {r: 34, g: 34, b: 34}],
        [{r: 41, g: 41, b: 41}, {r: 42, g: 42, b: 42}, {r: 43, g: 43, b: 43}, {r: 44, g: 44, b: 44}]];
    var tableauVoisinage = [[{r: 22, g: 22, b: 22}, {r: 23, g: 23, b: 23}, {r: 24, g: 24, b: 24}],
        [{r: 32, g: 32, b: 32}, {r: 33, g: 33, b: 33}, {r: 34, g: 34, b: 34}],
        [{r: 42, g: 42, b: 42}, {r: 43, g: 43, b: 43}, {r: 44, g: 44, b: 44}]];
    var tableauVoisinage2 = [[{r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}],
        [ {r: 0, g: 0, b: 0}, {r: 11, g: 11, b: 11}, {r: 12, g: 12, b: 12}],
        [{r: 0, g: 0, b: 0}, {r: 21, g: 21, b: 21}, {r: 22, g: 22, b: 22}]];

    var tableauVoisinage3 =
        [[{r: 22, g: 23, b: 24}, {r: 52, g: 53, b: 54}, {r: 82, g: 83, b: 84}],
            [{r: 32, g: 33, b: 34}, {r: 62, g: 63, b: 64}, {r: 92, g: 93, b: 94}],
            [{r: 42, g: 43, b: 44}, {r: 72, g: 73, b: 74}, {r: 102, g: 103, b: 104}]];
    var tableauVoisinage4 =
        [[{r: 19, g: 19, b: 20}, {r: 38, g: 39, b: 39}, {r: 32, g: 32, b: 33}],
            [{r: 31, g: 32, b: 33}, {r: 62, g: 63, b: 64}, {r: 51, g: 52, b: 53}],
            [{r: 23, g: 24, b: 24}, {r: 45, g: 45, b: 46}, {r: 36, g: 37, b: 37}]];

    console.assert(areEqual(sousImage(tableau,2,2,3), tableauVoisinage));
    console.assert(areEqual(sousImage(tableau,0,0,3), tableauVoisinage2));
    console.assert(ponderationsEgales(ponderation(tableauVoisinage,3),{ponderR: 33, ponderG: 33, ponderB: 33}));
    console.assert(areEqual(flou(tableauVoisinage3, 3),tableauVoisinage4));

    var testContoursInput = [
        [{r:   0, g:  32, b:  29}, {r:   8, g:   5, b:  37}, {r:  16, g:  13, b:  45}, {r:  24, g:  21, b:  53}],
        [{r:  50, g:  16, b:  48}, {r:  58, g:  24, b:  56}, {r:   0, g:  32, b:  64}, {r:   8, g:  40, b:  72}],
        [{r:   4, g:   4, b:   4}, {r:  12, g:  12, b: 128}, {r:  20, g:   0, b:   0}, {r:   0, g: 255, b:   0}]];
    var testContoursOutput = [
        [{r:  85, g:  85, b:  85}, {r: 120, g: 120, b: 120}, {r: 124, g: 124, b: 124}, {r:  99, g:  99, b:  99}],
        [{r:  95, g:  95, b:  95}, {r:   8, g:   8, b:   8}, {r: 182, g: 182, b: 182}, {r: 255, g: 255, b: 255}],
        [{r:  85, g:  85, b:  85}, {r: 120, g: 120, b: 120}, {r: 255, g: 255, b: 255}, {r:  99, g:  99, b:  99}]];

    console.assert(areEqual(detectionContours(testContoursInput),testContoursOutput));
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

function ponderationsEgales(enr1, enr2) {
    if (enr1.ponderR != enr2.ponderR
        || enr1.ponderG != enr2.ponderG
        || enr1.ponderB != enr2.ponderB) {
        return false;
    }
    return true;
}

