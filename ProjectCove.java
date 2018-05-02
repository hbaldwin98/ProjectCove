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
	public static JTextArea screenText = new JTextArea("");
	public static JTextField textInput = new JTextField("");
	public static String input = "";

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

	public static void display()
	{

		JFrame display = new JFrame("Project Cove");
		Dimension d = new Dimension(800, 400);
		Container c = display.getContentPane();
		Font font = new Font("Verdana", Font.BOLD, 14);
		display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Action action = new AbstractAction()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e)
			{
				input = textInput.getText();
				textInput.setText("");
			}
		};

		screenText.setLineWrap(true);
		screenText.setWrapStyleWord(true);
		screenText.setBounds(25, 25, 600, 300);
		screenText.setFont(font);
		screenText.setEditable(false);
		screenText.setForeground(Color.WHITE);
		screenText.setBackground(Color.BLACK);
		textInput.setBounds(0, 385, 810, 25);
		textInput.addActionListener(action);
		c.setLayout(null);
		c.setBackground(Color.BLACK);
		c.setPreferredSize(d);
		c.add(textInput);
		c.add(screenText);
		display.pack();
		display.setVisible(true);
		display.setResizable(false);
	}
	
	public static void mainMenu()
	{
		while (!input.equalsIgnoreCase("play"))
		{
			screenText.setText("Welcome to Project Cove! This is a short-narrative adventure game with "
					+ "some RPG elements incorporated. The ultimate goal of the game is to reach the end,"
					+ " but be warned, skeletons are on the loose and will put up a fight!\n\n"
					+ "As soon as you would like to play, type PLAY.");
			pause();
		}
		input = "";
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
