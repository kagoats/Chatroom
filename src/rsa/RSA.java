package rsa;

import java.math.BigInteger;

import data.tuple.Tuple2D;

public class RSA {
	
	//PUBLIC KEY
	
	// n is the modulus for the public key and the private keys
	private final BigInteger n;
	// e is the public key exponent
	private final BigInteger e;
	
	
	//PRIVATE KEY
	
	// p and q are random very large primes
	private final BigInteger p;
	private final BigInteger q;
	// d is the private key exponent
	private final BigInteger d;
	
	public RSA() {
		Tuple2D<BigInteger, BigInteger> pq = generateTwoPrimes();
		p = pq.getA();
		q = pq.getB();
		
		n = findN(p, q);
		
		Tuple2D<BigInteger, BigInteger> ed = generatePublicPrivateKeyExponent();
		e = ed.getA();
		d = ed.getB();
	}
	public BigInteger encrypt(BigInteger m) {
		return encrypt(m,n,e);
	}
	public BigInteger decrypt(BigInteger c) {
		return decrypt(c,n,d);
	}
	
	public static Tuple2D<BigInteger, BigInteger>generateTwoPrimes() {
		return new Tuple2D<BigInteger, BigInteger>(null, null);
	}
	public static BigInteger findN(BigInteger p, BigInteger q) {
		return p.multiply(q);
	}

	public static Tuple2D<BigInteger, BigInteger> generatePublicPrivateKeyExponent() {
		return new Tuple2D<BigInteger, BigInteger>(null, null);
	}
	
	public static BigInteger encrypt(BigInteger m, BigInteger n, BigInteger e) {
		return null;
	}
	public static BigInteger decrypt(BigInteger c, BigInteger n, BigInteger d) {
		return null;
	}
}
