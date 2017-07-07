package cn.lkk.pss.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 创建一个权限对象
 * 
 * @author Quid_Lee 2017年6月28日
 */
@Entity
@Table(name = "permission")
public class Permission extends BaseDomain {

	private String name;
	private String method;
	// 描述
	private String descs;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

}
