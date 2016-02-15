package gui;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class PrefsDialog extends JDialog
{
    private static final boolean MODALITY = false;   // Model means carry on working, non-model does not allow you to do work in the GUI
    
    public PrefsDialog(JFrame parent)
    {
        super(parent, "Preferences", MODALITY);
        
        setSize(400, 300);
    }
}
