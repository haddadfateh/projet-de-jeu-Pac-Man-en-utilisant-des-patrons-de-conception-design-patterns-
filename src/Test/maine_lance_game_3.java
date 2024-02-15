package Test;
import Controllers.ControllerPacmanGAme;
import Mes_Strategy.fantom_seloign_des_pacman;
import Modelles.PacmanGame;
import view.ViewGen;

public class maine_lance_game_3 {
    public static void main(String[] args) {
        // Assurez-vous que le chemin du fichier layout du labyrinthe est correct
        String chemin_maze = "src/layout/originalClassic.lay";

        // Initialisation du jeu Pacman avec une durée de delay et un seed pour le random
        PacmanGame game = new PacmanGame(chemin_maze, 200, (long) 100);

        // Utilisation de la stratégie ClosestCapsuleStrategy
        fantom_seloign_des_pacman strategieChoisie = new fantom_seloign_des_pacman();

        // Initialisation du jeu avec la stratégie choisie
        game.initalesgame(strategieChoisie, false);

        // Création du contrôleur et de la vue pour le jeu
        ControllerPacmanGAme controller = ControllerPacmanGAme.getInstance(game);
        ViewGen _view = ViewGen.getInstance(controller, game, true);


    }
}
