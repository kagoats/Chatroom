package main;

import java.math.BigInteger;

import encryption.RSA;

public class Testing {

	public static void main(String[] args) {
		RSA privatersa = new RSA();
		
		RSA publicrsa = privatersa.getPublicCopy();
		

		BigInteger val = new BigInteger("20");

		BigInteger encrypt = publicrsa.encrypt(val);

		System.out.println(encrypt);
		System.out.println(encrypt.bitCount());

		BigInteger decrypt = privatersa.decrypt(encrypt);

		System.out.println(decrypt);
		

		if(decrypt.equals(val)) {
			System.out.println("SUCCESS");
		}
		else {
			System.out.println("FAILURE");
		}

	}
}
