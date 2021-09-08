package Game.GameObjects.FactoryM;
import java.awt.*;

import Game.GameObjects.Players.*;

/**
 * Interface factoryMethod.
 */
public interface Factory {
        public abstract ElementGame factoryMethod(String name, Color c, Player p) throws Exception;
    }
