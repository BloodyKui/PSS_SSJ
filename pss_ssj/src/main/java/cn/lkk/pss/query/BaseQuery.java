package cn.lkk.pss.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 创建一个BaseQuery(作为所有查询对象的父类)里面有
 * currentPage,PageSize(这两个字段和PageList字段中不同，这两个字段是将前台设置的条件返回到后台；
 * 而PageList中的这两个字段是从后台返回到前台的数据)
 * 
 * @author Quid_Lee 2017年6月24日
 */
public abstract class BaseQuery {
	// 当前页
	private int currentPage = 1;
	// 每页条数
	private int pageSize = 10;
	// 查询sql,因为这两条sql是每个domain做高级分页查询都会发送的，所以将这两条sql抽取到BaseQuery中进行拼接；
	private StringBuilder countjpql;
	private StringBuilder limitjpql;
	private StringBuilder onlyWhereCondition;
	// 用于装查询条件的参数
	private List params;
	// 标志，判断是否已经拼接好了sql，默认为true-表示还未拼接
	private boolean flag = true;

	public BaseQuery(Class entityClass) {
		/*
		 * sql中的查询domain利用子类构造方法会自动调用父类无参构造方法的特性，
		 * 将具体查询的类型传入到BaseQuery中；这样没有查询条件的sql就拼接完成了。
		 */
		this.countjpql = new StringBuilder("SELECT count(o) FROM " + entityClass.getName() + " o");
		this.limitjpql = new StringBuilder("SELECT o FROM " + entityClass.getName() + " o");
		//只需要where后面的条件就可以了，所以这里只产生空串
		this.onlyWhereCondition = new StringBuilder();
		this.params = new ArrayList();
	}

	/*
	 * 定义一个抽象方法addCondition让子类必须实现用于将查询条件语句和参数传入到addWhere中。
	 * 以后就只用在子类中修改具体的查询条件即可。
	 */
	protected abstract void addCondition();

	// addWhere方法中完成拼接
	protected void addWhere(String whereJpql, Object... params) {
		if (this.params.size() > 0) {
			this.countjpql.append(" AND ").append(whereJpql);
			this.limitjpql.append(" AND ").append(whereJpql);
			this.onlyWhereCondition.append(" AND ").append(whereJpql);
		} else {
			this.countjpql.append(" WHERE ").append(whereJpql);
			this.limitjpql.append(" WHERE ").append(whereJpql);
			this.onlyWhereCondition.append(" WHERE ").append(whereJpql);
		}
		// 将本次拼接的参数添加到查询参数列表中(必须按顺序添加)
		this.params.addAll(Arrays.asList(params));
	}

	/*
	 * 用于在仓库中能拿到sql和查询条件参数
	 * 因为需要仓库中需要拿到sql，所以在get方法中执行拼接方法即可(此处需要使用标记，方法多个get方法调用时重复拼接)
	 */
	private void builderWhere() {
		if (this.flag) {
			this.addCondition();
			this.flag = false;
		}
	}

	public String getCountjpql() {

		this.builderWhere();
		return countjpql.toString();
	}

	public String getLimitjpql() {
		this.builderWhere();
		return limitjpql.toString();
	}

	public String getOnlyWhereCondition() {
		this.builderWhere();
		return onlyWhereCondition.toString();
	}
	
	public List getParams() {
		this.builderWhere();
		return params;
	}

	/////////////////////// getter/setter方法/////////////////////////////
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
