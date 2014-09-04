package game;

public abstract class Character {
	protected Weapon weapon = new NoWeapon();
	
	protected double health = 100;
	protected double strength = 1;
	protected double resistence = 0;

	abstract void update();
	
	public void attack(Character c) {
		this.weapon.hit(this, c);
	}
	
	public Weapon getWeapon() {
		return weapon;
	}
	

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	public double getHealth() {
		return health;
	}
	
	public void setHealth(double health) {
		this.health = health;
	}
	
	public double getStrength() {
		return strength;
	}
	
	public void setStrength(double strength) {
		this.strength = strength;
	}
	
	public double getResistence() {
		return resistence;
	}
	
	public void setResistence(double resistence) {
		this.resistence = resistence;
	}

	public void reduceHealth(double ammount) {
		health -= ammount;
		if ( health <= 0 ) {
			die();
		}
	}

	abstract void die();
}
