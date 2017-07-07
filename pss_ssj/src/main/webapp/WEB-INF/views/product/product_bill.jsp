<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>product管理</title>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
</head>

<body>
	<!-- 设置domainForm 然后使高级查询和分页连接起来 -->
	<!-- s：from表单 自动添加action后缀 -->
	<s:form action="product" id="domainForm">

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
										<th>编号</th>
										<th>产品名称</th>
										<th>颜色</th>
										<th>缩略图</th>
										<th>成本价格</th>
										<th>产品类型</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody id="eachPage">
									<s:iterator value="pageList.results">
										<!-- get方法的值会放入到parameters数组中，所以通过这个拿到id来判断如果id相同就说明
														 是刚刚添加的，所以将它标记
													 -->
										<tr>
											<td>${id}</td>
											<td>${name}</td>
											<td><span class="btn-colorpicker"
												style="background-color:${color};"></span></td>
											<td>
												<%-- <img alt="缩略图" src="${smallPic}" /> --%> <img alt=""
												src="${smallPic}" />
											</td>
											<td>${costPrice}</td>
											<td>${parentType.name}</td>

											<td>
												<div
													class="visible-md visible-lg hidden-sm hidden-xs btn-group">
													<!-- 编辑时为了保证编辑过后留在当前页，需要传递的参数比较多
																 如果用url拼接会比较麻烦。所以采用提交表单的形式，且在提交之前将表单的action修改为
																 提交至编辑页面，然后通过隐藏域，在配置在struts.xml中，从而两次重定向后仍然能得到之前
																 的参数
															 -->
													<%-- <a href="/product_input.action?id=${id}&baseQuery.currentPage=${baseQuery.currentPage}" --%>
													<!-- class="btn btn-xs btn-info"> <i -->
													<!-- class="icon-edit bigger-120"></i> -->
													<!-- </a>  -->
													<button bill="confirmAdd" type="button"
														class="btn btn-xs btn-info">
														<i class="icon-edit bigger-120"></i>
													</button>
												</div>

											</td>
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
	</s:form>
	</script>

</body>
</html>
