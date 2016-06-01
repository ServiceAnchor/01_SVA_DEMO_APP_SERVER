var paramconfig = function () {
	var refreshTable = function(){
		$.get("/sva/paramconfig/api/getData?t="+Math.random(),function(data){
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
							"mData": "banThreshold"
							
						},
						{ 
							"aTargets": [1],
							"mData": "filterLength"
							
						},
						{ 
							"aTargets": [2],
							"mData": "horizontalWeight"
							
						},
						{ 
							"aTargets": [3],
							"mData": "ongitudinalWeight"
						},
						{ 
							"aTargets": [4],
							"mData": "maxDeviation"
						},
						{ 
							"aTargets": [5],
							"mData": "exceedMaxDeviation"
						},
						{ 
							"aTargets": [6],
							"mData": "correctMapDirection"
						},
						{ 
							"aTargets": [7],
							"mData": "restTimes"
						},
						{ 
							"aTargets": [8],
							"mData": "filterPeakError"
						},
						{ 
							"aTargets": [9],
							"mData": "peakWidth"
						},
						{ 
							"aTargets": [10],
							"mData": "step"
						},
						{ 
							"aTargets": [11],
							"mData": "updateTime",
							"mRender": function ( data, type, full ) {
								var date = new Date();
								date.setTime(data);
								return dateFormat(date,"yyyy/MM/dd HH:mm:ss");
							}
						},
						{
							"aTargets": [12],
							"bSortable": false,
							"bFilter": false,
							"mData": function(source, type, val) {
								return "";
							},
							"mRender": function ( data, type, full ) {
								var html = "" +
									'<input type="button" data-type="edit" style="width: 54px;height:30px;font-size: 13px;font-family:inherit;" data-id="'+full.id+'" value="'+i18n_edit+'">';
								return html;
							}
						}
					]
    				
				});
			}
		});
	};
	
	var clearInput = function(){
		$(".demoform").Validform().resetForm();
		$("#id").val("");
		$("#banThreshold").val("");
		$("#filterLength").val("");
		$("#horizontalWeight").val("");
		$("#ongitudinalWeight").val("");
		$("#maxDeviation").val("");
		$("#exceedMaxDeviation").val("");
		$("#correctMapDirection").val("");
		$("#restTimes").val("");
		$("#filterPeakError").val("");
		$("#peakWidth").val("");
		$("#step").val("");		
		$("#categoryName").removeClass("Validform_error");
		$(".Validform_checktip").removeClass("Validform_right");
		$(".Validform_checktip").text("");
		$(".Validform_checktip").removeClass("Validform_wrong");
		$(".sameInfo").removeClass("Validform_wrong");
		$(".sameInfo").text("");
	};
	
	var checkInput = function(){
		
		var name=$("#categoryName").val();
		if (name=="") {
			return false;
		}else{
			return true;
		}
	};

	return {
        
    	initTable:function(){
    		refreshTable();
        },	
        
		bindClickEvent: function(){
			$("input[data-type='edit']").live("click",function(e){
            	$(".demoform").Validform().resetForm();
				var row = $(this).parent().parent();
				var data = oTable.fnGetData(row[0]);
				$("#editBox").show();
				$("#id").val(data.id);
    			$("#banThreshold").val(data.banThreshold);
    			$("#filterLength").val(data.filterLength);
    			$("#horizontalWeight").val(data.horizontalWeight);
    			$("#ongitudinalWeight").val(data.ongitudinalWeight);
    			$("#maxDeviation").val(data.maxDeviation);
    			$("#exceedMaxDeviation").val(data.exceedMaxDeviation);
    			$("#correctMapDirection").val(data.correctMapDirection);
    			$("#restTimes").val(data.restTimes);
    			$("#filterPeakError").val(data.filterPeakError);
    			$("#peakWidth").val(data.peakWidth);
    			$("#step").val(data.step);
    			$(".sameInfo").removeClass("Validform_wrong");
    			$(".sameInfo").text("");
            });
            
			$("#confirm").on("click",function(e){
//				if($("#errorAngleId").val()=="")
//					{
//					$("#errorAngleId").val("0");
//					}
            	var check = validForm.check(false);
            	if (!check) {
					return false;
				}
            	var param ={
        			id : "1",
        			banThreshold : $("#banThreshold").val(),
        			filterLength : $("#filterLength").val(),
        			horizontalWeight : $("#horizontalWeight").val(),
        			ongitudinalWeight : $("#ongitudinalWeight").val(),
        			maxDeviation : $("#maxDeviation").val(),
        			exceedMaxDeviation : $("#exceedMaxDeviation").val(),
        			correctMapDirection : $("#correctMapDirection").val(),
        			restTimes : $("#restTimes").val(),
        			filterPeakError : $("#filterPeakError").val(),
        			peakWidth : $("#peakWidth").val(),
        			step : $("#step").val()
            	};
            	$.ajax({
		              "dataType": 'json', 
		              "type": "POST", 
		              "url": "/sva/paramconfig/api/saveData", 
		              "data": param, 
		              "success": function(data){
		            	  if(data.error){
		            		  $(".Validform_checktip").removeClass("Validform_right");
		            		  $(".Validform_checktip").text("");
		            		  $(".sameInfo").addClass("Validform_wrong");
		            		  $("#info").text(data.error);
		            		  $(".alert").removeClass("alert-info");
		            		  $(".alert").addClass("alert-error");
		            		 // $("#alertBox").show();
		            	  }else{
		            		  $("#editBox").hide();
		            		  refreshTable();
		            	  }
		              }
		        });
            	
            });
            $("#cancel").on("click",function(e){
            	$("#editBox").hide();
            	clearInput();
            });
            $("#add").on("click",function(e){
            	clearInput();
            	$("#editBox").show();
            });
          
    	}
    };

}();