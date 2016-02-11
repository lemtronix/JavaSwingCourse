import java.util.EventObject;

public class FormEvent extends EventObject {

    private String name;
    private String occupation;
    private int ageCategory;
    private String employementCategory;
    private String taxId;
    private boolean usCitizen;

    public FormEvent(Object source) {
        super(source);
    }

    public FormEvent(Object source, String name, String occupation, int ageCategory, String employmentCategory, String taxId,
            boolean usCitizen) {
        super(source);

        this.name = name;
        this.occupation = occupation;
        this.setAgeCategory(ageCategory);
        this.employementCategory = employmentCategory;
        this.setTaxId(taxId);
        this.setUsCitizen(usCitizen);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(int ageCategory) {
        this.ageCategory = ageCategory;
    }

    public String getEmployeeCategory() {
        return employementCategory;
    }

    public void setEmployeeCategory(String employeeCategory) {
        this.employementCategory = employeeCategory;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public boolean isUsCitizen() {
        return usCitizen;
    }

    public void setUsCitizen(boolean usCitizen) {
        this.usCitizen = usCitizen;
    }
}
