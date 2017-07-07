package cn.lkk.pss.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * 即时库存表：业务要求：同一个仓库里面的产品是唯一的
 * 
 */
@Entity
@Table(name = "productstock")
public class ProductStock extends BaseDomain {
	// 产品id 入库时间 入库数量 价格 小计
	// 100 03 100 10 1000
	// 100 05 200 30 6000
	// 加权平均法
	// 100 05 300 (1000+6000)/300 7000
	private BigDecimal num;// 当前仓库的产品的数量
	private BigDecimal amount;// 当前仓库的产品的小计
	private BigDecimal price;// 当前仓库的产品的价格
	private Date incomeDate;// 入库时间
	private Boolean warning = true;// 库存过多，过少自动发出库存预警，是否需要提醒的标记
	private BigDecimal topNum = new BigDecimal(100);// 最大库存量
	private BigDecimal bottomNum = new BigDecimal(50);// 最小库存量
	// 同时唯一
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_id")
	private Product product;// 多对一,非空
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "depot_id")
	private Depot depot;// 多对一,非空

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getIncomeDate() {
		return incomeDate;
	}

	public void setIncomeDate(Date incomeDate) {
		this.incomeDate = incomeDate;
	}

	public Boolean getWarning() {
		return warning;
	}

	public void setWarning(Boolean warning) {
		this.warning = warning;
	}

	public BigDecimal getTopNum() {
		return topNum;
	}

	public void setTopNum(BigDecimal topNum) {
		this.topNum = topNum;
	}

	public BigDecimal getBottomNum() {
		return bottomNum;
	}

	public void setBottomNum(BigDecimal bottomNum) {
		this.bottomNum = bottomNum;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Depot getDepot() {
		return depot;
	}

	public void setDepot(Depot depot) {
		this.depot = depot;
	}

}
