package Test;
import Controllers.ControllerPacmanGAme;
import Modelles.PacmanGame;
import Mes_Strategy.Random1;
import view.ViewGen;
public class main_lance_Random {

	public static void main(String[] args) {

		String chemin_maze = "src/layout/originalClassic.lay";
		PacmanGame game = new PacmanGame(chemin_maze, 200, (long) 1);
		Random1 statgegieCHoisie = new Random1();

		game.initalesgame(statgegieCHoisie, false);

		ControllerPacmanGAme controller = ControllerPacmanGAme.getInstance(game);
		ViewGen _view = ViewGen.getInstance(controller, game, true);

	}
	
}
