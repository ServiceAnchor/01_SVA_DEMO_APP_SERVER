var Account = function () {
	var updateList = function(renderId,data,selectTxt,callback){
		var sortData = data.sort(function(a,b){return a.name - b.name;});
	    var len = sortData.length;
	    var options = '';
	    for(var i=0;i<len;i++){
	    	var info = sortData[i];
	        if(selectTxt == info.name){
	    		options += '<option class="addoption" selected=true value="'+info.id+'">' + HtmlDecode3(info.name) +'</option>';
	    	}else{
	    		options += '<option class="addoption" value="'+info.id+'">' + HtmlDecode3(info.name) +'</option>';
	    	}
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    if(callback){
	        callback();
	    }
	    return;
	};
	  var removeOption = function(renderId){
			$('#'+renderId+' .addoption').remove().trigger("liszt:updated");
		};
	var refreshTable = function(){
		$.get("/sva/account/api/getData?t="+Math.random(),function(data){
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
							"mData": "username" 
						},	
//						{ 
//							"aTargets": [2],
//							"mData": "password"
//						},
						{ 
							"aTargets": [2],
							"mData": "type"
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
		$(".demoform").Validform().resetForm();
		$("#id").val("");
		$("#userName").val("");
		$("#password").val("");
		$("#type").val("");
//		$(".Validform_checktip").removeClass("Validform_right");
//		$(".Validform_checktip").text("");
//		$(".Validform_checktip").removeClass("Validform_wrong");
//		$(".sameInfo").removeClass("Validform_wrong");
//		$(".sameInfo").text("");
	};
	var checkInput = function(){
		var name=$("#userName").val();
		if (name=="") {
			return false;
		}else{
			return true;
		}
	};

	return {
        
    	initTable:function(){
    		refreshTable();
    		$.get("/sva/role/api/getData?t="+Math.random(),function(data){
    			if(!data.error){
    				updateList("type",data.data);
    			}
    		});
        },	
        
		bindClickEvent: function(){
			$("input[data-type='edit']").live("click",function(e){
//            	$(".demoform").Validform().resetForm();
				var row = $(this).parent().parent();
				var data = oTable.fnGetData(row[0]);
				$("#id").val(data.id);
    			$("#userName").val(data.username);
    			$("#password").val(data.password);
    			$("#type").val(data.roleId);
    			$("#editBox").show();
    			$(".sameInfo").removeClass("Validform_wrong");
    			$(".sameInfo").text("");
            });
            
            $("input[data-type='del']").live("click",function(e){
            	if(confirm(i18n_deleteInfo)){
	            	var id = $(this).data("id");	           	 
	        		$.post("/sva/account/api/deleteData",{id:id},function(data){
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
//            	if(!checkInput()){
//            		$(".Validform_checktip").addClass("Validform_wrong");
//            		$(".Validform_checktip").text(i18n_storeinput);
//            		$("#userName").addClass("Validform_error");
//            		return false;
//            	}
            	var	a= $("#userName").val();
            	var password = $("#password").val();
            	var type = $("#type").val();
            	if(a == "" && password == "" && type == ""){
            		$("#userName").blur();
            		$("#password").blur();
            		 $("#type").blur();
            		return false;
            	}
            	if (a=="") {
            		$("#userName").blur();
            		return false;
				}
            	if (password=="") {
            		$("#password").blur();
            		return false;
            	}
            	if (type=="") {
            		$("#type").blur();
            		return false;
            	}
            	if(password.length < 4 || password.length > 20){
            		//alert("密码必须大于4位小于20位");
            		return false;
            	}
            	var param ={
        			id : $("#id").val(),
        			userName : $("#userName").val().replace(/\s/g, ""),
        			password : $("#password").val().replace(/\s/g, ""),
        			type : $("#type").val()
            	};
            	$.ajax({
		              "dataType": 'json', 
		              "type": "POST", 
		              "url": "/sva/account/api/saveData", 
		              "data": param, 
		              "success": function(data){
		            	  if(data.error){
		            		  $(".Validform_checktip").removeClass("Validform_right");
		            		  $(".Validform_checktip").text("");
		            		  $(".sameInfo").addClass("Validform_wrong");
		            		  $(".sameInfo").text(i18n_sameInfo);
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