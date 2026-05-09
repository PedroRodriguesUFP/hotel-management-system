package hotel.model.pessoas;

public class Customer extends Person {

    private String taxId;
    private String nationality;

    public Customer(String name, String email, String phone,
                    String taxId, String nationality) {
        super(name, email, phone);
        this.taxId = taxId;
        this.nationality = nationality;
    }

    public String getTaxId() { return taxId; }
    public String getNationality() { return nationality; }

    public void setTaxId(String taxId) { this.taxId = taxId; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    @Override
    public String toString() {
        return super.toString() + " | Tax ID: " + taxId + " | Nationality: " + nationality;
    }
}
