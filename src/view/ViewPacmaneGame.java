package view;

import Modelles.PacmanGame;

import javax.swing.*;

public class ViewPacmaneGame extends JFrame {
    public ViewPacmaneGame(PacmanGame pacmanGame){
        super("PACMAN GAME DE FATEH");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 800);
        PanelPacmanGame panelPacmanGame = new PanelPacmanGame(pacmanGame.getMaze());
        this.add(panelPacmanGame);
        this.setVisible(true);
    }

}
