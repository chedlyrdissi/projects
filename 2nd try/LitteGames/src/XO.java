import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class XO implements Game{
	
	private String name;
	private Icon gameicon;
	private boolean favorite;
	private boolean finished;
	private long bestFinishTime;
	private Time start;
	
	private class XOModel{
		private char[][] field={{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
		private char player='X';
		private int steps=0; 
		
		public char getPlayer() {
			return player;
		}
		public char getCell(int r,int c) {
			return field[r][c];
		}
		private void changePlayer() {
			if (player=='X') {
				player='O';
			} else {
				player='X';
			}
		}
		public boolean setCell(int r,int c) {
			if (field[r][c]==' ') {
				field[r][c]=player;
				changePlayer();
				steps++;
				return true;
			}else {
				return false;
			}
		}
		public boolean done() {
			return steps==9;
		}
		public char winner() {
			
			if (field[0][0]==field[1][1] && field[1][1]==field[2][2] ||
					field[0][2]==field[1][1] && field[1][1]==field[2][0]) {
				return field[1][1];
			}
			
			for (int i=0;i<3;i++) {
				if ( field[i][0]==field[i][1] && field[i][1]==field[i][2] ) {
					return field[i][0];
				}
				if ( field[0][i]==field[1][i] && field[1][i]==field[2][i] ) {
					return field[0][i];
				}
			}
			
			if (!done()) {
				return ' ';
			}
			
			return 'D';
			
		}
	}
	
	private class XOView extends JPanel{
		
		private final Icon XICON=new ImageIcon("Xicon.png");
		private final Icon OICON=new ImageIcon("Oicon.png");
		private final Icon EMPTYICON=new ImageIcon("emptyicon.jpg");
		private JButton[][] field;
		private JPanel buttonpanel=new JPanel(new GridLayout(3,3));
		private XOModel model;
		private XOController controller;
		
		public XOView(XOModel model, XOController controller) {
			super(new BorderLayout());
			this.model=model;
			this.controller=controller;
			
			add(new JLabel("XO Game"),BorderLayout.NORTH);
			add(buttonpanel,BorderLayout.CENTER);
			JButton button;
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					button=new JButton(EMPTYICON);
					button.addActionListener(controller);
					button.setActionCommand("("+i+","+j+")");
					field[i][j]=button;
					buttonpanel.add(button);
				}
			}
		}
		public void updateButton(int r,int c) {
			
		}
	}
	
	private class XOController implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			;
		}
	}
	
	public XO(String name,Icon icon) {
		
		this.name=name;
		this.gameicon=icon;
		
	}
	
	//to implement
	public String getName() {
		return name;
	}
	
	//to implement
	public Icon getIcon() {
		return gameicon;
	}
	
	//to implement
	public JPanel play() {
		return null;
	}
	
	//to implement
	public Boolean isFavorite() {
		return favorite;
	}
	
	//to implement
	public boolean IsFinished() {
		return finished;
	}
	
	//to implement
	public long bestFinishTime() {
		return 0;
	}
}
