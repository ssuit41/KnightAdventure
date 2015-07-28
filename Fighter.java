import java.util.Random;

public abstract class Fighter
{
	private int health_, dmgMin_, dmgRange_;
	private String name_, armor_, attackType_;
	static Random sRandom = new Random();
	Random random = new Random();
	
	abstract int fight(Hero h, Fighter e); //represents damage taken
		
	void takeDamage(int dmg) throws negativeDmgException
	{
		//Report damage and remaining health
		try
		{
			if(dmg < 0)
				throw new negativeDmgException();
			health_ = health_ - dmg; //try/catch to negative dmg
		}
		catch (negativeDmgException e)
		{
			
		}
	}
	
	@Override
	public String toString()
	{
		return String.format("\nName: %s\nHealth: %d\nArmor: %s\nDamage: %d - %d %s", 
				name_, health_, armor_, dmgMin_, dmgMin_ + dmgRange_, attackType_);
	}
	
	public static Fighter getRandomEnemy(int lvl)
	{
		Fighter monster = null;
		
		switch(sRandom.nextInt(5))
		{
		case 0:
			monster = new Ogre(lvl);
			break;
		case 1:
			monster = new Troll(lvl);
			break;
		case 2: 
			monster = new Goblin(lvl);
			break;
		case 3:
			monster = new Magician(lvl);
			break;
		case 4:
			monster = new Archer(lvl);
			break;
		}
		return monster;
		
	}
	
	void setName(String name){name_ = name;}
	String getName(){return name_;}
	
	void setHealth(int health){health_ = health;}
	int getHealth(){return health_;}
	
	void setDmgMin(int dmg){dmgMin_ = dmg;}
	int getDmgMin(){return dmgMin_;}
	
	void setDmgRange(int dmg){dmgRange_ = dmg;}
	int getDmgRange(){return dmgRange_;}
	
	void setArmor(String armor){armor_ = armor;}
	String getArmor(){return armor_;}
	
	void setAttack(String attack){attackType_ = attack;}
	String getAttack(){return attackType_;}
	
	Fighter(String name, String armor, String attack, int lvl)
	{
		name_ = name;
		armor_ = armor;
		attackType_ = attack;
		health_ = (70 + random.nextInt(50)) * lvl;
		dmgMin_ = 7 * lvl;
		dmgRange_ = 2 * lvl;
	}
}

class Hero extends Fighter
{
	int fight(Hero h, Fighter e) 
	{
		int dmg = 0;
		if((h.getArmor() == "Heavy" && e.getAttack() == "Magic")
				|| (h.getArmor() == "Light" && e.getAttack() == "Physical")
				|| (h.getArmor() == "Medium" && e.getAttack() == "Ranged"))
		{
			dmg = e.getDmgMin() + random.nextInt(e.getDmgRange()) + random.nextInt(e.getDmgRange() * 10);
		}
		else if((h.getArmor() == "Light" && e.getAttack() == "Magic")
				|| (h.getArmor() == "Heavy" && e.getAttack() == "Physical"))
		{
			dmg = e.getDmgMin() + random.nextInt(e.getDmgRange()) - random.nextInt(e.getDmgRange() * 10);
		}
		else 
		{
			dmg = e.getDmgMin() + random.nextInt(e.getDmgRange());
		}
		
		if(dmg < 0)
			dmg = 0;
		
		System.out.printf("%s did %d %s to %s\n", e.getName(), dmg, e.getAttack(), h.getName());
		return dmg;
	}
	
	Hero(String name, String armor, String attack, int lvl)
	{
		super(name, armor, attack, lvl);
	}
}

class Ogre extends Fighter
{
	int fight(Hero h, Fighter e)
	{
		int dmg = 0;
		if (random.nextInt(2) == 0) //represents ogres slow speed and likely hood to miss
			return dmg;
		else if (h.getAttack() == "Magic")
		{
			dmg = h.getDmgMin() + random.nextInt(h.getDmgRange()) + random.nextInt(h.getDmgRange() * 10);
		}
		else if (h.getAttack() == "Physical")
		{
			dmg = h.getDmgMin() + random.nextInt(h.getDmgRange()) - random.nextInt(h.getDmgRange() * 10);
		}
		else
		{
			dmg = h.getDmgMin() + random.nextInt(h.getDmgRange());
		}
		
		if(dmg < 0)
			dmg = 0;
		
		System.out.printf("%s did %d %s to %s\n", h.getName(), dmg, h.getAttack(), e.getName());
		return dmg;
	}
	
	Ogre(int lvl)
	{
		super("Ogre", "Heavy", "Physical", lvl);
		setHealth(getHealth() * 10);
		setDmgMin(getDmgMin() * 5);
	}
}

class Troll extends Fighter
{
	int fight(Hero h, Fighter e)
	{
		int dmg = 0;
		if (h.getAttack() == "Ranged")
		{
			dmg = h.getDmgMin() + random.nextInt(h.getDmgRange()) + random.nextInt(h.getDmgRange() * 10);
		}
		else
		{
			dmg = h.getDmgMin() + random.nextInt(h.getDmgRange());
		}
		
		if(dmg < 0)
			dmg = 0;
		
		System.out.printf("%s did %d %s to %s\n", h.getName(), dmg, h.getAttack(), e.getName());
		return dmg;
	}
	
	Troll(int lvl)
	{
		super("Troll", "Medium", "Physical", lvl);
	}
}

class Goblin extends Fighter
{
	int fight(Hero h, Fighter e)
	{
		int dmg = 0;
		if (h.getAttack() == "Physical")
		{
			dmg = h.getDmgMin() + random.nextInt(h.getDmgRange()) + random.nextInt(h.getDmgRange() * 10);
		}
		else if (h.getAttack() == "Magic")
		{
			dmg = h.getDmgMin() + random.nextInt(h.getDmgRange()) - random.nextInt(h.getDmgRange() * 10);
		}
		else
		{
			dmg = h.getDmgMin() + random.nextInt(h.getDmgRange());
		}
		
		if(dmg < 0)
			dmg = 0;
		
		System.out.printf("%s did %d %s to %s\n", h.getName(), dmg, h.getAttack(), e.getName());
		return dmg;
	}
	
	Goblin(int lvl)
	{
		super("Goblin", "Light", "Physical", lvl);
		setHealth(getHealth() / 2);
		setDmgMin(getDmgMin() / 2);
	}
}

class Magician extends Fighter
{
	int fight(Hero h, Fighter e)
	{
		int dmg = 0;
		if (h.getAttack() == "Physical")
		{
			dmg = h.getDmgMin() + random.nextInt(h.getDmgRange()) + random.nextInt(h.getDmgRange() * 10);
		}
		else if (h.getAttack() == "Magic")
		{
			dmg = h.getDmgMin() + random.nextInt(h.getDmgRange()) - random.nextInt(h.getDmgRange() * 10);
		}
		else
		{
			dmg = h.getDmgMin() + random.nextInt(h.getDmgRange());
		}
		
		if(dmg < 0)
			dmg = 0;
			
		System.out.printf("%s did %d %s to %s\n", h.getName(), dmg, h.getAttack(), e.getName());
		return dmg;
	}
	
	Magician(int lvl)
	{
		super("Magician", "Light", "Magic", lvl);
		setHealth(getHealth() / 2);
		setDmgMin(getDmgMin() * 2);
	}
}

class Archer extends Fighter
{
	int fight(Hero h, Fighter e)
	{
		int dmg = 0;
		if (h.getAttack() == "Ranged")
		{
			dmg = h.getDmgMin() + random.nextInt(h.getDmgRange()) + random.nextInt(h.getDmgRange() * 10);
		}
		else
		{
			dmg = h.getDmgMin() + random.nextInt(h.getDmgRange());
		}
		
		if(dmg < 0)
			dmg = 0;
				
		System.out.printf("%s did %d %s to %s\n", h.getName(), dmg, h.getAttack(), e.getName());
		return dmg;
	}
		
	Archer(int lvl)
	{
		super("Archer", "Medium", "Ranged", lvl);
	}
}

@SuppressWarnings("serial")
class negativeDmgException extends Exception
{
	
}