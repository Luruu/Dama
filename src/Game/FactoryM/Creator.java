package Game.FactoryM;
import java.awt.*;

import Game.FactoryM.Players.*;

public interface Creator {
        public abstract Object factoryMethod(String name, Color c, Player p) throws Exception;
    }
