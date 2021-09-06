package Game.GraphicObjects.Start;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main{
    public static void main(String[] args) {
        final int numThreads = 5;
        ThreadPoolExecutor exec = (ThreadPoolExecutor)Executors.newFixedThreadPool(numThreads);
        exec.execute(new Thread(()->CheckersStart.getInstance()));
        exec.shutdown();
    }
}
