import java.awt.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Creator factoryM = new ConcreteFactoryM();
        String name1 = "Renato", name2 = "Luca";
        final int DIM = 8;
        //N.B: Window sizes are (DIM * Rectangle.DIM_RECT, Rectangle.DIM * DIM_RECT)
        CheckersTable table = CheckersTable.getInstance(DIM, DIM);
        Player pl1 = (Player) factoryM.factoryMethod(name1, Color.red, null);
        Player pl2 = (Player) factoryM.factoryMethod(name2, Color.green, null);
        table.startGame(pl1, pl2);
    }
}
