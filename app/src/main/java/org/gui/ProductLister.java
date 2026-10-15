package org.gui;
import java.awt.Font;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel; 

import static org.gui.Context.*; 
import static org.gui.Context.Fonts.*; 

public class ProductLister {
	JTable productsSelectedTable;
	JScrollPane scrollList;
	JDialog salesInventoryWindow;
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
		salesInventoryWindow = createSalesInventoryWindow();
	}

	private JDialog createSalesInventoryWindow () {
		JDialog window = new JDialog(MainWindow, "Sales Inventory");
		JTable salesInventoryTable = new JTable(globalModel);
		JScrollPane salesInventoryPane = new JScrollPane(salesInventoryTable);
		JLabel inventoryLabel = new JLabel("McDonald's Sales Inventory");
		window.setLayout(null);

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
		inventoryLabel.setFont(itemQuantityFont); 
		inventoryLabel.setForeground(Colors.ITEM_SELECTED_BORDER);

		// Global table config
		salesInventoryTable.getTableHeader().setFont(tableHeaderFont);
		salesInventoryTable.setFont(itemPriceFont);
		salesInventoryTable.setDefaultEditor(Object.class, null);
		salesInventoryTable.setShowGrid(false);
		salesInventoryTable.setTableHeader(null);

		// Blend in to the window
		salesInventoryTable.setBackground(Colors.ITEM_CONTAINER_BG);
		salesInventoryTable.setEnabled(false);
		salesInventoryPane.getViewport().setBackground(Colors.ITEM_CONTAINER_BG);
		salesInventoryPane.setBorder(BorderFactory.createLineBorder(Colors.ITEM_CONTAINER_BG, 1));

		// Sizing and display to the screen
		salesInventoryPane.setBounds(10, 70, columnOverAllWidth, rowWidth);
		inventoryLabel.setBounds(10, 10, columnWidth, 40);
		window.setSize(columnWidth, 360);
		window.getContentPane().setBackground(Colors.ITEM_CONTAINER_BG);
		window.setLocationRelativeTo(MainWindow);
		window.add(inventoryLabel);
		window.add(salesInventoryPane);
		window.setResizable(false);
		window.setVisible(false);

		return window;
	}
}

