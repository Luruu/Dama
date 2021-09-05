package Game.FactoryM;
import java.awt.*;

import Game.FactoryM.Players.*;

public interface Factory {
        public abstract ObjGame factoryMethod(String name, Color c, Player p) throws Exception;
    }
