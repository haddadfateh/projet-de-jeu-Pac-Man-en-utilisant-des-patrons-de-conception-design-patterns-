package Agents.Fabrique;

import Agents.Agent;
import Agents.PositionAgent;
import Agents.Type_Agnet;
import Mes_Strategy.*;


import Mes_Strategy.Strategy;
import Mes_Strategy.pacman_seloigne_des_fantom;
import Mes_Strategy.pacman_atak_fantom;

public class PacmanFactory implements factory {

    @Override
    public Agent createAgent(PositionAgent position, Strategy normalStrategy, Strategy scaredStrategy) {
        Agent pacmanAgent = new Agent(position, Type_Agnet.PamMan);
        pacmanAgent.setBeheviorNormal(normalStrategy != null ? normalStrategy : new pacman_seloigne_des_fantom());
        pacmanAgent.setBeheviorScared(scaredStrategy != null ? scaredStrategy : new pacman_atak_fantom());
        return pacmanAgent;
    }

    public static Agent stratPacmanFactory(PositionAgent position) {
        return new PacmanFactory().createAgent(position, new pacman_atak_fantom(), new pacman_seloigne_des_fantom());
    }

    public static Agent playerAgent(PositionAgent position, Strategy strat) {
        return new PacmanFactory().createAgent(position, strat, strat);
    }
}
