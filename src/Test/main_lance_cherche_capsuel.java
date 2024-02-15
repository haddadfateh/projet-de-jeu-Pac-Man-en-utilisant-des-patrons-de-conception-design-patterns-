package Test;

import Controllers.ControllerPacmanGAme;
import Mes_Strategy.capsule_Directement;
import Modelles.PacmanGame;
import view.ViewGen;

public class main_lance_cherche_capsuel {
    public static void main(String[] args) {

        String chemin_maze = "src/layout/originalClassic.lay";
        PacmanGame game = new PacmanGame(chemin_maze, 200, (long) 1000);

        // Créez une instance de ClosestCapsuleStrategy
        capsule_Directement strategieChoisie = new capsule_Directement();

        // Initialisez le jeu avec la nouvelle stratégie
        game.initalesgame(strategieChoisie, false);

        // Créer le contrôleur et la vue
        ControllerPacmanGAme controller = ControllerPacmanGAme.getInstance(game);
        ViewGen _view = ViewGen.getInstance(controller, game, true);
    }
}
