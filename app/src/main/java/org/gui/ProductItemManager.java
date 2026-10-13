package org.gui;

import java.awt.Font;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

import org.gui.*;

public class ProductItemManager {
	private ProductItem[] products = new ProductItem[12];
	DefaultTableModel Model, GlobalModel;

	ProductItemManager (Font nameFont, Font priceFont, Font quantityFont, DefaultTableModel model, DefaultTableModel globalModel, JFormattedTextField totalIn) 
	{
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

			String total = "₱ "+ (products[e].price*products[e].quantity);
			globalModel.addRow(new Object[]{
				products[e].productName,
				products[e].price,
				products[e].quantity,
				total,
				e
			});
		}

		Model = model;
		GlobalModel = globalModel;
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
