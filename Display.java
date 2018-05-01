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

public class Display
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
		screenText.setText("A dark-looming cave stretches up ahead of you. ");
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
}
