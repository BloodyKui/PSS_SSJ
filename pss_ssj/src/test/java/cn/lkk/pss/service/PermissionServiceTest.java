package cn.lkk.pss.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.lkk.pss.domain.Employee;
import cn.lkk.pss.domain.Permission;
import cn.lkk.pss.query.PermissionQuery;

public class PermissionServiceTest extends BaseServiceTest{
	
	@Autowired
	IPermissionService permissionService;
	
	@Test
	public void testFindAll() throws Exception {
		List<Permission> all = permissionService.getAll();
		System.out.println(permissionService);
		System.out.println(permissionService.getClass());
		System.out.println(all.size());
	}
	
	@Test
	public void testGetPermission() throws Exception {
		//拿到系统所有的权限名称
		List<String> allSystemPermission = permissionService.getAllSystemPermission();
		System.out.println(allSystemPermission);
		System.out.println("---------------------------------------------");
		//拿到当前登录用户的所有权限
		Employee employee = new Employee();
		employee.setId(1L);
		List<String> allLoginUserPermission = permissionService.getAllLoginUserPermission(employee);
		System.out.println(allLoginUserPermission);
	}
}
