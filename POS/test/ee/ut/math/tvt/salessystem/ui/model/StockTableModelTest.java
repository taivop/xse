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
	StockTableModel stm0;
	StockTableModel stm1;
	StockTableModel stm2;
	StockTableModel stm3;
	PurchaseItemPanel pip;
	SalesSystemModel ssm;
	SalesDomainControllerImpl sdc;
	
	@Before
	public void setUp(){
		stockItem1 = new StockItem((long)55, "item1", "test item 1", 5.5);
		stockItem2 = new StockItem((long)60, "item2", "test item 2", 6.7, 2);
		stm0=  new StockTableModel();
		stm1=  new StockTableModel();
		stm2=  new StockTableModel();
		stm3=  new StockTableModel();
	}

	@Test
	public void testValidateNameUniqueness() {
		stm0.addItem(stockItem1);
		assertFalse(stm0.validateNameUniqueness("item1"));
	}

	@Test
	public void testHasEnoughInStock() {
		stm1.addItem(stockItem2);
		assertFalse(stm1.hasEnoughInStock(stockItem2, 4));
	}

	@Test
	public void testGetItemByIdWhenItemExists() {
		stm2.addItem(stockItem1);
		assertEquals(stm2.getItemById((long)55), stockItem1);
	}

	@Test (expected=NoSuchElementException.class)
	public void testGetItemByIdWhenThrowsException() {
		stm3.addItem(stockItem1);
		assertEquals(stm3.getItemById((long)56), stockItem1);
	}

}
