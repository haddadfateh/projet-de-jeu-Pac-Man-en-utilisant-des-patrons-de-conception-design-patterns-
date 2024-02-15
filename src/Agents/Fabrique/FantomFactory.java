package Agents.Fabrique;
import Agents.Agent;
import Agents.PositionAgent;
import Agents.Type_Agnet;
import Mes_Strategy.*;

import java.util.ArrayList;

public class FantomFactory implements factory {

    @Override
    public Agent createAgent(PositionAgent position, Strategy normalStrategy, Strategy scaredStrategy) {
        Agent fantomAgent = new Agent(position, Type_Agnet.Fantom);
        fantomAgent.setBeheviorNormal(normalStrategy != null ? normalStrategy : new fantomAttackPacmanStrategy());
        fantomAgent.setBeheviorScared(scaredStrategy != null ? scaredStrategy : new fantom_seloign_des_pacman());
        return fantomAgent;
    }

    public static ArrayList<Agent> createFantom(ArrayList<PositionAgent> ghostStart) {
        FantomFactory factory = new FantomFactory();
        ArrayList<Agent> l = new ArrayList<>();
        for (PositionAgent p : ghostStart) {
            l.add(factory.createAgent(p, new fantomAttackPacmanStrategy(), new fantom_seloign_des_pacman()));
        }
        return l;
    }

    public static Agent payerFantomFactory(PositionAgent position) {
        return new FantomFactory().createAgent(position, new fantomAttackPacmanStrategy(), new fantom_seloign_des_pacman());
    }
}
