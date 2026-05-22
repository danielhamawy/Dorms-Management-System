
import enums.MaintenancePriority;
import model.*;
import util.FileHandler;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket         socket;
    private final BookingManager bookingManager;

    public ClientHandler(Socket socket, BookingManager bookingManager) {
        this.socket         = socket;
        this.bookingManager = bookingManager;
    }

    @Override
    public void run() {
        // try-with-resources closes streams automatically
        try (
                BufferedReader  in  = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter     out = new PrintWriter(
                        socket.getOutputStream(), true)
        ) {
            out.println("Welcome to the Dorm Management System!");
            out.println("Commands: VIEW_ALL | VIEW_AVAILABLE | " +
                    "BOOK <room> <studentId> [roommate] | " +
                    "CANCEL <room> <studentId> | " +
                    "MAINTENANCE <room> <studentId> <priority> <description...> | EXIT");

            String line;
            while ((line = in.readLine()) != null) {
                String response = handleCommand(line.trim());
                out.println(response);
                if (line.trim().equalsIgnoreCase("EXIT")) break;
            }
        } catch (IOException e) {
            System.err.println("[ClientHandler] Connection error: " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
            System.out.println("[Server] Client disconnected.");
        }
    }

    private String handleCommand(String command) {
        if (command == null || command.isEmpty()) return "Empty command.";

        String[] parts = command.split(" ", 5);
        String   cmd   = parts[0].toUpperCase();

        return switch (cmd) {

            case "VIEW_ALL" ->
                    bookingManager.getAllRoomsInfo();

            case "VIEW_AVAILABLE" ->
                    bookingManager.getAvailableRoomsInfo();

            case "BOOK" -> {
                if (parts.length < 3)
                    yield "Usage: BOOK <roomNumber> <studentId> [roommatePreference]";
                String roomNum   = parts[1];
                String studentId = parts[2];
                String roommate  = parts.length >= 4 ? parts[3] : null;

                // Check room exists first
                Room target = bookingManager.findRoom(roomNum);
                if (target == null) yield "Room " + roomNum + " does not exist.";

                boolean success = bookingManager.bookRoom(roomNum, studentId, roommate);
                if (success) {
                    yield String.format(
                            "SUCCESS: Room %s booked for student %s. Fee: $%.2f/month",
                            roomNum, studentId, target.getPrice());
                } else {
                    yield "FAILED: Room " + roomNum + " is already booked or unavailable.";
                }
            }

            case "CANCEL" -> {
                if (parts.length < 3)
                    yield "Usage: CANCEL <roomNumber> <studentId>";
                boolean ok = bookingManager.cancelBooking(parts[1], parts[2]);
                yield ok ? "Booking cancelled successfully."
                        : "FAILED: Could not cancel. Check room number and student ID.";
            }

            case "MAINTENANCE" -> {
                // Format: MAINTENANCE <room> <studentId> <LOW|MEDIUM|HIGH> <description>
                if (parts.length < 5)
                    yield "Usage: MAINTENANCE <room> <studentId> <LOW|MEDIUM|HIGH> <description>";
                try {
                    MaintenancePriority priority =
                            MaintenancePriority.valueOf(parts[3].toUpperCase());
                    MaintenanceRequest req = new MaintenanceRequest(
                            parts[2], parts[1], parts[4], priority);
                    FileHandler.saveMaintenanceRequest(req);
                    yield "Maintenance request logged: " + priority.getLabel();
                } catch (IllegalArgumentException e) {
                    yield "Invalid priority. Use: LOW, MEDIUM, or HIGH";
                }
            }

            case "EXIT" -> "Goodbye!";

            default -> "Unknown command: " + cmd;
        };
    }
}
