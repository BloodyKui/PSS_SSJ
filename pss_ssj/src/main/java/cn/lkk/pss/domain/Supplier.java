package cn.lkk.pss.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "supplier")
public class Supplier extends BaseDomain {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
