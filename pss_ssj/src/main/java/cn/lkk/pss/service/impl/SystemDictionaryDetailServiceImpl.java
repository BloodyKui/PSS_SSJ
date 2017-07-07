package cn.lkk.pss.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * 这里主要是添加一些相对应的具体的业务方法，进行业务处理，而常规的CRUD则抽取到父类中进行实现
 */
import cn.lkk.pss.domain.SystemDictionaryDetail;
import cn.lkk.pss.domain.SystemDictionaryType;
import cn.lkk.pss.service.ISystemDictionaryDetailService;
@Service
public class SystemDictionaryDetailServiceImpl extends BaseServiceImpl<SystemDictionaryDetail, Long> implements ISystemDictionaryDetailService {
	private String jpql = "SELECT o FROM SystemDictionaryDetail o WHERE o.systemDictionaryType.sn = ?";

	@Override
	public List<SystemDictionaryDetail> getBrands() {
		List<SystemDictionaryDetail> brands = super.findByJpql(jpql, SystemDictionaryType.PRODUCT_BRAND);
		System.out.println(brands);
		return brands;
	}

	@Override
	public List<SystemDictionaryDetail> getUnits() {
		List<SystemDictionaryDetail> units = super.findByJpql(jpql, SystemDictionaryType.PRODUCT_UNIT);
		return units;
	}

}
