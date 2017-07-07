package cn.lkk.pss.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * 入库订单明细:组合关系的多方
 * 
 */
@Entity
@Table(name = "stockincomebillitem")
public class StockIncomeBillItem extends BaseDomain {
	private BigDecimal price;
	private BigDecimal num;
	private BigDecimal amount;
	private String descs;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_id")
	private Product product;// 多对一,非空
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "bill_id")
	private StockIncomeBill bill;// 组合关系,非空

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getNum() {
		return num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public StockIncomeBill getBill() {
		return bill;
	}

	public void setBill(StockIncomeBill bill) {
		this.bill = bill;
	}

}
