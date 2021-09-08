package Game.GraphicObjects.Table;
import javax.swing.*;

import Game.Observer;
import Game.GameObjects.Players.Player;
import Game.GraphicObjects.GraphicComponents;

import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * <h1>PanelInfo class</h1> 
 * This class abstracts the panel containing information about the course of the game, including the timer.
 */
public class PanelInfo implements GraphicComponents, ActionListener, Observer {

    private JPanel panelInfo;
    private ArrayList<JComponent> jComponentList;
    private int nmove;
    private int time;


    /**
     * Costructor.
     * @param n widht of the panel.
     * @param dim height of the panel.
     * @param c background color.
     * @param lm LayoutManager of the panel.
     * @param p1 first player.
     * @param p2 second player.
     * @param n_sec number of seconds of the timer.
     */
    public PanelInfo(int n, int dim, Color c, LayoutManager lm, Player p1, Player p2, int n_sec){
        nmove = 0;
        time = 3;
        time = n_sec;
        panelInfo = addPanel(n, dim, c, lm);
        panelInfo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        jComponentList = new ArrayList<>();
        jComponentList.add(addLabel("Game Info",new Font("Verdana", Font.BOLD, 17)));
        jComponentList.add(addLabel(p1.getPlayerName()));
        jComponentList.get(1).setForeground(p1.getPlayerColor());
        jComponentList.add(addLabel(p1.getPlayerName() + "'s" + " score: "+ p1.getPlayerScore()));
        jComponentList.add(addLabel(p2.getPlayerName()));
        jComponentList.get(3).setForeground(p2.getPlayerColor());
        jComponentList.add(addLabel(p2.getPlayerName() + "'s" + " score: "+ p2.getPlayerScore()));
        jComponentList.add(addLabel("N° moves: " + nmove));
        jComponentList.add(addLabel("Turn: " + p1.getPlayerName()));
        jComponentList.get(6).setForeground(p1.getPlayerColor());
        jComponentList.add(addLabel("Timer: " + time));
        jComponentList.add(addButton("Give up", this, "1"));
        jComponentList.add(addButton("Restart", this, "2"));


        for (JComponent jComponent : jComponentList) {
            panelInfo.add(jComponent);
        }
        
        panelInfo.setVisible(true);
    }

    /**
     * This method associates operations with buttons on the panel.
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        String action = arg0.getActionCommand();
        CheckersTable ct= null;
        try {
            ct =  CheckersTable.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (action){
            case "1":
                String name = null;
                name = ct.getActivePlayer().getPlayerName();
                JOptionPane.showMessageDialog(null, "Game over! " + name +" gave up.");
                ct.returnToStart();
                ct.stopTimer();
                break;
            case "2":
                try {
                    ct.stopTimer();
                    ct.reStartGame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * This method update the score of the match.
     * @param p player to update the score to.
     */
    public  void updateScore(Player p){
        JLabel jl;
        jl = (p.getPlayerColor().equals(Color.RED)) ? (JLabel) jComponentList.get(2) : (JLabel) jComponentList.get(4);
        jl.setText(p.getPlayerName() + "'s" + " score: "+ p.getPlayerScore());
    }

    /**
     * This method changes the player's turn.
     * @param p player who can move the pieces.
     */
    public void switchTurn(Player p){
        JLabel jl = (JLabel) jComponentList.get(6);
        jl.setText("Turn: " + p.getPlayerName());
        jl.setForeground(p.getPlayerColor());
        increaseNMOVE();
        
        
    }

    /**
     * This method updates the NMOVE field of the panel.
     */
    private void updateNMOVE(){
        JLabel jl = (JLabel) jComponentList.get(5);
        jl.setText("N° moves: " + nmove);
    }

    /**
     * This method increases the number of movements.
     */
    private void increaseNMOVE(){
        nmove++;
        updateNMOVE();
    }

    /**
     * Reset the number of movements.
     */
    public void reSetNMOVE(){
        nmove = 0;
        updateNMOVE();
    }

    /**
     * This method updates the timer value within the panel.
     */
    @Override
    public void update(Object obj){
        if (obj instanceof Integer){
            time = (int)obj;
            time++;
        }
        time--;
        JLabel jl = (JLabel) jComponentList.get(7);
        jl.setText("Timer: " + time);
    }

    /**
     *  Returns the panel with information on the game in progress.
     * @return the panel with information on the game in progress.
     */
    public JPanel getpanelInfo(){
        return panelInfo;
    }

}