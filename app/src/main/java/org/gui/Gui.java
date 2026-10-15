package org.gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import static org.gui.Context.*;
import static org.gui.Context.Fonts.*;
import javax.swing.*;

class GUI {
	JFrame JF;

	GUI () { 
		// Enable anti-aliasing
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

		JF = new JFrame("McDonalds Cashiering System");
		MainWindow = JF;

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
		// Assemble product objects and globalize the productItemManager
		ProductLister productList = new ProductLister(tableHeaderFont);
		ProductItemManager productItemManager = new ProductItemManager(productList);
		ProductManager = productItemManager;

		// McDonalds Logo
		JLabel mcLogo = Helpers.imageLabel(8, 8, 50, 50, "/mcdonalds_logo.png");
		JF.add(mcLogo);

		// Display product list (table)
		productList.scrollList.setBounds(720, 60, 345, 320);
		JF.add(productList.scrollList);

		// Cashier operations
		// Total input and label
		int inputOffset = 360;
		JFormattedTextField totalIn = Helpers.inputOperations(
			inputFont, inputLabelFont, 
			inputOffset, JF, "Total", false
		);
		TotalInput = totalIn;
		JF.add(totalIn);
		// Cash input and label
		inputOffset += 80;
		JFormattedTextField cashIn = Helpers.inputOperations(
			inputFont, inputLabelFont, 
			inputOffset, JF, "Cash", true
		);
		CashInput = cashIn;
		JF.add(cashIn);
		// Change input and label
		inputOffset += 80;
		JFormattedTextField changeIn = Helpers.inputOperations(
			inputFont, inputLabelFont, 
			inputOffset, JF, "Change", false
		);
		ChangeInput = changeIn;
		JF.add(changeIn);


		// Products and its container
		JPanel item_container = new JPanel();
		item_container.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 6));
		item_container.setBackground(Colors.ITEM_CONTAINER_BG);
		item_container.setBounds(8, 60, 700, 620);
		productItemManager.render(item_container);
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

		ButtonConfig handleButton = new ButtonConfig(okButton, nextButton, cancelButton, salesButton);
		handleButton.inputButtonsStyles();
		handleButton.inputButtonsFunctions();
		JF.add(okButton);
		JF.add(nextButton);
		JF.add(cancelButton);
		JF.add(salesButton);
	}

	public void display () {
		JF.setResizable(false);
		JF.setSize(1080,720);
		JF.setVisible(true);
		JF.getContentPane().setBackground(Colors.BG_COLOR);
		JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

//	for testing the sizes:
//		test.setBorder(BorderFactory.createLineBorder(Config.colors.fg_color, 2));
