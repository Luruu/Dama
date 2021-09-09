package Game.GameObjects.FactoryM;
import java.awt.*;

import Game.GameObjects.Players.*;
import Game.GraphicObjects.Table.IstanceException;

/**
 * Interface factoryMethod.
 */
public interface Factory {
        public abstract ElementGame factoryMethod(String name, Color c, Player p) throws NPlayerExceededException, IstanceException;
    }
