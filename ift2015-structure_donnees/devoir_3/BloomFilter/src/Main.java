import java.text.DecimalFormat;

public class Main {
//TODO ne pas inclure ce fichier dans le rendu final

    public static void main(String[] args) {
        //testBitSet();
        testBloomFilter();
//        byte b1 = (byte) -1;
//        String s1 = String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
//        System.out.println(s1); // 10000001
    }

    private static void testBloomFilter() {
        DecimalFormat df = new DecimalFormat("##.####");

//        var bf = new BloomFilter(8, 2);
        int limit = 9;
        // Bloom Filter en settant la pro
        var bf = new BloomFilter(limit, 0.5);
        //        var a = BloomFilter.hash("Hello".getBytes(), 1);
        var chaines = new String[] {
                "hello",
                "Who are you?",
                "Bob",
                "Voiture",
                "Bonjour",
                "Les vacances",
                "Je sais voler",
                "Je suis malade",
                "John",
                "hello2",
                "Who are you?2",
                "Bob2",
                "Voiture2",
                "Bonjour2",
                "Les vacances2",
                "Je sais voler2",
                "Je suis malade2",
                "John2"
        };
        System.out.println("la proba de faux positifs est de " + df.format(bf.fpp()*100.0) + "% pour 0 insertion.");
        for (int i = 0; i < limit; i++) {
            bf.add(chaines[i].getBytes());
            System.out.println("la proba de faux positifs est de " + df.format(bf.fpp()*100.0) + "% pour " + (i + 1) + " insertions.");
        }

        for (int i = 0; i < chaines.length; i++) {
            System.out.println("Le set contient peut-être '" + chaines[i] + "' ? " + bf.contains(chaines[i].getBytes()) + " (" + (i < limit) + ")");
        }


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
