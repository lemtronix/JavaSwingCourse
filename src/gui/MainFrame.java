/*
 * MainFrame is the GUI controller for all of the different objects (i.e. FormPanel, TablePanel)
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import controller.Controller;

public class MainFrame extends JFrame
{
    private Controller controller;
    private FormPanel formPanel;
    private JFileChooser fileChooser;
    private TablePanel tablePanel;
    private PrefsDialog prefsDialog;
    private Preferences prefs; // This information is stored somewhere in the OS and allows preferences to be persistent 

    public MainFrame()
    {
        super("Employee Entry Form");

        setLayout(new BorderLayout());

        // Controller
        controller = new Controller();

        // Preferences Node
        prefs = Preferences.userRoot().node("db");

        // Preferences Dialog
        prefsDialog = new PrefsDialog(this);
        prefsDialog.setPrefsListener(new PrefsListener()
        {
            // Set the preferences
            @Override
            public void preferencesSet(String user, String password, int portNumber)
            {
                prefs.put("user", user);
                prefs.put("password", password);
                prefs.putInt("port", portNumber);
            }
        });
        
        String user = prefs.get("user",  "");
        String password = prefs.get("password",  "");
        Integer portNumber = prefs.getInt("port",  3306);
        
        prefsDialog.setDefaults(user, password, portNumber);
        
        // Table Panel
        tablePanel = new TablePanel();
        tablePanel.setData(controller.getPeople());
        tablePanel.setPersonTableListener(new PersonTableListener()
        {
            public void rowDeleted(int row)
            {
                System.out.println("MainFrame Deleted Row: " + row);
                controller.removePerson(row);
            }
        });

        // File Chooser
        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new PersonFileFilter());

        setJMenuBar(createMenuBar());

        // Form Panel
        formPanel = new FormPanel();
        formPanel.setFormListener(new FormListener()
        {
            @Override
            public void formEventOccurred(FormEvent e)
            {
                controller.addPerson(e);
                tablePanel.refresh();

            }
        });

        // Handle window close events
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            // Execute when the user clicks directly on the red X
            @Override
            public void windowClosing(WindowEvent e)
            {
                // JFrame dispose method quits automatically 
                dispose();
                
                // Perform garbage collection at the last possible moment
                System.gc();
            }
        });
        // Add components to layout manager
        add(formPanel, BorderLayout.WEST);
        add(tablePanel, BorderLayout.CENTER);

        setMinimumSize(new Dimension(500, 400));
        setSize(500, 400);

        setVisible(true);
    }

    private JMenuBar createMenuBar()
    {

        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem importDataItem = new JMenuItem("Import Data...");
        JMenuItem exportDataItem = new JMenuItem("Export Data...");
        JMenuItem exitItem = new JMenuItem("Exit");

        // CTRL-I for importing an item
        importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));

        importDataItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
                {
                    try
                    {
                        controller.loadFromFile(fileChooser.getSelectedFile());

                        // Notify the table panel that there's data to process
                        tablePanel.refresh();

                        System.out.println("Loaded: " + fileChooser.getSelectedFile());
                    }
                    catch (IOException ioe)
                    {
                        JOptionPane.showMessageDialog(MainFrame.this, "Could not load selected file.", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    System.out.println("PopDown Error!");
                }
            }
        });

        // CTRL-E for exporting an item
        exportDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));

        exportDataItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
                {
                    try
                    {
                        controller.saveToFile(fileChooser.getSelectedFile());
                        System.out.println("Saved: " + fileChooser.getSelectedFile());
                    }
                    catch (IOException ioe)
                    {
                        JOptionPane.showMessageDialog(MainFrame.this, "Could not save data to selected file.", "Error!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    System.out.println("PopDown Error!");
                }
            }
        });

        exitItem.setMnemonic(KeyEvent.VK_X);
        KeyStroke keyStrokeCtrlX = KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK);
        exitItem.setAccelerator(keyStrokeCtrlX);

        exitItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Prompt the user to ensure they really want to quit
                int action = JOptionPane.showConfirmDialog(MainFrame.this, "Do you really want to exit?", "Confirm Exit",
                        JOptionPane.OK_CANCEL_OPTION);

                if (action == JOptionPane.OK_OPTION)
                {
                    // Grab all window listeners 
                    WindowListener[] listeners = getWindowListeners();
                    
                    for (WindowListener listener : listeners)
                    {
                        listener.windowClosing(new WindowEvent(MainFrame.this,  0));
                    }
                }
                else
                {
                    // Ignore
                }
            }
        });

        fileMenu.add(importDataItem);
        fileMenu.add(exportDataItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        fileMenu.setMnemonic(KeyEvent.VK_F);

        // Create Window > Show
        JMenu showSubMenu = new JMenu("Show");

        // Create Window > Show > Person Form
        JCheckBoxMenuItem showPersonFormItem = new JCheckBoxMenuItem("Person Form");
        showPersonFormItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JCheckBoxMenuItem item = (JCheckBoxMenuItem) e.getSource();
                formPanel.setVisible(item.isSelected());
            }
        });

        showPersonFormItem.setSelected(true);
        showSubMenu.add(showPersonFormItem);

        // Create Window > Show > Preferences
        JMenuItem prefsItem = new JMenuItem("Preferences...");
        prefsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        prefsItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                prefsDialog.setVisible(true);
            }
        });

        // Window Menu
        JMenu windowMenu = new JMenu("Window");
        windowMenu.setMnemonic(KeyEvent.VK_W);
        windowMenu.add(showSubMenu);
        windowMenu.add(prefsItem);

        // Create the Menu Bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(windowMenu);

        return menuBar;
    }
}
