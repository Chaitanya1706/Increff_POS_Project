
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
    $('#order-table').DataTable().destroy();
	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();


	for(var i in data){

		var e = data[i];
		var ourl = getOrderItemUrl() + "/" + e.id;
		var purl = getPdfUrl() + "/" + e.id;

		if(e.status==true){
		    var buttonHtml = '<button type="button" class="btn btn-secondary" id="download" onclick="download(' + e.id + ')"><i class="fa-solid fa-file-arrow-down"></i></button>'
		}
		else{
		    var buttonHtml = '<a class="btn btn-warning btn-md mr-3" href="'+ ourl + '" role="button"><i class="fa-solid fa-pen-to-square"></i></a>'
		    buttonHtml += '<button type="button" class="btn btn-success" id="place-order" onclick="placeOrder(' + e.id + ')"><i class="fa-solid fa-square-check"></i></button>'
		}

//		      buttonHtml += '<a class="btn btn-success btn-md" href="'+ purl + '" role="button">Place Order</a>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.date + '</td>'
		+ '<td class="text-center">' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
	pagenation();
}

function pagenation(){
    $('#order-table').DataTable();
      $('.dataTables_length').addClass("bs-select");
}


//INITIALIZATION CODE
function init(){
	$('#add-order').click(addOrder);
	$('#refresh-data').click(getOrderList);
	$('#place-order').click(getOrderList);
}

$(document).ready(init);
$(document).ready(getOrderList);

