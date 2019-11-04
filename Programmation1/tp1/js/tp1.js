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

    for (var ligne = 0; ligne < copie.length; ligne++) {
        for (var colonne = 0; colonne < copie[0].length; colonne++) {
            //remplacement des valeurs r, g, b de chaque pixel par la luminance
            var lum = luminance(copie[ligne][colonne].r, copie[ligne][colonne].g, copie[ligne][colonne].b);
            copie[ligne][colonne].r = lum;
            copie[ligne][colonne].g = lum;
            copie[ligne][colonne].b = lum;
        }
    }
    return copie;
}

//prend les valeurs r, g, b issues d'un pixel
//et retourne une moyenne pondérée (la luminance)
function luminance(r,g,b) {
    return Math.round(r * 0.2126 + g * 0.7152 + b * 0.0722);
}

//prend une image originale et une quantité
//et retourne une nouvelle image avec la clarté augmentée/réduite
function correctionClarte(imageOriginale, quantite) {
    var copie = cloneImg(imageOriginale);

    for (var ligne = 0; ligne < copie.length; ligne++) {
        for (var colonne = 0; colonne < copie[0].length; colonne++) {
            //remplacement des valeurs r, g, b de chaque pixel
            // par une valeur plus ou moins lumineuse
            copie[ligne][colonne].r = appliquerClarteCouleur(copie[ligne][colonne].r, quantite);
            copie[ligne][colonne].g = appliquerClarteCouleur(copie[ligne][colonne].g, quantite);
            copie[ligne][colonne].b = appliquerClarteCouleur(copie[ligne][colonne].b, quantite);
        }
    }
    return copie;
}

//prend les valeurs r, g, b de chaque pixel et une quantité
//et retourne une valeur sur laquelle on applique une clarté
function appliquerClarteCouleur(valeur, quantite) {
    return Math.pow(valeur/255, quantite) * 255;
}

//prend des valeurs du tableau original et une taille du voisinage
//et retourne une nouvelle image floue
function flou(imageOriginale, taille) {
    var copie = cloneImg(imageOriginale);

    for (var ligne = 0; ligne < copie.length; ligne++) {
        for (var colonne = 0; colonne < copie[0].length; colonne++) {
            //récupère la sous-image du pixel courant puis calcule le flou
            var sousImg = sousImage(imageOriginale, ligne, colonne, taille);
            var pixelFlou = ponderationFlou(sousImg, taille);
            copie[ligne][colonne].r = pixelFlou.ponderationR;
            copie[ligne][colonne].g = pixelFlou.ponderationG;
            copie[ligne][colonne].b = pixelFlou.ponderationB;
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
    for (var ligne = 0; ligne < N; ligne++) {
        voisinage[ligne] = Array(N);

        for (var colonne = 0; colonne < N; colonne++) {
            //indice des lignes du voisinage
            var indiceLigne = indicePixelLigne - Math.floor(N/2) + ligne;
            //indice des colonnes du voisinage
            var indiceColonne = indicePixelColonne - Math.floor(N/2) + colonne;

            if (indiceLigne < 0 || indiceLigne > image.length-1 ||
                indiceColonne < 0 || indiceColonne > image[0].length-1) {
                //si on est hors de l'image, le pixel est noir
                voisinage[ligne][colonne] = {r: 0, g: 0, b: 0};
            } else {
                //sinon on prend les valeurs du pixel original
                voisinage[ligne][colonne] = image[indiceLigne][indiceColonne];
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
    var coeff = 1/(N*N);

    for (var ligne = 0; ligne < sousImage.length; ligne++) {
        for (var colonne = 0; colonne < sousImage[0].length; colonne++) {
            ponderationR += sousImage[ligne][colonne].r;
            ponderationG += sousImage[ligne][colonne].g;
            ponderationB += sousImage[ligne][colonne].b;
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

    for (var ligne = 0; ligne < copieNoirEtBlanc.length; ligne++) {
        for (var colonne = 0; colonne < copieNoirEtBlanc[0].length; colonne++) {
            //création d'un voisinage du pixel[ligne][colonne] de taille 3
            var voisinage = sousImage(referenceNoirEtBlanc, ligne, colonne, 3);
            //calcul et choix de l'intensité du contour max
            var intensiteContour = Math.max(
                Math.abs(ponderationContours(voisinage, ponderationHorizontale)),
                Math.abs(ponderationContours(voisinage, ponderationVerticale))
            );

            //on majore la valeur des canaux à 255
            if (intensiteContour > 255) {
                copieNoirEtBlanc[ligne][colonne].r = 255;
                copieNoirEtBlanc[ligne][colonne].g = 255;
                copieNoirEtBlanc[ligne][colonne].b = 255;
            } else {
                copieNoirEtBlanc[ligne][colonne].r = intensiteContour;
                copieNoirEtBlanc[ligne][colonne].g = intensiteContour;
                copieNoirEtBlanc[ligne][colonne].b = intensiteContour;
            }
        }
    }
    return copieNoirEtBlanc;
}

//prend les valeurs du voisinage et de la pondération horizontale/verticale
//et retourne une moyenne poindorée horizontale/verticale
function ponderationContours(sousImage, ponderation) {
    var moyennePonderee = 0;

    for (var ligne = 0; ligne < sousImage.length; ligne++) {
        for (var colonne = 0; colonne < sousImage[0].length; colonne++) {
            moyennePonderee += sousImage[ligne][colonne].r * ponderation[ligne][colonne];
        }
    }
    return moyennePonderee;
}

//fonction d'aide pour les tests
//compare les valeurs arrondies de 2 tableaux et retourne vrai
//s'ils elles sont toutes égales
function sontEgaux(tab1, tab2) {
    if (tab1.length == tab2.length && tab1.length >= 1
        && tab1[0].length == tab2[0].length) {
        for (var ligne = 0; ligne < tab1.length; ligne++) {
            for (var colonne = 0; colonne < tab1[0].length; colonne++) {
                if (Math.round(tab1[ligne][colonne].r) != Math.round(tab2[ligne][colonne].r) ||
                    Math.round(tab1[ligne][colonne].g) != Math.round(tab2[ligne][colonne].g) ||
                    Math.round(tab1[ligne][colonne].b) != Math.round(tab2[ligne][colonne].b)) {
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
    if (Math.round(enr1.ponderationR) != Math.round(enr2.ponderationR)
     || Math.round(enr1.ponderationG) != Math.round(enr2.ponderationG)
     || Math.round(enr1.ponderationB) != Math.round(enr2.ponderationB)) {
        return false;
    }
    return true;
}

//tests unitaires
function tests() {
    //pixel blanc central
    var tab1 = [[{r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}],
        [{r: 0, g: 0, b: 0}, {r: 255, g: 255, b: 255}, {r: 0, g: 0, b: 0}],
        [{r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}]];

    //pixels noirs
    var tab2 = [[{r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}],
        [{r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}],
        [{r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}, {r: 0, g: 0, b: 0}]];

    //pixels blancs
    var tab3 = [[{r: 255, g: 255, b: 255}, {r: 255, g: 255, b: 255}],
        [{r: 255, g: 255, b: 255}, {r: 255, g: 255, b: 255}]];

    //tests de "sontEgaux"
    console.assert(sontEgaux(tab1,tab1));
    console.assert(!sontEgaux(tab1,tab2));
    console.assert(!sontEgaux(tab2,tab3));

    var tab4 = [[{r: 100, g: 50, b: 150}, {r: 49, g: 0, b: 199}, {r: 255, g: 0, b: 80}],
        [{r: 0, g: 88, b: 234}, {r: 200, g: 76, b: 132}, {r: 99, g: 3, b: 49}],
        [{r: 78, g: 200, b: 143}, {r: 122, g: 209, b: 0}, {r: 11, g: 90, b: 32}]];

    var tab5 = [[{r: 68, g: 68, b: 68}, {r: 25, g: 25, b: 25}, {r: 60, g: 60, b: 60}],
        [{r: 80, g: 80, b: 80}, {r: 106, g: 106, b: 106}, {r: 27, g: 27, b: 27}],
        [{r: 170, g: 170, b: 170}, {r: 175, g: 175, b: 175}, {r: 69, g: 69, b: 69}]];

    //tests de "noirEtBlanc"
    console.assert(sontEgaux(noirEtBlanc(tab1),tab1));
    console.assert(sontEgaux(noirEtBlanc(tab4),tab5));
    console.assert(sontEgaux(noirEtBlanc(tab3),tab3));

    //tests de "luminance"
    console.assert(luminance(0,0,0) == 0);
    console.assert(luminance(255,255,255) == 255);
    console.assert(luminance(104,212,32) == 176);

    var tab6 = [[{r: 63, g: 22, b: 115}, {r: 21, g: 0, b: 176}, {r: 255, g: 0, b: 45}],
        [{r: 0, g: 52, b: 224}, {r: 177, g: 41, b: 95}, {r: 62, g: 0, b: 21}],
        [{r: 43, g: 177, b: 107}, {r: 84, g: 189, b: 0}, {r: 2, g: 53, b: 11}]];

    //tests de "correctionClarte"
    console.assert(sontEgaux(correctionClarte(tab1,1.5),tab1));
    console.assert(sontEgaux(correctionClarte(tab2,1.5),tab2));
    console.assert(sontEgaux(correctionClarte(tab4,1.5),tab6));

    //tests de "appliquerClarteCouleur"
    console.assert(appliquerClarteCouleur(0,2) == 0);
    console.assert(appliquerClarteCouleur(255,2) == 255);
    console.assert(appliquerClarteCouleur(51,1) == 51);

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

    var tabVoisinage3 = [[{r: 21, g: 221, b: 25}]];

    //tests de "sousImage"
    console.assert(sontEgaux(sousImage(tab7,2,2,3), tabVoisinage1));
    console.assert(sontEgaux(sousImage(tab7,0,0,3), tabVoisinage2));
    console.assert(sontEgaux(sousImage(tab7,1,0,1), tabVoisinage3));

    var enreg1 = {ponderationR: 10, ponderationG: 10, ponderationB: 10};
    var enreg2 = {ponderationR: 10, ponderationG: 0, ponderationB: 10};

    //tests de "ponderationsEgales"
    console.assert(ponderationsEgales(enreg1,enreg1) == true);
    console.assert(ponderationsEgales(enreg1,enreg2) == false);

    //tests de "ponderationFlou"
    console.assert(ponderationsEgales(ponderationFlou(tab2,3),{ponderationR: 0, ponderationG: 0, ponderationB: 0}));
    console.assert(ponderationsEgales(ponderationFlou(tab3,2),{ponderationR: 255, ponderationG: 255, ponderationB: 255}));
    console.assert(ponderationsEgales(ponderationFlou(tabVoisinage2,3), {ponderationR: 7.33, ponderationG: 41.89, ponderationB: 54.33}));

    var tab8 = [[{r: 64, g: 64, b: 64}, {r: 128, g: 128, b: 128}],
        [{r: 128, g: 128, b: 128}, {r: 255, g: 255, b: 255}]];

    //tests de "flou"
    console.assert(sontEgaux(flou(tab2, 3), tab2));
    console.assert(sontEgaux(flou(tab3, 2), tab8));
    //console.assert(sontEgaux(flou(, ),));

    var ponderationHorizontale = [[-1, 0, 1], [-2, 0, 2], [-1, 0, 1]];
    var ponderationVerticale = [[-1, -2, -1], [0, 0, 0], [1, 2, 1]];
    var tab9 = [[{r: 255, g: 255, b: 255}, {r: 255, g: 255, b: 255}, {r: 255, g: 255, b: 255}],
        [{r: 255, g: 255, b: 255}, {r: 255, g: 255, b: 255}, {r: 255, g: 255, b: 255}],
        [{r: 255, g: 255, b: 255}, {r: 255, g: 255, b: 255}, {r: 255, g: 255, b: 255}]];

    //tests de "ponderationContours"
    console.assert(ponderationContours(tab1, ponderationHorizontale) == 0);
    console.assert(ponderationContours(tab2, ponderationVerticale) == 0);
    console.assert(ponderationContours(tab9, ponderationVerticale) == 0);

    //console.assert(sontEgaux(detectionContours(),));
    //console.assert(sontEgaux(detectionContours(),));
}



