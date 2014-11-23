package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

/**
 * Purchase pane + shopping cart table UI.
 */
public class PurchaseItemPanel extends JPanel {

	private static final Logger log = Logger.getLogger(PurchaseItemPanel.class);

	private static final long serialVersionUID = 1L;

	// Text field on the dialogPane
	private JTextField barCodeField;
	private JTextField quantityField;
	private JTextField priceField;

	private JButton addItemButton;

	// Dropdown list for Menu Items
	private JComboBox dropdownMenu;

	// Warehouse model
	private static SalesSystemModel model;

	public static SalesSystemModel getModel() {
		return model;
	}

	/**
	 * Constructs new purchase item panel.
	 * 
	 * @param model
	 *            composite model of the warehouse and the shopping cart.
	 */

	public PurchaseItemPanel(SalesSystemModel model) {
		this.model = model;

		setLayout(new GridBagLayout());

		add(drawDialogPane(), getDialogPaneConstraints());
		add(drawBasketPane(), getBasketPaneConstraints());

		setEnabled(false);
	}

	// ADDED

	public JComboBox getDropdownMenu() {
		return dropdownMenu;
	}

	// Creates model for JComboBox
	public DefaultComboBoxModel getdropdownModel() {

		Object value;
		DefaultComboBoxModel productsModel = new DefaultComboBoxModel();

		for (int i = 0; i < model.getWarehouseTableModel().getRowCount(); i++) {
			value = model.getWarehouseTableModel().getValueAt(i, 1);
			productsModel.addElement(value);

		}

		return productsModel;
	}

	private JComboBox drawDropdownMenu() {

		dropdownMenu = new JComboBox(getdropdownModel());
		dropdownMenu.setSelectedIndex(-1);
		dropdownMenu.setEnabled(false);

		/* Choose from dropdown menu */
		dropdownMenu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				JComboBox<String> combo = (JComboBox<String>) event.getSource();
				String selectedItem = (String) combo.getSelectedItem();

				// Finds bar code end fills the dialog fields
				if (dropdownMenu.getSelectedIndex() != -1) {
					StockTableModel productsTable = model
							.getWarehouseTableModel();
					for (int i = 0; i < productsTable.getRowCount(); i++) {

						if (selectedItem.equals((String) productsTable
								.getValueAt(i, 1))) {
							String barCode = productsTable.getValueAt(i, 0)
									.toString();
							barCodeField.setText(barCode);
							barCodeField.setEnabled(false);
							fillDialogFields();
						}
					}
				}
			}
		});

		return dropdownMenu;
	}

	// shopping cart pane
	private JComponent drawBasketPane() {

		// Create the basketPane
		JPanel basketPane = new JPanel();
		basketPane.setLayout(new GridBagLayout());
		basketPane.setBorder(BorderFactory.createTitledBorder("Shopping cart"));

		// Create the table, put it inside a scollPane,
		// and add the scrollPane to the basketPanel.
		JTable table = new JTable(model.getCurrentPurchaseTableModel());
		JScrollPane scrollPane = new JScrollPane(table);

		basketPane.add(scrollPane, getBacketScrollPaneConstraints());

		return basketPane;
	}

	// purchase dialog
	private JComponent drawDialogPane() {

		// Create the panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));
		panel.setBorder(BorderFactory.createTitledBorder("Product"));

		// Initialize the textfields
		barCodeField = new JTextField();
		quantityField = new JTextField("1");
		priceField = new JTextField();
		dropdownMenu = drawDropdownMenu();

		// Fill the dialog fields if the bar code text field loses focus
		barCodeField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
				fillDialogFields();
			}
		});

		priceField.setEditable(false);
		barCodeField.setEditable(false);

		// == Add components to the panel
		panel.add(new JLabel("Menu item:"));
		panel.add(dropdownMenu);

		// - bar code
		panel.add(new JLabel("Bar code:"));
		panel.add(barCodeField);

		// - amount
		panel.add(new JLabel("Amount:"));
		panel.add(quantityField);

		// - price
		panel.add(new JLabel("Price:"));
		panel.add(priceField);

		// Create and add the button
		addItemButton = new JButton("Add to cart");
		addItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addItemEventHandler();
			}
		});

		panel.add(addItemButton);

		return panel;
	}

	// Fill dialog with data from the "database".
	public void fillDialogFields() {
		StockItem stockItem = getStockItemByBarcode();

		if (stockItem != null) {
			String priceString = String.valueOf(stockItem.getPrice());
			priceField.setText(priceString);
		} else {
			reset();
		}
	}

	// Search the warehouse for a StockItem with the bar code entered
	// to the barCode textfield.
	private StockItem getStockItemByBarcode() {
		try {
			int code = Integer.parseInt(barCodeField.getText());
			return model.getWarehouseTableModel().getItemById(code);
		} catch (NumberFormatException ex) {
			return null;
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	
	public boolean hasEnoughInStock(StockItem itemInStock, int currentAddedQuantityOfItem, int quantity){
		boolean hasEnoughInStock=true;
		
		List<SoldItem> currentItems = model
				.getCurrentPurchaseTableModel().getTableRows();
		for (SoldItem item : currentItems) {
			if (item.getStockItemId() == itemInStock.getId())
				currentAddedQuantityOfItem += item.getQuantity();
		}

		// Calculate the quantity of items available
		int availableQuantity = itemInStock.getQuantity()
				- currentAddedQuantityOfItem;
		
		if (availableQuantity<=0 || quantity >availableQuantity){
			hasEnoughInStock=false;
		}
		return hasEnoughInStock;
			
		
	}
	/**
	 * Add new item to the cart.
	 */
	public void addItemEventHandler() {
		// add chosen item to the shopping cart.
		StockItem stockItem = getStockItemByBarcode();
		if (stockItem != null) {
			int quantity; //needed quantity
			try {
				quantity = Integer.parseInt(quantityField.getText());
				// } catch (NumberFormatException ex) {
				// quantity = 1;
				// }
				if (quantity > 0) {
					// Calculate, how many of this item we currently have in our
					// order
					int currentAddedQuantityOfItem = 0;
					/*
					List<SoldItem> currentItems = model
							.getCurrentPurchaseTableModel().getTableRows();
					for (SoldItem item : currentItems) {
						if (item.getStockItemId() == stockItem.getId())
							currentAddedQuantityOfItem += item.getQuantity();
					}

					// Calculate the quantity of items available
					int availableQuantity = stockItem.getQuantity()
							- currentAddedQuantityOfItem;
					if (quantity > availableQuantity) {
						// We do not have enough of this item in stock
*/					if(!hasEnoughInStock(stockItem, currentAddedQuantityOfItem, quantity)){
						String notEnoughInStockMessage = String
								.format("The stock of %s is too low: currently %d in stock and %d in your order.",
										stockItem.getName(),
										stockItem.getQuantity(),
										currentAddedQuantityOfItem);

						// Open pop-up window with warning
						JOptionPane.showMessageDialog(this.getParent(),
								notEnoughInStockMessage,
								"Warning: stock too low",
								JOptionPane.WARNING_MESSAGE);
						System.out.println(notEnoughInStockMessage);

					} else {
						// We have enough of this item

						model.getCurrentPurchaseTableModel().addItem(
								new SoldItem(stockItem, quantity));
					}
				} else {
					String amountMessage = String
							.format("You inserted invalid amount");
					JOptionPane.showMessageDialog(this.getParent(),
							amountMessage, "Warning",
							JOptionPane.WARNING_MESSAGE);
					quantityField.setText("1");
					log.info("Inserted invalid data");

				}
			} catch (NumberFormatException ex) {
				String amountMessage = String
						.format("You inserted invalid amount");
				JOptionPane.showMessageDialog(this.getParent(), amountMessage,
						"Warning", JOptionPane.WARNING_MESSAGE);
				quantityField.setText("1");
				log.info("Inserted invalid data");

			}
		}
	}

	/**
	 * Sets whether or not this component is enabled.
	 */
	@Override
	public void setEnabled(boolean enabled) {
		this.addItemButton.setEnabled(enabled);
		this.barCodeField.setEnabled(enabled);
		this.quantityField.setEnabled(enabled);

	}

	/**
	 * Reset dialog fields.
	 */
	public void reset() {
		barCodeField.setText("");
		quantityField.setText("1");
		priceField.setText("");
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

	// Formatting constraints for the dialogPane
	private GridBagConstraints getDialogPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 0d;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.NONE;

		return gc;
	}

	// Formatting constraints for the basketPane
	private GridBagConstraints getBasketPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 1.0;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.BOTH;

		return gc;
	}

	private GridBagConstraints getBacketScrollPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		return gc;
	}

	// Dropdown Menu constraints
	private GridBagConstraints getConstrainstForDropdownMenu() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.weightx = 1;
		gc.weighty = 0.0d;
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridwidth = GridBagConstraints.RELATIVE;

		return gc;
	}

}
