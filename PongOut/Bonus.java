package model;

import view.*;
import java.util.Random;

public abstract class Bonus {
	protected Paddle _paddle;
	protected int _xspeed;
	protected int _yspeed;
	protected double _xChange;
	protected double _xacc;
	protected int _lifespan;
	protected int _X;
	protected int _Y;
	protected int _width;
	protected int _height;
	protected Entity _ent;
	protected boolean _collected;
	protected GameLogic _gl;

	public Bonus( Paddle paddle, int lifespan, Entity ent, int x, int y, int width, int height, GameLogic gl, boolean own ) {
			_paddle = paddle;
			Random r = new Random();
			_xspeed = (int) 4 - r.nextInt(8);
			_yspeed = (int) 5 - r.nextInt(10);
			_xChange = 0;
			if( own ) {
				_xacc = -.1;
			}
			else {
				_xacc = .1;
			}
			_lifespan = lifespan;
			_ent = ent;
			_ent.setZAxis(3);
			_X = x;
			_Y = y;
			_width = width;
			_height = height;
			_collected = false;
			_gl = gl;
			moveEntity();
	}
	
	public void collect() {
		_collected = true;
		actionOnce();
	}
	
	public boolean isCollected() {
		return _collected;
	}
	
	public Paddle getPaddle() {
		return _paddle;
	}
	
	public int getLifespan() {
		return _lifespan;
	}
	
	public int getXSpeed() {
		return _xspeed;
	}
	
	public int getYSpeed() {
		return _yspeed;
	}
	
	public int getX() {
		return _X;
	}
	
	public int getY() {
		return _Y;
	}
	
	public int getWidth() {
		return _width;
	}
	
	public int getHeight() {
		return _height;
	}
	
	public int getLeft() {
		return _X;
	}
	
	public int getRight() {
		int right = _X + _width;
		return right;
	}
	
	public int getTop() {
		return _Y;
	}
	
	public int getBottom() {
		int bottom = _Y + _height;
		return bottom;
	}
	
	public Entity getEntity() {
		return _ent;
	}
	
	public String getName() {
		return _ent.getName();
	}
	
	public void setY( int y ) {
		_Y = y;
	}
	
	public void setYSpeed( int speed ) {
		_yspeed = speed;
	}
	
	public void step() {
		action();
		_lifespan--;
		if (_lifespan < 0)
			end();
	}
	
	public void moveEntity() {
		_ent.setX(_X);
		_ent.setY(_Y);
	}
	
	public void move() {
		if( Math.abs(_xspeed) < 10 ) {
			//double adjust = 0;
			//if( _xacc < 0 ) adjust = -.5;
			//if( _xacc > 0 ) adjust = .5;
			_xChange = _xChange + _xacc;
			_xspeed = _xspeed + (int) (_xChange);// + adjust);
			if( Math.abs(_xChange) >= 1) {
				_xChange = 0;
			}
		}
		_X = _X + _xspeed;
		_Y = _Y + _yspeed;
		moveEntity();
	}

	public abstract void actionOnce();	// Single time action
	public abstract void action();		// Constant action
	public abstract void end();			// Action when power is gone
}
