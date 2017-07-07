<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>department管理</title>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
</head>

<body>
	<!-- 设置domainForm 然后使高级查询和分页连接起来 -->
	<!-- s：from表单 自动添加action后缀 -->
	<s:form action="department" id="domainForm">
		<s:debug></s:debug>
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

					<li><a href="#">Tables</a></li>
					<li class="active">Simple &amp; Dynamic</li>
				</ul>
				<!-- .breadcrumb -->

			</div>

			<div class="page-content">
				<div class="page-header">
					部门名称
					<s:textfield name="baseQuery.name" placeholder="Search ..."
						class="nav-search-input" id="nav-search-input" autocomplete="off" />
					<!-- 搜索按钮 -->
					<!-- <button class="ui-icon icon-search orange" /> -->
					<!-- button 默认type为submit 
						为了防止每次修改查询条件后，没有从第一页开始，
						所以修改button的type，让其不能自动提交，改为手动提交
						并跳转到第一页
					-->
					<button type="button" class="btn btn-warning btn-xs"
						onclick="goPage(1)">
						<i class="icon-search  bigger-110 icon-only"></i>
					</button>
					<!-- 新增数据按钮 -->
					<a href="/department_input.action" class="btn"> <i
						class="icon-pencil align-top"></i> 添加部门
					</a>
				</div>
				<!-- /.page-header -->

				<div class="row">
					<div class="col-xs-12">
						<!-- PAGE CONTENT BEGINS -->

						<div class="row">
							<div class="col-xs-12">
								<div class="table-responsive">
									<table id="sample-table-1"
										class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th class="center"><label> <input
														type="checkbox" class="ace" /> <span class="lbl"></span>
												</label>
												</th>
												<th>编号</th>
												<th>department</th>
												<th></th>
											</tr>
										</thead>

										<tbody id="eachPage">
											<s:iterator value="pageList.results">
												<!-- get方法的值会放入到parameters数组中，所以通过这个拿到id来判断如果id相同就说明
														 是刚刚添加的，所以将它标记
													 -->
												<tr
													<s:if test="id==#parameters.id[0]">
														style="color:deeppink;"
													</s:if>>
													<td class="center"><label> <input
															type="checkbox" class="ace" /> <span class="lbl"></span>
													</label></td>

													<td>${id}</td>
													<td>${name}</td>

													<td>
														<div
															class="visible-md visible-lg hidden-sm hidden-xs btn-group">
															<!-- 编辑时为了保证编辑过后留在当前页，需要传递的参数比较多
																 如果用url拼接会比较麻烦。所以采用提交表单的形式，且在提交之前将表单的action修改为
																 提交至编辑页面，然后通过隐藏域，在配置在struts.xml中，从而两次重定向后仍然能得到之前
																 的参数
															 -->
															<%-- <a href="/department_input.action?id=${id}&baseQuery.currentPage=${baseQuery.currentPage}" --%>
															<!-- class="btn btn-xs btn-info"> <i -->
															<!-- class="icon-edit bigger-120"></i> -->
															<!-- </a>  -->
															<button type="button"
																onclick="transferParams('/department_input.action', '${id}')"
																class="btn btn-xs btn-info">
																<i class="icon-edit bigger-120"></i>
															</button>
															<button type="button"
																onclick="deleteOne('/department_delete.action', ${id},this)"
																class="btn btn-xs btn-danger">
																<i class="icon-trash bigger-120"></i>
															</button>
														</div>

														<div class="visible-xs visible-sm hidden-md hidden-lg">
															<div class="inline position-relative">
																<button
																	class="btn btn-minier btn-primary dropdown-toggle"
																	data-toggle="dropdown">
																	<i class="icon-cog icon-only bigger-110"></i>
																</button>

																<ul
																	class="dropdown-menu dropdown-only-icon dropdown-yellow pull-right dropdown-caret dropdown-close">
																	<li><a href="#" class="tooltip-info"
																		data-rel="tooltip" title="View"> <span
																			class="blue"> <i
																				class="icon-zoom-in bigger-120"></i>
																		</span>
																	</a></li>

																	<li><a href="#" class="tooltip-success"
																		data-rel="tooltip" title="Edit"> <span
																			class="green"> <i class="icon-edit bigger-120"></i>
																		</span>
																	</a></li>

																	<li><a href="#" class="tooltip-error"
																		data-rel="tooltip" title="Delete"> <span
																			class="red"> <i class="icon-trash bigger-120"></i>
																		</span>
																	</a></li>
																</ul>
															</div>
														</div>
													</td>
												</tr>
											</s:iterator>
										</tbody>
									</table>
									<!-- /.table-responsive -->
									<!-- 对分页条进行抽取 -->
									<%@ include file="/WEB-INF/views/common/endPage.jsp"%>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 引入模态框，自定义弹窗效果 -->
		<div class="modal">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title">删除失败</h4>
					</div>
					<div class="modal-body">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
	</s:form>
</body>
</html>
