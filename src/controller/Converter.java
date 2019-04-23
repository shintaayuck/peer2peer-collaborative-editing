package controller;

import java.io.*;
import java.nio.ByteBuffer;

public class Converter {
    /* Serialize the object to byte array */
    public static byte[] getByteArray(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream os = new ObjectOutputStream(bos)) {
            os.writeObject(obj);
        }
        return bos.toByteArray();
    }

    public static byte[] getByteArray(ByteBuffer buffer) {
        byte[] b = new byte[buffer.remaining()];
        buffer.get(b);
        return b;
    }

    /* De serialize the byte array to object */
    public static Object getObject(byte[] byteArr) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(byteArr);
        ObjectInput in = new ObjectInputStream(bis);
        return in.readObject();
    }

    public static Object getObject(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        byte[] b = getByteArray(buffer);
        return getObject(b);
    }


}
