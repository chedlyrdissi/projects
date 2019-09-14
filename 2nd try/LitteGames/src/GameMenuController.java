import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenuController implements ActionListener{
	
		private GameMenu menu;
		
		public GameMenuController() {
			menu=new GameMenu(this);
		}
		
		public void actionPerformed(ActionEvent e) {
			
			String command=e.getActionCommand();
			
			
		}
	

}
