package hotel.model.pessoas;

public class Receptionist extends Employee {

    private String shift;

    public Receptionist(String name, String email, String phone,
                        String employeeNumber, double salary, String shift) {
        super(name, email, phone, employeeNumber, salary);
        this.shift = shift;
    }

    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }

    @Override
    public String toString() {
        return super.toString() + " | Shift: " + shift;
    }
}
