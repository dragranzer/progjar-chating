import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void main(String[] args) {
        try {
            ThreadServer threadServer = new ThreadServer();
            threadServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
