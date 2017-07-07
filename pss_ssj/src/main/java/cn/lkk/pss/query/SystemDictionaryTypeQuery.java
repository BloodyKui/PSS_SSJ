package cn.lkk.pss.query;

import org.apache.commons.lang3.StringUtils;

import cn.lkk.pss.domain.SystemDictionaryType;

/**
 * 创建一个SystemDictionaryTypeQuery继承BaseQuery进行针对性的设置，里面的字段有username,email,deptId
 * 
 * @author Quid_Lee 2017年6月24日
 */
public class SystemDictionaryTypeQuery extends BaseQuery {
	private String name;

	public SystemDictionaryTypeQuery() {
		super(SystemDictionaryType.class);
	}

	@Override
	protected void addCondition() {
		if (StringUtils.isNotBlank(name)) {
			super.addWhere("o.name LIKE ?", "%"+this.name+"%");
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String username) {
		this.name = name;
	}

}
