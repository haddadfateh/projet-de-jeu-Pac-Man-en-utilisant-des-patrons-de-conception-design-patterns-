package Agents.Fabrique;
import Agents.Agent;
import Agents.PositionAgent;
import Mes_Strategy.Strategy;

public interface factory {
    Agent createAgent(PositionAgent position, Strategy normalStrategy, Strategy scaredStrategy);

}
