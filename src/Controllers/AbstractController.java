package Controllers;

import Modelles.Game;

public abstract class AbstractController {//un controleur abtstrait comme demander
    protected Game game;
    public AbstractController(Game game){
        this.game= game;
    }
    public void Restart(){this.game.init();//initi je suis pas sur
        this.game.launch();}
    public void Playe(){this.game.launch();/*je lance*/}
    public void Pause(){this.game.Pause();}
    public void step(){this.game.step();}
    public void speed(int speed) {game.set_speed(1000 / speed);}
}
