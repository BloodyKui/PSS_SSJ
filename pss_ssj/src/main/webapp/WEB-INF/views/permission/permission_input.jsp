<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>权限编辑</title>
<%@ include file="/WEB-INF/views/common/head.jsp"%>

<script type="text/javascript" src="/assets/validator/js/bootstrapValidator.js"></script>
<!-- 引入国际化 -->
<script type="text/javascript" src="/assets/validator/js/language/zh_CN.js"></script>
<!-- 引入自己写的js文件，这个文件必须要在bootstrapValidator.js下面 -->
<script type="text/javascript" src="/js/model/permission.js"></script>

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
					<s:form class="form-horizontal" action="permission_save" id="domainForm">
						<!-- 设置隐藏域用于存储id,根据id是否为空判断是保存还是修改操作 -->
						<s:hidden name="id" />
						<!-- 隐藏域来保存传过来的值，防止重定向的时候被丢失 -->
						<s:hidden name="baseQuery.currentPage" />
						<s:hidden name="baseQuery.pageSize" />
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 权限 </label>

							<div class="col-sm-9">
								<s:textfield class="clearAll" name="name" />
								<!-- 显示错误信息 -->
								<s:property value="fieldErrors['nameIsNull'][0]" />
							</div>
						</div>

					
<!-- 						<div class="form-group"> -->
<!-- 							<label class="col-sm-3 control-label no-padding-right" -->
<!-- 								for="form-field-2"> 部&nbsp;门 </label> -->

<!-- 							<div class="col-sm-9"> -->
<%-- 								<s:select id="depts" name="department.id" list="#allDept" --%>
<%-- 									listKey="id" listValue="name" headerKey="-1" --%>
<%-- 									headerValue="--请选择--" /> --%>
<!-- 							</div> -->
<!-- 						</div> -->

						<div class="space-4"></div>

						<div class="clearfix form-actions">
							<div class="col-md-offset-3 col-md-9">
							<!-- bootstrap中不支持默认type，所以需要写出来 -->
								<button type="submit" class="btn btn-info">
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
								<button class="btn" id="cancelBtn" type="button"> <i class="icon-undo bigger-110"></i>
									取消
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
