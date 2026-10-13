package org.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter; 

class GUI {
	JFrame JF;

	// font sizes
	Font itemQuantityFont;
	Font itemNameFont;
	Font itemPriceFont;
	Font tableHeaderFont;
	Font inputLabelFont;
	Font inputFont;
	ProductLister productList;

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
			itemNameFont = baseFont.deriveFont(12f);
			tableHeaderFont = baseFont.deriveFont(9f);
			inputLabelFont = baseFont.deriveFont(18f);
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();

			itemQuantityFont = new Font("Arial", Font.PLAIN, 32);
			itemNameFont = new Font("Arial", Font.PLAIN, 3);
			tableHeaderFont = new Font("Arial", Font.PLAIN, 1);
			inputLabelFont = new Font("Arial", Font.PLAIN, 16);
		}

		itemPriceFont = new Font("Arial", Font.PLAIN, 12);
		inputFont = new Font("Arial", Font.PLAIN, 15);
		JF.setLayout(null);
	}

	public void addContents () {
		// McDonalds Logo
		JLabel mcLogo = MyUtils.imageLabel(8, 8, 50, 50, "/mcdonalds_logo.png");
		JF.add(mcLogo);

		// Product Lister
		productList = new ProductLister(tableHeaderFont);
		productList.scrollList.setBounds(720, 60, 345, 320);
		JF.add(productList.scrollList);


		// Cashier operations
		// Total input and label
		int inputOffset = 360;
		JFormattedTextField totalIn = MyUtils.inputOperations(
			inputFont, inputLabelFont, 
			inputOffset, JF, "Total", false
		);
		JF.add(totalIn);
		// Cash input and label
		inputOffset += 80;
		JFormattedTextField cashIn = MyUtils.inputOperations(
			inputFont, inputLabelFont, 
			inputOffset, JF, "Cash", true
		);
		JF.add(cashIn);
		// Change input and label
		inputOffset += 80;
		JFormattedTextField changeIn = MyUtils.inputOperations(
			inputFont, inputLabelFont, 
			inputOffset, JF, "Change", false
		);
		JF.add(changeIn);


		// Products and its container
		JPanel item_container = new JPanel();
		ProductItemManager products = new ProductItemManager(
			itemNameFont, itemPriceFont, itemQuantityFont, 
			productList.model, productList.globalModel, totalIn
		);
		item_container.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 6));
		item_container.setBackground(Config.colors.item_container_bg);
		item_container.setBounds(8, 60, 700, 620);
		products.render(item_container);
		JF.add(item_container);

		// Buttons
		// - ok
		JButton okButton = new JButton("OK"); okButton.setFont(inputLabelFont);
		okButton.setBounds(1000, 630, 60, 40);
		// - next
		JButton nextButton = new JButton("Next Customer"); nextButton.setFont(inputLabelFont);
		nextButton.setBounds(830, 630, 165, 40);
		// - cancel
		JButton cancelButton = new JButton("Cancel"); cancelButton.setFont(inputLabelFont);
		cancelButton.setBounds(721, 630, 104, 40);
		// - sales inventory
		JButton salesButton = new JButton("Sales Inventory"); salesButton.setFont(inputLabelFont);
		salesButton.setBounds(880, 16, 180, 40);

		Config.ButtonConfig.inputButtonsStyles(okButton, nextButton, cancelButton, salesButton);
		Config.ButtonConfig.inputButtonsFunctions(
			okButton, nextButton, cancelButton, salesButton, 
			products, new Object[] {totalIn, cashIn, changeIn}
		);
		JF.add(okButton);
		JF.add(nextButton);
		JF.add(cancelButton);
		JF.add(salesButton);
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
	static Image tempGrayImage, tempOrigImage;

	public static JFormattedTextField inputOperations (Font inputFont, Font inputLabelFont, int offset, JFrame JF, String labelText, Boolean canEdit) {
		JLabel label = MyUtils.quickLabel(720, offset, labelText, inputLabelFont);
		JFormattedTextField field = MyUtils.quickInput(720, offset+60, inputFont, canEdit);

		JF.add(label);
		return field;
	}

	public static JLabel imageLabel (int x, int y, int width, int height, String imagePath) 
	{
		Image importImage = new ImageIcon(
			GUI.class.getResource(imagePath))
			.getImage()
			.getScaledInstance(width, height, Image.SCALE_SMOOTH);

		ImageIcon loadImage = new ImageIcon(importImage);
		JLabel label = new JLabel(loadImage);
		label.setBounds(x, y, width, height);

		// Process orig and gray version
		tempOrigImage = importImage;
		tempGrayImage = GrayFilter.createDisabledImage(importImage);

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

	public static JLabel quickLabel (int x, int y, String label, Font font) {
		JLabel label_ = new JLabel(label);
		label_.setFont(font);
		label_.setBounds(x, y, 100, 100);
		label_.setForeground(Config.colors.item_selected_border);
		return label_;
	}

	public static JFormattedTextField quickInput (int x, int y, Font inputFont, Boolean canEdit) {
		NumberFormat format = NumberFormat.getIntegerInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setMinimum(0);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);

		JFormattedTextField input = new JFormattedTextField(formatter);
		input.setColumns(10);
		input.setValue(0);
		input.setBounds(x, y, 338, 40);
		input.setEditable(canEdit);
		input.setFont(inputFont);

		return input;
	}
}

//	for testing the sizes:
//		test.setBorder(BorderFactory.createLineBorder(Config.colors.fg_color, 2));
