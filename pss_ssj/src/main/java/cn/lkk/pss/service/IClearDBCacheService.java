package cn.lkk.pss.service;

public interface IClearDBCacheService {
	/**
	 * 定义一个每一小时清空ip缓存的方法
	 */
	void clearCache();
}
