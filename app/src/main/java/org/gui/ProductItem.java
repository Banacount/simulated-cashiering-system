package org.gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*; 

public class ProductItem {
	JPanel item;
	int quantity = 0, price;

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

		// Item events
		productQuantityLbl.setVisible(false);
		item.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 3 && quantity > 0) quantity--;
				if (e.getButton() == 1) quantity++;

				if (quantity > 0) {
					item.setBorder(BorderFactory.createLineBorder(Config.colors.item_selected_border, 2));
					productQuantityLbl.setVisible(true);
					productQuantityLbl.setText(String.valueOf(quantity));
				} else {
					item.setBorder(BorderFactory.createLineBorder(Config.colors.item_border, 1));
					productQuantityLbl.setVisible(false);
				}

				System.out.println(name + ": " + quantity);
			}
		});
	}

	public JPanel getJPanel () {
		return item;
	}
}

