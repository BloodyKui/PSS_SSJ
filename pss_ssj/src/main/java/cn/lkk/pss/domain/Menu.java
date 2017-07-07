package cn.lkk.pss.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 创建一个菜单对象
 * 
 * @author Quid_Lee 2017年6月28日
 */
@Entity
@Table(name = "menu")
public class Menu extends BaseDomain {

	private String name;
	private String url;
	private String icon;
	// 双向一对多自相关，设置父菜单，多个子菜单对应一个父菜单
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Menu parent;

	// 一个父菜单对应多个子菜单,,list集合用于父菜单中存储子菜单，因为只是用来存储子菜单，所以不交给jpa管理
	@Transient
	private List<Menu> children = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "Menu [name=" + name + ", url=" + url + ", icon=" + icon + "]";
	}

}
