
import java.io.Serializable;

public abstract class Room implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String roomNumber;
    private final int capacity;        // max occupants
    private boolean isBooked;
    private String bookedByStudentId;

    public Room(String roomNumber, int capacity) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.isBooked = false;
        this.bookedByStudentId = null;
    }
    public abstract double getPrice();
    public abstract String getRoomType();

    /** Marks this room as booked by the given student. */
    public void book(String studentId) {
        this.isBooked = true;
        this.bookedByStudentId = studentId;
    }

    /** Releases the room back to available status. */
    public void release() {
        this.isBooked = false;
        this.bookedByStudentId = null;
    }

    public String  getRoomNumber() { return roomNumber; }
    public int     getCapacity() { return capacity; }
    public boolean isBooked() { return isBooked; }
    public String  getBookedByStudentId() { return bookedByStudentId; }

    @Override
    public String toString() {
        return String.format("[%s] %s | Capacity: %d | Price: $%.2f/month | %s",
                roomNumber,
                getRoomType(),
                capacity,
                getPrice(),
                isBooked ? "BOOKED by " + bookedByStudentId : "AVAILABLE");
    }
}
