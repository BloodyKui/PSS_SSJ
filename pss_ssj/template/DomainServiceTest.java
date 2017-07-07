package cn.lkk.pss.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.lkk.pss.domain.${entity};
import cn.lkk.pss.query.${entity}Query;

public class ${entity}ServiceTest extends BaseServiceTest{
	
	@Autowired
	I${entity}Service ${lowerEntity}Service;
	
	@Test
	public void testFindAll() throws Exception {
		List<${entity}> all = ${lowerEntity}Service.getAll();
		System.out.println(${lowerEntity}Service);
		System.out.println(${lowerEntity}Service.getClass());
		System.out.println(all.size());
	}

}
