import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class XO implements Game{
	
	private String name;
	private Icon gameicon;
	private boolean favorite;
	private boolean finished;
	private long bestFinishTime;
	private Time start;
	
	private static class XOModel{
		private char[][] field={{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
		private char player='X';
		private int steps=0; 
		private boolean canplay=true;
		
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
		
		public void reStart() {
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					field[i][j]=' ';
				}
			}
			steps=0;
			canplay=true;
			player='X';
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
			
			if ((field[0][0]==field[1][1] && field[1][1]==field[2][2] ||
					field[0][2]==field[1][1] && field[1][1]==field[2][0]) && field[1][1]!=' ' ) {
				
				canplay=false;
				return field[1][1];
			
			}
			
			for (int i=0;i<3;i++) {
				
				if ( field[i][0]==field[i][1] && field[i][1]==field[i][2] && field[i][1]!=' ') {
					canplay=false;
					return field[i][0];
				}
				
				if ( field[0][i]==field[1][i] && field[1][i]==field[2][i] && field[1][i]!=' ') {
					canplay=false;
					return field[0][i];
				}
				
			}
			
			if (!done()) {
				return ' ';
			}

			return 'D';
			
		}
		public boolean getCanPlay() {
			return canplay;
		}
	}
	
	private static class XOView extends JPanel{
		
		private final Icon XICON=new ImageIcon("src/Xicon.png");
		private final Icon OICON=new ImageIcon("src/Oicon.png");
		private final Icon EMPTYICON=new ImageIcon("src/emptyicon.jpg");
		private JButton[][] field=new JButton[3][3];
		private JLabel playerLabel=new JLabel();
		private JPanel buttonpanel=new JPanel(new GridLayout(3,3));
		private JButton resetbutton;
		private XOModel model;
		
		public XOView(XOModel model,XOController controller) {
			
			super(new BorderLayout());
			this.model=model;
			
			JPanel leftpanel=new JPanel();
			add(leftpanel,BorderLayout.EAST);
			leftpanel.setLayout(new GridLayout(2,1));
			
			leftpanel.add(playerLabel);
			
			resetbutton=new JButton("reset");
			resetbutton.setActionCommand("reset");
			resetbutton.addActionListener(controller);
			leftpanel.add(resetbutton);
			
			add(new JLabel("XO Game"),BorderLayout.NORTH);
			add(buttonpanel,BorderLayout.CENTER);
			
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					
					field[i][j]=new JButton(EMPTYICON);
					field[i][j].addActionListener(controller);
					field[i][j].setActionCommand("("+i+","+j+")");
					buttonpanel.add(field[i][j]);
			
				}
			}
			
			buttonpanel.setPreferredSize(new Dimension(300,300));
		}
		private void updateButton(int i,int j) {
			if (model.getCell(i,j)=='O') {
				this.field[i][j].setIcon(OICON);
			}else if (model.getCell(i,j)=='X') {
				this.field[i][j].setIcon(XICON);
			}else {
				this.field[i][j].setIcon(EMPTYICON);
			}
			
		}
		public void update() {
			playerLabel.setText("player "+model.getPlayer()+"'s turn");
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					updateButton(i,j);
				}
			}
		}
	}
	
	private static class XOController implements ActionListener{
		private XOModel model;
		private XOView view;
		public XOController() {
			this.model=new XOModel();
			this.view=new XOView(model,this);
			view.update();
		}
		public void actionPerformed(ActionEvent e) {
			
			String command=e.getActionCommand();
			
			if (command.equals("reset")) {
				model.reStart();
				view.update();
				return;
			}
			
			if(model.getCanPlay()) {
				
				int r=Integer.parseInt(command.substring(1,2));
				int c=Integer.parseInt(command.substring(3,4));
				if(model.getCell(r,c)==' ') {
					model.setCell(r,c);
					view.update();
				}
				switch(model.winner()) {
				case 'X':JOptionPane.showConfirmDialog(null,"player X wins","GAME OVER",JOptionPane.DEFAULT_OPTION);
						break;
				case 'O':JOptionPane.showConfirmDialog(null,"player O wins","GAME OVER",JOptionPane.DEFAULT_OPTION);
						break;
				case 'D':JOptionPane.showConfirmDialog(null,"DRAW","GAME OVER",JOptionPane.DEFAULT_OPTION);
						break;
				}
			}
		}
		public JPanel getPanel() {
			return view;
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
	public JPanel getGame() {
		return (new XOController()).getPanel();
	}
	
	
	public static void main(String[] args) {
		JFrame frame=new JFrame();
		XO game=new XO("xo",new ImageIcon("XOICON.png"));
		frame.add(game.getGame());
		frame.pack();
		frame.setVisible(true);
	}
}
