package cn.lkk.pss.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.lkk.pss.domain.Role;
import cn.lkk.pss.query.RoleQuery;

public class RoleServiceTest extends BaseServiceTest{
	
	@Autowired
	IRoleService roleService;
	
	@Test
	public void testFindAll() throws Exception {
		List<Role> all = roleService.getAll();
		System.out.println(roleService);
		System.out.println(roleService.getClass());
		System.out.println(all.size());
	}

}
