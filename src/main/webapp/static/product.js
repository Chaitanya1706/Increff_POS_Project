
function getProductUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/product";
}

function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brands";
}

//BUTTON ACTIONS
function addProduct(event){
	//Set the values to update
	var $form = $("#product-form");
	var json = toJson($form);
	var url = getProductUrl();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		getProductList();
	   },
	   error: handleAjaxError
	});

	return false;
}

function updateProduct(event){
	$('#edit-product-modal').modal('toggle');
	//Get the ID
	var id = $("#product-edit-form input[name=id]").val();
	var url = getProductUrl() + "/" + id;

	//Set the values to update
	var $form = $("#product-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		getProductList();
	   },
	   error: handleAjaxError
	});

	return false;
}


function getProductList(){
	var url = getProductUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayProductList(data);
	   },
	   error: handleAjaxError
	});
}


function getCategoryList(){
	var url = getBrandUrl()+"/"+"brandNames";
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		getList(data);
	   },
	   error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#productFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
		return;
	}
	
	//Process next row
	var row = fileData[processCount];
	processCount++;
	
	var json = JSON.stringify(row);
	var url = getProductUrl();

	//Make ajax call
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		uploadRows();  
	   },
	   error: function(response){
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   }
	});

}

function downloadErrors(){
	writeFileData(errorData);
}

//UI DISPLAY METHODS

function displayProductList(data){
    $('#product-table').DataTable().destroy();
	var $tbody = $('#product-table').find('tbody');
	$tbody.empty();
//	console.log(data);
	for(var i in data){
		var e = data[i];
		var buttonHtml = ' <button class="btn btn-warning" onclick="displayEditProduct(' + e.id + ')"><i class="fa-solid fa-pen-to-square"></i></button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
//		+ '<td>' + e.brandId + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '<td>'  + e.barcode + '</td>'
		+ '<td>'  + e.name + '</td>'
		+ '<td>'  + e.mrp + '</td>'
		+ '<td class="text-center">' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
	pagenation();
}

function pagenation(){
    $('#product-table').DataTable();
      $('.dataTables_length').addClass("bs-select");
}

function getList(data){
    var $dropDown = $('#inputBrand');
    var $editDropDown = $('#editBrand')
    $dropDown.empty();
    $editDropDown.empty();

    for(var i in data){
    		var e = data[i];
    		var opt = '<option value="' + e +'">' + e + '</option>'

    		$dropDown.append(opt);
    		$editDropDown.append(opt);
    }
}

function getCategory(value){

    var url = getBrandUrl() + "/" + "categ";
    $.ajax({
    	   url: url,
    	   type: 'POST',
    	   data: value,
    	   headers: {
           	'Content-Type': 'application/json'
           },
    	   success: function(data) {
    	   		getCategfromBrand(data);
    	   },
    	   error: handleAjaxError
    	});
}


function getCategoryEdit(value){

    var url = getBrandUrl() + "/" + "categ";
    $.ajax({
    	   url: url,
    	   type: 'POST',
    	   data: value,
    	   headers: {
           	'Content-Type': 'application/json'
           },
    	   success: function(data) {
    	   		getCategfromBrandEdit(data);
    	   },
    	   error: handleAjaxError
    	});
}

function getCategfromBrand(data){
    var $dropDown = $('#inputBrandCategory');
    $dropDown.empty();
    for(var i in data){
            var e = data[i];
            var opt = '<option value="' + e +'">' + e + '</option>'
            $dropDown.append(opt);
       }

}

function getCategfromBrandEdit(data){

    var $dropDown2 = $('#editBrandCategory');

    $dropDown2.empty();
    for(var i in data){
            var e = data[i];
            var opt = '<option value="' + e +'">' + e + '</option>'

            $dropDown2.append(opt);
       }

}



function displayEditProduct(id){
	var url = getProductUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayProduct(data);
	   },
	   error: handleAjaxError
	});	
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#productFile');
	$file.val('');
	$('#productFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts	
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#productFile');
	var fileName = $file.val();
	$('#productFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog(); 	
	$('#upload-product-modal').modal('toggle');
}

function displayProduct(data){
	$("#product-edit-form input[name=barcode]").val(data.barcode);
	$("#product-edit-form input[name=name]").val(data.name);
	$("#product-edit-form input[name=mrp]").val(data.mrp);
	$("#product-edit-form input[name=id]").val(data.id);
	$('#edit-product-modal').modal('toggle');
}


//INITIALIZATION CODE
function init(){
	$('#add-product').click(addProduct);
	$('#update-product').click(updateProduct);
	$('#refresh-data').click(getProductList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#productFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getProductList);
$(document).ready(getCategoryList);

