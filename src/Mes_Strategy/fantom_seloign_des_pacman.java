package Mes_Strategy;

import Agents.AgentAction;
import Agents.PositionAgent;
import Modelles.PacmanGame;
import Modelles.Maze;

public class fantom_seloign_des_pacman extends Abstract_Strategy {
    private PositionAgent lastPosition;

    public fantom_seloign_des_pacman() {
        lastPosition = null;
    }

    @Override
    public AgentAction chooseAction(PacmanGame game) {
        PositionAgent target;
        if (game.getMaze().getRemainingCapsules() > 0) {
            target = getClosestCapsule(game.getMaze(), game.pacman.get_position());
        } else {
            target = getClosestFood(game.getMaze(), game.pacman.get_position());
        }

        if (target != null) {
            int direction = getDirectionTowardsTarget(game.getMaze(), game.pacman.get_position(), target, lastPosition);
            PositionAgent newPosition = calculateNewPosition(game.pacman.get_position(), direction);

            if (!game.getMaze().isWall(newPosition.getX(), newPosition.getY())) {
                lastPosition = newPosition;
                return new AgentAction(direction);
            }
        }

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
        // Implémentation hypothétique pour trouver la capsule la plus proche
        return findClosestTarget(maze, pacmanPos, true);
    }

    private PositionAgent getClosestFood(Maze maze, PositionAgent pacmanPos) {
        // Implémentation hypothétique pour trouver le point de nourriture le plus proche
        return findClosestTarget(maze, pacmanPos, false);
    }

    private PositionAgent findClosestTarget(Maze maze, PositionAgent pacmanPos, boolean isCapsule) {
        PositionAgent closestTarget = null;
        double minDistance = Double.MAX_VALUE;

        for (int x = 0; x < maze.getSizeX(); x++) {
            for (int y = 0; y < maze.getSizeY(); y++) {
                if ((isCapsule && maze.isCapsule(x, y)) || (!isCapsule && maze.isFood(x, y))) {
                    PositionAgent targetPos = new PositionAgent(x, y, 0);
                    double distance = calculateDistance(pacmanPos, targetPos);
                    if (distance < minDistance) {
                        minDistance = distance;
                        closestTarget = targetPos;
                    }
                }
            }
        }

        return closestTarget;
    }

    private double calculateDistance(PositionAgent pos1, PositionAgent pos2) {
        return Math.sqrt(Math.pow(pos1.getX() - pos2.getX(), 2) + Math.pow(pos1.getY() - pos2.getY(), 2));
    }

    private int getDirectionTowardsTarget(Maze maze, PositionAgent pacmanPos, PositionAgent target, PositionAgent previousPosition) {
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

        return bestDirection;
    }

    private AgentAction getRandomMove() {
        int randomDirection = (int) (Math.random() * 4);
        return new AgentAction(randomDirection);
    }

    @Override
    public void update(PacmanGame game, PacmanGame nextState, AgentAction action, double scoreGlobal) {
        // Mettre à jour la stratégie en fonction des changements d'état du jeu
    }
}
