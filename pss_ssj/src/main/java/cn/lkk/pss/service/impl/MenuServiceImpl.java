package cn.lkk.pss.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.lkk.pss.domain.Employee;
/**
 * 这里主要是添加一些相对应的具体的业务方法，进行业务处理，而常规的CRUD则抽取到父类中进行实现
 */
import cn.lkk.pss.domain.Menu;
import cn.lkk.pss.service.IMenuService;
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, Long> implements IMenuService {

	@Override
	public List<Menu> findByLoginUser(Employee employee) {
		//定义想关联的jpql,注意去重
		String jpql = "SELECT DISTINCT m FROM Employee e JOIN e.roles r JOIN r.menus m WHERE e.id = ?";
		//根据当前用户查询该用户能使用的所有菜单
		List<Menu> menus = findByJpql(jpql, employee.getId());
		//遍历这个菜单进行父菜单和子菜单的筛选
		List<Menu> parents = new ArrayList<>();
		//此方法要求数据库中的子菜单必须在相应父菜单的后面，否则会造成数据丢失
		for (Menu menu : menus) {
			//如果这一个菜单对象没有父菜单，那么这个菜单就是父菜单
			if (menu.getParent() == null) {
				parents.add(menu);
			} else {
				//如果有父菜单，那么就放到相应的父菜单中
				for (Menu parent : parents) {
					//如果该菜单的id和某一个父菜单的id相同，则是该类父菜单的子菜单
					if (menu.getParent().getId()==parent.getId()) {
						parent.getChildren().add(menu);
					}
				}
			}
		}
		return parents;
	}
	
}
