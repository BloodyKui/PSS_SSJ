package cn.lkk.pss.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.lkk.pss.domain.PurchaseBill;
import cn.lkk.pss.query.PurchaseBillQuery;

public class PurchaseBillServiceTest extends BaseServiceTest{
	
	@Autowired
	IPurchaseBillService purchaseBillService;
	
	@Test
	public void testFindAll() throws Exception {
		List<PurchaseBill> all = purchaseBillService.getAll();
		System.out.println(purchaseBillService);
		System.out.println(purchaseBillService.getClass());
		System.out.println(all.size());
	}

}
