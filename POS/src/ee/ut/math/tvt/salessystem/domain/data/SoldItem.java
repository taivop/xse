package ee.ut.math.tvt.salessystem.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Already bought StockItem. SoldItem duplicates name and price for preserving
 * history.
 */
@Entity
@Table(name = "SoldItem")
public class SoldItem implements Cloneable, DisplayableItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "sale_id")
	private Long sale_id;

	@Column(name = "name")
	private String name;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "itemprice")
	private double price;

	@Column(name = "stockitem_id")
	private Long stockItemId;

	public SoldItem(StockItem stockItem, int quantity) {
		// this.stockItem = stockItem;
		this.stockItemId = stockItem.getId();
		this.name = stockItem.getName();
		this.price = stockItem.getPrice();
		this.quantity = quantity;

	}

	public Long getStockItemId() {
		return stockItemId;
	}

	public void setStockItemId(Long stockItemId) {
		this.stockItemId = stockItemId;
	}

	public Long getSale_id() {
		return sale_id;
	}

	public void setSale_id(Long sale_id) {
		this.sale_id = sale_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	@Override
	public String toString() {
		return "SoldItem [id=" + id + ", sale_id=" + sale_id + ", name=" + name
				+ ", quantity=" + quantity + ", price=" + price
				+ ", stockItemId=" + stockItemId + "]";
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public SoldItem(Long id, Long sale_id, Long stockItemId, String name,
			Integer quantity, double price) {
		super();
		this.id = id;
		this.sale_id = sale_id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.stockItemId = stockItemId;
	}

	public SoldItem() {
	}

	public double getSum() {
		return Math.round(price * ((double) quantity)* 100.0) / 100.0;
	}
	/*
	 * public StockItem getStockItem() { return stockItem; }
	 * 
	 * public void setStockItem(StockItem stockItem) { this.stockItem =
	 * stockItem; }
	 */
}
