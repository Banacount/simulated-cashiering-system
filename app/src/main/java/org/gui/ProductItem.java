package org.gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static org.gui.Context.*;
import static org.gui.Context.Fonts.*;

public class ProductItem {
	JPanel item;
	Boolean isDisabled = false;
	DefaultTableModel model;
	JLabel nameLabel, priceLabel, quantityLabel, imageLabel;
	Image originalImage, grayImage;

	int quantity = 0, price, id;
	String productName;

	ProductItem (String name, int productPrice, String imagePath) {
		int width = 163 + 4, height = 194 + 4, imgPadding = 20;
		price = productPrice;
		productName = name;

		item = new JPanel();
		JLabel productImage = Helpers.imageLabel(
			imgPadding/2, 
			imgPadding/2, 
			width-imgPadding, 
			width-imgPadding, 
			imagePath
		);
		originalImage = Helpers.tempOrigImage;
		grayImage = Helpers.tempGrayImage;

		JLabel productNameLbl = new JLabel(name, SwingConstants.CENTER);
		JLabel productPriceLbl = new JLabel(CURRENCY + price, SwingConstants.CENTER);
		JLabel productQuantityLbl = new JLabel("0", SwingConstants.LEFT);

		int offset = width;

		productNameLbl.setFont(itemNameFont);
		productNameLbl.setBounds(imgPadding/2, offset-6, width-imgPadding, 16);
		productNameLbl.setForeground(Colors.FG_COLOR);

		productPriceLbl.setFont(itemPriceFont);
		productPriceLbl.setBounds(0, offset+6, width, 22);
		productPriceLbl.setForeground(Colors.FG_COLOR);

		productQuantityLbl.setFont(itemQuantityFont);
		productQuantityLbl.setBounds(10, 10, width-50, 40);
		productQuantityLbl.setForeground(Colors.ITEM_SELECTED_BORDER);

		item.setLayout(null);
		item.add(productQuantityLbl); item.add(productImage); item.add(productNameLbl);
		item.add(productPriceLbl);

		item.setBackground(Colors.ITEM_CONTAINER_BG);
		item.setBorder(BorderFactory.createLineBorder(Colors.ITEM_BORDER, 1));
		item.setSize(width, height);
		item.setPreferredSize(new Dimension(width, height));
		item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// Item events/actions/interaction
		productQuantityLbl.setVisible(false);
		item.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isDisabled) return;
				if (e.getButton() == 3 && quantity > 0) quantity--;
				if (e.getButton() == 2) quantity++;

				// Enter quantity manually
				if (e.getButton() == 1) {
					String userInput = JOptionPane.showInputDialog("Enter item quantity:");
					if (userInput == null) return;
					if (userInput.equals("")) return;

					try {
						int quantityInput = Integer.parseInt(userInput);
						quantity = (quantityInput >= 0 ? quantityInput : 0);
					} catch (NumberFormatException err) {
						JOptionPane.showMessageDialog(item, "Quantity input is invalid.", "Input Error", JOptionPane.ERROR_MESSAGE);
					}
				}

				// Indicator for quantity
				if (quantity > 0) {
					item.setBorder(BorderFactory.createLineBorder(Colors.ITEM_SELECTED_BORDER, 2));
					productQuantityLbl.setVisible(true);
					productQuantityLbl.setText(String.valueOf(quantity));
				} else {
					item.setBorder(BorderFactory.createLineBorder(Colors.ITEM_BORDER, 1));
					productQuantityLbl.setVisible(false);
				}

				SalesManager.updateTableModel(id, price, quantity, name);
			}
		});

		nameLabel = productNameLbl;
		priceLabel = productPriceLbl;
		quantityLabel = productQuantityLbl;
		imageLabel = productImage;
	}
}

