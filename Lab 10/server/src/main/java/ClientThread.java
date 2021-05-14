import exceptions.InvalidCommand;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * ClientThread class
 */
public class ClientThread extends Thread {
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    public ClientThread(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        this.socket = socket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public void run() {
        String received;

        while (true) {
            try {
                dataOutputStream.writeUTF("Please enter a command (register/login/friend/send/read/stop).");

                received = dataInputStream.readUTF();
                String[] commands = received.split(" ");
                int k = commands.length;
                if (commands[0].equals("stop")) {
                    System.out.println("Closing this connection.");
                    this.socket.close();
                    System.out.println("Connection closed");
                    break;
                }

                switch (commands[0]) {
                    case "register":
                        System.out.println("Client wants to: " + commands[0]);
                        dataOutputStream.writeUTF("Your name is: " + commands[1]);
                        dataOutputStream.flush();
                        break;
                    case "login":
                        System.out.println("Client wants to: " + commands[0]);
                        dataOutputStream.writeUTF("You are logged in as: " + commands[1]);
                        dataOutputStream.flush();
                        break;
                    case "friend":
                        for (int j = 1; j < k; j++) {
                            System.out.println("Client wants to become friends with: " + commands[j]);
                            dataOutputStream.writeUTF("You are now friends with: " + commands[j]);
                        }
                        dataOutputStream.flush();
                        break;
                    case "send":
                        System.out.println("Client wants to send a message");
                        dataOutputStream.writeUTF("Your message has been sent");
                        dataOutputStream.flush();
                        break;
                    case "read":
                        System.out.println("Client wants to read his messages");
                        dataOutputStream.writeUTF("These are your messages:");
                        dataOutputStream.flush();
                        break;
                    default:
                        throw new InvalidCommand("This is not a valid command!");
                }
            } catch (Exception exception) {
                System.out.println(exception);
            }
        }

        try {
            this.dataInputStream.close();
            this.dataOutputStream.close();

        } catch (IOException exception) {
            System.out.println(exception);
        }
    }
}
