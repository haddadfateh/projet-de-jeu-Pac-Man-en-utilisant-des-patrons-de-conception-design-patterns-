package Mes_Strategy;

import Agents.AgentAction;
import Agents.PositionAgent;
import Modelles.PacmanGame;


public interface Strategy {



	public AgentAction play(PacmanGame state, PositionAgent positionAgent, PositionAgent objectif);


	public void update(PacmanGame state, PacmanGame nextState, AgentAction action, double scorglobal);




}
