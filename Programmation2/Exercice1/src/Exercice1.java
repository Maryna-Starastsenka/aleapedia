import java.util.Arrays;
import java.util.Scanner;

public class Exercice1 {

    /**
     * Cette fonction prend un tableau de Strings en paramètre et retourne un
     * nouveau tableau contenant les mêmes éléments, avec une case disponible de
     * plus.
     */
    public static String[] agrandirTab(String[] tab) {
        String[] newTab = new String[tab.length + 1];
        for (int i = 0; i < tab.length; i++) newTab[i] = tab[i];
        return newTab;
    }

    /**
     * Cette fonction lit des mots sur la ligne de commande et les retourne dans
     * un tableau de Strings. Chaque "mot" est définit comme une ligne complète,
     * du début de la ligne entrée jusqu'au prochain \n.
     */
    public static String[] lireMots() {
        String[] words = new String[0];
        Scanner in = new Scanner(System.in);
        String s = "";
        while (in.hasNext()) {
            String word = in.next();

            if (word.equals("exit")) break;

            words = agrandirTab(words);
            words[words.length - 1] = word;
        }

        return words;
    }

    /**
     * Cette fonction prend en paramètre un tableau de mots et retourne un
     * nouveau tableau contenant ces mots triés en ordre croissant.
     */
    public static String[] trier(String[] mots) {
        String[] motsTries = new String[mots.length];
        for (int i = 0; i < motsTries.length; i++) motsTries[i] = mots[i];

        for (int i = 0; i < motsTries.length; i++) {
            for (int j = 0; j < motsTries.length -1; j++) {
                if (motsTries[j].compareTo(motsTries[j+1]) > 0) {
                    var temp = motsTries[j];
                    motsTries[j] = motsTries[j+1];
                    motsTries[j+1] = temp;
                }
            }
        }
        //Arrays.sort(motsTries);
        return motsTries;
    }

    public static boolean containsElement(String[] mots, String mot) {
        for (int i = 0; i < mots.length; i++) {
            if (mots[i].compareTo(mot) == 0) return true;
        }
        return false;
    }

    /**
     * Cette fonction prend en paramètre un tableau de mots trié et retourne un
     * nouveau tableau où chaque mot est unique (tous les doublons sont
     * retirés).
     */
    public static String[] retirerDoublons(String[] mots) {
        String[] motsSansDoublon = new String[0];

        for (int i = 0; i < mots.length; i++) {
            if (!containsElement(motsSansDoublon, mots[i])) {
                motsSansDoublon = agrandirTab(motsSansDoublon);
                motsSansDoublon[motsSansDoublon.length - 1] = mots[i];
            }
        }

        return motsSansDoublon;
    }

    /**
     * Fonction principale du programme (utilisée pour tester le code).
     */
    public static void main(String[] args) {
        var mots = lireMots();
        mots = trier(mots);
        mots = retirerDoublons(mots);

        for (int i = 0; i < mots.length; i++) {
            System.out.println(mots[i]);
        }
    }
}
