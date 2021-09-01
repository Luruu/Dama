package Game.Windows.Start;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main implements Runnable {
    
    public void run(){
        //CheckersStart.getIstance();
        for (int i = 0; i < 60; i++) {
            System.out.println(i + " " + Thread.currentThread().getId());
        }
    }

}

class ThreadPool{
    public static void main(String[] args) {
        ThreadPoolExecutor tpe = (ThreadPoolExecutor) Executors.newFixedThreadPool(6);
        tpe.execute(new Main());
        tpe.shutdown();
    }
}
