import java.awt.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Creator factoryM = new ConcreteFactoryM();
        String name1 = "Renato", name2 = "Luca";
        CheckersTable table = CheckersTable.getInstance(8, 8, 96);
        Player p1 = (Player) factoryM.factoryMethod(name1,Color.red);
        Player p2 = (Player) factoryM.factoryMethod(name2,Color.green);

        table.startGame(p1, p2);
    }
}
