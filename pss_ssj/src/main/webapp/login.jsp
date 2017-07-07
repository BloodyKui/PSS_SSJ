<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>滨河渔具进销存系统登录</title>

<!-- basic styles -->
<script src='assets/js/jquery-2.0.3.min.js'></script>
<link href="assets/css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="assets/css/font-awesome.min.css" />

<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />

<!-- ace styles -->

<link rel="stylesheet" href="assets/css/ace.min.css" />
<link rel="stylesheet" href="assets/css/ace-rtl.min.css" />
<script type="text/javascript">
	//在登录页面的js中判断如果当前页面不是顶层页面，
	//那么就把当前页面变成顶层页面(top!=window){top.location.href = window.location.href}，
	//注意判断是否顶层页面，否则为一直进行页面的设置即使是已经在顶层页面了。
	if (top != window) {
		top.location.href = window.location.href;
	}
</script>
<script type="text/javascript">
	$(document).ready(function() {
		//记住密码功能
		var str = getCookie("loginInfo");
		//截取用户名和密码
		var loginInfo = str.split("_");
		var username = loginInfo[0];
		var password = loginInfo[1];
		//自动填充用户名和密码
		$("#username").val(username);
		$("#pwd").val(password);
		
		//登录验证
		$("#loginBtn").click(function(){
			$.post("login.action",$("#loginForm").serialize(),function(result){
				if (result.ok) {
					//如果登录成功则跳转至主页面
					window.location.href="main.action";
				} else {
					console.debug(result instanceof Object);
					if (result.flag==1||!(result instanceof Object)) {
						//如果ip被锁定，则跳转到一个单独的页面，进行提示
						window.location.href="/forbiden.jsp";
					}
					$("#info").html(result.msg);
					//登录失败后，都应该更新验证码并清空输入框
					$("#randomCode").click();
					$("input[name=randomCode]").val("");
					//将验证码设置为可见
					$("#confirmCode").css("display","block");
					//让后台开启验证码验证
					$("input[name=statusCode]").val(true);
				}
			});
		});	
});
	//获取cookie
	function getCookie(cookieKey) {
		//cookie的存储形式是key=value，所以要加一个等号
		var name = cookieKey + "=";
		
		//UM_distinctid=15cf4267b9e477-0ccb936b43e818-4c322d7e-13c680-15cf4267b9f2b3; 
		//CNZZDATA155540=cnzz_eid%3D28060241-1498739868-%26ntime%3D1499204330; 
		//loginInfo=admin_admin
		console.debug(document.cookie);
		
		//多个cookie之间使用分号隔开的，所以通过分号进行拆分，得到cookie数组
		var cookies = document.cookie.split(';');
		//遍历这个数组
		for (var i = 0; i < cookies.length; i++) {
			//拿到每一个cookie字符串
			var eachCookie = cookies[i];
			//去掉前面的空格
			/* while (eachCookie.charAt(0) == ' '){
				eachCookie = eachCookie.substring(1);
			} */
			eachCookie = eachCookie.trim();
			
			//如果这个字符串中有该键值，那么就找到了对应的cookie，直接返回=号后面的字符串
			if (eachCookie.indexOf(name) != -1){
				return eachCookie.substring(name.length);
			}
		}
		return "";
	}

	//记住密码功能
	function remember() {
		var remFlag = $("input[type='checkbox']").is(':checked');
		if (remFlag == true) { //如果选中设置remFlag为1
			//cookie存用户名和密码,回显的是真实的用户名和密码,存在安全问题.
			var conFlag = confirm("记录密码功能不宜在公共场所(如网吧等)使用,以防密码泄露.您确定要使用此功能吗?");
			if (conFlag) { //确认标志
				$("#remFlag").val("1");
			} else {
				$("input[type='checkbox']").removeAttr('checked');
				$("#remFlag").val("");
			}
		} else { //如果没选中设置remFlag为""
			$("#remFlag").val("");
		}
	}
	//点击图片后更换验证码
	function changeItems(){
		$("#randomCode").attr("src","/random.action?date="+new Date());
	}

</script>
</head>

<body class="login-layout">
	<div class="main-container">
		<div class="main-content">
			<div class="row">
				<div class="col-sm-10 col-sm-offset-1">
					<div class="login-container">
						<div class="center">
							<h1>
								<i class="icon-leaf green"></i> <span class="red">滨河渔具</span> <span
									class="white">进销存系统</span>
							</h1>
							<h4 class="blue">&copy; ZHL网络科技有限公司</h4>
						</div>

						<div class="space-6"></div>

						<div class="position-relative">
							<div id="login-box"
								class="login-box visible widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header blue lighter bigger">
											<i class="icon-coffee green"></i> 请输入信息
										</h4>

										<div class="space-6"></div>
										<span id="info" style="color: red;"></span>
										<form id="loginForm" action="login.action" method="post">
											<fieldset>
												<label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="text" name="username" id="username"
														class="form-control" placeholder="Username" /> <i
														class="icon-user"></i>
												</span>
												</label> <label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="password" class="form-control" name="password"
														placeholder="Password" id="pwd" /> <i class="icon-lock"></i>
												</span>
												</label>
												<%-- 验证码 --%>
												<s:hidden  name="statusCode"/>
												<div id="confirmCode" style="display: none;">
													 <input name="randomCode" type="text" size="8" style="height: 36px"></i> <span>
													<img id="randomCode" src="/random.action" onclick="changeItems()" title="点击更换验证码"/></span>
												</div>
												<div class="space"></div>

												<div class="clearfix">
													<s:hidden name="remFlag" />
													<label class="inline"> <input type="checkbox"
														onclick="remember()" class="ace" title="建议在网吧或公共电脑上取消该选项。" />
														<span class="lbl"> Remember Me</span>
													</label>

													<button type="button" id="loginBtn"
														class="width-35 pull-right btn btn-sm btn-primary">
														<i class="icon-key"></i> Login
													</button>
												</div>

												<div class="space-4"></div>
											</fieldset>
										</form>

										<div class="social-or-login center">
											<span class="bigger-110">Or Login Using</span>
										</div>

										<div class="social-login center">
											<a class="btn btn-primary"> <i class="icon-facebook"></i>
											</a> <a class="btn btn-info"> <i class="icon-twitter"></i>
											</a> <a class="btn btn-danger"> <i class="icon-google-plus"></i>
											</a>
										</div>
									</div>
									<!-- /widget-main -->

									<div class="toolbar clearfix">
										<div>
											<a href="#" onclick="show_box('forgot-box'); return false;"
												class="forgot-password-link"> <i class="icon-arrow-left"></i>
												I forgot my password
											</a>
										</div>

										<div>
											<a href="#" onclick="show_box('signup-box'); return false;"
												class="user-signup-link"> I want to register <i
												class="icon-arrow-right"></i>
											</a>
										</div>
									</div>
								</div>
								<!-- /widget-body -->
							</div>
							<!-- /login-box -->

							<div id="forgot-box" class="forgot-box widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header red lighter bigger">
											<i class="icon-key"></i> Retrieve Password
										</h4>

										<div class="space-6"></div>
										<p>Enter your email and to receive instructions</p>

										<form>
											<fieldset>
												<label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="email" class="form-control" placeholder="Email" />
														<i class="icon-envelope"></i>
												</span>
												</label>

												<div class="clearfix">
													<button type="button"
														class="width-35 pull-right btn btn-sm btn-danger">
														<i class="icon-lightbulb"></i> Send Me!
													</button>
												</div>
											</fieldset>
										</form>
									</div>
									<!-- /widget-main -->

									<div class="toolbar center">
										<a href="#" onclick="show_box('login-box'); return false;"
											class="back-to-login-link"> Back to login <i
											class="icon-arrow-right"></i>
										</a>
									</div>
								</div>
								<!-- /widget-body -->
							</div>
							<!-- /forgot-box -->

							<div id="signup-box" class="signup-box widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header green lighter bigger">
											<i class="icon-group blue"></i> New User Registration
										</h4>

										<div class="space-6"></div>
										<p>Enter your details to begin:</p>

										<form>
											<fieldset>
												<label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="email" class="form-control" placeholder="Email" />
														<i class="icon-envelope"></i>
												</span>
												</label> <label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="text" class="form-control" placeholder="Username" />
														<i class="icon-user"></i>
												</span>
												</label> <label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="password" class="form-control"
														placeholder="Password" /> <i class="icon-lock"></i>
												</span>
												</label> <label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="password" class="form-control"
														placeholder="Repeat password" /> <i class="icon-retweet"></i>
												</span>
												</label> <label class="block"> <input type="checkbox"
													class="ace" /> <span class="lbl"> I accept the <a
														href="#">User Agreement</a>
												</span>
												</label>

												<div class="space-24"></div>

												<div class="clearfix">
													<button type="reset" class="width-30 pull-left btn btn-sm">
														<i class="icon-refresh"></i> Reset
													</button>

													<button type="button"
														class="width-65 pull-right btn btn-sm btn-success">
														Register <i class="icon-arrow-right icon-on-right"></i>
													</button>
												</div>
											</fieldset>
										</form>
									</div>

									<div class="toolbar center">
										<a href="#" onclick="show_box('login-box'); return false;"
											class="back-to-login-link"> <i class="icon-arrow-left"></i>
											Back to login
										</a>
									</div>
								</div>
								<!-- /widget-body -->
							</div>
							<!-- /signup-box -->
						</div>
						<!-- /position-relative -->
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
	</div>


	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='assets/js/jquery-2.0.3.min.js'>"
								+ "<"+"/script>");
	</script>

	<!-- <![endif]-->

	<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->

	<script type="text/javascript">
		if ("ontouchend" in document)
			document
					.write("<script src='assets/js/jquery.mobile.custom.min.js'>"
							+ "<"+"/script>");
	</script>

	<!-- inline scripts related to this page -->

	<script type="text/javascript">
		function show_box(id) {
			jQuery('.widget-box.visible').removeClass('visible');
			jQuery('#' + id).addClass('visible');
		}
	</script>
	<div style="display: none">
		<script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540'
			language='JavaScript' charset='gb2312'></script>
	</div>
</body>
</html>
