public class Main {

    // Driver Code
    public static void main(String[] args)
    {
//        int[][] adjacencyMatrix = {
//                { 0,  4, 0,  0,  0,  0, 0,  8, 0 },
//                { 4,  0, 8,  0,  0,  0, 0, 11, 0 },
//                { 0,  8, 0,  7,  0,  4, 0,  0, 2 },
//                { 0,  0, 7,  0,  9, 14, 0,  0, 0 },
//                { 0,  0, 0,  9,  0, 10, 0,  0, 0 },
//                { 0,  0, 4,  0, 10,  0, 2,  0, 0 },
//                { 0,  0, 0, 14,  0,  2, 0,  1, 6 },
//                { 8, 11, 0,  0,  0,  0, 1,  0, 7 },
//                { 0,  0, 2,  0,  0,  0, 6,  7, 0 }
//        };
//        DijkstrasAlgorithm.dijkstra(adjacencyMatrix, 0);


        int[][] T = {
                {0, 0},
                {1, 4},
                {2, 5},
                {3, 4},
                {4, 0},
                {5, 4},
                {6, 7},
                {7, 5},
                {8, 7},
        };

        int v = 6;

        imprimer(v, T, true);

    }

    public static void imprimer(int v, int[][] T, boolean last) {
        if (T[v][1] != 0) {
            imprimer(T[v][1], T, false);
            if (last) System.out.print("v" + v);
            else System.out.print("v" + v + " à ");
        } else {
            System.out.print("v" + v + " à ");
        }
    }
}
