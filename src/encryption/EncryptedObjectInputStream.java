package encryption;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class EncryptedObjectInputStream extends ObjectInputStream {
	
	private RSA myRSA;

	public EncryptedObjectInputStream(RSA myRSA, InputStream in) throws IOException {
		super(in);
		this.myRSA = myRSA;
	}
	
	public void setMyRSA(RSA myRSA) {
		this.myRSA = myRSA;
	}

	public Object readEncryptedObject() throws IOException, ClassNotFoundException {
		byte[] objData = (byte[]) readObject();
		
		objData = myRSA.decrypt(objData);

		ByteArrayInputStream bais = new ByteArrayInputStream(objData);
		ObjectInputStream ois = new ObjectInputStream(bais);

		Object obj = ois.readObject();

		bais.close();
		ois.close();

		return obj;
	}
}
