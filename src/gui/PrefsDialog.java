/*
 * This is the Window > Preferences dialog box that pops up in the application
 */

package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class PrefsDialog extends JDialog
{
    private static final boolean MODALITY = false;   // Model means carry on working, non-model does not allow you to do work in the GUI
    private static final int DIALOG_WIDTH = 200;
    private static final int DIALOG_HEIGHT = 200;
    
    private static final int JFIELD_LENGTH = 10;
    
    private static final int SQL_PORT_DEFAULT = 3306;
    private static final int SQL_PORT_MIN = 0;
    private static final int SQL_PORT_MAX = 9999;
    private static final int SQL_PORT_INCREMENT = 1;
    
    private JButton okButton;
    private JButton cancelButton;
    private JSpinner portSpinner;
    private SpinnerNumberModel spinnerModel;
    private JTextField userField;
    private JPasswordField passField;
    
    private PrefsListener prefsListener;
    
    public PrefsDialog(JFrame parent)
    {
        super(parent, "Preferences", MODALITY);
        
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        
        spinnerModel = new SpinnerNumberModel(SQL_PORT_DEFAULT, SQL_PORT_MIN, SQL_PORT_MAX, SQL_PORT_INCREMENT);
        portSpinner = new JSpinner(spinnerModel);
        
        userField = new JTextField(JFIELD_LENGTH);
        passField = new JPasswordField(JFIELD_LENGTH);
        passField.setEchoChar('*');
        
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        
        // General grid bag constraints
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;  // Allow components to be their own size instead of auto filling
        
        // FIRST ROW
        gc.gridy = 0;
        gc.gridx = 0;
        
        add(new JLabel("User: "), gc);
        
        gc.gridx++;
        add(userField, gc);
        
        // NEXT ROW
        gc.gridy++;
        gc.gridx = 0;
        
        add(new JLabel("Password: "), gc);
        
        gc.gridx++;
        add(passField, gc);
        
        // NEXT ROW
        gc.gridy++;
        gc.gridx = 0;
        
        add(new JLabel("Port: "), gc);
        
        gc.gridx++;
        add(portSpinner, gc);
        
        // NEXT ROW
        gc.gridy++;
        gc.gridx = 0;
        
        add(okButton, gc);
        
        gc.gridx++;
        add(cancelButton, gc);
        
        // Get the values and close the window
        okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int portNumber = (int) portSpinner.getValue();
                String userName = userField.getText();
                char[] password = passField.getPassword();
                
                if(prefsListener != null)
                {
                    prefsListener.preferencesSet(userName, new String(password), portNumber);
                }
                
                setVisible(false);
            }
        });
        
        // Close the window if the cancel button is pressed
        cancelButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
            }
        });
        
        setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        setLocationRelativeTo(parent);
    }

    public void setDefaults(String user, String password, int portNumber)
    {
        userField.setText(user);
        passField.setText(password);
        portSpinner.setValue(portNumber);
    }
    
    public void setPrefsListener(PrefsListener prefsListener)
    {
        this.prefsListener = prefsListener;
    }
    

}
