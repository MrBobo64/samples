package model.bonuses;

import view.Entity;
import view.Environment;
import model.Bonus;
import model.GameLogic;
import model.Paddle;

public class Expand extends Bonus {
	Paddle _paddle;
	Environment _env;
	Boolean _own;

	public Expand(Paddle paddle, int lifespan, Entity ent, int x, int y, int width, int height, GameLogic gl, boolean own) {
		super(paddle, lifespan, ent, x, y, width, height, gl, own);
		_paddle = paddle;
		_env = gl.env;
		_own = own;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionOnce() {
		int size = (_paddle.getHeight()-50)/10;
	
		String newsize;
		if(size < 6){
			if(size > -1){
				newsize = "+"+(size+1);
			}else{
				if(size == -1){
					newsize = "0";
				}else{
					newsize = "-"+(size+1);
				}
			}
		}else{
			newsize = "+5";
		}
		
		String name;
		
		if(_own)
			name = "paddle_1";
		else
			name = "paddle_2";

		_paddle.setEntity(_env.replaceEntity(name, "sprites/bonus/paddle"+newsize+".gif", _paddle.getX(), _paddle.getY()));

	}

	@Override
	public void end() {
		int size = (_paddle.getHeight()-50)/10;
		String newsize;
		if(size > -3){
			if(size > 1){
				newsize = "+"+(size-1);
			}else{
				if(size == 1){
					newsize = "0";
				}else{
					newsize = ""+(size-1);
				}
			}
		}else{
			newsize = "-3";
		}

		String name;
		if(_own)
			name = "paddle_1";
		else
			name = "paddle_2";
		
		_paddle.setEntity(_env.replaceEntity(name, "sprites/bonus/paddle"+newsize+".gif", _paddle.getX(), _paddle.getY()));
	}

}
