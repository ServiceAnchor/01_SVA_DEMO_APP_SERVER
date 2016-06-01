
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
	    var options = '' ;
	    for(var i=0;i<len;i++){
	    	var info = sortData[i];
	        if(selectTxt == sortData[i].floor){
	    		options += '<option class="addoption" data-width="'+info.imgWidth+'" data-height="'+info.imgHeight+'"  data-x="'+info.xo+'"data-y="'+info.yo+'" data-path="'+info.path+'" data-scale="'+info.scale+'" data-coordinate ="'+info.coordinate+'" selected=true value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
	    	}else{
	    		options += '<option class="addoption" data-width="'+info.imgWidth+'" data-height="'+info.imgHeight+'"  data-x="'+info.xo+'"data-y="'+info.yo+'" data-path="'+info.path+'" data-scale="'+info.scale+'" data-coordinate ="'+info.coordinate+'" value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
	    	}
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    if(callback){
	        callback();
	    }
	    return;
	};
	var updateList1 = function(renderId,data,selectTxt,callback){
		var sortData = data.sort(function(a,b){return a.name - b.name;});
	    var len = sortData.length;
	    var options = '';
	    for(var i=0;i<len;i++){
	    	var info = sortData[i];
	        if(selectTxt == info.areaName){
	    		options += '<option class="addoption" selected=true value="'+info.id+'">' + HtmlDecode3(info.areaName) +'</option>';
	    	}else{
	    		options += '<option class="addoption" value="'+info.id+'">' + HtmlDecode3(info.areaName) +'</option>';
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
	    var options = "<option value=''></option>";
	    for(var i=0;i<len;i++){
	    	var info = sortData[i];
	        if(selectTxt == sortData[i].floor){
	    		options += '<option class="addoption" data-width="'+info.imgWidth+'" data-height="'+info.imgHeight+'"  data-x="'+info.xo+'"data-y="'+info.yo+'" data-path="'+info.path+'" data-scale="'+info.scale+'" data-coordinate ="'+info.coordinate+'" selected=true value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
	    	}else{
	    		options += '<option class="addoption" data-width="'+info.imgWidth+'" data-height="'+info.imgHeight+'"  data-x="'+info.xo+'"data-y="'+info.yo+'" data-path="'+info.path+'" data-scale="'+info.scale+'" data-coordinate ="'+info.coordinate+'" value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
	    	}
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    return;
	};
	
	
	var clacImgZoomParam = function( maxWidth, maxHeight, width, height,x,y,coordinate){  
	    var param = {top:0, left:0, width:width, height:height,x:x,y:y,coordinate:coordinate};  
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
	    param.zoomScale = width / param.width;
	    return param;  
	};
    
    var deleteMsg = function(id){
    	if(confirm(i18n_deleteInfo))
    	{
    	$.post("/sva/electronic/api/deleteData",{id:id},function(data){
    		if(!data.error){
        		var obj = document.getElementById(id);
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

    return {
    	
    	initSelect: function(){
    		$.get("/sva/store/api/getData?t="+Math.random(),function(data){
    			if(!data.error){
    				updateList("placeSel",data.data);
    			}
    		});
    		$.get("/sva/input/api/getTableData?t="+Math.random(),function(data){
    			if(!data.error){
    				updateList1("shopId",data.data);
    			}
    		});
    	},
    	
    	bindClickEvent: function(){
    		// 地点下拉列表修改 触发楼层下拉列表变化
    		$("#placeSel").on("change", function(){
    			var placeId = $("#placeSel").val();
//    			yanzheng(this);
				$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeId}, function(data){
					if(!data.error){
						updateFloorList1("zSel",data.data);
					}
				});
	    		$('a[href="#myModal1"]').attr("disabled","disabled");
			});
			// 楼层下拉列表修改 触发选择坐标时地图变化
			$("#zSel").on("change", function(){
				var lastVal = this.validform_lastval; 
				if (lastVal!=null) 
				{
					this.validform_lastval = null; 
				}
//				if (lastVal==null&&val==null)
//				{
//					
//				}

//				$("#zSel").blur();

//				if (val==" ") {
//					$("#zSel").find("span").addClass("Validform_wrong");
//					$("#zSel").addClass("Validform_error");
//				}
				var opts = $("#zSel option");
				var selectedOpt = opts[$(this)[0].selectedIndex];
				if($("#zSel").val() != " "){
					var width = $(selectedOpt).data("width"),
						height = $(selectedOpt).data("height"),
						path = $(selectedOpt).data("path"),
						scale = $(selectedOpt).data("scale");
						coordinate = $(selectedOpt).data("coordinate");
						x = $(selectedOpt).data("x");
						y = $(selectedOpt).data("y");
					var MAXWIDTH  = document.getElementById("body").offsetWidth * 0.8;  
					var MAXHEIGHT = 500;  	
					rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, width, height,x,y,coordinate);
					rect.scale = scale;
					$("#preview").empty();
					$("#preview").css({
						"width" : rect.width + "px",
						"height" : rect.height + "px",
						"x" : rect.x + "px",
						"y" : rect.y + "px",
						"coordinate" :rect.coordinate,
						"margin-left" : rect.left + 'px',
						"margin-top" : rect.top + 'px',
						"background-image": "url(../upload/" + path + ")",
						"background-size":"cover",
						"-moz-background-size": "cover"
					});
					$('a[href="#myModal1"]').attr("disabled",false);
				}else{
					$('a[href="#myModal1"]').attr("disabled","disabled");
				}
			});		
			$('a[href="#myModal1"]').on("click",function(e){
				if(typeof($(this).attr("disabled"))!="undefined"){
					e.preventDefault();
					return false;
				}
     			$("#infoScale").text("");
     			$("#alertBoxScale").hide();
				Ploy.clearPaper();
				var xo = $("#xSpotId").val(),
     				yo = $("#ySpotId").val(),
     				range = $("#rangeSpotId").val();
				if(xo && yo && range){
					var r = parseFloat(range)*parseFloat(rect.scale)/rect.zoomScale.toFixed(2),
						coordinate = rect.coordinate,
						xtemp = (parseFloat(xo)+parseFloat(rect.x))*parseFloat(rect.scale)/rect.zoomScale.toFixed(2),
						ytemp = (parseFloat(yo)+parseFloat(rect.y))*parseFloat(rect.scale)/rect.zoomScale.toFixed(2);
					
					switch (coordinate){
	         		case "ul":
	         			var px = xtemp;
	             		var py = ytemp;
	         			break;
	         		case "ll":
	         			imagey = rect.height;
	         			var px = xtemp;
	             		var py = imagey-ytemp;
	         			break;
	         		case "ur":
	         			imagex = rect.width;
	         			var px = imagex-xtemp;
	             		var py = ytemp;
	         			break;
	         		case "lr":
	         			imagex = rect.width ;
	         			imagey = rect.height;
	         			var px = imagex-xtemp;
	             		var py = imagey-ytemp;
	         			break;
	         		}
					Ploy.makeSquare(px,py,r,$('#preview').width(),$('#preview').height(),"preview");
				}
			});
			$('#preview').click(function(e){
				if(!Ploy.square){
					var left=e.pageX;
					var top=e.pageY;
					var t=top-$('#preview').offset().top;
					var l=left-$('#preview').offset().left;
					Ploy.makeSquare(l,t,50,$('#preview').width(),$('#preview').height(),"preview");					
				}
				
			});
         	$(".clearPaper").on("click",function(e){
         		Ploy.clearPaper();
         	});
         	
         	$("#Ok").on("click",function(e){	
         		//判断原点位置
         		var coordinate = rect.coordinate;
         		if(Ploy.square != null){
         			var range = Ploy.square.square.attr("width")/2,
	         			xtemp = Ploy.square.square.attr("x")+range,
	         			ytemp = Ploy.square.square.attr("y")+range;
	         		switch (coordinate){
	         		case "ul":
	         			var px = xtemp;
	             		var py = ytemp;
	         			break;
	         		case "ll":
	         			imagey = rect.height;
	         			var px = xtemp;
	             		var py = imagey-ytemp;
	         			break;
	         		case "ur":
	         			imagex = rect.width;
	         			var px = imagex-xtemp;
	             		var py = ytemp;
	         			break;
	         		case "lr":
	         			imagex = rect.width ;
	         			imagey = rect.height;
	         			var px = imagex-xtemp;
	             		var py = imagey-ytemp;
	         			break;
	         		}
         		}	
	         		
     			if(px){
     				var scale = rect.scale;
         			$("#xSpotId").val(((parseFloat(px)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.x)).toFixed(2));
         			$("#ySpotId").val(((parseFloat(py)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.y)).toFixed(2));
         			$("#rangeSpotId").val((range*rect.zoomScale/parseFloat(scale)).toFixed(2));
         			$("#myModal1").modal('hide');
         			$("#xSpotId").blur();
         			$("#ySpotId").blur();
         			$("#rangeSpotId").blur();
         			$("#alertBoxScale").hide();
         		}else{	         			
         			$("#infoScale").text(i18n_choose_title);
         			$("#alertBoxScale").show();
         		}
         	});

            $("input[data-type='del']").live("click",function(e){
        		var xSpot = $(this).data("xspot"),
           	 		ySpot = $(this).data("yspot"),
           	 		floorNo = $(this).data("floorno"),
        		id = $(this).data("id");
	           	 deleteMsg(id);
            });
            
            $("input[data-type='edt']").live("click",function(e){
            	$(".demoform").Validform().resetForm();
            	$('a[href="#myModal1"]').attr("disabled",false);
            	var placeId = $(this).data("placeid");
            	var shopId = $(this).data("shopid");
	           	var rowObj = $(this)[0].parentNode.parentNode;
	           	var row = $(this).parent().parent();
	        	var data1 = oTable.fnGetData(row[0]);
	           	var place = rowObj.childNodes[0].innerHTML,
	           	       shopName =rowObj.childNodes[3].innerHTML,
	           	 	   floor = rowObj.childNodes[1].innerHTML,
	           	 	   message = $(rowObj.childNodes[4].childNodes[0]).attr("title");


	 
	           	//MapMng.deleteMap(xSpot, ySpot, zSpot, place);
	           	$("#placeSel").val(placeId);
				$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeId}, function(data){
					if(!data.error){
						$("#textfield").val(data1.pictruePath);
						$("#textfield1").val(data1.moviePath);
						$("#shopId").val(shopId);
						$("#areaName").val(data1.electronicName);
						$("#idid").val(data1.id);
						updateFloorList("zSel",data.data,floor,function(){$("#zSel").change();});
					}
				});

	           	//$("#zSel").val(zSpot);

	           	$("input[name='message']").val(message);

	           	$("#editBox").show();
           	 
            });
            

            
            $("a[data-type='preview']").live("click",function(e){
            	$("#movieCover").show();
            	var pictrue = $(this).data("pictrue");
            	var movie = $(this).data("movie");
            	var bgImgStr = "/sva/upload/" + pictrue;
            	var bgMovie2 = "/sva/upload/" + movie;
            	var bgMovie  = "http://www.w3school.com.cn/i/movie.mp4" ;
            	//var html = '<video id="movieId" width="798px" height="426px" controls="controls" poster="/sva/images/logo.png" src ="'+bgMovie2+'"> </video>';
            	$("#pictureid").attr("src",bgImgStr);
            	$("#video").attr("src",bgMovie2);
            	//$("#movie").append(html);
            	$('#myTab a:last').tab('show');
            	$('#myTab a:first').tab('show');
            	$("#myModal").modal("show");
            	//$("#movie").append(html);
            });
            
        	$("#fileField").on("change",function(e){
        		var file = this;
        		document.getElementById('textfield').value=file.value;	
        		$("#textfield").blur();
        	});
        	
        	$("#fileField1").on("change",function(e){
        		var file = this;
        		document.getElementById('textfield1').value=file.value;	
        		$("#textfield1").blur();
        	});
            
            $("#play").click(function(e){
            	var m = document.getElementById("video");
            	$("#movieCover").hide();
            	m.play();
            });
            
            $('#myModal').on('hidden', function () {
            	$("#video").attr("src","");
            });
    	},
        
        initMsgTable:function(){
        	$.get("/sva/electronic/api/getTableData?t="+Math.random(),function(data){
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
    								if (data.length>10) {
    									var html = data.substring(0,10)+"...";
    									html = '<span title="'+data+'">'+HtmlDecode3(html)+'</span>';
    										return html;
    								}
    								return '<span title="'+HtmlDecode3(data)+'">'+HtmlDecode3(data)+'</span>';
    							}
        					},
        					{ 
        						"aTargets": [1],
        						"mData": "floor"
        					},
        					{ 
        						"aTargets": [2],
        						"mData": "shopName"
        					},
        					{ 
        						"aTargets": [3],
        						"mData": "electronicName"
        					},
        					{ 
        						"aTargets": [4],
        						"mData": "message",
	    	                    "mRender": function ( data, type, full ) {
									if (data.length>10) {
										var html = data.substring(0,10)+"...";
										html = '<span title="'+data+'">'+HtmlDecode3(html)+'</span>';
											return html;
									}
									return '<span title="'+HtmlDecode3(data)+'">'+HtmlDecode3(data)+'</span>';
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
        	                    		
        	                    		'<input type="button" data-type="edt" style="width: 54px;height:30px;font-size: 13px;font-family:inherit;" data-placeid="'+full.placeId+'" data-shopId="'+full.areaId+'" data-floorno="'+full.floorNo+'" value="'+i18n_edit+'" id="'+full.id+' ">' +
        	                    		'<input type="button" data-type="del" style="width: 54px;height:30px;font-size: 13px;font-family:inherit;" data-floorNo="'+full.floorNo+'" id="'+full.id+'" data-id="'+full.id+'" value="'+i18n_delete+'">'+
        	                    		'<a data-type="preview" role="button" class="btn"  style="font-size:13px;" data-floorNo="'+full.floorNo+'" id="'+full.id+'" data-pictrue="'+full.pictruePath+'" data-movie="'+full.moviePath+'">'+i18n_Preview+'</a>';
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
        }
    };

}();

function HtmlDecode2(str) {
	var str1 = str.replace(/&lt;/g,"<");
	var str2 = str1.replace(/&gt;/g,">");
	var str3 = str2.replace(/&amp;/g,"&");
	var str4 = str3.replace(/&quot;/g,"\"");
	var str5 = str4.replace(/&apos;/g,"\'");
    return str5;
}
function HtmlDecode3(str) {
	var str1 = str.replace(/</g,"&lt;");
	var str2 = str1.replace(/>/g,"&gt;");
	return str2;
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
	$('a[href="#myModal1"]').attr("disabled","disabled");
}
function clearinfo()
{
	$("#placeSel").val("");
	$("#shopNameId").val("");
	$("#messageId").val("");
	$("#timeInterval").val("");
	$("#xSpotId").val("");
	$("#ySpotId").val("");
	$("#rangeSpotId").val("");
	$("#isEnableId").val("");
	$("#idid").val("");
	$("#textfield").val("");
	$("#fileField").val("");
	$("#fileField1").val("");
	$("#textfield1").val("");
	$("#zSel").find("option").remove(); 
	$("#zSel").val("");
}
/*
function editMap(str)
{
    $("input[data-type='edit']").live("click",function(e){
    	var row = $(str).parent().parent();
    	var data = oTable.fnGetData(row[0]);
		$("#editBox").show();
		$("#placeSel").val(data.place);
		var place = $("#placeSel").val();
		$.post("/sva/heatmap/api/getFloorsByMarket",{place:place}, function(data1){
			if(!data1.error){
				var floors = _.pluck(data1.data,"floor");
				var flooNos = _.pluck(data1.data,"floorNo");
				
			    var sortData = floors.sort();
			    var sortData1 =flooNos.sort();
			    var len = sortData.length;
			    var options = '';
			    for(var i=0;i<len;i++){
			    	if(data.floor == sortData[i]){
			    		options += '<option class="addoption" selected=true value="'+sortData1[i]+'">' + sortData[i] +'</option>';
			    	}else{
			    		options += '<option class="addoption" value="'+sortData1[i]+'">' + sortData[i] +'</option>';
			    	}
			    }
			    var renderId = "zSel";
			    $('#'+renderId+' .addoption').remove().trigger("liszt:updated");
			    $("#zSel").append(options);
			    $("#zSel").val(data.floorNo);
			}
		});
//		$("#zSel").val(data.floor);
//		var option = '<option value='+data.floor+'selected=true>';
//		$("#zSel").append(option);
		$("#shopNameId").val(data.shopName);
		$("#messageId").val(data.message);
		$("#xSpotId").val(data.xSpot);
		$("#ySpotId").val(data.ySpot);
		$("#rangeSpotId").val(data.rangeSpot);
		$("#isEnableId").val(data.isEnable);
		$("#textfield").val(data.pictruePath);
		$("#textfield1").val(data.moviePath);
		$("#idid").val(data.id);
		

    });	
}*/
function clickFile()
{
	var file = document.getElementById("fileField");
	//document.getElementById('textfield').value=file.value;
	file.click();
}
function clickFile1()
{
	var file = document.getElementById("fileField1");
	//document.getElementById('textfield1').value=file.value;
	file.click();
}
function fileValue(file)
{

		document.getElementById('textfield').value=file.value;	
		$("#textfield").blur();
}
function fileValue1(file)
{
	document.getElementById('textfield1').value=file.value;	
	$("#textfield1").blur();
}
function pictrueMovie(event)
{
	var pictrue = $(event).data("pictrue");
	var movie = $(event).data("movie");
	var bgImgStr = "/sva/upload/" + pictrue;
	var bgMovie2 = "/sva/upload/" + movie;
	var bgMovie  = "http://www.w3school.com.cn/i/movie.mp4" ;
	var html = '<video width=798px height=426px controls="controls" autoplay="autoplay"> <source  type="video/mp4" src ='+bgMovie2+'> </video>';
	$("#pictureid").attr("src",bgImgStr);
	$("#movie").append(html);
	$('#myTab a:last').tab('show');
	$('#myTab a:first').tab('show');
		
	//	"width":300+"px",
	//	"height":300+"px"

}
function closeModel()
{
	$("#myModal").modal('hide');
	//$("#movie").empty();
}
