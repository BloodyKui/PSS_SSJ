package cn.lkk.pss.web.action;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.lkk.pss.domain.Employee;
import cn.lkk.pss.domain.ForbidIP;
import cn.lkk.pss.service.IEmployeeService;
import cn.lkk.pss.service.IForbidIPService;

@Controller
@Scope("prototype")
public class LoginAction extends BaseAction {
	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private IForbidIPService forbidIPService;

	private String username;
	private String password;
	// 定义是否记住密码的标记
	private String remFlag;
	// 获取真实ip地址
	private String realAddr;
	// 定义一个状态，当输错一次用户名或者密码后就弹出验证码，让其输入,通过前端页面传入
	private Boolean statusCode = false;

	// 验证码
	private String randomCode;

	@Override
	public String execute() throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		// 一定要设置返回的格式，否则前台拿到的就是字符串，不是json对象
		response.setContentType("text/json;charset=UTF-8");
		// 第一次进行登录的时候不需要验证码
		if (statusCode) {
			/*
			 * 一般来说，先验证验证码，后验证密码。 因为验证验证码比较简单，消耗CPU/内存等资源少。
			 * 验证密码要从数据库读取、加密/解密等，比较消耗较多资源。 如果验证码错了，就可以省去这些资源消耗...
			 */
			String random = (String) ActionContext.getContext().getSession().get("RANDOMCODE_IN_SESSION");
			// 不区分大小写
			if (!random.toLowerCase().equals(randomCode.toLowerCase())) {
				// 如果验证码不同则直接返回页面并提示验证码输入错误
				response.getWriter().print("{\"ok\":false,\"msg\": \"验证码输入错误，请重新输入!\",\"flag\":2}");
				return NONE;
			}
		}
		// 验证用户登录
		Employee employee = employeeService.login(username);
		if (employee != null) {
			// 如果对象不为空，则表示该用户名正确，再验证密码
			// 先判断ip是否被锁定
			// 拿到当前访问的客户端的真实ip
			realAddr = getIpAddr(request);
			ForbidIP forbidIP = forbidIPService.findByUsername(realAddr, username);
			if (forbidIP == null) {
				forbidIP = new ForbidIP();
				// 将id放入其中
				forbidIP.setPath_IP(realAddr);
				// 将当前输错的用户放入其中
				forbidIP.setDangerEmp(employee);
				// 设置初始可输入次数
				forbidIP.setWrong(2);
			}
			// 得到当前ip对当前用户登录的错误次数
			Integer wrong = forbidIP.getWrong();

			if (wrong == 0 && forbidIP.getPath_IP().equals(realAddr)) {
				response.getWriter().print("{\"ok\":false,\"msg\": \"对不起，你的IP已被锁定，禁止登录!\",\"flag\":1}");
				return NONE;
			}
			if (employee.getPassword().equals(password)) {
				// 如果密码相同则表示该用户登录成功，将该用户对象放到session中方便本次会话调用
				ActionContext.getContext().getSession().put(BaseAction.LOGINUSER, employee);
				// 同时判断用户是否勾选记住我，如果勾选则默认保存7天
				// 记住用户名、密码功能(注意：cookie存放密码会存在安全隐患)
				if ("1".equals(remFlag)) { // "1"表示用户勾选记住密码
					/*
					 * String cookieUserName = Utils.encrypt(name); String
					 * cookiePwd = Utils.encrypt(passWord); String loginInfo =
					 * cookieUserName+","+cookiePwd;
					 */
					// 报出异常 An invalid character [44] was present in the Cookie
					// value，ascll的44为逗号，说明cookie中不能有逗号
					String loginInfo = employee.getUsername() + "_" + employee.getPassword();
					Cookie userCookie = new Cookie("loginInfo", loginInfo);
					System.out.println(userCookie);
					userCookie.setMaxAge(7 * 24 * 60 * 60); // 存活期为7天 单位为秒
					// 设置整个路径都可以访问这个cookie
					userCookie.setPath("/");
					response.addCookie(userCookie);
				}
				// 登录成功，跳转到主页面
				response.getWriter().print("{\"ok\":true}");
			} else {
				// 表示密码错误，将该错误信息返回到前端页面
				// addActionError("密码错误，请重新输入！");
				forbidIP.setWrong(wrong - 1);
				forbidIPService.save(forbidIP);
				response.getWriter().print("{\"ok\":false,\"msg\": \"密码错误，请重新输入！你还有" + wrong + "次机会\"}");
				// TODO 记录次数,输错两次后则要求输入验证码

			}
		} else {
			// addActionError("用户名不存在，请确认！");
			response.getWriter().print("{\"ok\":false,\"msg\": \"用户名不存在，请确认!\"}");
		}
		return NONE;
	}

	public String logout() throws Exception {
		// 执行退出操作，先将本次会话中的用户信息全部清空，再跳转回登录页面
		ActionContext.getContext().getSession().clear();
		return LOGIN;
	}

	// 获得客户端真实IP地址
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemFlag() {
		return remFlag;
	}

	public void setRemFlag(String remFlag) {
		this.remFlag = remFlag;
	}

	public String getRandomCode() {
		return randomCode;
	}

	public void setRandomCode(String randomCode) {
		this.randomCode = randomCode;
	}

	public Boolean getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Boolean statusCode) {
		this.statusCode = statusCode;
	}

}
