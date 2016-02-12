package model;

public class Person
{

    private static int instanceCounter = 0;

    private int id;
    private String name;
    private String occupation;
    private AgeCategory ageCategory;
    private EmployementCategory employementCategory;
    private String taxId;
    private boolean usCitizen;
    private Gender gender;

    private Person(Builder builder)
    {
        this.name = builder.name;
        this.occupation = builder.occupation;
        this.ageCategory = builder.ageCategory;
        this.employementCategory = builder.employementCategory;
        this.taxId = builder.taxId;
        this.usCitizen = builder.usCitizen;
        this.gender = builder.gender;

        this.id = instanceCounter;
        instanceCounter++;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getOccupation()
    {
        return occupation;
    }

    public AgeCategory getAgeCategory()
    {
        return ageCategory;
    }

    public EmployementCategory getEmployementCategory()
    {
        return employementCategory;
    }

    public String getTaxId()
    {
        return taxId;
    }

    public boolean isUsCitizen()
    {
        return usCitizen;
    }

    public Gender getGender()
    {
        return gender;
    }

    public static class Builder
    {

        private String name;
        private String occupation;
        private AgeCategory ageCategory;
        private EmployementCategory employementCategory;
        private String taxId;
        private boolean usCitizen;
        private Gender gender;

        public Builder name(String value)
        {
            this.name = value;
            return this;
        }

        public Builder occupation(String value)
        {
            this.occupation = value;
            return this;
        }

        public Builder ageCategory(AgeCategory value)
        {
            this.ageCategory = value;
            return this;
        }

        public Builder employementCategory(EmployementCategory value)
        {
            this.employementCategory = value;
            return this;
        }

        public Builder taxId(String value)
        {
            this.taxId = value;
            return this;
        }

        public Builder usCitizen(boolean value)
        {
            this.usCitizen = value;
            return this;
        }

        public Builder gender(Gender value)
        {
            this.gender = value;
            return this;
        }

        public Person build()
        {
            return new Person(this);
        }
    }
}