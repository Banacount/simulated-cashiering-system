package org.gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.gui.Config.colors; 

public class ProductItem {
	JPanel item;
	JFormattedTextField totalIn;
	int quantity = 0, price, id;
	Boolean isDisabled = false;
	DefaultTableModel model;
	JLabel nameLabel, priceLabel, quantityLabel, imageLabel;
	Image originalImage, grayImage;

	ProductItem (String name, int productPrice, String imagePath, Font nameFont, Font priceFont, Font quantityFont) {
		int width = 163 + 4, height = 194 + 4, imgPadding = 20;
		price = productPrice;

		item = new JPanel();
		JLabel productImage = MyUtils.imageLabel(
			imgPadding/2, 
			imgPadding/2, 
			width-imgPadding, 
			width-imgPadding, 
			imagePath
		);
		originalImage = MyUtils.tempOrigImage;
		grayImage = MyUtils.tempGrayImage;

		JLabel productNameLbl = new JLabel(name, SwingConstants.CENTER);
		JLabel productPriceLbl = new JLabel("₱ " + price, SwingConstants.CENTER);
		JLabel productQuantityLbl = new JLabel("0", SwingConstants.LEFT);

		int offset = width;

		productNameLbl.setFont(nameFont);
		productNameLbl.setBounds(imgPadding/2, offset-6, width-imgPadding, 16);
		productNameLbl.setForeground(Config.colors.fg_color);

		productPriceLbl.setFont(priceFont);
		productPriceLbl.setBounds(0, offset+6, width, 22);
		productPriceLbl.setForeground(Config.colors.fg_color);

		productQuantityLbl.setFont(quantityFont);
		productQuantityLbl.setBounds(10, 10, width-50, 40);
		productQuantityLbl.setForeground(Config.colors.item_selected_border);

		item.setLayout(null);
		item.add(productQuantityLbl);
		item.add(productImage);
		item.add(productNameLbl);
		item.add(productPriceLbl);

		item.setBackground(Config.colors.item_container_bg);
		item.setBorder(BorderFactory.createLineBorder(Config.colors.item_border, 1));
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
				if (e.getButton() == 1) quantity++;

				// Enter quantity manually
				if (e.getButton() == 2) {
					String userInput = JOptionPane.showInputDialog("Enter item quantity:");

					try {
						int quantityInput = Integer.parseInt(userInput);
						quantity += quantityInput;
					} catch (NumberFormatException err) {
						JOptionPane.showMessageDialog(item, "Quantity input is invalid.", "Input Error", JOptionPane.ERROR_MESSAGE);
					}
				}

				// Indicator for quantity
				if (quantity > 0) {
					item.setBorder(BorderFactory.createLineBorder(Config.colors.item_selected_border, 2));
					productQuantityLbl.setVisible(true);
					productQuantityLbl.setText(String.valueOf(quantity));
				} else {
					item.setBorder(BorderFactory.createLineBorder(Config.colors.item_border, 1));
					productQuantityLbl.setVisible(false);
				}

				System.out.println(name + ": " + quantity);

				// Update the product lister
				int totalRows = model.getRowCount();
				int itemRow = -1;

				for (int i = 0; i < totalRows; i++) {
					int row_id = (Integer) model.getValueAt(i, 4);

					if (row_id == id) itemRow = i;
				}

				if (itemRow == -1 && quantity > 0) {
					String total = "₱ "+ (price*quantity);
					model.addRow(new Object[]{name, price, quantity, total, id});
				} else if (itemRow > -1) {
					String total = "₱ "+ (price*quantity);
					model.setValueAt(quantity, itemRow, 2);
					model.setValueAt(total, itemRow, 3);
				}

				if (itemRow != -1 && quantity <= 0) model.removeRow(itemRow);

				// Calculate total
				int total = 0;
				totalRows = model.getRowCount();
				for (int t = 0; t < totalRows; t++) {
					int price = (Integer) model.getValueAt(t, 1);
					int quantity = (Integer) model.getValueAt(t, 2);
					total += price*quantity;
				}
				totalIn.setValue(total);
			}
		});

		nameLabel = productNameLbl;
		priceLabel = productPriceLbl;
		quantityLabel = productQuantityLbl;
		imageLabel = productImage;
	}
}

class ProductItemManager {
	private ProductItem[] products = new ProductItem[12];

	ProductItemManager (Font nameFont, Font priceFont, Font quantityFont, DefaultTableModel model, JFormattedTextField totalIn) {
		products[0]  = new ProductItem("1-pc. Chicken McDo", 104, "/products/1pc_chicken_mcdo.png", nameFont, priceFont, quantityFont);
		products[1]  = new ProductItem("Crispy Chicken Fillet Ala King", 99, "/products/crispy_chicken_fillet_ala_king.png", nameFont, priceFont, quantityFont);
		products[2]  = new ProductItem("McShaker Fries Medium Galbi", 115, "/products/mcshaker_fries_medium_galbi.png", nameFont, priceFont, quantityFont);
		products[3]  = new ProductItem("6-pc. Chicken McShare Box", 525, "/products/6pc_chicken_mcshare_box.png", nameFont, priceFont, quantityFont);
		products[4]  = new ProductItem("test", 500, "/test_product.png", nameFont, priceFont, quantityFont);
		products[5]  = new ProductItem("test", 500, "/test_product.png", nameFont, priceFont, quantityFont);
		products[6]  = new ProductItem("test", 500, "/test_product.png", nameFont, priceFont, quantityFont);
		products[7]  = new ProductItem("test", 500, "/test_product.png", nameFont, priceFont, quantityFont);
		products[8]  = new ProductItem("test", 500, "/test_product.png", nameFont, priceFont, quantityFont);
		products[9]  = new ProductItem("test", 500, "/test_product.png", nameFont, priceFont, quantityFont);
		products[10] = new ProductItem("test", 500, "/test_product.png", nameFont, priceFont, quantityFont);
		products[11] = new ProductItem("test", 500, "/test_product.png", nameFont, priceFont, quantityFont);

		for (int e = 0; e < products.length; e++) {
			products[e].id = e;
			products[e].model = model;
			products[e].totalIn = totalIn;
		}
	}

	void render (JPanel container) {
		for (int i = 0; i < products.length; i++) {
			container.add(products[i].item);
		}
	}

	void disableAllProduct () {
		for (int i = 0; i < products.length; i++) {
			ProductItem product = products[i];

			product.isDisabled = true;
			product.nameLabel.setForeground(Config.colors.disabled_color);
			product.priceLabel.setForeground(Config.colors.disabled_color);
			product.quantityLabel.setForeground(Config.colors.disabled_color);
			product.item.setBorder(BorderFactory.createLineBorder(Config.colors.disabled_color, 2));
			// Change image
			ImageIcon loadImg = new ImageIcon(product.grayImage);
			product.imageLabel.setIcon(loadImg);
		}
	}

	void enableAllProduct () {
		for (int i = 0; i < products.length; i++) {
			ProductItem product = products[i];

			product.isDisabled = false;
			product.nameLabel.setForeground(Config.colors.fg_color);
			product.priceLabel.setForeground(Config.colors.fg_color);
			product.quantityLabel.setForeground(Config.colors.item_selected_border);
			product.item.setBorder(BorderFactory.createLineBorder(Config.colors.item_border, 1));

			// Change image
			ImageIcon loadImg = new ImageIcon(product.originalImage);
			product.imageLabel.setIcon(loadImg);
		}
	}

	void resetAllProduct () {
		enableAllProduct();

		for (int i = 0; i < products.length; i++) {
			ProductItem product = products[i];
			product.quantity = 0;
			product.model.setRowCount(0);
			product.quantityLabel.setVisible(false);
		}
	}

	ProductItem getProduct (int productIndex) {
		return products[productIndex];
	}
}
