package ee.ut.math.tvt.salessystem.ui.model;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.panels.PurchaseItemPanel;
public class StockTableModelTest {
	
	StockItem stockItem1;
	StockItem stockItem2;
	StockTableModel stm;
	
	@Before
	public void setUp(){
		stockItem1 = new StockItem((long)55, "item1", "test item 1", 5.5);
		stockItem2 = new StockItem((long)60, "item2", "test item 2", 6.7, 2);
		
	}

	@Test
	public void testValidateNameUniqueness() {
		stm =  new StockTableModel();
		stm.addItem(stockItem1);
		assertFalse(stm.validateNameUniqueness("item1"));
	}

	@Test
	public void testHasEnoughInStock() {
		stm =  new StockTableModel();
		stm.addItem(stockItem2);
		assertFalse(stm.hasEnoughInStock(stockItem2, 4));
	}

	@Test
	public void testGetItemByIdWhenItemExists() {
		stm =  new StockTableModel();
		stm.addItem(stockItem1);
		assertEquals(stm.getItemById((long)55), stockItem1);
	}

	@Test (expected=NoSuchElementException.class)
	public void testGetItemByIdWhenThrowsException() {
		stm =  new StockTableModel();
		stm.addItem(stockItem1);
		assertEquals(stm.getItemById((long)56), stockItem1);
	}

}
