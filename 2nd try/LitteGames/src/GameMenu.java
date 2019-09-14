import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.*;

public class GameMenu extends JFrame{
	
	private LinkedList<Game> gamelist;
	private JPanel playpanel;
	private JPanel gamelistpanel;
	
	
	
	public GameMenu(GameMenuController controller) {
		
		gamelist=new LinkedList<Game>();
		gamelist.add(new XO());
		
		setPreferredSize(new Dimension(800,500));
		playpanel.setPreferredSize(new Dimension(500,500));
		gamelistpanel.setPreferredSize(new Dimension(300,500));
		gamelistpanel.setLayout(new GridLayout(gamelist.size(),1));
		
		add(playpanel);
		add(gamelistpanel);
		
		pack();
		setVisible(true);
	}
	
	private JPanel getGamePanel(Game game,GameMenuController controller) {
		
		JPanel panel=new JPanel();
		panel.add(new JButton(game.getIcon()));
		panel.add(new JLabel(game.getName()));
		return panel;
		
	}
	
	private void prepareGameListPanel(GameMenuController controller) {
		
		ListIterator<Game> iterator=gamelist.listIterator(0);
		Game game;
		while (iterator.hasNext()) {
			game=iterator.next();
			gamelistpanel.add(getGamePanel(game,controller));
		}
		
	}
	public static void main(String[] args) {
	
	}
}
