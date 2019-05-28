package encryption;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class EncryptedObjectInputStream extends ObjectInputStream {

	protected EncryptedObjectInputStream() throws IOException, SecurityException {
		super();
	}

	public EncryptedObjectInputStream(InputStream in) throws IOException {
		super(in);
	}

	public Object readEncryptedObject() throws IOException, ClassNotFoundException {
		byte[] objData = (byte[]) readObject();

		// TODO decrypt objData;

		ByteArrayInputStream bais = new ByteArrayInputStream(objData);
		ObjectInputStream ois = new ObjectInputStream(bais);

		Object obj = ois.readObject();

		bais.close();
		ois.close();

		return obj;
	}
}
