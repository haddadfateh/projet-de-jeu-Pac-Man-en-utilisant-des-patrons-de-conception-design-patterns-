package view;

import Modelles.Game;
import Observable_Observer.Observable;
import Observable_Observer.Observer;

import javax.swing.*;
import java.awt.*;

public class ViewSimpleGame implements Observer {
    private JFrame fenetre;
    private JPanel mon_paneal;
    private JLabel label;
    public Game game;
    public ViewSimpleGame(Game game) {
        this.game = game;
        //addObserver
        this.game.AjouterObservateur(this);

        fenetre = new JFrame();
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setTitle("PACMANE GAME");
        fenetre.setSize(new Dimension(800, 800));

        mon_paneal = new JPanel();
        label = new JLabel("turn courrant " + game.get_turn());

        mon_paneal.add(label);
        fenetre.add(mon_paneal);
        fenetre.setVisible(true);
    }

    private void setTurn() {
        label.setText("le tour courant" + game.get_turn());
    }
    @Override
    public void Actualiser() {
        setTurn();

    }

    @Override
    public void Actualiser(Observable obs, Object arg) {
        setTurn();

    }
    //view actualiser
}
