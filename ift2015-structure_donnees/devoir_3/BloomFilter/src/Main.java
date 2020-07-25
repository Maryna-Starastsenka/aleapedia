public class Main {
//TODO ne pas inclure ce fichier dans le rendu final

    public static void main(String[] args) {
        testBitSet();
    }

    private static void testBitSet() {
        var bs = new BitSet(16);
        bs.set(12);
        bs.set(0);
        bs.set(7);
        bs.set(13);
        bs.set(15);
        bs.set(8);
        for (int i=0; i<16; i++) {
            System.out.println(i + " : " + bs.get(i));
        }

        bs.clear(2);
        bs.clear(0);
        bs.clear(15);
        bs.clear(13);
        for (int i=0; i<16; i++) {
            System.out.println(i + " : " + bs.get(i));
        }
    }
}
