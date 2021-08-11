// import java.awt.*; MAI USATO

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
    public void move(Rectangle r, int i , int j){
        command.move(r,i,j);
    }
}
