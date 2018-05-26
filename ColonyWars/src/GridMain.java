import javax.swing.JFrame;
public class GridMain {

	
	public static void main (String[] args) {
		
		JFrame gridFrame = new JFrame ("Grid");
		gridFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gridFrame.getContentPane().add(new Grid());		
		gridFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		gridFrame.setResizable(false);
		gridFrame.setUndecorated(true);
		gridFrame.pack();
		gridFrame.setVisible(true);
		
		
	}
	
	
	
	
	
	
	
}


