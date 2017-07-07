package cn.lkk.pss.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.lkk.pss.domain.Employee;
import cn.lkk.pss.domain.Menu;
import cn.lkk.pss.query.MenuQuery;

public class MenuServiceTest extends BaseServiceTest{
	
	@Autowired
	IMenuService menuService;
	
	@Test
	public void testFindAll() throws Exception {
		List<Menu> all = menuService.getAll();
		/*for (Menu menu : all) {
			System.out.println(menu);
		}*/
		//测试jpql的关联查询
//		String jpql = "SELECT m.name FROM Employee e JOIN e.roles r JOIN r.menus m WHERE e.id = 1";
//		List list = menuService.findByJpql(jpql, null);
		//模拟一个用户，查看想对应的菜单列表
		Employee employee = new Employee();
		employee.setId(1L);
		List<Menu> parents = menuService.findByLoginUser(employee);
		
		//查看菜单的父子关系是否正确
		for (Menu menu : parents) {
			
			System.out.println("模块======================="+menu.getName());
			System.out.println("-----------------------------------");
			List<Menu> children = menu.getChildren();
			for (Menu menu2 : children) {
				
				System.out.println(menu2);
			}
		}
	}

	
}
