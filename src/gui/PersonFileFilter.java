package gui;
import java.io.File;

import javax.swing.filechooser.FileFilter;

public class PersonFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        
        // Check for directories first
        if (f.isDirectory())
        {
            return true;
        }
        
        // Otherwise, it is a file, check the file extension
        String name = f.getName();
        String extension = Utils.getFileExtension(name);
        
        if (extension == null)
        {
            return false;
        }
        
        if (extension.equalsIgnoreCase("per"))
        {
            return true;
        }
        
        return false;
    }

    @Override
    public String getDescription() {
        return "Person Database Files (*.per)";
    }

}

class Utils {
    public static String getFileExtension(String name) {

        int pointIndex = name.lastIndexOf('.');
        int stringLength = name.length();

        // No decimal point found
        if (pointIndex == -1) {
            return null;
        }

        // Decimal found but is the last item in the string, NO GOOD!
        if (pointIndex == stringLength - 1) {
            return null;
        }

        return name.substring(pointIndex + 1, stringLength);
    }
}