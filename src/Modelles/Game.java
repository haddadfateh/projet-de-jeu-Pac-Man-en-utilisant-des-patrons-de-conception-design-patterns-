package Modelles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Observable_Observer.*;

public abstract class Game implements Runnable, Observable, Serializable {

	protected int _turn;//nombre de tourn du jeu
	protected int _max_Turn;//a charger dans le constructeur
	protected boolean _is_Running;//un boolean vrai ou faut
	protected long _speed;
	protected Etat _etat_Actuelle;//pour letat en temps reel
	transient Thread thread;
	protected transient List<Observer> observers;//TD observateur _ observable
	//Constructeur pour game avec 2 parametrre ou avec 1 seul ou jinitialise automatiquemment a 1000 speed

	public Game(int mxtrn, long spd) {
		this._max_Turn = mxtrn;
		this._speed = spd;
		observers = new ArrayList<Observer>();
	}

	public Game(int mxtrn) {
		this._max_Turn = mxtrn;
		this._speed = 1000;
		initGame();
	}

	public abstract void initGame();

	public void launch() {
		_is_Running = true;
		thread = new Thread(this);
		thread.start();//lancer le thread
	}

	public void init() {
		_turn = 0;
		_is_Running = false;
		_etat_Actuelle = Etat.Start;//etat start
		Reinitialise_Game();//je sais pas si je la garde
		notifyObservers();//ne pas oublier a chaque changement de faire le notify...

	}

	public abstract void Reinitialise_Game();
	

	public void step() {
		_turn++;
		if (!GameContinue()) {
			takeTurn();
		} else {
			gameOver();
			_etat_Actuelle = Etat.GameOver;
		}
		notifyObservers();
	}

	public abstract void takeTurn();

	public abstract void gameOver();

	public void victoire(){
		_is_Running = false;
		_etat_Actuelle = Etat.Gagne;
	}
	
	public abstract boolean GameContinue() ;

	public void run() {
		while ((!GameContinue()) && _is_Running) {
			_etat_Actuelle = Etat.Run;
			
			step();
			
			if(_speed != -1) {
				try {
					Thread.sleep(_speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
		}
	}
	//on aura besoin de cette fonction par la suite pour les manip et principalement
	//pour stopper le jeux quand un pacman est mort GameOver
	public String get_Etat_TOstring() {

		switch (_etat_Actuelle) {
			case Start:  return  "Init";
			case Run:  return "Run";
			case Pause:  return "Pause";
			default:  return "!! Pas Possible!!!";
		}

	}


	
	public void Pause() {
		if (_is_Running) {
			_is_Running = false;
			_etat_Actuelle = Etat.Pause;
		} else {
			_is_Running = true;
			_etat_Actuelle = Etat.Run;
			launch();
		}
		notifyObservers();
	}


	//getter et setter utiliser dans la suite
	public int get_turn() {
		return _turn;
	}

	public int get_max_Turn() {
		return _max_Turn;
	}

	public boolean is_is_Running() {
		return _is_Running;
	}

	public void setIsRunning(boolean bool) {
		_is_Running = bool;
	}

	public long get_speed() {
		return _speed;
	}

	public void set_speed(long speed) {
		_speed = speed;
	}

	public Etat get_etat_Actuelle() {
		return _etat_Actuelle;
	}
	//observer

	@Override
	public void AjouterObservateur(Observer o) {
		observers.add(o);
	}

	@Override
	public void SupprimerObservateur(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for (Observer o : observers)
			o.Actualiser();
	}



}
