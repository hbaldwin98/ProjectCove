package projectCove;

public class Areas extends ProjectCove
{
	private static int id;
	private static boolean battle;

	Areas()
	{
		id = 1;
	}

	public static void command(String input)
	{
		if (battle)
		{
			battle = battle(battle);
		} else
		{
			if (id == 1)
				beach(input);

			if (id == 2)
				cave(input);
		}
	}

	public static void beach(String input)
	{
		screenText.setText("A blinding light piercing from above causes you to blink your eyes. "
				+ "The feel of sand all around your body and the smell and the sea permeates around you."
				+ " Finally opening your eyes, you look around and find yourself on a beach in a cove. Wood litters"
				+ " the floor all around you and you spot a cave deep into the back of the cove.");

		if (input.contains("wood"))
		{
			screenText.setText(
					"The wood looks as if it was part of a ship. Most of it has been violently ripped apart and damaged."
							+ " You think a storm may be responsible, but are not sure.");
		}

		if (input.contains("cave") || input.contains("next"))
		{
			id = 2;
			cave(input);
		}

	}

	public static void cave(String input)
	{
		screenText.setText(
				"It takes you a few minutes to adjust your eyes, but after that you see a gigantic cave system. "
						+ "Stalagmites hang all around and blue moss seems to glow in the corner. The cave goes deeper in.");
		if (input.contains("blue") || input.contains("moss"))
		{
			screenText.setText("You go to the blue moss. Reaching down you pick it up out of the ground. "
					+ "Strangely the moss seems to continue glowing even after being picked up. "
					+ "As you start to walk away a rumbling sound appears from behind you. \n\nA skeleton attacks! (Press Enter to start the battle)");
			battle = true;
			newMonster(1);
		}
	}
}
