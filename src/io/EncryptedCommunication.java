package io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import encryption.EncryptedObjectInputStream;
import encryption.EncryptedObjectOutputStream;
import encryption.RSA;

public class EncryptedCommunication extends Communication {
	private RSA myRSA;
	private RSA otherRSA;

	EncryptedObjectOutputStream encryptObjout;
	EncryptedObjectInputStream encryptObjin;

	public EncryptedCommunication(RSA myRSA, RSA otherRSA, InputStream in, OutputStream out) {
		this.myRSA = myRSA;
		this.otherRSA = otherRSA;
		try {
			this.encryptObjout = new EncryptedObjectOutputStream(this.otherRSA, out);
			this.encryptObjout.flush();
			this.encryptObjin = new EncryptedObjectInputStream(this.myRSA, in);

			objout = encryptObjout;
			objin = encryptObjin;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public EncryptedCommunication(RSA myRSA, RSA otherRSA, Socket sock) throws IOException {
		this(myRSA, otherRSA, sock.getInputStream(), sock.getOutputStream());
	}

	public void setMyRSA(RSA myRSA) {
		this.myRSA = myRSA;
		encryptObjin.setMyRSA(this.myRSA);
	}

	public void setOtherRSA(RSA otherRSA) {
		this.otherRSA = otherRSA;
		encryptObjout.setOtherRSA(this.otherRSA);
	}

	public void sendEncryptedObject(Object obj) {
		this.sendEncryptedObject(obj, true);
	}

	public void sendEncryptedObject(Object obj, boolean flush) {
		try {
			((EncryptedObjectOutputStream) objout).writeEncryptedObject(obj);
			if (flush) {
				this.objout.flush();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Object recieveEncryptedObject() {
		try {
			return ((EncryptedObjectInputStream) objin).readEncryptedObject();
		} catch (ClassNotFoundException | IOException e) {
			throw new RuntimeException(e);
		}
	}

}
