package org.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

		try {
			InputStream file = GUI.class.getResourceAsStream("/speedee_bold.ttf");

			if (file == null) {
				throw new IOException("Cannot get speede_bold font.");
			}

			// load fonts
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

class ProductItem {
	JPanel item;
	int quantity = 0, price;

	ProductItem (String name, int productPrice, String imagePath, Font nameFont, Font priceFont, Font quantityFont) {
		int width = 163 + 4, height = 194 + 4, imgPadding = 20;
		price = productPrice;

		item = new JPanel();
		JLabel productImage = MyUtils.imageLabel(
			imgPadding/2, 
			imgPadding/2, 
			width-imgPadding, 
			width-imgPadding, 
			imagePath
		);

		JLabel productNameLbl = new JLabel(name, SwingConstants.CENTER);
		JLabel productPriceLbl = new JLabel("₱ " + price, SwingConstants.CENTER);
		JLabel productQuantityLbl = new JLabel("0", SwingConstants.LEFT);

		int offset = width;

		productNameLbl.setFont(nameFont);
		productNameLbl.setBounds(imgPadding/2, offset-6, width-imgPadding, 16);
		productNameLbl.setForeground(Config.colors.fg_color);

		productPriceLbl.setFont(priceFont);
		productPriceLbl.setBounds(0, offset+6, width, 22);
		productPriceLbl.setForeground(Config.colors.fg_color);

		productQuantityLbl.setFont(quantityFont);
		productQuantityLbl.setBounds(10, 10, width-50, 40);
		productQuantityLbl.setForeground(Config.colors.item_selected_border);

		item.setLayout(null);
		item.add(productQuantityLbl);
		item.add(productImage);
		item.add(productNameLbl);
		item.add(productPriceLbl);

		item.setBackground(Config.colors.item_container_bg);
		item.setBorder(BorderFactory.createLineBorder(Config.colors.item_border, 1));
		item.setSize(width, height);
		item.setPreferredSize(new Dimension(width, height));
		item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// Item events
		productQuantityLbl.setVisible(false);
		item.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 3 && quantity > 0) quantity--;
				if (e.getButton() == 1) quantity++;

				if (quantity > 0) {
					item.setBorder(BorderFactory.createLineBorder(Config.colors.item_selected_border, 2));
					productQuantityLbl.setVisible(true);
					productQuantityLbl.setText(String.valueOf(quantity));
				} else {
					item.setBorder(BorderFactory.createLineBorder(Config.colors.item_border, 1));
					productQuantityLbl.setVisible(false);
				}

				System.out.println(name + ": " + quantity);
			}
		});
	}

	public JPanel getJPanel () {
		return item;
	}
}

//	for testing the sizes:
//		test.setBorder(BorderFactory.createLineBorder(Config.colors.fg_color, 2));
