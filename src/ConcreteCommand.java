public class ConcreteCommand implements Command{
    private Piece receiver = null;
    //private Rectangle[][] rectangles;
    private CheckersTable checkTable;
    public ConcreteCommand(CheckersTable ct) {
        checkTable = ct;
    }

    @Override
    public void suggestion(int i, int j) {
        checkTable.suggestion(i,j);
    }
}
