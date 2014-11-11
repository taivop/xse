package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.data.ViewItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Implementation of the sales domain controller.
 */
@SuppressWarnings("unchecked")
public class SalesDomainControllerImpl implements SalesDomainController {
	private static final Logger log = Logger
			.getLogger(SalesDomainControllerImpl.class);

	private Session session = HibernateUtil.currentSession();

	private SalesSystemModel model;

	public void setModel(SalesSystemModel model) {
		this.model = model;
	}

	public SalesSystemModel getModel() {
		return this.model;
	}

	public void endSession() {
		HibernateUtil.closeSession();
	}

	public void submitCurrentPurchase(List<SoldItem> goods)
			throws VerificationFailedException {
		// Let's assume we have checked and found out that the buyer is
		// underaged and
		// cannot buy chupa-chups
		// throw new VerificationFailedException("Underaged!");
		try {

			session.beginTransaction();
			double sum = 0;
			for (SoldItem si : goods) {
				sum += si.getSum();
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
			Date date = new Date();
			// ID FROM DB-->no need to find highest
			// get current highest id
			/*
			 * long currentHighest = 0; HistoryTableModel
			 * currentHistoryTableModel = model .getCurrentHistoryTableModel();
			 * for (HistoryItem historyItem : currentHistoryTableModel
			 * .getTableRows()) { if (historyItem.getId() > currentHighest) {
			 * currentHighest = historyItem.getId(); } }
			 * 
			 * Long id = currentHighest + 1;
			 */
			// New historyitem. Inserting to DB
			HistoryItem hi = new HistoryItem(dateFormat.format(date),
					timeFormat.format(date), sum, goods);
			session.save(hi);

			// get id from DB
			Long saleId = hi.getId();

			// Inserting SOLDITEMS to DB and updating stock
			for (SoldItem si : goods) {
				si.setSale_id(saleId);
				session.save(si);
				int oldStock = model.getWarehouseTableModel()
						.getItemById(si.getStockItemId()).getQuantity();
				int newStock = oldStock - si.getQuantity();
				Query query = session
						.createQuery("update StockItem set quantity = :quantity "
								+ "WHERE id = :stockitem_id");
				query.setParameter("quantity", newStock);
				query.setParameter("stockitem_id", si.getStockItemId());
				query.executeUpdate();

			}

			session.getTransaction().commit();
			session.clear();

			// updating right away
			model.updateHistoryTab();
			model.getCurrentHistoryTableModel().fireTableDataChanged();
			model.updateWareHouse();
			model.getWarehouseTableModel().fireTableDataChanged();

		} catch (Exception e1) {
			// Rollback transaction if sth failed
			session.getTransaction().rollback();
			log.error("Submitting failed. Transaction rollbacked");
		}
	}

	public void cancelCurrentPurchase() throws VerificationFailedException {
		// XXX - Cancel current purchase
	}

	public void startNewPurchase() throws VerificationFailedException {
		// XXX - Start new purchase
	}

	public List<HistoryItem> loadHistoryTab() {

		List<HistoryItem> result = session.createQuery("from HistoryItem")
				.list();
		return result;
	}

	public List<StockItem> loadWarehouseState() {

		List<StockItem> dataset = session.createQuery("from StockItem").list();

		/*
		 * List<StockItem> dataset = new ArrayList<StockItem>();
		 * 
		 * StockItem chips = new StockItem(1l, "Lays chips", "Potato chips",
		 * 11.0, 5); StockItem chupaChups = new StockItem(2l, "Chupa-chups",
		 * "Sweets", 8.0, 8); StockItem frankfurters = new StockItem(3l,
		 * "Frankfurters", "Beer sauseges", 15.0, 12); StockItem beer = new
		 * StockItem(4l, "Free Beer", "Student's delight", 0.0, 100);
		 * 
		 * dataset.add(chips); dataset.add(chupaChups);
		 * dataset.add(frankfurters); dataset.add(beer);
		 */

		return dataset;
	}

	public List<ViewItem> loadHistoryView(int rowNum) {
		// data frm DB
		HistoryItem hi = model.getCurrentHistoryTableModel().getTableRows()
				.get(rowNum);
		Long saleId = hi.getId();
		Query query = session.createQuery("from SoldItem where sale_id=:id");
		query.setParameter("id", saleId);

		List<SoldItem> goods = query.list();
		ArrayList<ViewItem> viewItems = new ArrayList<ViewItem>();

		for (SoldItem g : goods) {
			ViewItem vi = new ViewItem(g.getStockItemId(), g.getName(),
					new BigDecimal(g.getPrice()), g.getQuantity(),
					new BigDecimal(g.getSum()));
			viewItems.add(vi);
		}

		Collections.sort(viewItems); // should sort items by ID

		return viewItems;
	}
}
