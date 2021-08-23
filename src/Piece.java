import javax.swing.*;
import java.awt.*;


public abstract class Piece extends JComponent {
   private final Color color;
   protected int points;
   private ImagePiece objIMG;
   private final int DIM_IMG;
   private Point coord;
   private Player owner;
   
   protected final CheckersTable TABLE;
   private static Point posAfterMove = new Point(); //free position on which to show suggestion 
   


   public Piece(Color c, int p, Player ply) throws Exception{
      color = c;
      points = p;
      owner = ply;
      addMouseListener(owner);
      objIMG = new ImagePiece(getPathIMG());
      TABLE = CheckersTable.getInstance();
      DIM_IMG = Rectangle.DIM_RECT - 10; //Dimension of a rectangle - margin
      objIMG.img = ImagePiece.scale(objIMG.img, DIM_IMG, DIM_IMG);
      setPreferredSize(new Dimension(DIM_IMG, DIM_IMG));
      coord = new Point();
   }

   protected abstract String getPathIMG();

   protected void paintComponent(Graphics g){
      super.paintComponent(g);
      g.drawImage(objIMG.img, 0, 0,null);
   }

 

   //return values: 2 (deve mangiare) -  0(si può muovere o meno (non verificato!!))
   protected int showSuggestions(int direction){
      final int GOLEFT_COL = coord.y - 1, GORIGHT_COL = coord.y + 1;
      int esito_left, esito_right;
      
      posAfterMove.x = direction;
      esito_left = checkMove(posAfterMove.x, GOLEFT_COL);

      if (esito_left == 0 || esito_left == 1){ // Se a sinistra non si può mangiare
         esito_right = checkMove(posAfterMove.x, GORIGHT_COL); //Vedo se a destra posso muovermi o mangiare
         if (esito_right == 2){ //DEVO mangiare a DESTRA
            posAfterMove.x = setRowonEat(posAfterMove);
            TABLE.showFreeRectangle(posAfterMove.x, posAfterMove.y);
            return 2;
         }
         else{ //Se nemmeno a destra si può mangiare allora..
            if (esito_left == 0) //Se posso muovermi a sinistra
               TABLE.showFreeRectangle(posAfterMove.x, GOLEFT_COL);   
            if (esito_right == 0) //Se posso muovermi a destra
               TABLE.showFreeRectangle(posAfterMove.x, GORIGHT_COL); 
         }
      }
      else if (esito_left == 2){ //DEVO mangiare a SINISTRA
         posAfterMove.x = setRowonEat(posAfterMove);
         TABLE.showFreeRectangle(posAfterMove.x, posAfterMove.y);
         return 2;
      }
      return 0;
  }
  
   //Indica la colonna da colorare quando occorre mangiare
   protected int setColonEat(int col){
      return (col > coord.y) ? coord.y + 2 : coord.y - 2;
   }
      
   // Indica la riga da colorare quando occorre mangiare
   //Position indica il rettangolo nel quale si trova il pezzo da mangiare.
   protected int setRowonEat(Point position){
      return (coord.x - position.x > 0) ? coord.x - 2 : coord.x + 2; 
   }

   protected int setRowbyColor(){
      return (getColor() == Color.red) ? coord.x - 1 : coord.x + 1;
   }

   protected int checkMove(int row, final int COL_DIRECTION){
      if (TABLE.illegalMove(row) || TABLE.illegalMove(COL_DIRECTION))
         return 1; //Non può moversi.

      Point posToAnalize = new Point(row, COL_DIRECTION); //Indica le coordinate del rettangolo da analizzare
      Point posToSuggestion = new Point();
      int r = enemyPiece_inRect(posToAnalize);
      
      if(r == 2 && !arcTryEatArch(posToAnalize)){ //Enemy piece found but I don't know if I can eat (at the moment)
         posToSuggestion.x = setRowonEat(posToAnalize);
         posToSuggestion.y = setColonEat(posToAnalize.y);
         posAfterMove.y = posToSuggestion.y; //save new position to Move (for suggestion)
         return (canIeat(posToSuggestion)) ? 2 : 1; // 2: I MUST EAT ----- 1: I cannot eat or move
      }
      else if(r == 0) //if rectangle is free
         return 0; //I can move
      else
         return 1; //I cannot eat or move
   }
   
   //returns true if an archer try eat another archer, else returns false
   private boolean arcTryEatArch(Point position) {
      Rectangle rect = TABLE.getRectanglefromList(position.x, position.y);
      String enemyPieceClass = rect.getPiece().getClass().toString();
      String pieceClass = getClass().toString();
      
      if (pieceClass.equals("class Archer") && enemyPieceClass.equals("class Archer"))
         return true;
      return false;
   }

   protected int enemyPiece_inRect(Point position){
      Rectangle rect = TABLE.getRectanglefromList(position.x, position.y);
       
      if(rect.HasPiece()){ // Se c'è un pezzo
         // vediamo se è un pezzo avversario
         Piece tmp = (Piece) rect.getComponent(0);
         boolean pezzo_avversario = ! getColor().equals(tmp.getColor());
         return (pezzo_avversario) ? 2 : 1; // 2: pezzo avversario, forse è mangiabile 
      }                                      // 1: c'è un mio pezzo, non posso mangiarlo
      else
         return 0; //non c'è un pezzo, casella libera
   }

   //Controlla se in position c'è un pezzo o meno
   //Position indica il rettangolo su cui posizionarsi dopo aver mangiato
   protected boolean canIeat(Point position){
      if (TABLE.illegalMove(position.x) || TABLE.illegalMove(position.y))
         return false; //Non può mangiare perché il nemico è su un bordo
      
      return (enemyPiece_inRect(position) == 0) ? true : false;//true: il secondo rect è libero. SI DEVE MANGIARE.  
   }                          //false: Non posso mangiare. Il secondo rect è occupato da un pezzo rosso o verde

   

   // Getters and Setters methods..

   public void setCoord(int x, int y){
      coord.x = x;
      coord.y = y;
   }

   public Point getCoord() {
      return coord;
   }

   protected Color getColor(){
      return color;
   }

   public Piece getPtoMove(){
      return this;
   }

   public Player getOwner(){
      return owner;
   }

   public int getPoints(){
      return points;
   }

}