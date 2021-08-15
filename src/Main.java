import java.awt.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Creator factoryM = new ConcreteFactoryM();
        String name1 = "Renato", name2 = "Luca";
        final int DIM = 8, DIM_RECT = 96;

        CheckersTable table = CheckersTable.getInstance(DIM, DIM, DIM_RECT);
        Player p1 = (Player) factoryM.factoryMethod(name1,Color.red);
        Player p2 = (Player) factoryM.factoryMethod(name2,Color.green);
        table.startGame(p1, p2);
    }
}
