<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />

<%@ include file="/WEB-INF/views/common/head.jsp"%>

<script type="text/javascript"
	src="/assets/validator/js/bootstrapValidator.js"></script>
<!-- 引入国际化 -->
<script type="text/javascript"
	src="/assets/validator/js/language/zh_CN.js"></script>
<!-- 引入自己写的js文件，这个文件必须要在bootstrapValidator.js下面 -->
<script type="text/javascript" src="/js/model/product.js"></script>
<script src="assets/js/ace-elements.min.js"></script>
<title>product</title>
</head>

<body>
	<s:fielderror></s:fielderror>
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
				<li class="active">Form Elements</li>
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
			<div class="page-header">
				<h1>
					Form Elements <small> <i class="icon-double-angle-right"></i>
						Common form elements and layouts
					</small>
				</h1>
			</div>
			<!-- /.page-header -->

			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->

					<!-- <form class="form-horizontal" role="form"> -->
					<!-- s的form表单action中不能加'/'否则不能访问，非要使用'/'需要在namespace属性中添加'/' -->
					<s:form class="form-horizontal" action="product_save"
						id="domainForm" enctype="multipart/form-data">
						<!-- 设置隐藏域用于存储id,根据id是否为空判断是保存还是修改操作 -->
						<s:hidden name="id" />
						<!-- 隐藏域来保存传过来的值，防止重定向的时候被丢失 -->
						<s:hidden name="baseQuery.currentPage" />
						<s:hidden name="baseQuery.pageSize" />
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 名称 </label>

							<div class="col-sm-9">
								<s:textfield class="clearAll" name="name" placeholder="名称" />
								<!-- 显示错误信息 -->
								<s:property value="fieldErrors['nameIsNull'][0]" />
							</div>
						</div>

						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-input-readonly">颜色</label>
							<div class="col-sm-9">
								<s:select id="simple-colorpicker-1" name="color"
									list="{'#ac725e','#d06b64','#f83a22','#fa573c','#ff7537','#ffad46','#42d692','#16a765','#7bd148','#b3dc6c','#fbe983','#fad165','#92e1c0','#9fe1e7','#9fc6e7','#4986e7','#9a9cff','#b99aff','#c2c2c2','#cabdbf','#cca6ac','#f691b2','#cd74e6','#a47ae2','#555'}" />
							</div>
							<script>
								$(function() {
									//处理颜色选择框
									$('#simple-colorpicker-1')
											.ace_colorpicker();
								});
							</script>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 成本价格 </label>

							<div class="col-sm-9">
								<s:textfield class="clearAll" name="costPrice" placeholder="成本价格"/>
								<!-- 显示错误信息 -->
								<s:property value="fieldErrors['nameIsNull'][0]" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 销售价格 </label>

							<div class="col-sm-9">
								<s:textfield class="clearAll" name="salePrice" placeholder="销售价格"/>
								<!-- 显示错误信息 -->
								<s:property value="fieldErrors['nameIsNull'][0]" />
							</div>
						</div>


						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-4">品牌</label>
							<div class="col-sm-9">
								<s:select list="#allBrands" name="brand.id" listKey="id"
									listValue="name" />
							</div>
						</div>
						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-4">单位</label>
							<div class="col-sm-9">
								<s:select list="#allUnits" name="unit.id" listKey="id"
									listValue="name" />
							</div>
						</div>
						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-4">产品类型</label>
							<!--<div class="col-sm-9"> -->
							<%-- 	<s:select list="#allTypes" name="productTypeParent.id" listValue="name" listKey="id" /> --%>
							<!-- </div> -->
							<div class="col-sm-9">
								<!-- headerKey="-1" headerValue="--请选择--" 必须选类型 -->
								<s:select id="parentType" list="#allParents" name="parentType.productTypeParent.id" listValue="name" listKey="id"/>
								<s:select id="childrenType" list="#allChildren" name="parentType.id" listValue="name" listKey="id" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-4">图片</label>
							<div class="col-sm-9" style="width: 35%">
								<s:file id="id-input-file-2" name="upload" ></s:file> <label
									class="file-label" data-title="Choose"> <span
									class="file-name" data-title="No File ..."> </span>
								</label>
							</div>
						</div>


						<div class="space-4"></div>

						<div class="clearfix form-actions">
							<div class="col-md-offset-3 col-md-9">
								<!-- bootstrap中不支持默认type，所以需要写出来 -->
								<!-- 提交时先判断验证字段，在进行提交 -->
								<button type="button" class="btn btn-info" onclick="certifyForm()">
									<i class="icon-ok bigger-110"></i> 提交
								</button>

								&nbsp; &nbsp; &nbsp;
								<s:if test="id==null">
									<button class="btn" type="reset">
										<i class="icon-undo bigger-110"></i> 重置
									</button>
								</s:if>
								<s:else>
									<!-- 									<button class="btn" type="button" onclick="clearAll()"> -->
									<!-- 										<i class="icon-undo bigger-110"></i> 重置 -->
									<!-- 									</button> -->
									<button class="btn" type="button" id="resetBtn_edit">
										<i class="icon-undo bigger-110"></i> 重置
									</button>
								</s:else>

								&nbsp; &nbsp; &nbsp;
								<!-- TODO 取消时也应返回到之前跳转过来的对应页 -->
								<button class="btn" id="cancelBtn" type="button">
									<i class="icon-undo bigger-110"></i> 取消
								</button>
							</div>
						</div>
					</s:form>
					<!-- </form> -->
				</div>
			</div>
		</div>
	</div>
</body>
</html>
