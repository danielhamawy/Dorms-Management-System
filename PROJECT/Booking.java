

import java.io.Serializable;

public class Booking implements Serializable {

    private static final long serialVersionUID = 9L;

    private final String studentId;
    private final String roomNumber;
    private final String roommatePreference; // may be null
    private final double amountPaid;
    private final String bookingDate;

    public Booking(String studentId, String roomNumber,
                   String roommatePreference, double amountPaid,
                   String bookingDate) {
        this.studentId = studentId;
        this.roomNumber = roomNumber;
        this.roommatePreference = roommatePreference;
        this.amountPaid = amountPaid;
        this.bookingDate = bookingDate;
    }

    public String getStudentId() { return studentId; }
    public String getRoomNumber() { return roomNumber; }
    public String getRoommatePreference() { return roommatePreference; }
    public double getAmountPaid() { return amountPaid; }
    public String getBookingDate() { return bookingDate; }

    @Override
    public String toString() {
        return String.format("Booking[Student=%s, Room=%s, Roommate=%s, Paid=$%.2f, Date=%s]",
                studentId, roomNumber,
                roommatePreference != null ? roommatePreference : "None",
                amountPaid, bookingDate);
    }
}
