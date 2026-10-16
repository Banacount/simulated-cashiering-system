package org.gui;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

import static org.gui.Context.*;

public class ProductItemManager {
	private ProductItem[] products = new ProductItem[12];
	ProductLister productLister;

	ProductItemManager (ProductLister product_lister) 
	{
		products[0]  = new ProductItem("1-pc. Chicken McDo", 104, "/products/1pc_chicken_mcdo.png");
		products[1]  = new ProductItem("Crispy Chicken Fillet Ala King", 99, "/products/crispy_chicken_fillet_ala_king.png");
		products[2]  = new ProductItem("McShaker Fries Medium Galbi", 115, "/products/mcshaker_fries_medium_galbi.png");
		products[3]  = new ProductItem("6-pc. Chicken McShare Box", 525, "/products/6pc_chicken_mcshare_box.png");
		products[4]  = new ProductItem("2-pc. Hotcakes Solo", 99, "/products/2pc_hotcakes_solo.png");
		products[5]  = new ProductItem("Sausage McMuffin w/ Hash Browns Meal", 163, "/products/sausage_mcmuffin_w_hash_browns_meal.png");
		products[6]  = new ProductItem("Cheesy Eggdesal w/ Sausage Solo", 115, "/products/cheesy_eggdesal_w_sausage_solo.png");
		products[7]  = new ProductItem("Cheesy Eggdesal Solo", 85, "/products/cheesy_eggdesal_solo.png");
		products[8]  = new ProductItem("Sausage McMuffin w/ Egg Solo", 123, "/products/sausage_mcmuffin_w_egg_solo.png");
		products[9]  = new ProductItem("Sausage McMuffin Solo", 106, "/products/sausage_mcmuffin_solo.png");
		products[10] = new ProductItem("Double Egg-Oh! Melt", 99, "/products/double_egg_oh_melt.png");
		products[11] = new ProductItem("Big Breakfast Meal", 203, "/products/big_breakfast_meal.png");

		productLister = product_lister;
		DefaultTableModel globalModel = productLister.globalModel;
		DefaultTableModel model = productLister.model;

		for (int e = 0; e < products.length; e++) {
			products[e].id = e;
			products[e].model = model;

			String total = CURRENCY + (products[e].price*products[e].quantity);
			productLister.globalModel.addRow(new Object[]{
				products[e].productName,
				products[e].price,
				products[e].quantity,
				total,
				e
			});
		}
		globalModel.addRow((Object[]) null);
		globalModel.addRow((Object[]) null);
		globalModel.addRow(new Object[]{
			"TOTAL SALES", "", "", "₱ 0", -1,
		});
		AllProductsCount = products.length;
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
			product.nameLabel.setForeground(Colors.DISABLED_COLOR);
			product.priceLabel.setForeground(Colors.DISABLED_COLOR);
			product.quantityLabel.setForeground(Colors.DISABLED_COLOR);
			product.item.setBorder(BorderFactory.createLineBorder(Colors.DISABLED_COLOR, 2));

			// Change image
			ImageIcon loadImg = new ImageIcon(product.grayImage);
			product.imageLabel.setIcon(loadImg);
		}
	}

	void enableAllProduct () {
		for (int i = 0; i < products.length; i++) {
			ProductItem product = products[i];

			product.isDisabled = false;
			product.nameLabel.setForeground(Colors.FG_COLOR);
			product.priceLabel.setForeground(Colors.FG_COLOR);
			product.quantityLabel.setForeground(Colors.ITEM_SELECTED_BORDER);
			product.item.setBorder(BorderFactory.createLineBorder(Colors.ITEM_BORDER, 1));

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
