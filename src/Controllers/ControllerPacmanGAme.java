package Controllers;


import Modelles.*;


public class ControllerPacmanGAme {//principale
	public PacmanGame PacmanGame;
	private static ControllerPacmanGAme uniqueInstance;
	//mon constructeur
	private ControllerPacmanGAme(PacmanGame pacmangame) {
		this.PacmanGame = pacmangame;
	}
//utilisation de design pattern du cour singleton unique instance
	public static ControllerPacmanGAme getInstance(PacmanGame pacmangame) {
		if(uniqueInstance == null)
		{uniqueInstance = new ControllerPacmanGAme(pacmangame);}
		return uniqueInstance;
	}//
	//controller selon la situation definie das gm
	public void pause() {
		if ((PacmanGame.get_Etat_TOstring() == "Run") || (PacmanGame.get_Etat_TOstring() == "Pause")) {PacmanGame.Pause();}}
	public void start() { // demarre le jeu
		if (PacmanGame.get_Etat_TOstring() == "Init") { /* si on reinitialise*/ PacmanGame.launch();
		} else {if (PacmanGame.get_Etat_TOstring() == "Pause") { /*si on etait en pause*/PacmanGame.Pause();
		}}}

	public void restart() {
		if ((PacmanGame.get_Etat_TOstring() == "Init") || (PacmanGame.get_Etat_TOstring() == "GameOver")
				|| (PacmanGame.get_Etat_TOstring() == "Pause")) {PacmanGame.init();}}
	public void step() {
		if ((PacmanGame.get_Etat_TOstring() == "Pause") || (PacmanGame.get_Etat_TOstring() == "Init")) {PacmanGame.step();}
	}
//setter regllage du spped
	public void speed(int speed) {
		PacmanGame.set_speed(1000 / speed);
	}

}
