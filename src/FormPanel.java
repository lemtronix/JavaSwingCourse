import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FormPanel extends JPanel {

    private final int BORDER_TOP = 0;
    private final int BORDER_LEFT = 5;
    private final int BORDER_BOTTOM = 0;
    private final int BORDER_RIGHT = 5;

    private final Insets INSET_5_RT = new Insets(0, 0, 0, 5); // Adds a space between the object on the left and the object on the right
    private final Insets INSET_NONE = new Insets(0, 0, 0, 0);

    private JTextField nameField;
    private JTextField occupationField;
    private JList<AgeCategory> ageList;
    private JComboBox<String> employmentCombo;
    private JCheckBox citizenCheck;
    private JTextField taxField;
    private JLabel taxLabel;
    private JButton okButton;

    private FormListener formListener;

    public FormPanel() {

        // Create the components
        nameField = new JTextField(10);
        occupationField = new JTextField(10);
        ageList = new JList<AgeCategory>();

        // Setup the listbox
        DefaultListModel<AgeCategory> ageModel = new DefaultListModel<AgeCategory>();
        ageModel.addElement(new AgeCategory(0, "Under 18"));
        ageModel.addElement(new AgeCategory(1, "18-65"));
        ageModel.addElement(new AgeCategory(2, "Over 65"));

        ageList.setPreferredSize(new Dimension(115, 60));
        ageList.setBorder(BorderFactory.createEtchedBorder());
        ageList.setModel(ageModel);
        ageList.setSelectedIndex(1); // Select the second item in the list

        // Setup employment combo-box
        employmentCombo = new JComboBox<String>();
        DefaultComboBoxModel<String> employmentModel = new DefaultComboBoxModel<String>();
        employmentModel.addElement("Employed");
        employmentModel.addElement("Self-Employed");
        employmentModel.addElement("Unemployed");
        employmentCombo.setModel(employmentModel);
        employmentCombo.setSelectedIndex(0);

        // Create the checkbox and field
        citizenCheck = new JCheckBox();
        taxField = new JTextField(10);
        taxLabel = new JLabel("Tax ID: ");

        taxLabel.setEnabled(false);
        taxField.setEnabled(false);

        citizenCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isTicked = citizenCheck.isSelected();
                taxLabel.setEnabled(isTicked);
                taxField.setEnabled(isTicked);
            }
        });

        // Create the OK button
        okButton = new JButton("OK");

        // When the okay button is clicked...
        okButton.addActionListener(new ActionListener() {

            // Get the values from the field and list boxes and send a form
            // event to any form listeners
            public void actionPerformed(ActionEvent e) {
                AgeCategory ageSelection = ageList.getSelectedValue();

                FormEvent formEvent = new FormEvent(this, nameField.getText(), occupationField.getText(), ageSelection.getId(),
                        employmentCombo.getSelectedItem().toString(), taxField.getText(), citizenCheck.isSelected());

                if (formListener != null) {
                    formListener.formEventOccurred(formEvent);
                }
            }
        });

        // Set the preferred width of this FormPanel to wider than 10, 10 pixels
        setPreferredSize(new Dimension(250, 10));

        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(BORDER_TOP, BORDER_LEFT, BORDER_BOTTOM, BORDER_RIGHT), // outer border
                BorderFactory.createTitledBorder("Add Person"))); // inner border

        layoutComponents();
    }

    public void setFormListener(FormListener listener) {
        if (listener != null) {
            this.formListener = listener;
        }
    }

    public void layoutComponents() {
        // Use the Grid Bag Layout manager
        setLayout(new GridBagLayout());

        // Setup the constraints; x is left to right, y is top to bottom; Do not
        // resize the components
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.NONE;

        // weight determines how bunched objects should be together; higher
        // number = more breathing room between components.

        /// FIRST ROW ///
        gc.gridy = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = INSET_5_RT;
        add(new JLabel("Name: "), gc);

        gc.gridy = 0;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = INSET_NONE;
        add(nameField, gc);

        // NEXT ROW ///
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = INSET_5_RT;
        add(new JLabel("Occupation: "), gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = INSET_NONE;
        add(occupationField, gc);

        // NEXT ROW ///
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(new JLabel("Age: "), gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(ageList, gc);

        // NEXT ROW ///
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(new JLabel("Employment: "), gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(employmentCombo, gc);

        // NEXT ROW ///
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(new JLabel("US Citizen: "), gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(citizenCheck, gc);

        // NEXT ROW ///
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(taxLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(taxField, gc);

        // NEXT ROW ///
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 2;

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(okButton, gc);
    }
}

class AgeCategory {

    private int id;
    private String text;

    public AgeCategory(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public String toString() {
        return text;
    }

    public int getId() {
        return id;
    }
}