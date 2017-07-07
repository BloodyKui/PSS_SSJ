package cn.lkk.pss.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.lkk.pss.domain.SystemDictionaryDetail;
import cn.lkk.pss.query.SystemDictionaryDetailQuery;

public class SystemDictionaryDetailServiceTest extends BaseServiceTest{
	
	@Autowired
	ISystemDictionaryDetailService systemDictionaryDetailService;
	
	@Test
	public void testFindAll() throws Exception {
		List<SystemDictionaryDetail> all = systemDictionaryDetailService.getAll();
		System.out.println(systemDictionaryDetailService);
		System.out.println(systemDictionaryDetailService.getClass());
		System.out.println(all.size());
	}

}
