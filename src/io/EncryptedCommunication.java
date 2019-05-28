package io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import encryption.EncryptedObjectInputStream;
import encryption.EncryptedObjectOutputStream;

public class EncryptedCommunication extends Communication {

	public EncryptedCommunication(InputStream in, OutputStream out) {
		try {
			this.objout = new EncryptedObjectOutputStream(out);
			this.objout.flush();
			this.objin = new EncryptedObjectInputStream(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public EncryptedCommunication(Socket sock) throws IOException {
		this(sock.getInputStream(), sock.getOutputStream());
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
