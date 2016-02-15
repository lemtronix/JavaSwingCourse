package gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Person;

public class TablePanel extends JPanel
{
    private JTable table;
    private PersonTableModel tableModel;
    private JPopupMenu popUp; // Menu system for when mouse is right clicked in the table area
    
    public TablePanel()
    {
        tableModel = new PersonTableModel();
        table = new JTable(tableModel);
        popUp = new JPopupMenu();
        
        JMenuItem removeItem = new JMenuItem("Delete Row");
        popUp.add(removeItem);
        
        // Watch for right clicks, use MouseAdapter so we don't have to implement ALL of the MouseListener events
        table.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON3)
                {
                    // Show the menu where the mouse is located
                    popUp.show(table,  e.getX(), e.getY());;
                }
            }
            
        });
        
        setLayout(new BorderLayout());

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setData(List<Person> db)
    {
        tableModel.setData(db);
    }
    
    public void refresh()
    {
        tableModel.fireTableDataChanged();
    }
}
