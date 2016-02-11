import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

    private TextPanel textPanel;
    private JButton button;
    private Toolbar toolbar;
    private FormPanel formPanel;

    public MainFrame() {
        super("Employee Entry Form");

        setLayout(new BorderLayout());

        textPanel = new TextPanel();
        button = new JButton("Click Me!");
        toolbar = new Toolbar();
        formPanel = new FormPanel();

        toolbar.setStringListener(new StringListener() {
            @Override
            public void textEmitted(String text) {
                textPanel.appendText(text);
            }
        });

        formPanel.setFormListener(new FormListener() {
            @Override
            public void formEventOccurred(FormEvent e) {
                boolean isUsCitizen = e.isUsCitizen();
                String taxIdString = e.getTaxId();

                if (isUsCitizen == false) {
                    // Only accept the taxIdString if the person is a US Citizen
                    taxIdString = "";
                }

                textPanel.appendText(e.getName() + ": " + e.getOccupation() + ": " + e.getAgeCategory() + ": " + e.getEmployeeCategory()
                        + ": " + taxIdString + ": " + e.isUsCitizen() + "\n");
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textPanel.appendText("Hello!\n");
            }
        });

        add(toolbar, BorderLayout.NORTH);
        add(button, BorderLayout.SOUTH);
        add(formPanel, BorderLayout.WEST);
        add(textPanel, BorderLayout.CENTER);

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
