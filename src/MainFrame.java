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
		super("Hello World");
		
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
			public void formEventOccurred(FormEvent e ) {
				textPanel.appendText(e.getName() + ": " + e.getOccupation() + ": " + e.getAgeCategory() + "\n");
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
