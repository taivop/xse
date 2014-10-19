package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

public class StockItemPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// Text field on the dialogPane
	private JTextField nameField;
	private JTextField itemPriceField;
	private JTextField quantityField;
	private JTextField idField;

	private JButton addItemToStockButton;

	// Warehouse model
	private SalesSystemModel model;

	public StockItemPanel(SalesSystemModel model) {
		this.model = model;

		setLayout(new GridBagLayout());

		add(drawAddToStockPane(), getAddToStockPaneConstraints());

		setEnabled(false);
	}

	private JComponent drawAddToStockPane() {

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));
		panel.setBorder(BorderFactory.createTitledBorder("New product"));

		// Initialize the textfields
		idField = new JTextField();
		quantityField = new JTextField();
		nameField = new JTextField();
		itemPriceField = new JTextField();

		// == Add components to the panel
		// - item id
		panel.add(new JLabel("Id:"));
		panel.add(idField);
		// - item Name
		panel.add(new JLabel("Name:"));
		panel.add(nameField);
		// Price
		panel.add(new JLabel("Price:"));
		panel.add(itemPriceField);
		// Quantity
		panel.add(new JLabel("Quantity:"));
		panel.add(quantityField);

		// Create and add the button
		addItemToStockButton = new JButton("Add to stock");
		addItemToStockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addItemToStockEventHandler();
			}
		});
		panel.add(addItemToStockButton);
		return panel;

	}

	public void setEnabled(boolean enabled) {
		this.addItemToStockButton.setEnabled(enabled);
		this.idField.setEnabled(enabled);
		this.quantityField.setEnabled(enabled);
		this.itemPriceField.setEnabled(enabled);
		this.nameField.setEnabled(enabled);
	}

	public void addItemToStockEventHandler() {
		// TODO

	}

	/*
	 * === Ideally, UI's layout and behavior should be kept as separated as
	 * possible. If you work on the behavior of the application, you don't want
	 * the layout details to get on your way all the time, and vice versa. This
	 * separation leads to cleaner, more readable and better maintainable code.
	 * 
	 * In a Swing application, the layout is also defined as Java code and this
	 * separation is more difficult to make. One thing that can still be done is
	 * moving the layout-defining code out into separate methods, leaving the
	 * more important methods unburdened of the messy layout code. This is done
	 * in the following methods.
	 */

	// UI CONSTRAINTS
	private GridBagConstraints getAddToStockPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 0d;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.NONE;

		return gc;
	}

}
