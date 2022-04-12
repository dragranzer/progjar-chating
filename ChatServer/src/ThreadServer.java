import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;

public class ThreadServer extends Thread {
    private Hashtable<String, ThreadClient> clientList;
    private Hashtable<String, String> clientNameList;
    private ServerSocket serverSocket;

    public ThreadServer() throws IOException {
        this.clientList = new Hashtable<String, ThreadClient>();
        this.clientNameList = new Hashtable<String, String>();
        this.serverSocket = new ServerSocket(6666);
    }

    @Override
    public void run() {
        // listen for a new connection
        while (true) {
            // accept a new connection
            try {
                Socket socket = this.serverSocket.accept();

                // create a new ThreadClient
                ThreadClient threadClient = new ThreadClient(socket, this);

                // start the new thread
                threadClient.start();

                // store the new thread to the hashtable
                String clientId = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
                this.clientList.put(clientId, threadClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendToAll(Message message) throws IOException {
        // iterate to all client
        Enumeration<String> e = this.clientList.keys();

        while (e.hasMoreElements()) {
            String clientId = e.nextElement();
            System.out.println(clientId);
            // send the message
            ThreadClient threadClient = this.clientList.get(clientId);
            threadClient.send(message);
        }
    }

    public void setNameID(Message message) throws IOException {
        Enumeration<String> e = this.clientList.keys();
        String nameClient = message.getSender();

        while (e.hasMoreElements()) {
            String clientId = e.nextElement();
            System.out.println(clientId);
            if (!clientNameList.containsKey(nameClient)){
                this.clientNameList.put(nameClient, clientId);
            }
        }
        System.out.println(clientNameList);

    }
}
