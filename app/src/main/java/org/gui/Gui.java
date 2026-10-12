package org.gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.*; 

class GUI {
	JFrame JF;

	// font sizes
	Font itemQuantityFont;
	Font itemNameFont;
	Font itemPriceFont;

	GUI () { 
		// Enable anti-aliasing
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

		JF = new JFrame("McDonalds Cashiering System");

		// load fonts
		try {
			InputStream file = GUI.class.getResourceAsStream("/speedee_bold.ttf");
			if (file == null) {
				throw new IOException("Cannot get speede_bold font.");
			}

			Font baseFont = Font.createFont(Font.TRUETYPE_FONT, file);

			itemQuantityFont = baseFont.deriveFont(32f);
			itemNameFont = baseFont.deriveFont(12f);;
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();

			itemQuantityFont = new Font("Arial", Font.PLAIN, 32);
			itemNameFont = new Font("Arial", Font.PLAIN, 3);
		}

		itemPriceFont = new Font("Arial", Font.PLAIN, 12);
	}

	public void config () {
		JF.setLayout(null);
	}

	public void addContents () {
		// McDonalds Logo
		JLabel mcLogo = MyUtils.imageLabel(8, 8, 50, 50, "/mcdonalds_logo.png");
		JF.add(mcLogo);

		// Products
		JPanel item_container = new JPanel();
		item_container.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 6));

		for (int i = 0; i < 12; ++i) {
			ProductItem item = new ProductItem(
				"Test number " + i, 
				50, 
				"/test_product.png", 
				itemNameFont, 
				itemPriceFont,
				itemQuantityFont
			);
			item_container.add(item.getJPanel());
		}

		item_container.setBackground(Config.colors.item_container_bg);
		item_container.setBounds(8, 60, 700, 620);
		JF.add(item_container);
	}

	public void display () {
		JF.setResizable(false);
		JF.setSize(1080,720);
		JF.setVisible(true);
		JF.getContentPane().setBackground(Config.colors.bg_color);
		JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class MyUtils {
	public static JLabel imageLabel (int x, int y, int width, int height, String imagePath) 
	{
		Image importImage = new ImageIcon(
			GUI.class.getResource(imagePath))
			.getImage()
			.getScaledInstance(width, height, Image.SCALE_SMOOTH);

		ImageIcon loadImage = new ImageIcon(importImage);
		JLabel label = new JLabel(loadImage);
		label.setBounds(x, y, width, height);
		return label;
	}

	public static JButton imageButton (int x, int y, int width, int height, String imagePath) 
	{
		Image importImage = new ImageIcon(
			GUI.class.getResource(imagePath))
			.getImage()
			.getScaledInstance(width, height, Image.SCALE_SMOOTH);

		ImageIcon loadImage = new ImageIcon(importImage);
		JButton button = new JButton("TestImage", loadImage);
		button.setBounds(x, y, width, height);
		return button;
	}
}

//	for testing the sizes:
//		test.setBorder(BorderFactory.createLineBorder(Config.colors.fg_color, 2));
