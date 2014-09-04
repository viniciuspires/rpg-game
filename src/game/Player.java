package game;

public class Player extends Character {
	@Override
	public String toString() {
		return "P";
	}

	@Override
	public void update() {
		
	}

	@Override
	void die() {
		Game.removeFromMap(this);
		Game.over();
	}
}
