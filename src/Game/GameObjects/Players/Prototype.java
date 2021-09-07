package Game.GameObjects.Players;

/**
 * This is a marker interface. Those who implement it can create an object from themselves.
 */
public interface Prototype extends Cloneable {
    public abstract Object clone() throws CloneNotSupportedException;
}
