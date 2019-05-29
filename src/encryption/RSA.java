package encryption;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Random;

import data.tuple.Tuple2D;

public class RSA implements Serializable {

	private static final long serialVersionUID = 932154546065193611L;

	// PUBLIC KEY

	// n is the modulus for the public key and the private keys
	private final BigInteger n;
	
	// e is the public key exponent
	private final BigInteger e;

	// PRIVATE KEY

	// p and q are random very large primes
	private final BigInteger p;
	private final BigInteger q;
	
	// d is the private key exponent
	private final BigInteger d;

	public RSA(boolean generate) {
		//Tuple2D<BigInteger, BigInteger> pq = generateTwoPrimes();
		p = BigInteger.probablePrime(256, new Random());
		q = BigInteger.probablePrime(256, new Random());

		n = p.multiply(q); // n = p*q

		Tuple2D<BigInteger, BigInteger> ed = generatePublicPrivateKeyExponent();
		e = ed.getA();
		d = ed.getB();
	}

	public RSA(BigInteger n, BigInteger e) {
		this.n = n;
		this.e = e;

		p = BigInteger.ZERO;
		q = BigInteger.ZERO;
		d = BigInteger.ZERO;
	}

	public RSA getPublicCopy() {
		RSA rsa = new RSA(this.n, this.e);
		return rsa;
	}

	public BigInteger encrypt(BigInteger m) {
		return encrypt(m, n, e);
	}

	public byte[] encrypt(byte[] mData) {
		return mData;
	}

	public BigInteger decrypt(BigInteger c) {
		return decrypt(c, n, d);
	}

	public byte[] decrypt(byte[] cData) {
		
		return cData;
	}
	/*
	public static Tuple2D<BigInteger, BigInteger> generateTwoPrimes() {
		BigInteger p = null, q = null;
		p = BigInteger.probablePrime(256, new Random());
		p = BigInteger.probablePrime(256, new Random());
		return new Tuple2D<BigInteger, BigInteger>(p, q);
	}

	
	public static BigInteger findN(BigInteger p, BigInteger q) {
		return p.multiply(q);
	}
	*/
	
	private static BigInteger getTotient(BigInteger n, BigInteger p, BigInteger q) {
		BigInteger pMinusOne = p.subtract(new BigInteger("1"))
		BigInteger qMinusOne = q.subtract(new BigInteger("1"))
		BigInteger lcm = pMinusOne.multiply(qMinusOne).divide(pMinusOne.gcd(qMinusOne)) // lcm of p - 1 and q - 1
		return lcm
	}

	public static Tuple2D<BigInteger, BigInteger> generatePublicPrivateKeyExponent() {
		return new Tuple2D<BigInteger, BigInteger>(null, null);
	}

	public static BigInteger encrypt(BigInteger m, BigInteger n, BigInteger e) {
		// c = m^e mod n
		return m.modPow(e, n);
	}

	public static BigInteger decrypt(BigInteger c, BigInteger n, BigInteger d) {
		// m = c^d mod n
		if (c.equals(BigInteger.ZERO) || n.equals(BigInteger.ZERO) || d.equals(BigInteger.ZERO)) {
			throw new RuntimeException("Cannot decrypt encrypted message with public copy of RSA");
		}
		return c.modPow(d, n);
	}
}
