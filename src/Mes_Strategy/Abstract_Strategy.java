package Mes_Strategy;

import Agents.AgentAction;
import Agents.PositionAgent;
import Modelles.PacmanGame;

public abstract class Abstract_Strategy implements Strategy {
	public Abstract_Strategy() {}
	public AgentAction play(PacmanGame game, PositionAgent positionAgent, PositionAgent objectif) {
		return this.chooseAction(game);}
	public abstract AgentAction chooseAction(PacmanGame Etat);

	public abstract void update(PacmanGame Etat, PacmanGame Next_Etat, AgentAction Action, double scorglobal);

	
}
