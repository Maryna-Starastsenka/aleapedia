/**
 * Adaptation, refactorisation et simplification par Maryna Starastsenka (20166402) (l'auteure du devoir)
 * de la fonction de hachage cryptographique SipHasher initialement implémentée
 * par Isaac Whitfield (license MIT) en 2016 :
 * https://github.com/whitfin/siphash-java/tree/master/src/main/java/io/whitfin/siphash
 */
final class SipHasher {

    /**
     * Valeurs par défaut des tours de compression C et D.
     */
    static final int DEFAULT_C = 2;
    static final int DEFAULT_D = 4;

    /**
     * Valeurs initiales des nombres magiques v0 à v3.
     */
    static final long INITIAL_V0 = 0x736f6d6570736575L;
    static final long INITIAL_V1 = 0x646f72616e646f6dL;
    static final long INITIAL_V2 = 0x6c7967656e657261L;
    static final long INITIAL_V3 = 0x7465646279746573L;

    /**
     * Fait le hachage 0A d'une entrée de données
     *
     * Utilisation des valeurs par défaut pour les tours C et D.
     * key représente la valeur de seed du hachage (fixée par défaut ici).
     *
     * @param data l'entrée à hacher
     * @return une valeur long en sortie du hachage
     */
    public static long hash(byte[] data) {
        // seed
        byte[] key = new byte[16];
        for (int i = 0; i < 16; i++) {
            key[i] = (byte) i;
        }

        long k0 = bytesToLong(key, 0);
        long k1 = bytesToLong(key, 8);

        long v0 = INITIAL_V0 ^ k0;
        long v1 = INITIAL_V1 ^ k1;
        long v2 = INITIAL_V2 ^ k0;
        long v3 = INITIAL_V3 ^ k1;

        long m;
        int last = data.length / 8 * 8;
        int i = 0;
        int r;

        while (i < last) {
            m = data[i++] & 0xffL;
            for (r = 1; r < 8; r++) {
                m |= (data[i++] & 0xffL) << (r * 8);
            }

            v3 ^= m;
            for (r = 0; r < DEFAULT_C; r++) {
                v0 += v1;
                v2 += v3;
                v1 = rotateLeft(v1, 13);
                v3 = rotateLeft(v3, 16);

                v1 ^= v0;
                v3 ^= v2;
                v0 = rotateLeft(v0, 32);

                v2 += v1;
                v0 += v3;
                v1 = rotateLeft(v1, 17);
                v3 = rotateLeft(v3, 21);

                v1 ^= v2;
                v3 ^= v0;
                v2 = rotateLeft(v2, 32);
            }
            v0 ^= m;
        }

        m = 0;
        for (i = data.length - 1; i >= last; --i) {
            m <<= 8;
            m |= (data[i] & 0xffL);
        }
        m |= (long) data.length << 56;

        v3 ^= m;
        for (r = 0; r < DEFAULT_C; r++) {
            v0 += v1;
            v2 += v3;
            v1 = rotateLeft(v1, 13);
            v3 = rotateLeft(v3, 16);

            v1 ^= v0;
            v3 ^= v2;
            v0 = rotateLeft(v0, 32);

            v2 += v1;
            v0 += v3;
            v1 = rotateLeft(v1, 17);
            v3 = rotateLeft(v3, 21);

            v1 ^= v2;
            v3 ^= v0;
            v2 = rotateLeft(v2, 32);
        }
        v0 ^= m;

        v2 ^= 0xff;
        for (r = 0; r < DEFAULT_D; r++) {
            v0 += v1;
            v2 += v3;
            v1 = rotateLeft(v1, 13);
            v3 = rotateLeft(v3, 16);

            v1 ^= v0;
            v3 ^= v2;
            v0 = rotateLeft(v0, 32);

            v2 += v1;
            v0 += v3;
            v1 = rotateLeft(v1, 17);
            v3 = rotateLeft(v3, 21);

            v1 ^= v2;
            v3 ^= v0;
            v2 = rotateLeft(v2, 32);
        }

        return v0 ^ v1 ^ v2 ^ v3;
    }

    /**
     * Convertit un morceau de 8 octets en un nombre en petit-boutiste.
     *
     * Accepte un offset pour déterminer où le morceau commence
     *
     * @param bytes le tableau d'octets contenant nos octets à convertir.
     * @param offset l'index à partir duquel on commence à couper les octets.
     * @return une représentation 'long', en petit-boutiste
     */
    static long bytesToLong(byte[] bytes, int offset) {
        long m = 0;
        for (int i = 0; i < 8; i++) {
            m |= ((((long) bytes[i + offset]) & 0xff) << (8 * i));
        }
        return m;
    }

    /**
     * Fait tourner vers la gauche un nombre d'entrée "val" par un nombre de "shift" bits .
     *
     * Les bits qui sont poussés vers la gauche sont retournés vers la droite,
     * ce qui constitue une rotation vers la gauche (un déplacement circulaire).
     *
     * Finalement, masquage 64 bits.
     *
     * @param value la valeur à shifter.
     * @param shift à quel point shifter à gauche.
     * @return une valeur 'long' après avoir été shiftée.
     */
    static long rotateLeft(long value, int shift) {
        return (value << shift) | value >>> (64 - shift);
    }
}