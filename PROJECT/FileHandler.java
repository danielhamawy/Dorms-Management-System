
import model.*;
import enums.MaintenancePriority;

import java.io.*;
import java.util.*;

public class FileHandler {

    private static final String ROOMS_FILE    = "rooms.txt";
    private static final String BOOKINGS_FILE = "bookings.txt";
    private static final String MAINTENANCE_FILE = "maintenance.txt";

    public static synchronized void saveRooms(List<Room> rooms) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ROOMS_FILE))) {
            for (Room r : rooms) {
                pw.printf("%s|%s|%b|%s%n",
                        r.getRoomNumber(),
                        r.getRoomType(),
                        r.isBooked(),
                        r.getBookedByStudentId() != null
                                ? r.getBookedByStudentId() : "null");
            }
        } catch (IOException e) {
            System.err.println("[FileHandler] Error saving rooms: " + e.getMessage());
        }
    }

    public static List<Room> loadRooms() {
        File f = new File(ROOMS_FILE);
        if (!f.exists()) return createDefaultRooms();

        List<Room> rooms = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 4) continue;

                String  num      = parts[0];
                String  type     = parts[1];
                boolean booked   = Boolean.parseBoolean(parts[2]);
                String  bookedBy = parts[3].equals("null") ? null : parts[3];

                Room room = switch (type) {
                    case "Double Room" -> new model.DoubleRoom(num);
                    case "Suite"       -> new model.Suite(num);
                    default            -> new model.SingleRoom(num);
                };

                if (booked && bookedBy != null) room.book(bookedBy);
                rooms.add(room);
            }
        } catch (IOException e) {
            System.err.println("[FileHandler] Error loading rooms: " + e.getMessage());
        }
        return rooms;
    }

    public static synchronized void saveBooking(Booking booking) {
        try (PrintWriter pw = new PrintWriter(
                new FileWriter(BOOKINGS_FILE, true))) {  // true = append
            pw.printf("%s|%s|%s|%.2f|%s%n",
                    booking.getStudentId(),
                    booking.getRoomNumber(),
                    booking.getRoommatePreference() != null
                            ? booking.getRoommatePreference() : "null",
                    booking.getAmountPaid(),
                    booking.getBookingDate());
        } catch (IOException e) {
            System.err.println("[FileHandler] Error saving booking: " + e.getMessage());
        }
    }

    public static List<Booking> loadBookings() {
        List<Booking> bookings = new ArrayList<>();
        File f = new File(BOOKINGS_FILE);
        if (!f.exists()) return bookings;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length < 5) continue;
                bookings.add(new Booking(
                        p[0], p[1],
                        p[2].equals("null") ? null : p[2],
                        Double.parseDouble(p[3]), p[4]));
            }
        } catch (IOException e) {
            System.err.println("[FileHandler] Error loading bookings: " + e.getMessage());
        }
        return bookings;
    }

    public static synchronized void saveMaintenanceRequest(MaintenanceRequest req) {
        try (PrintWriter pw = new PrintWriter(
                new FileWriter(MAINTENANCE_FILE, true))) {
            pw.printf("%s|%s|%s|%s|%b%n",
                    req.getStudentId(), req.getRoomNumber(),
                    req.getDescription(), req.getPriority().name(),
                    req.isResolved());
        } catch (IOException e) {
            System.err.println("[FileHandler] Error saving maintenance: " + e.getMessage());
        }
    }

    private static List<Room> createDefaultRooms() {
        List<Room> defaults = new ArrayList<>();
        // Single rooms: 101–105
        for (int i = 101; i <= 105; i++)
            defaults.add(new model.SingleRoom(String.valueOf(i)));
        // Double rooms: 201–203
        for (int i = 201; i <= 203; i++)
            defaults.add(new model.DoubleRoom(String.valueOf(i)));
        // Suites: 301–302
        defaults.add(new model.Suite("301"));
        defaults.add(new model.Suite("302"));
        saveRooms(defaults);   // persist them immediately
        return defaults;
    }
}
