package Game.GameObjects;
import java.awt.*;

import Game.GameObjects.Pieces.*;
import Game.GameObjects.Players.*;

/**
 * Represents the concrete factory of the pattern factory methods.
 * This class is delegated the choice of the concrete type to instantiate.
 */
public class ConcreteFactoryM implements Factory {
    @Override
    public ElementGame factoryMethod(String name, Color color, Player pl) throws Exception {
        switch (name) {
            case "pawn":
                return new Pawn(color, pl);
            case "wizard":
                return new Wizard(color, pl);
            case "checkers":
                return new Checkers(color, pl);
            default:
                return new Player(color, name); //name here is name of player, not string "player"
        }
    }
}
