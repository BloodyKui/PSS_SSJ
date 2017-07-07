package cn.lkk.pss.service;

import java.util.List;

import cn.lkk.pss.domain.SystemDictionaryDetail;

public interface ISystemDictionaryDetailService extends IBaseService<SystemDictionaryDetail, Long> {
	List<SystemDictionaryDetail> getBrands();
	
	List<SystemDictionaryDetail> getUnits();
}
