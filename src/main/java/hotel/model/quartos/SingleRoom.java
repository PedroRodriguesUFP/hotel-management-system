package hotel.model.quartos;

public class SingleRoom extends Room {

    private boolean hasBalcony;

    public SingleRoom(int number, int floor, double pricePerNight, boolean hasBalcony) {
        super(number, floor, pricePerNight);
        this.hasBalcony = hasBalcony;
    }

    public boolean isHasBalcony() { return hasBalcony; }
    public void setHasBalcony(boolean hasBalcony) { this.hasBalcony = hasBalcony; }

    @Override
    public String getRoomType() { return "Single"; }

    @Override
    public String toString() {
        return super.toString() + " | Balcony: " + (hasBalcony ? "Yes" : "No");
    }
}
