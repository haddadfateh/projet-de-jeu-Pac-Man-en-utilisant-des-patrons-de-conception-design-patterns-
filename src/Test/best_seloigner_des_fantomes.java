package Test;
import Controllers.ControllerPacmanGAme;
import Mes_Strategy.pacman_seloigne_des_fantom;
import Modelles.PacmanGame;
import view.ViewGen;

public class best_seloigner_des_fantomes {
    public static void main(String[] args) {
        // Assurez-vous que le chemin du fichier layout du labyrinthe est correct
        String chemin_maze = "src/layout/originalClassic.lay";
        //String chemin_maze = "src/layout/original.lay";

        // Initialisation du jeu Pacman avec une durée de delay et un seed pour le random
        PacmanGame game = new PacmanGame(chemin_maze, 200, (long) 100);

        // Utilisation de la stratégie AvoidGhostsStrategy
        pacman_seloigne_des_fantom strategieChoisie = new pacman_seloigne_des_fantom();

        // Initialisation du jeu avec la stratégie choisie
        game.initalesgame(strategieChoisie, false);

        // Création du contrôleur et de la vue pour le jeu
        ControllerPacmanGAme controller = ControllerPacmanGAme.getInstance(game);
        ViewGen _view = ViewGen.getInstance(controller, game, true);


    }
}
