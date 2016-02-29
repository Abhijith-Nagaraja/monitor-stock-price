$(function() {
	currentTrends();
	companyDetails();
});

function companyDetails() {
	$
			.get(
					"./details/CompanyDetails",
					function(data, status) {
						$('#details').html("");
						if (data == "empty") {
							$('#details')
									.html(
											"<tr><td></td><td>Click on Add company to view the details</td></tr>")
						} else {
							data = JSON.parse(data);
							for (var i = 0; i < data.length; i++) {
								var value = data[i];
								item = "<tr>";
								item += "<td>" + value.index
								"</td>";
								item += "<td>" + value.name
								"</td>";
								item += "<td>" + value.price
								"</td>";
								if (Number(value.change) < 0)
									item += "<td style='background: red;text-align:right;'>"
											+ value.change + "</td>"
								else if (Number(value.change) > 0)
									item += "<td style='background: green;text-align:right;'>"
											+ value.change + "</td>"
								else
									item += "<td style='background: gray;text-align:right;'>"
											+ value.change + "</td>"

								item += "<td><button type='button' class='btn btn-info'	aria-label='Left Align' onClick='showHistory(\""
										+ value.symbol
										+ "\",\""
										+ value.name
										+ "\")'> <span class=' glyphicon glyphicon-stats' aria-hidden='true'></span> Historical Data</button></td>";
								item += "<td><button type='button' id='remove' onClick='removeCompany(\""
										+ value.id
										+ "\")' class='btn btn-danger' aria-label='Left Align'><span class=' glyphicon glyphicon-minus'aria-hidden='true'></span> Remove Company</button></td>'"
								item += "</tr>"
								$('#details').append(item);
							}
						}
						setTimeout(companyDetails, 5000)
					});
}
function currentTrends() {
	$
			.get(
					"./details/current-trends",
					function(data, status) {
						$('#trend').html("");
						data = JSON.parse(data);
						$(data)
								.each(
										function(obj) {
											item = "<tr>" + "<td>" + this.name
													+ "</td>" + "<td>"
													+ this.price + "</td>";
											if (Number(this.change) < 0)
												item += "<td style='background: red;text-align:right;'>"
														+ this.change + "</td>"
											else if (Number(this.change) > 0)
												item += "<td style='background: green;text-align:right;'>"
														+ this.change + "</td>"
											else
												item += "<td style='background: gray;text-align:right;'>"
														+ this.change + "</td>"
											item += "</tr>"
											$('#trend').append(item);
										});
						setTimeout(currentTrends, 5000);
					});
}

$('#selectCompany').select2({
	placeholder : 'Select stock',
	minimumInputLength : 2,
	width : '100%',
	tags : [],
	ajax : {
		url : "./details/stocks",
		dataType : 'json',
		type : "GET",
		data : function(term) {
			return {
				term : term
			};
		},
		processResults : function(data) {
			return {
				results : $.map(data, function(item) {
					return {
						text : item.name,
						id : item.symbol
					}
				})
			};
		}
	}
});

$("#company").click(function() {
	$.post("./crud/AddCompany", {
		sym : $('#selectCompany').val()
	}, function(data, status) {
		if (data == 'success') {
			location.reload();
		}
	}).fail(function(data) {
		alert(data);
	});

});
function removeCompany(id) {
	$.post("./crud/RemoveCompany", {
		id : id
	}, function(data, status) {
		if (data == 'success') {
			location.reload();
			$('#chart').html("");
		}
	}).fail(function(data) {
		alert(data);
	});
}

function showHistory(symbol, name) {
	$
			.getJSON(
					"./details/history?symbol=" + symbol,
					function(data, status) {
						$('#chart')
								.highcharts(

										{
											chart : {
												zoomType : 'x'
											},
											title : {
												text : 'Historical data for '
														+ name
											},
											xAxis : {
												type : 'datetime'
											},
											legend : {
												enabled : false
											},
											plotOptions : {
												area : {
													fillColor : {
														linearGradient : {
															x1 : 0,
															y1 : 0,
															x2 : 0,
															y2 : 1
														},
														stops : [
																[
																		0,
																		Highcharts
																				.getOptions().colors[0] ],
																[
																		1,
																		Highcharts
																				.Color(
																						Highcharts
																								.getOptions().colors[0])
																				.setOpacity(
																						0)
																				.get(
																						'rgba') ] ]
													},
													marker : {
														radius : 2
													},
													lineWidth : 1,
													states : {
														hover : {
															lineWidth : 1
														}
													},
													threshold : null
												}
											},
											series : [ {
												name : symbol,
												type : 'area',
												data : data
											} ]
										});
					}).fail(function(data) {
				alert(data);
			});
}