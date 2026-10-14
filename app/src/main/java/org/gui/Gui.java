package org.gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import static org.gui.Config.*;
import javax.swing.*;

class GUI {
	JFrame JF;

	// font sizes
	ProductLister productList;

	GUI () { 
		// Enable anti-aliasing
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

		JF = new JFrame("McDonalds Cashiering System");
		Config.mainWindow = JF;

		// load fonts
		try {
			InputStream file = GUI.class.getResourceAsStream("/speedee_bold.ttf");
			if (file == null) {
				throw new IOException("Cannot get speede_bold font.");
			}

			Font baseFont = Font.createFont(Font.TRUETYPE_FONT, file);

			Config.itemQuantityFont = baseFont.deriveFont(32f);
			Config.itemNameFont = baseFont.deriveFont(12f);
			Config.tableHeaderFont = baseFont.deriveFont(9f);
			Config.inputLabelFont = baseFont.deriveFont(18f);
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();

			Config.itemQuantityFont = new Font("Arial", Font.PLAIN, 32);
			Config.itemNameFont = new Font("Arial", Font.PLAIN, 3);
			Config.tableHeaderFont = new Font("Arial", Font.PLAIN, 1);
			Config.inputLabelFont = new Font("Arial", Font.PLAIN, 16);
		}

		Config.itemPriceFont = new Font("Arial", Font.PLAIN, 12);
		Config.inputFont = new Font("Arial", Font.PLAIN, 15);
		JF.setLayout(null);
	}

	public void addContents () {
		// McDonalds Logo
		JLabel mcLogo = Helpers.imageLabel(8, 8, 50, 50, "/mcdonalds_logo.png");
		JF.add(mcLogo);

		// Product Lister
		productList = new ProductLister(tableHeaderFont);
		productList.scrollList.setBounds(720, 60, 345, 320);
		productLister = productList; 
		JF.add(productList.scrollList);


		// Cashier operations
		// Total input and label
		int inputOffset = 360;
		JFormattedTextField totalIn = Helpers.inputOperations(
			inputFont, Config.inputLabelFont, 
			inputOffset, JF, "Total", false
		);
		JF.add(totalIn);
		// Cash input and label
		inputOffset += 80;
		JFormattedTextField cashIn = Helpers.inputOperations(
			inputFont, inputLabelFont, 
			inputOffset, JF, "Cash", true
		);
		JF.add(cashIn);
		cashInput = cashIn;
		// Change input and label
		inputOffset += 80;
		JFormattedTextField changeIn = Helpers.inputOperations(
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

//	for testing the sizes:
//		test.setBorder(BorderFactory.createLineBorder(Config.colors.fg_color, 2));
