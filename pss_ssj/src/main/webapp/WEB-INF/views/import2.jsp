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
			<script type="text/javascript">
				try {
					ace.settings.check('breadcrumbs', 'fixed')
				} catch (e) {
				}
			</script>

			<ul class="breadcrumb">
				<li><i class="icon-home home-icon"></i> <a href="#">Home</a></li>

				<li><a href="#">Forms</a></li>
				<li class="active">Dropzone File Upload</li>
			</ul>
			<!-- .breadcrumb -->

		</div>

		<div class="page-content">
			<div class="page-header">
				<h1>
					Dropzone.js <small> <i class="icon-double-angle-right"></i>
						Drag &amp; drop file upload with image preview
					</small>
				</h1>
			</div>
			<!-- /.page-header -->

			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->

					<div class="alert alert-info">
						<i class="icon-hand-right"></i> ${importResult}
						<button class="close" data-dismiss="alert">
							<i class="icon-remove"></i>
						</button>
					</div>

					<div id="dropzone">
						<s:form id="importForm" action="import"
							enctype="multipart/form-data" class="dropzone dz-clickable"
							role="form">
							<div class="dz-default dz-message">
								<span><span class="bigger-150 bolder"><i
										class="icon-caret-right red"></i> Drop files</span> to upload <span
									class="smaller-80 grey">(or click)</span> <br> <i
									class="upload-icon icon-cloud-upload blue icon-3x"></i></span>
							</div>
						</s:form>
					</div>
					<!-- PAGE CONTENT ENDS -->
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.page-content -->
	</div>
	<!-- inline scripts related to this page -->
	<script src="assets/js/dropzone.min.js"></script>
	<script type="text/javascript">
		jQuery(function($) {

			try {
				$(".dropzone")
						.dropzone(
								{
									paramName : "file", // The name that will be used to transfer the file
									maxFilesize : 0.5, // MB

									addRemoveLinks : true,
									dictDefaultMessage : '<span class="bigger-150 bolder"><i class="icon-caret-right red"></i> Drop files</span> to upload \
				<span class="smaller-80 grey">(or click)</span> <br /> \
				<i class="upload-icon icon-cloud-upload blue icon-3x"></i>',
									dictResponseError : 'Error while uploading file!',

									//change the previewTemplate to use Bootstrap progress bars
									previewTemplate : "<div class=\"dz-preview dz-file-preview\">\n  <div class=\"dz-details\">\n    <div class=\"dz-filename\"><span data-dz-name></span></div>\n    <div class=\"dz-size\" data-dz-size></div>\n    <img data-dz-thumbnail />\n  </div>\n  <div class=\"progress progress-small progress-striped active\"><div class=\"progress-bar progress-bar-success\" data-dz-uploadprogress></div></div>\n  <div class=\"dz-success-mark\"><span></span></div>\n  <div class=\"dz-error-mark\"><span></span></div>\n  <div class=\"dz-error-message\"><span data-dz-errormessage></span></div>\n</div>"
								});
			} catch (e) {
				alert('Dropzone.js does not support older browsers!');
			}

		});
	</script>
</body>
</html>
