import java.util.ArrayList;
import java.util.Arrays;

public class Warrior {
	final private static ArrayList<String> RANKS = new ArrayList<String>(Arrays.asList("Pushover", "Novice", "Fighter",
			"Warrior", "Veteran", "Sage", "Elite", "Conqueror", "Champion", "Master", "Greatest"));
	private int experience;
	private String name;
	

	public Warrior() {
		this.experience = 100;
	}
	
	public Warrior(String name) {
		this.experience = 100;
		setName(name);
		
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name=name;
	}

	public int getWarriorLevel() {
		return this.experience / 100;
	}

	public void setWarriorLevel(int warriorLevel) {
		if (warriorLevel >= 1 && warriorLevel <= 100) {
			this.experience = warriorLevel * 100;
		} else {
			throw new IllegalArgumentException("Invalid level");
		}
	}

	public String getWarriorRank() {
		return RANKS.get(getWarriorLevel() / 10);
	}

	public int getExperince() {
		return this.experience;
	}

	public void setExperince(int experience) {
		if (experience >= 100 && experience <= 10000) {
			this.experience = experience;
		} else {
			throw new IllegalArgumentException("Experience must be greater than or equal 100 and less than or equal 10000");
		}
	}

	public int increaseExperience(int experience) {
		this.experience += experience;
		if (this.experience >= 10000) {
			this.experience = 10000;
		}
		return this.experience;

	}
	
	public static String[] battle(Warrior warrior1 , Warrior warrior2) {
		Warrior tempWarrior1 = new Warrior();
		tempWarrior1.setExperince(warrior1.getExperince());
		String[] battles = new String[2];
		battles[0]=warrior1.battle(warrior2);
		battles[1]=warrior2.battle(tempWarrior1);
		return battles ;
		
	}
	private String battle(Warrior enemy) {
		int differenceLevel = this.getWarriorLevel() - enemy.getWarriorLevel();
		if (differenceLevel == 0) {
			this.increaseExperience(10);
			return "A good fight";
		} 
		if (differenceLevel == 1) {
			this.increaseExperience(5);
			return "A good fight";
		}
		if (differenceLevel >= 2) {
			this.increaseExperience(0);
			return "Easy fight";
		}
		if (differenceLevel < 0) {
			if (differenceLevel <= -5
					&& (RANKS.indexOf(this.getWarriorRank()) < RANKS.indexOf(enemy.getWarriorRank()))) {
				return "You've been defeated";
			}
			this.increaseExperience(20 * differenceLevel * differenceLevel);
			return "An intense fight";
		}

		return null;

	}
	public String train(String description, int experiencePoints, int minimumLevel) {
		if(this.getWarriorLevel()<minimumLevel) {
			return "Not strong enough";
		}
		this.increaseExperience(experiencePoints);
		return description;
		
	}

}
