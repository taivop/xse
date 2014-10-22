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
		panel.add(new JLabel("Name:"));
		panel.add(nameField);
		// Price
		panel.add(new JLabel("Price:"));
		panel.add(itemPriceField);
		// Quantity
		panel.add(new JLabel("Quantity:"));
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

	public void addItemToStockEventHandler() {
		// TODO: all fields are required? Or description optional? If id is not
		// filled should it be generated automatically?
		// If increasing stock, should price, name match?
		try {
			long id;
			String name;
			String description;
			double price;
			int quantity;
			// id is inserted
			if (!idField.getText().equals("")) {
				id = Long.parseLong(idField.getText());
				// too big-->new product
				if (!(id <= model.getWarehouseTableModel().getRowCount()))
					id = model.getWarehouseTableModel().getRowCount() + 1;
			} else
				// id not inserted
				id = model.getWarehouseTableModel().getRowCount() + 1;
			System.out.println(id);
			name = nameField.getText();
			description = descriptionField.getText();
			price = Double.parseDouble(itemPriceField.getText());
			quantity = Integer.parseInt(quantityField.getText());
			StockItem stockItem = new StockItem(id, name, description, price,
					quantity);
			model.getWarehouseTableModel().addItem(stockItem);
		} catch (NumberFormatException ex) {
			log.error(ex.getMessage());

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
