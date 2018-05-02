package projectCove;

public class Play extends ProjectCove
{
	static Player player;
	static Monster monster;

	public static void main(String[] args)
	{
		player = new Player();

		display();

		while (true)
		{
			mainMenu();
			
			monster = new Monster(player.getLevel());
			encounter(monster, player);
		}
	}
}
