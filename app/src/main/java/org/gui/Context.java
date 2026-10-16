package org.gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;

public class Context {
	// State Variables
	static JFrame MainWindow;
	static Boolean CanClickNextButton = false;
	static int AllProductsCount;
	static double TotalSales = 0;
	static ProductItemManager ProductManager;
	static SalesService SalesManager;
	
	static JFormattedTextField TotalInput;
	static JFormattedTextField CashInput;
	static JFormattedTextField ChangeInput;

	// Initialized Variable (changed once only)
	static class Fonts {
		static Font itemQuantityFont;
		static Font itemNameFont;
		static Font itemPriceFont;
		static Font tableHeaderFont;
		static Font inputLabelFont;
		static Font inputFont;
	}

	// Non-state variables
	static String CURRENCY = "₱";
	static class Colors {
		final static Color BG_COLOR = new Color(219, 0, 7);
		final static Color FG_COLOR = new Color(0, 0, 0);
		final static Color ITEM_CONTAINER_BG = new Color(255, 255, 255);
		final static Color ITEM_BORDER = new Color(194, 194, 194);
		final static Color ITEM_SELECTED_BORDER = new Color(255, 188, 13);
		final static Color FG_RED = new Color(194, 29, 29);
		final static Color FG_BLUE = new Color(78, 116, 212);
		final static Color DISABLED_COLOR = new Color(150, 150, 150);
	}
}

