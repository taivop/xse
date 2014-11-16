package ee.ut.math.tvt.salessystem.domain.data;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StockItemTest {

	@Test
	public void testClone() {
		StockItem stockItem = new StockItem((long) 1, "Test Item",
				"For testing", 9.1, 3);
		StockItem clonedItem = (StockItem) stockItem.clone();
		assertEquals(stockItem, clonedItem);

	}

	@Test
	public void testGetColumn() {

		StockItem stockItem = new StockItem((long) 1, "Test Item",
				"For testing", 9.1, 3);
		assertEquals(stockItem.getColumn(0), stockItem.getId());
		assertEquals(stockItem.getColumn(1), stockItem.getName());
		assertEquals(stockItem.getColumn(2), new Double(9.1));
		assertEquals(stockItem.getColumn(3), new Integer(3));

	}

}
