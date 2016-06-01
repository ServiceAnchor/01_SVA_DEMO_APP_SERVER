var SellerMng = function () {
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
	    		options += '<option class="addoption" data-width="'+info.imgWidth+'" data-height="'+info.imgHeight+'" data-x="'+info.xo+'"data-y="'+info.yo+'" data-path="'+info.path+'" data-scale="'+info.scale+'" data-coordinate ="'+info.coordinate+'" selected=true value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
	    	}else{
	    		options += '<option class="addoption" data-width="'+info.imgWidth+'" data-height="'+info.imgHeight+'" data-x="'+info.xo+'"data-y="'+info.yo+'" data-path="'+info.path+'" data-scale="'+info.scale+'" data-coordinate ="'+info.coordinate+'" value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
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
	    		options += '<option class="addoption" data-width="'+info.imgWidth+'" data-height="'+info.imgHeight+'" data-x="'+info.xo+'"data-y="'+info.yo+'" data-path="'+info.path+'" data-scale="'+info.scale+'" data-coordinate ="'+info.coordinate+'" selected=true value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
	    	}else{
	    		options += '<option class="addoption" data-width="'+info.imgWidth+'" data-height="'+info.imgHeight+'" data-x="'+info.xo+'"data-y="'+info.yo+'" data-path="'+info.path+'" data-scale="'+info.scale+'" data-coordinate ="'+info.coordinate+'" value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
	    	}
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    return;
	};
	
	var clacImgZoomParam = function( maxWidth, maxHeight, width, height,x,y,coordinate ){  
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
    	$.post("/sva/seller/api/deleteData",{id:id},function(data){
    		if(!data.error){
        		var obj = document.getElementById(id);
        		obj=obj.parentNode;
        		obj=obj.parentNode;
        		obj.parentNode.removeChild(obj);
    		}
    	});
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
    	},
    	
    	bindClickEvent: function(){
    		// 地点下拉列表修改 触发楼层下拉列表变化
    		$("#placeSel").on("change", function(){
				var placeId = $("#placeSel").val();
//				yanzheng(this);
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
    			var opts = $("#zSel option");
    			var selectedOpt = opts[$(this)[0].selectedIndex];
    			if($("#zSel").val() != " "){
					var width = $(selectedOpt).data("width"),
						height = $(selectedOpt).data("height"),
						path = $(selectedOpt).data("path"),
						coordinate = $(selectedOpt).data("coordinate"),
						scale = $(selectedOpt).data("scale");
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
						"coordinate" : rect.coordinate,
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
    		
    		$('a[href="#myModal1"]').on("click",function(e){
    			if(typeof($(this).attr("disabled"))!="undefined"){
        			e.preventDefault();
        			return false;
        		}
     			$("#infoScale").text("");
     			$("#alertBoxScale").hide();
         		Ploy.clearPaper();
     			$("#pointX").val("");
         		$("#pointY").val("");
         	});       	

         	$('#preview').click(function(e){

                var left=e.pageX;
                var top=e.pageY;
                
                var datas = Ploy.getData();
            	if(datas.length<1){
                	Ploy.addPoint(top,left);
                	var t=top-$('#preview').offset().top;
                    var l=left-$('#preview').offset().left;
                    $("#pointX").val(l);
             		$("#pointY").val(t);
                }

            });
         	
         	$(".clearPaper").on("click",function(e){
         		Ploy.clearPaper();
     			$("#pointX").val("");
         		$("#pointY").val("");
         	});
         	
         	$("#Ok").on("click",function(e){
         		//判断原点位置
         		var coordinate = rect.coordinate;
         		var px =$("#pointX").val();
         		if(px != ""){
	         		switch (coordinate){
	         		case "ul":
	         			 px =$("#pointX").val();
	             		var py =$("#pointY").val();
	         			break;
	         		case "ll":
	         			imagey = rect.height;
	         			var px =$("#pointX").val();
	             		var py =imagey-$("#pointY").val();
	         			break;
	         		case "ur":
	         			imagex = rect.width;
	         			var px =imagex-$("#pointX").val();
	             		var py =$("#pointY").val();
	         			break;
	         		case "lr":
	         			imagex = rect.width ;
	         			imagey = rect.height;
	         			var px =imagex-$("#pointX").val();
	             		var py =imagey-$("#pointY").val();
	         			break;
	         		}
         		}
     			if(px){
     				var scale = rect.scale;
         			$("#xSpotId").val(((parseFloat(px)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.x)).toFixed(2));
         			$("#ySpotId").val(((parseFloat(py)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.y)).toFixed(2));
         			$("#myModal1").modal('hide');
         			$("#xSpotId").blur();
         			$("#ySpotId").blur();
         			$("#alertBoxScale").hide();
         		}else{	         			
         			$("#infoScale").text(i18n_choose_title);
         			$("#alertBoxScale").show();
         		}
         	});

            $("input[data-type='del']").live("click",function(e){
	           	 var id = $(this).data("id");
	           	 deleteMsg(id);
            });
            
            $("input[data-type='edt']").live("click",function(e){
            	$(".demoform").Validform().resetForm();
	           	$('a[href="#myModal1"]').attr("disabled",false);
	        	var row = $(this).parent().parent();
	        	var data = oTable.fnGetData(row[0]);
	        	var placeId = $(this).data("placeid");
	    		$("#editBox").show();
	    		$("#placeSel").val(placeId);
	    		var placeId = $("#placeSel").val(),
	    			floor = data.floor;
	
				$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeId}, function(data){
					if(!data.error){;
						updateFloorList("zSel",data.data,floor,function(){$("#zSel").change();});
					}
				});
				
				$("#xSpotId").val(data.xSpot);
				$("#ySpotId").val(data.ySpot);
				$("#infoId").val(data.info);
				$("#isEnableId").val(data.isEnable);
				$("#textfield").val(data.pictruePath);
				$("#textfield1").val(data.moviePath);
				$("#isVipId").val(data.isVip);
				$("#idid").val(data.id);
			});
            
            $("input[data-type='fuzhi']").live("click",function(e){
            	$(".demoform").Validform().resetForm();
	           	$('a[href="#myModal1"]').attr("disabled",false);
	        	var row = $(this).parent().parent();
	        	var data = oTable.fnGetData(row[0]);
	        	var placeId = $(this).data("placeid");
	    		$("#editBox").show();
	    		$("#placeSel").val(placeId);
	    		var placeId = $("#placeSel").val(),
	    			floor = data.floor;
	
				$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeId}, function(data){
					if(!data.error){;
						updateFloorList("zSel",data.data,floor,function(){$("#zSel").change();});
					}
				});
				
				$("#xSpotId").val(data.xSpot);
				$("#ySpotId").val(data.ySpot);
				$("#infoId").val(data.info);
				$("#isEnableId").val(data.isEnable);
				$("#textfield").val(data.pictruePath);
				$("#textfield1").val(data.moviePath);
				$("#isVipId").val(data.isVip);
//				$("#idid").val(data.id);
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
            
            $("#play").click(function(e){
            	var m = document.getElementById("video");
            	$("#movieCover").hide();
            	m.play();
            });
            
            $('#myModal').on('hidden', function () {
            	$("#video").attr("src","");
            });
    	},
        
        initSellerTable:function(){
        	$.get("/sva/seller/api/getTableData?t="+Math.random(),function(data){
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
        						"mData": "info",
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
        						"aTargets": [3],
        						"mData": "xSpot"
        					},
        					{ 
        						"aTargets": [4],
        						"mData": "ySpot"
        					},
        					{
								"aTargets": [5],
								"bSortable": true,
								"bFilter": false,
								"mData": function(source, type, val) {
									return "";
								},
							    "mRender": function ( data, type, full ) {
							    	var html ;
							    	if (full.isEnable=="开启") {
										html = i18n_open;
									}
							    	if (full.isEnable=="关闭") {
										html = i18n_close;
									}
							        return html;
							      }
							},
							{
								"aTargets": [6],
								"bSortable": true,
								"bFilter": false,
								"mData": function(source, type, val) {
									return "";
								},
								"mRender": function ( data, type, full ) {
									var html ;
									if (full.isVip=="是") {
										html = i18n_true;
									}
									if (full.isVip=="否") {
										html = i18n_false;
									}
									return html;
								}
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
        	                    		'<input type="button" style="width: 53px;height:30px;font-size: 13px;font-family:inherit;" data-type="fuzhi" data-placeid="'+full.placeId+'" data-id="'+full.floorid+'" value="'+i18n_fuzhi+'">' +
        	                    		'<input type="button" class="btn" data-type="edt" style="font-size: 13px;font-family:inherit;" data-placeId="'+full.placeId+'" value="'+i18n_edit+'" id="'+full.id+' ">' +
        	                    		'<input type="button" class="btn" data-type="del" style="font-size: 13px;font-family:inherit;" data-id="'+full.id+'" id="'+full.id+'" value="'+i18n_delete+'">'+
        	                    		'<a data-type="preview" role="button" class="btn" data-pictrue="'+full.pictruePath+'" data-movie="'+full.moviePath+'">'+i18n_Preview+'</a>';
											
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
function checkSeller()
{
	var placeSel=$("#placeSel").val();
	var floor=$("select[name='floorNo']").find("option:selected").text();
	var xSpot= $("input[name='xSpot']").val();	
	var ySpot= $("input[name='ySpot']").val();	
	var info= $("input[name='info']").val();	
	var isEnable= $("select[name='isEnable']").val();
	var isVip= $("select[name='isVip']").val();
	var file =$("input[name='file']").val();
	if (file!="") {
		var fi =file.split(".")[file.split(".").length-1];
		if (fi!="jpg"&&fi!="png"&&fi!="PNG"&&fi!="GPG") {
			alert(i18n_format);
			return false;
		}
	}
	var file1 =$("input[name='file1']").val();
	if (file1!="") {
		var fi1 =file1.split(".")[file1.split(".").length-1];
		if (fi1!="mp4"&&fi1!="MP4") {
			alert(i18n_movie);
			return false;
		}
	}
	if (floor==""||xSpot==""||ySpot==""||info==""||isEnable==""||isVip=="") 
	{
		alert(i18n_info);
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
function addMap()
{
	$(".demoform").Validform().resetForm();
	clearinfo();
	$("#editBox").show();	
//	$.post("/sva/api/getPrruInfo",{floorNo:42222},function(data){
//		console.log(data);
//	});
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
	$("#xSpotId").val("");
	$("#ySpotId").val("");
	$("#infoId").val("");
	$("#isEnableId").val("");
	$("#textfield").val("");
	$("#textfield1").val("");
	$("#fileField").val("");
	$("#fileField1").val("");
	$("#isVipId").val("");
	$("#idid").val("");
	$("#zSel").find("option").remove();
}

function clickFile()
{
var file = document.getElementById("fileField");
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
$("#movie").empty();
var pictrue = $(event).data("pictrue");
var movie = $(event).data("movie");
var bgImgStr = "/sva/upload/" + pictrue;
var bgMovie2 = "/sva/upload/" + movie;
var bgMovie  = "http://www.w3school.com.cn/i/movie.mp4" ;
var html = '<video width=798px height=426px controls="controls"  autoplay="autoplay"> <source  type="video/mp4" src ='+bgMovie2+'> </video>';
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

