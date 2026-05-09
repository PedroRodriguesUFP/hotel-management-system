package hotel.model.quartos;

public class DoubleRoom extends Room {

    private boolean hasView;

    public DoubleRoom(int number, int floor, double pricePerNight, boolean hasView) {
        super(number, floor, pricePerNight);
        this.hasView = hasView;
    }

    public boolean isHasView() { return hasView; }
    public void setHasView(boolean hasView) { this.hasView = hasView; }

    @Override
    public String getRoomType() { return "Double"; }

    @Override
    public String toString() {
        return super.toString() + " | View: " + (hasView ? "Yes" : "No");
    }
}
