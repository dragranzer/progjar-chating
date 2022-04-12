import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadClient extends Thread {
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private ThreadServer threadServer;

    public ThreadClient(Socket socket, ThreadServer threadServer) {
        try {
            this.socket = socket;
            this.objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            this.threadServer = threadServer;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Message message) throws IOException {
        this.objectOutputStream.writeObject(message);
        this.objectOutputStream.flush();
    }

    @Override
    public void run() {
        while (true) {
            // jalan tiap kali chat client di enter
            try {
                Message message = (Message) this.objectInputStream.readObject();
//                System.out.println("jalan");

                //append list id and name
//                System.out.println(message.getSender());
                // send this message to other clients
//                this.threadServer.sendToAll(message);
                System.out.println("Request dalam client di ser "+message.getRequest()+" "+message.getSender());
                if(message.getRequest().equals("False")){
                    if(!(message.getReceiver() == null)) {
                        this.threadServer.sendToAClient(message, message.getReceiver(), message.getSender());
                    }

                }else{
                    System.out.println("Masuk else "+message.getSender());

                    this.threadServer.getOnlineUser(message);
                }
                this.threadServer.setNameID(message);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
