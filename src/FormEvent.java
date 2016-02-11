import java.util.EventObject;

public class FormEvent extends EventObject {

	private String name;
	private String occupation;
	private int ageCategory;
	private String employementCategory;
	
	public FormEvent(Object source) {
		super(source);
	}
	
	public FormEvent(Object source, String name, String occupation, int ageCategory, String employmentCategory) {
		super(source);
		
		this.name = name;
		this.occupation = occupation;
		this.setAgeCategory(ageCategory);
		this.employementCategory = employmentCategory;
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

	public String toString() {
		return "Name: " + name + "\n" + "Occupation: " + occupation + "\n";
	}
}