package cn.lkk.pss.query;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import cn.lkk.pss.domain.PurchaseBill;
import cn.lkk.pss.domain.Supplier;

/**
 * 创建一个PurchaseBillQuery继承BaseQuery进行针对性的设置，里面的字段有username,email,deptId
 * 
 * @author Quid_Lee 2017年6月24日
 */
public class PurchaseBillQuery extends BaseQuery {
	private Integer status;
	// 开始时间
	private Date fromDate;
	// 结束时间
	private Date toDate;
	// 供应商的id
	private Long supplierId;

	public PurchaseBillQuery() {
		super(PurchaseBill.class);
	}

	@Override
	protected void addCondition() {
		//约定status为-2时查询所有
		if (status!=null&&status!=-2) {
			super.addWhere("o.status = ?", status);
		}
		// 拼接起始时间的查询条件
		if (fromDate != null) {
			super.addWhere("o.vdate >= ?", fromDate);
		}
		// 拼接结束时间的查询条件
		if (toDate != null) {
			// 为了解决查询日期区间产生的时分秒问题，所以让天数加1天，但是为了回显正确，所以另外定义一个变量
			Date endDate = DateUtils.addDays(toDate, 1);
			super.addWhere("o.vdate < ?", endDate);
		}
		// 根据供应商来查询
		if (supplierId != null && supplierId != -1) {
			super.addWhere("o.supplier.id = ?", supplierId);
		}
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

}
