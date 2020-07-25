// Nom : Maryna Starastsenka, Matricule : 20166402

public class Exercice2 {

    public static void main(String[] args) {

        double[] elements = new double[]{1, 2, 3};
        Vecteur v1 = new Vecteur(elements);
        Vecteur v2 = new Vecteur(elements);
        v2.setElement(0, 10);

        System.out.println("--- Vecteurs ---");
        System.out.println("Vecteur 1 :");
        v1.afficher();
        System.out.println("Vecteur 2 :");
        v2.afficher();

        System.out.println("Produit scalaire de v1 et v2 : "
                + v1.dotProduct(v2));

        // test de mauvaises dimensions
        Vecteur v3 = new Vecteur(new double[]{1, 2, 3, 4});
        v1.dotProduct(v3);

        System.out.println("--- Matrices ---");
        Matrice m1 = Matrice.identite(2);
        m1.multiplierScalaire(3);
        m1.setCell(0, 1, 5);
        m1.setCell(1, 0, -2);
        System.out.println("Matrice 1 : Id(2) + 3 avec modif de cellules");
        m1.afficher();

        Matrice m2 = new Matrice(2, 1);
        m2.additionnerScalaire(10);
        System.out.println("Matrice 2 : m2 + 10");
        m2.afficher();

        Matrice m3 = m1.dotProduct(m2);
        System.out.println("Matrice 3 : produit matriciel de m1 et m2");
        m3.afficher();

        Matrice m4 = m1.transpose();
        System.out.println("Matrice 4 : transposé de m1");
        m4.afficher();

        Matrice m5 = m1.dotProduct(m4);
        System.out.println("Matrice 5 : produit matriciel de m1 et m4");
        m5.afficher();

        Matrice m6 = m5.dotProduct(Matrice.identite(2));
        System.out.println("Matrice 6 : produit matriciel de m5 et Id(2)");
        m6.afficher();

        // test de mauvaises dimensions
        m5.dotProduct(Matrice.identite(5));
    }
}