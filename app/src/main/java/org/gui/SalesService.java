package org.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static org.gui.Context.*;

public class SalesService {
	private ButtonConfig buttonConfig;
	DefaultTableModel model = ProductManager.productLister.model;
	DefaultTableModel globalModel = ProductManager.productLister.globalModel;

	SalesService (ButtonConfig button_config) {
		buttonConfig = button_config;
	}

	void resetSalesState () {
		ProductManager.resetAllProduct();
		TotalInput.setValue(0);
		CashInput.setValue(0);
		ChangeInput.setValue(0);

		CanClickNextButton = false;
		ButtonConfig.disableButtonStyle(buttonConfig.nextButton);
		buttonConfig.nextButton.setEnabled(false);
		CashInput.setEnabled(true);
	}

	void onOk () 
	{
		Number cashNum = (Number) CashInput.getValue();
		Number totalNum = (Number) TotalInput.getValue();
		double cashValue = cashNum.doubleValue();
		double totalValue = totalNum.doubleValue();

		if (model.getRowCount() <= 0) {
			JOptionPane.showMessageDialog(TotalInput,  
				"No Items.", 
				"Warning", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (cashValue <= 0) {
			JOptionPane.showMessageDialog(TotalInput,  
				"Please input cash.", 
				"Warning", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (cashValue < totalValue) {
			JOptionPane.showMessageDialog(TotalInput,  
				"Insufficient amount.", 
				"Warning", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		ProductManager.disableAllProduct();
		ChangeInput.setValue(cashValue - totalValue);
		CashInput.setEnabled(false);
		CanClickNextButton = true;
		buttonConfig.inputButtonsStyles();
		buttonConfig.nextButton.setEnabled(true);
	}

	void onNext () 
	{
		if (!CanClickNextButton) return;

		// Add the globalTable
		int totalRow = model.getRowCount();

		// Update the global table
		for (int t = 0; t < totalRow; t++) {
			int rowId = (Integer) model.getValueAt(t, 4),
				quantity = (Integer) model.getValueAt(t, 2),
				price = (Integer) globalModel.getValueAt(rowId, 1),
				productSold = (Integer) globalModel.getValueAt(rowId, 2);

			TotalSales += price*(quantity+productSold);
			String total = CURRENCY + (price*(quantity+productSold));
			globalModel.setValueAt(productSold+quantity, rowId, 2);
			globalModel.setValueAt(total, rowId, 3);
		}

		// Calculate the total sales of the global table
		TotalSales = 0;
		for (int i = 0; i < AllProductsCount; ++i) {
			int getQuantity = (Integer) globalModel.getValueAt(i, 2),
				getPrice = (Integer) globalModel.getValueAt(i, 1);
			TotalSales += getQuantity * getPrice;
		}
		
		String total = CURRENCY + TotalSales;
		globalModel.setValueAt((Object)total, 14, 3);

		JOptionPane.showMessageDialog(
			buttonConfig.cancelButton, 
			"Added to sales inventory.", 
			"Success", 
			JOptionPane.INFORMATION_MESSAGE);

		resetSalesState();
	}

	void onCancel () 
	{
		JOptionPane.showMessageDialog(
			buttonConfig.cancelButton, 
			"The operation has been canceled.", 
			"Canceled", 
			JOptionPane.CANCEL_OPTION);

		resetSalesState();
	}

	void updateTableModel (int id, int price, int quantity, String name) {
		// Update the table model (active product list)
		int totalRows = model.getRowCount();
		int itemRow = -1; // if this remains as -1 then the product doesn't exist in the model table

		for (int i = 0; i < totalRows; i++) {
			int row_id = (Integer) model.getValueAt(i, 4);
			if (row_id == id) itemRow = i;
		}

		// check and add
		if (itemRow == -1 && quantity > 0) {
			String total = CURRENCY + (price*quantity);
			model.addRow(new Object[]{name, price, quantity, total, id});
		}

		// check and set
		if (itemRow > -1) {
			String total = CURRENCY + (price*quantity);
			model.setValueAt(quantity, itemRow, 2);
			model.setValueAt(total, itemRow, 3);
		}

		// check and remove
		if (itemRow != -1 && quantity <= 0) model.removeRow(itemRow);

		// Calculate total value and set to total input
		int total = 0;
		totalRows = model.getRowCount();
		for (int t = 0; t < totalRows; t++) {
			int row_price = (Integer) model.getValueAt(t, 1);
			int row_quantity = (Integer) model.getValueAt(t, 2);
			total += row_price*row_quantity;
		}

		TotalInput.setValue(total);
	}
}
