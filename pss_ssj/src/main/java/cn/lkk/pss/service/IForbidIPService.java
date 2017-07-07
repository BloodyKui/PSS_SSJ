package cn.lkk.pss.service;

import java.util.List;

import cn.lkk.pss.domain.ForbidIP;

public interface IForbidIPService extends IBaseService<ForbidIP, Long> {
	/**
	 * 通过用户名查找到锁定ip的对象
	 * @param Username
	 * @return
	 */
	ForbidIP findByUsername(String realAddr,String username);
	
	/**
	 * 根据在拦截器中查找所有被锁定的ip
	 * @param realAddr
	 * @return
	 */
	List<String> findIPEqul5();
	
}
