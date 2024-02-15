package Test;
import Controllers.ControllerPacmanGAme;
import Mes_Strategy.fantomAttackPacmanStrategy;
import Modelles.PacmanGame;
import view.ViewGen;

public class main_fantom_attaque_pacman {
    public static void main(String[] args) {
        // Assurez-vous que le chemin du fichier layout du labyrinthe est correct
        String chemin_maze = "src/layout/fateh_labiraint.lay";

        // Initialisation du jeu Pacman avec une durée de delay et un seed pour le random
        PacmanGame game = new PacmanGame(chemin_maze, 200, (long) 100);

        // Utilisation de la stratégie AttackPacmanStrategy
        fantomAttackPacmanStrategy strategieChoisie = new fantomAttackPacmanStrategy();

        // Initialisation du jeu avec la stratégie choisie
        game.initalesgame(strategieChoisie, false);

        // Création du contrôleur et de la vue pour le jeu
        ControllerPacmanGAme controller = ControllerPacmanGAme.getInstance(game);
        ViewGen _view = ViewGen.getInstance(controller, game, true);

}}
