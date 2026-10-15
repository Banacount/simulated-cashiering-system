package org.gui;

import java.awt.Font;
import java.awt.Image;
import java.text.NumberFormat;

import static org.gui.Config.*;
import javax.swing.*;
import javax.swing.text.NumberFormatter; 

public class Helpers {
	static Image tempGrayImage, tempOrigImage;

	static void showSalesInventory () {
		productItemManager.productLister.salesInventoryWindow.setVisible(true);
	}
	static void hideSalesInventory () {
		productItemManager.productLister.salesInventoryWindow.setVisible(false);
	}

	public static JFormattedTextField inputOperations (Font inputFont, Font inputLabelFont, int offset, JFrame JF, String labelText, Boolean canEdit) {
		JLabel label = Helpers.quickLabel(720, offset, labelText, inputLabelFont);
		JFormattedTextField field = Helpers.quickInput(720, offset+60, inputFont, canEdit);

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
