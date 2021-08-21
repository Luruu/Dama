public interface Command {
    void suggestions(Piece p);
    void clear();
    void move(Rectangle r,int i, int j) throws Exception;
}
