//import javax.swing.*; Attualmente inutile

import java.awt.*;

public class Main {
    public static void main(String[] args){
        String name1 = "Renato";
        String name2 = "Luca";
        CheckersTable campo = CheckersTable.getInstance(8, 8,96, name1,name2);
        Pawn pedina = new Pawn(Color.gray);

    }
}
