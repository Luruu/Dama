package Game.Windows.Start;
public class MyThread implements Runnable {
    
    public void run(){
        CheckersStart.getIstance();
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new MyThread());
        t1.start();
    }

}
