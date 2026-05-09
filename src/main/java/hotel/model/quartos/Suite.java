package hotel.model.quartos;

public class Suite extends Room {

    private int numberOfRooms;
    private boolean hasJacuzzi;

    public Suite(int number, int floor, double pricePerNight,
                 int numberOfRooms, boolean hasJacuzzi) {
        super(number, floor, pricePerNight);
        this.numberOfRooms = numberOfRooms;
        this.hasJacuzzi = hasJacuzzi;
    }

    public int getNumberOfRooms() { return numberOfRooms; }
    public boolean isHasJacuzzi() { return hasJacuzzi; }

    @Override
    public String getRoomType() { return "Suite"; }

    @Override
    public String toString() {
        return super.toString() + " | Rooms: " + numberOfRooms +
                " | Jacuzzi: " + (hasJacuzzi ? "Yes" : "No");
    }
}
