package ee.ut.math.tvt.salessystem.domain.data;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "HistoryItem")
public class HistoryItem implements DisplayableItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "DATE")
	private String date;
	
	@Column(name = "TIME")
	private String time;
	
	@Column(name = "TOTAL")
	private BigDecimal sum;
	
	@Transient	// TODO is this correct?
	private List<SoldItem> goods;
	
	
	public HistoryItem(){
		
	}
	public HistoryItem(String date, String time, BigDecimal sum, List<SoldItem> goods) {
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
	
	public void addSoldItem(SoldItem item) {
		this.goods.add(item);
		this.sum.add(item.getSum());
		this.sum.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public Long getId() {
		return id;
	}
	
	//Getting sum of goods
	public BigDecimal getSumOfGoods() {
		BigDecimal sumOfGoods = new BigDecimal(0);
		for (SoldItem si : goods) {
			sumOfGoods=sumOfGoods.add(si.getSum());
		}
		return sumOfGoods.setScale(2, BigDecimal.ROUND_HALF_UP);
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

	public BigDecimal getSum() {
		return sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}
}