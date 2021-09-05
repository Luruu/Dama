package Game.ObjGamepkg;
import java.awt.*;

import Game.ObjGamepkg.Players.*;

public interface Factory {
        public abstract ObjGame factoryMethod(String name, Color c, Player p) throws Exception;
    }
