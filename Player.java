package projectCove;


public class Player
{
	private int score;
	private int currentHealth;
	private int maxHealth;
	private int level;
	private int experience;
	private int minDamage;
	private int maxDamage;
	private int potionAmount;
	private double protection;
	private boolean aliveStatus;
	private final int[] EXP_TABLE = {83, 151, 275, 500, 911, 1657, 3017, 5490, 9992};
	//	private Weapon currentWeapon;
	//	private Spell currentSpell;
	//  private Something perks;

	public Player()
	{
		score = 0;
		maxHealth = 17;
		currentHealth = maxHealth;
		level = 1;
		experience = 0;
		potionAmount = 3;
		protection = 1;
		minDamage = 2; //+ currentWeapon.getMinDamage();
		maxDamage = 6; //+ currentWeapon.getMaxDamage();
		aliveStatus = true;
	}

	/***This handles the random rolls for the player's damage dealt when attacking a monster.
	 *  Also deals with the hit-chance for the player.*/
	public int attack(Monster monster)
	{
		int damage = (int)(Math.random() * (maxDamage - minDamage + 1)) + minDamage;
		double hitChance = Math.random();

		if(hitChance > protection - monster.getProtection())
			return 0;
		else
			monster.takeDamage(damage);

		return damage;
	}
	
	/***This is used to deal damage to the player.*/
	public void takeDamage(int damage)
	{
		currentHealth -= damage;
		
		if (currentHealth < 1)
			setAliveStatus(false);
	}
	
	/***Levels up the player character. */
	public void levelUp()
	{
		//The player can only level until level 10.
		if(level < 10)
		{
			level++;
			System.out.println("Congratulations! You leveled up to " + level);
			System.out.println("Minimum damage increased by: " + (int)((minDamage + 1.4) * 0.3) + " (" + (minDamage += (int) ((minDamage + 1.4) * 0.3)) + ")");
			System.out.println("Maximum damage increased by: " + (int)((maxDamage + 1.4) * 0.3) + " (" + (maxDamage += (int) ((maxDamage + 1.4) * 0.24)) + ")");
			System.out.println("Maximum health increased by: " + (int)((maxHealth + 1.4) * 0.3) + " (" + (maxHealth += (int) ((maxHealth + 1.4) * 0.20)) + ")");
			System.out.println("Maximum protection increased.");
			protection += 0.01;
			currentHealth = maxHealth;
		} 
		else
			System.out.println("You're already at max level!");
	}

	/***This function gives the player a certain amount of experience. This is typically used
	 *  when a monster has been killed and gives the player experience based off the monster type.*/
	public void giveExp(int exp)
	{
		experience += exp;
		System.out.println("You earned: " + exp + "exp!");
		while(experience > EXP_TABLE[level-1])
			if(level != 10 && experience >= EXP_TABLE[level-1])
				levelUp();
	}
	
	public void useHealthPotion()
	{
		int healAmount;
		if(potionAmount == 0)
		{
			System.out.println("You are out of potions!");
			return ;
		}
		
		if(currentHealth == maxHealth)
			System.out.println("Health is already full!\n");
		else
		{
			potionAmount--;
			healAmount = (int)(Math.random() * ((maxHealth * 0.25) - (maxHealth * 0.15) + 1)) + (int)(maxHealth * 0.15);
			currentHealth += healAmount;
			if(currentHealth >= maxHealth)
			{
				System.out.println("You were healed: " + healAmount + ". You are now at MaxHP: " + maxHealth);
				System.out.println("You now have " + potionAmount + " health potions left.\n");
				currentHealth = maxHealth;
			}
			else
			{
				System.out.println("You were healed: " + healAmount + ". You now have: " + currentHealth + "HP!");
				System.out.println("You now have " + potionAmount + " health potions left.\n");
			}
		}
	}
	
	public int getCurrentHealth()
	{
		return currentHealth;
	}
	
	public int getLevel()
	{
		return level;
	}

	public boolean isAliveStatus()
	{
		return aliveStatus;
	}

	public void setAliveStatus(boolean aliveStatus)
	{
		this.aliveStatus = aliveStatus;
	}
}
