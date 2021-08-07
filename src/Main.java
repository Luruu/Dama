//import javax.swing.*; Attualmente inutile


import java.awt.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String name1 = "Renato", name2 = "Luca";
        CheckersTable table = CheckersTable.getInstance(8, 8, 96);
        Player p1 = new Player(Color.red, name1);
        Player p2 = new Player(Color.green, name2);
        table.startGame(p1, p2);
    }
}
