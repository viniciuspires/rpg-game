package game;

public class NoWeapon extends Weapon {

	@Override
	void hit(Character attacker, Character attacked) {
		double attack = attacker.getStrength();
		double defense = attacked.getResistence();
		
		attacked.reduceHealth(attack - defense);
	}

}
