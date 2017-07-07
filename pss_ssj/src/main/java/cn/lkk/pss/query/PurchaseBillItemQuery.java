package cn.lkk.pss.query;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import cn.lkk.pss.domain.PurchaseBillItem;

/**
 * 创建一个PurchaseBillItemQuery继承BaseQuery进行针对性的设置，里面的字段有username,email,deptId
 * 
 * @author Quid_Lee 2017年6月24日
 */
public class PurchaseBillItemQuery extends BaseQuery {
	private String groupBy = "item.purchaseBill.supplier.name";
	private Date fromDate;
	private Date toDate;
	private Integer status;

	public PurchaseBillItemQuery() {
		super(PurchaseBillItem.class);
	}

	@Override
	protected void addCondition() {
		// 约定status为-2时查询所有
		if (status != null && status != -2) {
			super.addWhere("item.purchaseBill.status = ?", status);
		}
		// 拼接起始时间的查询条件
		if (fromDate != null) {
			super.addWhere("item.purchaseBill.vdate >= ?", fromDate);
		}
		// 拼接结束时间的查询条件
		if (toDate != null) {
			// 为了解决查询日期区间产生的时分秒问题，所以让天数加1天，但是为了回显正确，所以另外定义一个变量
			Date endDate = DateUtils.addDays(toDate, 1);
			super.addWhere("item.purchaseBill.vdate < ?", endDate);
		}
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
