<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>导入xlsx</title>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<link rel="stylesheet"
	href="assets/plugin/validator/css/bootstrapValidator.css" />
<script type="text/javascript"
	src="assets/plugin/validator/js/bootstrapValidator.js"></script>
<script type="text/javascript"
	src="assets/plugin/validator/js/language/zh_CN.js"></script>
<script src="assets/js/ace-elements.min.js"></script>
<script src="assets/js/jquery.maskedinput.min.js"></script>
<script type="text/javascript">
	$(function() {
		$('#importForm')
				.bootstrapValidator(
						{
							feedbackIcons : {
								valid : 'glyphicon glyphicon-ok',
								invalid : 'glyphicon glyphicon-remove',
								validating : 'glyphicon glyphicon-refresh'
							},
							fields : {
								upload : {
									validators : {
										file : {
											extension : 'xlsx',
											type : 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
											maxSize : 2 * 1024 * 1024, // 2 MB
											message : '必须上传xlsx文件而且大小不能超过2MB'
										}
									}
								}
							}
						});

	});
</script>
</head>
<body>
	<div class="main-content">
		<div class="breadcrumbs" id="breadcrumbs">
			<ul class="breadcrumb">
				<li><i class="icon-home home-icon"></i> <a href="/"
					target="_top">主页</a></li>
				<li><i class="icon-list"></i><a href="#">系统管理</a></li>
				<li class="active">导入xlsx</li>
			</ul>
			<!-- .breadcrumb -->
			<!-- #nav-search -->
		</div>
		<div class="page-content">
			<div class="page-header">
				<h1>
					${importResult} <small> <i class="icon-double-angle-right"></i>
						<s:actionerror style="color:blue" />
					</small>
				</h1>
			</div>
			<!-- /.page-header -->
			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->
					<s:form id="importForm" action="import"
						enctype="multipart/form-data" cssClass="form-horizontal"
						role="form">
						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-4">xlsx</label>
							<div class="col-sm-9" style="width: 35%">
								<input id="id-input-file-2" name="upload" type="file"> <label
									class="file-label" data-title="Choose"> <span
									class="file-name" data-title="No File ..."> </span>
								</label>
							</div>
						</div>
						<div class="form-group" align="center">
							<div class="col-lg-9 col-lg-offset-3">
								<button class="btn btn-primary" type="submit">
									<i class="icon-cloud-upload bigger-110"></i> 导入
								</button>
							</div>
						</div>
					</s:form>
				</div>
			</div>
		</div>
	</div>
	<!-- /.main-container-inner -->
	<!-- /.main-container-inner -->
	<script type="text/javascript">
		$(function() {
			//处理图片上传控件
			$('#id-input-file-2').ace_file_input({
				no_file : '选择xlsx文件 ...',
				btn_choose : '选择',
				btn_change : '改变',
				droppable : false,
				onchange : null,
				thumbnail : false
			});
		});
	</script>
</body>
</html>
