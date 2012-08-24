package model.bonuses;

import view.Entity;
import model.*;

public class Freeze extends Bonus {

	public Freeze( Paddle paddle, int lifespan, Entity ent, int x, int y, int width, int height, GameLogic gl, Boolean own ) {
		super( paddle, lifespan, ent, x, y, width, height, gl, own );
	}
	
	public void actionOnce() {
		_paddle.setCurrentSpeed(0);
	}
	
	public void action() {
		// null
	}
	
	public void end() {
		_paddle.setCurrentSpeed(15);
	}

}
