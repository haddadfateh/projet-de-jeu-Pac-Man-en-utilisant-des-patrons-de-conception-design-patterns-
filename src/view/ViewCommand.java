package view;

import Controllers.AbstractController;
import Modelles.Game;
import Observable_Observer.Observable;
import Observable_Observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ViewCommand implements Observer {
    private JFrame commandWindow;
    private JPanel commandPanel,buttonPanel,statusPanel,sliderPanel;
    private JButton restartButton, pauseButton,runButton,stepButton;
    private JSlider speedSlider;
    private JLabel speedLabel,turnLabel,scoreLabel;
    private GridLayout commandLayout,buttonLayout,statusLayout,sliderLayout;

    private AbstractController gameController;
    private Game pacmanGame;
    static final int min_sldr = 5;
    static final int max_sldr = 15;
    public static final int sld_int_sld = 5;

    public ViewCommand(AbstractController controller, Game game) {
        this.gameController = controller;
        this.pacmanGame = game;
        this.pacmanGame.AjouterObservateur(this);
        initButtons();
        initText();
        initialiser_slider();

        //paneau dinitalisation

        initialise_comnd_panel();
        initialsie_panel();
        initualis_le_statu_du_panel();
        init_ialise_bouton_panel();
        initialise_commane_wind();
        setListeners();
    }



    private void initButtons() {
        Icon icon_restart = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icones/icon_restart.png")));
        Icon icon_run = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icones/icon_run.png")));
        Icon icon_step = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icones/icon_step.png")));
        Icon icon_pause = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icones/icon_pause.png")));

        restartButton = new JButton(icon_restart);
        runButton = new JButton(icon_run);
        stepButton = new JButton(icon_step);
        pauseButton = new JButton(icon_pause);

        restartButton.setEnabled(false);
        stepButton.setEnabled(true);
        pauseButton.setEnabled(false);
    }

    private void setListeners() {
        runButton.addActionListener(e -> {
            runButton.setEnabled(false);
            restartButton.setEnabled(true);
            stepButton.setEnabled(false);
            pauseButton.setEnabled(true);

            gameController.Playe();
        });
        pauseButton.addActionListener(e -> {
            runButton.setEnabled(true);
            restartButton.setEnabled(true);
            stepButton.setEnabled(true);
            pauseButton.setEnabled(false);

            gameController.Pause();
        });
        restartButton.addActionListener(e -> {
            runButton.setEnabled(true);
            restartButton.setEnabled(false);
            stepButton.setEnabled(true);
            pauseButton.setEnabled(false);

            gameController.Restart();
        });
        stepButton.addActionListener(e -> {
            runButton.setEnabled(true);
            restartButton.setEnabled(true);
            stepButton.setEnabled(true);
            pauseButton.setEnabled(false);

            gameController.step();
        });
        speedSlider.addChangeListener(e -> {
            gameController.speed(speedSlider.getValue());
        });
    }

    private void initText() {
        turnLabel = new JLabel("", SwingConstants.CENTER);
        scoreLabel = new JLabel("", SwingConstants.CENTER); // Initialisation de scoreLabel
        scoreLabel.setForeground(new Color(0, 100, 0)); // Vert foncé
        setTurn(0, 0);
    }
    private void setTurn(int turn, int score) {
        turnLabel.setText("Turn : " + turn);
        scoreLabel.setText("Score : " + score);
        scoreLabel.setForeground(new Color(0, 100, 0)); // Vert foncé
    }

    private void initialiser_slider() {
        speedLabel = new JLabel("Number de tour par seconde", JLabel.CENTER);
        speedSlider = new JSlider(JSlider.HORIZONTAL, min_sldr, max_sldr, sld_int_sld);
        speedSlider.setMajorTickSpacing(1);
        speedSlider.setMinorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
    }

    private void initialsie_panel() {
        sliderPanel = new JPanel();
        sliderLayout = new GridLayout(2, 1);
        sliderPanel.setLayout(sliderLayout);

        sliderPanel.add(speedLabel);
        sliderPanel.add(speedSlider);
    }

    private void initualis_le_statu_du_panel() {
        statusPanel = new JPanel();
        statusLayout = new GridLayout(1, 3); // Modification ici pour inclure le label de score
        statusPanel.setLayout(statusLayout);

        statusPanel.add(sliderPanel);
        statusPanel.add(turnLabel);
        statusPanel.add(scoreLabel); // Ajout de scoreLabel au panel
    }

    private void init_ialise_bouton_panel() {
        buttonPanel = new JPanel();
        buttonLayout = new GridLayout(1, 4);
        buttonPanel.setLayout(buttonLayout);

        buttonPanel.add(restartButton);
        buttonPanel.add(runButton);
        buttonPanel.add(stepButton);
        buttonPanel.add(pauseButton);
    }

    private void initialise_comnd_panel() {
        commandPanel = new JPanel();
        commandLayout = new GridLayout(2, 1);
        commandPanel.setLayout(commandLayout);

        commandPanel.add(buttonPanel);
        commandPanel.add(statusPanel);
    }

    private void initialise_commane_wind() {
        commandWindow = new JFrame();
        commandWindow.setTitle("COMMANDE DU JEU PACMAN GAME");
        commandWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        commandWindow.setSize(new Dimension(700, 700));

        Dimension windowSize = commandWindow.getSize();



        commandWindow.add(commandPanel);
        commandWindow.setVisible(true);
    }

    @Override
    public void Actualiser() {
        /*************/

    }

    @Override
    public void Actualiser(Observable obs, Object arg) {
/*************************/
    }
}
