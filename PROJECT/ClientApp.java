
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {

    private static final String HOST = "localhost";
    private static final int    PORT = 5000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Connecting to Dorm Management Server...");

        try (
                Socket         socket = new Socket(HOST, PORT);
                BufferedReader in     = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter    out    = new PrintWriter(
                        socket.getOutputStream(), true);
                Scanner        kb     = new Scanner(System.in)
        ) {
            // Print welcome + command list from server
            System.out.println(in.readLine());
            System.out.println(in.readLine());

            while (true) {
                System.out.print("\nEnter command: ");
                String userInput = kb.nextLine().trim();

                if (userInput.isEmpty()) continue;

                out.println(userInput);        // send to server

                // Read all response lines until we get a blank line
                // (server sends one logical response, possibly multi-line)
                Thread.sleep(200);

// Read only what's available right now
                while (in.ready()) {
                    System.out.println(in.readLine());
                }

                if (userInput.equalsIgnoreCase("EXIT")) break;
                System.out.println();

                if (userInput.equalsIgnoreCase("EXIT")) break;
            }

        } catch (IOException e) {
            System.err.println("Could not connect to server: " + e.getMessage());
            System.err.println("Make sure the server is running first!");
        }
    }
}
