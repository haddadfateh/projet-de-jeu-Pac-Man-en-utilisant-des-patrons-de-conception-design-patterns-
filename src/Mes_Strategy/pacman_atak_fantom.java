package Mes_Strategy;

import Agents.Agent;
import Agents.AgentAction;
import Agents.PositionAgent;
import Modelles.PacmanGame;
import Modelles.Maze;

import java.util.List;
import java.util.PriorityQueue;

public class pacman_atak_fantom extends Abstract_Strategy {

    private boolean capsuleMangee = false; // Ajout d'une variable pour détecter si une capsule est mangée

    public pacman_atak_fantom() {
        // Constructeur vide
    }

    @Override
    public AgentAction chooseAction(PacmanGame game) {
        PositionAgent currentPosition = game.pacman.get_position();
        List<Agent> ghosts = game.get_agentsFantom();

        if (capsuleMangee) { // Vérifier si une capsule est mangée
            Agent nearestGhost = getNearestGhost(currentPosition, ghosts);

            if (nearestGhost != null) {
                return moveTowardsTarget(currentPosition, nearestGhost.get_position(), game.getMaze());
            } else {
                // Si aucun fantôme n'est trouvé, arrêter Pac-Man
                return new AgentAction(AgentAction.STOP);
            }
        } else {
            // Utilisation de la stratégie par défaut si aucune capsule n'est mangée
            return defaultPacmanStrategy(currentPosition, ghosts, game.getMaze());
        }
    }

    private Agent getNearestGhost(PositionAgent pacmanPos, List<Agent> ghosts) {
        Agent nearestGhost = null;
        double minDistance = Double.MAX_VALUE;

        for (Agent ghost : ghosts) {
            double distance = calculateDistance(pacmanPos, ghost.get_position());
            if (distance < minDistance) {
                minDistance = distance;
                nearestGhost = ghost;
            }
        }

        return nearestGhost;
    }

    private AgentAction moveTowardsTarget(PositionAgent currentPosition, PositionAgent targetPosition, Maze maze) {
        // Utilisation de l'algorithme A* pour trouver le chemin le plus court vers la cible
        PriorityQueue<Node> openList = new PriorityQueue<>();
        boolean[][] closedList = new boolean[maze.getSizeX()][maze.getSizeY()];

        Node startNode = new Node(currentPosition, null, 0, calculateDistance(currentPosition, targetPosition));
        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();

            if (currentNode.position.equals(targetPosition)) {
                // Chemin trouvé, remonter jusqu'au début
                while (currentNode.parent != null && !currentNode.parent.position.equals(currentPosition)) {
                    currentNode = currentNode.parent;
                }
                return new AgentAction(currentNode.action);
            }

            closedList[currentNode.position.getX()][currentNode.position.getY()] = true;

            for (int action = 0; action < 4; action++) {
                PositionAgent newPosition = calculateNewPosition(currentNode.position, action);
                if (!maze.isWall(newPosition.getX(), newPosition.getY()) && !closedList[newPosition.getX()][newPosition.getY()]) {
                    double gScore = currentNode.gScore + 1;
                    double hScore = calculateDistance(newPosition, targetPosition);
                    double fScore = gScore + hScore;

                    openList.add(new Node(newPosition, currentNode, action, fScore));
                }
            }
        }

        // Si aucun chemin n'a été trouvé, arrêter Pac-Man
        return new AgentAction(AgentAction.STOP);
    }

    private AgentAction defaultPacmanStrategy(PositionAgent currentPosition, List<Agent> ghosts, Maze maze) {
        // Stratégie par défaut : déplacer le Pac-Man dans une direction aléatoire

        // Liste des directions possibles
        int[] possibleDirections = {AgentAction.NORTH, AgentAction.SOUTH, AgentAction.EAST, AgentAction.WEST};

        // Sélectionner une direction aléatoire parmi les directions possibles
        int randomDirection = possibleDirections[(int) (Math.random() * possibleDirections.length)];

        // Calculer la nouvelle position en fonction de la direction
        PositionAgent newPosition = calculateNewPosition(currentPosition, randomDirection);

        // Vérifier si la nouvelle position est valide (pas un mur)
        if (!maze.isWall(newPosition.getX(), newPosition.getY())) {
            return new AgentAction(randomDirection);
        } else {
            // Si la direction aléatoire conduit à un mur, arrêter le Pac-Man
            return new AgentAction(AgentAction.STOP);
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

    private double calculateDistance(PositionAgent pos1, PositionAgent pos2) {
        return Math.sqrt(Math.pow(pos1.getX() - pos2.getX(), 2) + Math.pow(pos1.getY() - pos2.getY(), 2));
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
    public void update(PacmanGame game, PacmanGame nextState, AgentAction action, double scoreGlobal) {

    }


}

