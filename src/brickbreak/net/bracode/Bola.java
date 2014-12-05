package brickbreak.net.bracode;

import jplay.Sprite;

public class Bola extends Sprite {
	
	boolean moverX;
	boolean moverY;

	public Bola(String fileName, int numFrames, boolean eixoX, boolean eixoY) {
		super(fileName, numFrames);
		moverX = eixoX;
		moverY = eixoY;
	}
	
	public void moverX(long vel) {
		if(this.x + this.width <= 800 && moverX == true){
			this.x += vel;
		}
		else{
			moverX = false;
		}
		
		if(this.x >= 0 && moverX == false){
			this.x -= vel;
		}
		else{
			moverX = true;
		}
	}
	
	public void moverY(long vel) {
		if(this.y <= 600 && moverY == true){
			this.y += vel;
		}
		else{
			moverY = false;
		}
		
		if(this.y >= 50 && moverY == false){
			this.y -= vel;
		}
		else{
			moverY = true;
		}
	}
}

