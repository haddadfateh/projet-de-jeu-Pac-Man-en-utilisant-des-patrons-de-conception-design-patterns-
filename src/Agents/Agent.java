package Agents;

import java.io.Serializable;
import Modelles.PacmanGame;
import Mes_Strategy.Strategy;



public class Agent implements Serializable {
	
	private Type_Agnet _type;//pacman ou fantom
	public PositionAgent _position;//la position de lagent pac ou fan
	
	transient private Strategy _behavior_ghostScarred;//td behaviorNurs inspirer
	transient private Strategy _behavior_normal;
	private int next_move;
	private boolean _en_vie;//toujour vivant?!!!!
	private PositionAgent _positio_du_depart;//position de lagent au depart
	public AgentAction play(PacmanGame game, boolean fantom_scared, PositionAgent destination) {

		if (_behavior_normal == null) {
			return new AgentAction(next_move);
		} else {
			if (fantom_scared) {
				return _behavior_ghostScarred.play(game, _position, destination);
			} else {
				return _behavior_normal.play(game, _position, destination);
			}
		}
	}
	//constructeur de agent
	//un agent il as une position et le type pac ou fantom
	public Agent(PositionAgent pos, Type_Agnet type_agent) {
		set_position(pos);
		set_type(type_agent);
		_positio_du_depart = pos;
		_en_vie = true;
	}
	public void update(PacmanGame state, PacmanGame nextState, AgentAction action, double reward) {
			
		this._behavior_normal.update(state, nextState, action, reward) ;
	}
	
//getter && setter utile

	public Type_Agnet get_type() {
		return _type;
	}

	public void set_type(Type_Agnet _type) {
		this._type = _type;
	}

	public PositionAgent get_position() {
		return _position;
	}

	public void set_position(PositionAgent _position) {
		this._position = _position;
	}


	public boolean is_en_vie() {
		return _en_vie;
	}

	public void set_en_vie(boolean _en_vie) {
		this._en_vie = _en_vie;
	}

	public void setBeheviorNormal(Strategy b) {
		_behavior_normal = b;
	}

	public void setBeheviorScared(Strategy b) {
		_behavior_ghostScarred = b;
	}
	
	public PositionAgent get_positio_du_depart(){
		return _positio_du_depart;
	}
}