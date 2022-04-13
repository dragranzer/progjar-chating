import java.io.IOException;
import java.io.ObjectInputStream;

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
//                System.out.println("List di thread client "+message.getListOnline());
                if (message.getRequest().equals("False")){
                    if(message.getType().equals("1")){
                        ch = "[ALL]";
                    }
                    else if(message.getType().equals("2")){
                        ch = "[Chat]";
                    }
                    System.out.println(message.getSender() + ch +" : " + message.getText());
                }else if(message.getRequest().equals("True")){

                    System.out.println("Online users : \n"+message.getText());
//                    System.out.println(message.getListOnline());
                }else{
                    System.out.println(message.getSender() + " : " + message.getText());
                    break;
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
