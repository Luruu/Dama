package Game.Windows.Table;
import javax.swing.*;

import Game.FactoryM.Players.Player;
import Game.Windows.CGO;

import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelInfo implements ActionListener{

    private JPanel panelInfo;
    private ArrayList<JComponent> jlabelList = new ArrayList<>();
 
    private int nmove = 0;


    public  PanelInfo(int n, int dim, Color c, LayoutManager lm, Player p1, Player p2){
        panelInfo = CGO.addPanel(n, dim, c, lm);
        panelInfo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        jlabelList.add(CGO.addLabel("Game Info",new Font("Verdana", Font.BOLD, 17)));
        jlabelList.add(CGO.addLabel("Player1: " + p1.getPlayerName()));
        jlabelList.add(CGO.addLabel(p1.getPlayerName() + "'s" + " score: "+ p1.getPlayerScore()));
        jlabelList.add(CGO.addLabel("Player2: " + p2.getPlayerName()));
        jlabelList.add(CGO.addLabel(p2.getPlayerName() + "'s" + " score: "+ p2.getPlayerScore()));
        jlabelList.add(CGO.addLabel("N° moves: " + nmove));
        jlabelList.add(CGO.addLabel("Turn: " + p1.getPlayerName()));
        jlabelList.add(CGO.addLabel("Timer: " + "boh"));
        jlabelList.add(CGO.addButton("Give up", this, "1"));
        jlabelList.add(CGO.addButton("Restart", this, "2"));


        for (JComponent jComponent : jlabelList) {
            panelInfo.add(jComponent);
        }
        
        panelInfo.setVisible(true);
    }

    public JPanel getpanelInfo(){
        return panelInfo;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        String action = arg0.getActionCommand();
        switch (action){
            case "1": 
                System.exit(1);
                break;
            case "2": 
                System.out.println("Ricomincia, MEMENTO");
                break;
        }
    }

    public  void updateScore(Player p){
        JLabel jl;
        jl = (p.getPlayerColor().equals(Color.RED)) ? (JLabel) jlabelList.get(2) : (JLabel) jlabelList.get(4);
        jl.setText(p.getPlayerName() + "'s" + " score: "+ p.getPlayerScore());
    }

    public void switchTurn(Player p){
        JLabel jl = (JLabel) jlabelList.get(6);
        jl.setText("Turn: " + p.getPlayerName());
        increaseNMOVE();
    }

    private void increaseNMOVE(){
        nmove++;
        JLabel jl = (JLabel) jlabelList.get(5);
        jl.setText("N° moves: " + nmove);
    }
}