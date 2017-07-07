<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>purchaseBillItem管理</title>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<%-- 引入日期控件的js --%>
<script language="javascript" type="text/javascript"
	src="/js/My97DatePicker/WdatePicker.js"></script>
<script src="/js/Highcharts-4.0.4/js/highcharts.js"></script>
<script src="/js/Highcharts-4.0.4/js/highcharts-3d.js"></script>
<script src="/js/Highcharts-4.0.4/js/modules/exporting.js"></script>
<script type="text/javascript">
	$(function() {
		$("a[data-url]").click(function() {
			//拿到请求的地址
			var url = $(this).data("url");
			//将表单序列化，方便提交参数	
			var param = $("#domainForm").serialize();
			$("#chartmodal .modal-body").load(url,param);
			$("#chartmodal").modal();
		});
	});
</script>
</head>

<body>
	<!-- 设置domainForm 然后使高级查询和分页连接起来 -->
	<!-- s：from表单 自动添加action后缀 -->
	<s:form action="purchaseBillItem" id="domainForm">
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
					<%-- 交易时间，状态分类，采购员分类-供应商-月份分类 --%>
					从
					<!-- 时间回显时格式不正确：需要设置struts标签s:date进行重新格式化生成一个变量，然后再调用就可以 -->
					<s:date name="baseQuery.fromDate" format="yyyy-MM-dd"
						var="beginDate" />
					<s:textfield style="height:28px" name="baseQuery.fromDate"
						value="%{beginDate}" placeholder="起始时间" class="Wdate"
						onClick="WdatePicker()" size="12" />
					至
					<s:date name="baseQuery.toDate" format="yyyy-MM-dd" var="endDate" />
					<s:textfield style="height:28px" name="baseQuery.toDate"
						value="%{endDate}" placeholder="结束时间" class="Wdate"
						onClick="WdatePicker()" size="12" />
					状态
					<s:select list="#{1:'已审', 0:'待审', -1:'作废'}" name="baseQuery.status"
						headerKey="-2" headerValue="--所有--" onchange="goPage(1)"></s:select>
					分组
					<s:select
						list="#{'item.purchaseBill.buyer.username':'采购员', 'item.purchaseBill.supplier.name':'供应商', 'MONTH(item.purchaseBill.vdate)':'月份'}"
						name="baseQuery.groupBy" onchange="goPage(1)"></s:select>
					<!-- 搜索按钮 -->
					<!-- <button class="ui-icon icon-search orange" /> -->
					<!-- button 默认type为submit 
						为了防止每次修改查询条件后，没有从第一页开始，
						所以修改button的type，让其不能自动提交，改为手动提交
						并跳转到第一页
					-->
					<%-- 供应商的查询 --%>
					<%-- 供应商<s:select list="#allSupplier" name="baseQuery.supplierId" listKey="id" listValue="name" headerKey="-1" headerValue="--所有--" onchange="goPage(1)"></s:select> --%>
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
					<div class="btn-group">
						<button data-toggle="dropdown"
							class="btn btn-primary dropdown-toggle">
							饼图 <i class="icon-angle-down icon-on-right"></i>
						</button>

						<ul class="dropdown-menu">
							<li><a data-url="purchaseBillItem_chart2d.action">2d饼图</a></li>

							<li><a data-url="purchaseBillItem_chart3d.action">3d饼图</a></li>

							<li><a href="#">Something else here</a></li>

							<li class="divider"></li>

							<li><a href="#">Separated link</a></li>
						</ul>
					</div>
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
												<th>明细编号</th>
												<th>供应商名称</th>
												<th>采购员</th>
												<th>产品名称</th>
												<th>交易时间</th>
												<th>采购数量</th>
												<th>采购价格</th>
												<th>小计</th>
												<th>产品类别</th>
												<th>状态</th>
											</tr>
										</thead>

										<tbody id="eachPage">
											<%-- 
											因为循环的list中是object[]数组，在栈顶中找不到，
											所以需要将数组放入map栈中(s:iterator value="集合<数组>" var="数组放入map栈中,这里就是key")
											即可通过map栈拿到数据。
										 --%>
											<s:iterator value="groups" var="eachGroup">
												<tr>
													<td colspan="10">${eachGroup[0]},共${eachGroup[1]}条</td>
												</tr>
												<%-- 
													怎样在循环出一个分组时继续循环出该分组下的所有明细？
													方式一：后台使用list进行顺序的重新组装，在放到前台来；
													方式二：OGNL表达式可以直接调用方法([对象，action可不写对象].方法名()调用);所以在action中直接写一个方法，进行明细的获取，同时将条件参数传递回后台。
												--%>
												<s:iterator value="findItemByGroup(#eachGroup[0])">
													<tr>
														<td>${id}</td>
														<td>${purchaseBill.supplier.name}</td>
														<td>${purchaseBill.buyer.username}</td>
														<td>${product.name}</td>
														<td>${purchaseBill.vdate}</td>
														<td>${num}</td>
														<td>${price}</td>
														<td>${amount}</td>
														<td>${product.parentType.name}</td>
														<td style="text-align: center;"><s:if
																test="purchaseBill.status==0">
																<%-- 0-待审；1-审核通过； -1-作废 --%>
																<span class="label label-warning">待审</span>
															</s:if> <s:elseif test="purchaseBill.status==-1">
																<span class="label label-danger arrowed-in">作废</span>
															</s:elseif> <s:else>
																<span class="label label-success arrowed">已审</span>
															</s:else></td>
													</tr>
												</s:iterator>
												<%--
													总计的计算方式：方式一：页面加载完后使用js计算(循环行，累加)；
													方式二：使用s:set标签设置一个变量(该变量在map栈中)，每次循环进行一次累加；
													方式三：在查询分组信息的jqpl中，就进行总计的计算；
												 --%>
												<tr>
													<th colspan="5">总计：</th>
													<th>${eachGroup[2]}</th>
													<th></th>
													<th>${eachGroup[3]}</th>
													<th></th>
													<th></th>
												</tr>
											</s:iterator>
										</tbody>
									</table>
									<!-- /.table-responsive -->
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 引入模态框，自定义弹窗效果 -->
		<div id="chartmodal" class="modal">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title">饼图</h4>
					</div>
					<div class="modal-body"></div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
	</s:form>

</body>
</html>
