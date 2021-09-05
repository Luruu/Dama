package Game.GraphicObjects.Start;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

class Thrd implements Runnable {
    public void run(){
        CheckersStart.getInstance();
    }
}

public class Main{
    public static void main(String[] args) {
        final int numThreads = 5;
        ThreadPoolExecutor exec = (ThreadPoolExecutor)Executors.newFixedThreadPool(numThreads);
        exec.execute(new Thrd());
        exec.shutdown();
    }
}


