package cn.lkk.pss.web.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import cn.lkk.pss.domain.PurchaseBill;
import cn.lkk.pss.domain.PurchaseBillItem;
import cn.lkk.pss.page.PageList;
import cn.lkk.pss.query.PurchaseBillQuery;
import cn.lkk.pss.service.IDepartmentService;
import cn.lkk.pss.service.IEmployeeService;
import cn.lkk.pss.service.IPurchaseBillService;
import cn.lkk.pss.service.ISupplierService;

@Controller
@Scope("prototype")
public class PurchaseBillAction extends CRUDAction<PurchaseBill> {
	@Autowired
	private IPurchaseBillService purchaseBillService;

	@Autowired
	private ISupplierService supplierService;

	@Autowired
	private IEmployeeService employeeService;
	/*
	 * 哪些数据放List栈，哪些数据放map栈 我们说的是外面的基本要求：
	 * 当前Action中的数据都放在list栈(即XxxAction，那么所有的跟Xxx有关的数据都放入List栈中) 其他的数据放入map栈中
	 */
	// 通过get方法传到前端页面
	// private List<PurchaseBill> purchaseBills;

	/*
	 * 创建PageList对象用于将数据返回到页面
	 */
	private PageList<PurchaseBill> pageList = new PageList<>();

	/*
	 * 查询条件对象，用于封装从前台传过来的查询条件
	 */
	private PurchaseBillQuery baseQuery = new PurchaseBillQuery();
	// 实体类 因为CRUDAction父类实现了ModelDriven方法，所以不用提供get方法，通过getModel拿值
	// 同时在拦截器ModelDriven中如果对象不为空，则将值压入值栈的栈顶。此时在页面上就可以直接使用对象中的字段进行取值了
	private PurchaseBill purchaseBill;

	@Override
	public void list() {
		// 将数据通过map栈传到前端页面
		// putContext("purchaseBills", purchaseBillService.getAll());
		// purchaseBills = purchaseBillService.getAll();
		pageList = purchaseBillService.findPageByQuery(baseQuery);

		// 获取所有的供应商并放入map栈中
		putContext("allSupplier", supplierService.getAll());
	}

	//////////////////// 进行增删改操作//////////////////////////////
	// 跳转到新增或者修改页面
	@Override
	public String input() throws Exception {
		// 获取所有的供应商
		putContext("allSupplier", supplierService.getAll());
		// 获取所有的采购员
		putContext("allbuyer", employeeService.findByDeptName("采购部"));
		return INPUT;
	}

	// 保存或修改方法
	@Override
	@InputConfig(methodName = INPUT)
	public String save() throws Exception {
		// 同时重新设置录入人，为当前用户
		purchaseBill.setInputUser(super.getLoginUserInSession());

		// 用于计算总价和总数量
		BigDecimal totalAmount = new BigDecimal(0);
		BigDecimal totalNum = new BigDecimal(0);
		// 保存前必须让双方都知道对方,特别是让多方中的每一个都知道一方
		List<PurchaseBillItem> purchaseBillItems = purchaseBill.getPurchaseBillItems();
		for (PurchaseBillItem purchaseBillItem : purchaseBillItems) {
			purchaseBillItem.setPurchaseBill(purchaseBill);
			// 获得每一个产品明细的小计
			System.out.println(purchaseBillItem.getPrice());

			purchaseBillItem.setAmount(purchaseBillItem.getNum().multiply(purchaseBillItem.getPrice()));
			// 计算总价和总数量
			totalNum = totalNum.add(purchaseBillItem.getNum());
			totalAmount = totalAmount.add(purchaseBillItem.getAmount());
		}
		// 将计算好的总价和总数量放入订单中
		purchaseBill.setTotalAmount(totalAmount);
		purchaseBill.setTotalNum(totalNum);

		// 是保存功能
		if (id == null) {
			purchaseBill.setInputUser(getLoginUserInSession());
			purchaseBill.setInputTime(new Date());
		}

		// save方法中集成了保存和修改
		purchaseBillService.save(purchaseBill);
		if (id == null) {
			baseQuery.setCurrentPage(Integer.MAX_VALUE);
		}
		// 保存后就可以获得这个id
		id = purchaseBill.getId();
		// 因为重定向会丢失数据，所以id会保存在值栈中，同时也会保存在map栈中
		return RELOAD;
	}

	// 删除方法
	@Override
	public String delete() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		// 一定要设置返回的格式，否则前台拿到的就是字符串，不是json对象
		response.setContentType("text/json;charset=UTF-8");
		try {
			purchaseBillService.delete(super.id);
			// 如果删除成功，则向前端返回一个true的信号
			response.getWriter().print("{\"ok\":true}");
		} catch (Exception e) {
			// 如果删除出现异常，则进行捕获，并将错误信息打印到前端页面
			response.getWriter().print("{\"ok\":false,\"msg\": \"删除失败！" + e.getMessage() + "\"}");
		}
		// 使用ajax后就不用在返回结果视图了
		return NONE;
	}

	// 在执行input方法前会执行
	@Override
	public void prepareInput() throws Exception {
		if (id != null) {
			// 这里的get方式调用的是getOne 但是这是得到的是代理对象，这样会造成懒加载问题
			// 所以持久层要使用findOne方法
			// purchaseBill = purchaseBillService.get(purchaseBill.getId());
			purchaseBill = purchaseBillService.get(id);
			// 如果是修改，同时要获取订单明细进行回显

		} else {
			// 如果是新增，则给定一个默认时间，就是当前时间
			purchaseBill = new PurchaseBill();
			purchaseBill.setVdate(new Date());
		}
	}

	// 在执行save方法前会执行
	@Override
	public void prepareSave() throws Exception {
		// 在执行修改操作之前，将所有关联对象置空，否则会出现持久化状态不能修改OID的异常
		if (id != null) {
			purchaseBill = purchaseBillService.get(id);
			purchaseBill.setAuditor(null);
			purchaseBill.setBuyer(null);
			purchaseBill.setSupplier(null);
			purchaseBill.getPurchaseBillItems().clear();
		} else {
			purchaseBill = new PurchaseBill();
		}

	}

	@Override
	public PurchaseBill getModel() {
		return purchaseBill;
	}

	/***** Action中接收参数的getter与setter ************/

	// 因为已经new了，所以只用提供get方法
	public PageList<PurchaseBill> getPageList() {

		return pageList;
	}

	public PurchaseBillQuery getBaseQuery() {

		return baseQuery;
	}

}
