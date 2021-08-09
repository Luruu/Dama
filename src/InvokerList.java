import java.awt.*;

public class InvokerList {
    private Command command;
    public InvokerList(Command command){
        this.command = command;
    }

    public void suggestion(Piece p){
        command.suggestion(p);
    }

    public void clear() {
        command.clear();
    }
}
