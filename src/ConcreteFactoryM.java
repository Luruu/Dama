import java.awt.*;

public class ConcreteFactoryM implements Creator {
    public Object factoryMethod(String name, Color c) throws Exception {
        if (name.equals("pawn"))
            return new Pawn(c);
        else if (name.equals("archer"))
            return new Archer(c);
        else if (name.equals("checkers"))
            return new Checkers(c);
        else
            return new Player(c,name);
    }
}
