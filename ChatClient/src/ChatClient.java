import java.io.*;
import java.net.Socket;

public class ChatClient {
    public static void main(String[] args) {
        boolean online = true;
        try {
            while (true) {
                Socket socket = new Socket("localhost", 6666);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

                System.out.println("Login");
                System.out.println("Enter your username: ");
                String username = bufferedReader.readLine();
                online = true;

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                ThreadClient threadClient = new ThreadClient(new ObjectInputStream(socket.getInputStream()));
                threadClient.start();

                Message messageObjectfirst = new Message();
                messageObjectfirst.setSender(username);
                messageObjectfirst.setRequest("False");
                messageObjectfirst.setStat("on");
                objectOutputStream.writeObject(messageObjectfirst);
                objectOutputStream.flush();

                do {
                    System.out.println("""
                            Welcome to Whyapp!
                            1. Chat
                            2. See Online Users
                            3. Logout
                            """);
                    String task = bufferedReader.readLine();
                    switch (task) {
                        case "1" -> {
                            System.out.println("""
                                    Select which channel do you want to send message :
                                    1. All
                                    2. Private
                                    """);
                            String choice = bufferedReader.readLine();

                            System.out.println("Type your message: ");
                            String message = bufferedReader.readLine();

                            String receiver;
                            if (choice.equals("2")) {
                                System.out.println("Send To: ");
                                receiver = bufferedReader.readLine();
                            } else {
                                receiver = "all";
                            }

                            Message messageObject = new Message();
                            messageObject.setSender(username);
                            messageObject.setType(choice);
                            messageObject.setText(message);
                            messageObject.setStat("on");
                            messageObject.setRequest("False");
                            messageObject.setReceiver(receiver);

                            objectOutputStream.writeObject(messageObject);
                            objectOutputStream.flush();
                        }
                        case "2" -> {
//                            System.out.println("OK");
                            Message messageObject = new Message();
                            messageObject.setSender(username);
                            messageObject.setStat("on");
                            messageObject.setReceiver(username);
                            messageObject.setRequest("True");

                            objectOutputStream.writeObject(messageObject);
                            objectOutputStream.flush();

                        }
                        case "3" -> {
                            Message messageObject = new Message();
                            messageObject.setSender(username);
                            messageObject.setStat("off");
                            messageObject.setRequest("logout");

                            objectOutputStream.writeObject(messageObject);
                            objectOutputStream.flush();

                            online = false;
                        }
                    }
                } while (online);

//                if(!online){
//                    objectOutputStream.close();
//                    socket.close();
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
