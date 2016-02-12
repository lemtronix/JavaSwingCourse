package controller;

import gui.FormEvent;

import model.AgeCategory;
import model.Database;
import model.EmployementCategory;
import model.Gender;
import model.Person;

public class Controller
{
    private Database db = new Database();

    public void addPerson(FormEvent formContents)
    {
        db.add(new Person.Builder().name(formContents.getName()).occupation(formContents.getOccupation())
                .ageCategory(convertAgeCategoryId(formContents.getAgeCategory()))
                .employementCategory(convertEmployeeCategoryString(formContents.getEmployeeCategory())).taxId(formContents.getTaxId())
                .usCitizen(formContents.isUsCitizen()).gender(convertGenderCategoryString(formContents.getGender())).build());
    }

    private AgeCategory convertAgeCategoryId(int ageCatId)
    {
        AgeCategory ageCategory = AgeCategory.minor;

        switch (ageCatId)
        {
        case 0:
            ageCategory = AgeCategory.minor;
            break;
        case 1:
            ageCategory = AgeCategory.adult;
            break;
        case 2:
            ageCategory = AgeCategory.senior;
            break;
        }

        return ageCategory;
    }

    private EmployementCategory convertEmployeeCategoryString(String empCatString)
    {
        EmployementCategory empCat;

        if (empCatString.equals("Employed"))
        {
            empCat = EmployementCategory.Employed;
        } else if (empCatString.equals("Self-Employed"))
        {
            empCat = EmployementCategory.SelfEmployed;
        } else if (empCatString.equals("Unemployed"))
        {
            empCat = EmployementCategory.Unemployed;
        } else
        {
            // Error, the user entered an invalid input!
            empCat = EmployementCategory.Other;
            System.err.println(empCatString);
        }

        return empCat;
    }

    private Gender convertGenderCategoryString(String genCatString)
    {
        Gender gender;

        if (genCatString.equals("M"))
        {
            gender = Gender.Male;
        } else
        {
            gender = Gender.Female;
        }

        return gender;
    }
}
