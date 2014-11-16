package ee.ut.math.tvt.salessystem.domain.data;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class HistoryItemTest {
	private StockItem stockItem1;
	private StockItem stockItem2;
	private SoldItem soldItem1;
	private SoldItem soldItem2;

	@Before
	public void setUp() {
		stockItem1 = new StockItem((long) 1, "Test Item", "For testing", 9.1, 3);
		stockItem2 = new StockItem((long) 2, "Test Item1", "For testing", 6.0, 3);
		soldItem1 = new SoldItem(stockItem1, 3);
		soldItem2 = new SoldItem(stockItem2, 1);
	}

	@Test
	public void testAddSoldItem() {
		List<SoldItem> goods = new ArrayList<SoldItem>();
		goods.add(soldItem1);
		HistoryItem historyItem = new HistoryItem("01/01/2014", "01:01:01",
				new BigDecimal(3 * 9.1), goods);
		historyItem.addSoldItem(soldItem2);
		// TODO: assertequals. What should be checked?
		// Currently checking if sum is updated
		assertEquals(historyItem.getSumOfGoods(), new BigDecimal(3 * 9.1 + 6.0).setScale(2, BigDecimal.ROUND_HALF_UP));
	}

	@Test
	public void testGetSumWithNoItems() {
		// No items in order
		List<SoldItem> goods = new ArrayList<SoldItem>();
		HistoryItem historyItem = new HistoryItem("01/01/2014", "01:01:01",
				new BigDecimal(3 * 9.1), goods);
		// Sum must be 0
		assertEquals(historyItem.getSumOfGoods(), new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP));

	}

	@Test	
	public void testGetSumWithOneItem() {
		List<SoldItem> goods = new ArrayList<SoldItem>();
		goods.add(soldItem1);
		HistoryItem historyItem = new HistoryItem("01/01/2014", "01:01:01",
				new BigDecimal(3 * 9.1), goods);
		assertEquals(historyItem.getSumOfGoods(), new BigDecimal(3 * 9.1).setScale(2, BigDecimal.ROUND_HALF_UP));
	}

	@Test
	public void testGetSumWithMultipleItems() {
		List<SoldItem> goods = new ArrayList<SoldItem>();
		goods.add(soldItem1);
		goods.add(soldItem2);
		HistoryItem historyItem = new HistoryItem("01/01/2014", "01:01:01",
				new BigDecimal(3 * 9.1 + 6.0), goods);
		assertEquals(historyItem.getSumOfGoods(), new BigDecimal(3 * 9.1 + 6.0).setScale(2, BigDecimal.ROUND_HALF_UP));
	}

}
