package Game.Observer;

import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import Game.GameObjects.Players.Player;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
/**
 * <h1>TimerObservable</h1>
 * Represents the observervable which starts a TimerObsvervable with a new thread
 */
public class CreateTimerObservable{
    ThreadTimerObservable obv; 
    public CreateTimerObservable(ArrayList<Observer> players, int index, int timer_value){
        final int numThreads = 5;
        ThreadPoolExecutor exec = (ThreadPoolExecutor)Executors.newFixedThreadPool(numThreads);
        obv = new ThreadTimerObservable(players, index, timer_value);
        exec.execute(obv);
        exec.shutdown();
    }

    public void stop(){
        obv.stop();
    }

    public void reStartTimer(){
        obv.reStartTimer();
    }

}
/**
 * <h1>TimerObservable</h1>
 * Represents the observervable Timer.
 */
class ThreadTimerObservable implements Observable, ActionListener, Runnable {
    public   ArrayList<Observer> observer = new ArrayList<Observer>();
    private  Timer timer = null;
    private  int timerStop;
    private  int timerValueStart;
    private  int index;

    public void run(){
        timer = new Timer(1000, this);
        timer.start();
    }

    public ThreadTimerObservable(ArrayList<Observer> players, int index, int timer_value){
        this.index = index;
        timerStop = timer_value;
        timerValueStart = timer_value;
        for (Observer player : players)
            observer.add(player);
    }
    
    public void updateText(Object obj){
        observer.get(index).update(obj);
    }

    @Override
    public void notifyObserver() {
        if (timerStop != 0){ //notify panelInfo to update label text
            updateText(null);
        }
        else{
            observer.remove(index);
            Player p1 = (Player)observer.get(0);
            Player p2 = (Player)observer.get(1);
            Player winner;
            Boolean noWinner = null;

            if (p1.getPlayerScore() > p2.getPlayerScore()) //P1 is the winner
                winner = p1;
            else if ( p1.getPlayerScore() < p2.getPlayerScore()) //P2 is the winner
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

    public void stop(){
        timer.stop();
    }

    public void reStartTimer(){
        timerStop = timerValueStart;
        timer.start();
        updateText(timerStop);
    }
    
    
}