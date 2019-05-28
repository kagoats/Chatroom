package encryption;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class EncryptedObjectOutputStream extends ObjectOutputStream {

	protected EncryptedObjectOutputStream() throws IOException, SecurityException {
		super();
	}

	public EncryptedObjectOutputStream(OutputStream out) throws IOException {
		super(out);
	}

	public void writeEncryptedObject(Object obj) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		oos.flush();
		byte[] objData = baos.toByteArray();
		baos.close();
		oos.close();

		// objData is byte serialization of obj

		// TODO encrypt objData;

		writeObject(objData);

	}

}
