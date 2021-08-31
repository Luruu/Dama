package Game.Windows.Table;
import Game.FactoryM.Pieces.*;

public class InvokerList {
    private Command command;
    public InvokerList(Command command){
        this.command = command;
    }

    public void suggestions(Piece p){
        command.suggestions(p);
    }

    public void clear() {
        command.clear();
    }
    public void move(Box b, int i , int j) throws Exception{
        command.move(b,i,j);
    }
}
