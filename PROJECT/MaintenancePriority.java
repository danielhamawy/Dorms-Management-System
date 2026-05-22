
public enum MaintenancePriority {
    LOW,
    MEDIUM,
    HIGH;

    public String getLabel() {
        return switch (this) {
            case LOW    -> "Low Priority";
            case MEDIUM -> "Medium Priority";
            case HIGH   -> "High Priority — Urgent!";
        };
    }
}