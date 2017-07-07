package cn.lkk.pss.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "systemdictionarydetail")
public class SystemDictionaryDetail extends BaseDomain {
	private String name;
	// optional = false表示这个外键不能为空
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "types_id")
	private SystemDictionaryType systemDictionaryType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SystemDictionaryType getSystemDictionaryType() {
		return systemDictionaryType;
	}

	public void setSystemDictionaryType(SystemDictionaryType systemDictionaryType) {
		this.systemDictionaryType = systemDictionaryType;
	}

}
