
public class SingleRoom extends Room {

    private static final long serialVersionUID = 2L;

    public SingleRoom(String roomNumber) {
        super(roomNumber, 1);
    }

    @Override
    public double getPrice() { return 300.00; }

    @Override
    public String getRoomType() { return "Single Room"; }
}
