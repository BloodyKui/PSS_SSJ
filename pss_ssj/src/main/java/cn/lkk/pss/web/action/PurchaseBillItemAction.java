package cn.lkk.pss.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import cn.lkk.pss.domain.PurchaseBillItem;
import cn.lkk.pss.page.PageList;
import cn.lkk.pss.query.BaseQuery;
import cn.lkk.pss.query.PurchaseBillItemQuery;
import cn.lkk.pss.service.IDepartmentService;
import cn.lkk.pss.service.IPurchaseBillItemService;

@Controller
@Scope("prototype")
public class PurchaseBillItemAction extends BaseAction {
	@Autowired
	private IPurchaseBillItemService purchaseBillItemService;

	private PurchaseBillItemQuery baseQuery = new PurchaseBillItemQuery();

	private List<Object[]> groups;

	@Override
	public String execute() throws Exception {
		groups = purchaseBillItemService.findByGroup(baseQuery);
		return SUCCESS;
	}
	//让前台调用这个方法拿到明细
	public List<PurchaseBillItem> findItemByGroup(Object condition) {
		return purchaseBillItemService.findItemByGroup(baseQuery, condition);
	}

	
	//转到3d饼图页面
	public String chart3d(){
		return "chart3d";
	}
	
	//转到2d饼图页面
	public String chart2d(){
		return "chart2d";
	}
	
	//通过ajax得到的数据
	public String chartData() throws Exception{
		List<Map<String, Object>> datasByJson = purchaseBillItemService.findDataToJson(baseQuery);
		putContext("map", datasByJson);
		return "json";
	}
///////////////////////////////////为了让前台拿到数据//////////////////////////////////////////////
	public List<Object[]> getGroups() {
		return groups;
	}

	public PurchaseBillItemQuery getBaseQuery() {
		return baseQuery;
	}

}
