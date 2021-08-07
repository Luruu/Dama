public class InvokerList {
    private Command command;
    public InvokerList(Command command){
        this.command = command;
    }

    public void suggestion(int i , int j ){
        command.suggestion(i ,j);
    }
}
