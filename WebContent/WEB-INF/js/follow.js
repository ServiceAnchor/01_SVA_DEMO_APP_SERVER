//	var refreshHeatmapData = function(){
//		$.post("/sva/heatmap/api/getData5",{place:place, floorNo:floorNo},function(data){
//			if(!data.error){
//				if(data.data && data.data.length>0){
//					//var points = {max:1,data:dataFilter(data)};
//					var points = dataFilter(data.data,origX,origY,scale,imgWidth,imgHeight,coordinate,imgScale);
//					
//					var dataObj = {
//							max:pointVal,
//							min:1,
//							data:points
//					};
//					heatmap.setData(dataObj);
//					$("#legend").show();
//				}else{
//					$("#legend").hide();
//				}
//				$("#count").text(data.data.length);
//			}
//			timer = setTimeout("refreshHeatmapData();", 4000);
//		});
//	};
	
//	var dataFilter = function(data, xo, yo, scale, width, height, coordinate, imgScale){
//		var list = [];
//		xo = parseFloat(xo);
//		yo = parseFloat(yo);
//		scale = parseFloat(scale);
//		switch(coordinate){
//		case "ul":
//			for(var i in data){
//				var point ={
//						x:(data[i].x/10*scale+xo*scale)/imgScale+Math.random()/10,
//						y:(data[i].y/10*scale+yo*scale)/imgScale+Math.random()/10,
//						value:1
//				};
//				list.push(point);
//			}
//			break;
//		case "ll":
//			for(var i in data){
//				var point ={
//						x:(data[i].x/10*scale+xo*scale)/imgScale+Math.random()/10,
//						y:height-(data[i].y/10*scale+yo*scale)/imgScale+Math.random()/10,
//						value:1
//				};
//				list.push(point);
//			}
//			break;
//		case "ur":
//			for(var i in data){
//				var point ={
//						x:width-(data[i].x/10*scale+xo*scale)/imgScale+Math.random()/10,
//						y:(data[i].y/10*scale+yo*scale)/imgScale+Math.random()/10,
//						value:1
//				};
//				list.push(point);
//			}
//			break;
//		case "lr":
//			for(var i in data){
//				var point ={
//						x:width-(data[i].x/10*scale+xo*scale)/imgScale+Math.random()/10,
//						y:height-(data[i].y/10*scale+yo*scale)/imgScale+Math.random()/10,
//						value:1
//				};
//				list.push(point);
//			}
//			break;		
//		}
//		
//		return list;
//	};
	
	var calImgSize = function(width, height){
		var newWidth,newHeight,imgScale;
		var divWidth = parseInt($("#divCon").css("width").slice(0,-2));
		
		if(divWidth/600 > width/height){
			newHeight = 600;
			imgScale = height/newHeight;
			newWidth = width/imgScale;
		}else{
			newWidth = divWidth;
			imgScale = width/newWidth;
			newHeight = height/imgScale;
		}
		
		return [imgScale,newWidth,newHeight];		
	};
	
	var Heatmap5 = function () {
	
	/**
	* 将对应信息填充到对应的select
	* @ param renderId [string] 标签id
	* @ param data [array] 列表数据
	*/
	var updateList = function(renderId,data,callback){
		var sortData = data.sort(function(a, b) {
			return a.name - b.name;
		});
		var len = sortData.length;
		var options = '';
		for ( var i = 0; i < len; i++) {
			options += '<option class="addoption" value="'
					+ sortData[i].id + '">' + HtmlDecode3(sortData[i].name)
					+ '</option>';
		}
		removeOption(renderId);
		$('#' + renderId).append(options);
		if (callback) {
			callback();
		}
		return;
	};
	
	/**
	* 将对应信息填充到对应的select
	* @ param renderId [string] 标签id
	* @ param data [array] 列表数据
	*/
	var updateFloorList = function(renderId,data,callback){
	    var sortData = data.sort(function(a,b){return a.floor - b.floor;});
	    var len = sortData.length;
	    var options = '';
	    for(var i=0;i<len;i++){
	        options += '<option class="addoption" value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    if(callback){
	        callback();
	    }
	    return;
	};
	
	var updateFloorList1 = function(renderId,data,callback){
		$("#zSel").find("option").remove(); 
	    var sortData = data.sort(function(a,b){return a.userID - b.userID;});
	    var len = sortData.length;
	    var options = '';
	    for(var i=0;i<len;i++){
	        options += '<option class="addoption" value="'+sortData[i].userID+'">' + sortData[i].userID +'</option>';
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
    var initHeatmap = function (floorNo) {
    	//$("#mapContainer").css("background-image","");
		//$("#heatmap").empty();
    	$.post("/sva/heatmap/api/getMapInfoByPosition",{floorNo:floorNo},function(data){
			if(!data.error){
				if(data.bg){
					// 全局变量赋值
					origX = data.xo;
					origY = data.yo;
					bgImg = data.bg;
					bgImgWidth = data.bgWidth;
					bgImgHeight = data.bgHeight;
					scale = data.scale;
					coordinate = data.coordinate;
					// 设置背景图片
					var bgImgStr = "url(../upload/"+bgImg+")";
					var imgInfo = calImgSize(bgImgWidth,bgImgHeight);
					imgScale = imgInfo[0];
					imgWidth = imgInfo[1];
					imgHeight = imgInfo[2];
					console.log(imgInfo);
					$("#preview").css({"width":imgWidth+"px","height":imgHeight+"px","background-image":bgImgStr,"background-size":imgWidth+"px "+imgHeight+"px","margin":"0 auto"});
					
//					configObj.onExtremaChange = function(data) {
//						updateLegend(data);
//					};
//					heatmap = h337.create(configObj);
//					$.post("/sva/heatmap/api/getData5",{place:place, floorNo:floorNo},function(data){
//						if(!data.error){
//							if(data.data && data.data.length>0){
//								//var points = {max:1,data:dataFilter(data)};
//								var points = dataFilter(data.data,origX,origY,scale,imgWidth,imgHeight,coordinate,imgInfo[0]);
//								var dataObj = {
//										max:pointVal,
//										min:1,
//										data:points
//								};
//								heatmap.setData(dataObj);
//								$("#legend").show();
//							}
//							$(".countInfo").show();
//							$("#count").text(data.data.length);
//						}
//					});
//					clearTimeout(timer);
//					timer = setTimeout("refreshHeatmapData();", 4000);
					//refreshHeatmapData();
				}
			}
		});
    };
	
//	
//	/* legend code */
//	var updateLegend = function(data) {
//		// the onExtremaChange callback gives us min, max, and the
//		// gradientConfig
//		// so we can update the legend
//		min.innerHTML = data.min;
//		max.innerHTML = data.max;
//		// regenerate gradient image
//		if (data.gradient != gradientCfg) {
//			gradientCfg = data.gradient;
//			var gradient = legendCtx.createLinearGradient(0, 0, 100, 1);
//			for ( var key in gradientCfg) {
//				gradient.addColorStop(key, gradientCfg[key]);
//			}
//
//			legendCtx.fillStyle = gradient;
//			legendCtx.fillRect(0, 0, 100, 10);
//			gradientImg.src = legendCanvas.toDataURL();
//		}
//	};
//	/* legend code end */
//
//	var updateTooltip = function(x, y, value) {
//		// + 15 for distance to cursor
//		var transform = 'translate(' + (x + 15) + 'px, ' + (y + 15) + 'px)';
//		tooltip.style.MozTransform = transform; /* Firefox */
//		tooltip.style.msTransform = transform; /*
//												 * IE (9+) - note ms is
//												 * lowercase
//												 */
//		tooltip.style.OTransform = transform; /* Opera */
//		tooltip.style.WebkitTransform = transform; /* Safari and Chrome */
//		tooltip.style.transform = transform; /* One day, my pretty */
//		tooltip.innerHTML = value;
//	};

    return {
    	//初始化下拉列表
    	initDropdown: function(){
    		$("#marketSel").chosen({width:220});
    		$("#floorSel").chosen({width:220});
    		$("#userId").chosen({width:220});
    		$("#timeId").chosen({width:220});
    		$.get("/sva/store/api/getData?t="+Math.random(), function(data) {
				if (!data.error) {
					var storeList = data.data;
					updateList("marketSel", storeList, function() {
						$('#marketSel').trigger("liszt:updated");
					});
				}
			});
    	},
    	
    	bindClickEvent: function(){
    		// 地点下拉列表修改 触发楼层下拉列表变化
    		$("#marketSel").chosen().change( function(){
				var placeId = $("#marketSel").val();
				$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeId}, function(data){
					if(!data.error){
						var floors = data.data;
						updateFloorList("floorSel",floors,function(){
							$('#floorSel').trigger("liszt:updated");
//							$('#confirm').prop("disabled", false);
						});
					}
				});
			});
    		
    		//  确认按钮点击  触发热力图刷新    		
    		$('#confirm').click(function(e){
    			var placeId = $("#marketSel").val();
				var floorNo = $("select[name='floorSelName']").find("option:selected").val();
			    if (placeId=="") {
			    	$("#marketSel").blur();
			    	return false;
				}
			    if (floorNo=="") {
			    	$("#floorSel").blur();
			    	return false;
				}
				if ($("#timeId").val()==""){
					$("#timeId").blur();
					return false;
				}
				if ($("#userId").val()=="") {
					$("#userId").blur();
					return false;
				}
				$("#userId").blur();
    			$.post("/sva/heatmap/api/getMark",{userId:$("#userId").val(),time:$("#timeId").val()}, function(data){
    				if(!data.error){
    					var obj = {
    							x:origX,
    							y:origY,
    							scale:scale,
    							imgScale:imgScale,
    							coordinate:coordinate,
    							width:imgWidth,
    							height:imgHeight
    					};

    					Ploy.clearPaper();
    					Ploy.makeFoldLine('#preview',data.data,obj);

    				}
    			});
    		});
    		
    		/*
			demoWrapper.onmousemove = function(ev) {
				var x = ev.layerX;
				var y = ev.layerY;
				var value = heatmap.getValueAt({
					x : x,
					y : y
				});
				
				var currentData = heatmap.getData();

				if(currentData.data.length){
					tooltip.style.display = 'block';
					updateTooltip(x, y, value);
				}
			};
			demoWrapper.onmouseout = function() {
				tooltip.style.display = 'none';
			};*/
    	},
    	userId: function(){
    		$("#marketSel").chosen();
    		$("#floorSel").chosen();
    		$("#floorSel").chosen().change( function(){
				Ploy.clearPaper();
      			$("#mainContent").show();
    			initHeatmap($("#floorSel").val());
    			$("#timeId").chosen().change();
    		});
    		$("#timeId").chosen().change( function(){
    			$.post("/sva/heatmap/api/getFlooNo",{floorNo:$("#floorSel").val(),time:$("#timeId").val()}, function(data){
					if(!data.error){
						updateFloorList1("userId",data.data,function(){
							$('#userId').trigger("liszt:updated");
						});
					}
    			});
    		});
    		
    	}  	
    	

    };

}();