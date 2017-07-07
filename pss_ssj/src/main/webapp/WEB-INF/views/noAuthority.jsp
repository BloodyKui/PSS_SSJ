<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>500错误页面 - Bootstrap后台管理系统模版Ace下载</title>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
</head>
<body>
	<div class="main-content">
		<div class="breadcrumbs" id="breadcrumbs">
			<script type="text/javascript">
				try {
					ace.settings.check('breadcrumbs', 'fixed')
				} catch (e) {
				}
			</script>

			<ul class="breadcrumb">
				<li><i class="icon-home home-icon"></i> <a href="#">Home</a></li>

				<li><a href="#">Other Pages</a></li>
				<li class="active">Error 500</li>
			</ul>
			<!-- .breadcrumb -->

			<div class="nav-search" id="nav-search">
				<form class="form-search">
					<span class="input-icon"> <input placeholder="Search ..."
						class="nav-search-input" id="nav-search-input" autocomplete="off"
						type="text"> <i class="icon-search nav-search-icon"></i>
					</span>
				</form>
			</div>
			<!-- #nav-search -->
		</div>

		<div class="page-content">
			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->

					<div class="error-container">
						<div class="well">
							<h1 class="grey lighter smaller">
								<span class="blue bigger-125"> <i class="icon-random"></i>
									Authentication Failed
								</span> You haven't enough authority
							</h1>

							<hr>
							<h3 class="lighter smaller">
								<i
									class="icon-wrench icon-animated-wrench bigger-125"></i> Please to contact with us!
							</h3>

							<div class="space"></div>

							<div>
								<h4 class="lighter smaller">Try one of the
									following:</h4>

								<ul class="list-unstyled spaced inline bigger-110 margin-15">
									<li><i class="icon-hand-right blue"></i> Apply the Authority</li>

									<li><i class="icon-hand-right blue"></i> Let someone has enough Authority do it</li>
								</ul>
							</div>

							<hr>
							<div class="space"></div>

							<div class="center">
								<button  class="btn btn-grey" type="button" onclick="javascript:window.history.go(-1)"> <i class="icon-arrow-left"></i>
									Go Back
								</button> <a href="#" class="btn btn-primary"> <i
									class="icon-dashboard"></i> Dashboard
								</a>
							</div>
						</div>
					</div>

					<!-- PAGE CONTENT ENDS -->
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.page-content -->
	</div>
</body>
</html>
