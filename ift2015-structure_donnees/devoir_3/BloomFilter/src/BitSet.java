/**
 * @author Maryna Starastsenka (20166402)
 *
 * Ensemble de bits qui sert de support au filtre de Bloom
 */
public class BitSet {

    private byte[] bitSet;
    private static final int EIGHT = 8;
    private int nbits;

    public int getNbits() {
        return nbits;
    }

    /**
     * Crée un ensemble de bits, d'une certaine taille. Ils sont initialisés à
     * {@code false}.
     *
     * @param nbits taille initiale de l'ensemble
     */
    public BitSet(int nbits) {
        this.nbits = nbits;
        // trouver le nombre d'octets nécessaires pour stocker nbits
        int nBytes = nbits % EIGHT == 0 ? nbits/EIGHT : (nbits/EIGHT) + 1;
        bitSet = new byte[nBytes];
    }

    /**
     * Retourne la valeur du bit à l'index spécifié.
     *
     * @param bitIndex l'index du bit
     * @return la valeur du bit à l'index spécifié
     */
    public boolean get(int bitIndex) {
        if (bitIndex >= 0 && bitIndex < nbits) {
            int byteIndex = getByteIndex(bitIndex);
            byte oneMask = getOneMask(bitIndex);
            // On utilise le ET logique et un masque ne contenant qu'un 1 binaire
            // au bon endroit pour vérifier la valeur du bit recherché
            return (bitSet[byteIndex] & oneMask) != 0;
        }
        return false;
    }

    /**
     * Définit le bit à l'index spécifié comme {@code true}.
     *
     * @param bitIndex l'index du bit
     */
    public void set(int bitIndex) {
        if (bitIndex >= 0 && bitIndex < nbits) {
            int byteIndex = getByteIndex(bitIndex);
            byte oneMask = getOneMask(bitIndex);
            // On utilise le OU logique et un masque ne contenant qu'un 1 binaire
            // au bon endroit pour setter la valeur du bit spécifié à un 1 binaire
            bitSet[byteIndex] |= oneMask;
        }
    }

    /**
     * Définit le bit à l'index spécifié comme {@code false}.
     *
     * @param bitIndex l'index du bit
     */
    public void clear(int bitIndex) {
        if (bitIndex >= 0 && bitIndex < nbits) {
            int byteIndex = getByteIndex(bitIndex);
            byte oneMask = getOneMask(bitIndex);
            // On utilise le ET logique et la négation logique ainsi qu'un masque ne
            // contenant qu'un 1 binaire au bon endroit pour setter la valeur du bit spécifié à un 0 binaire
            bitSet[byteIndex] &= ~oneMask;
        }
    }

    /**
     * Obtenir l'indice de l'octet où se trouve l'indice du bit spécifié
     *
     * @param bitIndex indice du bit dont on cherche à connaître l'octet parent.
     * @return indice de l'octet contenant le bit spécifié.
     */
    private int getByteIndex(int bitIndex) {
        return bitIndex / EIGHT;
    }

    /**
     * Obtenir un masque sur un octet qui ne contient qu'un bit {@code true}
     * à l'endroit spécifié en paramètre.
     *
     * @param bitIndex indice du bit qui doit être {@code true}.
     * @return masque sur un octet qui ne contient qu'un bit {@code true}.
     */
    private byte getOneMask(int bitIndex) {
        byte oneMask = (byte) (1 << (bitIndex % EIGHT));
        return oneMask;
    }

}
