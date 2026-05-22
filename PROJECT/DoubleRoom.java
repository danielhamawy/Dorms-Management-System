
public class DoubleRoom extends Room {

    private static final long serialVersionUID = 3L;

    public DoubleRoom(String roomNumber) {
        super(roomNumber, 2);
    }

    @Override
    public double getPrice() { return 450.00; }

    @Override
    public String getRoomType() { return "Double Room"; }
}
