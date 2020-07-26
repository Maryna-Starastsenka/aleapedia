/**
 * https://github.com/whitfin/siphash-java/tree/master/src/main/java/io/whitfin/siphash
 * Provides hashing for the SipHash cryptographic hash family.
 *
 * This class offers three main utilities;
 *
 * - A zero-allocation SipHash algorithm.
 * - A container implementation for single-key environments.
 * - A streaming SipHash algorithm for unknown input length.
 *
 * In most cases, the zero-allocation (0A) implementation will be desired. This
 * can be called via {@link #hash(byte[], byte[])} on the most basic level.
 *
 */
final class SipHasher {

    /**
     * Default value for the C rounds of compression.
     */
    static final int DEFAULT_C = 2;

    /**
     * Default value for the D rounds of compression.
     */
    static final int DEFAULT_D = 4;

    /**
     * Initial value for the v0 magic number.
     */
    static final long INITIAL_V0 = 0x736f6d6570736575L;

    /**
     * Initial value for the v1 magic number.
     */
    static final long INITIAL_V1 = 0x646f72616e646f6dL;

    /**
     * Initial value for the v2 magic number.
     */
    static final long INITIAL_V2 = 0x6c7967656e657261L;

    /**
     * Initial value for the v3 magic number.
     */
    static final long INITIAL_V3 = 0x7465646279746573L;

    /**
     * Creates a new container, seeded with the provided key.
     *
     * @param key
     *      the key bytes used to seed the container.
     * @return
     *      a {@link SipHasherContainer} instance after initialization.
     */
    public static SipHasherContainer container(byte[] key) {
        return new SipHasherContainer(key);
    }

    /**
     * Hashes a data input
     *
     * This will used the default values for C and D rounds.
     *
     * @param data
     *      the input data to hash.
     * @return
     *      a long value as the output of the hash.
     */
    public static long hash(byte[] data) {
        byte[] key = new byte[16];
        for (int i = 0; i < 16; i++) {
            key[i] = (byte) i;
        }
        return hash(key, data, DEFAULT_C, DEFAULT_D);
    }

    /**
     * Hashes a data input for a given key.
     *
     * This will used the default values for C and D rounds.
     *
     * @param key
     *      the key to seed the hash with.
     * @param data
     *      the input data to hash.
     * @return
     *      a long value as the output of the hash.
     */
    public static long hash(byte[] key, byte[] data) {
        return hash(key, data, DEFAULT_C, DEFAULT_D);
    }

    /**
     * Hashes a data input for a given key, using the provided rounds
     * of compression.
     *
     * @param key
     *      the key to seed the hash with.
     * @param data
     *      the input data to hash.
     * @param c
     *      the number of C rounds of compression
     * @param d
     *      the number of D rounds of compression.
     * @return
     *      a long value as the output of the hash.
     */
    public static long hash(byte[] key, byte[] data, int c, int d) {
        if (key.length != 16) {
            throw new IllegalArgumentException("Key must be exactly 16 bytes!");
        }

        long k0 = bytesToLong(key, 0);
        long k1 = bytesToLong(key, 8);

        return hash(
                c, d,
                INITIAL_V0 ^ k0,
                INITIAL_V1 ^ k1,
                INITIAL_V2 ^ k0,
                INITIAL_V3 ^ k1,
                data
        );
    }

    /**
     * Initializes a streaming hash, seeded with the given key.
     *
     * This will used the default values for C and D rounds.
     *
     * @param key
     *      the key to seed the hash with.
     * @return
     *      a {@link SipHasherStream} instance to update and digest.
     */
    public static SipHasherStream init(byte[] key) {
        return init(key, DEFAULT_C, DEFAULT_D);
    }

    /**
     * Initializes a streaming hash, seeded with the given key and desired
     * rounds of compression.
     *
     * This will used the default values for C and D rounds.
     *
     * @param key
     *      the key to seed the hash with.
     * @param c
     *      the number of C rounds of compression
     * @param d
     *      the number of D rounds of compression.
     * @return
     *      a {@link SipHasherStream} instance to update and digest.
     */
    public static SipHasherStream init(byte[] key, int c, int d) {
        return new SipHasherStream(key, c, d);
    }

    /**
     * Converts a hash to a hexidecimal representation.
     *
     * @param hash
     *      the finalized hash value to convert to hex.
     * @return
     *      a {@link String} representation of the hash.
     */
    public static String toHexString(long hash) {
        String hex = Long.toHexString(hash);

        if (hex.length() == 16) {
            return hex;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0, j = 16 - hex.length(); i < j; i++) {
            sb.append('0');
        }

        return sb.append(hex).toString();
    }

    /**
     * Converts a chunk of 8 bytes to a number in little endian.
     *
     * Accepts an offset to determine where the chunk begins.
     *
     * @param bytes
     *      the byte array containing our bytes to convert.
     * @param offset
     *      the index to start at when chunking bytes.
     * @return
     *      a long representation, in little endian.
     */
    static long bytesToLong(byte[] bytes, int offset) {
        long m = 0;
        for (int i = 0; i < 8; i++) {
            m |= ((((long) bytes[i + offset]) & 0xff) << (8 * i));
        }
        return m;
    }

    /**
     * Internal 0A hashing implementation.
     *
     * Requires initial state being manually provided (to avoid allocation). The
     * compression rounds must also be provided, as nothing will be validated in
     * this layer (such as defaults).
     *
     * @param c
     *      the rounds of C compression to apply.
     * @param d
     *      the rounds of D compression to apply.
     * @param v0
     *      the seeded initial value of v0.
     * @param v1
     *      the seeded initial value of v1.
     * @param v2
     *      the seeded initial value of v2.
     * @param v3
     *      the seeded initial value of v3.
     * @param data
     *      the input data to hash using the SipHash algorithm.
     * @return
     *      a long value as the output of the hash.
     */
    static long hash(int c, int d, long v0, long v1, long v2, long v3, byte[] data) {
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
            for (r = 0; r < c; r++) {
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
        for (r = 0; r < c; r++) {
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
        for (r = 0; r < d; r++) {
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
     * Rotates an input number `val` left by `shift` number of bits.
     *
     * Bits which are pushed off to the left are rotated back onto the right,
     * making this a left rotation (a circular shift).
     *
     * This is very close to {@link Long#rotateLeft(long, int)} aside from
     * the use of the 64 bit masking.
     *
     * @param value
     *      the value to be shifted.
     * @param shift
     *      how far left to shift.
     * @return
     *      a long value after being shifted.
     */
    static long rotateLeft(long value, int shift) {
        return (value << shift) | value >>> (64 - shift);
    }
}

/**
 * Small container of state to aid SipHash throughput.
 *
 * This will keep a constant key and seeded v* values for use across many
 * hashes. As such, this avoids a small amount of overhead on each hash which
 * might prove useful in the case you have constant keys (hash tables, etc).
 */
final class SipHasherContainer {

    /**
     * The seeded value for the magic v0 number.
     */
    private final long v0;

    /**
     * The seeded value for the magic v1 number.
     */
    private final long v1;

    /**
     * The seeded value for the magic v2 number.
     */
    private final long v2;

    /**
     * The seeded value for the magic v3 number.
     */
    private final long v3;

    /**
     * Initializes a container from a key seed.
     *
     * @param key
     *      the key to use to seed this hash container.
     */
    SipHasherContainer(byte[] key) {
        if (key.length != 16) {
            throw new IllegalArgumentException("Key must be exactly 16 bytes!");
        }

        long k0 = SipHasher.bytesToLong(key, 0);
        long k1 = SipHasher.bytesToLong(key, 8);

        this.v0 = SipHasher.INITIAL_V0 ^ k0;
        this.v1 = SipHasher.INITIAL_V1 ^ k1;
        this.v2 = SipHasher.INITIAL_V2 ^ k0;
        this.v3 = SipHasher.INITIAL_V3 ^ k1;
    }

    /**
     * Hashes input data using the preconfigured state.
     *
     * @param data
     *      the data to hash and digest.
     * @return
     *      a long value as the output of the hash.
     */
    public final long hash(byte[] data) {
        return hash(data, SipHasher.DEFAULT_C, SipHasher.DEFAULT_D);
    }

    /**
     * Hashes input data using the preconfigured state.
     *
     * @param data
     *      the data to hash and digest.
     * @param c
     *      the desired rounds of C compression.
     * @param d
     *      the desired rounds of D compression.
     * @return
     *      a long value as the output of the hash.
     */
    public final long hash(byte[] data, int c, int d) {
        return SipHasher.hash(
                c, d,
                this.v0,
                this.v1,
                this.v2,
                this.v3,
                data
        );
    }
}

/**
 * Streaming implementation of the SipHash algorithm.
 *
 * This implementation is slower than the 0A implementation, but allows for
 * unknown input this.lengths to enable hashing as more data is received. Chunks
 * are processed in 8-byte blocks in the SipHash algorithms, so if you're
 * using huge input, you should expect slowness.
 *
 * Although this implementation requires an initial allocation, there are
 * no further allocations - so memory should prove similar to the non-streaming
 * implementation.
 */
final class SipHasherStream {

    /**
     * The specified rounds of C compression.
     */
    private final int c;

    /**
     * The specified rounds of D compression.
     */
    private final int d;

    /**
     * Counter to keep track of the input
     */
    private byte len;

    /**
     * Index to keep track of chunk positioning.
     */
    private int m_idx;

    /**
     * The current value for the m number.
     */
    private long m;

    /**
     * The current value for the this.v0 number.
     */
    private long v0;

    /**
     * The current value for the this.v1 number.
     */
    private long v1;

    /**
     * The current value for the this.v2 number.
     */
    private long v2;

    /**
     * The current value for the this.v3 number.
     */
    private long v3;

    /**
     * Initializes a streaming digest using a key and compression rounds.
     *
     * @param key
     *      the key to use to seed this hash container.
     * @param c
     *      the desired rounds of C compression.
     * @param d
     *      the desired rounds of D compression.
     */
    SipHasherStream(byte[] key, int c, int d) {
        if (key.length != 16) {
            throw new IllegalArgumentException("Key must be exactly 16 bytes!");
        }

        long k0 = SipHasher.bytesToLong(key, 0);
        long k1 = SipHasher.bytesToLong(key, 8);

        this.v0 = SipHasher.INITIAL_V0 ^ k0;
        this.v1 = SipHasher.INITIAL_V1 ^ k1;
        this.v2 = SipHasher.INITIAL_V2 ^ k0;
        this.v3 = SipHasher.INITIAL_V3 ^ k1;

        this.c = c;
        this.d = d;

        this.m = 0;
        this.len = 0;
        this.m_idx = 0;
    }

    /**
     * Updates the hash with a single byte.
     *
     * This will only modify the internal `m` value, nothing will be modified
     * in the actual `v*` states until an 8-byte block has been provided.
     *
     * @param b
     *      the byte being added to the digest.
     * @return
     *      the same {@link SipHasherStream} for chaining.
     */
    public final SipHasherStream update(byte b) {
        this.len++;
        this.m |= (((long) b & 0xff) << (this.m_idx++ * 8));
        if (this.m_idx < 8) {
            return this;
        }
        this.v3 ^= this.m;
        for (int i = 0; i < this.c; i++) {
            round();
        }
        this.v0 ^= this.m;
        this.m_idx = 0;
        this.m = 0;
        return this;
    }

    /**
     * Updates the hash with an array of bytes.
     *
     * @param bytes
     *      the bytes being added to the digest.
     * @return
     *      the same {@link SipHasherStream} for chaining.
     */
    public final SipHasherStream update(byte[] bytes) {
        for (byte b : bytes) {
            update(b);
        }
        return this;
    }

    /**
     * Finalizes the digest and returns the hash.
     *
     * This works by padding to the next 8-byte block, before applying
     * the compression rounds once more - but this time using D rounds
     * of compression rather than C.
     *
     * @return
     *      the final result of the hash as a long.
     */
    public final long digest() {
        byte msgLenMod256 = this.len;

        while (this.m_idx < 7) {
            update((byte) 0);
        }
        update(msgLenMod256);

        this.v2 ^= 0xff;
        for (int i = 0; i < this.d; i++) {
            round();
        }

        return this.v0 ^ this.v1 ^ this.v2 ^ this.v3;
    }

    public static long rotateLeft(long i, int distance) {
        return (i << distance) | (i >>> -distance);
    }

    /**
     * SipRound implementation for internal use.
     */
    private void round() {
        this.v0 += this.v1;
        this.v2 += this.v3;
        this.v1 = rotateLeft(this.v1, 13);
        this.v3 = rotateLeft(this.v3, 16);

        this.v1 ^= this.v0;
        this.v3 ^= this.v2;
        this.v0 = rotateLeft(this.v0, 32);

        this.v2 += this.v1;
        this.v0 += this.v3;
        this.v1 = rotateLeft(this.v1, 17);
        this.v3 = rotateLeft(this.v3, 21);

        this.v1 ^= this.v2;
        this.v3 ^= this.v0;
        this.v2 = rotateLeft(this.v2, 32);
    }
}