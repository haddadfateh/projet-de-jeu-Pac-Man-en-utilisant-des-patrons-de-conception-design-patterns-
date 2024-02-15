package Agents;

import Modelles.Maze;

import java.io.Serializable;

public class PositionAgent implements Serializable {

	private static final long serialVersionUID = 1L;

	private int x;
	private int y;
	private int dir;

	public PositionAgent(int x, int y, int dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	public boolean equals(PositionAgent other) {
		return (x == other.x) && (y == other.y);
	}
	/////////////////////
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

}