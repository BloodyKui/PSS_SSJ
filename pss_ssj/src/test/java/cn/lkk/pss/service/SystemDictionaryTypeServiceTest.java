package cn.lkk.pss.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.lkk.pss.domain.SystemDictionaryType;
import cn.lkk.pss.query.SystemDictionaryTypeQuery;

public class SystemDictionaryTypeServiceTest extends BaseServiceTest{
	
	@Autowired
	ISystemDictionaryTypeService systemDictionaryTypeService;
	
	@Test
	public void testFindAll() throws Exception {
		List<SystemDictionaryType> all = systemDictionaryTypeService.getAll();
		System.out.println(systemDictionaryTypeService);
		System.out.println(systemDictionaryTypeService.getClass());
		System.out.println(all.size());
	}

}
