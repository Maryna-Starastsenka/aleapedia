/**
 * @author Prénom Nom (Matricule)
 */
public class BloomFilter {

    private BitSet bitSet;
    private int count;
    private int numHashes;

    /**
     * Crée un filtre de Bloom basé sur la taille de l'ensemble de bits et du
     * nombre de fonctions de hachage.
     *
     * @param numBits taille de l'ensemble de bits
     * @param numHashes nombre de fonctions de hachage
     */
    public BloomFilter(int numBits, int numHashes) {
        // TODO À compléter DONE
        bitSet = new BitSet(numBits);
        this.numHashes = numHashes;
    }

    /**
     * Crée un filtre de Bloom basé sur le nombre d'éléments attendus et de la
     * probabilité de faux positifs désirée.
     *
     * @param numElems nombre d'éléments à insérer
     * @param falsePosProb probabilité de faux positifs
     */
    public BloomFilter(int numElems, double falsePosProb) {
        // TODO À compléter DONE
        this(getNumBitsFrom(numElems, falsePosProb), getNumHashesFrom(falsePosProb));
    }

    /**
     * Ajoute un élément au filtre de Bloom.
     *
     * @param key l'élément à insérer
     */
    public void add(byte[] key) {
        // TODO À compléter DONE
        for (int i = 0; i < numHashes; i++) {
            bitSet.set(hash(key, i, size()));
        }
        count++;
    }

    /**
     * Cherche pour l'élément dans le filtre de Bloom.
     *
     * @param key l'élément à trouver
     * @return si l'élément est possiblement dans le filtre
     */
    public boolean contains(byte[] key) {
        for (int i = 0; i < numHashes; i++) {
            if (!bitSet.get(hash(key, i, size()))) {
                return false;
            }
        }
        return true; // TODO À compléter DONE
    }

    /**
     * Remet à zéro le filtre de Bloom.
     */
    public void reset() {
        // TODO À compléter DONE
        bitSet = new BitSet(size());
    }

    /**
     * Retourne le nombre de bits du filtre de Bloom.
     *
     * @return nombre de bits
     */
    public int size() {
        return bitSet.getNbits(); // TODO À compléter DONE
    }

    /**
     * Retourne le nombre d'éléments insérés dans le filtre de Bloom.
     *
     * @return nombre d'éléments insérés
     */
    public int count() {
        return count; // TODO À compléter DONE
    }

    /**
     * Retourne la probabilité actuelle de faux positifs.
     *
     * @return probabilité de faux positifs
     */
    public double fpp() {
        return Math.pow(1-Math.exp((double) -numHashes * count() / size()), numHashes);
        // TODO À compléter DONE
    }

    //***** CUSTOM FUNCTIONS *****//
    public static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }

    private static int getNumHashesFrom(double falsePosProb) {
        return (int) Math.ceil(-log2(falsePosProb));
    }

    private static int getNumBitsFrom(int numElems, double falsePosProb) {
        return (int) Math.ceil((-numElems * Math.log(falsePosProb)) / Math.pow(Math.log(2), 2));
    }

    public static int hash(byte[] key, int hashId, int size) {
        // hachage non-cryptographique Murmur3
        long hash1 = Murmur3.murmur3Hash64(key);

        // hachage cryptographique SipHasher
        long hash2 = SipHasher.hash(key);

        // génération de la fonction de hachage basée sur la méthode de Kirsch-Mitzenmacher
        // à l'exception du nombre premier qui est ici arbitraire
        long hash = hash1 + hash2 * hashId % 10007;

        // Gérer l'overflow, on veut toujours un hash positif
        if (hash < 0) hash = hash + Long.MAX_VALUE + 1;
        return (int) (hash % size);
    }
}