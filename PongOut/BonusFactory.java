package model;

import view.*;
import model.bonuses.*;
import java.util.Random;

public class BonusFactory {
	Brick _brick;
	Paddle _paddle;
	
	public static Bonus makeBonus( Ball ball, Brick brick, Paddle paddle, String name, GameLogic gl ) {
		Random rand = new Random();
		
		if( rand.nextInt(100) > 0 ) {
			int r = rand.nextInt(100);
			
			if( r > 70 ) {
				Bonus speed = makeSpeedBall( ball, brick, paddle, name, gl );
				return speed;
			}
			else if( r > 60 ) {
				Bonus frozen = makeFreeze( ball, brick, paddle, name, gl );
				return frozen;
			}
			else if( r > 50 ) {
				Bonus slow = makeSlowBall( ball, brick, paddle, name, gl );
				return slow;
			}
			else if(r > 40) {
				Bonus shrink = makeShrink( ball, brick, paddle, name, gl);
				return shrink;
			}
			else if(r > 30){
				Bonus expand = makeExpand( ball, brick, paddle, name, gl);
				return expand;
			}
		}
		
		return null;
	}
	
	public static Bonus makeFreeze( Ball ball, Brick brick, Paddle paddle, String name, GameLogic gl ) {
		int x = brick.getX() + brick.getWidth()/2 - 5;
		int y = brick.getY() + brick.getHeight()/2 - 5;
		boolean own = ball.getOwnership();
		
		Entity ent = new Entity( name, "sprites/bonus/frozen.gif", x, y );
		return new Freeze( paddle, 100, ent, x, y, 10, 10, gl, own );
	}
	
	public static Bonus makeSpeedBall( Ball ball, Brick brick, Paddle paddle, String name, GameLogic gl ) {
		int x = brick.getX() + brick.getWidth()/2 - 5;
		int y = brick.getY() + brick.getHeight()/2 - 5;
		boolean own = ball.getOwnership();
		
		Entity ent = new Entity( name, "sprites/bonus/speedball.gif", x, y );
		return new SpeedBall( paddle, 200, ent, x, y, 10, 10, gl, own, ball );
	}
	
	public static Bonus makeSlowBall( Ball ball, Brick brick, Paddle paddle, String name, GameLogic gl ) {
		int x = brick.getX() + brick.getWidth()/2 - 5;
		int y = brick.getY() + brick.getHeight()/2 - 5;
		boolean own = ball.getOwnership();
		
		Entity ent = new Entity( name, "sprites/bonus/slowball.gif", x, y );
		return new SlowBall( paddle, 200, ent, x, y, 10, 10, gl, own, ball );
	}
	
	public static Bonus makeShrink( Ball ball, Brick brick, Paddle paddle, String name, GameLogic gl ) {
		int x = brick.getX() + brick.getWidth()/2 - 5;
		int y = brick.getY() + brick.getHeight()/2 - 5;
		boolean own = ball.getOwnership();
		
		Entity ent = new Entity( name, "sprites/bonus/shrink.gif", x, y );
		return new Shrink( paddle, 1000, ent, x, y, 10, 10, gl, own);
	}
	
	public static Bonus makeExpand( Ball ball, Brick brick, Paddle paddle, String name, GameLogic gl ) {
		int x = brick.getX() + brick.getWidth()/2 - 5;
		int y = brick.getY() + brick.getHeight()/2 - 5;
		boolean own = ball.getOwnership();
		
		Entity ent = new Entity( name, "sprites/bonus/expand.gif", x, y );
		return new Expand( paddle, 1000, ent, x, y, 10, 10, gl, own);
	}
}
