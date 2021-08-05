import java.awt.*;

public class ConcreteFactoryM extends Creator {
    protected  Piece factoryMethod(String name, Color c){
        if (name.equals("pawn"))
           return new Pawn(c);
        else
            return new Archer(c);
    }
}
