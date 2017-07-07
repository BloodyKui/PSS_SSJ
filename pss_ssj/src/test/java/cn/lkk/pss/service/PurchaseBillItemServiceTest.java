package cn.lkk.pss.service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.lkk.pss.domain.PurchaseBillItem;
import cn.lkk.pss.query.PurchaseBillItemQuery;

public class PurchaseBillItemServiceTest extends BaseServiceTest {

	@Autowired
	IPurchaseBillItemService purchaseBillItemService;

	@Test
	public void testFindAll() throws Exception {
		List<PurchaseBillItem> all = purchaseBillItemService.getAll();
		System.out.println(purchaseBillItemService);
		System.out.println(purchaseBillItemService.getClass());
		System.out.println(all.size());
	}

	// 测试没有条件jpql
	@Test
	public void testJPQLGroupWithoutCondition() throws Exception {
		// 根据供应商分组
		String jpql = "SELECT item.purchaseBill.supplier.name, count(item) FROM PurchaseBillItem item GROUP BY item.purchaseBill.supplier.name";
		// 根据采购员分组
		jpql = "SELECT item.purchaseBill.buyer.username, count(item) FROM PurchaseBillItem item GROUP BY item.purchaseBill.buyer.username";
		// 根据月份分组
		jpql = "SELECT MONTH(item.purchaseBill.vdate), count(item) FROM PurchaseBillItem item GROUP BY MONTH(item.purchaseBill.vdate)";

		// 对上述的jpql进行抽取
		String groupBySupplier = "item.purchaseBill.supplier.name";
		String groupByBuyer = "item.purchaseBill.buyer.username";
		String groupByMonth = "MONTH(item.purchaseBill.vdate)";

		// 使用jpql
		jpql = "SELECT " + groupBySupplier + ", count(item) FROM PurchaseBillItem item GROUP BY " + groupBySupplier;
		// jpql查询多个字段，应该用object数组接收
		// List<Object[]> groupBySupplier =
		// purchaseBillItemService.findByJpql(jpql);
		// List<Object[]> groupByBuyer =
		// purchaseBillItemService.findByJpql(jpql);
		// List<Object[]> groupByMonth =
		// purchaseBillItemService.findByJpql(jpql);
		List<Object[]> groupBy = purchaseBillItemService.findByJpql(jpql);
		for (Object[] objects : groupBy) {
			System.out.println(Arrays.asList(objects));
		}
	}

	// 测试有查询条件jpql明细、
	@Test
	public void testJPQLGroupWithCondition() throws Exception {
		// 对上述的jpql进行抽取
		String groupBySupplier = "item.purchaseBill.supplier.name";
		String groupByBuyer = "item.purchaseBill.buyer.username";
		String groupByMonth = "MONTH(item.purchaseBill.vdate)";
		String condition = "item.purchaseBill.status";
		// 使用jpql
		String jpql = "SELECT " + groupBySupplier + ", count(item) FROM PurchaseBillItem item GROUP BY " + groupBySupplier;
		List<Object[]> groupBy = purchaseBillItemService.findByJpql(jpql);
		for (Object[] objects : groupBy) {
			System.out.println(Arrays.asList(objects));
			//根据条件查询相应的明细
			String conditionJpql = "SELECT item FROM PurchaseBillItem item WHERE "+condition+" = ? AND "+groupBySupplier+" = ?";
			List<PurchaseBillItem> items = purchaseBillItemService.findByJpql(conditionJpql, 0,objects[0]); //objects[0]取当前分组的名称
			for (PurchaseBillItem purchaseBillItem : items) {
				System.out.println("*******************"+purchaseBillItem);
			}
		}
	}
	
	@Test
	public void testFindByGroup() throws Exception {
		//TODO 测试service层中的分组查询代码
		PurchaseBillItemQuery baseQuery = new PurchaseBillItemQuery();
		String groupBy = "item.purchaseBill.supplier.name";
//		baseQuery.setGroupBy(groupBy);
		baseQuery.setStatus(0);
//		baseQuery.setFromDate(new Date());
		List<Object[]> group = purchaseBillItemService.findByGroup(baseQuery);
		for (Object[] objects : group) {
			System.out.println(Arrays.asList(objects));
			List<PurchaseBillItem> itemByGroup = purchaseBillItemService.findItemByGroup(baseQuery, objects[0]);
			for (PurchaseBillItem purchaseBillItem : itemByGroup) {
				System.out.println(purchaseBillItem);
			}
		}
	}
}
