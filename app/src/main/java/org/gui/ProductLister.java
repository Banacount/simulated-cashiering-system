package org.gui;
import java.awt.Component;
import java.awt.Font;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel; 

public class ProductLister {
	JTable productsSelectedTable;
	JScrollPane scrollList;
	DefaultTableModel model, globalModel;
	Font HeaderFont;

	ProductLister (Font tableHeaderFont) {
		String[] columns = {"Name", "Price", "Quantity", "Subtotal", "id"};
		HeaderFont = tableHeaderFont;

		model = new DefaultTableModel(columns, 0);
		globalModel = new DefaultTableModel(columns, 0);
		productsSelectedTable = new JTable(model);
		scrollList = new JScrollPane(productsSelectedTable);

		// Current table column sizing
		productsSelectedTable.getColumnModel().getColumn(0).setPreferredWidth(160);
		productsSelectedTable.getColumnModel().getColumn(1).setPreferredWidth(40);
		productsSelectedTable.getColumnModel().getColumn(2).setPreferredWidth(40);
		productsSelectedTable.getColumnModel().getColumn(3).setPreferredWidth(60);
		productsSelectedTable.getTableHeader().setFont(HeaderFont);

		// Current table config
		productsSelectedTable.setDefaultEditor(Object.class, null);
		productsSelectedTable.removeColumn(productsSelectedTable.getColumn("id"));
	}

	static void showSalesInventory (DefaultTableModel globalModel) {
		JDialog tempWindow = new JDialog(Config.mainWindow, "Sales Inventory");
		JTable salesInventoryTable = new JTable(globalModel);
		JScrollPane salesInventoryPane = new JScrollPane(salesInventoryTable);
		tempWindow.setLayout(null);

		// Global table column sizing
		int columnWidth = 560, rowWidth = 300, columnOverAllWidth = 540;
		salesInventoryTable.getColumnModel().getColumn(0).setPreferredWidth((int)(columnOverAllWidth * 0.40));
		salesInventoryTable.getColumnModel().getColumn(1).setPreferredWidth((int)(columnOverAllWidth * 0.20));
		salesInventoryTable.getColumnModel().getColumn(2).setPreferredWidth((int)(columnOverAllWidth * 0.10));
		salesInventoryTable.getColumnModel().getColumn(3).setPreferredWidth((int)(columnOverAllWidth * 0.30));
		salesInventoryTable.removeColumn(salesInventoryTable.getColumn("id"));

		DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) salesInventoryTable.getTableHeader().getDefaultRenderer();
		headerRenderer.setBorder(null);
		headerRenderer.setHorizontalAlignment(JLabel.LEFT);

		// Global table config
		salesInventoryTable.getTableHeader().setFont(Config.tableHeaderFont);
		salesInventoryTable.setFont(Config.itemPriceFont);
		salesInventoryTable.setDefaultEditor(Object.class, null);
		salesInventoryTable.setShowGrid(false);
		salesInventoryTable.setTableHeader(null);

		// Blend in to the window
		salesInventoryTable.setBackground(Config.colors.item_container_bg);
		salesInventoryTable.setEnabled(false);
		salesInventoryPane.getViewport().setBackground(Config.colors.item_container_bg);
		salesInventoryPane.setBorder(BorderFactory.createLineBorder(Config.colors.item_container_bg, 1));

		salesInventoryPane.setBounds(10, 40, columnOverAllWidth, rowWidth);
		tempWindow.setSize(columnWidth, 400);
		tempWindow.getContentPane().setBackground(Config.colors.item_container_bg);
		tempWindow.setLocationRelativeTo(Config.mainWindow);
		tempWindow.add(salesInventoryPane);
		tempWindow.setResizable(false);
		tempWindow.setVisible(true);
	}
}

