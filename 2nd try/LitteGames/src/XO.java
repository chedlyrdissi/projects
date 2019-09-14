import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class XO implements Game{
	
	private String name="XO";
	private Icon gameicon=new ImageIcon("src/XOICON.png");
	private boolean favorite;
	private boolean finished;
	private long bestFinishTime;
	private Time start;
	public final int SOLO=1;
	public final int MULTI=2;

	
	private static class XOModel{
		private char[][] field={{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
		private char player='X';
		private int steps=0; 
		private boolean canplay=true;
		private boolean canstep=false;
		private int nextstep;
		
		public XOModel() {
		}
		public XOModel(XOModel model) {
			this.field=new char[3][3];
			for(int i=0;i<9;i++) {
				int r=i/3;
				int c=i%3;
				this.field[r][c]=model.field[r][c];
			}
			this.player=model.player;
			this.steps=model.steps;
			this.canplay=model.canplay;
		}
		
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
				if(canstep) {
					nextstep=r*3+c;
					canstep=false;
				}
				changePlayer();
				return true;
			}else {
				return false;
			}
		}
		public void setCanStep() {
			canstep=true;
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
		public static Stack<XOModel> getSolutions(XOModel model) {
			
			LinkedStack<XOModel> solutions=new LinkedStack<XOModel>();
			LinkedStack<XOModel> ready=new LinkedStack<XOModel>();
			solutions.push(model);
			XOModel sol,sol2;
			boolean ad;
			char w;
			
			while(!solutions.isEmpty()) {
				
				sol=solutions.pop();
				for(int pos=0;pos<9;pos++) {
					
					if ( sol.getCell(pos/3,pos%3)==' ' ) {
						
						sol2=new XOModel(sol);
						ad=sol2.setCell(pos/3,pos%3);
						w=sol2.winner();
						
						if(w=='O' || w=='D') {
							
							solutions.push(sol2);
							
						}
						
					}
					
				}
				
			}
			
			return solutions;
		
		}
	}
	
	private static class XOView extends JPanel{
		
		private final Icon XICON=new ImageIcon("C:/Users/Chedl/Desktop/side_projects/2nd try/LitteGames/src/Xicon.png");
		private final Icon OICON=new ImageIcon("C:/Users/Chedl/Desktop/side_projects/2nd try/LitteGames/src/Oicon.png");
		private final Icon EMPTYICON=new ImageIcon("C:/Users/Chedl/Desktop/side_projects/2nd try/LitteGames/src/emptyicon.jpg");
		private JButton[][] field=new JButton[3][3];
		private JLabel playerLabel=new JLabel();
		private JPanel buttonpanel=new JPanel(new GridLayout(3,3));
		private JButton resetbutton;
		private XOModel model;
		
		public XOView(XOModel model,ActionListener controller) {
			
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
				initiateEnd();
			}
		}
		private void initiateEnd(){
			switch(model.winner()) {
			case 'X':JOptionPane.showConfirmDialog(null,"player X wins","GAME OVER",JOptionPane.DEFAULT_OPTION);
					break;
			case 'O':JOptionPane.showConfirmDialog(null,"player O wins","GAME OVER",JOptionPane.DEFAULT_OPTION);
					break;
			case 'D':JOptionPane.showConfirmDialog(null,"DRAW","GAME OVER",JOptionPane.DEFAULT_OPTION);
					break;
			}
		}
		public JPanel getPanel() {
			return view;
		}
	}
	
	private static class XOPlayerController implements ActionListener{
		
		private XOModel model;
		private XOView view;
		
		public XOPlayerController() {
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
					}
					view.update();
					initiateEnd();
				Stack<XOModel> solutions=XOModel.getSolutions(model);
				if(solutions.isEmpty()) {
					int pos=0;
					while(pos<9) {
						if (model.getCell(pos/3,pos%3)==' ') {
							model.setCell(pos/3,pos%3);
							break;
						}
						pos++;
					}
				} else {
					//select correct cell
					
					}
				view.update();
				initiateEnd();
			}
		}
		public JPanel getPanel() {
			return view;
		}
		
		private void initiateEnd(){
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
	//to implement
	public String getName() {
		return name;
	}
	
	//to implement
	public Icon getIcon() {
		return gameicon;
	}
	
	//to implement
	public JPanel play(int type) {
		if (type==2) {
			return (new XOController()).getPanel();
		}
		else {
			return (new XOPlayerController()).getPanel();
		}

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
	
	public static void main(String[] args) {
		JFrame frame=new JFrame("XO game");
		frame.add(new XO().play(1));
		frame.pack();
		frame.setVisible(true);
	}
	
}
