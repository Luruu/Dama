package Game.GraphicObjects.Start;

public enum EnumIndices {
    FIRST(0),       SECOND(1),      THIRD(2), 
    FOURTH(3),      FIFTH(4),       SIXTH(5), 
    SEVENTH(6),     EIGHTH(7),      NINTH(8), 
    TENTH(9),       ELEVENTH(10),   TWELFTH(11);

    private final int index;

    private EnumIndices(int index){
        this.index = index;
    }
    
    public int getValue() {
        return index;
    }
}
