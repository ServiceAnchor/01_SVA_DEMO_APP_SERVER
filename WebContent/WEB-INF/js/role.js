var role = function () {
	/**
	* 将对应信息填充到对应的select
	* @ param renderId [string] 标签id
	* @ param data [array] 列表数据
	*/
	var updateListmenu = function(renderId, data, selectTxt, callback) {
		var sortData = data;
		var len = sortData.length;
		var options = '<tr>';
		for ( var i = 1; i <= len; i++) {
			options += '<td><label><input name="menues" type="checkbox" id ="m'+ sortData[i-1].id + '" value="" />'+HtmlDecode3(sortData[i-1].name)+'</label></td>';
			if(i % 5 == 0 && i != 0){
				options += '</tr><tr>';
			}
		}
		removeOption(renderId);
		$('#' + renderId).append(options);
		if (callback) {
			callback();
		}
		return;
	};
	//赋值info
	var checkmenu = function(renderId, data, selectTxt, callback) {
		var sortData = data;
		var len = sortData.length;
		for ( var i = 0; i < len; i++) {
			if(sortData[i].id == selectTxt){
				$("#m"+ sortData[i].id).attr("checked", true);
			}	
		}
		removeOption(renderId);
		//$('#' + renderId).append(options);
		if (callback) {
			callback();
		}
		return;
	};
	var checkstore = function(renderId, data, selectTxt, callback) {
		var sortData = data.sort(function(a, b) {
			return a.name - b.name;
		});
		var len = sortData.length;
		for ( var i = 0; i < len; i++) {
			if(sortData[i].id == selectTxt){
				$("#s"+ sortData[i].id).attr("checked", true);
			}	
		}
		removeOption(renderId);
		//$('#' + renderId).append(options);
		if (callback) {
			callback();
		}
		return;
	};
	var updateListStore = function(renderId, data, selectTxt, callback) {
		var sortData = data.sort(function(a, b) {
			return a.name - b.name;
		});
		var len = sortData.length;
		var options = '<tr>';
		for ( var i = 1; i <= len; i++) {
			options += '<td><label><input name="stores" type="checkbox" id ="s'+ sortData[i-1].id + '" value="" />'+HtmlDecode3(sortData[i-1].name)+'</label></td>';
			if(i % 6 == 0 && i != 0){
				options += '</tr><tr>';
			}
			
		}
		options += '</tr>';
		removeOption(renderId);
		$('#' + renderId).append(options);
		if (callback) {
			callback();
		}
		return;
	};
	
	 var removeOption = function(renderId){
			$('#'+renderId+' .addoption').remove().trigger("liszt:updated");
		};
	var refreshTable = function(){
		$.get("/sva/role/api/getData?t="+Math.random(),function(data){
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
								if(data.length > 20){
									var html = data.substring(0,20)+"...";
									html = '<span title="'+HtmlDecode3(data)+'">'+HtmlDecode3(html)+'</span>';
										return html;
								}
								return HtmlDecode3(data);
							}
						},
						{ 
							"aTargets": [2],
							"mData": "menues",
							"mRender": function ( data, type, full ) {
								if(data.length > 30){
									var html = data.substring(0,30)+"...";
									html = '<span title="'+HtmlDecode3(data.replace(/\s/gi,''))+'">'+HtmlDecode3(html)+'</span>';
										return html;
								}
								return HtmlDecode3(data);
							}
						},
						{ 
							"aTargets": [3],
							"mData": "stores",
							"mRender": function ( data, type, full ) {
								if(data.length > 30){
									var html = data.substring(0,30)+"...";
									html = '<span title="'+HtmlDecode3(data)+'">'+HtmlDecode3(html)+'</span>';
										return html;
								}
								return HtmlDecode3(data);
							}
						},
						{ 
							"aTargets": [4],
							"mData": "updateTime",
							"mRender": function ( data, type, full ) {
								var date = new Date();
								date.setTime(data);
								return dateFormat(date,"yyyy/MM/dd HH:mm:ss");
							}
						
						},
						{
							"aTargets": [5],
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
		$("#roleName").val("");
		$("#storeAccount").val("");
		$("#storeAccount").html("");
		$("#menuAccount").val("");
		$("#menuAccount").html("");
	};
	return {
        
    	initTable:function(){
    		refreshTable();
        },	
        
		bindClickEvent: function(){
			$("input[data-type='edit']").live("click",function(e){
				clearInput();
				$("#add").click();
				var row = $(this).parent().parent();
				var data = oTable.fnGetData(row[0]);
				$("#id").val(data.id);
    			$("#roleName").val(HtmlDecode2(data.name));	
    			var menu = data.menues;
    			var stores = data.stores;
    			var menuId = "";
    			var storeId = "";
    			var arr = new Array();
    			var arrStore = new Array();
    			//通过菜单名称查询出相对应菜单的id，编辑时显示菜单
    			$.get("/sva/role/api/selmenuid?t="+Math.random(),{menu:menu},function(dataes){
        			if(!dataes.error){
        				menuId = dataes.dataes;
        				allMenuID = dataes.menuId;
        				arr = menuId.split(',');
        	    			$.get("/sva/role/api/menu?t="+Math.random(),function(data){
        	        			if(!data.error){
        	        				isselectedMenu =_.difference(allMenuID, arr);
	        	        			for(var m =0 ; m <arr.length;m++){
	        	        				checkmenu("menuAccount",data.data,arr[m],function(){
	        	        				});
	        	        			}
        	        			}	
        	        		});
            				
	           		}else{
            			refreshTable();
	           		}
        		});
    			//通过商场名称查询出相对应商场的id，编辑时显示商场
    			$.get("/sva/role/api/selRoleid?t="+Math.random(),{stores:stores},function(storedata){
        			if(!storedata.error){
        				storeId = storedata.storedata;
        				allStoreId = storedata.storeId;
        				arrStore = storeId.split(',');
        				$.get("/sva/store/api/stores?t="+Math.random(),function(data){
        	        			if(!data.error){
	        	        			//isselectedStore 未选中的商场
        	        				isselectedStore =_.difference(allStoreId, arrStore);
        	        				//显示文本框中选中的商场
	        	        			for(var m =0 ; m <arrStore.length;m++){
	        	        				checkstore("storeAccount",data.data,arrStore[m],function(){
	        	        				});
	        	        			}
        	        			}	
        	        		});
	           		}else{
            			refreshTable();
	           		}
        			
        		});
    			$("#editBox").show();
    			$(".sameInfo").removeClass("Validform_wrong");
    			$(".sameInfo").text("");
            });
            
            $("input[data-type='del']").live("click",function(e){
            	if(confirm(i18n_deleteInfo)){
	            	var id = $(this).data("id");	           	 
	        		$.post("/sva/role/api/deleteData",{id:id},function(data){
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
            function funStore(checkStore){	
        		obj = document.getElementsByName("stores");	
        		for(k in obj){		
        			if(obj[k].checked){	
        				checkStore.push(obj[k].id.substring(1,obj[k].id.length));
        			}	
        		}
        		return checkStore;
        	}
            function funmenu(checkMenu){	
            	obj = document.getElementsByName("menues");	
            	for(k in obj){		
            		if(obj[k].checked){	
            			checkMenu.push(obj[k].id.substring(1,obj[k].id.length));
            		}	
            	}
            	return checkMenu;
            }
            
            $("#confirm").on("click",function(e){
            	var roleVale = $("#roleName").val();
            	var checkStore = [];
            	var checkMenu = [];
            	checkStore = funStore(checkStore);
            	checkMenu = funmenu(checkMenu);
            	if (roleVale=="") {
            		$("#roleName").blur();
            		return false;
				}
            	if (checkMenu.length == 0) {
            		alert(i18n_selmenu);
            		return false;
            	}
            	if (checkStore.length == 0) {
            		alert(i18n_selstore);
            		return false;
            	}
            	var param ={
        			id : $("#id").val(),
        			name : $("#roleName").val(),
        			menuAccount : checkMenu+"",
        			storeAccount : checkStore+""
            	};
            	$.ajax({
		              "dataType": 'json', 
		              "type": "POST", 
		              "url": "/sva/role/api/saveData", 
		              "data": param, 
		              "success": function(data){
		            	  if(data.error){
		            		  $(".Validform_checktip").removeClass("Validform_right");
		            		  $(".Validform_checktip").text("");
		            		  $(".sameInfo").addClass("Validform_wrong");
		            		  $(".sameInfo").text(i18n_roleSame);
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
//            	clearInput();
            });
           
            $("#cancel").on("click",function(e){
            
            	$("#editBox").hide();
            	clearInput();
            });
            
            $("#add").on("click",function(e){
            	clearInput();
            	$("#editBox").show();
            	
         		$.get("/sva/store/api/stores?t="+Math.random(),function(data){
             		if(!data.error){
             			updateListStore("storeAccount",data.data,null,function(){
             			});
             		}
             	});
         		$.get("/sva/role/api/menu?t="+Math.random(),function(data){
         			if(!data.error){
         				updateListmenu("menuAccount",data.data,null,function(){
         				});
         			}
         		});
         	 
            });
    	}
    };
}();