package ee.ut.math.tvt.salessystem.ui.model;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.ViewItem;

public class HistoryViewModel extends SalesSystemTableModel<ViewItem>{

	public HistoryViewModel() {
		super(new String[] {"Item ID", "Name", "Quantity", "Unit price", "Total price"});
		}

	@Override
	protected Object getColumnValue(ViewItem item, int columnIndex) {
		// TODO Auto-generated method stub
		switch(columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getQuantity();
		case 3:
			return item.getPrice();
		case 4:
			return item.getTotal();
		}
		return null;
	}
}