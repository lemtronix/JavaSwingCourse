package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

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

    public MainFrame()
    {
        super("Employee Entry Form");

        controller = new Controller();

        setLayout(new BorderLayout());

        formPanel = new FormPanel();
        tablePanel = new TablePanel();

        tablePanel.setData(controller.getPeople());

        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new PersonFileFilter());

        setJMenuBar(createMenuBar());

        formPanel.setFormListener(new FormListener()
        {
            @Override
            public void formEventOccurred(FormEvent e)
            {
                controller.addPerson(e);
                tablePanel.refresh();

            }
        });

        add(formPanel, BorderLayout.WEST);
        add(tablePanel, BorderLayout.CENTER);

        setMinimumSize(new Dimension(500, 400));
        setSize(500, 400);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JMenuBar createMenuBar()
    {

        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem importDataItem = new JMenuItem("Import Data...");
        JMenuItem exportDataItem = new JMenuItem("Export Data...");
        JMenuItem exitItem = new JMenuItem("Exit");

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
                        JOptionPane.showMessageDialog(MainFrame.this, "Could not save data to selected file.", "Error!", JOptionPane.ERROR_MESSAGE);
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
                    System.exit(0);
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

        // Window Menu
        JMenu windowMenu = new JMenu("Window");
        windowMenu.add(showSubMenu);
        windowMenu.setMnemonic(KeyEvent.VK_W);

        // Create the Menu Bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(windowMenu);

        return menuBar;
    }
}
