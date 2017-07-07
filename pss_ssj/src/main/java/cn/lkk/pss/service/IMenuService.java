package cn.lkk.pss.service;

import java.util.List;

import cn.lkk.pss.domain.Employee;
import cn.lkk.pss.domain.Menu;

public interface IMenuService extends IBaseService<Menu, Long> {
	//通过访问的当前用户，来访问想对应的菜单
	List<Menu> findByLoginUser(Employee employee);
}
