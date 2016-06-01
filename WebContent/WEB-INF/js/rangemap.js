$(document).ready(function(){
	$("#picture").hide();
});
var Rangemap = function() {
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
	var clacImgZoomParam = function(maxWidth, maxHeight, width, height,x,y,coordinate ){  
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

	var removeOption = function(renderId) {
		$('#' + renderId + ' .addoption').remove().trigger("liszt:updated");
	};
	
	var showInfo = function(param){
		$.post("/sva/rangemap/api/getInfoData",param,function(data){
			if(!data.error){				
				$("#total").text(data.total);		
			}
		});
	};
	
	var showChart = function(param){
		$.post("/sva/rangemap/api/getChartData",param,function(data){
			if(!data.error){
				if (data.data==null||data.data=="") 
				{
				$("#total").text(0);
				$("#max").text("");
				$("#min").text("");
				}else
				{
				var max = _.max(data.data,function(d){return d.number;});
				var min = _.min(data.data,function(d){return d.number;});
				$("#max").text(max.time);
				$("#min").text(min.time);
				}
				var resJson = {};
				var time = $("#select_time_begin_tab1").val();
				var dayTime = time.replace(/-/g,"/");
				for(var i=0; i<24; i++){
					if (i<10) 
					{
						var key = dayTime+" 0"+i+":00:00";
						resJson[key] = 0;	
					}else
						{
						var key = dayTime+" "+i+":00:00";
						resJson[key] = 0;
						}
				}
				var dataRes= data.data;
				for(var d in dataRes){
					if(resJson[dataRes[d].time] === 0){
						resJson[dataRes[d].time] = dataRes[d].number;
					}
				}
				var xTitle= _.keys(resJson);
				var yVal = _.values(resJson);
				console.log(xTitle);
				console.log(yVal);
				var myChart = echarts.init(document.getElementById("chart"));
				var option = {
				    title : {
				    	x: 'center',
				        text: i18n_title
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            dataView : {
				            	show: true, 
				            	title : i18n_dataview,
				            	readOnly: true,
				                lang: [i18n_dataview, i18n_close, i18n_refresh]
				            },
				            saveAsImage : {
				            	show: true,
				            	title : i18n_saveimg
				            }
				        }
				    },
				    calculable : false,
				    xAxis : [
				        {
				            type : 'category',
				            boundaryGap : false,
				            data : xTitle
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            axisLabel : {
				                formatter: '{value} '+i18n_person
				            }
				        }
				    ],
				    series : [
				        {
				            name:i18n_tag,
				            type:'line',
				            data: yVal,
				            markPoint : {
				                data : [
				                    {type : 'max', name: i18n_max}
				                   // ,{type : 'min', name: i18n_min}
				                ]
				            },
				            markLine : {
				                data : [
				                    {type : 'average', name: i18n_avg}
				                ]
				            }
				        }
				    ]
				};                  
				
				myChart.setOption(option);
			}
		});
	};

	return {

		initDropdown : function() {
			$.get("/sva/store/api/getData?t="+Math.random(),function(data){
				if(!data.error){
					updateList("marketSel",data.data);
				}
			});
		},
		
		showDate: function(id){
			WdatePicker({
				el : document.getElementById(id),
				lang : i18n_language,
				isShowClear : false,
				isShowToday:false,
				readOnly : true,
				dateFmt : 'yyyy-MM-dd',
				maxDate : '%y-%M-%d',
				skin : "twoer"
			});
		},
		
		bindClickEvent: function(){
			// 地点下拉列表修改 触发楼层下拉列表变化
			$("#marketSel").change( function(){
				var placeId = $("#marketSel").val();
				$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeId}, function(data){
					if(!data.error){
						var floors = data.data;
						updateFloorList("floorSel",floors);
					}
				});
				$("#picture").hide();
			});
			// 楼层下拉列表修改 触发选择坐标时地图变化
			$('#floorSel').on("change", function(){
				var opts = $("#floorSel option");
				var selectedOpt = opts[$(this)[0].selectedIndex];
				if($("#floorSel").val() != -1){
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
					$("#picture").show();
				}else{
					$("#picture").hide();
				}	
				
			});	
			//点击弹出地图
			$('#picture').on("click",function(e){				
				Ploy.clearPaper();
				$("#pointX1").val("");
				$("#pointY1").val("");
				$("#pointX2").val("");
				$("#pointY2").val("");
				$("#Ok").attr("disabled","disabled");
			});	
			 //地图选择点
			$('#preview').click(function(e){
				   var left=e.pageX;
	                var top=e.pageY;
	                var o={
	                        left:left,
	                        top:top
	                 };
	                var datas = Ploy.getData();
	                if(datas.length<2){
	                	Ploy.makeRect('#preview',o);
	                	//Ploy.addPoint(top,left);
	                	var t=top-$('#preview').offset().top;
	                    var l=left-$('#preview').offset().left;
		            	if(datas.length<1){
		                    $("#pointX1").val(l);
		             		$("#pointY1").val(t);
		                }else{
		                    $("#pointX2").val(l);
		             		$("#pointY2").val(t);
		             		$("#Ok").attr("disabled",false);
		                }
	                }
			});
			$(".clearPaper").on("click",function(e){
         		Ploy.clearPaper();
         		$("#pointX1").val("");
				$("#pointY1").val("");
				$("#pointX2").val("");
				$("#pointY2").val("");
				$("#Ok").attr("disabled",true);
         	});

         	$("#Ok").on("click",function(e){	
         		//判断原点位置
         		var px1 = $("#pointX1").val();
         		var px2;
         		var py1;
         		var py2;
         		
         		if(px1){
					var coordinate = rect.coordinate;
					switch (coordinate){
					case "ul":
						 px1 = $("#pointX1").val();
	             		 py1 = $("#pointY1").val();
	             		 px2 = $("#pointX2").val();
	             		 py2 = $("#pointY2").val();
	         			break;
	         		case "ll":
	         			imagey = rect.height;
	         			 px1 = $("#pointX1").val();
	             		 py1 = imagey-$("#pointY1").val();
	             		 px2 = $("#pointX2").val();
	             		if(px2){
	             			 py2 = imagey-$("#pointY2").val();
	             		}else{
	             			 py2 = $("#pointY2").val();
	             		}
	             		
	         			break;
	         		case "ur":
	         			imagex = rect.width ;
	             		 px1 =imagex-$("#pointX1").val();
	             		 py1 = $("#pointY1").val();
	             		 py2 = $("#pointY2").val();
	             		if(py2){
	             			 px2 = imagex-$("#pointX2").val();
	             		}else{
	             			 px2 = $("#pointX2").val();
	             		}
	             		
	         			break;
	         		case "lr":
	         			imagex = rect.width ;
	         			imagey = rect.height;
	         			var x1test = $("#pointX1").val();
	         			var x2test = $("#pointX2").val();
	         			if(x1test){
		             		 px1 = imagex-$("#pointX1").val();
		             		 py1 = imagey-$("#pointY1").val();
	         			}else{
	         				 px1 = $("#pointX1").val();
	         				 py1 = $("#pointY1").val();
	         			}
	         			if(x2test){
	         				 px2 = imagex-$("#pointX2").val();
		             		 py2 = imagey-$("#pointY2").val();
	         			}else{
	         				 px2 = $("#pointX2").val();
		             		 py2 = $("#pointY2").val();
	         			}
	         			break;
	         		}
	     				var scale = rect.scale;
	         			$("#myModal1").modal('hide');
	         			$("#alertBoxScale").hide();
	         			$("#x1").val(((parseFloat(px1)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.x)).toFixed(2));
	         			$("#y1").val(((parseFloat(py1)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.y)).toFixed(2));
	         			$("#x2").val(((parseFloat(px2)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.x)).toFixed(2));
	         			$("#y2").val(((parseFloat(py2)*rect.zoomScale)/parseFloat(scale)-parseFloat(rect.y)).toFixed(2));
    			    	$("#msgdemo2").removeClass("Validform_wrong");
    			    	$("#msgdemo2").removeClass("Validform_right");
    			    	$("#msgdemo2").text("");
	    			    if ($("#x1").val()<0) {
	    			    	$("#msgdemo2").addClass("Validform_wrong");
	    			    	$("#msgdemo2").text(i18n_error_x);
//	    			    	$("#x1").blur();
	    			    	return false;
	    			    }
	    			    if ($("#y1").val()=="") {
	    			    	$("#msgdemo2").addClass("Validform_wrong");
	    			    	$("#msgdemo2").text(i18n_null_y);
//	    			    	$("#y1").blur();
	    			    	return false;
	    				}
	    			    if ($("#y1").val()<0) {
	    			    	$("#msgdemo2").addClass("Validform_wrong");
	    			    	$("#msgdemo2").text(i18n_error_y);
//	    			    	$("#y1").blur();
	    			    	return false;
	    			    }
	    			    if ($("#x2").val()=="") {
	    			    	$("#msgdemo2").addClass("Validform_wrong");
	    			    	$("#msgdemo2").text(i18n_null_x1);
//	    			    	$("#x2").blur();
	    			    	return false;
	    				}
	    			    if (isNaN($("#x2").val())||$("#x2").val()<0) {
	    			    	$("#msgdemo2").addClass("Validform_wrong");
	    			    	$("#msgdemo2").text(i18n_error_x1);
//	    			    	$("#x2").blur();
	    			    	return false;
	    			    }
	    			    if ($("#y2").val()=="") {
	    			    	$("#msgdemo2").addClass("Validform_wrong");
	    			    	$("#msgdemo2").text(i18n_null_y1);
//	    			    	$("#y2").blur();
	    			    	return false;
	    				}
	    			    if (isNaN($("#x2").val())||$("#y2").val()<0) {
	    			    	$("#msgdemo2").addClass("Validform_wrong");
	    			    	$("#msgdemo2").text(i18n_error_y1);
//	    			    	$("#y2").blur();
	    			    	return false;
	    			    }
	    				$("#msgdemo2").removeClass("Validform_wrong");
	    				$("#msgdemo2").addClass("Validform_right");
	    				$("#msgdemo2").text(pa);
         		}else{	         			
         			$("#infoScale").text(i18n_choose_title);
         			$("#alertBoxScale").show();
         		}
         	});

    		
    		//  确认按钮点击  触发热力图刷新    		
    		$('#confirm').click(function(e){
			    if ($("#marketSel").val()=="") {
			    	$("#msgdemo2").addClass("Validform_wrong");
			    	$("#msgdemo2").text(i18n_store);
//			    	$("#marketSel").blur();
			    	return false;
				}
			    if ($("#floorSel").val()=="") {
			    	$("#msgdemo2").addClass("Validform_wrong");
			    	$("#msgdemo2").text(i18n_floor);
//			    	$("#floorSel").blur();
			    	return false;
				}
			    if ($("#select_time_begin_tab1").val()=="") {
			    	$("#msgdemo2").addClass("Validform_wrong");
			    	$("#msgdemo2").text(i18n_time);
//			    	$("#select_time_begin_tab1").blur();
			    	return false;
				}
			    if ($("#x1").val()=="") {
			    	$("#msgdemo2").addClass("Validform_wrong");
			    	$("#msgdemo2").text(i18n_null_x);
//			    	$("#x1").blur();
			    	return false;
				}
			    if ($("#x1").val()<0) {
			    	$("#msgdemo2").addClass("Validform_wrong");
			    	$("#msgdemo2").text(i18n_error_x);
//			    	$("#x1").blur();
			    	return false;
			    }
			    if ($("#y1").val()=="") {
			    	$("#msgdemo2").addClass("Validform_wrong");
			    	$("#msgdemo2").text(i18n_null_y);
//			    	$("#y1").blur();
			    	return false;
				}
			    if ($("#y1").val()<0) {
			    	$("#msgdemo2").addClass("Validform_wrong");
			    	$("#msgdemo2").text(i18n_error_y);
//			    	$("#y1").blur();
			    	return false;
			    }
			    if ($("#x2").val()=="") {
			    	$("#msgdemo2").addClass("Validform_wrong");
			    	$("#msgdemo2").text(i18n_null_x1);
//			    	$("#x2").blur();
			    	return false;
				}
			    if (isNaN($("#x2").val())||$("#x2").val()<0) {
			    	$("#msgdemo2").addClass("Validform_wrong");
			    	$("#msgdemo2").text(i18n_error_x1);
//			    	$("#x2").blur();
			    	return false;
			    }
			    if ($("#y2").val()=="") {
			    	$("#msgdemo2").addClass("Validform_wrong");
			    	$("#msgdemo2").text(i18n_null_y1);
//			    	$("#y2").blur();
			    	return false;
				}
			    if (isNaN($("#x2").val())||$("#y2").val()<0) {
			    	$("#msgdemo2").addClass("Validform_wrong");
			    	$("#msgdemo2").text(i18n_error_y1);
//			    	$("#y2").blur();
			    	return false;
			    }
    			var check = validForm.check(false);
    			if(!check)
    				{
    				return false;
    				}
    			var param = {
	    			placeId : $("#marketSel").val(),
	    			floorNo :$("select[name='floorSelName']").find("option:selected").val(),
	    			time : $("#select_time_begin_tab1").val(),
	    			x1 : (parseFloat($("#x1").val()))*10,
	    			y1 : (parseFloat($("#y1").val()))*10,
	    			x2 : (parseFloat($("#x2").val()))*10,
	    			y2 : (parseFloat($("#y2").val()))*10
    			};
    			var floor = $("select[name='floorSelName']").find("option:selected").text();
    			if (parseInt(param.x1)>=parseInt(param.x2)||parseInt(param.y1)>=parseInt(param.y2)) {
    				$("#msgdemo2").removeClass("Validform_right");
    				$("#msgdemo2").addClass("Validform_wrong");
    				$("#msgdemo2").text(i18n_coordinate);
    				return false;
    			}
				$("#msgdemo2").removeClass("Validform_wrong");
				$("#msgdemo2").addClass("Validform_right");
				$("#msgdemo2").text(pa);
    			if(param.placeId && param.floorNo && param.time){
    				$(".chartArea").show();
    				showChart(param);
    				showInfo(param);
    			}
    		});
		}
	};

}();