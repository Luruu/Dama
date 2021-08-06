public class InvokerList {
    private Command command = null;
    public InvokerList(Command command){
        this.command = command;
    }

    public void suggestion(){
        command.suggestion();
    }
}
