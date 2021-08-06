import java.awt.*;

public class ConcreteFactoryM extends Creator {
    protected  Piece factoryMethod(String name, Color c){
        if (name.equals("pawn"))
            return new Pawn(c, (c== Color.green) ? "/images/PawnGreen.png" : "/images/PawnRed.png");
        else
            return new Archer(c, (c == Color.green) ? "/images/ArcherGreen.png" : "/images/ArcherRed.png");
    }
}
