/**
 * @author Prénom Nom (Matricule)
 */
public class BitSet {

    private byte[] bitSet;
    private static final int EIGHT = 8;
    private int nbits;

    /**
     * Crée un ensemble de bits, d'une certaine taille. Ils sont initialisés à
     * {@code false}.
     *
     * @param nbits taille initiale de l'ensemble
     */
    public BitSet(int nbits) {
        // TODO À compléter DONE
        this.nbits = nbits;
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
        // TODO À compléter DONE
        if (bitIndex < nbits) {
            int byteIndex = getByteIndex(bitIndex);
            byte oneMask = getOneMask(bitIndex);
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
        // TODO À compléter
        if (bitIndex < nbits) {
            int byteIndex = getByteIndex(bitIndex);
            byte oneMask = getOneMask(bitIndex);
            bitSet[byteIndex] |= oneMask;
        }
    }

    /**
     * Définit le bit à l'index spécifié comme {@code false}.
     *
     * @param bitIndex l'index du bit
     */
    public void clear(int bitIndex) {
        // TODO À compléter
        if (bitIndex < nbits) {
            int byteIndex = getByteIndex(bitIndex);
            byte oneMask = getOneMask(bitIndex);
            bitSet[byteIndex] &= ~oneMask;
        }
    }

//***** CUSTOM FUNCTIONS *****//
//TODO ajouter la javadoc

    private int getByteIndex(int bitIndex) {
        return bitIndex / EIGHT;
    }

    private byte getOneMask(int bitIndex) {
        // bitIndexWithinByte
        byte oneMask = (byte) (1 << (bitIndex % EIGHT));
        return oneMask;
    }

    public int getNbits() {
        return nbits;
    }
}
