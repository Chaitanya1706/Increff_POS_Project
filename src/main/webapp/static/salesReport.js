
function getSalesReportUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/report/sales";
}

function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brands";
}

//BUTTON ACTIONS
function addReport(event){
	//Set the values to update
	var $form = $("#salesReport-form");
	var json = toJson($form);
	var url = getSalesReportUrl();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		displayReport(response);
	   },
	   error: handleAjaxError
	});

	return false;
}


function getReport(){
	var url = getSalesReportUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayReport(data);
	   },
	   error: handleAjaxError
	});
}


function getBrandList(){
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


//UI DISPLAY METHODS

function displayReport(data){
    $('#salesReport-table').DataTable().destroy();
	var $tbody = $('#salesReport-table').find('tbody');
	$tbody.empty();
//	console.log(data);
    var c = 0;
	for(var i in data){
		var e = data[i];
		c++;
		var row = '<tr>'
		+ '<td>' + c + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '<td>'  + e.quantity + '</td>'
		+ '<td>'  + e.revenue + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
	pagenation();
}

function pagenation(){
    $('#salesReport-table').DataTable();
      $('.dataTables_length').addClass("bs-select");
}

function getList(data){
    var $dropDown = $('#inputBrand');
   
    $dropDown.empty();
    
    var opt = '<option value="all">All</option>'
    $dropDown.append(opt);
    for(var i in data){
    		var e = data[i];
    		opt = '<option value="' + e +'">' + e + '</option>'

    		$dropDown.append(opt);
    		
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



function getCategfromBrand(data){
    var $dropDown = $('#inputBrandCategory');
    $dropDown.empty();
    var opt = '<option value="all">All</option>'
    $dropDown.append(opt);
    for(var i in data){
            var e = data[i];
            opt = '<option value="' + e +'">' + e + '</option>'
            $dropDown.append(opt);
       }

}






//INITIALIZATION CODE
function init(){
	$('#get-report').click(addReport);
	$('#get-all').click(getReport);
}

$(document).ready(init);
$(document).ready(getReport);
$(document).ready(getBrandList);

