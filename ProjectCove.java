package projectCove;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ProjectCove
{
	public static JTextArea screenText;
	public static JTextField textInput;
	public static String input;
	private static JFrame display;
	private static Areas areas;
	public static Player player;
	public static Monster monster;

	public static void startGame()
	{
		player = new Player();
		areas = new Areas();
		monster = new Monster();

		screenText = new JTextArea("");
		textInput = new JTextField("");
		input = "";
		display = new JFrame("Project Cove");
		Container c = display.getContentPane();
		display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		screenText.setLineWrap(true);
		screenText.setWrapStyleWord(true);
		screenText.setBounds(25, 25, 600, 300);
		screenText.setFont(new Font("Verdana", Font.BOLD, 14));
		screenText.setEditable(false);
		screenText.setForeground(Color.WHITE);
		screenText.setBackground(Color.BLACK);
		textInput.setBounds(0, 385, 810, 25);
		
		textInput.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e)
			{
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER)
				{
					input = textInput.getText().toLowerCase();

					textInput.setText("");

					Areas.command(input);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});

		c.setLayout(null);
		c.setBackground(Color.BLACK);
		c.setPreferredSize(new Dimension(800, 400));
		c.add(textInput);
		c.add(screenText);
		display.pack();
		display.setVisible(true);
		display.setResizable(false);

		mainMenu();
	}

	/*** The main menu text. */
	public static void mainMenu()
	{
		screenText.setText("Welcome to Project Cove! This is a short-narrative adventure game with "
				+ "some RPG elements incorporated. The ultimate goal of the game is to reach the end,"
				+ " but be warned, skeletons are on the loose and will fight you!\n\n"
				+ "Once you are finished reading each portion of the story, type NEXT to go to the next part or you can try to to find some extra stuff by searching around *wink wink*."
				+ "\n\nAs soon as you would like to play, type PLAY. Or type anything for that matter. Or just press enter. It all works.");
	}

	/*
	 * battle an enemy. Take in level of monster and fight until death.
	 * while true
	 * 	display default battle text
	 * 	if (input == "attack")
	 * 		display damage dealt to monster
	 * 		if not monster is alive
	 * 			display monster is dead
	 * 			give player the exp relative to the monster level
	 * 			roll player drop for health and weapon
	 * 			set the active encounter to false.
	 * 			return false
	 * 		else
	 * 			monster deals damage to player
	 * 			if player is not alive
	 * 				display player dead
	 * 				exit game
	 * 	else if (input == "heal")
	 * 		use player health potion
	 * 
	 * reset input to "" (prevent's doing the same action again and getting into a loop)
	 * return true;
	 */
	
	/*** Deals with combat encounters between the player and monster */
	public static boolean battle(boolean battle, int encounter)
	{
		screenText.setText("You have " + player.getCurrentHealth() + " health left." + "\nThe " + monster.getName()
				+ " has " + monster.getHealth() + " health left!" + "\nWhat would you like to do? (ATTACK/HEAL)");

		if (input.equalsIgnoreCase("attack"))
		{
			screenText.setText("You deal " + player.attack(monster) + " damage to the " + monster.getName() + "!");

			// checks life status of monster.
			if (!monster.isAliveStatus())
			{
				screenText.setText("The monster is dead!");

				player.giveExp(monster.getExperience());
				monster.rollDrop(player);
				player.getEncounter(encounter).setActive(false);
				return false;
			}

			// if the monster is still alive have him attack the player
			else
			{
				// monster attacks the player
				screenText.append("\nYou take " + monster.attack(player) + " damage!");

				// checks if the player is alive
				if (!player.isAliveStatus())
				{
					screenText.setText("You were killed! Try again later!");
					System.exit(0);
				}

			}
		}
		// if the option selected is 2, then the player uses a health potion.
		else if (input.equalsIgnoreCase("heal"))
			player.useHealthPotion();

		input = "";
		return true;
	}

	public static void newMonster(int level)
	{
		monster = new Monster(level);
	}
}
