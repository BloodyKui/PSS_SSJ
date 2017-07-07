function certifyForm(){
	// TODO 验证字段
}



$(function() {
	$('#domainForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			name : {
				validators : {
					notEmpty : true
				}
			},
			costPrice : {
				validators : {
					notEmpty : true,
					numeric : true
				// 数值
				}
			},
			salePrice : {
				validators : {
					notEmpty : true,
					numeric : true
				}
			},
			"types.id" : {// 二级类型
				validators : {
					notEmpty : true
				}
			},
			"types.parent.id" : {// 一级类型
				validators : {
					different : {
						field : "types.id",
						message : '必须选择正确产品类型'
					}
				}
			},
			upload : {
				validators : {
					file : {
						extension : 'gif,png,jpg,jpeg',
						type : 'image/gif,image/png,image/jpeg',
						maxSize : 5 * 1024 * 1024, // 5 MB
						message : '必须上传图片而且图片不能超过5MB'
					}
				}
			}
		}
	});
	// 处理图片上传控件
	$('#id-input-file-2').ace_file_input({
		no_file : '选择图片 ...',
		btn_choose : '选择',
		btn_change : '改变',
		droppable : false,
		onchange : null,
		thumbnail : false
	});

	// 二级联动
	$("#parentType").on(
			"change",
			function() {
				// 先将子类型的下拉框清空
				$("#childrenType").empty();
				// 获取该父类型下拉框的值
				var parent_id = $("#parentType").val();
				// 如果父类型的id值为-1，则让子类型的下拉框变为请选择
				if (parent_id == -1) {
					$("#childrenType").html(
							"<option value='-1'>--请选择--</option>");
				} else {
					// 使用缓存 新增用法 data(obj) 可传入key-value形式的数据。
					var cacheRuesult = $("#childrenType").data(parent_id);
					//如果有缓存。则直接使用
					if (cacheRuesult) {
						for (var i = 0; i < $(cacheRuesult).size(); i++) {
							var id = cacheRuesult[i].id
							var name = cacheRuesult[i].name
							$("#childrenType").append("<option value='" + id + "'>" + name + "</option>");
						}
					} else {
						//如果没有缓存，则去后台获取
						// 如果父类型有选择，则通过ajax去后台获取
						$.get("/product_findChildren.action", {id : parent_id}, function(result) {
							//获取过后将结果放入缓存中
							$("#childrenType").data(parent_id,result);
							console.debug($(result).size());
							for (var i = 0; i < $(result).size(); i++) {
								var id = result[i].id
								var name = result[i].name
								$("#childrenType").append("<option value='" + id + "'>" + name + "</option>");
							}
						})
					}
				}
			});

});