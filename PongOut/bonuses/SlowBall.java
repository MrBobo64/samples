package model.bonuses;

import view.Entity;
import model.*;

public class SlowBall extends Bonus {
	Ball _ball;
	
	public SlowBall( Paddle paddle, int lifespan, Entity ent, int x, int y, int width, int height, GameLogic gl, Boolean own, Ball ball ) {
		super( paddle, lifespan, ent, x, y, width, height, gl, own );
		_ball = ball;
	}

	@Override
	public void action() {
		// null
	}

	@Override
	public void actionOnce() {
		_ball.setSpeed( (int) (_ball.getXvelocity() / 2), (int) (_ball.getYvelocity() / 2) );
	}

	@Override
	public void end() {
		_ball.setSpeed( _ball.getXvelocity() * 2, _ball.getYvelocity() * 2 );
	}
}
