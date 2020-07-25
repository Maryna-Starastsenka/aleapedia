/**
 * @author Prénom Nom - Matricule
 * @author Prénom Nom - Matricule
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

class Node implements Comparable<Node> {
    // TODO Attributs

    // Leaf Node
    Node(char symbol, int frequency) {
        // TODO À compléter
    }

    // Internal Node
    Node(Node left, Node right) {
        // TODO À compléter
    }

    boolean isLeaf() {
        return true; // TODO À compléter
    }

    @Override
    public int compareTo(Node node) {
        return 0; // TODO À compléter : comparaison selon la fréquence d'occurrence
    }

    @Override
    public String toString() {
        return null; // TODO À compléter : représentation du nœud en format DOT
    }
}

class HuffmanCode {
    /**
     * @param text Texte à analyser
     * @return Fréquence de chaque caractère ASCII sur 8 bits
     */
    private static int[] getCharacterFrequencies(String text) {
        return null; // TODO À compléter
    }

    /**
     * @param charFreq Fréquence de caractères
     * @return Nœud racine de l'arbre
     */
    private static Node getHuffmanTree(int[] charFreq) {
        return null; // TODO À compléter
    }

    /**
     * @param node Nœud actuel
     * @param code Code Huffman
     */
    private static void printTable(Node node, String code) {
        // TODO À compléter
    }

    /**
     * @param node Nœud de départ
     */
    private static void printGraph(Node node) {
        // TODO À compléter
    }

    // Ne pas modifier
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Node root = getHuffmanTree(getCharacterFrequencies(reader.readLine()));

        // Table
        if (args.length == 0 || Arrays.asList(args).contains("table")) {
            System.out.println("Char Freq Code\n---- ---- ----");
            printTable(root, "");
        }

        // Graphe
        if (args.length == 0 || Arrays.asList(args).contains("graph")) {
            printGraph(root);
        }
    }
}
