import javax.swing.JOptionPane;

public class XO implements playable{

  private char[][] field={{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};

  public void play() throws OutOfFieldException,WrongInputException,OccupiedCellException{
      int player=1;
      int[] input;
      char w=' ';
      while(w==' '){
            input=getInput();
            if (isOccupied(input[0],input[1])){
              JOptionPane.showMessageDialog(null,"the cell "+input[0]+","+input[1]+" is occupied");
            } else {
              if (player==1){
                setValue(input[0],input[1],'X');
                player*=-1;
              }else {
                setValue(input[0],input[1],'O');
                player*=-1;
              }
              showField();
              w=Winner();
            }
          }
      System.out.println(w);
    }

  private static class OutOfFieldException extends Exception{
      public OutOfFieldException(){
        super("column or row is out of the game field");
      }
    }

  private static class WrongInputException extends Exception{

        public WrongInputException(){
          super("the input value can not be used ");
        }

    }

  private static class OccupiedCellException extends Exception{
    public OccupiedCellException(){
      super("this cell is already occupied");
    }
  }

  private static class GameNotOverEXception extends Exception{
    public GameNotOverEXception(){
      super("game is not over yet");
    }
  }

  public char getValue(int row,int column) throws OutOfFieldException{

        if (row>=3 || column>=3 || row<0 || column<0){
          throw new OutOfFieldException();
        }

        return field[row][column];

    }

  private int[] getInput() {
      String input=JOptionPane.showInputDialog("input the row , column");
      // for now let s leave it simple
      return new int[]{Integer.parseInt(input.substring(0,1)),Integer.parseInt(input.substring(2,3))};
  }
  public boolean isOccupied(int row,int column) throws OutOfFieldException{

    if (row>=3 || column>=3 || row<0 || column<0){
      throw new OutOfFieldException();
    }

    return field[row][column]!=' ' ;

  }

  public void setValue(int row,int column,char value)throws OutOfFieldException,WrongInputException,OccupiedCellException{

    if (row>=3 || column>=3 || row<0 || column<0){
      throw new OutOfFieldException();
    }

    if ( value!='X' && value!='O' ) {
      throw new WrongInputException();
    }

    if (field[row][column]!=' ' ){
      throw new OccupiedCellException();
    }

    field[row][column]=value;

  }

  public boolean isOver(){
    for(int i=0;i<3;i++){
      for(int j=0;j<3;j++){
        if (field[i][j]!='X' || field[i][j]!='O' ){
          return false;
        }
      }
    }
    return true;
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

  public void showField(){
    System.out.println(field[0][0]+" | "+field[0][1]+" | "+field[0][2]+
    "\n"+"---------"+"\n"+field[1][0]+" | "+field[1][1]+" | "+field[1][2]+
    "\n"+"---------"+"\n"+field[2][0]+" | "+field[2][1]+" | "+field[2][2]);
  }
  public static void main(String[] args) throws OutOfFieldException,WrongInputException,OccupiedCellException{
    XO game=new XO();
    game.play();
  }
}
