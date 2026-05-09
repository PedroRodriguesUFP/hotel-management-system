package hotel.model.pessoas;

public abstract class Employee extends Person {

    private String employeeNumber;
    private double salary;

    public Employee(String name, String email, String phone,
                    String employeeNumber, double salary) {
        super(name, email, phone);
        this.employeeNumber = employeeNumber;
        this.salary = salary;
    }

    public String getEmployeeNumber() { return employeeNumber; }
    public double getSalary() { return salary; }

    public void setEmployeeNumber(String employeeNumber) { this.employeeNumber = employeeNumber; }
    public void setSalary(double salary) { this.salary = salary; }

    @Override
    public String toString() {
        return super.toString() + " | No.: " + employeeNumber + " | Salary: " + salary + "€";
    }
}
