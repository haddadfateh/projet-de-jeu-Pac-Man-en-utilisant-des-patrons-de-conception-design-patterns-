package Test;
import Controllers.ControllerPacmanGAme;
import Mes_Strategy.pacman_atak_fantom;;
import Modelles.PacmanGame;
import view.ViewGen;

public class main_saataque_des_fantom {

        public static void main(String[] args) {

            String chemin_maze = "src/layout/fate_labiraint.lay";
            PacmanGame game = new PacmanGame(chemin_maze, 200, (long) 100);
            pacman_atak_fantom strategie = new pacman_atak_fantom();

            game.initalesgame(strategie, false);

            ControllerPacmanGAme controller = ControllerPacmanGAme.getInstance(game);
            ViewGen _view = ViewGen.getInstance(controller, game, true);

        }


    }

