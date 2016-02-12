package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Person;

public class PersonTableModel extends AbstractTableModel
{
    private static final int NUMBER_OF_COLUMNS = 8;
    private static final String[] colNames = { "ID", "Name", "Occupation", "Age Category", "Employement Category", "US Citizen", "Tax ID",
            "Gender" };
    
    private List<Person> db;
    
    @Override
    public String getColumnName(int column)
    {
        if (column < NUMBER_OF_COLUMNS)
        {
            return colNames[column];
        }
        else
        {
            // Otherwise, let the parent handle the request.
            return super.getColumnName(column);
        }
    }

    @Override
    public int getRowCount()
    {
        // Returns the number of person objects in the list, which is equal to the number of rows
        return db.size();
    }

    @Override
    public int getColumnCount()
    {
        return NUMBER_OF_COLUMNS;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Person person = db.get(rowIndex);

        switch (columnIndex)
        {
        case 0:
            return person.getId();
        case 1:
            return person.getName();
        case 2:
            return person.getOccupation();
        case 3:
            return person.getAgeCategory();
        case 4:
            return person.getEmployementCategory();
        case 5:
            return person.isUsCitizen();
        case 6:
            return person.getTaxId();
        case 7:
            return person.getGender();
        default:
            System.err.println("PersonTableModel.java: Invalid column index");
            break;
        }
        // No matches
        return null;
    }

    public void setData(List<Person> db)
    {
        this.db = db;
    }

}
