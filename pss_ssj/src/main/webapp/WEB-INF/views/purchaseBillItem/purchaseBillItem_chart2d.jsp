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
		$
				.get(
						"purchaseBillItem_chartData.action",
						params,
						function(result) {
							$('#container')
									.highcharts(
											{
												chart : {
													plotBackgroundColor : null,
													plotBorderWidth : 1,//null,
													plotShadow : false
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
														dataLabels : {
															enabled : true,
															format : '<b>{point.name}</b>: {point.percentage:.1f} %',
															style : {
																color : (Highcharts.theme && Highcharts.theme.contrastTextColor)
																		|| 'black'
															}
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