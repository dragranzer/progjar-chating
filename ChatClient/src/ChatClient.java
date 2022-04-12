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

            Message messageObjectfirst = new Message();
            messageObjectfirst.setSender(username);
            objectOutputStream.writeObject(messageObjectfirst);
            objectOutputStream.flush();

            while (true) {
                System.out.println("Welcome to Whyapp!\n" +
                        "1. Chat\n" +
                        "2. See Online Users\n");
                String task = bufferedReader.readLine();
                switch (task){
                    case "1":{
                        System.out.println("Select which channel do you want to send message :\n" +
                                "1. All\n" +
                                "2. Private\n");
                        String choice = bufferedReader.readLine();

                        System.out.println("Type your message: ");
                        String message = bufferedReader.readLine();

                        System.out.println("Send To: ");
                        String receiver = bufferedReader.readLine();

                        Message messageObject = new Message();
                        messageObject.setSender(username);
                        messageObject.setType(choice);
                        messageObject.setText(message);
                        messageObject.setReceiver(receiver);

                        objectOutputStream.writeObject(messageObject);

                        objectOutputStream.flush();
                    }
                    case "2": {
                        System.out.println("OK");

                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
