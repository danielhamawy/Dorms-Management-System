
public interface Reservable {
    /* Attempt to book a room for a student */
    boolean bookRoom(String roomNumber, String studentId, String roommate);
    /* Cancel an existing booking */
    boolean cancelBooking(String roomNumber, String studentId);
    /*  Check whether a specific room is currently available */
    boolean isRoomAvailable(String roomNumber);
}
