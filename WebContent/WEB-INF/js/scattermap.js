	
	var refreshHeatmapData = function(){
		$.post("/sva/heatmap/api/getData",{floorNo:floorNo,times:10},function(data){
			if(!data.error){
				if(data.data && data.data.length>0){
					//var points = {max:1,data:dataFilter(data)};
					var points = dataFilter(data.data,origX,origY,scale,
							imgWidth, imgHeight, coordinate, imgScale);
					var dataObj = {
							max:20,
							min:1,
							data:points
					};
					heatmap.setData(dataObj);
					$("#legend").show();
				}else{
					var canvas=document.getElementsByTagName('canvas')[0];
					var ctx=canvas.getContext('2d');
					ctx.clearRect(0,0,imgWidth,imgHeight);
					$("#legend").hide();
				}
				$("#count").text(data.data.length);
			}
			timer = setTimeout("refreshHeatmapData();", 4000);
		});
	};
	
	var dataFilter = function(data, xo, yo, scale,  width, height, coordinate,
			imgScale){
		var list = [];
		xo = parseFloat(xo);
		yo = parseFloat(yo);
		scale = parseFloat(scale);
		switch (coordinate){
		case "ul":
			for ( var i in data) {
				var point = {
					x : (data[i].x / 10 * scale + xo * scale) / imgScale+Math.random()/10,
					y : (data[i].y / 10 * scale + yo * scale) / imgScale+Math.random()/10,
					value : pointVal
				};
				list.push(point);
			}
			break;
		case "ll":
			for ( var i in data) {
				var point = {
					x : (data[i].x / 10 * scale + xo * scale) / imgScale+Math.random()/10,
					y : height - (data[i].y / 10 * scale + yo * scale) / imgScale+Math.random()/10,
					value : pointVal
				};
				list.push(point);
			}
			break;
		case "ur":
			for ( var i in data) {
				var point = {
					x : width - (data[i].x / 10 * scale + xo * scale) / imgScale+Math.random()/10,
					y : (data[i].y / 10 * scale + yo * scale) / imgScale+Math.random()/10,
					value : pointVal
				};
				list.push(point);
			}
			break;
		case "lr":
			for ( var i in data) {
				var point = {
					x : width - (data[i].x / 10 * scale + xo * scale) / imgScale+Math.random()/10,
					y : height - (data[i].y / 10 * scale + yo * scale) / imgScale+Math.random()/10,
					value : pointVal
				};
				list.push(point);
			}
			break;
		}
		
		return list;
	};
	
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
	
	var Scattermap = function () {
	
	/**
	* 将对应信息填充到对应的select
	* @ param renderId [string] 标签id
	* @ param data [array] 列表数据
	*/
	var updateList = function(renderId,data,selectTxt,callback){
		var sortData = data.sort(function(a, b) {
			return a.name - b.name;
		});
		var len = sortData.length;
		var options = '';
		for ( var i = 0; i < len; i++) {
			if(sortData[i].id == selectTxt){
				options += '<option class="addoption" selected value="'
					+ sortData[i].id + '">' + HtmlDecode3(sortData[i].name)
					+ '</option>';
			}else{
				options += '<option class="addoption" value="'
					+ sortData[i].id + '">' + HtmlDecode3(sortData[i].name)
					+ '</option>';
			}
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
	var updateFloorList = function(renderId,data,selectTxt,callback){
		var sortData = data.sort(function(a,b){return a.floor - b.floor;});
		var len = sortData.length;
		var options = '';
		for(var i=0;i<len;i++){
			if(sortData[i].floorNo == selectTxt){
				options += '<option class="addoption" selected value="'
					+ sortData[i].floorNo + '">' + sortData[i].floor
					+ '</option>';
			}else{
				options += '<option class="addoption" value="'
					+ sortData[i].floorNo + '">' + sortData[i].floor
					+ '</option>';
			}
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    if(callback){
	        callback();
	    }
	    return;
	};
    
    var initHeatmap = function (floorNo) {
    	$("#mapContainer").css("background-image","");
		$("#heatmap").empty();
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
					$("#mapContainer").css({"width":imgInfo[1]+"px","height":imgInfo[2]+"px","background-image":bgImgStr,"background-size":imgInfo[1]+"px "+imgInfo[2]+"px","margin":"0 auto"});
					
					heatmap = h337.create(configObj);
					$.post("/sva/heatmap/api/getData",{floorNo:floorNo},function(data){
						if(!data.error){
							if(data.data && data.data.length>0){
								//var points = {max:1,data:dataFilter(data)};
								var points = dataFilter(data.data,origX,origY,scale,imgWidth, imgHeight,
										coordinate,imgInfo[0]);
								var dataObj = {
										max:20,
										min:1,
										data:points
								};
								heatmap.setData(dataObj);
							}
							$(".countInfo").show();
							$("#count").text(data.data.length);
						}
					});
					clearTimeout(timer);
					timer = setTimeout("refreshHeatmapData();", 4000);
					//refreshHeatmapData();
				}
			}
		});
    };
	
	var removeOption = function(renderId){
		$('#'+renderId+' .addoption').remove().trigger("liszt:updated");
	};
	
	var changeFloor = function(placeId, floorNo, callback){
		$.post("/sva/heatmap/api/getFloorsByMarket", {
			placeId : placeId
		}, function(data) {
			if (!data.error) {
				var floors = data.data;
				updateFloorList("floorSel", floors, floorNo, function() {
					$('#floorSel').trigger("liszt:updated");
					callback();
				});
			}
		});
	}


    return {
    	//初始化下拉列表
    	initDropdown: function(){
    		$("#marketSel").chosen();
    		$("#floorSel").chosen();
    		var placeId = $.cookie("place");
			var floorNoTemp = $.cookie("floor");
			var array=new Array();
			var arrayfloor=new Array();
    		$.get("/sva/store/api/getData?t="+Math.random(),function(data){
    			if(!data.error){
    				var storeList = data.data;
    				updateList("marketSel",storeList,placeId,function(){
    					$('#marketSel').trigger("liszt:updated");
    					$("#marketSel option").each(function(){ //遍历全部option 
							//	var txt = $(this).text(); //获取单个text
							var val = $(this).val(); //获取单个value
							var node =val;
							array.push(node);
							
						}); 
						for(var i= 0;i<array.length;i++){
							if(array[i] == placeId){
								changeFloor(placeId,floorNoTemp,function(){
									$("#floorSel option").each(function(){ //遍历全部option 
										//	var txt = $(this).text(); //获取单个text
										var val = $(this).val(); //获取单个value
										var node =val;
										arrayfloor.push(node);
										
									}); 
									for(var i= 0;i<arrayfloor.length;i++){
										if(arrayfloor[i] == floorNoTemp){
											$('#confirm').click();
										}
										
									}
								});							
							}
						}
    				});
    			}
    		});
    	},
    	
    	bindClickEvent: function(){
    		// 地点下拉列表修改 触发楼层下拉列表变化
    		$("#marketSel").chosen().change( function(){
				var placeId = $("#marketSel").val();
				changeFloor(placeId);
			});
    		
    		//  确认按钮点击  触发热力图刷新    		
    		$('#confirm').click(function(e){
    			var placeId = $("#marketSel").val();
				floorNo = $("select[name='floorSelName']").find("option:selected").val();
				var floor = $("select[name='floorSelName']").find("option:selected").text();
			    if (placeId=="") {
			    	$("#marketSel").blur();
			    	return false;
				}
			    if (floorNo=="") {
			    	$("#floorSel").blur();
			    	return false;
				}
			    $("#floorSel").blur();
    			$("#mainContent").show();
    			initHeatmap(floorNo);
    			$.cookie("place", placeId, { expires: 30});
				$.cookie("floor", floorNo, { expires: 30});
    			
    		});
    	}

    };

}();