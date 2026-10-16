package org.gui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import static org.gui.Context.*;

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
		button.setForeground(Colors.DISABLED_COLOR);
		button.setBackground(Colors.ITEM_CONTAINER_BG);
		button.setBorder(BorderFactory.createLineBorder(Colors.DISABLED_COLOR, 2));
	}

	void inputButtonsStyles () 
	{
		okButton.setForeground(Colors.FG_BLUE);
		okButton.setBackground(Colors.ITEM_SELECTED_BORDER);

		nextButton.setForeground(Colors.ITEM_CONTAINER_BG);
		nextButton.setBackground(Colors.ITEM_SELECTED_BORDER);
		nextButton.setBorder(null);

		cancelButton.setForeground(Colors.FG_RED);
		cancelButton.setBackground(Colors.ITEM_SELECTED_BORDER);

		salesButton.setForeground(Colors.ITEM_CONTAINER_BG);
		salesButton.setBackground(Colors.ITEM_SELECTED_BORDER);
	}

	void inputButtonsFunctions () 
	{
		ButtonConfig.disableButtonStyle(nextButton);
		nextButton.setEnabled(false);

		okButton.addActionListener(e -> SalesManager.onOk());
		nextButton.addActionListener(e -> SalesManager.onNext());
		cancelButton.addActionListener(e -> SalesManager.onCancel());
		salesButton.addActionListener(e -> Helpers.showSalesInventory());
	}
}
