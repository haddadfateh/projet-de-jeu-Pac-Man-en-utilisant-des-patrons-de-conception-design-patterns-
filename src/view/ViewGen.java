package view;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Modelles.PacmanGame;
import Modelles.Etat;

import Observable_Observer.Observable;
import Observable_Observer.Observer;

import Controllers.ControllerPacmanGAme;

public class ViewGen implements Observer {

	private static ViewGen uniqueInstance;

	private ControllerPacmanGAme _controller;
	public PacmanGame _motor;

	private PanelPacmanGame _panelPacmanGame;
	private JFrame window,fenetre_pricipale_du_Game;
	private JPanel panelPrincipal,panelHaut,panelBas,panelBasGauche,panelGame;
	private GridLayout layoutPrincipal,layoutHaut,layoutBas,layoutBasGauche;
	private JButton btnRestart,btnPause,btnRun,btnStep;
	private JSlider slider;
	private JLabel label_cont,sliderLabel;
	private JLabel label_resum;

	static final int slider_min = 0;
	static final int slider_max = 10;
	static final int slider_init = 2;

	private String chemin_maze;

	private ViewGen(ControllerPacmanGAme controller, PacmanGame motor, boolean modeControl) {

		_panelPacmanGame = new PanelPacmanGame(motor.getMaze());


		_controller = controller;
		_motor = motor;
		_motor.AjouterObservateur(this);


		initBtn();
		initText();
		initGameText();
		initSlider();


		initPanelBasGauche();
		initPanelBas();
		initPanelHaut();
		initPanelPrincipal();


		initPanelGame();

		initWindow();
		initAffichage();

		initListener();

		window.setVisible(true);
		fenetre_pricipale_du_Game.setVisible(true);



	}

	public static ViewGen getInstance(ControllerPacmanGAme controller, PacmanGame motor, boolean modeControl) {

		if(uniqueInstance == null) {
			uniqueInstance = new ViewGen(controller, motor, modeControl);

		}

		return uniqueInstance;


	}



	@Override
	public void Actualiser() {
		setTurn(_motor.get_turn(), _motor.getScore());
		setLabel_resum("Tour " + _motor.get_turn() + " etat courant "
				+ _motor.get_Etat_TOstring());

		if (_motor.get_etat_Actuelle() != Etat.GameOver) {
			if (_motor.get_etat_Actuelle() != Etat.Gagne) {
				//jactualise les nouvelle valeur du panel avec les donnée du pacman game
				_panelPacmanGame.setPacmans_pos(_motor.getPostionPacman());
				_panelPacmanGame.setGhosts_pos(_motor.getPostionFantom());
				_panelPacmanGame.setGhostsScarred(_motor.isGhosts_Scarred());

				_panelPacmanGame.setMaze(_motor.getMaze());
				_panelPacmanGame.repaint();

				if (fenetre_pricipale_du_Game != null) {
					fenetre_pricipale_du_Game.getContentPane().add(_panelPacmanGame);
					fenetre_pricipale_du_Game.setVisible(true);
				}
			} else {
				btnRun.setEnabled(false);
				btnRestart.setEnabled(true);
				btnPause.setEnabled(false);
				btnStep.setEnabled(false);
			}
		} else {
			btnRun.setEnabled(false);
			btnRestart.setEnabled(true);
			btnPause.setEnabled(false);
			btnStep.setEnabled(false);
		}

	}

	@Override
	public void Actualiser(Observable obs, Object arg) {

	}
	//aficher letat actuel de nombre de turn et nombre de point courant


	public void setTurn(int turn, int point) {
		label_cont.setText("nombre de laps : " + turn +  " Point Gagné:" + point);
	}

	public void setLabel_resum(String text) {
		label_resum.setText(text);
	}

	//gestion des bouton en autorisant les est et bloquant les autre selon
	//la demande de notre projet

	private void initListener() {
		btnRestart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnRun.setEnabled(true);
				btnRestart.setEnabled(false);
				btnPause.setEnabled(false);
				btnStep.setEnabled(true);

				_controller.restart();
			}
		});
		btnRun.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnRun.setEnabled(false);
				btnRestart.setEnabled(false);
				btnPause.setEnabled(true);
				btnStep.setEnabled(false);

				_controller.start();
			}
		});

		btnStep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnRun.setEnabled(true);
				btnRestart.setEnabled(true);
				btnPause.setEnabled(false);
				btnStep.setEnabled(true);

				_controller.step();
			}
		});
		btnPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnRun.setEnabled(true);
				btnRestart.setEnabled(true);
				btnPause.setEnabled(false);
				btnStep.setEnabled(true);

				_controller.pause();
			}
		});

		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				_controller.speed(slider.getValue()+1);
			}
		});
	}

	/**//**//**//**//**//**//**//**//**//**//**//**/// inits

	public void initWindow() {
		window = new JFrame();
		window.setTitle("COMMANDE DU JEU");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(new Dimension(600, 300));

		Dimension windowSize = window.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x  - (int)(windowSize.width*1.3) ;
		int dy = centerPoint.y - windowSize.height / 2;
		window.setLocation(dx, dy);

		window.add(panelPrincipal);
	}

	public void initAffichage() {
		fenetre_pricipale_du_Game = new JFrame();
		fenetre_pricipale_du_Game.setTitle("BIENVENU DANS LE PACMAN GAME FATEH");
		fenetre_pricipale_du_Game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre_pricipale_du_Game.setSize(_panelPacmanGame.getMaze().getSizeX() * 50,
				_panelPacmanGame.getMaze().getSizeY() * 50);

		Dimension windowSize = fenetre_pricipale_du_Game.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x ;
		int dy = centerPoint.y - windowSize.height / 2;
		fenetre_pricipale_du_Game.setLocation(dx, dy);

		fenetre_pricipale_du_Game.add(_panelPacmanGame);
		fenetre_pricipale_du_Game.setVisible(true);
	}

	public void initPanelPrincipal() {
		panelPrincipal = new JPanel();
		layoutPrincipal = new GridLayout(2, 1);
		panelPrincipal.setLayout(layoutPrincipal);

		panelPrincipal.add(panelHaut);
		panelPrincipal.add(panelBas);

	}

	public void initPanelHaut() {
		panelHaut = new JPanel();
		layoutHaut = new GridLayout(1, 4);
		panelHaut.setLayout(layoutHaut);

		panelHaut.add(btnRestart);
		panelHaut.add(btnRun);
		panelHaut.add(btnStep);
		panelHaut.add(btnPause);
	}

	public void initPanelBas() {
		panelBas = new JPanel();
		layoutBas = new GridLayout(1, 2);
		panelBas.setLayout(layoutBas);

		panelBas.add(panelBasGauche);
		panelBas.add(label_cont);
	}

	public void initPanelBasGauche() {
		panelBasGauche = new JPanel();
		layoutBasGauche = new GridLayout(2, 1);
		panelBasGauche.setLayout(layoutBasGauche);

		panelBasGauche.add(sliderLabel);
		panelBasGauche.add(slider);
	}

	public void initPanelGame() {
		panelGame = new JPanel();

		panelGame.add(label_resum);
	}

	public void initBtn() {
		Icon icon_restart = new ImageIcon("src/icones/icon_restart.png");
		Icon icon_run = new ImageIcon("src/icones/icon_run.png");
		Icon icon_step = new ImageIcon("src/icones/icon_step.png");

		Icon icon_pause = new ImageIcon("src/icones/icon_pause.png");

		btnRestart = new JButton(icon_restart);
		btnRun = new JButton(icon_run);
		btnStep = new JButton(icon_step);
		btnPause = new JButton(icon_pause);

		btnRestart.setEnabled(false);
		btnStep.setEnabled(true);
		btnPause.setEnabled(false);
	}

	public void initSlider() {
		sliderLabel = new JLabel("Nombre turn per second", JLabel.CENTER);
		slider = new JSlider(JSlider.HORIZONTAL, slider_min, slider_max,
				slider_init);

		slider.setMajorTickSpacing(1);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
	}

	public void initText() {
		label_cont = new JLabel("", SwingConstants.CENTER);
		setTurn(0, 0);
	}

	public void initGameText() {
		label_resum = new JLabel("", SwingConstants.CENTER);
		setLabel_resum("Situation initiale");
	}

}
