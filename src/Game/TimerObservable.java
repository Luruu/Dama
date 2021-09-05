package Game;

import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import Game.ObjGamepkg.Players.Observer;
import Game.ObjGamepkg.Players.Player;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class TimerObservable{
    public TimerObservable(ArrayList<Observer> players, int index, int timer_value){
        final int numThreads = 5;
        ThreadPoolExecutor exec = (ThreadPoolExecutor)Executors.newFixedThreadPool(numThreads);
        exec.execute(new ThreadTimerObservable(players, index, timer_value));
        exec.shutdown();
    }

    public void stop(){
        ThreadTimerObservable.stop();
    }
    
}
class ThreadTimerObservable implements Observable, ActionListener, Runnable {
    public ArrayList<Observer> observer = new ArrayList<Observer>();
    private static Timer timer = null;
    private int timerStop;
    private int index;

    public void run(){
        timer = new Timer(1000, this);
        timer.start();
    }

    public ThreadTimerObservable(ArrayList<Observer> players, int index, int timer_value){
        this.index = index;
        timerStop = timer_value;
        for (Observer player : players)
            observer.add(player);
    }

    @Override
    public void notifyObserver() {
        if (timerStop != 0){ //notify panelInfo to update label text
            observer.get(index).update(null);
        }
        else{
            observer.remove(index);
            Player p1 = (Player)observer.get(0);
            Player p2 = (Player)observer.get(1);
            Player winner;
            Boolean noWinner = null;
            //Possibile violazione di SINGOLA RESPONSABILITA' però abbiamo la necessità di notificare il player corretto.
            if (p1.getPlayerScore() > p2.getPlayerScore())
                winner = p1;
            else if ( p1.getPlayerScore() < p2.getPlayerScore())
                winner = p2;
            else{ // p1.getPlayerScore() == p2.getPlayerScore()
                winner = p1; //p1 is not the winner. 
                noWinner = true; // there is no winner
            }
            winner.update(noWinner); 
            stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        notifyObserver();
         timerStop -= 1;
    }

    public static void stop(){
        timer.stop();
    }
    
}