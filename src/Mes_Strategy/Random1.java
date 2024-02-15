package Mes_Strategy;

import java.util.ArrayList;

import Agents.AgentAction;
import Modelles.PacmanGame;

import java.util.Random;

public class Random1 extends Abstract_Strategy {

	private Random random = new Random();

	public Random1() {
		// Constructeur vide
	}

	@Override
	public AgentAction chooseAction(PacmanGame Etat) {
		ArrayList<AgentAction> legalMoves = getLegalMoves(Etat);
		return legalMoves.isEmpty() ? new AgentAction(AgentAction.STOP) : legalMoves.get(random.nextInt(legalMoves.size()));
	}

	private ArrayList<AgentAction> getLegalMoves(PacmanGame state) {
		ArrayList<AgentAction> legalMoves = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			AgentAction action = new AgentAction(i);
			if (state.is_Legal_Move(state.pacman, action)) {
				legalMoves.add(action);
			}
		}
		return legalMoves;
	}

	@Override
	public void update(PacmanGame Etat, PacmanGame Next_Etat, AgentAction Action, double scorglobal) {

	}
}
