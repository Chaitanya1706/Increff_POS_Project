
function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order-item";
}

var order_id;

//BUTTON ACTIONS
function addOrderItem(event){
	//Set the values to update
	var $form = $("#orderItem-form");
	var json = toJson($form);
	var url = getOrderItemUrl()+"/"+order_id;

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		getOrderItemList();
	   },
	   error: handleAjaxError
	});

	return false;
}

function updateOrderItem(event){
	$('#edit-orderItem-modal').modal('toggle');
	//Get the ID
	var id = $("#orderItem-edit-form input[name=id]").val();
	var url = getOrderItemUrl() + "/" + id;
    console.log(url+"  url");
	//Set the values to update
	var $form = $("#orderItem-edit-form");
	var json = toJson($form);
	console.log(json+" json");

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		getOrderItemList();
	   },
	   error: handleAjaxError
	});

	return false;
}


function getOrderItemList(){
	var url = getOrderItemUrl()+"/order/"+order_id;
	console.log(url);
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderItemList(data);
	   },
	   error: handleAjaxError
	});
}



function deleteOrderItem(id){
	var url = getOrderItemUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getOrderItemList();
	   },
	   error: handleAjaxError
	});
}


//UI DISPLAY METHODS

function displayOrderItemList(data){
	var $tbody = $('#orderItem-table').find('tbody');
	$tbody.empty();
//	console.log(data);
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button class="btn btn-danger" onclick="deleteOrderItem(' + e.id + ')">delete</button>'
		buttonHtml += ' <button class="btn btn-warning" onclick="displayEditOrderItem(' + e.id + ')">edit</button>'
		var row = '<tr>'
		+ '<td>' + e.orderId + '</td>'
		+ '<td>' + e.productName + '</td>'
		+ '<td>'  + e.quantity + '</td>'
		+ '<td>'  + e.sellingPrice + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}



function displayEditOrderItem(id){
	var url = getOrderItemUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderItem(data);
	   },
	   error: handleAjaxError
	});	
}


function displayOrderItem(data){
	$("#orderItem-edit-form input[name=orderId]").val(data.orderId);
	$("#orderItem-edit-form input[name=id]").val(data.id);
	$("#orderItem-edit-form input[name=productName]").val(data.productName);
	$("#orderItem-edit-form input[name=quantity]").val(data.quantity);
	$("#orderItem-edit-form input[name=sellingPrice]").val(data.sellingPrice);
	$('#edit-orderItem-modal').modal('toggle');
}


//INITIALIZATION CODE
function init(){
	$('#add-orderItem').click(addOrderItem);
	$('#update-orderItem').click(updateOrderItem);
	$('#refresh-data').click(getOrderItemList);
    order_id = $("meta[name=order_id]").attr("content")
//    console.log(order_id+" orderId")
}

$(document).ready(init);
$(document).ready(getOrderItemList);


