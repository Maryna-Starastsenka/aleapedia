// Nom : Maryna Starastsenka, Matricule : 20166402

public class Vecteur {
    private double[] elements;

    //    Constructeur avec en paramètre un tableau d'éléments de type
    //    `double`
    public Vecteur(double[] elements) {
        this.elements = new double[elements.length];
        for (int i = 0; i < elements.length; i++) {
            this.elements[i] = elements[i];
        }
    }

    //    Permet de changer la valeur à l'index donné (commence à 0,
    //    comme dans un tableau)
    public void setElement(int index, double valeur) {
        elements[index] = valeur;
    }

    //    Retourne la valeur à l'index demandé
    public double getElement(int index) {
        return elements[index];
    }

    //    Calcule le produit scalaire avec un autre vecteur
    public double dotProduct(Vecteur v) {
        if (elements.length == v.elements.length) {
            double sum = 0.0;
            for (int i = 0; i < elements.length; i++) {
                sum += elements[i] * v.getElement(i);
            }
            return sum;
        } else {
            System.err.println("Erreur dans les dimensions des vecteurs");
            return Double.NaN;
        }
    }

    //    Affiche le contenu du vecteur entres accolades sur la console.
    //    Par exemple, le vecteur qui contient les nombres 1, 2 et 3 sera
    //    affiché avec :
    //    {1.0, 2.0, 3.0}
    public void afficher() {
        System.out.print("{");
        for (int i = 0; i < elements.length - 1; i++) {
            System.out.print(elements[i] + ", ");
        }
        System.out.print(elements[elements.length - 1] + "}\n");
    }
}


