package cn.lkk.pss.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * 入库订单:组合关系的一方
 * 
 */
@Entity
@Table(name = "stockincomebill")
public class StockIncomeBill extends BaseDomain {
	private Date vdate;// 交易时间
	private BigDecimal totalAmount;
	private BigDecimal totalNum;
	private Date inputTime = new Date();
	private Date auditorTime;
	/**
	 * 0待审,1已审，-1作废
	 */
	private Integer status = 0;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;// 多对一，非空
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auditor_id")
	private Employee auditor;// 多对一，可以为空
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "inputUser_id")
	private Employee inputUser;// 多对一，非空
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "keeper_id")
	private Employee keeper;// 多对一，非空
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "depot_id")
	private Depot depot;// 多对一，非空
	// 一般组合关系使用List
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bill", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<StockIncomeBillItem> items = new ArrayList<StockIncomeBillItem>();

	public Date getVdate() {
		return vdate;
	}

	public void setVdate(Date vdate) {
		this.vdate = vdate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(BigDecimal totalNum) {
		this.totalNum = totalNum;
	}

	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	public Date getAuditorTime() {
		return auditorTime;
	}

	public void setAuditorTime(Date auditorTime) {
		this.auditorTime = auditorTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Employee getAuditor() {
		return auditor;
	}

	public void setAuditor(Employee auditor) {
		this.auditor = auditor;
	}

	public Employee getInputUser() {
		return inputUser;
	}

	public void setInputUser(Employee inputUser) {
		this.inputUser = inputUser;
	}

	public Employee getKeeper() {
		return keeper;
	}

	public void setKeeper(Employee keeper) {
		this.keeper = keeper;
	}

	public Depot getDepot() {
		return depot;
	}

	public void setDepot(Depot depot) {
		this.depot = depot;
	}

	public List<StockIncomeBillItem> getItems() {
		return items;
	}

	public void setItems(List<StockIncomeBillItem> items) {
		this.items = items;
	}

}
