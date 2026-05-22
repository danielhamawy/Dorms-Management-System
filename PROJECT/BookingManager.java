
import util.FileHandler;

import java.time.LocalDate;
import java.util.*;

public class BookingManager implements Reservable {

    // In-memory list of rooms — loaded once from file at startup
    private final List<Room> rooms;

    public BookingManager() {
        this.rooms = FileHandler.loadRooms();
    }
    @Override
    public synchronized boolean bookRoom(String roomNumber,
                                         String studentId,
                                         String roommate) {
        Room target = findRoom(roomNumber);
        if (target == null || target.isBooked()) return false;

        // Mark as booked in memory
        target.book(studentId);

        // Persist room state
        FileHandler.saveRooms(rooms);

        // Persist booking record
        Booking booking = new Booking(
                studentId, roomNumber, roommate,
                target.getPrice(),
                LocalDate.now().toString());
        FileHandler.saveBooking(booking);

        return true;
    }

    @Override
    public synchronized boolean cancelBooking(String roomNumber,
                                              String studentId) {
        Room target = findRoom(roomNumber);
        if (target == null) return false;
        if (!studentId.equals(target.getBookedByStudentId())) return false;

        target.release();
        FileHandler.saveRooms(rooms);
        return true;
    }

    @Override
    public synchronized boolean isRoomAvailable(String roomNumber) {
        Room r = findRoom(roomNumber);
        return r != null && !r.isBooked();
    }

    public synchronized String getAllRoomsInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===== DORM ROOMS =====\n");
        for (Room r : rooms) {
            sb.append(r).append("\n");
        }
        sb.append("======================\n");
        return sb.toString();
    }

    public synchronized String getAvailableRoomsInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===== AVAILABLE ROOMS =====\n");
        rooms.stream()
                .filter(r -> !r.isBooked())
                .forEach(r -> sb.append(r).append("\n"));
        sb.append("===========================\n");
        return sb.toString();
    }

    public synchronized Room findRoom(String roomNumber) {
        return rooms.stream()
                .filter(r -> r.getRoomNumber().equals(roomNumber))
                .findFirst()
                .orElse(null);
    }
}
