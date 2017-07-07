package cn.lkk.pss.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * 这里主要是添加一些相对应的具体的业务方法，进行业务处理，而常规的CRUD则抽取到父类中进行实现
 */
import cn.lkk.pss.domain.PurchaseBillItem;
import cn.lkk.pss.query.PurchaseBillItemQuery;
import cn.lkk.pss.service.IPurchaseBillItemService;

@Service
public class PurchaseBillItemServiceImpl extends BaseServiceImpl<PurchaseBillItem, Long>
		implements IPurchaseBillItemService {

	@Override
	public List<Object[]> findByGroup(PurchaseBillItemQuery baseQuery) {
		// 拿到分组的条件
		String groupBy = baseQuery.getGroupBy();
		String onlyWhereCondition = baseQuery.getOnlyWhereCondition();
		// 因为这里必定有分组操作，所以在PurchaseBillItemQuery将groupBy设定一个默认值
		String jpql = "SELECT " + groupBy + ", count(item), sum(num), sum(amount) FROM PurchaseBillItem item "
				+ onlyWhereCondition + " GROUP BY " + groupBy;
		// 设置参数
		List<Object> params = baseQuery.getParams();
		// 可变参数不能接收集合，所以要转为数组
		List<Object[]> group = super.findByJpql(jpql, params.toArray());
		return group;
	}

	@Override
	public List<PurchaseBillItem> findItemByGroup(PurchaseBillItemQuery baseQuery, Object condition) {
		// 拿到分组的条件
		String groupBy = baseQuery.getGroupBy();
		String onlyWhereCondition = baseQuery.getOnlyWhereCondition();
		// 拼接，将分组的条件放在首位，因为放后面，有可能onlyWhereCondition没有值，所以不好判断groupBy是用and还是where
		String jpql = "SELECT item FROM PurchaseBillItem item WHERE " + groupBy + "= ? "
				+ onlyWhereCondition.replaceFirst("WHERE", "AND");
		// 因为查询时向第一个参数位置添加分组条件参数时没有将之前的参数替换掉(此时baseQuery对象是唯一的)，所以格外使用另外一个参数集合来进行拼接。
		List<Object> params = new ArrayList<>();
		params.add(condition);
		// addAll是全部添加，而非是只作为集合添加一个
		params.addAll(baseQuery.getParams());
		List<PurchaseBillItem> items = super.findByJpql(jpql, params.toArray());
		return items;
	}

	@Override
	public List<Map<String, Object>> findDataToJson(PurchaseBillItemQuery baseQuery) {
		// 拿到分组的条件
		String groupBy = baseQuery.getGroupBy();
		String onlyWhereCondition = baseQuery.getOnlyWhereCondition();
		// 因为这里必定有分组操作，所以在PurchaseBillItemQuery将groupBy设定一个默认值
		String jpql = "SELECT " + groupBy + ",sum(amount) FROM PurchaseBillItem item "
				+ onlyWhereCondition + " GROUP BY " + groupBy;
		// 设置参数
		List<Object> params = baseQuery.getParams();
		// 可变参数不能接收集合，所以要转为数组
		List<Object[]> group = super.findByJpql(jpql, params.toArray());
		List<Map<String, Object>> datas = new ArrayList<>();
		for (Object[] objects : group) {
			Map<String, Object> data = new HashMap<>();
			data.put("name",objects[0]);
			data.put("y",objects[1]);
			data.put("sliced",true);
			data.put("selected",false);
			datas.add(data);
		}
		return datas;
	}

}
