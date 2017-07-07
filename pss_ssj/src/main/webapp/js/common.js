// 下载文件时调用，将查询条件传递到后台
function downloadFile(url) {
	// 修改当前form表单的action属性
	$("#domainForm").attr("action", url + "_downloadExcel.action");
	// 提交action
	$("#domainForm").submit();
	// 因为下载后仍停留在查看界面，为了不影响其他操作，所以将表单的action属性改回原来的
	//因为此时修改时，已经被翻译为普通的form表单了，所以必须手动添加后缀
	$("#domainForm").attr("action", url+".action");
};

// 使用form表单提交形式来传递参数到指定url
function transferParams(url, id) {
	// 修改当前form表单的action属性
	$("#domainForm").attr("action", url + "?id=" + id);
	// 提交action
	$("#domainForm").submit();
};

// 分页跳转
function goPage(pageNo) {
	// 使当前页和查询条件一块被提交到后台
	$("#currentPage").val(pageNo);
	// window.location.href = "/employee?baseQuery.currentPage="+ pageNo;
	$("#domainForm").submit();
}

// 删除
function deleteOne(url, id, btn) {
	// 使用ajax进行删除
	url = url + "?id=" + id;
	confirm("你确定要删除吗?");
	$.get(url, function(result) {
		/*
		 * 使用了访问权限以后，当没有权限进行删除操作时，返回的提示不那么友好
		 * 因为后台直接访问了delete方法返回的一定是一个json对象
		 * 如果没有访问到delete方法那么返回的肯定不会json对象，
		 * 所以可以通过这个来修改当没有访问权限时页面显示的信息
		 */
		if (result instanceof Object) {
			if (result.ok) {
				if ($("#eachPage tr").size() == 1) {
					//如果当前页只剩下一条，那么就直接刷新页面
					$("#domainForm").submit();
				} else {
					// 删除成功后每次都要去刷新整个页面。很耗性能，所以前端通过直接删除指定行来达到目的
					// $("#domainForm").submit();
					// 因为没有使用jQuery的监听事件方式，所以this指代的是window对象而非button对象
					// 需要外界将当前点击的button对象传入
					// console.debug(this);
					$(btn).closest("tr").remove();
					// 修改左下分页条
					$("#endItem").html($("#endItem").html() - 1);
					$("#totalItem").html($("#totalItem").html() - 1);
				}
			} else {
				// 输出错误信息
				$(".modal-body").html("错误原因："+"<p style='color:red;'>"+result.msg+"</p>");
				$('.modal').modal();
			}
		} else {
			$(".modal-body").html("失败原因："+"<p style='color:red;'>对不起，权限不足，请联系管理员zhaoyi@itsource.cn</p>");
			$('.modal').modal();
		}
	});
}

$(function(){
	//1.在查询页面的左下角输入指定页的时候,只要用户输入非数字,就把非数字退掉，暂时不能去掉使用右键粘贴的非数字
	$("#currentPage").keyup(function(){  //keyup事件处理 
	        $(this).val($(this).val().replace(/\D|^0/g,''));  
	}).bind("paste",function(){  //CTR+V事件处理 
	        $(this).val($(this).val().replace(/\D|^0/g,''));  
	}).css("ime-mode", "disabled");  //CSS设置输入法不可用
	
	//2.修改的时候取消不保存
	$("#cancelBtn").click(function(){
//		window.history.go(-1);
		window.history.back();//获取浏览器缓存对象
		//location.href="employee.action";//很多人同时管理数据使用此方法，每次获取实时数据
	});
	
	//3.实现重置的功能,新建和修改
	$("#resetBtn_edit").click(function(){
		//s标签在浏览器中最终会被翻译为html,而s:textfield会被翻译为input标签
		$("input[name!='id'][name!='baseQuery.currentPage'][name!='baseQuery.pageSize']").val("");
		$("select").val(-1);
	});

});
