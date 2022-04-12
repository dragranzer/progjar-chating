import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ThreadClient extends Thread {
    private ObjectInputStream objectInputStream;
    private String NameClient;

    public ThreadClient(ObjectInputStream objectInputStream, String NameClient) {
        this.objectInputStream = objectInputStream;
        this.NameClient = NameClient;
    }

    @Override
    public void run() {
        while (true) {
            Message message = null;
            System.out.println("jalan2");
            try {
                message = (Message) this.objectInputStream.readObject();
                System.out.println(message.getSender() + " : " + message.getText());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
