$(function() {
	$('#domainForm')
			.bootstrapValidator(
					{
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
});