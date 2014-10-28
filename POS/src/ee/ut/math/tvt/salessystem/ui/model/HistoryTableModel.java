package ee.ut.math.tvt.salessystem.ui.model;

import java.util.NoSuchElementException;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;

public class HistoryTableModel extends SalesSystemTableModel<HistoryItem> {

	public HistoryTableModel() {
		super(new String[] { "Id", "Date", "Time", "Order sum"});
	}
	
	@Override
	protected Object getColumnValue(HistoryItem item, int columnIndex) {
		// TODO Auto-generated method stub
		switch(columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getDate();
		case 2:
			return item.getTime();
		case 3:
			return item.getSum();
		}
		return null;
	}
	
	
	public void addItem(final HistoryItem historyItem) {
        
			try {
				rows.add(historyItem);
			}
			catch (NoSuchElementException e) {
				System.out.println(e);
			}
			fireTableDataChanged();
    }

}