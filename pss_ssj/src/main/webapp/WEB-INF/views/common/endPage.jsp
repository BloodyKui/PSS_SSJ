<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row">
	<div class="col-sm-6">
		<ul class="pagination">

			<div class='dataTables_info' id='sample-table-2_info'>显示第
				${pageList.begin} 条 到-第<span id="endItem">${pageList.quantity}</span>条 共 <span id="totalItem">${pageList.totalCount}</span>条</div>

		</ul>
		每页显示
		<!-- 选择完每页显示的条数以后应该立即刷新页面。所以用onchange，同时应该从第一页重新开始显示，所以是goPage(1) -->
		<s:select list="{5,10,20,30,40}" name="baseQuery.pageSize"
			onchange="goPage(1)" />
		条数&nbsp;&nbsp; 第
		<s:textfield name="baseQuery.currentPage"
			value="%{pageList.currentPage}" id="currentPage" size="1"></s:textfield>
		页
		<!-- 设置一个keyup事件，用于监听 -->
		<!-- 另外使用已经写好的 -->
<!-- 		<script type="text/javascript">
// 			$("#currentPage").on("keyup", function() {
// 				var value = $("#currentPage").val();
// 				//value = value.substring(0,value.length-1);
// 				value = value.replace(/\D/, "");
// 				console.debug(value);
// 				$("#currentPage").val(value);
// 			});
 		</script> -->
		<!-- <button type="submit" style="display: none"></button> -->
		<input type="submit" value="跳转" />

	</div>
	<div class="col-sm-6">
		<div class="dataTables_paginate paging_bootstrap">
			<ul class="pagination">${pageList.page}
			</ul>
		</div>
	</div>
</div>