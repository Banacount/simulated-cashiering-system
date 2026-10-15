package org.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Config {
	// Global Variable
	static Boolean canClickNext = false;
	static int allProductsCount = 12;
	static double TotalSales = 0;
	static JFrame mainWindow;
	static ProductItemManager productItemManager;

	// Fonts
	static Font itemQuantityFont;
	static Font itemNameFont;
	static Font itemPriceFont;
	static Font tableHeaderFont;
	static Font inputLabelFont;
	static Font inputFont;

	// Inputs
	static JFormattedTextField totalInput;
	static JFormattedTextField cashInput;
	static JFormattedTextField changeInput;

	class colors {
		final static Color bg_color = new Color(219, 0, 7);
		final static Color fg_color = new Color(0, 0, 0);
		final static Color item_container_bg = new Color(255, 255, 255);
		final static Color item_border = new Color(194, 194, 194);
		final static Color item_selected_border = new Color(255, 188, 13);
		final static Color fg_red = new Color(194, 29, 29);
		final static Color fg_blue = new Color(78, 116, 212);
		final static Color disabled_color = new Color(150, 150, 150);
	}
	
	Config () {}
}

