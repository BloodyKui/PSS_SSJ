<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	$(function() {

		//拿到传递过来的参数
		var params = "${pageContext.request.queryString}";
		$.get("purchaseBillItem_chartData.action", params, function(result) {
						$('#container').highcharts({
							chart : {
								type : 'pie',
								options3d : {
									enabled : true,
									alpha : 45,
									beta : 0,
								}
							},
							title : {
								text : 'Browser market shares at a specific website, 2014'
							},
							tooltip : {
								pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
							},
							plotOptions : {
								pie : {
									allowPointSelect : true,
									cursor : 'pointer',
									depth : 35,
									dataLabels : {
										enabled : true,
										format : '{point.name}'
									}
								}
							},
							series : [ {
								type : 'pie',
								name : 'Browser share',
								data : result
							} ]
						});
		});

	});
</script>
</head>
<body>
	<div id="container" style="height: 400px"></div>
</body>
</html>