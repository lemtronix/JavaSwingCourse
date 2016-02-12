package gui;
import java.util.EventObject;

public class FormEvent extends EventObject {

    private String name;
    private String occupation;
    private int ageCategory;
    private String employementCategory;
    private String taxId;
    private boolean usCitizen;
    private String gender;

    public FormEvent(Object source) {
        super(source);
    }

    public FormEvent(Object source, String name, String occupation, int ageCategory, String employmentCategory, String taxId,
            boolean usCitizen, String gender) {
        super(source);

        this.name = name;
        this.occupation = occupation;
        this.ageCategory = ageCategory;
        this.employementCategory = employmentCategory;
        this.taxId = taxId;
        this.usCitizen = usCitizen;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getOccupation() {
        return occupation;
    }

    public int getAgeCategory() {
        return ageCategory;
    }

    public String getEmployeeCategory() {
        return employementCategory;
    }

    public String getTaxId() {
        return taxId;
    }

    public boolean isUsCitizen() {
        return usCitizen;
    }

    public String getGender() {
        return gender;
    }

}
