$("#exportButton").click(function(e){
	
	window.location.href = basePath +'phone/api/exportCodeTemplate';
	});
	
var Accuracy = function () {
	return {
		initTable:function(){
        	$.get("/sva/phone/api/getTableData?t="+Math.random(),function(data){
        		if(!data.error){
        			if(oTable){oTable.fnDestroy();};
        			oTable = $('#table').dataTable({
        				"bProcessing": true,
        				"sDom": 'rt<"toolbar"lp<"clearer">>',
        				"sPaginationType": "full_numbers",
        				"aaData":data.data,
        				"bStateSave": true,
        				"aoColumnDefs": [

        									{ 
        										"aTargets": [0],
        										"mData": "phoneNumber"
        									},
        									{ 
        										"aTargets": [1],
        										"mData": "ip"
        									},
        									{ 
        										"aTargets": [2],
        										"mData": "timestamp",
        										"mRender": function ( data, type, full ) {
        											var date = new Date();
        											date.setTime(data);
        											return dateFormat(date,"yyyy/MM/dd HH:mm:ss");
        										}
        									}
        								],
        				"fnCookieCallback": function (sName, oData, sExpires, sPath) {      
        					// Customise oData or sName or whatever else here     
        					var newObj = {iLength:oData.iLength};
        					return sName + "="+JSON.stringify(newObj)+"; expires=" + sExpires +"; path=" + sPath;    
        				}
        			});
        		}        		
        	});
        },
        initPopupText: function(data){
        	if(data){
				var val = '';
				if (data.offset<=data.deviation) {
					 val = i18n_Valid;
				}else{
					val = i18n_Invalid;
				}
	        	$("#place").text(data.place);
	        	$("#floor").text(data.floor);
	        	$("#x").text(data.origin);
	        	$("#y").text(data.destination);
		        	var startTime = data.startdate;
		        	var date = new Date();
		        	date.setTime(startTime);
		        	startTime =  dateFormat(date,"yyyy/MM/dd HH:mm:ss");
		        $("#startTime").text(startTime);
			        var endTime = data.enddate;
		        	var date = new Date();
		        	date.setTime(endTime);
		        	endTime =  dateFormat(date,"yyyy/MM/dd HH:mm:ss");
	        	$("#endTime").text(endTime);
	        	$("#trigger").text(data.triggerIp);
				$("#offset").text(data.offset);
				$("#estimate").text(data.deviation);
				$("#result").text(val);
        	}
        }
        
    
    };

}();