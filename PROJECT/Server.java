
import model.BookingManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 5000;

    public static void main(String[] args) {
        // One shared BookingManager — all threads share the same room list
        BookingManager bookingManager = new BookingManager();

        System.out.println("=== Dorm Management Server Started on port " + PORT + " ===");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {   // keep accepting connections forever
                Socket clientSocket = serverSocket.accept();
                System.out.println("[Server] New connection from: "
                        + clientSocket.getInetAddress());

                // Hand off to a dedicated thread
                ClientHandler handler =
                        new ClientHandler(clientSocket, bookingManager);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            System.err.println("[Server] Fatal error: " + e.getMessage());
        }
    }
}