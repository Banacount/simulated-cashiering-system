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
	public static Boolean canClickNext = false;
	public static int allProductsCount = 12;
	public static double TotalSales = 0;
	public static JFrame mainWindow;
	public static Font itemQuantityFont;
	public static Font itemNameFont;
	public static Font itemPriceFont;
	public static Font tableHeaderFont;
	public static Font inputLabelFont;
	public static Font inputFont;

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
	
	class ButtonConfig {
		public static void disableButtonStyle (JButton button) {
			button.setForeground(Config.colors.disabled_color);
			button.setBackground(Config.colors.item_container_bg);
			button.setBorder(BorderFactory.createLineBorder(Config.colors.disabled_color, 2));
		}

		public static void inputButtonsStyles (JButton okButton, JButton nextButton, JButton cancelButton, JButton salesButton) 
		{
			okButton.setForeground(Config.colors.fg_blue);
			okButton.setBackground(Config.colors.item_selected_border);

			nextButton.setForeground(Config.colors.item_container_bg);
			nextButton.setBackground(Config.colors.item_selected_border);
			nextButton.setBorder(null);

			cancelButton.setForeground(Config.colors.fg_red);
			cancelButton.setBackground(Config.colors.item_selected_border);

			salesButton.setForeground(Config.colors.item_container_bg);
			salesButton.setBackground(Config.colors.item_selected_border);
		}

		public static void inputButtonsFunctions (JButton okButton, JButton nextButton, JButton cancelButton, JButton salesButton, ProductItemManager productItemManager, Object[] inputs) 
		{
			JFormattedTextField totalIn = (JFormattedTextField) inputs[0];
			JFormattedTextField cashIn = (JFormattedTextField) inputs[1];
			JFormattedTextField changeIn = (JFormattedTextField) inputs[2];
			Config.ButtonConfig.disableButtonStyle(nextButton);
			nextButton.setEnabled(false);

			okButton.addActionListener(e -> {
				Number cashNum = (Number) cashIn.getValue();
				Number totalNum = (Number) totalIn.getValue();
				double cashValue = cashNum.doubleValue();
				double totalValue = totalNum.doubleValue();

				if (cashValue <= 0) {
					JOptionPane.showMessageDialog(totalIn,  
						"Please input cash.", 
						"Warning", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (cashValue < totalValue) {
					JOptionPane.showMessageDialog(totalIn,  
						"Insufficient amount.", 
						"Warning", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				productItemManager.disableAllProduct();
				changeIn.setValue(cashValue - totalValue);
				Config.canClickNext = true;
				Config.ButtonConfig.inputButtonsStyles(okButton, nextButton, cancelButton, salesButton);
				nextButton.setEnabled(true);
			});

			nextButton.addActionListener(e -> {
				if (!Config.canClickNext) return;

				// Add the globalTable
				DefaultTableModel model = productItemManager.Model;
				DefaultTableModel globalModel = productItemManager.GlobalModel;
				int totalRow = model.getRowCount();

				Config.TotalSales = 0;
				for (int t = 0; t < totalRow; t++) {
					int rowId = (Integer) model.getValueAt(t, 4),
						quantity = (Integer) model.getValueAt(t, 2),
						price = (Integer) globalModel.getValueAt(rowId, 1),
						productSold = (Integer) globalModel.getValueAt(rowId, 2);

					Config.TotalSales += price*(quantity+productSold);
					String total = "₱ "+ (price*(quantity+productSold));
					globalModel.setValueAt(productSold+quantity, rowId, 2);
					globalModel.setValueAt(total, rowId, 3);
				}
				// Calculate the total sales
				String total = "₱ " + Config.TotalSales;
				globalModel.setValueAt((Object)total, 14, 3);

				productItemManager.resetAllProduct();
				totalIn.setValue(0);
				cashIn.setValue(0);
				changeIn.setValue(0);

				JOptionPane.showMessageDialog(
					cancelButton, 
					"Added to sales inventory.", 
					"Success", 
					JOptionPane.INFORMATION_MESSAGE);

				Config.canClickNext = false;
				Config.ButtonConfig.disableButtonStyle(nextButton);
				nextButton.setEnabled(false);
			});

			cancelButton.addActionListener(e -> {
				productItemManager.resetAllProduct();
				totalIn.setValue(0);
				cashIn.setValue(0);
				changeIn.setValue(0);

				JOptionPane.showMessageDialog(
					cancelButton, 
					"The operation has been canceled.", 
					"Canceled", 
					JOptionPane.CANCEL_OPTION);

				Config.canClickNext = false;
				Config.ButtonConfig.disableButtonStyle(nextButton);
				nextButton.setEnabled(false);
			});

			salesButton.addActionListener(e -> {
				// print global table (testing)
				DefaultTableModel global = productItemManager.GlobalModel;
				int totalRow = Config.allProductsCount;

				for (int i = 0; i < totalRow; i++) {
					System.out.print("Name: " + global.getValueAt(i, 0) + ", ");
					System.out.print("Price: " + global.getValueAt(i, 1) + ", ");
					System.out.print("Quantity: " + global.getValueAt(i, 2) + ", ");
					System.out.print("Subtotal: " + global.getValueAt(i, 3) + ", ");
					System.out.println("");
				}
				System.out.println("\n");

				// Show GUI table
				ProductLister.showSalesInventory(productItemManager.GlobalModel);
			});
		}
	}
	Config () {}
}
