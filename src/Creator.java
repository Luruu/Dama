import java.awt.*;

public interface Creator {
        public abstract Object factoryMethod(String name, Color c, Player p) throws Exception;
    }
