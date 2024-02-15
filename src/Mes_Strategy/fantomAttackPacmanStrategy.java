package Mes_Strategy;

import Agents.Agent;
import Agents.AgentAction;
import Agents.PositionAgent;
import Modelles.PacmanGame;
import Modelles.Maze;

import java.util.PriorityQueue;

public class fantomAttackPacmanStrategy extends Abstract_Strategy {

    public fantomAttackPacmanStrategy() {
        // Constructeur vide
    }

    @Override
    public AgentAction chooseAction(PacmanGame game) {
        Agent fantom = game.get_agentsFantom().get(0);
        PositionAgent currentPosition = fantom.get_position();
        PositionAgent pacmanPosition = game.getPostionPacman().get(0);

        return moveTowardsTarget(currentPosition, pacmanPosition, game.getMaze());
    }

    private AgentAction moveTowardsTarget(PositionAgent currentPosition, PositionAgent targetPosition, Maze maze) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        boolean[][] closedList = new boolean[maze.getSizeX()][maze.getSizeY()];

        Node startNode = new Node(currentPosition, null, 0, calculateDistance(currentPosition, targetPosition));
        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();

            if (currentNode.position.equals(targetPosition)) {
                while (currentNode.parent != null && !currentNode.parent.position.equals(currentPosition)) {
                    currentNode = currentNode.parent;
                }
                return convertMoveToAction(currentNode.parent.position, currentNode.position);
            }

            closedList[currentNode.position.getX()][currentNode.position.getY()] = true;

            for (int action = 0; action < 4; action++) {
                PositionAgent newPosition = calculateNewPosition(currentNode.position, action);
                if (isValidPosition(newPosition, maze, closedList)) {
                    double gScore = currentNode.gScore + 1;
                    double hScore = calculateDistance(newPosition, targetPosition);
                    double fScore = gScore + hScore;

                    openList.add(new Node(newPosition, currentNode, action, fScore));
                }
            }
        }

        return new AgentAction(AgentAction.STOP);
    }

    private boolean isValidPosition(PositionAgent position, Maze maze, boolean[][] closedList) {
        return position.getX() >= 0 && position.getX() < maze.getSizeX() &&
                position.getY() >= 0 && position.getY() < maze.getSizeY() &&
                !maze.isWall(position.getX(), position.getY()) &&
                !closedList[position.getX()][position.getY()];
    }

    private PositionAgent calculateNewPosition(PositionAgent currentPosition, int direction) {
        switch (direction) {
            case AgentAction.NORTH:
                return new PositionAgent(currentPosition.getX(), currentPosition.getY() - 1, AgentAction.NORTH);
            case AgentAction.SOUTH:
                return new PositionAgent(currentPosition.getX(), currentPosition.getY() + 1, AgentAction.SOUTH);
            case AgentAction.EAST:
                return new PositionAgent(currentPosition.getX() + 1, currentPosition.getY(), AgentAction.EAST);
            case AgentAction.WEST:
                return new PositionAgent(currentPosition.getX() - 1, currentPosition.getY(), AgentAction.WEST);
            default:
                return new PositionAgent(currentPosition.getX(), currentPosition.getY(), direction);
        }
    }

    private double calculateDistance(PositionAgent pos1, PositionAgent pos2) {
        return Math.sqrt(Math.pow(pos1.getX() - pos2.getX(), 2) + Math.pow(pos1.getY() - pos2.getY(), 2));
    }

    private AgentAction convertMoveToAction(PositionAgent from, PositionAgent to) {
        if (to.getX() > from.getX()) return new AgentAction(AgentAction.EAST);
        if (to.getX() < from.getX()) return new AgentAction(AgentAction.WEST);
        if (to.getY() > from.getY()) return new AgentAction(AgentAction.SOUTH);
        if (to.getY() < from.getY()) return new AgentAction(AgentAction.NORTH);
        return new AgentAction(AgentAction.STOP);
    }

    private class Node implements Comparable<Node> {
        PositionAgent position;
        Node parent;
        int action;
        double gScore;
        double fScore;

        Node(PositionAgent position, Node parent, int action, double fScore) {
            this.position = position;
            this.parent = parent;
            this.action = action;
            this.gScore = parent != null ? parent.gScore + 1 : 0;
            this.fScore = fScore;
        }

        @Override
        public int compareTo(Node other) {
            return Double.compare(this.fScore, other.fScore);
        }
    }

    @Override
    public void update(PacmanGame Etat, PacmanGame Next_Etat, AgentAction Action, double scoreGlobal) {

    }
}
