public class Matrice {
//    Constructeur pour une matrice de taille Nlignes x Mcolonnes
//    remplie de zéros
    public Matrice(int lignes, int cols) {

    }

//    Additionne la valeur n dans toutes les cellules de la matrice
//    (modifie la matrice actuelle)
    public void additionnerScalaire(double n) {

    }

//    Multiplie toutes les cellules par un scalaire
//    (modifie la matrice actuelle)
    public void multiplierScalaire(double n) {

    }

//    Fait le produit matriciel entre deux matrices
//    (crée une *nouvelle matrice* de la bonne taille, ne modifie
//    pas les deux matrices originales)
    public Matrice dotProduct(Matrice m) {

    }

//    Accesseur pour la cellule à une ligne/colonne donnée
    public double getCell(int ligne, int col) {

    }

//    Mutateur pour la cellule à une ligne/colonne donnée
    public void setCell(int ligne, int col, double valeur) {

    }


//    Retourne un nouveau vecteur contenant la Nième ligne
    public Vecteur getLine(int ligne) {

    }


//    Retourne un nouveau vecteur contenant la Nième colonne
    public Vecteur getCol(int col) {

    }


//    Affiche la matrice sur la console, chaque ligne entre crochets [ ]
//
//    Par exemple, la matrice qui contient les nombres 1 et 2 sur la
//    première ligne et 13 et 14 sur la deuxième ligne sera affichée
//    avec :
//
//            [ 1.0 2.0 ]
//            [ 13.0 14.0 ]
    public void afficher() {

    }

//    Retourne une version transposée de la matrice
//    (sans modifier la matrice actuelle)
    public Matrice transpose() {

    }


//    Retourne une instance de la matrice identité N x N
    public static Matrice identite(int n) {

    }


}
