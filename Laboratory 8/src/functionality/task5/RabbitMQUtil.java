package task5;

import java.io.Serializable;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public class RabbitMQUtil {
    public static byte[] serializeObject(Serializable object) throws IOException{
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(byteArrayOutputStream)){
            objectOutputStream.writeObject(object);
            return byteArrayOutputStream.toByteArray();
        }
    }
    public static DataObject deserializeObject(byte[] bytes) throws IOException,ClassNotFoundException{
        try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)){
            return (DataObject) objectInputStream.readObject();
        }
    }
}
