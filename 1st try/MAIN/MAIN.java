import java.util.LinkedList;
import javax.swing.*;
import java.awt.*;
import java.util.*;
public class MAIN implements  ActionListener{

  public interface playable{
    public abstract void play();
    public abstract String getName();
    public abstract Icon getIcon();
  }
  private LinkedList<playable> gamelist=new LinkedList<playable>();
  private JFrame frame;
  private JPanel listpanel;
  private LayoutManager manager;

  public MAIN(){
    gamelist=new LinkedList<playable>();
    frame=new JFrame("main frame");
    listpanel=new JPanel();
    listpanel.setBackground(new Color(0,50,64));
    listpanel.setSize(200,100);
    manager=new GroupLayout(frame);
    frame.add(listpanel);
    frame.setSize(300,700);
    frame.setVisible(true);
  }
  public void importGame(){
  //  gameList.add();
  }
  public void prepareList(){
    Iterator<playable> iterator=gameList.descendingIterator();
    while(iterator.hasNext()){
      playable game=iterator.next();
      JButton button=new JButton(game.getName(),game.getIcon());
      button.setActionCommand(game.getName());
      button.addActionListener(this);
      listpanel.add(button);
    }
  }
  public void actionPerformed(ActionEvent e){
    String command=e.getActionCommand();
    Iterator<playable> iterator=gameList.descendingIterator();
    while(iterator.hasNext()){
      playable game=iterator.next();
      if (game.getName().equals(command)){
        game.play();
        break;
      }
    }
  }
  public static void main(String[] args){
    MAIN main=new MAIN();
  }
}
