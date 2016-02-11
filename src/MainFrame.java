import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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

        setJMenuBar(createMenuBar());
        
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
                        + ": " + taxIdString + ": " + e.isUsCitizen() + ": " + e.getGender() + "\n");
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

        setMinimumSize(new Dimension(500, 400));
        setSize(500, 400);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private JMenuBar createMenuBar() {
        
        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem importDataItem = new JMenuItem("Import Data...");
        JMenuItem exportDataItem = new JMenuItem("Export Data...");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        fileMenu.add(importDataItem);
        fileMenu.add(exportDataItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Window Menu
        JMenu windowMenu = new JMenu("Window");
        
        // Create Window > Show 
        JMenu showSubMenu = new JMenu("Show");
        JCheckBoxMenuItem showPersonFormItem = new JCheckBoxMenuItem("Person Form");
        showPersonFormItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem item = (JCheckBoxMenuItem)e.getSource();
                formPanel.setVisible(item.isSelected());
            }
        });
        showPersonFormItem.setSelected(true);
        showSubMenu.add(showPersonFormItem);
        
        
        windowMenu.add(showSubMenu);
        
        // Create the Menu Bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(windowMenu);
        
        return menuBar;
    }
}
