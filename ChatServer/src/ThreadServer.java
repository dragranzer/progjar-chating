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
//                this.join(3000);
//                System.out.println("Finish");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendToAll(Message message) throws IOException {
        // iterate to all client
        Enumeration<String> e = this.clientList.keys();
        System.out.println(message.getSender() + " : " + message.getText());

        while (e.hasMoreElements()) {
            String clientId = e.nextElement();
//            System.out.println(clientId);
            // send the message
            ThreadClient threadClient = this.clientList.get(clientId);
            threadClient.send(message);
        }
    }

    public void sendToAClient(Message message, String dst) throws IOException {
        if(clientNameList.containsKey(dst)) {
            String clientId = clientNameList.get(dst);
            ThreadClient threadClient = this.clientList.get(clientId);
            threadClient.send(message);
        }else if(dst.equalsIgnoreCase("All")){
            sendToAll(message);
        }
        else{
            System.out.println("User Tidak Ada");
        }
    }

    public void setNameID(Message message) throws IOException {
//        System.out.println("GET CLIENT LIST");
        Enumeration<String> e = this.clientList.keys();
        String nameClient = message.getSender();

        while (e.hasMoreElements()) {
            String clientId = e.nextElement();
//            System.out.println("SETNAMEID: " + clientId);
//            System.out.println(clientId);
            if (!clientNameList.containsValue(clientId)){
                this.clientNameList.put(nameClient, clientId);
            }
        }
//        if (!clientNameList.containsValue(nameClient){
//                this.clientNameList.put(nameClient, clientId);
//        }
        System.out.println(clientNameList);
    }
}
