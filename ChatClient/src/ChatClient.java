import java.io.*;
import java.net.Socket;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 6666);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Enter your username: ");
            String username = bufferedReader.readLine();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ThreadClient threadClient = new ThreadClient(new ObjectInputStream(socket.getInputStream()), username);
            threadClient.start();


            while (true) {
                System.out.println("Type your message: ");
                String message = bufferedReader.readLine();

                System.out.println("Send To: ");
                String receiver = bufferedReader.readLine();

                Message messageObject = new Message();
                messageObject.setSender(username);
                messageObject.setText(message);
                messageObject.setReceiver(receiver);

                objectOutputStream.writeObject(messageObject);

                objectOutputStream.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
