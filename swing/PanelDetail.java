package swing;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box.Filler;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelDetail extends JPanel {
	
	public PanelDetail() {
		initComponents();
	}
	
	private void initComponents() {
		Filler fill1 = new Filler(new Dimension(0,10), new Dimension(10,10), new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		setBorder(BorderFactory.createLoweredSoftBevelBorder());
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(
				layout.createParallelGroup(Alignment.LEADING)
				.addComponent(fill1, 0, 0, Short.MAX_VALUE)
		);
		
		layout.setVerticalGroup(
				layout.createParallelGroup(Alignment.LEADING)
				.addComponent(fill1, 0, 15, 15)
		);
	}
}
