import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ThreadClient extends Thread {
    private ObjectInputStream objectInputStream;

    public ThreadClient(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    @Override
    public void run() {
        while (true) {
            Message message;
            String ch = "";
            try {
                message = (Message) this.objectInputStream.readObject();
                System.out.println(message.getRequest());
                if (message.getRequest()=="False"){
                    if(message.getType().equals("1")){
                        ch = "[ALL]";
                    }
                    else if(message.getType().equals("2")){
                        ch = "[Chat]";
                    }
                    System.out.println(message.getSender() + ch +" : " + message.getText());
                }else{
                    System.out.println(message.getListOnline());
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
