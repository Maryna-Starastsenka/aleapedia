public class Correcteur {

    public static void main(String[] args) {

        String nomDuFichierACorriger = args[0];
        String nomDuDictionnaire = args[1];

        var correcteur = new CorrecteurOrthographique(nomDuFichierACorriger, nomDuDictionnaire);

        correcteur.construireStructuresDonnees();
        correcteur.corrigerTexte();
        correcteur.imprimerTexteCorrigeSurConsole();

    }
}
