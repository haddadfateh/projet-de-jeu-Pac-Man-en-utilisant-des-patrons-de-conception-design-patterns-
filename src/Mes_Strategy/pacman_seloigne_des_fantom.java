package Mes_Strategy;

import Agents.Agent;
import Agents.AgentAction;
import Agents.PositionAgent;
import Modelles.PacmanGame;
import Modelles.Maze;

import java.util.List;

public class pacman_seloigne_des_fantom extends Abstract_Strategy {

    private static final double GHOST_THREAT_DISTANCE = 8.0; // Distance à partir de laquelle un fantôme est considéré comme menaçant
    private PositionAgent lastPacmanPos = null;

    public pacman_seloigne_des_fantom() {
        // Constructeur vide
    }

    @Override
    public AgentAction chooseAction(PacmanGame game) {
        PositionAgent currentPosition = game.pacman.get_position();
        List<Agent> ghosts = game.get_agentsFantom();

        if (isGhostThreatening(currentPosition, ghosts)) {
            AgentAction action = moveAwayFromGhosts(currentPosition, ghosts, game.getMaze());
            if (action != null) {
                lastPacmanPos = currentPosition;
                return action;
            } else {
                // Si aucune action n'est possible pour s'éloigner des fantômes, revenir à la dernière position connue
                if (lastPacmanPos != null) {
                    return getDirectionTowardsTarget(game.getMaze(), currentPosition, lastPacmanPos, null);
                }
            }
        } else {
            AgentAction action = moveTowardsFood(game.getMaze(), currentPosition);
            if (action != null) {
                lastPacmanPos = currentPosition;
                return action;
            }
        }
        return new AgentAction(AgentAction.STOP);
    }

    private boolean isGhostThreatening(PositionAgent pacmanPos, List<Agent> ghosts) {
        for (Agent ghost : ghosts) {
            double distance = calculateDistance(pacmanPos, ghost.get_position());
            if (distance < GHOST_THREAT_DISTANCE) {
                return true;
            }
        }
        return false;
    }

    private AgentAction moveAwayFromGhosts(PositionAgent pacmanPos, List<Agent> ghosts, Maze maze) {
        int bestDirection = AgentAction.STOP;
        double maxDistance = 0.0;

        for (int dir = 0; dir < 4; dir++) {
            PositionAgent newPosition = calculateNewPosition(pacmanPos, dir);
            if (!maze.isWall(newPosition.getX(), newPosition.getY())) {
                double distanceToClosestGhost = calculateDistanceToClosestGhost(newPosition, ghosts);
                if (distanceToClosestGhost > maxDistance) {
                    maxDistance = distanceToClosestGhost;
                    bestDirection = dir;
                }
            }
        }

        if (maxDistance > 0.0) {
            return new AgentAction(bestDirection);
        } else {
            return null; // Aucune action possible pour s'éloigner des fantômes
        }
    }

    private PositionAgent calculateNewPosition(PositionAgent currentPosition, int direction) {
        switch (direction) {
            case AgentAction.NORTH:
                return new PositionAgent(currentPosition.getX(), currentPosition.getY() - 1, direction);
            case AgentAction.SOUTH:
                return new PositionAgent(currentPosition.getX(), currentPosition.getY() + 1, direction);
            case AgentAction.EAST:
                return new PositionAgent(currentPosition.getX() + 1, currentPosition.getY(), direction);
            case AgentAction.WEST:
                return new PositionAgent(currentPosition.getX() - 1, currentPosition.getY(), direction);
            default:
                return currentPosition;
        }
    }

    private double calculateDistanceToClosestGhost(PositionAgent position, List<Agent> ghosts) {
        double minDistance = Double.MAX_VALUE;
        for (Agent ghost : ghosts) {
            double distance = calculateDistance(position, ghost.get_position());
            if (distance < minDistance) {
                minDistance = distance;
            }
        }
        return minDistance;
    }

    private double calculateDistance(PositionAgent pos1, PositionAgent pos2) {
        return Math.sqrt(Math.pow(pos1.getX() - pos2.getX(), 2) + Math.pow(pos1.getY() - pos2.getY(), 2));
    }

    private AgentAction moveTowardsFood(Maze maze, PositionAgent pacmanPos) {
        PositionAgent closestFood = getClosestFood(maze, pacmanPos);
        if (closestFood != null) {
            return getDirectionTowardsTarget(maze, pacmanPos, closestFood, lastPacmanPos);
        }
        return null; // Aucune action possible pour se diriger vers la nourriture
    }

    private PositionAgent getClosestFood(Maze maze, PositionAgent pacmanPos) {
        PositionAgent closestFood = null;
        double minDistance = Double.MAX_VALUE;

        for (int x = 0; x < maze.getSizeX(); x++) {
            for (int y = 0; y < maze.getSizeY(); y++) {
                if (maze.isFood(x, y)) {
                    PositionAgent foodPos = new PositionAgent(x, y, 0);
                    double distance = calculateDistance(pacmanPos, foodPos);
                    if (distance < minDistance) {
                        minDistance = distance;
                        closestFood = foodPos;
                    }
                }
            }
        }

        return closestFood;
    }

    private AgentAction getDirectionTowardsTarget(Maze maze, PositionAgent pacmanPos, PositionAgent target, PositionAgent previousPosition) {
        int bestDirection = AgentAction.STOP;
        double minDistance = Double.MAX_VALUE;

        for (int dir = 0; dir < 4; dir++) {
            PositionAgent newPos = calculateNewPosition(pacmanPos, dir);
            if (!maze.isWall(newPos.getX(), newPos.getY()) && (previousPosition == null || !newPos.equals(previousPosition))) {
                double distance = calculateDistance(newPos, target);
                if (distance < minDistance) {
                    minDistance = distance;
                    bestDirection = dir;
                }
            }
        }

        return new AgentAction(bestDirection);
    }

    @Override
    public void update(PacmanGame game, PacmanGame nextState, AgentAction action, double scoreGlobal) {

    }
}
