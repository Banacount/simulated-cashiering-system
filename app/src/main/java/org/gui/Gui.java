package org.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.*; 

class GUI {
	JFrame JF;

	// font sizes
	Font h1_font;
	Font h2_font;
	Font h3_font;
	Font h4_font;
	Font h5_font;
	Font h6_font;

	GUI () { 
		// Enable anti-aliasing
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

		JF = new JFrame("McDonalds Cashiering System");

		try {
			InputStream file = GUI.class.getResourceAsStream("/speedee_bold.ttf");

			if (file == null) {
				throw new IOException("Cannot get speede_bold font.");
			}

			// load fonts
			Font baseFont = Font.createFont(Font.TRUETYPE_FONT, file);
			h1_font = baseFont.deriveFont(32f);
			h2_font = baseFont.deriveFont(24f);
			h3_font = baseFont.deriveFont(18f);
			h4_font = baseFont.deriveFont(16f);
			h5_font = baseFont.deriveFont(13f);
			h6_font = baseFont.deriveFont(10f);
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();

			h1_font = new Font("Arial", Font.PLAIN, 32);
			h2_font = new Font("Arial", Font.PLAIN, 24);
			h3_font = new Font("Arial", Font.PLAIN, 18);
			h4_font = new Font("Arial", Font.PLAIN, 16);
			h5_font = new Font("Arial", Font.PLAIN, 13);
			h6_font = new Font("Arial", Font.PLAIN, 10);
		}
	}

	public void config () {
		JF.setLayout(null);
	}

	public void addContents () {
		// McDonalds Logo
		JLabel mcLogo = MyUtils.imageLabel(20, 20, 50, 50, "/mcdonalds_logo.png");
		JF.add(mcLogo);

		// Products
		JPanel item_container = new JPanel();
		item_container.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));

		JPanel item = MyUtils.productItem(
			"Test", 
			"P 50", 
			"/test_product.png", 
			h5_font, 
			h2_font
		);
		JPanel item2 = MyUtils.productItem(
			"Test", 
			"P 50", 
			"/test_product.png", 
			h5_font, 
			h2_font
		);
		JPanel item3 = MyUtils.productItem(
			"Test", 
			"P 50", 
			"/test_product.png", 
			h5_font, 
			h2_font
		);
		item_container.add(item);
		item_container.add(item2);
		item_container.add(item3);
		item_container.setBounds(20, 100, 700, 500);
		JF.add(item_container);
	}

	public void display () {
		JF.setResizable(false);
		JF.setSize(800, 700);
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

	public static JPanel productItem (String name, String price, String imagePath, Font font_1, Font font_2) 
	{
		JPanel item = new JPanel();
		JLabel productImage = MyUtils.imageLabel(0, 0, 200, 200, imagePath);

		JLabel productName = new JLabel(name, SwingConstants.CENTER);
		JLabel productPrice = new JLabel(price, SwingConstants.CENTER);

		int offset = 202;

		productName.setFont(font_1);
		productName.setBounds(0, offset+5, 200, 20);

		productPrice.setFont(font_2);
		productPrice.setBounds(0, offset+30, 200, 30);

		item.setLayout(null);
		item.add(productImage);
		item.add(productName);
		item.add(productPrice);
		item.setBorder(BorderFactory.createLineBorder(Config.colors.fg_color_2, 5));
		item.setBackground(Config.colors.fg_color_2);
		item.setSize(200, 260);
		item.setPreferredSize(new Dimension(200, 260));

		return item;
	}
}

//	for testing the sizes:
//		test.setBorder(BorderFactory.createLineBorder(Config.colors.fg_color, 2));
