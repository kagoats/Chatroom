package encryption;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Random;

import data.tuple.Tuple2D;
import data.tuple.Tuple3D;

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

	private final BigInteger totientOfN;

	public RSA() {
		// Tuple2D<BigInteger, BigInteger> pq = generateTwoPrimes();
		p = BigInteger.probablePrime(256, new Random());
		q = BigInteger.probablePrime(256, new Random());

		n = p.multiply(q); // n = p*q
		
		totientOfN = getTotient(n, p, q);

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
		
		totientOfN = BigInteger.ZERO;
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
	 * public static Tuple2D<BigInteger, BigInteger> generateTwoPrimes() {
	 * BigInteger p = null, q = null; p = BigInteger.probablePrime(256, new
	 * Random()); p = BigInteger.probablePrime(256, new Random()); return new
	 * Tuple2D<BigInteger, BigInteger>(p, q); }
	 * 
	 * 
	 * public static BigInteger findN(BigInteger p, BigInteger q) { return
	 * p.multiply(q); }
	 */

	private static BigInteger getTotient(BigInteger n, BigInteger p, BigInteger q) {
		BigInteger pMinusOne = p.subtract(new BigInteger("1"));
		BigInteger qMinusOne = q.subtract(new BigInteger("1"));
		BigInteger lcm = pMinusOne.multiply(qMinusOne).divide(pMinusOne.gcd(qMinusOne)); // lcm of p - 1 and q - 1
		return lcm;
	}
	
	public static BigInteger modInv(BigInteger a, BigInteger mod) {
		return xgcd(a,mod).getB();
	}

	public static Tuple3D<BigInteger, BigInteger, BigInteger> xgcd(BigInteger a, BigInteger b) {
		//long[] retvals = { 0, 0, 0 };
		BigInteger ans1 = BigInteger.ZERO, ans2 = BigInteger.ZERO, ans3 = BigInteger.ZERO;

		BigInteger aa[] = { BigInteger.ONE, BigInteger.ZERO }, bb[] = { BigInteger.ZERO, BigInteger.ONE },
				q = BigInteger.ZERO;

		while (true) {
			q = a.divide(b);
			a = a.mod(b);
			//aa[0] = aa[0] - q * aa[1];
			aa[0]=aa[0].subtract(q.multiply(aa[1]));
			//bb[0] = bb[0] - q * bb[1];
			bb[0] = bb[0].subtract(q.multiply(bb[1]));
			if (a.equals(BigInteger.ZERO)) {
				//retvals[0] = b;
				ans1 = b;
				//retvals[1] = aa[1];
				ans2 = aa[1];
				//retvals[2] = bb[1];
				ans3 = bb[1];
				return new Tuple3D<>(ans1, ans2, ans3);
			}
			
			q=b.divide(a);
			b = b.mod(a);
			
			//aa[1] = aa[1] - q * aa[0];
			aa[1] = aa[1].subtract(q.multiply(aa[0]));
			//bb[1] = bb[1] - q * bb[0];
			bb[1] = bb[1].subtract(q.multiply(bb[0]));
			if (b.equals(BigInteger.ZERO)) {
				//retvals[0] = a;
				ans1 = a;
				//retvals[1] = aa[0];
				ans2 = aa[0];
				//retvals[2] = bb[0];
				ans3 = bb[0];
				return new Tuple3D<>(ans1, ans2, ans3);
			}
			
		}
	}

	public Tuple2D<BigInteger, BigInteger> generatePublicPrivateKeyExponent() {
		BigInteger ee = new BigInteger("65537");
		
		
		while(!BigInteger.ONE.equals(ee.gcd(totientOfN))) {
			int i = (int) (Math.random()*80000)+20000;
			ee = new BigInteger(i +"");
		}
		
		BigInteger dd = modInv(ee, totientOfN);
		
		return new Tuple2D<BigInteger, BigInteger>(ee, dd);
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
