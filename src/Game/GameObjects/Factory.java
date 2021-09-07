package Game.GameObjects;
import java.awt.*;

import Game.GameObjects.Players.*;

//It's Creator Factory
public interface Factory {
        public abstract ElementGame factoryMethod(String name, Color c, Player p) throws Exception;
    }
