package hotel.model.pessoas;

public class Housekeeping extends Employee {

    private String floor;

    public Housekeeping(String name, String email, String phone,
                        String employeeNumber, double salary, String floor) {
        super(name, email, phone, employeeNumber, salary);
        this.floor = floor;
    }

    public String getFloor() { return floor; }
    public void setFloor(String floor) { this.floor = floor; }

    @Override
    public String toString() {
        return super.toString() + " | Floor responsible: " + floor;
    }
}
