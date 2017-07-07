package cn.lkk.pss.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "systemdictionarytype")
public class SystemDictionaryType extends BaseDomain {
	// 定义2个常量：系统初始化的时候
	public static final String PRODUCT_BRAND = "productBrand";// 产品品牌
	public static final String PRODUCT_UNIT = "productUnit";// 产品单位

	// sn字段不会出现在update语句里面
	@Column(updatable = false,unique = true)
	private String sn;
	private String name;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
