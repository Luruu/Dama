package Game.GraphicObjects.Table;
/**
 * <h1>Memento class</h1> 
 * Pattern Memento
 */
@FunctionalInterface
public interface Memento {
    public void restoreState() throws Exception;
}
