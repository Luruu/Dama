package Game.GameObjects.Pieces;

import Game.ImageComponent;
import Game.GameObjects.ElementGame;
import Game.GameObjects.Players.Player;
import Game.GraphicObjects.Table.Box;
import Game.GraphicObjects.Table.CheckersTable;


import java.awt.*;

/**
 * <h1>Piece Class</h1> 
 * This class represents the abstraction of the game piece, therefore it encapsulates the logic of movement.
 * @author <a href="https://github.com/Luruu">Luca Rubino</a>
 * @author <a href="https://github.com/RenatoEsposito1999">Renato Esposito</a>
 * @version 1.0
 * @since 31-08-2021
 */
public abstract class Piece extends ImageComponent implements ElementGame {
   private final Color color;
   protected int points;
   
   private final int DIM_IMG;
   private Point coord;
   private Player owner;
   
   protected final CheckersTable TABLE;
   private static Point posAfterMove = new Point(); //free position on which to show suggestion 
   
/**
 * @param c the color of the piece piece.
 * @param p the points to be awarded to the player who eats this piece.
 * @param ply the owner player who has the ability to move the piece.
 * @throws Exception CheckersTable getInstance() Exception.
 */
   public Piece(Color c, int p, Player ply) throws Exception{
      color = c;
      points = p;
      owner = ply;
      addMouseListener(owner);
      TABLE = CheckersTable.getInstance();
      DIM_IMG = Box.DIM_BOX - 10; //Dimension of a Box - margin
      setImg(getPathIMG(), new Dimension(DIM_IMG, DIM_IMG));
      
      coord = new Point();
   }

      /**
 * Abstract method which is used by the constructor and to be implemented by subclasses.
 * @return the string that represents the path of the image.
 */
   protected abstract String getPathIMG();


/**
 * This method determines the possible movements of the piece that implements it.
 * @param direction the direction of the boxes to be analyzed.
 * @return 2 if have to eat, 0 otherwise.
 */
   public int showSuggestions(int direction){
      final int GOLEFT_COL = coord.y - 1, GORIGHT_COL = coord.y + 1;
      int esito_left, esito_right;
      
      posAfterMove.x = direction;
      esito_left = checkMove(posAfterMove.x, GOLEFT_COL);

      if (esito_left == 0 || esito_left == 1){ // If left you can't eat
         esito_right = checkMove(posAfterMove.x, GORIGHT_COL); //I see if I can move or eat to the right
         if (esito_right == 2){ //I HAVE to eat RIGHT
            posAfterMove.x = setRowonEat(posAfterMove);
            TABLE.showFreeBox(posAfterMove.x, posAfterMove.y);
            return 2;
         }
         else{ //If you can't even eat right then ..
            if (esito_left == 0) //If I can move left
               TABLE.showFreeBox(posAfterMove.x, GOLEFT_COL);   
            if (esito_right == 0) //If I can move right
               TABLE.showFreeBox(posAfterMove.x, GORIGHT_COL); 
         }
      }
      else if (esito_left == 2){ //I HAVE TO EAT ON THE LEFT
         posAfterMove.x = setRowonEat(posAfterMove);
         TABLE.showFreeBox(posAfterMove.x, posAfterMove.y);
         return 2;
      }
      return 0;
  }

/** 
 * This method implements the analysis of the box whose coordinates are passed in input.
 * @param row row index of the rectangle to be analyzed.
 * @param COL_DIRECTION col index of the rectangle to be analyzed.
 * @return 0 if FIRST box is free so piece can move. <br> 1 if FIRST box is not free , so 
 * piece cannot eat first box or move or because of wizard trying eat another wizard. <br> 
 * 2 if second box is FREE and Piece MUST EAT enemy piece in first box.
 */
   public int checkMove(int row, final int COL_DIRECTION){
      Point boxToAnalize = new Point(row, COL_DIRECTION); //First Box to analyze
      int r = enemyPiece_inBox(boxToAnalize);
      if(r == 2 && !WizardTryEatWizard(boxToAnalize)){ //if piece is in first box && piece is not a Wizard trying eat enemy Wizard
         //Enemy piece found but I don't know if piece can eat because I have to check if SECONDBOX BEHIND boxToAnalize is free
         Point second_boxToAnalize = new Point(setRowonEat(boxToAnalize), setColonEat(boxToAnalize.y));
         posAfterMove.y = second_boxToAnalize.y; //save new position to Move (for suggestion)
         return  (enemyPiece_inBox(second_boxToAnalize) == 0) ? 2 : 1; // 2: second box is FREE and Piece MUST EAT enemy piece in first box
      }
      else if (r == 0 || r==1)   
         return  r; //values can be r are: 0 or 1:  
            // 0 (FIRST box is free so piece can move) or 1 (FIRST box is not free, so piece cannot eat first box or move)
      else //r == 2 but wizard trying eat another wizard
         return 1; 
   }


/** 
 * This method implements the rule that a wizard cannot eat another wizard.
 * @param position represents the coordinates of the box containing the enemy piece.
 * @return true if an Wizard try eat another Wizard, else returns false.
 */
   public boolean WizardTryEatWizard(Point position) {
      Box box = TABLE.getBoxfromList(position.x, position.y);
      String enemy_pieceClass = box.getPiece().getClass().getSimpleName();
      String pieceClass = getClass().getSimpleName();
      
      return pieceClass.equals("Wizard") && enemy_pieceClass.equals("Wizard");
   }

/** 
 * This method check if there is an enemy in a box.
 * @param position represents the coordinates of the box to analyze.
 * @return 1 if piece cannot go over the table or there is a there is a friendly piece. <br>
 * 2 there is a enemy piece. <br>
 * 0 rectangle is free.
 */
   public int enemyPiece_inBox(Point position){
      if (TABLE.illegalMove(position.x) || TABLE.illegalMove(position.y))
         return 1; //Piece cannot go over the table (ex: for 6x6 matrix piece cannot go in -1,-1 or 6,6: only 0 to 5)

      Box box = TABLE.getBoxfromList(position.x, position.y);
      if(box.HasPiece()){ // if there is a piece
         Piece piece = box.getPiece();
         boolean enemy_piece = ! getColor().equals(piece.getColor());
         return (enemy_piece) ? 2 : 1; // 2: enemy piece --- 1: my piece
      } 
      else
         return 0; // rectangle is free
   }

   // Getters and Setters methods..

     
/**
 * Return the column index of the Box to color when you need to eat.
 * @param col column index of the piece to eat.
 * @return the column index of the Box to color when you need to eat.
 */
public int setColonEat(int col){
   return (col > coord.y) ? coord.y + 2 : coord.y - 2;
}
   
/**
* Return the row index of the Box to color when you need to eat.
* @param position indicates the rectangle in which the piece to eat is located.
* @return the row index of the Box to color when you need to eat.
*/
public int setRowonEat(Point position){
   return (coord.x - position.x > 0) ? coord.x - 2 : coord.x + 2; 
}

/**
* Return the row index of the Box to color, it depends on the color of the piece to be moved.
* @return the row index of the Box to color, it depends on the color of the piece to be moved.
*/
public int setRowbyColor(){
   return (getColor() == Color.red) ? coord.x - 1 : coord.x + 1;
}



/** 
 * Sets the coordinates of the piece in a two-dimensional data structure.
 * @param x row index of the position that the piece occupies in the structure.
 * @param y column index of the position that the piece occupies in the structure.
 */
   public void setCoord(int x, int y){
      coord.x = x;
      coord.y = y;
   }

/** 
 * Return the row and column indices that the piece occupies in the data structure in the form of coordinates.
 * @return the row and column indices that the piece occupies in the data structure in the form of coordinates.
 */
   public Point getCoord() {
      return coord;
   }

/** 
 * Return piece color.
 * @return piece color.
 */
   public Color getColor(){
      return color;
   }

/** 
* Return the object instance.
* @return the object instance.
*/
   public Piece getPtoMove(){
      return this;
   }

/** 
* Return the player owning the piece.
* @return the player owning the piece.
*/
   public Player getOwner(){
      return owner;
   }

/** 
* Return the value of the score associated with the piece.
* @return the value of the score associated with the piece.
*/
   public int getPoints(){
      return points;
   }
}
