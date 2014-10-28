package ee.ut.math.tvt.salessystem.domain.data;

import java.util.List;



public class HistoryItem implements DisplayableItem {

	private Long id;

	private String date;
	
	private String time;
	
	private Double sum;
	
	private List<SoldItem> goods;
	
	
	public HistoryItem(){
		
	}
	public HistoryItem(Long id, String date, String time, Double sum, List<SoldItem> goods) {
		this.id = id;
		this.date = date;
		this.time = time;
		this.sum = sum;
		this.goods = goods;
		}
	
	public List<SoldItem> getGoods() {
		return goods;
	}
	public void setGoods(List<SoldItem> goods) {
		this.goods = goods;
	}
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}	
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}
}