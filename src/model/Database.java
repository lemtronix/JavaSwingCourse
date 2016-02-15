package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Database
{
    private List<Person> people;

    public Database()
    {
        // ArrayLists are optimized for adding and removing items at the end of the list whereas LinkedLists are optimized for adding/removing
        // items anywhere in the list.
        people = new LinkedList<Person>();
    }

    public void add(Person person)
    {
        people.add(person);
    }

    public void remove(int index)
    {
        people.remove(index);
    }
    
    public List<Person> getPeople()
    {
        // Prevent the list from being modified by other modules
        return Collections.unmodifiableList(people);
    }
    
    public void saveToFile(File f) throws IOException
    {
        // Wrap the file in a file output stream
        FileOutputStream fos = new FileOutputStream(f);
        
        // Then, wrap the file output stream in an object output stream
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        
        // To avoid warnings of unchecked types...Convert the ArrayList of people into a simple array.
        // This avoids erasure of the Person type when saving the ArrayList<Person> directly to a file.. (see Pluralsight: Mastering Java Swing Part 2 - Serialization - Saving Objects to files 4m50s)
        Person[] personArray = people.toArray(new Person[people.size()]);
        
        oos.writeObject(personArray);
        oos.close();
    }
    
    public void loadFromFile(File f) throws IOException
    {
        FileInputStream fis = new FileInputStream(f);
        ObjectInputStream ois = new ObjectInputStream(fis);
        
        try
        {
            // An array in java does not suffer from erasure
            Person[] personArray = (Person[])ois.readObject();
            
            // Empty the current ArrayList since we're putting new values in 
            people.clear();
            
            // Covert the fixed array[] into a list and add it to the ArrayList
            people.addAll(Arrays.asList(personArray));
        }
        catch (ClassNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ois.close();
    }
}
