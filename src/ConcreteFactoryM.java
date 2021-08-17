import java.awt.*;

public class ConcreteFactoryM implements Creator {
    public  Object factoryMethod(String name, Color c) throws Exception {
        if (name.equals("pawn"))
            return new Pawn(c, (c == Color.green) ? "/images/PawnGreen.png" : "/images/PawnRed.png");
        else if (name.equals("archer"))
            return new Archer(c, (c == Color.green) ? "/images/ArcherGreen.png" : "/images/ArcherRed.png");
        else
            return new Player(c,name);
    }
}
