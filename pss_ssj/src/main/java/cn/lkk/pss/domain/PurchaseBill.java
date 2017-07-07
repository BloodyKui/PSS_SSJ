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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "purchasebill")
public class PurchaseBill extends BaseDomain {
	// 采购员 不可以为空
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "buyer_id")
	private Employee buyer;

	// 供应商 不可以为空
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;

	// 交易时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date vdate;
	/////////////// 以下代码不需要在页面上录入，由系统计算////////////////////////
	// 交易总价
	private BigDecimal totalAmount;
	// 交易总量
	private BigDecimal totalNum;

	// 该订单状态 0 - 待审；1 - 审核通过； -1 - 作废
	private Integer status = 0;
	
	// 录入员 不可以为空
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "inputUser_id")
	private Employee inputUser;

	// 审核人员
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auditor_id")
	private Employee auditor;

	// 录入时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date inputTime;
	// 审核时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date auditorTime;

	// 组合关系，本质还是双向多对一，一方放弃管理，由多方管理外键。同时设置最强级联操作,orphanRemoval=true可由一方操作多方
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "purchaseBill", orphanRemoval = true)
	private List<PurchaseBillItem> purchaseBillItems = new ArrayList<>();

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

	public Employee getBuyer() {
		return buyer;
	}

	public void setBuyer(Employee buyer) {
		this.buyer = buyer;
	}

	public List<PurchaseBillItem> getPurchaseBillItems() {
		return purchaseBillItems;
	}

	public void setPurchaseBillItems(List<PurchaseBillItem> purchaseBillItems) {
		this.purchaseBillItems = purchaseBillItems;
	}

}
