package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.data.ViewItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.HistoryTableModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {
	
	private SalesSystemModel model;

	public void setModel(SalesSystemModel model) {
		this.model = model;
	}
	
	public void endSession() {
	    HibernateUtil.closeSession();
	}
	public void submitCurrentPurchase(List<SoldItem> goods) throws VerificationFailedException {
		// Let's assume we have checked and found out that the buyer is underaged and
		// cannot buy chupa-chups
		// throw new VerificationFailedException("Underaged!");
		
		double sum = 0;
		for(SoldItem si : goods) {
			sum += si.getSum();
		}
		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		
		// get current highest id
		long currentHighest = 0;
		HistoryTableModel currentHistoryTableModel = model.getCurrentHistoryTableModel();
		for (HistoryItem historyItem : currentHistoryTableModel.getTableRows()) {
			if(historyItem.getId() > currentHighest) {
				currentHighest = historyItem.getId();
			}
		}
		
		Long id = currentHighest + 1;
		HistoryItem hi = new HistoryItem(id, dateFormat.format(date), timeFormat.format(date), sum, goods);
		
		// Decrease stock
		for (SoldItem si : goods) {
			int oldStock = model.getWarehouseTableModel().getItemById(si.getId()).getQuantity();
			int newStock = oldStock - si.getQuantity();
			model.getWarehouseTableModel().getItemById(si.getId()).setQuantity(newStock);
		}
		
		currentHistoryTableModel.addItem(hi);;
	}

	public void cancelCurrentPurchase() throws VerificationFailedException {				
		// XXX - Cancel current purchase
	}
	

	public void startNewPurchase() throws VerificationFailedException {
		// XXX - Start new purchase
	}

	public List<StockItem> loadWarehouseState() {
		// XXX mock implementation
		List<StockItem> dataset = new ArrayList<StockItem>();

		StockItem chips = new StockItem(1l, "Lays chips", "Potato chips", 11.0, 5);
		StockItem chupaChups = new StockItem(2l, "Chupa-chups", "Sweets", 8.0, 8);
	    StockItem frankfurters = new StockItem(3l, "Frankfurters", "Beer sauseges", 15.0, 12);
	    StockItem beer = new StockItem(4l, "Free Beer", "Student's delight", 0.0, 100);

		dataset.add(chips);
		dataset.add(chupaChups);
		dataset.add(frankfurters);
		dataset.add(beer);
		
		return dataset;
	}
	
	public List<ViewItem> loadHistoryView(int rowNum) {
		HistoryItem hi = model.getCurrentHistoryTableModel().getTableRows().get(rowNum);
		List<SoldItem> goods = hi.getGoods();
		
		ArrayList<ViewItem> viewItems = new ArrayList<ViewItem>();
 
		for(SoldItem g : goods) {
			ViewItem vi = new ViewItem(g.getId(), g.getName(), new BigDecimal(g.getPrice()), g.getQuantity(),
					new BigDecimal(g.getSum()));
			viewItems.add(vi);
		}
		
		Collections.sort(viewItems);	// should sort items by ID
		
		return viewItems;
	}
}






