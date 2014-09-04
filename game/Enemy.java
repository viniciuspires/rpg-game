package game;


public class Enemy extends Character {
	
	@Override
	public String toString() {
		return "E";
	}

	@Override
	public void update() {
		Point playerSpot = Game.findPlayerSpot();
		Point mySpot = Game.findSpot(this);
		
		if ( Game.isCloseTo(mySpot, playerSpot) ) {
			attack(Game.getPlayer());
		} else {
			Game.moveCloserTo(mySpot, playerSpot, this);
		}
		
	}

	@Override
	public void die() {
		Game.removeFromMap(this);
	}
}
