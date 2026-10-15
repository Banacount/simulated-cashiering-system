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
import static org.gui.Config.*;

public class ButtonConfig {
	JButton okButton;
	JButton nextButton;
	JButton cancelButton;
	JButton salesButton;

	ButtonConfig (JButton ok_button, JButton next_button, JButton cancel_button, JButton sales_button) 
	{
		okButton = ok_button;
		nextButton = next_button;
		cancelButton = cancel_button;
		salesButton = sales_button;
	}

	static void disableButtonStyle (JButton button) {
		button.setForeground(Config.colors.disabled_color);
		button.setBackground(Config.colors.item_container_bg);
		button.setBorder(BorderFactory.createLineBorder(Config.colors.disabled_color, 2));
	}

	void inputButtonsStyles () 
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

	void inputButtonsFunctions () 
	{
		ButtonConfig.disableButtonStyle(nextButton);
		nextButton.setEnabled(false);
		DefaultTableModel model = productItemManager.productLister.model,
						  globalModel = productItemManager.productLister.globalModel;

		okButton.addActionListener(e -> {
			Number cashNum = (Number) cashInput.getValue();
			Number totalNum = (Number) totalInput.getValue();
			double cashValue = cashNum.doubleValue();
			double totalValue = totalNum.doubleValue();

			if (model.getRowCount() <= 0) {
				JOptionPane.showMessageDialog(totalInput,  
					"No Items.", 
					"Warning", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (cashValue <= 0) {
				JOptionPane.showMessageDialog(totalInput,  
					"Please input cash.", 
					"Warning", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (cashValue < totalValue) {
				JOptionPane.showMessageDialog(totalInput,  
					"Insufficient amount.", 
					"Warning", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			productItemManager.disableAllProduct();
			changeInput.setValue(cashValue - totalValue);
			cashInput.setEnabled(false);
			canClickNext = true;
			inputButtonsStyles();
			nextButton.setEnabled(true);
		});

		nextButton.addActionListener(e -> {
			if (!canClickNext) return;

			// Add the globalTable
			int totalRow = model.getRowCount();

			for (int t = 0; t < totalRow; t++) {
				int rowId = (Integer) model.getValueAt(t, 4),
					quantity = (Integer) model.getValueAt(t, 2),
					price = (Integer) globalModel.getValueAt(rowId, 1),
					productSold = (Integer) globalModel.getValueAt(rowId, 2);

				TotalSales += price*(quantity+productSold);
				String total = "₱ "+ (price*(quantity+productSold));
				globalModel.setValueAt(productSold+quantity, rowId, 2);
				globalModel.setValueAt(total, rowId, 3);
			}
			// Calculate the total sales
			String total = "₱ " + TotalSales;
			globalModel.setValueAt((Object)total, 14, 3);

			productItemManager.resetAllProduct();
			totalInput.setValue(0);
			cashInput.setValue(0);
			changeInput.setValue(0);

			JOptionPane.showMessageDialog(
				cancelButton, 
				"Added to sales inventory.", 
				"Success", 
				JOptionPane.INFORMATION_MESSAGE);

			canClickNext = false;
			ButtonConfig.disableButtonStyle(nextButton);
			nextButton.setEnabled(false);
			cashInput.setEnabled(true);
		});

		cancelButton.addActionListener(e -> {
			productItemManager.resetAllProduct();
			totalInput.setValue(0);
			cashInput.setValue(0);
			changeInput.setValue(0);

			JOptionPane.showMessageDialog(
				cancelButton, 
				"The operation has been canceled.", 
				"Canceled", 
				JOptionPane.CANCEL_OPTION);

			canClickNext = false;
			ButtonConfig.disableButtonStyle(nextButton);
			nextButton.setEnabled(false);
			cashInput.setEnabled(true);
		});

		salesButton.addActionListener(e -> {
			// print global table (testing)
			int totalRow = allProductsCount;

			for (int i = 0; i < totalRow; i++) {
				System.out.print("Name: " + globalModel.getValueAt(i, 0) + ", ");
				System.out.print("Price: " + globalModel.getValueAt(i, 1) + ", ");
				System.out.print("Quantity: " + globalModel.getValueAt(i, 2) + ", ");
				System.out.print("Subtotal: " + globalModel.getValueAt(i, 3) + ", ");
				System.out.println("");
			}
			System.out.println("\n");

			// Show GUI table
			Helpers.showSalesInventory();
		});
	}
}
