package cn.lkk.pss.query;

import org.apache.commons.lang3.StringUtils;

import cn.lkk.pss.domain.Employee;

/**
 * 创建一个EmployeeQuery继承BaseQuery进行针对性的设置，里面的字段有username,email,deptId
 * 
 * @author Quid_Lee 2017年6月24日
 */
public class EmployeeQuery extends BaseQuery {

	private String username;
	private String email;
	private Long deptId;

	public EmployeeQuery() {
		super(Employee.class);
	}

	@Override
	protected void addCondition() {
		if (StringUtils.isNotBlank(username)) {
			super.addWhere("o.username LIKE ?", "%"+this.username+"%");
		}

		if (StringUtils.isNotBlank(email)) {
			super.addWhere("o.email LIKE ?", "%"+this.email+"%");
		}
		//当deptId为-1的时候也是视为全部查询
		if (deptId != null && deptId != -1) {
			super.addWhere("o.department.id = ?", this.deptId);
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

}
