package projectCove;

public class ProjectCove extends Display
{
	static Player player;
	static Monster monster;

	public static void main(String[] args)
	{
		player = new Player();

		display();

		while (true)
		{
			monster = new Monster(player.getLevel());
			encounter(monster, player);
		}
	}

	/*** Deals with battle encounters between the player and monster */
	public static void encounter(Monster monster, Player player)
	{
		while (monster.isAliveStatus())
		{
			// if no input, display the default attack text
			while (input.equals(""))
			{
				screenText.setText("You have " + player.getCurrentHealth() + " health left." + "\nThe "
						+ monster.getName() + " has " + monster.getHealth() + " health left!"
						+ "\nWhat would you like to do? (ATTACK/HEAL)");
				pause(750);
			}

			// The option of 1 is attacking. This starts a scenario where the player attacks
			// and then the monster attacks.
			if (input.equalsIgnoreCase("attack"))
			{
				screenText.setText("You deal " + player.attack(monster) + " damage to the " + monster.getName() + "!");
				
				pause();
				
				// checks life status of monster.
				if (monster.isAliveStatus() == false)
				{
					screenText.setText("The monster is dead!");
					pause();
					player.giveExp(monster.getExperience());
					monster.rollDrop(player);

				}

				// if the monster is still alive have him attack the player
				else
				{
					// monster attacks the player
					screenText.append("\nYou take " + monster.attack(player) + " damage!");
					pause();

					// checks if the player is alive
					if (player.isAliveStatus() == false)
					{
						screenText.setText("You were killed! Try again later!");
						pause(3000);
						System.exit(0);
					}
				}
			}
			// if the option selected is 2, then the player uses a health potion.
			else if (input.equalsIgnoreCase("heal"))
				player.useHealthPotion();
			else
			{
				screenText.setText("Sorry, command not recognized.");
				pause();
			}

			input = "";
		}

	}
}
