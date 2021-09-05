package Game.GraphicObjects.Table;

@FunctionalInterface
public interface Memento {
    public void restoreState() throws Exception;
}
