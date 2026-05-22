
import enums.MaintenancePriority;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MaintenanceRequest implements Serializable {

    private static final long serialVersionUID = 8L;

    private final String            studentId;
    private final String            roomNumber;
    private final String            description;
    private final MaintenancePriority priority;
    private final String            timestamp;
    private       boolean           resolved;

    public MaintenanceRequest(String studentId,
                              String roomNumber,
                              String description,
                              MaintenancePriority priority) {
        this.studentId = studentId;
        this.roomNumber = roomNumber;
        this.description = description;
        this.priority = priority;
        this.resolved = false;
        this.timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public void resolve() { this.resolved = true; }

    public String getStudentId()  { return studentId; }
    public String getRoomNumber() { return roomNumber; }
    public String getDescription(){ return description; }
    public MaintenancePriority getPriority()  { return priority; }
    public boolean isResolved() { return resolved; }
    public String getTimestamp()  { return timestamp; }

    @Override
    public String toString() {
        return String.format("[%s] Room %s | %s | %s | Resolved: %s",
                timestamp, roomNumber, priority.getLabel(),
                description, resolved ? "Yes" : "No");
    }
}
