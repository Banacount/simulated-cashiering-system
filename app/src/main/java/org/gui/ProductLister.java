package org.gui;

import java.awt.Font;
import javax.swing.*;
import javax.swing.table.DefaultTableModel; 

public class ProductLister {
	JTable productsSelectedTable;
	JScrollPane scrollList;
	DefaultTableModel model;

	ProductLister (Font tableHeaderFont) {
		String[] columns = {"Name", "Price", "Quantity", "Subtotal", "id"};

		model = new DefaultTableModel(columns, 0);
		productsSelectedTable = new JTable(model);
		scrollList = new JScrollPane(productsSelectedTable);

		productsSelectedTable.getColumnModel().getColumn(0).setPreferredWidth(160);
		productsSelectedTable.getColumnModel().getColumn(1).setPreferredWidth(40);
		productsSelectedTable.getColumnModel().getColumn(2).setPreferredWidth(40);
		productsSelectedTable.getColumnModel().getColumn(3).setPreferredWidth(60);
		productsSelectedTable.getTableHeader().setFont(tableHeaderFont);
		productsSelectedTable.setDefaultEditor(Object.class, null);
		productsSelectedTable.removeColumn(productsSelectedTable.getColumn("id"));
	}
}

