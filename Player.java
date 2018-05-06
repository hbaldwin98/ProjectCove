package projectCove;

public class Player extends ProjectCove
{
	private int score;
	private int currentHealth;
	private int maxHealth;
	private int level;
	private int experience;
	private int minDamageDefault;
	private int maxDamageDefault;
	private int minDamage;
	private int maxDamage;
	private int potionAmount;
	private double hitModifier;
	private boolean aliveStatus;
	private final int[] EXP_TABLE = { 83, 151, 275, 500, 911, 1657, 3017, 5490, 9992 };
	private Encounter[] encounters;
	private Weapon currentWeapon;

	public Player()
	{
		score = 0;
		maxHealth = 17;
		currentHealth = maxHealth;
		level = 1;
		experience = 0;
		potionAmount = 3;
		hitModifier = 0.99;
		aliveStatus = true;
		minDamageDefault = 2;
		maxDamageDefault = 6;
		
		encounters = Areas.populateEncounters(encounters);
		
		
		if (currentWeapon != null)
		{
			minDamage = minDamageDefault + currentWeapon.getMinDamage();
			maxDamage = maxDamageDefault + currentWeapon.getMaxDamage();
		} else
		{
			minDamage = minDamageDefault;
			maxDamage = maxDamageDefault;
		}

	}

	/***
	 * This handles the random rolls for the player's damage dealt when attacking a
	 * monster. Also deals with the hit-chance for the player.
	 */
	public int attack(Monster monster)
	{
		int damage = (int) (Math.random() * (maxDamage - minDamage + 1)) + minDamage;
		double hitChance = Math.random();

		if (hitChance >= hitModifier - monster.getProtection())
			return 0;
		else
			monster.takeDamage(damage);

		return damage;
	}

	/*** This is used to deal damage to the player. */
	public void takeDamage(int damage)
	{
		currentHealth -= damage;

		if (currentHealth < 1)
			setAliveStatus(false);
	}

	/*** Levels up the player character. */
	public void levelUp()
	{
		// The player can only level until level 10.
		if (level < 10)
		{
			level++;
			screenText.setText("Congratulations! You leveled up to " + level);
			screenText.append("\nMinimum damage increased by: " + (int) ((minDamageDefault + 1.4) * 0.3) + " ("
					+ (minDamageDefault += (int) ((minDamageDefault + 1.4) * 0.3)) + ")");
			screenText.append(("\nMaximum damage increased by: " + (int) ((maxDamageDefault + 1.4) * 0.3) + " ("
					+ (maxDamageDefault += (int) ((maxDamageDefault + 1.4) * 0.24)) + ")"));
			screenText.append(("\nMaximum health increased by: " + (int) ((maxHealth + 1.4) * 0.3) + " ("
					+ (maxHealth += (int) ((maxHealth + 1.4) * 0.3)) + ")"));
			screenText.append(("\nHit-chance increased."));
			hitModifier += 0.03;
			currentHealth = maxHealth;
			minDamage = minDamageDefault + currentWeapon.getMinDamage();
			maxDamage = maxDamageDefault + currentWeapon.getMaxDamage();
		} else
		{
			screenText.setText(("You're already at max level!"));
		}
	}

	/***
	 * This function gives the player a certain amount of experience. This is
	 * typically used when a monster has been killed and gives the player experience
	 * based off the monster type.
	 */
	public void giveExp(int exp)
	{
		experience += exp;
		screenText.append("\nYou earned: " + exp + "exp!");
		while (experience > EXP_TABLE[level - 1])
			if (level != 10 && experience >= EXP_TABLE[level - 1])
				levelUp();
	}

	public void useHealthPotion()
	{
		int healAmount;

		if (potionAmount == 0)
		{
			screenText.setText(("You are out of potions!"));
			return;
		}

		if (currentHealth == maxHealth)
		{
			screenText.setText(("Health is already full!\n"));
		} else
		{
			potionAmount--;
			healAmount = (int) (Math.random() * ((maxHealth * 0.35) - (maxHealth * 0.15) + 1))
					+ (int) (maxHealth * 0.15);
			currentHealth += healAmount;
			if (currentHealth >= maxHealth)
			{
				screenText.setText("You were healed: " + healAmount + ". You are now at MaxHP: " + maxHealth);
				screenText.append("\nYou now have " + potionAmount + " health potions left.\n");
				currentHealth = maxHealth;
			} else
			{
				screenText.setText("You were healed: " + healAmount + ". You now have: " + currentHealth + "HP!");
				screenText.append("\nYou now have " + potionAmount + " health potions left.\n");
			}
		}
	}

	public void giveHealthPotion()
	{
		potionAmount++;
	}

	public int getCurrentHealth()
	{
		return currentHealth;
	}

	public Weapon getCurrentWeapon()
	{
		return currentWeapon;
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

	public void setCurrentWeapon(Weapon weapon)
	{
		currentWeapon = weapon;
		minDamage = minDamageDefault + currentWeapon.getMinDamage();
		maxDamage = maxDamageDefault + currentWeapon.getMaxDamage();
	}
	
	public Encounter getEncounter(int encounter)
	{
		return encounters[encounter];
	}
	
	public boolean getActiveEncounter(int encounter)
	{
		return encounters[encounter].isActive();
	}

}
