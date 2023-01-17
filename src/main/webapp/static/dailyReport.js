
function getReportUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/report";
}

//BUTTON ACTIONS
function getReport(event){
	//Set the values to update
	var $form = $("#dailyReport-form");
	var json = toJson($form);
	var url = getReportUrl()+"/daily";
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {

	   		displayReportList(response);
	   },
	   error: handleAjaxError
	});
	return false;
}


function getReportList(){
	var url = getReportUrl()+"/daily";
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayReportList(data);
	   },
	   error: handleAjaxError
	});
}

//UI DISPLAY METHODS

function displayReportList(data){
	var $tbody = $('#dailyReport-table').find('tbody');
	$tbody.empty();
	var c = 0;
	for(var i in data){
		var e = data[i];
		c++;
		var row = '<tr>'
		+ '<td>' + c + '</td>'
		+ '<td>' + e.date + '</td>'
		+ '<td>'  + e.totalOrder + '</td>'
		+ '<td>'  + e.totalItem + '</td>'
		+ '<td>'  + e.totalRevenue + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}




//INITIALIZATION CODE
function init(){
	$('#get-report').click(getReport);
}

$(document).ready(init);
$(document).ready(getReportList);

