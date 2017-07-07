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
							username : {
								validators : {
									/*notEmpty : {
										message : '用户名不能为空'
									},
									regexp : {
										regexp : /^[a-zA-Z0-9_\.]+$/,
										message : 'The username can only consist of alphabetical, number, dot and underscore'
									},*/
									remote : {
										url : 'employee_checkName.action?id='+$("input[name='id']").val(),
										message : '用户名已存在'
									}
								}

							}/*,
							email : {
								validators : {
									emailAddress : {
										message : 'The input is not a valid email address'
									}
								}
							},

							password : {
								validators : {
									notEmpty : {
										message : 'The password is required and cannot be empty'
									},
									identical : {
										field : 'confirmPassword',
										message : 'The password and its confirm are not the same'
									}
								}
							},
							confirmPassword : {
								validators : {
									notEmpty : {
										message : 'The confirm password is required and cannot be empty'
									},
									identical : {
										field : 'password',
										message : 'The password and its confirm are not the same'
									}
								}
							},

							age : {
								validators : {
									notEmpty : {
										message : 'The confirm password is required and cannot be empty'
									},
									integer : true,
									between : {
										min : '18',
										max : '60'
									}
								}
							}*/
						}
					});
});