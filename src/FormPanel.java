import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class FormPanel extends JPanel {
	
	private final int BORDER_TOP = 0;
	private final int BORDER_LEFT = 5;
	private final int BORDER_BOTTOM = 0;
	private final int BORDER_RIGHT = 5;
	public FormPanel() {
		// Set the preferred width of this FormPanel to wider than 10, 10 pixels
		setPreferredSize(new Dimension(200, 10));
		
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(BORDER_TOP, BORDER_LEFT, BORDER_BOTTOM, BORDER_RIGHT), // outer border
				BorderFactory.createTitledBorder("Add Person"))); 	// inner border
	}
}
