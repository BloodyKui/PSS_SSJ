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
<script type="text/javascript" src="/js/model/purchaseBill.js"></script>
<%-- 引入日期控件的js --%>
<script language="javascript" type="text/javascript"
	src="/js/My97DatePicker/WdatePicker.js"></script>

<title>purchaseBill</title>
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
					<s:form class="form-horizontal" action="purchaseBill_save"
						id="domainForm">

						<!-- 隐藏域来保存传过来的值，防止重定向的时候被丢失 -->
						<s:hidden name="baseQuery.currentPage" />
						<s:hidden name="baseQuery.pageSize" />
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 交易时间 </label>

							<div class="col-sm-9">
								<s:date name="vdate" format="yyyy-MM-dd" var="vDate" />
								<s:textfield style="height:28px" name="vdate" value="%{vDate}"
									class="Wdate" onClick="WdatePicker()" size="12" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 供应商 </label>
							<div class="col-sm-9">
								<s:select name="supplier.id" list="#allSupplier" listKey="id"
									listValue="name" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 采购员 </label>
							<div class="col-sm-9">
								<s:select name="buyer.id" list="#allbuyer" listKey="id"
									listValue="username" />
							</div>
						</div>

						<%-- 设置明细的添加表 --%>
						<div class="row">
							<div class="col-xs-12">
								<div class="table-responsive">
									<table id="sample-table-1"
										class="table table-striped table-bordered table-hover">

										<thead>
											<tr>
												<th>产品名称</th>
												<th>产品颜色</th>
												<th>产品图片</th>
												<th>采购价格</th>
												<th>采购数量</th>
												<th>采购小计</th>
												<th>备注</th>
												<th>
													<button bill="addItems" class="btn btn-sm btn-success">
														<i class="icon-ok"></i>添加
													</button>
												</th>
											</tr>
										</thead>

										<tbody id="pageBill">
											<s:if test="id==null">
												<tr>
													<td>
														<!-- 设置隐藏域用于存储id,根据id是否为空判断是保存还是修改操作 --> <s:hidden
															bill="productId" name="purchaseBillItems[0].product.id" />
														<%-- list集合可以通过数组索引的取值方法拿值 --%> <s:textfield
															bill="productName" readonly="true"
															name="purchaseBillItems[0].product.name"></s:textfield> <%-- 点击以后可以弹出一个页面用于选择需要添加的产品 --%>
														<button bill="addProduct"
															class="btn btn-app btn-yellow btn-xs">
															<i class="icon-shopping-cart bigger-160"></i>
														</button>
													</td>
													<%-- 												<span class="btn-colorpicker" --%>
													<%-- 													style="background-color:${purchaseBillItems[0].product.color};"></span> --%>
													<td bill="productColor"></td>
													<!-- <img alt=""
													src="${purchaseBillItems[0].product.smallPic}" /> -->
													<td bill="productImg"></td>
													<td><s:textfield bill="productCostPrice"
															name="purchaseBillItems[0].price" class="form-control" /></td>

													<td><s:textfield name="purchaseBillItems[0].num"
															bill="productNum" class="form-control" /></td>
													<%-- 小计需要在前台实时计算出来 --%>
													<td bill="productAmount"></td>

													<td><s:textfield name="purchaseBillItems[0].descs"
															bill="productDescs" class="form-control" /></td>
													<td>
														<div
															class="visibl|e-md visible-lg hidden-sm hidden-xs btn-group">
															<%--清空该行信息的按钮 --%>
															<button bill="clearOne" class="btn btn-xs btn-success">
																<i class="icon-ok bigger-120"></i>
															</button>
															<%--删除该行信息的按钮 --%>
															<button bill="deleteOne" class="btn btn-xs btn-info">
																<i class="icon-edit bigger-120"></i>
															</button>
														</div>

													</td>

												</tr>
											</s:if>
											<s:else>
												<s:iterator value="purchaseBillItems">
													<tr>
														<td>
															<!-- 设置隐藏域用于存储id,根据id是否为空判断是保存还是修改操作 --> 
															<s:hidden bill="productId" name="product.id" /> 
																<%-- list集合可以通过数组索引的取值方法拿值 --%>
															<s:textfield bill="productName" readonly="true"
																name="product.name"></s:textfield> <%-- 点击以后可以弹出一个页面用于选择需要添加的产品 --%>
															<button bill="addProduct" class="btn btn-app btn-yellow btn-xs">
																<i class="icon-shopping-cart bigger-160"></i>
															</button>
														</td>
														<%-- 												<span class="btn-colorpicker" --%>
														<%-- 													style="background-color:${purchaseBillItems[0].product.color};"></span> --%>
														<td bill="productColor"><span class="btn-colorpicker"
															style="background-color:${product.color};"></span></td>
														<!-- <img alt=""
													src="${purchaseBillItems[0].product.smallPic}" /> -->
														<td bill="productImg"><img alt="" src="${product.smallPic}" /></td>
														<td><s:textfield bill="productCostPrice" name="price"
																class="form-control" /></td>

														<td><s:textfield name="num" bill="productNum"
																class="form-control" /></td>
														<%-- 小计需要在前台实时计算出来 --%>
														<td bill="productAmount">${amount}</td>

														<td><s:textfield name="descs" bill="productDescs"
																class="form-control" /></td>
														<td>
															<div
																class="visibl|e-md visible-lg hidden-sm hidden-xs btn-group">
																<%--清空该行信息的按钮 --%>
																<button bill="clearOne" type="button" class="btn btn-xs btn-success">
																	<i class="icon-ok bigger-120"></i>
																</button>
																<%--删除该行信息的按钮 --%>
																<button type="button" bill="deleteOne" class="btn btn-xs btn-info">
																	<i class="icon-edit bigger-120"></i>
																</button>
															</div>

														</td>
													</tr>
												</s:iterator>
											</s:else>
										</tbody>
									</table>
								</div>
								<!-- /.table-responsive -->
							</div>
							<!-- /span -->
						</div>
						<div class="clearfix form-actions">
							<div class="col-md-offset-3 col-md-9">
								<!-- bootstrap中不支持默认type，所以需要写出来 -->
								<button bill="submitBtn" type="button" class="btn">
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
	<!-- 引入模态框，进行产品明细的添加 -->
	<div id="myBillModal" class="modal" data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">产品列表</h4>
				</div>
				<div class="modal-body"></div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</body>
</html>
