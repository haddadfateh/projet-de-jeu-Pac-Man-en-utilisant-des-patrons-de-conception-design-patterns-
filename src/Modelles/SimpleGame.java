package Modelles;

public class SimpleGame extends Game {

	public SimpleGame(int maximum_laps) {
		super(maximum_laps);
	}

	@Override
	public void initGame() {
		{
			_turn = 0;
			_is_Running = true;
			_etat_Actuelle = Etat.Start;

		}

	}

	public SimpleGame(int maximum_laps, long speed) {
		super(maximum_laps, speed);
	}


	@Override
	public void takeTurn() {}

	@Override
	public void gameOver() {
		{
			_is_Running = false;
			_etat_Actuelle = Etat.GameOver;
		}
	}

	@Override
	public boolean GameContinue() {
		return (_turn > _max_Turn);
	}

	@Override
	public void Reinitialise_Game() {
		// TODO Auto-generated method stub
		
	}

}
