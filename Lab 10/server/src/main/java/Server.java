import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server class
 * Responsible with the creation of a ServerSocket
 */
public class Server {
    public final int port = 6666;

    public Server() throws IOException {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();

            System.out.println("A new client is connected: " + socket);

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            Thread thread = new ClientThread(socket, dataInputStream, dataOutputStream);
            thread.start();
        } catch (IOException exception) {
            System.out.println(exception);
        }
    }
}
