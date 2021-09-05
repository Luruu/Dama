package Game.FactoryM;
import java.awt.*;

import Game.FactoryM.Pieces.*;
import Game.FactoryM.Players.*;


public class ConcreteFactoryM implements Factory {
    @Override
    public ObjGame factoryMethod(String name, Color color, Player pl) throws Exception {
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
