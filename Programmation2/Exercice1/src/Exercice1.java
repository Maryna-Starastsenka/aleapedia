import java.util.Scanner;

public class Exercice1 {

    /**
     * Cette fonction prend un tableau de Strings en paramètre et retourne un
     * nouveau tableau contenant les mêmes éléments, avec une case disponible de
     * plus.
     */
    public static String[] agrandirTab(String[] tab) {
        String[] tabAgrandi = new String[tab.length + 1];

        for (int i = 0; i < tab.length; i++) {
            tabAgrandi[i] = tab[i];
        }

        return tabAgrandi;
    }

    /**
     * Cette fonction lit des mots sur la ligne de commande et les retourne dans
     * un tableau de Strings. Chaque "mot" est définit comme une ligne complète,
     * du début de la ligne entrée jusqu'au prochain \n.
     */
    public static String[] lireMots() {
        String[] tab = new String[0];
        Scanner scan = new Scanner(System.in);

        while (scan.hasNext()) {
            String motActuel = scan.nextLine();

            if (motActuel.equals("stop")) break;

            tab = agrandirTab(tab);
            tab[tab.length - 1] = motActuel;
        }

        return tab;
    }

    /**
     * Cette fonction prend en paramètre un tableau de mots et retourne un
     * nouveau tableau contenant ces mots triés en ordre croissant.
     */
    public static String[] trier(String[] mots) {
        // Tri par sélection
        String[] tabTrie = new String[mots.length];
        for (int i = 0; i < mots.length; i++) {
            tabTrie[i] = mots[i];
        }

        for (int i = 0; i < tabTrie.length - 1; i++) {
            int temp = i;
            for (int j = i + 1; j < tabTrie.length; j++) {
                if (tabTrie[j].compareTo(tabTrie[temp]) < 0) temp = j;
            }
            String min = tabTrie[temp];
            tabTrie[temp] = tabTrie[i];
            tabTrie[i] = min;
        }

        return tabTrie;
    }

    /**
     * Cette fonction prend en paramètre un tableau de mots trié et retourne un
     * nouveau tableau où chaque mot est unique (tous les doublons sont
     * retirés).
     */
    public static String[] retirerDoublons(String[] mots) {
        String[] motsUniques = new String[0];

        for (int i = 0; i < mots.length; i++) {
            boolean motEstUnique = true;
            for (int j = 0; j < motsUniques.length; j++) {
                if (mots[i].compareTo(motsUniques[j]) == 0)
                    motEstUnique = false;
            }
            if (motEstUnique) {
                motsUniques = agrandirTab(motsUniques);
                motsUniques[motsUniques.length-1] = mots[i];
            }
        }

        return motsUniques;
    }

    /**
     * Fonction principale du programme (utilisée pour tester le code).
     */
    public static void main(String[] args) {
        String[] mots = lireMots();
        mots = trier(mots);
        mots = retirerDoublons(mots);

        for (int i = 0; i < mots.length; i++) {
            System.out.println(mots[i]);
        }
    }
}
