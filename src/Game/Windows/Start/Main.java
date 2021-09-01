package Game.Windows.Start;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main implements Runnable {
    
    public void run(){
        CheckersStart.getIstance();
    }
}

class ThreadPool{
    public static void main(String[] args) {
    final int numThreads = 5;
    ThreadPoolExecutor exec = (ThreadPoolExecutor)Executors.newFixedThreadPool(numThreads);
    exec.execute(new Main());
    exec.shutdown();
  }
}
