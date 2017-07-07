package cn.lkk.pss.web.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import cn.lkk.pss.domain.Product;
import cn.lkk.pss.domain.ProductType;
import cn.lkk.pss.page.PageList;
import cn.lkk.pss.query.ProductQuery;
import cn.lkk.pss.service.IProductService;
import cn.lkk.pss.service.IProductTypeService;
import cn.lkk.pss.service.ISystemDictionaryDetailService;
import net.coobird.thumbnailator.Thumbnails;

@Controller
@Scope("prototype")
public class ProductAction extends CRUDAction<Product> {
	@Autowired
	private IProductService productService;
	@Autowired
	private IProductTypeService productTypeService;
	@Autowired
	private ISystemDictionaryDetailService systemDictionaryDetailService;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmssS");
	/*
	 * 哪些数据放List栈，哪些数据放map栈 我们说的是外面的基本要求：
	 * 当前Action中的数据都放在list栈(即XxxAction，那么所有的跟Xxx有关的数据都放入List栈中) 其他的数据放入map栈中
	 */
	// 通过get方法传到前端页面
	// private List<Product> products;

	/*
	 * 创建PageList对象用于将数据返回到页面
	 */
	private PageList<Product> pageList = new PageList<>();

	/*
	 * 查询条件对象，用于封装从前台传过来的查询条件
	 */
	private ProductQuery baseQuery = new ProductQuery();
	// 实体类 因为CRUDAction父类实现了ModelDriven方法，所以不用提供get方法，通过getModel拿值
	// 同时在拦截器ModelDriven中如果对象不为空，则将值压入值栈的栈顶。此时在页面上就可以直接使用对象中的字段进行取值了
	private Product product;

	@Override
	public void list() {
		// 将数据通过map栈传到前端页面
		// putContext("products", productService.getAll());
		// products = productService.getAll();
		// -1代表选择所有部门，那么就将查询条件对象中的部门id情况，去掉对部门的筛选
		pageList = productService.findPageByQuery(baseQuery);
	}

	//////////////////// 进行增删改操作//////////////////////////////
	// 跳转到新增或者修改页面
	@Override
	public String input() throws Exception {
		// 需要选择产品类型列表
		// putContext("allTypes", productTypeService.getAll());
		// 需要选择品牌列表
		putContext("allBrands", systemDictionaryDetailService.getBrands());
		// 需要选择单位列表
		putContext("allUnits", systemDictionaryDetailService.getUnits());
		List<ProductType> parents = productTypeService.findAllParents();
		// 查询出所有父类型
		putContext("allParents", parents);
		// 如果当前产品的产品类型的父类型不为空，则说明是修改，直接查询出当前父类型的所有子类、
		// 使用三目是为了排除新增时product.getParentType()为null的情况
		ProductType parent = product.getParentType() != null ? product.getParentType().getProductTypeParent() : null;
		if (parent != null) {
			System.out.println(parent.getId());
			putContext("allChildren", productTypeService.findChildren(parent.getId()));
		} else {
			// 如果id为空，则根据第一种父类型来查询出所有子类型
			putContext("allChildren", productTypeService.findChildren(parents.get(0).getId()));
		}
		return INPUT;
	}

	// 在执行input方法前会执行
	@Override
	public void prepareInput() throws Exception {
		if (id != null) {
			// 这里的get方式调用的是getOne 但是这是得到的是代理对象，这样会造成懒加载问题
			// 所以持久层要使用findOne方法
			// product = productService.get(product.getId());
			product = productService.get(id);
		} else {
			// 如果是新增，同样new一个对象，防止input方法中判断是否有父类型时，空指针异常
			product = new Product();
		}
	}

	// 保存或修改方法
	@Override
	@InputConfig(methodName = INPUT)
	public String save() throws Exception {
		if (upload != null) {
			// 获取webapp在服务器上面的物理路径
			// D:\EclipseAndTomcat\eclipse_new\workspace\pss\src\main\webapp
			String webapp = ServletActionContext.getServletContext().getRealPath("/");
			// 上传文件命名,拷贝到webapp的位置,设置pic到product
			Date date = new Date();
			// 大小图的路径+文件名称
			String fileName = "upload/" + sdf.format(date) + ".png";
			String smallFileName = "upload/" + sdf.format(date) + "_small.png";
			// 大小图的在服务器上面的物理路径
			File destFile = new File(webapp, fileName);
			File smallDestFile = new File(webapp, smallFileName);

			// 生成upload目录
			File parentFile = destFile.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();// 自动生成upload目录
			}

			// 把上传的临时图片，复制到当前项目的webapp路径
			FileUtils.copyFile(upload, destFile);
			// 处理缩略图
			Thumbnails.of(upload).scale(0.1F).toFile(smallDestFile);
			// 把大小图的相对webapp的路径设置到数据库产品表里面
			product.setPic(fileName);
			product.setSmallPic(smallFileName);
		}

		// save方法中集成了保存和修改
		productService.save(product);
		if (id == null) {
			//保存后跳转到最后一页
			baseQuery.setCurrentPage(Integer.MAX_VALUE);
		}
		// 保存后就可以获得这个id
		id = product.getId();
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
			product = productService.get(id);
			productService.delete(super.id);
			// 删除图片的代码，写在delete方法之后
			String webapp = ServletActionContext.getServletContext().getRealPath("/");
			//删除磁盘上保存的图片
			if (id != null && StringUtils.isNotBlank(product.getPic())) {
				File deleteFile = new File(webapp, product.getPic());
				if (deleteFile.exists()) {
					deleteFile.delete();
				}
				File deleteSmallFile = new File(webapp, product.getSmallPic());
				if (deleteSmallFile.exists()) {
					deleteSmallFile.delete();
				}

			}
			// 如果删除成功，则向前端返回一个true的信号
			response.getWriter().print("{\"ok\":true}");
		} catch (Exception e) {
			// 如果删除出现异常，则进行捕获，并将错误信息打印到前端页面
			response.getWriter().print("{\"ok\":false,\"msg\": \"删除失败！" + e.getMessage() + "\"}");
		}
		// 使用ajax后就不用在返回结果视图了
		return NONE;
	}

	// 处理上传
	private File upload;

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	// 在执行save方法前会执行
	@Override
	public void prepareSave() throws Exception {
		// 操作之前将所有有关联的对象置空
		if (id != null) {
			product = productService.get(id);
			// 把持久状态的xxx变成临时状态
			product.setParentType(null);
			product.setUnit(null);
			product.setBrand(null);

		} else {
			product = new Product();
		}
		String webapp = ServletActionContext.getServletContext().getRealPath("/");
		if (id != null && StringUtils.isNotBlank(product.getPic())) {
			File deleteFile = new File(webapp, product.getPic());
			if (deleteFile.exists()) {
				deleteFile.delete();
			}
			File deleteFile2 = new File(webapp, product.getSmallPic());
			if (deleteFile2.exists()) {
				deleteFile2.delete();
			}
		}
	}

	// jsp发出ajax:返回二级类型的json数据
	// 要做二级联动的回显就应该先拿到所有的父类型，然后在根据当前父类型的id去拿到所有的子类型
	public String findChildren() {
		List<ProductType> childrenList = productTypeService.findChildren(id);
		putContext("map", childrenList);
		return "json";
	}

	//添加订单明细的时候，返回一个产品列表
	public String bill() throws Exception {
		//使分页失效
		baseQuery.setPageSize(Integer.MAX_VALUE);
		list();
		return "bill";
	}
	
	@Override
	public Product getModel() {
		return product;
	}

	/***** Action中接收参数的getter与setter ************/

	// 因为已经new了，所以只用提供get方法
	public PageList<Product> getPageList() {

		return pageList;
	}

	public ProductQuery getBaseQuery() {

		return baseQuery;
	}

}
