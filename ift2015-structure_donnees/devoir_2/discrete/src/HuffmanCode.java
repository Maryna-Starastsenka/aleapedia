/**
 * @author Prénom Nom - Matricule
 * @author Prénom Nom - Matricule
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.LinkedList;

class Node implements Comparable<Node> {
    // TODO Attributs
    private char symbol;
    private int frequency;

    protected Node left;
    protected Node right;

    // Leaf Node
    Node(char symbol, int frequency) {
        // TODO À compléter
        this.symbol = symbol;
        this.frequency = frequency;
    }

    // Internal Node
    Node(Node left, Node right) {
        // TODO À compléter
        this.left = left;
        this.right = right;
        this.frequency = left.frequency + right.frequency;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getFrequency() {
        return frequency;
    }

    boolean isLeaf() {
        return left == null && right == null; // TODO À compléter
    }

    @Override
    public int compareTo(Node node) {
        return frequency - node.frequency; // TODO À compléter : comparaison selon la fréquence d'occurrence
    }

    @Override
    public String toString() {
        StringBuilder dotString = new StringBuilder();

        if (isLeaf()) {
            dotString.append("    " + hashCode() + " [label=\"{{'" + symbol + "'|" + frequency + "}}\", shape=record]");
        } else {
            dotString.append("    " + hashCode() + " [label=" + frequency + ", shape=rectangle, width=.5]");
            if (left != null) {
                dotString.append("\n    " + hashCode() + " -- " + left.hashCode() + " [label=0]");
            }
            if (right != null) {
                dotString.append("\n    " + hashCode() + " -- " + right.hashCode() + " [label=1]");
            }
        }

        return dotString.toString(); // TODO À compléter : représentation du nœud en format DOT
    }
}

class HuffmanCode {
    /**
     * @param text Texte à analyser
     * @return Fréquence de chaque caractère ASCII sur 8 bits
     */
    private static int[] getCharacterFrequencies(String text) {
        int[] frequencies = new int[128]; // remplit de 0
        for (char c : text.toCharArray()) {
            frequencies[(int) c]++; // prend le caractère, le converti en int (table ASCII), s'en sert comme d'index dans le tableau et augmente la fréquence de 1
        }
        return frequencies; // TODO À compléter
    }

    /**
     * @param charFreq Fréquence de caractères
     * @return Nœud racine de l'arbre
     */
    private static Node getHuffmanTree(int[] charFreq) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (int i = 0; i < charFreq.length; i++) {
            if (charFreq[i] != 0) {
                pq.add(new Node((char) i, charFreq[i]));
            }
        }

        while (pq.size() > 1) {
            Node first, second;

            first = pq.poll();
            second = pq.poll();

            pq.add(new Node(first, second));
        }

        // retourne la racine
        return pq.poll(); // TODO À compléter
    }

    /**
     * @param node Nœud actuel
     * @param code Code Huffman
     */
    private static void printTable(Node node, String code) {
        // DFS
        if (node.isLeaf()) {
            System.out.println(String.format("%4s %4s %-4s", node.getSymbol(), node.getFrequency(), code));
        }

        if (node.left != null) {
            printTable(node.left, code + "0");
        }
        if (node.right != null) {
            printTable(node.right, code + "1");
        }
        // TODO À compléter
    }

    /**
     * @param node Nœud de départ
     */
    private static void printGraph(Node node) {
        System.out.println("graph {\n    node [style=rounded]");

        //BFS
        LinkedList<Node> list = new LinkedList<>();
        list.add(node);
        while (list.size() > 0) {
            Node currentNode = list.poll(); //défiler
            System.out.println(currentNode.toString());

            if (currentNode.left != null) {
                list.add(currentNode.left);
            }
            if (currentNode.right != null) {
                list.add(currentNode.right);
            }
        }

        System.out.println("}");
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