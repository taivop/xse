package ee.ut.math.tvt.salessystem.domain.data;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class SoldItemTest {
	private StockItem item;

	@Before
	public void setUp() {
		item = new StockItem((long) 1, "Test Item", "For testing", 9.99, 3);
	}

	@Test
	public void testGetSumWithZeroQuantity() {

		SoldItem soldItem = new SoldItem(item, 0);
		assertEquals(soldItem.getSum(), new BigDecimal(0));
	}

	public void testGetSum() {
		SoldItem soldItem = new SoldItem(item, 3);
		assertEquals(soldItem.getSum(), new BigDecimal(3 * 9.99));

	}

}
