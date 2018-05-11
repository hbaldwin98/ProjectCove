package projectCove;

/*
 * Hunter Baldwin Player class includes all the attributes for the player. It
 * also handles all types of encounters the player may deal with including:
 * dealing damage, taking damage, healing, leveling, etc.
 */
public class Player extends ProjectCove
{
	private int currentHealth;
	private int maxHealth;
	private int level;
	private int experience;
	private int minDamageDefault;
	private int maxDamageDefault;
	private int minDamage;
	private int maxDamage;
	private int potionAmount;
	private int pos;
	private double hitModifier;
	private boolean aliveStatus;
	private final int[] EXP_TABLE = { 83, 151, 275, 500, 911, 1657, 3017, 5490, 9992 };
	private Encounter[] encounters;
	private Weapon currentWeapon;

	public Player()
	{
		maxHealth = 17;
		currentHealth = maxHealth;
		level = 1;
		experience = 0;
		potionAmount = 3;
		hitModifier = 0.99;
		aliveStatus = true;
		minDamageDefault = 1;
		maxDamageDefault = 5;
		pos = 101;
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
			setText("\n\nCongratulations! You leveled up to " + level);
			setText("\n\nMinimum damage increased by: " + (int) ((minDamageDefault + 1.4) * 0.3) + " ("
					+ (minDamageDefault += (int) ((minDamageDefault + 1.4) * 0.3)) + ")");
			setText(("\nMaximum damage increased by: " + (int) ((maxDamageDefault + 1.4) * 0.3) + " ("
					+ (maxDamageDefault += (int) ((maxDamageDefault + 1.4) * 0.24)) + ")"));
			setText(("\nMaximum health increased by: " + (int) ((maxHealth + 1.4) * 0.3) + " ("
					+ (maxHealth += (int) ((maxHealth + 1.4) * 0.3)) + ")"));
			setText(("\nHit-chance increased.\n"));
			hitModifier += 0.03;
			currentHealth = maxHealth;
			minDamage = minDamageDefault + currentWeapon.getMinDamage();
			maxDamage = maxDamageDefault + currentWeapon.getMaxDamage();
		} else
		{
			setText(("\nYou're already at max level!\n"));
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
		setText("You earned: " + exp + "exp!\n");
		// this while loop is to ensure that in the off-chance the player somehow gets
		// enough exp to level twice or more, it will level it appropriately.
		while (experience > EXP_TABLE[level - 1])
			if (level != 10 && experience >= EXP_TABLE[level - 1])
				levelUp();
	}

	public void useHealthPotion()
	{
		int healAmount;

		if (potionAmount == 0)
		{
			setText(("\nYou are out of potions!\n"));
			return;
		}

		if (currentHealth == maxHealth)
		{
			setText(("\nHealth is already full!\n"));
		} else
		{
			potionAmount--;
			healAmount = (int) (Math.random() * ((maxHealth * 0.35) - (maxHealth * 0.15) + 1))
					+ (int) (maxHealth * 0.15);
			currentHealth += healAmount;
			if (currentHealth >= maxHealth)
			{
				setText("\nYou were healed: " + healAmount + ". You are now at MaxHP: " + maxHealth + "\n");
				setText("\nYou now have " + potionAmount + " health potions left.\n");
				currentHealth = maxHealth;
			} else
			{
				setText("\nYou were healed: " + healAmount + ". You now have: " + currentHealth + "HP!\n");
				setText("\nYou now have " + potionAmount + " health potions left.\n");
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

	public int getMaxHealth()
	{
		return maxHealth;
	}

	public boolean isAliveStatus()
	{
		return aliveStatus;
	}

	public void setAliveStatus(boolean aliveStatus)
	{
		this.aliveStatus = aliveStatus;
	}

	public void setCurrentHealth(int health)
	{
		currentHealth = health;
	}

	public void setCurrentWeapon(Weapon weapon)
	{
		currentWeapon = weapon;
		minDamage = minDamageDefault + currentWeapon.getMinDamage();
		maxDamage = maxDamageDefault + currentWeapon.getMaxDamage();
	}

	public void setPotionAmount(int potionAmount)
	{
		this.potionAmount = potionAmount;
	}

	public void setPos(int pos)
	{
		this.pos = pos;
	}

	public int getPos()
	{
		return pos;
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
