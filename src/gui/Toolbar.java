package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Toolbar extends JPanel implements ActionListener
{

    private JButton helloButton;
    private JButton goodbyeButton;
    private StringListener textListener;

    public Toolbar()
    {
        helloButton = new JButton("Hello");
        goodbyeButton = new JButton("Goodbye");

        // Register the Toolbar as the thing that handles events for these button presses
        helloButton.addActionListener(this);
        goodbyeButton.addActionListener(this);

        setLayout(new FlowLayout(FlowLayout.LEFT));

        add(helloButton);
        add(goodbyeButton);
    }

    //
    public void setStringListener(StringListener listener)
    {
        if (listener != null)
        {
            this.textListener = listener;
        }
    }

    // When an event is generated by a button press, this function is called--which identifies which button generated the event
    // and then sends a message
    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton buttonThatWasClicked = (JButton) e.getSource();

        if (textListener != null)
        {

            if (buttonThatWasClicked == helloButton)
            {
                textListener.textEmitted("Hello\n");
            } else if (buttonThatWasClicked == goodbyeButton)
            {
                textListener.textEmitted("Goodbye\n");
            }
        }

    }
}
