// Auteurs: Maryna Sirotsina (matricule), PERSONNE 2 (matricule)


//fonction issue du fichier img.js
//prend une image sous forme de tableau et retourne sa copie
function cloneImg(data) {
    return data.map(function(line) {
        return line.map(function(pix) {
            return Object.assign({}, pix);
        });
    });
}

//prend un tableau original et retourne une nouvelle image en noir et blanc
function noirEtBlanc(imageOriginale) {
    var copie = cloneImg(imageOriginale);

    for (var i = 0; i < copie.length; i++) {
        for (var j = 0; j < copie[0].length; j++) {
            //remplacement des valeurs r, g, b de chaque pixel par la luminance
            var lum = luminance(copie[i][j].r, copie[i][j].g, copie[i][j].b);
            copie[i][j].r = lum;
            copie[i][j].g = lum;
            copie[i][j].b = lum;
        }
    }
    return copie;
}

//prend les valeurs r, g, b issues d'un pixel
//et retourne une moyenne pondérée (la luminance)
function luminance(r, g, b) {
    return Math.round(r * 0.2126 + g * 0.7152 + b * 0.0722);
}

//prend une image originale et une quantité
//et retourne une nouvelle image avec la clarté augmentée/réduite
function correctionClarte(imageOriginale, quantite) {
    var copie = cloneImg(imageOriginale);

    for (var i = 0; i < copie.length; i++) {
        for (var j = 0; j < copie[0].length; j++) {
            //remplacement des valeurs r, g, b de chaque pixel
            // par une valeur plus ou moins lumineuse
            copie[i][j].r = appliquerClarteCouleur(copie[i][j].r, quantite);
            copie[i][j].g = appliquerClarteCouleur(copie[i][j].g, quantite);
            copie[i][j].b = appliquerClarteCouleur(copie[i][j].b, quantite);
        }
    }
    return copie;
}

//prend les valeurs r, g, b de chaque pixel et une quantité
//et retourne une valeur sur laquelle on applique une clarté
function appliquerClarteCouleur(valeur, quantite) {
    return Math.pow(valeur / 255, quantite) * 255;
}

//prend des valeurs du tableau original et une taille du voisinage
//et retourne une nouvelle image floue
function flou(imageOriginale, taille) {
    var copie = cloneImg(imageOriginale);

    for (var i = 0; i < copie.length; i++) {
        for (var j = 0; j < copie[0].length; j++) {
            //récupère la sous-image du pixel courant puis calcule le flou
            var sousImg = sousImage(imageOriginale, i, j, taille);
            var pixelFlou = ponderationFlou(sousImg, taille);
            copie[i][j].r = pixelFlou.ponderationR;
            copie[i][j].g = pixelFlou.ponderationG;
            copie[i][j].b = pixelFlou.ponderationB;
        }
    }
    return copie;
}

//prend une image (tableau), l'indice de ligne et de colonne,
//la taille du voisinage (N) et retourne le tableau des pixels du voisinage
function sousImage(image, indicePixelLigne, indicePixelColonne, N) {
    //tableau du voisinage avec N lignes
    var voisinage = Array(N);

    //ajout de N colonnes dans le tableau du voisinage
    for (var i = 0; i < N; i++) {
        voisinage[i] = Array(N);

        for (var j = 0; j < N; j++) {
            //indice des lignes du voisinage
            var idxLigne = indicePixelLigne - Math.floor(N / 2) + i;
            //indice des colonnes du voisinage
            var idxColonne = indicePixelColonne -
                Math.floor(N / 2) + j;

            if (idxLigne < 0 || idxLigne > image.length - 1 ||
                idxColonne < 0 || idxColonne > image[0].length - 1) {
                //si on est hors de l'image, le pixel est noir
                voisinage[i][j] = { r: 0, g: 0, b: 0 };
            } else {
                //sinon on prend les valeurs du pixel original
                voisinage[i][j] = image[idxLigne][idxColonne];
            }
        }
    }
    return voisinage;
}

//prend le tableau du voisinage d'un pixel ainsi que sa taille
//et retourne un enregistrement avec les valeurs pondérées
//des canaux r, g, b
function ponderationFlou(sousImage, N) {
    //somme des valeurs r du voisinage
    var ponderationR = 0;
    //somme des valeurs g du voisinage
    var ponderationG = 0;
    //somme des valeurs b du voisinage
    var ponderationB = 0;

    //coefficient de la pondération
    var coeff = 1 / (N * N);

    for (var i = 0; i < sousImage.length; i++) {
        for (var j = 0; j < sousImage[0].length; j++) {
            ponderationR += sousImage[i][j].r;
            ponderationG += sousImage[i][j].g;
            ponderationB += sousImage[i][j].b;
        }
    }
    return {
        ponderationR: ponderationR * coeff,
        ponderationG: ponderationG * coeff,
        ponderationB: ponderationB * coeff
    };
}

//prend les valeurs d'une image originale et retourne
//une nouvelle image dont on voit les contours des motifs
function detectionContours(imageOriginale) {
    //image noir et blanc de référence à partir de l'image originale
    var referenceNoirEtBlanc = noirEtBlanc(imageOriginale);
    //copie de la référence noir et blanc
    var copieNoirEtBlanc = cloneImg(referenceNoirEtBlanc);

    //noyau (tableau) des valeurs de la pondération horizontale
    var ponderationHorizontale = [
        [-1, 0, 1],
        [-2, 0, 2],
        [-1, 0, 1]
    ];

    //noyau (tableau) des valeurs de la pondération verticale
    var ponderationVerticale = [
        [-1, -2, -1],
        [0, 0, 0],
        [1, 2, 1]
    ];

    for (var i = 0; i < copieNoirEtBlanc.length; i++) {
        for (var j = 0; j < copieNoirEtBlanc[0].length; j++) {
            //création d'un voisinage du pixel[ligne][colonne] de taille 3
            var voisinage = sousImage(referenceNoirEtBlanc, i, j, 3);
            //calcul et choix de l'intensité du contour max
            var intensiteContour = Math.max(
                Math.abs(ponderationContours(voisinage,
                    ponderationHorizontale)),
                Math.abs(ponderationContours(voisinage,
                    ponderationVerticale))
            );

            //on majore la valeur des canaux à 255
            if (intensiteContour > 255) {
                copieNoirEtBlanc[i][j].r = 255;
                copieNoirEtBlanc[i][j].g = 255;
                copieNoirEtBlanc[i][j].b = 255;
            } else {
                copieNoirEtBlanc[i][j].r = intensiteContour;
                copieNoirEtBlanc[i][j].g = intensiteContour;
                copieNoirEtBlanc[i][j].b = intensiteContour;
            }
        }
    }
    return copieNoirEtBlanc;
}

//prend les valeurs du voisinage et de la pondération horizontale/verticale
//et retourne une moyenne poindorée horizontale/verticale
function ponderationContours(sousImage, ponderation) {
    var moyennePonderee = 0;

    for (var i = 0; i < sousImage.length; i++) {
        for (var j = 0; j < sousImage[0].length; j++) {
            moyennePonderee += sousImage[i][j].r * ponderation[i][
                j];
        }
    }
    return moyennePonderee;
}

//fonction d'aide pour les tests
//compare les valeurs arrondies de 2 tableaux et retourne vrai
//s'ils elles sont toutes égales
function sontEgaux(tab1, tab2) {
    if (tab1.length == tab2.length && tab1.length >= 1 &&
        tab1[0].length == tab2[0].length) {
        for (var i = 0; i < tab1.length; i++) {
            for (var j = 0; j < tab1[0].length; j++) {
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

//fonction d'aide pour les tests
//compare les valeurs de 2 enregistrements de pondérations
//et retourne vrai s'ils sont égaux
function ponderationsEgales(enr1, enr2) {
    if (Math.round(enr1.ponderationR) != Math.round(enr2.ponderationR) ||
        Math.round(enr1.ponderationG) != Math.round(enr2.ponderationG) ||
        Math.round(enr1.ponderationB) != Math.round(enr2.ponderationB)) {
        return false;
    }
    return true;
}

//tests unitaires
function tests() {
    //pixel blanc central
    var tab1 =
        [[{ r: 0, g: 0, b: 0 }, { r: 0, g: 0, b: 0 }, { r: 0, g: 0, b: 0 }],
            [{ r: 0, g: 0, b: 0 }, { r: 255, g: 255, b: 255 },
                { r: 0, g: 0, b: 0 }],
            [{ r: 0, g: 0, b: 0 }, { r: 0, g: 0, b: 0 }, { r: 0, g: 0, b: 0 }]];

    //pixels noirs
    var tab2 =
        [[{ r: 0, g: 0, b: 0 }, { r: 0, g: 0, b: 0 }, { r: 0, g: 0, b: 0 }],
            [{ r: 0, g: 0, b: 0 }, { r: 0, g: 0, b: 0 }, { r: 0, g: 0, b: 0 }],
            [{ r: 0, g: 0, b: 0 }, { r: 0, g: 0, b: 0 }, { r: 0, g: 0, b: 0 }]];

    //pixels blancs
    var tab3 = [[{ r: 255, g: 255, b: 255 }, { r: 255, g: 255, b: 255 }],
        [{ r: 255, g: 255, b: 255 }, { r: 255, g: 255, b: 255 }]];

    //tests de "sontEgaux"
    console.assert(sontEgaux(tab1, tab1));
    console.assert(!sontEgaux(tab1, tab2));
    console.assert(!sontEgaux(tab2, tab3));

    var tab4 =
        [[{ r: 100, g: 50, b: 150 }, { r: 49, g: 0, b: 199 },
            { r: 255, g: 0, b: 80 }],
            [{ r: 0, g: 88, b: 234 }, { r: 200, g: 76, b: 132 },
                { r: 99, g: 3, b: 49 }],
            [{ r: 78, g: 200, b: 143 }, { r: 122, g: 209, b: 0 },
                { r: 11, g: 90, b: 32 }]];

    var tab5 =
        [[{ r: 68, g: 68, b: 68 }, { r: 25, g: 25, b: 25 },
            { r: 60, g: 60, b: 60 }],
            [{ r: 80, g: 80, b: 80 }, { r: 106, g: 106, b: 106 },
                {r: 27, g: 27, b: 27 }],
            [{ r: 170, g: 170, b: 170 }, { r: 175, g: 175, b: 175 },
                { r: 69, g: 69, b: 69 }]];

    //tests de "noirEtBlanc"
    console.assert(sontEgaux(noirEtBlanc(tab1), tab1));
    console.assert(sontEgaux(noirEtBlanc(tab4), tab5));
    console.assert(sontEgaux(noirEtBlanc(tab3), tab3));

    //tests de "luminance"
    console.assert(luminance(0, 0, 0) == 0);
    console.assert(luminance(255, 255, 255) == 255);
    console.assert(luminance(104, 212, 32) == 176);

    var tab6 =
        [[{ r: 63, g: 22, b: 115 }, { r: 21, g: 0, b: 176 },
            { r: 255, g: 0, b: 45 }],
            [{ r: 0, g: 52, b: 224 }, { r: 177, g: 41, b: 95 },
                { r: 62, g: 0, b: 21 }],
            [{ r: 43, g: 177, b: 107 }, { r: 84, g: 189, b: 0 },
                { r: 2, g: 53, b: 11 }]];

    //tests de "correctionClarte"
    console.assert(sontEgaux(correctionClarte(tab1, 1.5), tab1));
    console.assert(sontEgaux(correctionClarte(tab2, 1.5), tab2));
    console.assert(sontEgaux(correctionClarte(tab4, 1.5), tab6));

    //tests de "appliquerClarteCouleur"
    console.assert(appliquerClarteCouleur(0, 2) == 0);
    console.assert(appliquerClarteCouleur(255, 2) == 255);
    console.assert(appliquerClarteCouleur(51, 1) == 51);

    var tab7 = [[{ r: 11, g: 10, b: 110 }, { r: 12, g: 120, b: 122 },
        { r: 130, g: 153, b: 3 }, { r: 1, g: 14, b: 134 }],
        [{ r: 21, g: 221, b: 25 }, { r: 22, g: 26, b: 232 },
            { r: 2, g: 23, b: 213 }, { r: 24, g: 254, b: 4 }],
        [{ r: 31, g: 131, b: 3 }, { r: 32, g: 132, b: 137 },
            { r: 33, g: 133, b: 43 }, { r: 34, g: 134, b: 54 }],
        [{ r: 41, g: 141, b: 46 }, { r: 42, g: 142, b: 42 },
            { r: 43, g: 143, b: 40 }, { r: 44, g: 144, b: 4 }]];

    var tabVoisinage1 = [[{ r: 22, g: 26, b: 232 }, { r: 2, g: 23, b: 213 },
        { r: 24, g: 254, b: 4 }],
        [{ r: 32, g: 132, b: 137 }, { r: 33, g: 133, b: 43 },
            { r: 34, g: 134, b: 54 }],
        [{ r: 42, g: 142, b: 42 }, { r: 43, g: 143, b: 40 },
            { r: 44, g: 144, b: 4 }]];

    var tabVoisinage2 =
        [[{ r: 0, g: 0, b: 0 }, { r: 0, g: 0, b: 0 }, { r: 0, g: 0, b: 0 }],
            [{ r: 0, g: 0, b: 0 }, { r: 11, g: 10, b: 110 },
                { r: 12, g: 120, b: 122 }],
            [{ r: 0, g: 0, b: 0 }, { r: 21, g: 221, b: 25 },
                { r: 22, g: 26, b: 232 }]];

    var tabVoisinage3 = [[{ r: 21, g: 221, b: 25 }]];

    //tests de "sousImage"
    console.assert(sontEgaux(sousImage(tab7, 2, 2, 3), tabVoisinage1));
    console.assert(sontEgaux(sousImage(tab7, 0, 0, 3), tabVoisinage2));
    console.assert(sontEgaux(sousImage(tab7, 1, 0, 1), tabVoisinage3));

    var enreg1 = { ponderationR: 10, ponderationG: 10, ponderationB: 10 };
    var enreg2 = { ponderationR: 10, ponderationG: 0, ponderationB: 10 };

    //tests de "ponderationsEgales"
    console.assert(ponderationsEgales(enreg1, enreg1) == true);
    console.assert(ponderationsEgales(enreg1, enreg2) == false);

    //tests de "ponderationFlou"
    console.assert(ponderationsEgales(ponderationFlou(tab2,3),
        { ponderationR: 0, ponderationG: 0, ponderationB: 0 }));
    console.assert(ponderationsEgales(ponderationFlou(tab3, 2),
        {
            ponderationR: 255,
            ponderationG: 255,
            ponderationB: 255 }));
    console.assert(ponderationsEgales(ponderationFlou(tabVoisinage2,
        3), {
        ponderationR: 7.33,
        ponderationG: 41.89,
        ponderationB: 54.33
    }));

    var tab8 = [[{ r: 64, g: 64, b: 64 }, { r: 128, g: 128, b: 128 }],
        [{ r: 128, g: 128, b: 128 }, { r: 255, g: 255, b: 255 }]];

    var tab9 = [[{ r: 39, g: 24, b: 79 }, { r: 78, g: 24, b: 94 },
        { r: 67, g: 9, b: 51 }],
        [{ r: 61, g: 69, b: 95 }, { r: 102, g: 80, b: 113 },
            { r: 82, g: 42, b: 55 }],
        [{ r: 44, g: 64, b: 57 }, { r: 57, g: 74, b: 66 },
            { r: 48, g: 42, b: 24 }]];

    //tests de "flou"
    console.assert(sontEgaux(flou(tab2, 3), tab2));
    console.assert(sontEgaux(flou(tab3, 2), tab8));
    console.assert(sontEgaux(flou(tab4, 3), tab9));

    var ponderationHorizontale = [[-1, 0, 1], [-2, 0, 2], [-1, 0, 1]];
    var ponderationVerticale = [[-1, -2, -1], [0, 0, 0], [1, 2, 1]];
    var tab9 = [[{ r: 255, g: 255, b: 255 }, { r: 255, g: 255, b: 255 },
        { r: 255, g: 255, b: 255 }],
        [{ r: 255, g: 255, b: 255 }, { r: 255, g: 255, b: 255 },
            { r: 255, g: 255, b: 255 }],
        [{ r: 255, g: 255, b: 255 }, { r: 255, g: 255, b: 255 },
            { r: 255, g: 255, b: 255 }]];

    //tests de "ponderationContours"
    console.assert(ponderationContours(tab1, ponderationHorizontale) == 0);
    console.assert(ponderationContours(tab2, ponderationVerticale) == 0);
    console.assert(ponderationContours(tab9, ponderationVerticale) == 0);

    var tab11 = [
        [{ r: 180, g: 180, b: 180 }, { r: 216, g: 216, b: 216 },
            { r: 108, g: 108, b: 108}],
        [{ r: 255, g: 255, b: 255 }, { r: 255, g: 255, b: 255 },
            { r: 255, g: 255, b: 255 }],
        [{ r: 255, g: 255, b: 255 }, { r: 246, g: 246, b: 246 },
            { r: 255,  g: 255, b: 255 }]];

    //tests de "detectionContours"
    console.assert(sontEgaux(detectionContours(tab2), tab2));
    console.assert(sontEgaux(detectionContours(tab3), tab3));
    console.assert(sontEgaux(detectionContours(tab6), tab11));



}