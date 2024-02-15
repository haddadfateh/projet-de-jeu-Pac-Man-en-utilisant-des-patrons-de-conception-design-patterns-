package Mes_Strategy;

import Agents.AgentAction;
import Agents.PositionAgent;
import Modelles.PacmanGame;
import Modelles.Maze;

public class capsule_Directement extends Abstract_Strategy {

    private PositionAgent lastPosition; // Suivre la dernière position de Pac-Man

    public capsule_Directement() {
        lastPosition = null; // Initialiser lastPosition
    }

    @Override
    public AgentAction chooseAction(PacmanGame game) {
        PositionAgent currentPosition = game.pacman.get_position();
        PositionAgent closestCapsule = getClosestCapsule(game.getMaze(), currentPosition);
        if (closestCapsule != null) {
            int direction = getDirectionTowardsCapsule(game.getMaze(), currentPosition, closestCapsule, lastPosition);
            PositionAgent newPosition = calculateNewPosition(currentPosition, direction);

            if (!game.getMaze().isWall(newPosition.getX(), newPosition.getY())) {
                lastPosition = currentPosition; // Mise à jour de la dernière position
                return new AgentAction(direction);
            }
        }

        // Mouvement aléatoire si aucun mouvement vers une capsule n'est possible
        return getRandomMove();
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

    private PositionAgent getClosestCapsule(Maze maze, PositionAgent pacmanPos) {
        PositionAgent closestCapsule = null;
        double minDistance = Double.MAX_VALUE;

        for (int x = 0; x < maze.getSizeX(); x++) {
            for (int y = 0; y < maze.getSizeY(); y++) {
                if (maze.isCapsule(x, y)) {
                    PositionAgent capsulePos = new PositionAgent(x, y, 0);
                    double distance = calculateDistance(pacmanPos, capsulePos);
                    if (distance < minDistance) {
                        minDistance = distance;
                        closestCapsule = capsulePos;
                    }
                }
            }
        }

        return closestCapsule;
    }

    private double calculateDistance(PositionAgent pos1, PositionAgent pos2) {
        return Math.sqrt(Math.pow(pos1.getX() - pos2.getX(), 2) + Math.pow(pos1.getY() - pos2.getY(), 2));
    }

    private int getDirectionTowardsCapsule(Maze maze, PositionAgent pacmanPos, PositionAgent capsulePos, PositionAgent previousPosition) {
        int bestDirection = AgentAction.STOP;
        double minDistance = Double.MAX_VALUE;

        for (int dir = 0; dir < 4; dir++) {
            PositionAgent newPos = calculateNewPosition(pacmanPos, dir);
            if (!maze.isWall(newPos.getX(), newPos.getY()) && (previousPosition == null || !newPos.equals(previousPosition))) {
                double distance = calculateDistance(newPos, capsulePos);
                if (distance < minDistance) {
                    minDistance = distance;
                    bestDirection = dir;
                }
            }
        }

        return bestDirection;
    }

    private AgentAction getRandomMove() {
        int randomDirection = (int) (Math.random() * 4);
        return new AgentAction(randomDirection);
    }

    @Override
    public void update(PacmanGame game, PacmanGame nextState, AgentAction action, double scoreGlobal) {

    }
}
