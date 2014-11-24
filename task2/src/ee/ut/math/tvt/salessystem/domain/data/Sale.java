package ee.ut.math.tvt.salessystem.domain.data;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Query;

import ee.ut.math.tvt.salessystem.domain.exception.SalesSystemException;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Sale object. Contains client and sold items.
 */
@Entity
@Table(name = "SALE")
public class Sale implements DisplayableItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(targetEntity = SoldItem.class, mappedBy = "sale", cascade = CascadeType.ALL)
	private Set<SoldItem> soldItems;
	private Date sellingTime;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CLIENT_ID")
	private Client client;

	/** Empty constructors are used by hibernate */
	public Sale() {
	}

	public Sale(Client cli) {
		this.soldItems = new HashSet<SoldItem>();
		this.client = cli;
	}

	public Sale(List<SoldItem> goods) {
		this.soldItems = new HashSet<SoldItem>(goods);
		this.sellingTime = new Date();
	}
	public Sale(List<SoldItem> goods,Client cli) {
		this.soldItems = new HashSet<SoldItem>(goods);
		this.client = cli;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Date getSellingTime() {
		return sellingTime;
	}

	public void setSellingTime(Date sellingTime) {
		this.sellingTime = sellingTime;
	}

	public Set<SoldItem> getSoldItems() {
		return soldItems;
	}

	public void setSoldItems(Set<SoldItem> soldItems) {
		this.soldItems = soldItems;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void addSoldItem(SoldItem soldItem) throws SalesSystemException {
		Long stockItemId = soldItem.getStockItem().getId();
		StockItem stockItem = soldItem.getStockItem();

		// if exists in cart
		// validate stock
		SoldItem existingItem = getForStockItem(stockItemId);

		if (existingItem != null) {
			// increasing Quantity
			int totalQuantity = existingItem.getQuantity()
					+ soldItem.getQuantity();
			validateQuantityInStock(stockItem, totalQuantity);
			existingItem.setQuantity(totalQuantity);

		} else {
			validateQuantityInStock(soldItem.getStockItem(),
					soldItem.getQuantity());

			soldItem.setSale(this);
			soldItems.add(soldItem);

		}

	}

	private void validateQuantityInStock(StockItem si, int quantity)
			throws SalesSystemException {

		// getting old quantity from DB
		Query query1 = HibernateUtil.currentSession().createQuery(
				"from StockItem where id=:id");
		query1.setParameter("id", si.getId());
		List<StockItem> item = query1.list();
		int currentStock = item.get(0).getQuantity();

		if (currentStock < quantity)
			throw new SalesSystemException();
	}

	public SoldItem getForStockItem(long stockItemId) {
		for (SoldItem item : soldItems) {
			if (item.getStockItem().getId().equals(stockItemId)) {
				return item;
			}
		}
		return null;
	}

	public double getSum() {
		double sum = 0.0;
		for (SoldItem item : soldItems) {
			sum = sum + item.getPrice() * ((double) item.getQuantity());
		}
		return sum;
	}

}
