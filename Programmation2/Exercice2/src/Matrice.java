// Nom : Maryna Starastsenka, Matricule : 20166402

public class Matrice {
    private double[][] matrice;
    private int nlignes, mcolonnes;

//    Constructeur pour une matrice de taille Nlignes x Mcolonnes
//    remplie de zéros
    public Matrice(int lignes, int cols) {
        nlignes = lignes;
        mcolonnes = cols;
        matrice = new double[lignes][cols];
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < cols; j++) {
                matrice[i][j] = 0;
            }
        }
    }

//    Additionne la valeur n dans toutes les cellules de la matrice
//    (modifie la matrice actuelle)
    public void additionnerScalaire(double n) {
        for (int i = 0; i < nlignes; i++) {
            for (int j = 0; j < mcolonnes; j++) {
                matrice[i][j] += n;
            }
        }
    }

//    Multiplie toutes les cellules par un scalaire
//    (modifie la matrice actuelle)
    public void multiplierScalaire(double n) {
        for (int i = 0; i < nlignes; i++) {
            for (int j = 0; j < mcolonnes; j++) {
                matrice[i][j] *= n;
            }
        }
    }

//    Fait le produit matriciel entre deux matrices
//    (crée une *nouvelle matrice* de la bonne taille, ne modifie
//    pas les deux matrices originales)
    public Matrice dotProduct(Matrice m) {
        if (mcolonnes == m.nlignes) {
            Matrice matResultat = new Matrice(nlignes, m.mcolonnes);
            for (int i = 0; i < nlignes; i++) {
                for (int j = 0; j < m.mcolonnes; j++) {
                    Vecteur v1 = getLine(i);
                    Vecteur v2 = m.getCol(j);
                    matResultat.setCell(i, j, v1.dotProduct(v2));
                }
            }
            return matResultat;
        } else {
            System.err.println("Erreur dans les dimensions des matrices");
            return null;
        }
    }

//    Accesseur pour la cellule à une ligne/colonne donnée
    public double getCell(int ligne, int col) {
        return matrice[ligne][col];
    }

//    Mutateur pour la cellule à une ligne/colonne donnée
    public void setCell(int ligne, int col, double valeur) {
        matrice[ligne][col] = valeur;
    }

//    Retourne un nouveau vecteur contenant la Nième ligne
    public Vecteur getLine(int ligne) {
        return new Vecteur(matrice[ligne]);
    }

//    Retourne un nouveau vecteur contenant la Nième colonne
    public Vecteur getCol(int col) {
        double[] tab = new double[nlignes];
        for (int i = 0; i < nlignes; i++) {
            tab[i] = matrice[i][col];
        }
        return new Vecteur(tab);
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
        for (int i = 0; i < nlignes; i++) {
            System.out.print("[ ");
            for (int j = 0; j < mcolonnes; j++) {
                System.out.print(getCell(i, j) + " ");
            }
            System.out.print("]\n");
        }
    }

//    Retourne une version transposée de la matrice
//    (sans modifier la matrice actuelle)
    public Matrice transpose() {
        Matrice matriceTransposee = new Matrice(mcolonnes, nlignes);
        for (int i = 0; i < nlignes; i++) {
            for (int j = 0; j < mcolonnes; j++) {
                matriceTransposee.setCell(j, i, matrice[i][j]);
            }
        }
        return matriceTransposee;
    }

//    Retourne une instance de la matrice identité N x N
    public static Matrice identite(int n) {
        Matrice matIdentite = new Matrice(n, n);
        for (int i = 0; i < n; i++) {
            matIdentite.setCell(i, i, 1);
        }
        return matIdentite;
    }
}
