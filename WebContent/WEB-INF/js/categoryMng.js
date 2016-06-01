var Category = function () {
	var refreshTable = function(){
		$.get("/sva/category/api/getData?t="+Math.random(),function(data){
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
							"bVisible": false,
							"mData": "id" 
						},
							{ 
							"aTargets": [1],
							"mData": "name",
							"mRender": function ( data, type, full ) {
								if(data.length > 40){
									var html = data.substring(0,40)+"...";
									html = '<span title="'+HtmlDecode3(data)+'">'+HtmlDecode3(html)+'</span>';
										return html;
								}
								return HtmlDecode3(data);
							}
						},
						{ 
							"aTargets": [2],
							"mData": "createTime",
							"mRender": function ( data, type, full ) {
								var date = new Date();
								date.setTime(data);
								return dateFormat(date,"yyyy/MM/dd HH:mm:ss");
							}
						},
						{ 
							"aTargets": [3],
							"mData": "updateTime",
							"mRender": function ( data, type, full ) {
								var date = new Date();
								date.setTime(data);
								return dateFormat(date,"yyyy/MM/dd HH:mm:ss");
							}
						},
						{
							"aTargets": [4],
							"bSortable": false,
							"bFilter": false,
							"mData": function(source, type, val) {
								return "";
							},
							"mRender": function ( data, type, full ) {
								var html = "" +
									'<input type="button" data-type="edit" style="width: 54px;height:30px;font-size: 13px;font-family:inherit;" data-id="'+full.id+'" value="'+i18n_edit+'">' +
									'<input type="button" data-type="del" style="width: 54px;height:30px;font-size: 13px;font-family:inherit;" data-id="'+full.id+'" value="'+i18n_delete+'">';
								
								return html;
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
	};
	
	var clearInput = function(){
		$("#id").val("");
		$("#categoryName").val("");
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
				$("#id").val(data.id);
    			$("#categoryName").val(HtmlDecode2(data.name));
    			$("#editBox").show();
    			$(".sameInfo").removeClass("Validform_wrong");
    			$(".sameInfo").text("");
            });
            
            $("input[data-type='del']").live("click",function(e){
            	if(confirm(i18n_deleteInfo)){
	            	var id = $(this).data("id");	           	 
	        		$.post("/sva/category/api/deleteData",{id:id},function(data){
	        			if(data.error){
	        				$("#info").text(data.error);
	            			$(".alert").removeClass("alert-info");
	            			$(".alert").addClass("alert-error");
	            			$("#alertBox").show();
		           		}else{
	            			refreshTable();
		           		}
	        		});
            	}
            });
            
            $("#confirm").on("click",function(e){
            	if(!checkInput()){
            		$(".Validform_checktip").addClass("Validform_wrong");
            		$(".Validform_checktip").text(i18n_storeinput);
            		$("#categoryName").addClass("Validform_error");
            		
            	}
            var	a= $("#categoryName").val();
            	if (a=="") {
            		return false;
				}
            	var param ={
        			id : $("#id").val(),
        			name : $("#categoryName").val()
            	};
            	
            	$.ajax({
		              "dataType": 'json', 
		              "type": "POST", 
		              "url": "/sva/category/api/saveData", 
		              "data": param, 
		              "success": function(data){
		            	  if(data.error){
		            		  $(".Validform_checktip").removeClass("Validform_right");
		            		  $(".Validform_checktip").text("");
		            		  $(".sameInfo").addClass("Validform_wrong");
		            		  $(".sameInfo").text(i18n_categorySame);
		            		  $("#info").text(data.error);
		            		  $(".alert").removeClass("alert-info");
		            		  $(".alert").addClass("alert-error");
		            		 // $("#alertBox").show();
		            	  }else{
		            		  $("#editBox").hide();
		            		  clearInput();
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