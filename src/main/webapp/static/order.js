
function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}

function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/ui/order-item";
}

function getPdfUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/pdf";
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

function placeOrder(id){
        var url = getPdfUrl()+"/"+id;
        $.ajax({
           url: url,
           type: 'GET',
           success: function() {
                getOrderList();
           },
           error: handleAjaxError
        });
}

function download(id){
        var url = getPdfUrl()+"/download/"+id;

        window.location.href = url;

}


//UI DISPLAY METHODS

function displayOrderList(data){

	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();


	for(var i in data){

		var e = data[i];
		var ourl = getOrderItemUrl() + "/" + e.id;
		var purl = getPdfUrl() + "/" + e.id;

		if(e.status==true){
		    var buttonHtml = '<button type="button" class="btn btn-secondary" id="download" onclick="download(' + e.id + ')">Download Invoice</button>'
		}
		else{
		    var buttonHtml = '<a class="btn btn-primary btn-md mr-3" href="'+ ourl + '" role="button">Edit Order</a>'
		    buttonHtml += '<button type="button" class="btn btn-success" id="place-order" onclick="placeOrder(' + e.id + ')">Place Order</button>'
		}

//		      buttonHtml += '<a class="btn btn-success btn-md" href="'+ purl + '" role="button">Place Order</a>'
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
	$('#place-order').click(getOrderList);
}

$(document).ready(init);
$(document).ready(getOrderList);

