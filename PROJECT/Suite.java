
public class Suite extends Room {

    private static final long serialVersionUID = 4L;

    public Suite(String roomNumber) {
        super(roomNumber, 3);
    }

    @Override
    public double getPrice() { return 750.00; }

    @Override
    public String getRoomType() { return "Suite"; }
}
