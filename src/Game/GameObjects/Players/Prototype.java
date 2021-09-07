package Game.GameObjects.Players;

public interface Prototype extends Cloneable {
    public abstract Object clone() throws CloneNotSupportedException;
}
