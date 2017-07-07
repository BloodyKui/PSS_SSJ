$(function() {
	$('#domainForm').bootstrapValidator({
		// live: 'disabled',
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			// <s:textfield class="clearAll" name="username" />
			// 与标签中的name属性对应
			name : {
				validators : {
					notEmpty : true
				}

			}

		}
	});

	// 使用模态框显示产品明细
	$("#pageBill").on("click", "button[bill=addProduct]", function() {
		// 是那一行明细被点击了
		var tr = $(this).closest("tr");
		/*
		 * .modal()弹出模态框操作是异步，
		 * 使用shown.bs.modal弹出过后再注册(注册前先清除,jQuery练习中有遇到过这个问题)添加按钮的监听事件，
		 * 否则事件监听会注册失败。
		 */
		// $('#myBillModal').off("show.bs.modal");
		$('#myBillModal').on("shown.bs.modal", function() {
			alert(0);
			// 注册点击就将产品信息添加到明细中的事件
			$("button[bill=confirmAdd]").on("click", function() {
				/*
				 * 点击添加后将产品信息加入明细中： 点击button，获取当前操作的行的对象，然后在产品信息的点击事件中进行操作：
				 * 先找到当前添加按钮所在的tr， 在拿到该tr中所有的td(使用find方法)， 然后依次取出id......
				 * (对标签取值时,表单元素使用val,普通元素使用html())并放入明细的一行中。
				 * 同时关闭产品信息表(jQuery.click()调用点击事件)
				 */
				var tds = $(this).closest("tr").find("td");
				tr.find("input[bill=productId]").val($(tds[0]).html())
				tr.find("input[bill=productName]").val($(tds[1]).html())
				tr.find("td[bill=productColor]").html($(tds[2]).html())
				tr.find("td[bill=productImg]").html($(tds[3]).html())
				tr.find("input[bill=productCostPrice]").val($(tds[4]).html())
				// 调用关闭
				$(".close").click();
			});
		});
		// jQuery.load("载入远程html代码")
		// 单独写一个产品显示页面
		$("#myBillModal .modal-body").load("/product_bill.action");

		$('#myBillModal').modal();
	});
	// 为添加一行明细的按钮设置监听
	$("button[bill=addItems]").click(function() {
		// 先获取写好的tr，然后使用clone方法克隆一个tr,并把里面的所有数据清空，在追加到页面中
		var trClone = $("#pageBill tr:first").clone();
		// 清空数据
		trClone.find("input[bill=productId]").val("");
		trClone.find("input[bill=productName]").val("");
		trClone.find("td[bill=productColor]").html("");
		trClone.find("td[bill=productImg]").html("");
		trClone.find("input[bill=productCostPrice]").val("");
		trClone.find("input[bill=productNum]").val("");
		trClone.find("td[bill=productAmount]").html("");
		trClone.find("input[bill=productDescs]").val("");
		// 追加
		$("#pageBill").append(trClone);
		/*
		 * 当明细添加过多时，但是页面加载时就已经确定了高度，所以内容变化了，
		 * 但是高度没有变。方案一：更改iframe的scrolling属性从而显示滚动条。
		 * 方式二：每次添加明细的时候就调用iframe确定高度的方法，重新定义高度。
		 * 使用(window.parent.autoHeight)去查看父页面中是否有这个方法， 从而调用（知识点：子页面可以调用父页面的方法）。
		 */
		if (window.parent.autoHeight) {
			window.parent.autoHeight();
		}
	});

	/*
	 * 删除一行明细，还是要给整个页面注册事件，否则新增后的按钮无法注册事件; 如果把所有行删除，则不能再新增了，因为新增是依靠clone产生的，
	 * 所以拿到所有tr，如果tr.size()==1则直接结束方法 (还可以添加一个清空该行数据，但不删除该行的清空按钮)
	 */
	$("#pageBill").on("click", "button[bill=deleteOne]", function() {
		if ($("#pageBill").find("tr").size() == 1) {
			return;
		}
		$(this).closest("tr").remove();
	});
	
	//清空一行明细
	$("#pageBill").on("click", "button[bill=clearOne]", function() {
		//找到该行对象
		var tr = $(this).closest("tr");
		//清空该行的所有信息
		tr.find("input[bill=productId]").val("");
		tr.find("input[bill=productName]").val("");
		tr.find("td[bill=productColor]").html("");
		tr.find("td[bill=productImg]").html("");
		tr.find("input[bill=productCostPrice]").val("");
		tr.find("input[bill=productNum]").val("");
		tr.find("td[bill=productAmount]").html("");
		tr.find("input[bill=productDescs]").val("");
	});

	/*
	 * 点击新增后，多个明细合并成一行了，且报错(s:fielderr)，因为使用clone第一行tr时， 里面的回显name会同样拷贝过来；
	 * 解决方式1：每次点击添加后，修改所有的name（不推荐，因为删除过后，中间缺失了item[i]， 所以还需要重新赋值）;
	 * 方式二：每次点击提交时，用循环把名字一个一个改了；获取所有的tr,遍历tr, 然后find找到每一个输入的标签，
	 * 然后修改里面的name属性为items[i].....(因为id才是外键关联，所以可以不用修改产品名称的标签)，
	 * 一共要修改id,价格,数量,备注(产品名称可改可不改)。修改完以后再提交整个表单。
	 */
	$("button[bill=submitBtn]").click(function() {
		//定义一个标记用于出错后，不让提交;同时作为多个错误只报第一个错误的标记
		var submitflag = true;
		
		var trs = $("#pageBill tr");
		console.debug(trs);
		$(trs).each(function(i, ele) {
			$(ele).find("input[bill=productId]").attr("name","purchaseBillItems["+i+"].product.id");
			//名字可以不修改，因为只需要id就可以知道是什么产品了
			$(ele).find("input[bill=productName]").attr("name","purchaseBillItems["+i+"].product.name");
			$(ele).find("input[bill=productCostPrice]").attr("name","purchaseBillItems["+i+"].price");
			$(ele).find("input[bill=productNum]").attr("name","purchaseBillItems["+i+"].num");
			$(ele).find("input[bill=productDescs]").attr("name","purchaseBillItems["+i+"].descs");
			var productId = $(ele).find("input[bill=productId]").val();
			if (productId==null) {
				console.debug("亲，需要添加产品才能提交哦");
				submitflag = false;
			}
			//验证是否输入数量
			var num = $(ele).find("input[bill=productNum]");
			console.debug(num);
			if (submitflag && $(num).val()==null) {
				alert("亲，需要输入产品数量才能提交哦");
				num.focus();
				submitflag = false;
			}
		});
		//循环完成后才能提交！！！！！
		if (submitflag) {
			$('#domainForm').submit();
		}
	});
	
	/*
	 * 计算小计，同时监听两个文本框失去焦点事件(同时是监听整个页面中)，先拿到整行，
	 * 再取去两个文本框中的值(因为不知道是哪个文本框失去了焦点，所以只有这样操作)。
	 * 将空值变为0，然后将结果(使用toFixed(2)保留两位小说)写入到小计中
	 */
	$("#pageBill").on("blur", "input[bill=productCostPrice],input[bill=productNum]", function() {
		var price = $(this).closest("tr").find("input[bill=productCostPrice]").val();
		var num = $(this).closest("tr").find("input[bill=productNum]").val();
		$(this).closest("tr").find("td[bill=productAmount]").html(price*num);
	});
});