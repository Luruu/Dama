package Game;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import Game.GraphicObjects.Start.CheckersStart;

/**
 * <h1>Main class</h1>
 * Create a thread pool and start the start panel.
 */
public class Main{
    public static void main(String[] args) {
        final int numThreads = 5;
        ThreadPoolExecutor exec = (ThreadPoolExecutor)Executors.newFixedThreadPool(numThreads);
        exec.execute(new Thread(()->CheckersStart.getInstance()));
        exec.shutdown();
    }
}
