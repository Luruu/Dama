public interface Command {
    void suggestions(Piece pClicked);
    void clear();
    void move(Rectangle r,int i, int j) throws Exception;
}
