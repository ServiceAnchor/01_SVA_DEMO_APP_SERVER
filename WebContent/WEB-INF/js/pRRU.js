var MsgMng = function () {
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
	    	var info = sortData[i];
	        if(selectTxt == sortData[i].floor){
	    		options += '<option class="addoption"  selected=true value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
	    	}else{
	    		options += '<option class="addoption"  value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
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
	    	var info = sortData[i];
	        if(selectTxt == sortData[i].floor){
	    		options += '<option class="addoption"  selected=true value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
	    	}else{
	    		options += '<option class="addoption"  value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
	    	}
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    return;
	};
	
	var clacImgZoomParam = function( maxWidth, maxHeight, width, height){  
	    var param = {top:0, left:0, width:width, height:height};  
	    rateWidth = width / maxWidth;  
	    rateHeight = height / maxHeight;  
	      
	    if( rateWidth > rateHeight ){  
	        param.width =  maxWidth;  
	        param.height = Math.round(height / rateWidth);  
	    }else{  
	        param.width = Math.round(width / rateHeight);  
	        param.height = maxHeight;  
	    }
	      
	    param.left = Math.round((maxWidth - param.width) / 2);  
	    param.top = Math.round((maxHeight - param.height) / 2); 
	    return param;  
	};
    
    var deleteMsg = function(floorNo){
    	if(confirm(i18n_deleteInfo))
    	{
    	$.post("/sva/pRRU/api/deleteData",{floorNo:floorNo},function(data){
    		if(!data.error){
        		var obj = document.getElementById(floorNo);
        		obj=obj.parentNode;
        		obj=obj.parentNode;
        		obj.parentNode.removeChild(obj);
			}
    	});
    }else
    	{
    	return false;
    	}
    };
    
    var removeOption = function(renderId){
		$('#'+renderId+' .addoption').remove().trigger("liszt:updated");
	};

	var refreshSignal = function(){
		clearTimeout(timer);
		timer = setTimeout(function(){
			$.get("/sva/pRRU/api/getSignal",function(data){
				Ploy.paper.clear();
				for(var i = 0; i<data.data.length; i++){
					var neCode = data.data[i].gpp,
						rsrp = data.data[i].rsrp,
						selector = $("#"+neCode);
					if(selector){
						var x = parseFloat(selector.css("left").replace("px","")),
							y = parseFloat(selector.css("top").replace("px",""));
						Ploy.paper.circle(x, y, 20).animate({fill: "#223fa3","fill-opacity":0.3,"stroke":"rgb(34,63,163)", r:rsrp}, 500);
					}
				}
				//refreshSignal();
			});
		},4000);
	};
	
    return {
    	
    	initSelect: function(){
    		$.get("/sva/store/api/getData?t="+Math.random(),function(data){
    			if(!data.error){
    				updateList("placeSel",data.data);
    			}
    		});
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

            	var floorNo = $(this).data("id");
	           	 deleteMsg(floorNo);
            });
            
            $("input[data-type='edt']").live("click",function(e){
               	$(".sameInfo").removeClass("Validform_wrong");
               	$(".sameInfo").text("");
            	$(".demoform").Validform().resetForm();
	           	var rowObj = $(this)[0].parentNode.parentNode;
	           	var row = $(this).parent().parent();
	        	var data1 = oTable.fnGetData(row[0]);
				$("#idid").val(data1.floorNo);
				var floor = data1.floor;
				var place = data1.place;
				var path = data1.path;
				$("#placeSel").val(data1.placeId);
				$("#textfield").val(place+"_"+floor+".xml");
	           	//MapMng.deleteMap(xSpot, ySpot, zSpot, place);
				$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:data1.placeId},function(data){
					if(!data.error){
						updateFloorList("zSel",data.data,floor);
					}
				});
	           	$("#editBox").show();
           	 
            });
            
            $("a[data-type='point']").live("click",function(e){
            	var path = $(this).data("path");
            	var width = $(this).data("width");
            	var height = $(this).data("height");
            	var id =  $(this).data("id");
            	var scale =  $(this).data("scale");
            	var coordinate =  $(this).data("coordinate");
            	var origX = $(this).data("x");
            	var origY = $(this).data("y");
				var MAXWIDTH  = document.getElementById("body").offsetWidth * 0.8;  
            	var rect = clacImgZoomParam(MAXWIDTH,500,width,height);
            	$("#preview").css({
					"width" : rect.width + "px",
					"height" : rect.height + "px",
					"background-image": "url(../upload/" + path + ")",
					"background-size":"cover",
					"-moz-background-size": "cover",
					"margin":"0 auto"

				});
            	var imgScale = width/(rect.width);
            	$.get("/sva/pRRU/api/getPrruInfo",{floorNo:id},function(data){
    				if(!data.error){
    					$("#count").text(data.data.length);
    					var obj = {
    							x:origX,
    							y:origY,
    							scale:scale,
    							imgScale:imgScale,
    							coordinate:coordinate,
    							width:rect.width,
    							height:rect.height
    					};

    					Ploy.clearPaper();
    					Ploy.addPoints(data.data,obj);
    					//Ploy.paper = Raphael('preview', rect.width, rect.height);
    	            	//refreshSignal();
    				}
            		
            	});
            	//$("#movie").append(html);
            	$("#myModal").modal("show");
            	//$("#movie").append(html);
            });
            
            $("#confirm").on("click",function(e){
            	var param = {id:$("#idid").val(),floor:$("#zSel").val()};
            	var check = validForm.check(false);
            	if(check){
            	  $.post("/sva/pRRU/api/checkStore",param,function(data){
            			var b = data.data;
                		if (b)
                		{
                			$(".demoform").submit();
    					}else
    						{
    			              $(".sameInfo").addClass("Validform_wrong");
    			              $(".sameInfo").text(i18n_sameplace);
    						}
            		});

            		
            	}
            });
    	},
        
        initMsgTable:function(){
        	$.get("/sva/pRRU/api/getData?t="+Math.random(),function(data){
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
//        						"bVisible": false,
        						"mData": "place",
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
        	                    	var html = "" +
        	                    		'<input type="button" data-type="edt" style="width: 54px;height:30px;font-size: 13px;font-family:inherit;" data-place="'+full.place+'"data-floor="'+full.floor+'" value="'+i18n_edit+'" data-floorId="'+full.floorId+' " data-id="'+full.floorNo+' " id="'+full.floorNo+' ">' +
        	                    		'<input type="button" data-type="del" style="width: 54px;height:30px;font-size: 13px;font-family:inherit;" data-floor="'+full.floor+'" id="'+full.floorNo+'" data-id="'+full.floorNo+'" data-place="'+full.place+'"  value="'+i18n_delete+'">'+
        	                    		'<a data-toggle="modal" data-type="point" href="#myModal1" data-type="preview"  role="button" class="btn"  style="font-size:13px;" data-width="'+full.imgWidth+'" data-x="'+full.xo+'" data-y="'+full.yo+'" data-coordinate="'+full.coordinate+'" data-height="'+full.imgHeight+'" data-path="'+full.path+'" data-floor="'+full.floor+'" data-place="'+full.place+'" data-scale="'+full.scale+'" data-id="'+full.floorNo+'" >'+i18n_Preview+'</a>';
        	                        return html;
        	                    }
        	                }
        				]
        			});
        		}        		
        	});
        }
    };

}();
function checkMsg()
{
	var placeSel =$("#placeSel").val();
	var floor=$("select[name='floor']").find("option:selected").text();
	var file =$("input[name='file']").val();
	var id = $("#idid").val();
	var textval = $("#textfield").val();
	if (placeSel==""||floor=="") {
		alert(i18n_info);
		return false;
	}
	if (id=="") {
	if (file!="") {
		var fi =file.split(".")[file.split(".").length-1];
		if (fi!="xml"&&fi!="XML") {
			alert(i18n_format);
			return false;
		}
	}
	if (file=="") {
		alert(i18n_info);
		return false;
	}
	}else
		{
		if (textval=="") 
		{
			alert(i18n_info);
			return false;
		}
		if (file!="") {
			var fi =file.split(".")[file.split(".").length-1];
			if (fi!="xml") {
				alert(i18n_format);
				return false;
			}
		}
		
		}
	
}

function addMap()
{
	$(".demoform").Validform().resetForm();
	clearinfo();
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
	$("#idid").val("");
	$("#textfield").val("");
	$("#fileField").val("");
	$("#idid").val("");
   	$(".sameInfo").removeClass("Validform_wrong");
   	$(".sameInfo").text("");
	$("#zSel").find("option").remove();

}


function clickFile()
{
	var file = document.getElementById("fileField");
	file.click();
}
function fileValue(file)
{
	document.getElementById('textfield').value=file.value;	
	$("#textfield").blur();
}

function closeModel()
{
	$("#myModal1").modal('hide');
}
function showModel()
{
$("#myModal1").show();	
}

