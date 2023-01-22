
function getInventoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory";
}


function getInventoryReportList(){

	var url = getInventoryUrl()+"/report";
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayInventoryReportList(data);
	   },
	   error: handleAjaxError
	});
}





//UI DISPLAY METHODS

function displayInventoryReportList(data){
    $('#inventoryReport-table').DataTable().destroy();
	var $tbody = $('#inventoryReport-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.brandCategory + '</td>'
		+ '<td>'  + e.quantity + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
	pagenation();
}

function pagenation(){
    $('#inventoryReport-table').DataTable();
      $('.dataTables_length').addClass("bs-select");
}

$(document).ready(getInventoryReportList);

