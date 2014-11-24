package ee.ut.math.tvt.salessystem.ui.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class PurchaseInfoTableModelTest {
	
	StockItem stockItem1;
	StockItem stockItem2;
	SoldItem soldItem1;
	SoldItem soldItem2;
	
	
	
	@Before
	public void setUp(){
		
		stockItem1 = new StockItem((long) 1, "Test Item1", "For testing", 10.0, 2);
		stockItem2 = new StockItem((long) 2, "Test Item2", "For testing", 11.0, 1);
		soldItem1 = new SoldItem(stockItem1, 2);
		soldItem2 = new SoldItem(stockItem2, 1);
		
	}
	
	
	@Test
	public void testGetColumnValue(){
		PurchaseInfoTableModel pitm= new PurchaseInfoTableModel();
		pitm.addItem(soldItem1);
		System.out.println(pitm.toString());
		assertEquals(pitm.getColumnValue(soldItem1, 0), (long)1); //ID
		assertEquals(pitm.getColumnValue(soldItem1, 1),"Test Item1"); //Name
		assertEquals(pitm.getColumnValue(soldItem1, 2), 10.0); //Price
		assertEquals(pitm.getColumnValue(soldItem1, 3), 2); //Quantity
		assertEquals(pitm.getColumnValue(soldItem1, 4), new BigDecimal(2 * 10.0).setScale(2, RoundingMode.HALF_UP));//Sum
	}

	@Test
	public void testAddItem(){
		PurchaseInfoTableModel pitm= new PurchaseInfoTableModel();
		pitm.addItem(soldItem1);
		int rowCount=pitm.getRowCount();
		assertEquals(pitm.getTableRows().get(rowCount-1), soldItem1);
		pitm.addItem(soldItem2);
		rowCount=pitm.getRowCount();
		assertEquals(pitm.getTableRows().get(rowCount-1), soldItem2);
		
	}
}
