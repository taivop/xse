package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

public class StockItemPanel extends JPanel {
	private static final Logger log = Logger.getLogger(StockItemPanel.class);

	private static final long serialVersionUID = 1L;

	// Text field on the dialogPane
	private JTextField nameField;
	private JTextField itemPriceField;
	private JTextField quantityField;
	private JTextField idField;
	private JTextField descriptionField;

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
		panel.setLayout(new GridLayout(6, 2));
		panel.setBorder(BorderFactory.createTitledBorder("New product"));

		// Initialize the textfields
		idField = new JTextField();
		quantityField = new JTextField();
		nameField = new JTextField();
		itemPriceField = new JTextField();
		descriptionField = new JTextField();

		// == Add components to the panel
		// - item id
		panel.add(new JLabel("Id:"));
		panel.add(idField);
		// - item Name
		panel.add(new JLabel("Name:*"));
		panel.add(nameField);
		// Price
		panel.add(new JLabel("Price:*"));
		panel.add(itemPriceField);
		// Quantity
		panel.add(new JLabel("Quantity:*"));
		panel.add(quantityField);
		// Description
		panel.add(new JLabel("Description:"));
		panel.add(descriptionField);

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
		this.descriptionField.setEnabled(enabled);
	}
	public void clear() {
		this.idField.setText("");
		this.itemPriceField.setText("");
		this.quantityField.setText("");
		this.nameField.setText("");
		this.descriptionField.setText("");
	}
	
	public void addItemToStockEventHandler() {
		String incorrectDataStockMessage;
		try {
			long id;
			List<Long> allIds = new ArrayList<Long>();
			String name;
			String description;
			double price;
			int quantity;
			String priceString = itemPriceField.getText().replace(',', '.');
			// name, price(>= 0), quantity (>=0, integer) must be filled
			if (!nameField.getText().equals("")
					&& Double.parseDouble(priceString) >= 0
					&& Integer.parseInt(quantityField.getText()) >= 0) {
				// All current id-s to a list
				List<StockItem> allProducts = model.getWarehouseTableModel()
						.getTableRows();
				for (StockItem item : allProducts) {
					allIds.add(item.getId());
				}

				// id is inserted and already exists-->Warning
				if (!idField.getText().equals("")
						&& allIds.contains(Long.parseLong(idField.getText()))) {
					String existsInStockMessage = String
							.format("Item with id %s already exists",
									idField.getText());
					JOptionPane.showMessageDialog(this.getParent(),
							existsInStockMessage, "Warning",
							JOptionPane.WARNING_MESSAGE);
					log.error(existsInStockMessage);
				} else {
					// Id is not inserted
					if (idField.getText().equals(""))
						id = Collections.max(allIds) + 1;
					// Id is inserted
					else
						id = Long.parseLong(idField.getText());
					name = nameField.getText();
					description = descriptionField.getText();
					// Price: two decimal places
					price = Math.round(Double.parseDouble(priceString) * 100.0) / 100.0;
					quantity = Integer.parseInt(quantityField.getText());
					StockItem stockItem = new StockItem(id, name, description,
							price, quantity);
					model.getWarehouseTableModel().addItem(stockItem);
					//message
					String addedToStockMessage = String
							.format("Added item with id %s", id);
					JOptionPane.showMessageDialog(this.getParent(),
							addedToStockMessage, "Information",
							JOptionPane.INFORMATION_MESSAGE);
					log.info(addedToStockMessage);
					//clearing fields
					clear();
					setEnabled(false);
					
					
				}
			} else {
				// Open pop-up window with warning
				incorrectDataStockMessage = String
						.format("You inserted incorrect data. Price and quantity must be nonnegative");
				JOptionPane.showMessageDialog(this.getParent(),
						incorrectDataStockMessage, "Warning",
						JOptionPane.WARNING_MESSAGE);
				log.error(incorrectDataStockMessage);

			}

		} catch (NumberFormatException ex) {
			incorrectDataStockMessage = String
					.format("You inserted incorrect data. Id must be integer and fields with * are required");
			// Open pop-up window with warning
			JOptionPane.showMessageDialog(this.getParent(),
					incorrectDataStockMessage, "Warning",
					JOptionPane.WARNING_MESSAGE);
			log.error(incorrectDataStockMessage);

		}
	}

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
