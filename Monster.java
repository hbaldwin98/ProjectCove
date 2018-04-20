package projectCove;

public class Monster
{
	private int health;
	private String name;
	private int minDamage;
	private int maxDamage;
	private boolean aliveStatus;
	private double protection;
	private int experience;
	
	public Monster()
	{
		name = "Monster";
		health = 10;
		protection = 0.2;
		experience = 20;
		minDamage = 1;
		maxDamage = 3;
		aliveStatus = true;
	}
	
	public Monster(int level)
	{
		if(level == 1)
		{
			name = "Little Skeleton";
			health = 10;
			protection = 0.1;
			experience = 25;
			minDamage = 1;
			maxDamage = 3;
			aliveStatus = true;
		} 
		else if(level == 2)
		{
			name = "Skeleton";
			health = 18;
			protection = 0.15;
			experience = 45;
			minDamage = 2;
			maxDamage = 4;
			aliveStatus = true;
		}
		else if(level == 3)
		{
			name = "Big Skeleton";
			health = 25;
			protection = 0.2;
			experience = 65;
			minDamage = 2;
			maxDamage = 6;
			aliveStatus = true;
		}
		else if(level == 4)
		{
			name = "Giant Skeleton";
			health = 40;
			protection = 0.25;
			experience = 100;
			minDamage = 2;
			maxDamage = 12;
			aliveStatus = true;
		}
	}
	
	/***This deals with random rolls of damage for the monsters attacks and its hit chance.*/
	public int attack(Player player)
	{
		int damage = (int)(Math.random() * (maxDamage - minDamage + 1)) + minDamage;
		double hitChance = Math.random();

		if(hitChance > 0.7)
			return 0;
		else
			player.takeDamage(damage);

		return damage;
	}
	
	public void takeDamage(int damage)
	{
		health -= damage;
		
		if (health < 1)
			aliveStatus = false;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public int getExperience()
	{
		return experience;
	}
	
	public double getProtection()
	{
		return protection;
	}
	
	public boolean isAliveStatus()
	{
		return aliveStatus;
	}
}
