package projectCove;
/* Hunter Baldwin
 * This class handles the GUI as well as includes certain functions
 * relating to the core game such as the battle function.
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ProjectCove
{
	private static JTextArea screenText;
	private static JScrollPane scrollPane;
	private static JTextField textInput;
	protected static String input;
	private static JFrame display;
	private static Player player;
	private static Monster monster;

	public static void startGame()
	{
		display = new JFrame();
		display.setResizable(false);
		display.setTitle("Project Cove");
		display.setBounds(100, 100, 800, 400);
		display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.getContentPane().setLayout(null);
		display.setBackground(Color.BLACK);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 795, 348);
		display.getContentPane().add(scrollPane);

		screenText = new JTextArea();
		screenText.setWrapStyleWord(true);
		screenText.setLineWrap(true);
		screenText.setMaximumSize(new Dimension(300, 2147483647));
		scrollPane.setViewportView(screenText);
		screenText.setFont(new Font("Verdana", Font.PLAIN, 14));
		screenText.setBackground(Color.BLACK);
		screenText.setEditable(false);
		screenText.setForeground(Color.WHITE);
		textInput = new JTextField();

		textInput.setBounds(0, 347, 795, 25);
		display.getContentPane().add(textInput);

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
				JScrollBar vertical = scrollPane.getVerticalScrollBar();
				vertical.setValue(vertical.getMaximum());

			}

			@Override
			public void keyTyped(KeyEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});
		display.setVisible(true);
		textInput.requestFocus();
		mainMenu();
	}

	/*** The main menu text. */
	private static void mainMenu()
	{
		player = new Player();
		monster = new Monster();
		new Areas();

		setText("Welcome to Project Cove! This is a short-narrative adventure game with "
				+ "some RPG elements incorporated. The ultimate goal of the game is to reach the end,"
				+ " but be warned, skeletons are on the loose and will fight you!\n\n"
				+ "Continue the story by following the cues the story gives you of the next area. As well, don't forgot to search around, you never know what you might find!"
				+ "\n\nAs soon as you would like to play, type PLAY. Or type anything for that matter. Or just press enter. It all works.\n\n"
				+ "Additionally, if you'd like to do the Battlemode, a mode where you fight skeletons until you drop, type 'battlemode.'\n"
				+ "\nDouble additionally, if you type anything and the output screen looks the same, either you inputted something invalid or you just have to press enter again."
				+ " This is just a temporary fix until I can force it to the next display. :)"
				+ "\n");

	}
	/***
	 * Deals with combat encounters between the player and monster Takes in the
	 * boolean for true (battle is still going) or false (battle is over)
	 */
	public static boolean battle(boolean battle, Encounter encounter)
	{
		if (input.equals(""))
			setText("\nYou have " + player.getCurrentHealth() + " health left." + "\nThe " + monster.getName()
					+ " has " + monster.getHealth() + " health left!" + "\nWhat would you like to do? (ATTACK/HEAL)\n");

		if (input.equalsIgnoreCase("attack"))
		{
			setText("\nYou deal " + player.attack(monster) + " damage to the " + monster.getName() + "!\n");

			// checks life status of monster.
			if (!monster.isAliveStatus())
			{
				setText("\nThe monster is dead!\n");

				player.giveExp(monster.getExperience());
				monster.rollDrop(player);
				encounter.setActive(false);
				return false;
			}

			// if the monster is still alive have him attack the player
			else
			{
				// monster attacks the player
				setText("You take " + monster.attack(player) + " damage!\n");

				// checks if the player is alive
				if (!player.isAliveStatus())
				{
					setText("\nYou were killed! Try again later!\n");
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

	/*** Reinitializes the monster to a new one. */
	public static void newMonster(int level)
	{
		monster = new Monster(level);
	}
	
	/*** Returns the player*/
	public Player getPlayer()
	{
		return player;
	}
	
	public static void setText(String text)
	{
		screenText.append(text);
	}

}
