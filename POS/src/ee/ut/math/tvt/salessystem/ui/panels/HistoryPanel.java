package ee.ut.math.tvt.salessystem.ui.panels;

import javax.swing.JComponent;
import javax.swing.JPanel;

import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

/**
 * Purchase pane + shopping cart table UI.
 */
public class HistoryPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// Warehouse model
	private SalesSystemModel model;

	/**
	 * Constructs new history panel.
	 * 
	 * @param model
	 *            composite model of the warehouse and the shopping cart.
	 */
	public HistoryPanel(SalesSystemModel model) {
		this.model = model;

	}

	// This method should draw the history table
	private JComponent drawHistoryTable() {
		// can model after
		// salessystem.ui.panels.PurchaseItemPanel.drawBasketPane()

		return null;
	}

}
