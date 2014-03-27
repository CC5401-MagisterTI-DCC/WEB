$.datepicker.regional[""].dateFormat = 'yy-mm-dd';
	$.datepicker.setDefaults($.datepicker.regional['']);

	oTable = $('#historialTable').dataTable({
		"sPaginationType": "bootstrap",
		aaSorting : [[0, 'desc']]
	}).columnFilter({
		sPlaceHolder: "head:after",
		aoColumns: [ 	
		            {sSelector: "#dateFilter",type: "date-range"},
		            null,
		            null,
		            null
		            ]
	});
