import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client class
 * Reads commands from the keyboard and sends them to the server
 */
public class Client {
    public static InetAddress ip;
    public static int port = 6666;
    public static Scanner scanner;
    public static DataInputStream dataInputStream;
    public static DataOutputStream dataOutputStream;

    public static void main(String[] args) throws IOException {
        try {
            ip = InetAddress.getByName("localhost");
            scanner = new Scanner(System.in);
            Socket socket = new Socket(ip, port);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            while (true) {
                System.out.println(dataInputStream.readUTF());
                String send = scanner.nextLine();
                dataOutputStream.writeUTF(send);

                if(send.equals("exit")) {
                    System.out.println("Closing this connection: " + socket);
                    socket.close();
                    System.out.println("Connection closed");
                    break;
                }

                String received = dataInputStream.readUTF();
                System.out.println(received);
            }

            scanner.close();
            dataInputStream.close();
            dataInputStream.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
}
