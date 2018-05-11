package projectCove;
/* Hunter Baldwin
 * Monster class. Handles the created monsters (skeletons) and their attributes.
 * As well deals with player drops from the monster.
 */
import javax.swing.JOptionPane;

public class Monster extends ProjectCove
{
	private int health;
	private int level;
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
		level = 1;
		aliveStatus = true;
	}

	public Monster(int level)
	{
		this.level = level;

		if (level == 1)
		{
			name = "Little Skeleton";
			health = 13;
			protection = 0.1;
			experience = 10;
			minDamage = 1;
			maxDamage = 3;
			aliveStatus = true;
		} else if (level == 2)
		{
			name = "Skeleton";
			health = 22;
			protection = 0.14;
			experience = 25;
			minDamage = 2;
			maxDamage = 4;
			aliveStatus = true;
		} else if (level == 3)
		{
			name = "Big Skeleton";
			health = 30;
			protection = 0.18;
			experience = 45;
			minDamage = 2;
			maxDamage = 6;
			aliveStatus = true;
		} else if (level == 4)
		{
			name = "Giant Skeleton";
			health = 43;
			protection = 0.23;
			experience = 75;
			minDamage = 2;
			maxDamage = 12;
			aliveStatus = true;
		}
	}

	/***
	 * This deals with random rolls of damage for the monsters attacks and its hit
	 * chance.
	 */
	public int attack(Player player)
	{
		int damage = (int) (Math.random() * (maxDamage - minDamage + 1)) + minDamage;
		double hitChance = Math.random();

		if (hitChance >= 0.7)
			return 0;
		else
			player.takeDamage(damage);

		return damage;
	}

	/*** Deals damage to the monster. */
	public void takeDamage(int damage)
	{
		health -= damage;

		if (health < 1)
			aliveStatus = false;
	}

	/*** This deals with health potion drop rolls as well as weapons drops. */
	public void rollDrop(Player player)
	{
		// Rolls for a health potion
		if (Math.random() >= 0.49)
		{
			player.giveHealthPotion();
			setText(name + " drops a health potion!\n");
		}
		
		// rolls to see if the player gets a weapon drop. The drop damage changes with
		// the monster's level.
		if (Math.random() >= 0.7)
		{
			Weapon weapon = null;

			switch (level)
			{
			case 1:
				weapon = new Weapon(2, 3);
				break;
			case 2:
				weapon = new Weapon(2, 5);
				break;
			case 3:
				weapon = new Weapon(2, 6);
				break;
			case 4:
				weapon = new Weapon(4, 6);
				break;
			default:
				break;
			}

			setText("\n" + name + " drops " + weapon.getName() + ". (Damage: " + weapon.getMinDamage() + "-"
					+ weapon.getMaxDamage() + ")\n");
			// if the player does not have a current weapon, then automatically equip the
			// new one.
			if (player.getCurrentWeapon() == null)
			{
				setText("\nYou don't have a weapon! Equipping new weapon.\n");
				player.setCurrentWeapon(weapon);

			} else
			{
				int response = JOptionPane.showConfirmDialog(null,
						"Do you want to swap out your current weapon? (Yes/No)\nOLD: "
								+ player.getCurrentWeapon().getName() + ". (Damage: "
								+ player.getCurrentWeapon().getMinDamage() + "-"
								+ player.getCurrentWeapon().getMaxDamage() + ")" + "\nNEW: " + weapon.getName()
								+ ". (Damage: " + weapon.getMinDamage() + "-" + weapon.getMaxDamage() + ")");
				// ask the player if they want to switch their old weapon out for the new one.

				// if yes, then switch, else no.
				if (response == JOptionPane.YES_OPTION)
				{
					player.setCurrentWeapon(weapon);
					setText("\nWeapon swapped!\n");
				} else
					return;
			}
		}
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
