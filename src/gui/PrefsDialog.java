/*
 * This is the Window > Preferences dialog box that pops up in the application
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

public class PrefsDialog extends JDialog
{
    private static final Insets INSET_5_RT = new Insets(0, 0, 0, 5); // Adds a space between the object on the left and the object on the right
    private static final Insets INSET_NONE = new Insets(0, 0, 0, 0);

    private static final boolean MODALITY = false; // Model means carry on working, non-model does not allow you to do work in the GUI
    private static final int DIALOG_WIDTH = 225;
    private static final int DIALOG_HEIGHT = 175;

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

        // Get the values and close the window
        okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int portNumber = (int) portSpinner.getValue();
                String userName = userField.getText();
                char[] password = passField.getPassword();

                if (prefsListener != null)
                {
                    prefsListener.preferencesSet(userName, new String(password), portNumber);
                }

                setVisible(false);
            }
        });

        // Close the window and do nothing if the cancel button is pressed
        cancelButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
            }
        });

        layoutControls();
        setMinimumSize(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT));
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

    private void layoutControls()
    {
        JPanel controlsPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        /////// CONTROL PANELS LAYOUT ///////
        final int BORDER_EMPTY_SPACE = 5;
        Border spaceBoder = BorderFactory.createEmptyBorder(BORDER_EMPTY_SPACE, BORDER_EMPTY_SPACE, BORDER_EMPTY_SPACE, BORDER_EMPTY_SPACE);
        Border titleBorder = BorderFactory.createTitledBorder("Database Preferences");

        controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBoder, titleBorder));
        controlsPanel.setLayout(new GridBagLayout());

        // General grid bag constraints
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.fill = GridBagConstraints.NONE; // Allow components to be their own size instead of auto filling

        // FIRST ROW
        gc.gridy = 0;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = INSET_5_RT;
        controlsPanel.add(new JLabel("User: "), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = INSET_NONE;
        controlsPanel.add(userField, gc);

        // NEXT ROW
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = INSET_5_RT;
        controlsPanel.add(new JLabel("Password: "), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = INSET_NONE;
        controlsPanel.add(passField, gc);

        // NEXT ROW
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = INSET_5_RT;
        controlsPanel.add(new JLabel("Port: "), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = INSET_NONE;
        controlsPanel.add(portSpinner, gc);

        /////// BUTTONS PANEL ///////
        Dimension buttonSize = cancelButton.getPreferredSize();
        okButton.setPreferredSize(buttonSize);

        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        /////// GENERAL PANEL LAYOUT///////
        setLayout(new BorderLayout());
        add(controlsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
