
public class ConcreteCommand implements Command{
    private CheckersTable checkTable;
    public ConcreteCommand(CheckersTable ct) {
        checkTable = ct;
    }

    @Override
    public void suggestions(Piece pClicked) {
        checkTable.suggestions(pClicked);
    }

    public void clear(){
        checkTable.clearSuggestions();
    }
    
    public void move(Rectangle r, int i, int j) throws Exception{ 
        checkTable.move(r);
    }
}
