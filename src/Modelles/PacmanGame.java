package Modelles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import org.apache.commons.lang3.SerializationUtils;

import Agents.Agent;
import Agents.AgentAction;
import Agents.PositionAgent;
import Agents.Type_Agnet;
import Mes_Strategy.Strategy;
import Agents.Fabrique.*;

public class PacmanGame extends Game  {

	private static final long serialVersionUID = 1L;
	private static final int TOUR_MAX_INVINCIBLE = 10;
	//distribution des points
	private static final int Point_petite_capsule = 10;
	private static final int Point_capsule_rouge = 50;
	private static final int Point_dun_fantom = 50;
	private static final int Point_Gagner = 100;
	private static final int Point_a_la_mort = -100;

	
	
	private Maze _maze;
	private Maze _originalMaze;
	
	
	//un agent pacman et une liste de fantom
	public Agent pacman;
	private List<Agent> _agentsFantom;

	
	
	private int score;

	private boolean ghosts_Scarred;
	private int nb_tour_invincible;

	private int nbCapsule;
	private int nbFood;
	boolean nightmareMode = false;
	
	
	public PacmanGame(String chemin_maze, int maximum_laps, long speed) {
		super(maximum_laps, speed);
		
	    this._maze = null;
	    
	    this.score = 0;
	    
	    
		try {
			_maze = new Maze(chemin_maze);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//this._originalMaze = SerializationUtils.clone(this._maze);
		
	}

	public PacmanGame(int maximum_laps) {
		super(maximum_laps);
	}

	@Override
	public void initGame() {
		_turn = 0;
		_is_Running = true;
		_etat_Actuelle = Etat.Start;


	}


	public void initalesgame(Strategy behavior, boolean nightmareMode ) {
		
		this.initGame();

		this.nightmareMode = nightmareMode;
		

		_agentsFantom = FantomFactory.createFantom(_maze.getGhosts_start());

		pacman = PacmanFactory.playerAgent(_maze.getPacman_start().get(0), behavior);

		
		ghosts_Scarred = false;
		nb_tour_invincible = 0;
		score = 0;

		notifyObservers();
		
		nbCapsule = countCapsules(_maze);
		nbFood = countFoods(_maze);
		
	}
	
	
	//fonction pour calculer le nombre de capsule total
	public int countCapsules(Maze maze) {
		
		int nb = 0;
		
		for(int i =0; i < maze.getSizeX(); i++) {
			
			for(int j =0; j < maze.getSizeY(); j++) {
				
				if(maze.isCapsule(i, j)) {
					
					nb += 1;
				}
				
			}
		}
		
		return nb;
	}
	
	public int countFoods(Maze maze) {
		//je calcule le nombre de capsule dans le maze
		
		int nombre_de_capsule = 0;
		
		for(int i =0; i < maze.getSizeX(); i++) {
			
			for(int j =0; j < maze.getSizeY(); j++) {
				//controle si cest une capsule rouge

				
				if(maze.isFood(i, j)) {
					//jincrimente +1
					nombre_de_capsule += 1;
				}
				
			}
		}
		//je return le nombre total a la fin
		return nombre_de_capsule;
	}
	

//verifier si un agent fanrtom ou pacman capable de faire une action legale ou pas
	public boolean is_Legal_Move(Agent agent, AgentAction action) {
		//si deja il est en vie
		if (agent.is_en_vie()) {
			return !_maze.isWall(agent.get_position().getX() + action.get_vx(),
					agent.get_position().getY() + action.get_vy());
		}
		return false;//sinon false direct
	}

	//deplacement
	
	public void Deplacer_un_agent(Agent agent, AgentAction action) {

		int dirX = agent.get_position().getX() + action.get_vx();
		int dirY = agent.get_position().getY() + action.get_vy();

		agent.set_position(new PositionAgent(dirX, dirY, action.get_direction()));


		if (agent.get_type() == Type_Agnet.PamMan) {
			if (_maze.isCapsule(dirX, dirY)) {
				Manger_Capsule(dirX, dirY);
			    this.nbCapsule -= 1;//-1 pour les capsules
			}  
			if (_maze.isFood(dirX, dirY)) {
				Mange_Petite_capsule(dirX, dirY);
				this.nbFood -= 1;//-1 pour les ptt food
			}
		}


	}
//methode pour verifier si 2 agent sont dans la meme position dans le ...
	public boolean Meme_Position(Agent agent1, Agent agent2) {
		return (agent1.get_position().getX() == agent2.get_position().getX())
				&& (agent1.get_position().getY() == agent2.get_position().getY());
	}

	//manger une capsule
	public void Manger_Capsule(int x, int y) {

		// on commence l'invincibilité
		nb_tour_invincible = TOUR_MAX_INVINCIBLE;
		score += Point_capsule_rouge;
		//quand il mange les goste serront effrayer du pacman proche
		setGhosts_Scarred(true);
		// la capsule manger donc on la suprime du maze
		_maze.setCapsule(x, y, false);
	}

	public void Mange_Petite_capsule(int x, int y) {
		
		score += Point_petite_capsule;//score augement
		_maze.setFood(x, y, false);//et on suprime la pomme
	}

	
	@Override
	public void Reinitialise_Game() {
		//jinitialise tout et les fantom sont plus effrayer on recommance
		ghosts_Scarred = false;
		nb_tour_invincible = 0;
		score = 0;


//creation des agent
		_agentsFantom = FantomFactory.createFantom(_maze.getGhosts_start());
		
		
		//a la Reintialisation le pacman est en vie
		pacman.set_en_vie(true);
		//je plac le pacmane a la case de depart initiale definie
		pacman.set_position(pacman.get_positio_du_depart());
		//et je place aussi les capsule et pomme dans le jeu
		
		nbCapsule = countCapsules(_maze);
		nbFood = countFoods(_maze);
		
		//joublie pas de notify tjr
		notifyObservers();
	}
/////////////////////////////////////////////////////////////////////////////////////////
/*
	private AgentAction getRandomAction() {
		int randomNum = new Random().nextInt(4); // Génère un nombre aléatoire entre 0 et 3
		switch (randomNum) {
			case 0: return new AgentAction(AgentAction.MOVE_UP);
			case 1: return new AgentAction(AgentAction.MOVE_DOWN);
			case 2: return new AgentAction(AgentAction.MOVE_LEFT);
			case 3: return new AgentAction(AgentAction.MOVE_RIGHT);
		}
		return new AgentAction(AgentAction.STOP); // En cas d'erreur
	}
*/
	/////////////////////////////////////////////////////////////////

	@Override
	public void takeTurn() {

			
		if (isGhosts_Scarred()) {
			if (nb_tour_invincible == 0) {
				setGhosts_Scarred(false);
			} else {
				nb_tour_invincible--;
			}
		}

		
		double reward =  - this.score;

		AgentAction action;

		
		
		action = pacman.play(this, ghosts_Scarred, null);
		if (is_Legal_Move(pacman, action)) {
			Deplacer_un_agent(pacman, action);
		}


		if(_agentsFantom.size() > 0) {
			
			Iterator<Agent> iterator = _agentsFantom.iterator(); 
			while (iterator.hasNext()) {
				
				Agent fantom = (Agent)iterator.next();
				
				boolean isRemoved = false;
						
				if(Meme_Position(pacman,fantom )) {
					
					if (isGhosts_Scarred()) {
						
						score += Point_dun_fantom;
						iterator.remove();
						isRemoved = true;
						
					} else {
						
						score += PacmanGame.Point_a_la_mort;
						pacman.set_en_vie(false);
						
					}
				}
				
				if(!isRemoved && pacman.is_en_vie()) {
					//suivre le pacman comme objectif
					
					AgentAction aa = fantom.play(this, ghosts_Scarred, pacman.get_position());
					if (is_Legal_Move(fantom, aa)) {
						Deplacer_un_agent(fantom, aa);
					}
					
					
					if(Meme_Position(pacman,fantom)) {
					
						if (isGhosts_Scarred()) {
							score += Point_dun_fantom;
							iterator.remove();
						} else {
							score += PacmanGame.Point_a_la_mort;
							//la mort du fantom
							pacman.set_en_vie(false);
						}
					}
				}
				
				
			}
		}
		

		//tjr notifye
		notifyObservers();
		//quand le pacman est mort on arrete le scor et nbr de laps
		boolean isFinalState = checkIfFinalState();
		
		//retourner le score final
		reward += this.score;
		
		

		
		//partie perdu
		if(isFinalState) {
			gameOver();
		}
	}

	public boolean checkIfFinalState() {

		boolean fin_gum = false; 
		boolean fin_pacman = false; 
		
		if(nbCapsule == 0 && nbFood == 0) {
			fin_gum = true;
		} else {
			fin_gum = false;
		}


		if (pacman.is_en_vie()) {
			fin_pacman = false;
		} else {
			fin_pacman = true;
		}
		
		if (fin_gum) {
			score += PacmanGame.Point_Gagner;
		}
		
		return (fin_gum || fin_pacman);
	}


	@Override
	public void gameOver() {
		_is_Running = false;
		_etat_Actuelle = Etat.GameOver;
	
	}

	@Override
	public boolean GameContinue() {
		return false;
	}


	public List<Agent> get_agentsFantom() {
		return _agentsFantom;
	}

	public void set_agentsFantom(List<Agent> _agentsFantom) {
		this._agentsFantom = _agentsFantom;
	}

	public ArrayList<PositionAgent> getPostionPacman() {
		ArrayList<PositionAgent> pa = new ArrayList<PositionAgent>();
	
		pa.add(pacman.get_position());
		
		return pa;
	}

	public ArrayList<PositionAgent> getPostionFantom() {
		ArrayList<PositionAgent> pa = new ArrayList<PositionAgent>();
		for (Agent agent : _agentsFantom) {
			pa.add(agent.get_position());
		}
		return pa;
	}

	public Maze getMaze() {
		return _maze;
	}

	public void setMaze(Maze _maze) {
		this._maze = _maze;
		
		this.initGame();
	}

	public boolean isGhosts_Scarred() {
		return ghosts_Scarred;
	}

	public void setGhosts_Scarred(boolean ghosts_Scarred) {
		this.ghosts_Scarred = ghosts_Scarred;
	}

	public int getScore() {
		return this.score;
	}

	public int getNbcapsule() {
		return nbCapsule;
	}
	
	public int getNbFood() {
		
		return this.nbFood;
	}

	public void movePacManWithKeyBoard(AgentAction action, Agent agent) {
		if(agent.get_type() == Type_Agnet.PamMan) {
			int dirX = agent.get_position().getX() + action.get_vx();
			int dirY = agent.get_position().getY() + action.get_vy();


			agent.set_position(new PositionAgent(dirX, dirY, action.get_direction()));
		}
	}

}