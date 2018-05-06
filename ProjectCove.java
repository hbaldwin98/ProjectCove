package projectCove;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractAction;
import javax.swing.Action;
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

	public static void pause()
	{
		try
		{
			TimeUnit.MILLISECONDS.sleep(1500);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public static void pause(int time)
	{
		try
		{
			TimeUnit.MILLISECONDS.sleep(time);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

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

		Action action = new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				input = textInput.getText().toLowerCase();
				;
				textInput.setText("");

				Areas.command(input);
			}
		};

		textInput.addActionListener(action);

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

	public static void mainMenu()
	{
		screenText.setText("Welcome to Project Cove! This is a short-narrative adventure game with "
				+ "some RPG elements incorporated. The ultimate goal of the game is to reach the end,"
				+ " but be warned, skeletons are on the loose and will fight you!\n\n"
				+ "Once you are finished reading each portion of the story, type NEXT to go to the next part or you can try to to find some extra stuff by searching around *wink wink*."
				+ "\n\nAs soon as you would like to play, type PLAY. Or type anything for that matter. Or just press enter. It all works.");
	}

	/*** Deals with combat encounters between the player and monster */
	public static boolean battle(boolean battle)
	{
		if (!monster.isAliveStatus())
			return false;

		if (input.equals(""))
		{
			screenText.setText("You have " + player.getCurrentHealth() + " health left." + "\nThe " + monster.getName()
					+ " has " + monster.getHealth() + " health left!" + "\nWhat would you like to do? (ATTACK/HEAL)");
		}

		if (input.equalsIgnoreCase("attack"))
		{
			screenText.setText("You deal " + player.attack(monster) + " damage to the " + monster.getName() + "!");

			// checks life status of monster.
			if (monster.isAliveStatus() == false)
			{
				screenText.setText("The monster is dead!");

				player.giveExp(monster.getExperience());
				monster.rollDrop(player);
				return false;
			}

			// if the monster is still alive have him attack the player
			else
			{
				// monster attacks the player
				screenText.append("\nYou take " + monster.attack(player) + " damage!");

				// checks if the player is alive
				if (player.isAliveStatus() == false)
				{
					screenText.setText("You were killed! Try again later!");
					System.exit(0);
				}

				input = "";
			}

			return true;
		}
		// if the option selected is 2, then the player uses a health potion.
		else if (input.equalsIgnoreCase("heal"))
			player.useHealthPotion();

		return true;
	}

	public static void newMonster(int level)
	{
		monster = new Monster(level);
	}
}
