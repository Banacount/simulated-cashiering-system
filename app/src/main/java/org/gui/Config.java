package org.gui;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

public class Config {
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
		public static void inputButtonsStyles (JButton okButton, JButton nextButton, JButton cancelButton) 
		{
			okButton.setForeground(Config.colors.fg_blue);
			okButton.setBackground(Config.colors.item_selected_border);

			nextButton.setForeground(Config.colors.item_container_bg);
			nextButton.setBackground(Config.colors.item_selected_border);

			cancelButton.setForeground(Config.colors.fg_red);
			cancelButton.setBackground(Config.colors.item_selected_border);
		}

		public static void inputButtonsFunctions (JButton okButton, JButton nextButton, JButton cancelButton, ProductItemManager productItemManager, Object[] inputs) 
		{
			JFormattedTextField totalIn = (JFormattedTextField) inputs[0];
			JFormattedTextField cashIn = (JFormattedTextField) inputs[1];
			JFormattedTextField changeIn = (JFormattedTextField) inputs[2];

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
			});

			nextButton.addActionListener(e -> {
				productItemManager.resetAllProduct();
				totalIn.setValue(0);
				cashIn.setValue(0);
				changeIn.setValue(0);
			});

			//cancelButton.setForeground(Config.colors.fg_red);
		}
	}
	Config () {}
}
