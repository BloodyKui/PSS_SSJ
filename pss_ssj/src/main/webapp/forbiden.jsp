<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
</head>
<body>
	<div class="page-content">
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<div class="error-container" align="center">
					<div class="well">
						<h1 class="grey lighter smaller" align="center">
							<span class="blue bigger-125"> <i class="icon-random"></i>
								403
							</span> Forbidden
						</h1>

						<hr>
						<h3 class="lighter smaller" >
							由于密码错误次数超过5次，你的ip已经被锁定 <i
								class="icon-wrench icon-animated-wrench bigger-125"></i> 
						</h3>

						<div class="space"></div>

						<div>
							<h4 class="lighter smaller">
							如果是本人误操作，请及时联系管理员进行解除</h4>

							<ul class="list-unstyled spaced inline bigger-110 margin-15">
								<li><i class="icon-hand-right blue"></i> 如果不是本公司相关人员，请不要进行尝试登录系统的操作</li>
							</ul>
						</div>

						<hr>
						<div class="space"></div>
					</div>
				</div>

				<!-- PAGE CONTENT ENDS -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</div>
</body>
</html>