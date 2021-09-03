package Game;

import Game.FactoryM.Players.Observer;
import Game.FactoryM.Players.Player;

import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class TimerObservable implements Observable, ActionListener {
    public ArrayList<Observer> observer = new ArrayList<Observer>();
    private Timer timer = null;
    private int timerStop;
    private int index;


    public TimerObservable(ArrayList<Observer> p, int indice, int timer_value){
        index = indice;
        timerStop = timer_value;
        timer = new Timer(1000, this);
        timer.start();

        for (Observer player : p)
            observer.add(player);
    }

    @Override
    public void notifyObserver() {
        if (timerStop != 0 ){ //notify panelInfo to update label text
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

    public void stop(){
        timer.stop();
    }
    
}
