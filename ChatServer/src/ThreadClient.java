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

                if(message.getRequest().equals("False")){
                    if(!(message.getReceiver() == null)) {
                        this.threadServer.sendToAClient(message, message.getReceiver(), message.getSender());
                    }
                }else if(message.getRequest().equals("True")){
                    this.threadServer.getOnlineUser(message);
                }

                if(message.getStat().equals("on")) {
                    this.threadServer.setNameID(message);
                }else{
                    this.threadServer.logout(message);
                    break;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
