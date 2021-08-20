import java.awt.*;

public class ConcreteFactoryM implements Creator {
    public Object factoryMethod(String name, Color c, Player p) throws Exception {
        if (name.equals("pawn"))
            return new Pawn(c, p);
        else if (name.equals("archer"))
            return new Archer(c, p);
        else if (name.equals("checkers"))
            return new Checkers(c, p);
        else
            return new Player(c,name);
    }
}
