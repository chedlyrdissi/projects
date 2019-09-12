import javax.swing.*;
import java.awt.event.*;

public class XOGUI implements ActionListener{

  private static class XOModel{

      private char[][] field={{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
      private char player='X';
      private int nb=0;

      public char getValue(int row,int column){
            return field[row][column];
      }

      public boolean isOccupied(int row,int column){
        return field[row][column]!=' ' ;
      }

      public void setValue(int row,int column){
        field[row][column]=player;
        if (player == 'X'){
          player='O';
        } else {
          player='X';
        }
        nb++;
      }

      public boolean isOver(){
        return nb==9;
      }

      public char Winner() {

        if (field[0][0]==field[1][1] && field[1][1]==field[2][2] || field[0][2]==field[1][1] && field[1][1]==field[2][0]) {
          return field[1][1];
        }
        for(int i=0;i<3;i++){
          if (field[i][0]==field[i][1] && field[i][1]==field[i][2]){
            return field[i][0];
          }
          if (field[0][i]==field[1][i] && field[1][i]==field[2][i]){
            return field[0][i];
          }
        }
        if (isOver()){
          return 'D';
        }
        return ' ';

      }

      public char getPlayer(){
        return player;
      }
  }
  private static class XOView{

    private class XOCell extends JButton{

      private final Icon x=new ImageIcon("Xicon.png");
      private final Icon o=new ImageIcon("Oicon.png");
      private char state;
      private String command;

      public XOCell(XOGUI Controller,String name){
        super(new ImageIcon("emptyicon.jpg"));
        setSize(100,100);
        state=' ';
        setActionCommand(name);
        addActionListener(controller);
      }

      public void set(char value){
        if (value=='X'){
          setIcon(x);
        }
        if (value=='O'){
          setIcon(o);
        }
      }

      public char getState(){
        return state;
      }
    }


    private XOModel model;
    private XOGUI controller;
    private XOCell[][] cells;
    private JFrame frame;
    private JPanel cellpanel,controlpanel;
    private JLabel player;


    public XOView(XOModel model,XOGUI controller){

      this.model=model;
      this.controller=controller;

      cells=new XOCell[3][3];
      for (int i=0;i<3;i++){
        for (int j=0;j<3;j++){
          cells[i][j]=new XOCell(controller,"("+i+","+j+")");
        }
      }

      frame=new JFrame("XO");
      //frame.setSize(600,300);
      cellpanel=new JPanel();
      cellpanel.setLayout(new BoxLayout(cellpanel,BoxLayout.X_AXIS));
      JPanel c1,c2,c3;
      c1=new JPanel();
      c1.setLayout(new BoxLayout(c1,BoxLayout.Y_AXIS));
      c2=new JPanel();
      c2.setLayout(new BoxLayout(c2,BoxLayout.Y_AXIS));
      c3=new JPanel();
      c3.setLayout(new BoxLayout(c3,BoxLayout.Y_AXIS));
      cellpanel.add(c1);
      cellpanel.add(c2);
      cellpanel.add(c3);

      //cellpanel.setSize(300,300);
      controlpanel=new JPanel();
    //  controlpanel.setSize(200,300);

      for (int i=0;i<3;i++){
          c1.add(cells[i][0]);
      }
      for (int i=0;i<3;i++){
          c2.add(cells[i][1]);
      }
      for (int i=0;i<3;i++){
          c3.add(cells[i][2]);
      }

      controlpanel.add(new JLabel("welcome to XO game"));
      player=new JLabel("it is player "+model.getPlayer()+"'s turn");
      controlpanel.add(player);

      frame.add(cellpanel);
      //frame.add(controlpanel);

      frame.setVisible(true);
      frame.pack();

    }
    public void set(int r,int c,char value){
      cells[r][c].set(value);
    }
    public JFrame getFrame(){
      return frame;
    }
    }

  private XOModel model;
  private XOView view;

  public void actionPerformed(ActionEvent e){
      String a=e.getActionCommand();
      int r=Integer.parseInt(a.substring(1,2));
      int c=Integer.parseInt(a.substring(3,4));
      if (!model.isOccupied(r,c)){
        view.set(r,c,model.getPlayer());
        model.setValue(r,c);
          switch(model.Winner()){
            case 'X' :JOptionPane.showMessageDialog(view.getFrame(),"congratulations player X you have won","winner",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("winnericon.png"));
                      JOptionPane.showOptionDialog(null,"do you want to play again ", "GAME OVER", JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE,new ImageIcon("infinityicon.jpg") , null, null);
                      java.lang.System.exit(0);
                      break;
            case 'O' :JOptionPane.showMessageDialog(view.getFrame(),"congratulations player O you have won","winner",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("winnericon.png"));
                      JOptionPane.showOptionDialog(null,"do you want to play again ", "GAME OVER", JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE,new ImageIcon("infinityicon.jpg") , null, null);
                      java.lang.System.exit(0);
                      break;
            case 'D' :JOptionPane.showMessageDialog(view.getFrame(),"it is a draw","draw",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("disapointedicon.png"));
                      JOptionPane.showOptionDialog(null,"do you want to play again ", "GAME OVER", JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE,new ImageIcon("infinityicon.jpg") , null, null);
                      java.lang.System.exit(0);
                      break;
          }
      }
    }


  public XOGUI(XOModel model){
    this.model=model;
    this.view=new XOView(model,this);
  }
  public static void main(String[] args){
    XOGUI game=new XOGUI(new XOModel());
  }
}
