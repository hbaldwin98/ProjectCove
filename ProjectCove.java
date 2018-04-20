package projectCove;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProjectCove
{
	static Player player;
	static Monster monster;
	static Scanner input = new Scanner(System.in);

	public static void main(String[] args)
	{
		monster = new Monster();
		player = new Player();

		//				for(int i = 0; i < 15; i++)
		//				{
		//					if(monster.getAliveStatus() == false)
		//					{
		//						System.out.println("Monster is dead!");
		//						player.giveExp(monster.getExperience());
		//						break;
		//					}
		//					else
		//					{
		//						System.out.println(monster.getName() + " takes: " + player.attack(monster) + "(" + monster.getHealth() + ")");
		//						try
		//						{
		//							TimeUnit.SECONDS.sleep(1);
		//						} catch (InterruptedException e)
		//						{
		//							// TODO Auto-generated catch block
		//							e.printStackTrace();
		//						}
		//		
		//						
		//					}
		//				}


		while(true)
		{
			monster = new Monster(2);
			encounter(monster, player);
		}
	}

	/***Deals with battle encounters between the player and monster*/
	public static void encounter(Monster monster, Player player)
	{
		while(monster.isAliveStatus() == true)
		{
			System.out.println("Press 1 to attack!\nPress 2 to use health potion!");
			int option = -1;

			//catches if the user inputs anything other than a number.
			try {
				option = input.nextInt();
			} catch (InputMismatchException a)
			{
				System.out.println("Please input a valid number.\n");
				input.nextLine();
			}

			//The option of 1 is attacking. This starts a scenario where the player attacks and than the monster attacks.
			if(option == 1) 
			{
				System.out.println("You dealt " + player.attack(monster) + " damage to the " + monster.getName() + "!");

				try
				{
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}

				if(monster.isAliveStatus() == false)
				{
					System.out.println("The monster is dead!");
					player.giveExp(monster.getExperience());
				}
				else	
				{
					System.out.println(monster.getName() + " has: " + monster.getHealth() + " HP left.");

					try
					{
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}

					System.out.println("Player takes: " + monster.attack(player) + "(" + player.getCurrentHealth() + ")\n");

					if(player.isAliveStatus() == false)
					{
						System.out.println("You were killed! Try again later!");
						System.exit(0);
					}
				}
			} 
			else if (option == 2)
				player.useHealthPotion();
			else
				System.out.println("Please input something other than " + option + ". Like attack with 1 or something.");
		}
	}

}
