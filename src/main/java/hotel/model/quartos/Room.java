package hotel.model.quartos;

public abstract class Room {

    private int number;
    private int floor;
    private double pricePerNight;
    private boolean available;

    public Room(int number, int floor, double pricePerNight) {
        this.number = number;
        this.floor = floor;
        this.pricePerNight = pricePerNight;
        this.available = true;
    }

    public int getNumber() { return number; }
    public int getFloor() { return floor; }
    public double getPricePerNight() { return pricePerNight; }
    public boolean isAvailable() { return available; }

    public void setPricePerNight(double pricePerNight) { this.pricePerNight = pricePerNight; }
    public void setAvailable(boolean available) { this.available = available; }

    public abstract String getRoomType();

    @Override
    public String toString() {
        return "Room " + number + " | Floor: " + floor +
                " | Type: " + getRoomType() +
                " | Price/Night: " + pricePerNight + "€" +
                " | Available: " + (available ? "Yes" : "No");
    }
}
