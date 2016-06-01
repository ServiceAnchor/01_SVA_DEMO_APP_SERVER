var Estimate = function () {
	/**
	* 将对应信息填充到对应的select
	* @ param renderId [string] 标签id
	* @ param data [array] 列表数据
	*/
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
	
	/**
	* 将对应信息填充到对应的select
	* @ param renderId [string] 标签id
	* @ param data [array] 列表数据
	*/
	var updateFloorList = function(renderId,data,selectTxt,callback){
	    var sortData = data.sort(function(a,b){return a.floor - b.floor;});
	    var len = sortData.length;
	    var options = '';
	    for(var i=0;i<len;i++){
	        if(selectTxt == sortData[i].floor){
	    		options += '<option class="addoption" selected=true value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
	    	}else{
	    		options += '<option class="addoption" value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
	    	}
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    if(callback){
	        callback();
	    }
	    return;
	};
	
	var updateFloorList1 = function(renderId,data,selectTxt,callback){
		$("#zSel").find("option").remove(); 
	    var sortData = data.sort(function(a,b){return a.floor - b.floor;});
	    var len = sortData.length;
	    var options = '<option value=""></option>';
	    for(var i=0;i<len;i++){
	        if(selectTxt == sortData[i].floor){
	    		options += '<option class="addoption" selected=true value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
	    	}else{
	    		options += '<option class="addoption" value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
	    	}
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    return;
	};
	
	var removeOption = function(renderId){
		$('#'+renderId+' .addoption').remove();
	};
	
	var refreshTable = function(){
//		$("#table").empty();
		$.get("/sva/estimate/api/getTableData?t="+Math.random(),function(data){
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
//    						"bVisible": false,
    						"mData": "place",
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
    						"aTargets": [1],
    						"mData": "floor"
    					},
    					{
    	                    "aTargets": [2],
    	                    "bSortable": false,
    	                    "bFilter": false,
    	                    "mData": function(source, type, val) {
    	                        return "";
    	                    },
    	                    "mRender": function ( data, type, full ) {
    	                    	var html ;
    	            			if (full.type=="0.25") {
    	            				html = i18n_estimate1;
    						}
    	        			if (full.type=="0.33") {
    	        				html = i18n_estimate2;
    	        			}
    	        			if (full.type=="0.375") {
    	        				html = i18n_estimate3;
    	        			}
    	        			if (full.type=="0.5") {
    	        				html = i18n_estimate4;
    	        			}
    	                        return html;
    	                      }
    	                },
    					{ 
    						"aTargets": [3],
    						"mData": "a"
    					},
    					{ 
    						"aTargets": [4],
    						"mData": "b"
    					},
    					{ 
    						"aTargets": [5],
    						"mData": "n"
    					},
    					{
    						"aTargets": [6],
    						"mData": "deviation"	
    					},
    					{
    	                    "aTargets": [7],
    	                    "bSortable": false,
    	                    "bFilter": false,
    	                    "mData": function(source, type, val) {
    	                        return "";
    	                    },
    	                    "mRender": function ( data, type, full ) {
    	                    	var html = "" +
    	                    	'<input type=button data-type="edt" data-placeId="'+full.placeId+'" data-id="'+full.id+'" value="'+i18n_edit+'"  />'+
    	        				'<input type=button data-type="del" data-id="'+full.id+'" value="'+i18n_delete+'"  />';
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
	
	var initSelect = function(){
		$.get("/sva/store/api/getData?t="+Math.random(),function(data){
			if(!data.error){
				updateList("placeSel",data.data);
			}
		});
	};

    return {
        
    	init:function(){
    		initSelect();
    		refreshTable();
        },
    	
    	bindClickEvent: function(){
    		// 地点下拉列表修改 触发楼层下拉列表变化
    		$("#placeSel").on("change", function(){
				var placeId = $("#placeSel").val();
				$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeId}, function(data){
					if(!data.error){
						updateFloorList1("zSel",data.data);
					}
				});
			});

            $("input[data-type='del']").live("click",function(e){
            	var id = $(this).data("id");
            	if(confirm(i18n_deleteInfo)){
		           	$.post("/sva/estimate/api/deleteData",{id:id}, function(data){
						if(!data.error){
							refreshTable();
						}
					});
            	}else{
            		return false;
            	}
            });
            
            $("#confirm").on("click",function(e){
            	var param = {id:$("#idid").val(),floor:$("#zSel").val()};
            	var check = validForm.check(false);
            	if(check){
              	  $.post("/sva/estimate/api/checkFloorNo",param,function(data){
              			var b = data.data;
                  		if (b)
                  		{
                  			$(".demoform").submit();
      					}else
      						{
      			              $(".sameInfo").addClass("Validform_wrong");
      			              $(".sameInfo").text(i18n_same);
      						}
              		});

              		
              	}
            });
            
            $("input[data-type='edt']").live("click",function(e){
            	$(".demoform").Validform().resetForm();
            	$(".sameInfo").removeClass("Validform_wrong");
            	$(".sameInfo").text("");
            	var id = $(this).data("id");
	           	var rowObj = $(this)[0].parentNode.parentNode;
	           	var place = rowObj.childNodes[0].innerHTML,
	           	       floor =rowObj.childNodes[1].innerHTML,
	           	 	   a = rowObj.childNodes[3].innerHTML,
	           	 	   b = rowObj.childNodes[4].innerHTML,
	           	 	   n = rowObj.childNodes[5].innerHTML,
	           	 	   type = rowObj.childNodes[2].innerHTML;
	           	var placeId = $(this).data("placeid");
	           	if (type==i18n_estimate1)
	           	{
	           		type = 0.25;
				}
	           	if (type==i18n_estimate2)
	           	{
	           		type = 0.33;
				}
	           	if (type==i18n_estimate3)
	           	{
	           		type = 0.375;
				}
	           	if (type==i18n_estimate4)
	           	{
	           		type = 0.5;
				}
	
	           	//MapMng.deleteMap(xSpot, ySpot, zSpot, place);
	           	$("#placeSel").val(placeId);
				$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeId}, function(data){
					if(!data.error){
						updateFloorList("zSel",data.data,floor);
					}
				});
				$("#editBox").show();
				$("input[name='a']").val(HtmlDecode2(a));
	           	$("input[name='b']").val(b);
	           	$("input[name='n']").val(n);
	           	$("#enableSel").val(type);
        		$("#idid").val(id);
            });
    	}
    };

}();
function checkMsg()
{
	var placeSel =$("#placeSel").val();
	var zSel= $("select[name='floorNo']").find("option:selected").text();
	var a=$("select[name='a']").val();
	var b=$("input[name='b']").val();
	var n=$("input[name='n']").val();
	var type=$("#enableSel").val();
	if (zSel==""||a==""||b==""||n==""||type=="") {
		alert(i18n_info);
		return false;
	}
	if (n=="1") {
		alert(i18n_pRRU);
		return false;
	}
}
function HtmlDecode2(str) {
	var str1 = str.replace(/&lt;/g,"<");
	var str2 = str1.replace(/&gt;/g,">");
	var str3 = str2.replace(/&amp;/g,"&");
	var str4 = str3.replace(/&quot;/g,"\"");
	var str5 = str4.replace(/&apos;/g,"\'");
    return str5;
     }
function estimateOnkeyup(str)
{
if (isNaN(str.value)&&!isNaN(str.value.substring(0,str.value.length-1))) {
		str.value = str.value.substring(0,str.value.length-1);
		str.focus();
		return false;
	}
if(isNaN(str.value)&&isNaN(str.value.substring(0,str.value.length-1)))
	{
	str.value = "";
	str.focus();
	return false;
	}
	if (str.value.split(".").length<2) {
		var a = parseInt(str.value.substring(str.value.length-1,str.value.length));
		if (a<0)
		{
			str.value = str.value.substring(0,str.value.length-1);
			str.focus();
			return false;
		}
	}else
		{
		var c = str.value.split(".")[1];
		var b = str.value.split(".")[0];
		if (c.length>2) 
		{
			str.value = str.value.substring(0,b.length+3);
			str.focus();
			return false;	
		}else
			{
			if (isNaN(str.value))
			{
				str.value = str.value.substring(0,str.value.length-1);
				str.focus();
				return false;
			}else
				{
				if(str.value.split(".")[1]!=""&&str.value.split(".")[1]!="0"&&str.value.split(".")[1]!="00")
					{
					str.value = parseFloat(str.value);
					str.focus();
					return false;
					}
				return false;
				}
			
			
			}
		}
	}
function estimateOnkeyup1(str)
{
	var a = parseInt(str.value);
	if (str.value.split(".").length<2) {
	if (isNaN(str.value)&&!isNaN(str.value.substring(0,str.value.length-1))) {
			str.value = str.value.substring(0,str.value.length-1);
			str.focus();
			return false;
		}
	if(isNaN(str.value)&&isNaN(str.value.substring(0,str.value.length-1)))
		{
		str.value = "";
		str.focus();
		return false;
		}
	if (a<1) {
		str.value = str.value.substring(0,str.value.length-1);
		str.focus();
		return false;
	}
	}else
		{
		str.value = str.value.split(".")[0]+str.value.split(".")[1];
		str.focus();
		return false;
		}
}
function addMap()
{
	clearinfo();
	$(".sameInfo").removeClass("Validform_wrong");
	$(".sameInfo").text("");
	$("#editBox").show();	
}
function hideAdd()
{
	$(".demoform").Validform().resetForm();
	clearinfo();
	$("#editBox").hide();
}
function clearinfo()
{
	$("#placeSel").val("");
	$("#zSel").val("");
	$("#aId").val("");
	$("#bId").val("");
	$("#nId").val("");
	$("#enableSel").val("");
   	$(".sameInfo").removeClass("Validform_wrong");
   	$(".sameInfo").text("");
   	$("#idid").val("");
	$("#zSel").find("option").remove();
}