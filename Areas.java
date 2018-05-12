package projectCove;
/*
 * Hunter Baldwin This essentially stores all the "game." This includes the text
 * and battle encounters.
 */

public class Areas extends ProjectCove
{
	private static int id;
	private static boolean battle;
	private static boolean battleMode;
	private static boolean start;
	private static Encounter currentEncounter; // current encounter keeps track of the currentEncounter (used for
												// battles)

	private static Player player;

	//Calling this starts the game.
	Areas()
	{
		id = 1;
		player = getPlayer();
		start = true;
		currentEncounter = new Encounter();
	}

	/*** Command is called every time enter is pressed in the TextField.*/
	public static void command(String input)
	{
		if (!player.isAliveStatus())
			System.exit(0);

		if (input.equals("battlemode"))
		{
			player.setPotionAmount(6);
			battleMode = true;
		}
		// If battle, then start a battle. This goes until battle is false;
		if (battle)
			battle = battle(battle, currentEncounter);
		else if (battleMode)
		{
			battleMode();
			battle = battle(battle, currentEncounter);
		} else // else, go to the area of which Id corresponds to.
		{
			if (id == 1)
				beach(input);
			else if (id == 2)
				cave(input);
			else if (id == 3)
				deepCave(input);
			else
				setText("\nYou've reached the end of the game!");

		}
	}

	/*** Starting beach area */
	public static void beach(String input)
	{

		if (player.getPos() == 101 && start)
		{
			setText("\nA blinding light piercing from above causes you to blink your eyes."
					+ " The feel of sand all around your body and the smell and the sea permeates around you."
					+ " Looking around and find yourself on a beach in a cove. Wood litters"
					+ " the floor all around you and you spot a cave deep into the back of the cove."
					+ " You also feel around your pockets and notice a few elixirs that could help restore your strength if you're in danger.\n");
			start = false;
		}
		if ((input.contains("back")) && player.getPos() == 102)
		{
			player.setPos(101);
			start = true;
			beach(input);
		}

		if ((input.contains("pick") || input.contains("up") || input.contains("grab"))
				&& player.getEncounter(0).isActive() && player.getPos() == 102)
		{
			setText("\nYou decide pick up a sharp piece of wood off the ground. "
					+ "It won't help too much, but it is something. You can never be too safe.\n");

			player.setCurrentWeapon(new Weapon("Sharp Stick", "stick", 1, 1));
			setText("\nYou equip a Sharp Stick. \n\nIt increases your minimum damage by 1 and maximum damage by 1.\n");
			player.getEncounter(0).setActive(false);
		} else if (input.contains("wood"))
		{
			setText("\nThe wood looks as if it was part of a ship. Most of it has been violently ripped apart and damaged."
					+ " You think a storm may be responsible, but are not sure. Some of the wood looks particularly sharp.\n");
			player.setPos(102);

		} else if (input.contains("cave"))
		{
			id = 2;
			player.setPos(201);
			start = true;
			cave(input);
		}
	}

	/*** First Cave area */
	public static void cave(String input)
	{
		if (player.getPos() == 201 && start)
		{
			setText("\nA chill wind blows from deep within the cave. An eerie silence hangs about."
					+ " Stalagmites hang all around and you see some blue moss that seems to be glowing in a small corner. The cave goes deeper in.\n");
			start = false;
		}

		if ((input.contains("back")) && player.getPos() != 201)
		{
			player.setPos(201);
			start = true;
			cave(input);
		}
		if (input.contains("stal"))
		{
			setText("\nThe stalagmites rise from the floor all over. They look sharp and pokey!\n");
			player.setPos(202);
		}

		if ((input.contains("blue") || input.contains("moss")) && player.getActiveEncounter(1))
		{
			setText("\nYou go to the blue moss. Reaching down you pick it up out of the ground. "
					+ "Strangely, the moss seems to continue glowing even after being picked up. "
					+ "You notice a skeleton of a human wedged into the stone wall. "
					+ "As you start to walk away a rumbling sound appears from behind you. \n\nA skeleton attacks! (Press Enter to start the battle)\n");
			newBattle(player.getEncounter(1).getMonsterLevel(), 1);
			start = true;

		} else if (((input.contains("blue") || input.contains("moss")) && !player.getActiveEncounter(1)))
		{
			setText("\nYou walk back over to the spot where the skeleton attacked you. The moss you previously held on the floor. You reach down to pick it back up."
					+ " Touching the moss you feel a cold chill sweep through body. You suddenly feel completely rejuvenated! The moss loses its glow shortly after."
					+ " You see the spot where you picked up the moss as well as a mark in the wall where the skeleton attacked you."
					+ " It seemed to have ripped itself out of the stone. Those skeletons must be pretty strong."
					+ " Although, you did beat it. You must be pretty strong as well. \n\nOr lucky.\n");
			player.setCurrentHealth(player.getMaxHealth());
			player.setPos(203);
		} else if ((input.contains("go") || input.contains("deep")))
		{
			setText("\nYou start to walk deeper into the cave, the sense of foreboding ever increasing every step you take."
					+ " Looking behind you, you see out into the cove of where you came from. Suddenly a violent shaking happens, throwing you down to the ground."
					+ " Rocks start falling from behind and before you know it, the cave exit has been sealed off! Darkness envelopes around you. \n\nSuddenly you hear a chatter."
					+ " A torch blares into existence deeper in the cave being carried by a skeleton! Before you can react, the skeleton rushes you."
					+ "\n\nA small skeleton attacks! (Press Enter to start the battle)\n");
			id = 3;
			start = true;
			player.setPos(301);
			newBattle(player.getEncounter(2).getMonsterLevel(), 2);
		}
	}

	/*** Deeper Cave area */
	public static void deepCave(String input)
	{
		if (player.getPos() == 301 && start)
		{
			setText("\nAfter the fight with the skeleton, you grabbed its torch. Walking deeper into the cave, the walls started to"
					+ " pulsate with a deep-red glow, as if it was a living being. The corpses of formerly living beings ground underneath your feet"
					+ " and the air grew an uncomfortable humid. Sounds of screams echo throughout the walls. You hear the chattering of skeletons"
					+ " all around. Before you can ascertain where you are at, a horde of skeletons rush at you!"
					+ "\n\nA skeleton army attacks! Survive as long as you can! (Press Enter to start the battle)\n");
			start = false;
			battleMode = true;
		}
	}

	/***
	 * Battle mode! Creates a new monster every time the previous one was killed.
	 */
	public static void battleMode()
	{
		setInput("");
		battle = true;
		newMonster(player.getLevel());
	}

	/***
	 * Prepares for a new battle and creates a new monster this is used in encounter
	 * battles throughout the game. It takes in the encounter # and then once it's
	 * finished, sets that encounter active to false.
	 */
	public static void newBattle(int level, int encounter)
	{
		currentEncounter = player.getEncounter(encounter);
		battle = true;
		newMonster(level);
	}

	/*** Manually populated encounters. */
	public static Encounter[] populateEncounters(Encounter[] encounters)
	{
		encounters = new Encounter[5];
		encounters[0] = new Encounter("pick up", "sharp stick", 0, true);
		encounters[1] = new Encounter("battle", "blue moss", 2, true);
		encounters[2] = new Encounter("battle", "deeper into cave", 1, true);
		return encounters;
	}
}
