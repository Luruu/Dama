public class ConcreteCommand implements Command{
    private Piece receiver = null;
    private Rectangle[][] rectangles;

    public ConcreteCommand(Rectangle[][] rectangleList){
        rectangles = rectangleList;
    }

    @Override
    public void suggestion() {
        //Devo trovare il pezzo selezionato dalla lista di rettangoli,
        // impostarlo a receiver e inviargli il comando
        // receiver.Do();
    }
}
