/**
 * @author Maryna Starastsenka (20166402)
 *
 * Filtre de Bloom (structure de données probabiliste)
 */
public class BloomFilter {

    private BitSet bitSet;
    private int count;
    private int numHashes;

    /**
     * Crée un filtre de Bloom basé sur la taille de l'ensemble de bits et du
     * nombre de fonctions de hachage.
     *
     * @param numBits taille de l'ensemble de bits.
     * @param numHashes nombre de fonctions de hachage.
     */
    public BloomFilter(int numBits, int numHashes) {
        bitSet = new BitSet(numBits);
        this.numHashes = numHashes;
    }

    /**
     * Crée un filtre de Bloom basé sur le nombre d'éléments attendus et de la
     * probabilité de faux positifs désirée.
     *
     * @param numElems nombre d'éléments à insérer.
     * @param falsePosProb probabilité de faux positifs.
     */
    public BloomFilter(int numElems, double falsePosProb) {
        this(getNumBitsFrom(numElems, falsePosProb), getNumHashesFrom(falsePosProb));
    }

    /**
     * Ajoute un élément au filtre de Bloom.
     *
     * @param key l'élément à insérer.
     */
    public void add(byte[] key) {
        // on hache et ajoute chaque élément haché autant de fois qu'on a de fonctions de hachage
        for (int i = 0; i < numHashes; i++) {
            bitSet.set(hash(key, i, size()));
        }
        // on incrémente le nombre d'éléments ajoutés au filtre
        count++;
    }

    /**
     * Cherche pour l'élément dans le filtre de Bloom.
     *
     * @param key l'élément à trouver.
     * @return si l'élément est possiblement dans le filtre.
     */
    public boolean contains(byte[] key) {
        for (int i = 0; i < numHashes; i++) {
            if (!bitSet.get(hash(key, i, size()))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Remet à zéro le filtre de Bloom.
     */
    public void reset() {
        bitSet = new BitSet(size());
    }

    /**
     * Retourne le nombre de bits du filtre de Bloom.
     *
     * @return nombre de bits.
     */
    public int size() {
        return bitSet.getNbits();
    }

    /**
     * Retourne le nombre d'éléments insérés dans le filtre de Bloom.
     *
     * @return nombre d'éléments insérés.
     */
    public int count() {
        return count;
    }

    /**
     * Retourne la probabilité actuelle de faux positifs.
     *
     * @return probabilité de faux positifs.
     */
    public double fpp() {
        return Math.pow(1-Math.exp((double) -numHashes * count() / size()), numHashes);
    }

    /**
     * Logarithme en base 2.
     *
     * @param x nombre dont on veut calculer le log en base 2.
     * @return logarithme en base 2 du nombre.
     */
    public static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }

    /**
     * Calcul du nombre de fonctions de hachage à avoir pour obtenir une certaine probabilité de faux positifs.
     *
     * @param falsePosProb probabilité de faux positifs.
     * @return nombre de fonctions de hachage à avoir pour obtenir la probabilité de faux positifs spécifiée.
     */
    private static int getNumHashesFrom(double falsePosProb) {
        return (int) Math.ceil(-log2(falsePosProb));
    }

    /**
     * Calcul de la taille de l'ensemble de bits à avoir pour obtenir une certaine probabilité de faux positifs
     * en fonction du nombre d'éléments à insérer dans le filtre de Bloom.
     *
     * @param numElems nombre d'éléments à insérer dans le filtre de Bloom.
     * @param falsePosProb probabilité de faux positifs.
     * @return taille de l'ensemble de bits nécessaires pour obtenir la probabilité de faux positifs spécifiée.
     */
    private static int getNumBitsFrom(int numElems, double falsePosProb) {
        return (int) Math.ceil((-numElems * Math.log(falsePosProb)) / Math.pow(Math.log(2), 2));
    }

    /**
     * Fonction de hachage inspirée de la méthode de Kirsch-Mitzenmacher
     * qui utilise 2 fonctions de hachage (une non-cryptographique et une cryptographique) pour
     * générer une infinité de nouvelles fonctions de hachage.
     * Contrairement à la méthode originale, le nombre premier utilisée dans la formule
     * est ici arbitraire et constant (il ne dépend pas d'autres paramètres).
     *
     * La fonction de hachage non-cryptographique choisie est Murmur3.
     * La fonction de hachage cryptographique choisie est SipHasher.
     *
     * Les adaptations de ces 2 fonctions de hachage sont présentées (ainsi que les auteurs dont
     * elles sont issues) dans les classes Murmur3 et SipHasher.
     *
     * @param key tableau d'octets à hacher.
     * @param hashId numéro de la fonction de hachage dont on souhaite se servir pour le hachage courant.
     * @param size taille de l'ensemble de bits.
     * @return valeur du hachage du tableau d'octets d'entrée sous forme d'entier signé de 32 bits (mais la
     * valeur est toujours positive).
     */
    public static int hash(byte[] key, int hashId, int size) {
        // hachage non-cryptographique Murmur3
        long hash1 = Murmur3.murmur3Hash64(key);

        // hachage cryptographique SipHasher
        long hash2 = SipHasher.hash(key);

        // génération de la fonction de hachage basée sur la méthode de Kirsch-Mitzenmacher
        // à l'exception du nombre premier qui est ici arbitraire
        long hash = hash1 + hash2 * hashId % 10007;

        // gérer l'overflow car on veut toujours un hash positif
        if (hash < 0) hash = hash + Long.MAX_VALUE + 1;

        // inutile d'avoir un hash qui dépasse la taille de l'ensemble de bits
        return (int) (hash % size);
    }
}