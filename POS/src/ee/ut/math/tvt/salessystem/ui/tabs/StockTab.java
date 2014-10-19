package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.panels.StockItemPanel;

public class StockTab {
	private static final Logger log = Logger.getLogger(StockTab.class);

	private JButton addItem;

	private StockItemPanel stockItemPane;

	private SalesSystemModel model;

	public StockTab(SalesSystemModel model) {
		this.model = model;

	}

	// warehouse stock tab - consists of a menu, table and stockItemPanel
	public Component draw() {
		JPanel panel = new JPanel();
		// Layout
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setLayout(new GridBagLayout());

		panel.add(drawStockMenuPane(), getMenuPaneConstraints());
		panel.add(drawStockMainPane(), getMainPaneConstraints());

		// Add item pane
		stockItemPane = new StockItemPanel(model);
		panel.add(stockItemPane, getConstraintsForStockItemkPanel());
		return panel;
	}

	// warehouse menu: button Add
	private Component drawStockMenuPane() {
		JPanel panel = new JPanel();

		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();

		panel.setLayout(gb);

		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.weightx = 0;

		addItem = new JButton("Add");
		// Create and add the button
		addItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addItemToStockClicked();
			}
		});

		gc.gridwidth = GridBagConstraints.RELATIVE;
		gc.weightx = 1.0;
		panel.add(addItem, gc);

		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		return panel;
	}

	protected void addItemToStockClicked() {
		log.info("New  process started");
		newItem();
		// TODO
	}

	private void newItem() {

		stockItemPane.setEnabled(true);
		// TODO
	}

	// table of the wareshouse stock
	private Component drawStockMainPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Warehouse status"));

		JTable table = new JTable(model.getWarehouseTableModel());
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane, getStockScrollPaneConstraints());

		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);

		return panel;
	}

	// UI CONSTRAINTS
	private GridBagConstraints getConstraintsForStockItemkPanel() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 0.d;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.NONE;

		return gc;
	}

	private GridBagConstraints getMainPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 1.0;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.BOTH;

		return gc;
	}

	// Formatting constraints for the basketPane
	private GridBagConstraints getMenuPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 0.2;
		gc.weighty = 0d;

		return gc;
	}

	private GridBagConstraints getStockScrollPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		return gc;
	}

}