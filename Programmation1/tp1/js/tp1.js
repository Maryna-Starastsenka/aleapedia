// Auteurs: PERSONNE 1 (matricule), PERSONNE 2 (matricule)


//fonction du fichier img.js
//prend un tableau initial et retourne sa copie
function cloneImg(data) {
    return data.map(function(line) {
        return line.map(function(pix) {
            return Object.assign({}, pix);
        });
    });
}

//prend un tableau original est retourne une nouvelle image en noir et blanc
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
    return Math.round(r*0.2126+g*0.7152+b*0.0722);
}

//prend une image originale et une quantité
//et retourne une nouvelle image avec la clarté augmentée/réduite
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

//prend des valeurs du tableau original et une taille du voisinage
//et retourne une nouvelle image floue
function flou(imageOriginale, taille) {
    var imageCopie = cloneImg(imageOriginale);

    for (var i = 0; i < imageCopie.length ; i++) {
        for (var j = 0; j < imageCopie[0].length ; j++) {
            //
            var sousImg = sousImage(imageOriginale,i,j,taille);
            var pixelFlou = ponderationFlou(sousImg,taille);
            imageCopie[i][j].r = pixelFlou.ponderR;
            imageCopie[i][j].g = pixelFlou.ponderG;
            imageCopie[i][j].b = pixelFlou.ponderB;
        }
    }
    return imageCopie;
}

//prend une copie d'une image originale, le noméro de ligne et de colonne,
//la taille de voisinage et retourne les valeurs du voisinage de taille N
function sousImage(image,ligne,colonne,N) {
    //tableau du voisinage avec N lignes
    var voisinage = Array(N);

    //ajout de N colonnes dans le tableau du voisinage
    for (var i = 0; i < N; i++) {
        voisinage[i] = Array(N);

        for (var j = 0; j < N; j++) {
            //index des lignes du voisinage
            var indexI = ligne-Math.floor(N/2)+i;
            //index des colonnes du voisinage
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

//prend le voisinage d'un pixel et la taille du voisinage,
//retourne un enregistrement avec les valeurs pondérées
//des canaux r, g, b
function ponderationFlou(soustableau,N) {
    //somme des valeures r du voisinage
    var ponderR = 0;

    //somme des valeures g du voisinage
    var ponderG = 0;

    //somme des valeures b du voisinage
    var ponderB = 0;

    //coefficien de la pondération
    var coeff = 1/(N*N);

    for (var i = 0; i < soustableau.length ; i++) {
        for (var j = 0; j < soustableau[0].length ; j++) {
            ponderR += soustableau[i][j].r;
            ponderG += soustableau[i][j].g;
            ponderB += soustableau[i][j].b;
        }
    }
    return {ponderR: ponderR * coeff, ponderG: ponderG * coeff, ponderB: ponderB * coeff};
}

//prend les valeurs d'une image originale et retourne
//une nouvelle image avec les contours à l'intérieur
function detectionContours(imageOriginale) {
    //création d'une copie de une'image en noir et blanc
    var cloneNoirEtBlanc = noirEtBlanc(imageOriginale);

    var imageCopie = cloneImg(cloneNoirEtBlanc);

    //tableau des valeurs de la pondération horizontale
    var ponderHoriz = [[-1, 0, 1], [-2, 0, 2], [-1, 0, 1]];

    //tableau des valeurs de la pondération vérticale
    var ponderVertic = [[-1, -2, -1], [0, 0, 0], [1, 2, 1]];

    for (var i = 0; i < imageCopie.length; i++) {
        for (var j = 0; j < imageCopie[0].length; j++) {
            //création d'un voisinage du pixel[i][j] de la taille 3
            var voisinage = sousImage(cloneNoirEtBlanc, i, j, 3);
            //calcul et choix de l'intensité du contour max
            var intensiteContour = Math.max(Math.abs(ponderationContours(voisinage,ponderHoriz)),
                Math.abs(ponderationContours(voisinage,ponderVertic)));

            if (intensiteContour > 255) {
                imageCopie[i][j].r = 255;
                imageCopie[i][j].g = 255;
                imageCopie[i][j].b = 255;
            } else {
                imageCopie[i][j].r = intensiteContour;
                imageCopie[i][j].g = intensiteContour;
                imageCopie[i][j].b = intensiteContour;
            }
        }
    }
    return imageCopie;
}

//prend les valeurs du voisinage et de la pondération horiz/vertic
//et retourne moyenne poindorée horiz/veric
function ponderationContours(soustableau,ponder) {
    var moyennePonderee = 0;
    for (var i = 0; i < soustableau.length; i++) {
        for (var j = 0; j < soustableau[0].length; j++) {
            moyennePonderee += soustableau[i][j].r * ponder[i][j];
        }
    }
    return moyennePonderee;
}

//compare des valeurs de 2 tableaux et retourne vrai ou faux
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
//OU METTRE ?? on l'utilise seulement pour les tests
//compare des valeurs de 2 enregistrements et retourne vrai ou faux
function ponderationsEgales(enr1, enr2) {
    if (enr1.ponderR != enr2.ponderR
        || enr1.ponderG != enr2.ponderG
        || enr1.ponderB != enr2.ponderB) {
        return false;
    }
    return true;
}

function tests() {
    var tab1 = [[{r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}],
        [{r: 0, g: 0, b: 0}, {r: 255, g: 255, b: 255}, {r: 0, g: 0, b: 0}],
        [{r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}]];

    var tab2 = [[{r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}],
        [{r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}],
        [{r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}]];

    var tab3 = [[{r: 255, g: 255, b: 255}, {r: 255, g: 255, b: 255}],
        [{r: 255, g: 255, b: 255}, {r: 255, g: 255, b: 255}]];

    console.assert(areEqual(tab1,tab1));
    console.assert(!areEqual(tab1,tab2));
    console.assert(!areEqual(tab2,tab3));

    var tab4 = [[{r: 100, g: 50, b: 150}, {r: 49, g: 0, b: 199}, {r: 255, g: 0, b: 80}],
        [{r: 0, g: 88, b: 234}, {r: 200, g: 76, b: 132}, {r: 99, g: 3, b: 49}],
        [{r: 78, g: 200, b: 143}, {r: 122, g: 209, b: 0}, {r: 11, g: 90, b: 32}]];

    var tab5 = [[{r: 68, g: 68, b: 68}, {r: 25, g: 25, b: 25}, {r: 60, g: 60, b: 60}],
        [{r: 80, g: 80, b: 80}, {r: 106, g: 106, b: 106}, {r: 27, g: 27, b: 27}],
        [{r: 170, g: 170, b: 170}, {r: 175, g: 175, b: 175}, {r: 69, g: 69, b: 69}]];

    console.assert(areEqual(noirEtBlanc(tab1),tab1));
    console.assert(areEqual(noirEtBlanc(tab4),tab5));
    console.assert(areEqual(noirEtBlanc(tab3),tab3));

    console.assert(luminance(0,0,0) == 0);
    console.assert(luminance(255,255,255) == 255);
    console.assert(luminance(104,212,32) == 176);

    var tab6 = [[{r: 63, g: 22, b: 115}, {r: 21, g: 0, b: 176}, {r: 255, g: 0, b: 45}],
        [{r: 0, g: 52, b: 224}, {r: 177, g: 41, b: 95}, {r: 62, g: 0, b: 21}],
        [{r: 43, g: 177, b: 107}, {r: 84, g: 189, b: 0}, {r: 2, g: 53, b: 11}]];

    console.assert(areEqual(correctionClarte(tab1,1.5),tab1));
    console.assert(areEqual(correctionClarte(tab2,1.5),tab2));
    console.assert(areEqual(correctionClarte(tab4,1.5),tab6));

    console.assert(corrClarteCouleur(0,2) == 0);
    console.assert(corrClarteCouleur(255,2) == 255);
    console.assert(corrClarteCouleur(51,1) == 51);

    var tab7 = [[{r: 11, g: 10, b: 110}, {r: 12, g: 120, b: 122}, {r: 130, g: 153, b: 3}, {r: 1, g: 14, b: 134}],
        [{r: 21, g: 221, b: 25}, {r: 22, g: 26, b: 232}, {r: 2, g: 23, b: 213}, {r: 24, g: 254, b: 4}],
        [{r: 31, g: 131, b: 3}, {r: 32, g: 132, b: 137}, {r: 33, g: 133, b: 43}, {r: 34, g: 134, b: 54}],
        [{r: 41, g: 141, b: 46}, {r: 42, g: 142, b: 42}, {r: 43, g: 143, b: 40}, {r: 44, g: 144, b: 4}]];

    var tabVoisinage1 = [[{r: 22, g: 26, b: 232}, {r: 2, g: 23, b: 213}, {r: 24, g: 254, b: 4}],
        [{r: 32, g: 132, b: 137}, {r: 33, g: 133, b: 43}, {r: 34, g: 134, b: 54}],
        [{r: 42, g: 142, b: 42}, {r: 43, g: 143, b: 40}, {r: 44, g: 144, b: 4}]];

    var tabVoisinage2 = [[{r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}],
        [{r: 0, g: 0, b: 0}, {r: 11, g: 10, b: 110}, {r: 12, g: 120, b: 122}],
        [{r: 0, g: 0, b: 0}, {r: 21, g: 221, b: 25}, {r: 22, g: 26, b: 232}]];

    var tabVoisinage3 = [{r: 21, g: 221, b: 25}];

    console.assert(areEqual(sousImage(tab7,2,2,3), tabVoisinage1));
    console.assert(areEqual(sousImage(tab7,0,0,3), tabVoisinage2));
    console.assert(areEqual(sousImage(tab7,1,0,1), tabVoisinage3));

    var enreg1 = {ponderR: 10, ponderG: 10, ponderB: 10};
    var enreg2 = {ponderR: 10, ponderG: 0, ponderB: 10};

    console.assert(ponderationsEgales(enreg1,enreg1) == true);
    console.assert(ponderationsEgales(enreg1,enreg2) == false);

    console.assert(ponderationsEgales(ponderationFlou(tab2,3),{ponderR: 0, ponderG: 0, ponderB: 0}));
    console.assert(ponderationsEgales(ponderationFlou(tab3,2),{ponderR: 255, ponderG: 255, ponderB: 255}));
    console.assert(ponderationsEgales(ponderationFlou(tabVoisinage2,3),{ponderR: 66/9, ponderG: 377/9, ponderB: 489/9}));

    var tab8 = [[{r: 255/4, g: 255/4, b: 255/4}, {r: 510/4, g: 510/4, b: 510/4}],
        [{r: 125, g: 125, b: 125}, {r: 255, g: 255, b: 255}]];

    console.assert(areEqual(flou(tab2, 3),tab2));
    console.assert(areEqual(flou(tab3, 2),tab8));
    //console.assert(areEqual(flou(, ),));

    var ponderHoriz = [[-1, 0, 1], [-2, 0, 2], [-1, 0, 1]];
    var ponderVertic = [[-1, -2, -1], [0, 0, 0], [1, 2, 1]];
    var tab9 = [[{r: 255, g: 255, b: 255}, {r: 255, g: 255, b: 255}, {r: 255, g: 255, b: 255}],
        [{r: 255, g: 255, b: 255}, {r: 255, g: 255, b: 255}, {r: 255, g: 255, b: 255}],
        [{r: 255, g: 255, b: 255}, {r: 255, g: 255, b: 255}, {r: 255, g: 255, b: 255}]];
    console.assert(ponderationContours(tab1,ponderHoriz) == 0);
    console.assert(ponderationContours(tab2,ponderVertic) == 0);
    console.assert(ponderationContours(tab9,ponderVertic) == 0);

    //console.assert(areEqual(detectionContours(),));
    //console.assert(areEqual(detectionContours(),));
}



