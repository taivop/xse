package ee.ut.math.tvt.salessystem.ui.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class HistoryTableModelTest {

	
	HistoryItem historyItem;
	StockItem stockItem1;
	StockItem stockItem2;
	SoldItem soldItem1;
	SoldItem soldItem2;
	List<SoldItem> goods;
	
	@Before
	public void setUp() {
		stockItem1 = new StockItem((long) 1, "Test Item", "For testing", 10.0, 1);
		stockItem2 = new StockItem((long) 2, "Test Item", "For testing", 11.0, 1);
		soldItem1 = new SoldItem(stockItem1, 2);
		soldItem2 = new SoldItem(stockItem2, 1);
		goods = new ArrayList<SoldItem>();
		goods.add(soldItem1);
		goods.add(soldItem2);
		historyItem = new HistoryItem("23/11/2014", "01:01:01",
				new BigDecimal(2 * 11.0+10.0), goods);
		historyItem.setId((long)1);
	}
	
	@Test 
	public void testAddItem(){
		HistoryTableModel htm= new HistoryTableModel();
		htm.addItem(historyItem);
		int rowCount=htm.getRowCount();
		assertEquals(htm.getTableRows().get(rowCount-1), historyItem);
	}

	
	@Test 
	public void testGetColumnValue(){
		HistoryTableModel htm= new HistoryTableModel();
		htm.addItem(historyItem);
		assertEquals(htm.getColumnValue(historyItem, 0), (long)1); //ID
		assertEquals(htm.getColumnValue(historyItem, 1), "23/11/2014"); //Date
		assertEquals(htm.getColumnValue(historyItem, 2), "01:01:01"); //Time
		assertEquals(htm.getColumnValue(historyItem, 3), new BigDecimal(2 * 11.0+10.0).setScale(2, RoundingMode.HALF_UP)); //Cost
	}
	
	@Test 
	public void testClear(){
		HistoryTableModel htm= new HistoryTableModel();
		htm.addItem(historyItem);
		assertEquals(htm.getRowCount(), 1);
		htm.clear();
		assertEquals(htm.getRowCount(), 0);
	}
	
	
}

