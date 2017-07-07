package cn.lkk.pss.service;

import java.util.List;
import java.util.Map;

import cn.lkk.pss.domain.PurchaseBillItem;
import cn.lkk.pss.query.PurchaseBillItemQuery;

public interface IPurchaseBillItemService extends IBaseService<PurchaseBillItem, Long> {
	/**
	 * 根据前台的查询条件和分组进行查询
	 * @param baseQuery 可以输入的查询条件有起止时间，状态，分组
	 * @return
	 */
	List<Object[]> findByGroup(PurchaseBillItemQuery baseQuery);
	
	/**
	 * 再定义一个根据查询条件查询每一个分组中的明细的方法(需要多传一个条件)
	 * @param condition 传入的是分组条件
	 */
	List<PurchaseBillItem> findItemByGroup(PurchaseBillItemQuery baseQuery, Object condition);
	
	/**
	 * 只获取名称和小计的查询方法
	 */
	List<Map<String, Object>> findDataToJson(PurchaseBillItemQuery baseQuery);
}
