package ee.ut.math.tvt.salessystem.domain.data;

import java.math.BigDecimal;


public class ViewItem implements Cloneable, DisplayableItem, Comparable<ViewItem> {

	private Long id;
	
	private String name;
	
	private BigDecimal price;
	
    private Integer quantity;
	
    private BigDecimal total;
	
	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public void setName(String name) {
		this.name = name;
	}
    
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

//	public String getName() {
//		return stockItem.getName();
//	}
	
	public String getName() {
		return name;
	}

	public ViewItem()
	{}

	
    @Override
	public String toString() {
		return "ViewItem [id=" + id + ", name=" + name + ", price=" + price
				+ ", quantity=" + quantity + ", total=" + total + "]";
	}

	public ViewItem(Long id, String name, BigDecimal price, Integer quantity,
			BigDecimal total) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.total = total;
	}

	public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public int compareTo(ViewItem v)
    {
    	// When sorting ViewItems, sort on ID
         return (int) (this.getId() - v.getId());	// ugly cast. assuming product IDs fit into ints
    }
    
 
}