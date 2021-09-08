package Game.GraphicObjects.Table;
/**
 * Pattern Memento
 */
@FunctionalInterface
public interface Memento {
    public void restoreState() throws Exception;
}
