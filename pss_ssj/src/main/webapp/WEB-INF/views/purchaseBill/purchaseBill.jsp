<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>purchaseBill管理</title>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<%-- 引入日期控件的js --%>
<script language="javascript" type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
</head>

<body>
<s:fielderror></s:fielderror>
	<!-- 设置domainForm 然后使高级查询和分页连接起来 -->
	<!-- s：from表单 自动添加action后缀 -->
	<s:form action="purchaseBill" id="domainForm">
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
					从
					<!-- 时间回显时格式不正确：需要设置struts标签s:date进行重新格式化生成一个变量，然后再调用就可以 -->
					<s:date name="baseQuery.fromDate" format="yyyy-MM-dd" var="beginDate"/>
					<s:textfield style="height:28px" name="baseQuery.fromDate" value="%{beginDate}" placeholder="起始时间" class="Wdate" onClick="WdatePicker()" size="12"/>
					至
					<s:date name="baseQuery.toDate" format="yyyy-MM-dd" var="endDate"/>
					<s:textfield style="height:28px" name="baseQuery.toDate" value="%{endDate}" placeholder="结束时间" class="Wdate" onClick="WdatePicker()" size="12"/>
					<!-- 搜索按钮 -->
					<!-- <button class="ui-icon icon-search orange" /> -->
					<!-- button 默认type为submit 
						为了防止每次修改查询条件后，没有从第一页开始，
						所以修改button的type，让其不能自动提交，改为手动提交
						并跳转到第一页
					-->
					<%-- 供应商的查询 --%>
					供应商<s:select list="#allSupplier" name="baseQuery.supplierId" listKey="id" listValue="name" headerKey="-1" headerValue="--所有--" onchange="goPage(1)"></s:select>
					<%-- 状态的查询 --%>
					状态<s:select list="#{1:'已审', 0:'待审', -1:'作废'}" name="baseQuery.status" headerKey="-2" headerValue="--所有--" onchange="goPage(1)"></s:select>
					<button type="button" class="btn btn-warning btn-xs"
						onclick="goPage(1)">
						<i class="icon-search  bigger-110 icon-only"></i>
					</button>
					<!-- 新增数据按钮 -->
					<a href="/purchaseBill_input.action" class="btn"> <i
						class="icon-pencil align-top"></i> 新建
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
												<!-- TODO 页面展示 -->
												<td>交易时间</td>
												<td>总数量</td>
												<td>总金额</td>
												<td>供应商</td>
												<td>状态</td>
												<td>操作</td>
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

													<td>${vdate}</td>
													<td>${totalAmount}</td>
													<td>${totalNum}</td>
													<td>${supplier.name}</td>
													<td style="text-align: center;">
														<s:if test="status==0">
															<%-- 0-待审；1-审核通过； -1-作废 --%>
															<span class="label label-warning">待审</span>
														</s:if>
														<s:elseif test="status==-1">
															<span class="label label-danger arrowed-in">作废</span>
														</s:elseif>
														<s:else>
															<span class="label label-success arrowed">已审</span>
														</s:else>
													</td>

													<td>
														<div
															class="visible-md visible-lg hidden-sm hidden-xs btn-group">
															<!-- 编辑时为了保证编辑过后留在当前页，需要传递的参数比较多
																 如果用url拼接会比较麻烦。所以采用提交表单的形式，且在提交之前将表单的action修改为
																 提交至编辑页面，然后通过隐藏域，在配置在struts.xml中，从而两次重定向后仍然能得到之前
																 的参数
															 -->
															<%-- <a href="/purchaseBill_input.action?id=${id}&baseQuery.currentPage=${baseQuery.currentPage}" --%>
															<!-- class="btn btn-xs btn-info"> <i -->
															<!-- class="icon-edit bigger-120"></i> -->
															<!-- </a>  -->
															<button type="button"
																onclick="transferParams('/purchaseBill_input.action', '${id}')"
																class="btn btn-xs btn-info">
																<i class="icon-edit bigger-120"></i>
															</button>
															<button type="button"
																onclick="deleteOne('/purchaseBill_delete.action', ${id},this)"
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
