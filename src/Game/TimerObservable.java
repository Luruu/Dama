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


    public TimerObservable(ArrayList<Observer> p, int indice, int n_sec){
        index = indice;
        timerStop = n_sec;
        timer = new Timer(1000, this);
        timer.start();

        for (Observer player : p) {
            observer.add(player);
        }
    }

    @Override
    public void notifyObserver() {
        if (timerStop != 0 ){ //notify panelInfo to update label text
            observer.get(index).update(null);
        }
        else{
            observer.remove(index);
            Player winner;
            Boolean flag = null;
            //Possibile violazione di SINGOLA RESPONSABILITA' però abbiamo la necessità di notificare il player corretto.
            if ( ((Player)observer.get(0)).getPlayerScore() > ((Player)observer.get(1)).getPlayerScore()  )
                winner = (Player)observer.get(0);
            else if ( ((Player)observer.get(0)).getPlayerScore() < ((Player)observer.get(1)).getPlayerScore()  )
                winner = (Player)observer.get(1);
            else{
                winner = (Player)observer.get(0);
                flag = true;
            }
            winner.update(flag); 
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
