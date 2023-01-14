
function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}

function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/ui/order-item";
}

//BUTTON ACTIONS
function addOrder(event){
	//Set the values to update
	var $form = $("#order-form");
	var json = toJson($form);
	var url = getOrderUrl()+"/create";

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		getOrderList();
	   },
	   error: handleAjaxError
	});

	return false;
}


function getOrderList(){
	var url = getOrderUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderList(data);
	   },
	   error: handleAjaxError
	});
}


//UI DISPLAY METHODS

function displayOrderList(data){
	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();


	for(var i in data){
		var e = data[i];
		var ourl = getOrderItemUrl() + "/" + e.id;
		var buttonHtml = '<a class="btn btn-success btn-md" href="'+ ourl + '" role="button">View Order</a>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.date + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}


//INITIALIZATION CODE
function init(){
	$('#add-order').click(addOrder);
	$('#refresh-data').click(getOrderList);
}

$(document).ready(init);
$(document).ready(getOrderList);

