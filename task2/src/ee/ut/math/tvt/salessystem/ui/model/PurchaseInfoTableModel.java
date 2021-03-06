package ee.ut.math.tvt.salessystem.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.Sale;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.SalesSystemException;

/**
 * Purchase history details model.
 */
public class PurchaseInfoTableModel extends SalesSystemTableModel<SoldItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger
			.getLogger(PurchaseInfoTableModel.class);

	private SalesSystemModel model;

	private Sale sale;

	private List<SoldItem> rows;

	public PurchaseInfoTableModel() {
		super(new String[] { "Id", "Name", "Price", "Quantity", "Sum" });
		rows = new ArrayList<SoldItem>();
	}

	public PurchaseInfoTableModel(SalesSystemModel model) {
		this();
		this.model = model;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	@Override
	protected Object getColumnValue(SoldItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getStockItem().getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		case 4:
			return item.getSum();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final SoldItem item : rows) {
			buffer.append(item.getId() + "\t");
			buffer.append(item.getName() + "\t");
			buffer.append(item.getPrice() + "\t");
			buffer.append(item.getQuantity() + "\t");
			buffer.append(item.getSum() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

	/**
	 * Returns the total sum that needs to be paid for all the items in the
	 * basket.
	 */
	public double getTotalPrice() {
		double price = 0.0;
		for (SoldItem item : rows) {
			price += item.getSum();
		}
		return price;
	}

	public static PurchaseInfoTableModel getEmptyTable() {
		return new PurchaseInfoTableModel();
	}

	/**
	 * Replace the current contents of the table with the SoldItems of the given
	 * Sale. (Used by the history details table in the HistoryTab).
	 */
	public void showSale(Sale sale) {
		this.rows = new ArrayList<SoldItem>(sale.getSoldItems());
		fireTableDataChanged();
	}

	public List<SoldItem> getTableRows() {
		return rows;
	}

}
