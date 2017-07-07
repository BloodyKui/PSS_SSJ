package cn.lkk.pss.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

@Entity
@Table(name = "productType")
public class ProductType extends BaseDomain {
	private String name;
	@JoinColumn(name="descs")
	private String descs;
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private ProductType productTypeParent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JSON(serialize=false) //是当前属性不作为json格式输出
	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

	@JSON(serialize=false) //是当前属性不作为json格式输出
	public ProductType getProductTypeParent() {
		return productTypeParent;
	}

	public void setProductTypeParent(ProductType productTypeParent) {
		this.productTypeParent = productTypeParent;
	}

}
