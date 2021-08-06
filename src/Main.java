//import javax.swing.*; Attualmente inutile


public class Main {
    public static void main(String[] args){
        String name1 = "Renato", name2 = "Luca";
        CheckersTable table = CheckersTable.getInstance(8, 8, 96, name1, name2);
    }
}
