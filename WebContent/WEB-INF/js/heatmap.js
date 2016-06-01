
var refreshHeatmapData = function() {
	//var times = $("#time").val($.cookie("times"));	
	var times = $.cookie("times");
	$.post("/sva/heatmap/api/getData?t="+Math.random(), {floorNo : floorLoop,times:times}, function(data) {
		if (!data.error) {
			if (data.data && data.data.length > 0) {
				// var points = {max:1,data:dataFilter(data)};
				var points = dataFilter(data.data, origX, origY, scale,
						imgWidth, imgHeight, coordinate, imgScale);
				var dataObj = {
					max : pointVal,
					min : 1,
					data : points
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

var dataFilter = function(data, xo, yo, scale, width, height, coordinate,
		imgScale) {
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
				value : 1
			};
			list.push(point);
		}
		break;
	case "ll":
		for ( var i in data) {
			var point = {
				x : (data[i].x / 10 * scale + xo * scale) / imgScale+Math.random()/10,
				y : height - (data[i].y / 10 * scale + yo * scale) / imgScale+Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "ur":
		for ( var i in data) {
			var point = {
				x : width - (data[i].x / 10 * scale + xo * scale) / imgScale+Math.random()/10,
				y : (data[i].y / 10 * scale + yo * scale) / imgScale+Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "lr":
		for ( var i in data) {
			var point = {
				x : width - (data[i].x / 10 * scale + xo * scale) / imgScale+Math.random()/10,
				y : height - (data[i].y / 10 * scale + yo * scale) / imgScale+Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	}

	return list;
};

var calImgSize = function(width, height) {
	var newWidth, newHeight, imgScale;
	var divWidth = parseInt($("#divCon").css("width").slice(0, -2));

	if (divWidth / 600 > width / height) {
		newHeight = 600;
		imgScale = height / newHeight;
		newWidth = width / imgScale;
	} else {
		newWidth = divWidth;
		imgScale = width / newWidth;
		newHeight = height / imgScale;
	}

	return [ imgScale, newWidth, newHeight ];
};

var Heatmap = function() {

	/**
	 * 将对应信息填充到对应的select @ param renderId [string] 标签id @ param data [array]
	 *                   列表数据
	 */
	var updateList = function(renderId, data, selectTxt, callback) {
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
					+ sortData[i].id + '">' +HtmlDecode3(sortData[i].name)
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
	 * 将对应信息填充到对应的select @ param renderId [string] 标签id @ param data [array]
	 *                   列表数据
	 */
	var updateFloorList = function(renderId, data, selectTxt, callback) {
		var sortData = data.sort(function(a, b) {
			return a.floor - b.floor;
		});
		var len = sortData.length;
		var options = '';
		for ( var i = 0; i < len; i++) {
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
		if (callback) {
			callback();
		}
		return;
	};

	var initHeatmap = function(floorNo) {
		$("#mapContainer").css("background-image", "");
		$("#heatmap").empty();
		$.post("/sva/heatmap/api/getMapInfoByPosition", {
			floorNo : floorNo
		}, function(data) {
			if (!data.error) {
				if (data.bg) {
					// 全局变量赋值
					origX = data.xo;
					origY = data.yo;
					bgImg = data.bg;
					bgImgWidth = data.bgWidth;
					bgImgHeight = data.bgHeight;
					scale = data.scale;
					coordinate = data.coordinate;
					// 设置背景图片
					var bgImgStr = "url(../upload/" + bgImg + ")";
					var imgInfo = calImgSize(bgImgWidth, bgImgHeight);
					imgScale = imgInfo[0];
					imgWidth = imgInfo[1];
					imgHeight = imgInfo[2];
					console.log(imgInfo);
					$("#mapContainer").css({
						"width" : imgWidth + "px",
						"height" : imgHeight + "px",
						"background-image" : bgImgStr,
						"background-size" : imgWidth + "px " + imgHeight + "px",
						"margin" : "0 auto"
					});

					configObj.onExtremaChange = function(data) {
						updateLegend(data);
					};
					var times = $.cookie("times");
				//	var times = $("#time").val($.cookie("times"));
					heatmap = h337.create(configObj);
					$.post("/sva/heatmap/api/getData?t="+Math.random(), {
						floorNo : floorNo,times:times
					}, function(data) {
						if (!data.error) {
							if (data.data && data.data.length > 0) {
								// var points = {max:1,data:dataFilter(data)};
								var points = dataFilter(data.data, origX,
										origY, scale, imgWidth, imgHeight,
										coordinate, imgInfo[0]);
								var dataObj = {
									max : pointVal,
									min : 1,
									data : points
								};
								heatmap.setData(dataObj);
								$("#legend").show();
							}
							$(".countInfo").show();
							$("#count").text(data.data.length);
						}
					});
					clearTimeout(timer);
					timer = setTimeout("refreshHeatmapData();", 4000);
					// refreshHeatmapData();
				}
			}
		});
	};

	var removeOption = function(renderId) {
		$('#' + renderId + ' .addoption').remove().trigger("liszt:updated");
	};

	/* legend code */
	var updateLegend = function(data) {
//		if (data.max==1)
//		{
//			$("#min").css("display","none");
//			$("#minup").css("display","none");
//			$("#max").css("display","none");
//			$("#maxup").popover('destroy') ;
//			var htmlstrmin = '<i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i>'+"非常拥挤:人数大于1";
//			var option = {html:true,trigger:"hover",content:htmlstrmin,placement:"top"};
//			$("#maxup").html("非常拥挤:1");
//			$("#maxup").css("color","white");
//			$("#maxup").popover(option);
//		}
//		if (data.max==2)
//		{
//			$("#min").css("display","none");
//			$("#minup").css("display","none");
//			$("#max").show()  ;
//			$("#maxup").popover('destroy') ;
//			$("#max").popover('destroy') ;
//			var htmlstrmin = '<i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i>'+"非常拥挤:人数大于2";
//			var htmlstrmin1 = '<i style="color:rgba(251,255,0,1);" class="icon-male"></i><i style="color:rgba(251,255,0,1);" class="icon-male"></i><i style="color:rgba(251,255,0,1);" class="icon-male"></i>'+"拥挤:人数大于1";
//			var option = {html:true,trigger:"hover",content:htmlstrmin,placement:"top"};
//			var option1 = {html:true,trigger:"hover",content:htmlstrmin1,placement:"top"};
//			$("#max").popover(option1);
//			$("#maxup").popover(option);
//			$("#max").html("拥挤:1");
//			$("#max").css("color","white");
//			$("#maxup").html("非常拥挤:2");
//			$("#maxup").css("color","white");
//		}
//		if (data.max==3)
//		{
//			$("#min").css("display","none");
//			$("#minup").show()  ;
//			$("#max").show()  ;
//			$("#maxup").popover('destroy') ;
//			$("#max").popover('destroy') ;
//			$("#minup").popover('destroy') ;
//			var htmlstrmin2 = '<i style="color:rgba(73,255,0,1);" class="icon-male"></i><i style="color:rgba(73,255,0,1);" class="icon-male"></i>'+"一般:人数大于1";
//			var option2 = {html:true,trigger:"hover",content:htmlstrmin2,placement:"top"};
//			var htmlstrmin = '<i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i>'+"非常拥挤:人数大于3";
//			var htmlstrmin1 = '<i style="color:rgba(251,255,0,1);" class="icon-male"></i><i style="color:rgba(251,255,0,1);" class="icon-male"></i><i style="color:rgba(251,255,0,1);" class="icon-male"></i>'+"拥挤:人数大于2";
//			var option = {html:true,trigger:"hover",content:htmlstrmin,placement:"top"};
//			var option1 = {html:true,trigger:"hover",content:htmlstrmin1,placement:"top"};
//			$("#max").popover(option1);
//			$("#maxup").popover(option);
//			$("#minup").popover(option2);
//			$("#max").html("拥挤:2");
//			$("#max").css("color","white");
//			$("#maxup").html("非常拥挤:3");
//			$("#maxup").css("color","white");
//			$("#minup").html("一般:1");
//			$("#minup").css("color","white");
//		}
//		if (data.max>3) 
//		{
//			$("#min").show() ;
//			$("#max").show() ;
//			$("#minup").show() ;
		if (data.max==1)
		{
			$("#minup").popover("destroy") ;
			$("#max").popover('destroy') ;
			$("#min").popover('destroy') ;
			$("#maxup").popover('destroy') ;
			$("#max").html(i18n_heatmap_max);
			$("#max").css("color","purple");
			$("#maxup").html(i18n_heatmap_maxup);
			$("#maxup").css("color","purple");
			$("#minup").html(i18n_heatmap_minup);
			$("#minup").css("color","purple");
			$("#min").html(i18n_heatmap_min);
			$("#min").css("color","purple");
			var htmlstrmin3 = '<i style="color:rgba(0,0,255,1);" class="icon-male"></i>'+i18n_heatmap_min1+data.max;
			var option3 = {html:true,trigger:"hover",content:htmlstrmin3,placement:"top"};
			var htmlstrmin2 = '<i style="color:rgba(73,255,0,1);" class="icon-male"></i><i style="color:rgba(73,255,0,1);" class="icon-male"></i>'+i18n_heatmap_minup2+Math.round(((data.max-data.min)/3)+1);
			var option2 = {html:true,trigger:"hover",content:htmlstrmin2,placement:"top"};
			var htmlstrmin = '<i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i>'+i18n_heatmap_maxup4+data.max;
			var htmlstrmin1 = '<i style="color:rgba(251,255,0,1);" class="icon-male"></i><i style="color:rgba(251,255,0,1);" class="icon-male"></i><i style="color:rgba(251,255,0,1);" class="icon-male"></i>'+i18n_heatmap_maxup3+Math.round(((data.max-data.min)/3)+1);
			var option = {html:true,trigger:"hover",content:htmlstrmin,placement:"top"};
			var option1 = {html:true,trigger:"hover",content:htmlstrmin1,placement:"top"};
			$("#max").popover(option1);
			$("#min").popover(option3);
			$("#maxup").popover(option);
			$("#minup").popover(option2);
		}
		if (data.max==2)
		{
			$("#minup").popover("destroy") ;
			$("#max").popover('destroy') ;
			$("#min").popover('destroy') ;
			$("#maxup").popover('destroy') ;
			$("#max").html(i18n_heatmap_max);
			$("#max").css("color","purple");
			$("#maxup").html(i18n_heatmap_maxup);
			$("#maxup").css("color","purple");
			$("#minup").html(i18n_heatmap_minup);
			$("#minup").css("color","purple");
			$("#min").html(i18n_heatmap_min);
			$("#min").css("color","purple");
			var htmlstrmin3 = '<i style="color:rgba(0,0,255,1);" class="icon-male"></i>'+i18n_heatmap_min1+data.min;
			var option3 = {html:true,trigger:"hover",content:htmlstrmin3,placement:"top"};
			var htmlstrmin2 = '<i style="color:rgba(73,255,0,1);" class="icon-male"></i><i style="color:rgba(73,255,0,1);" class="icon-male"></i>'+i18n_heatmap_minup2+Math.round(((data.max-data.min)/3)+1);
			var option2 = {html:true,trigger:"hover",content:htmlstrmin2,placement:"top"};
			var htmlstrmin = '<i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i>'+i18n_heatmap_maxup4+data.max;
			var htmlstrmin1 = '<i style="color:rgba(251,255,0,1);" class="icon-male"></i><i style="color:rgba(251,255,0,1);" class="icon-male"></i><i style="color:rgba(251,255,0,1);" class="icon-male"></i>'+i18n_heatmap_max3+Math.round(((data.max-data.min)/3)+1);
			var option = {html:true,trigger:"hover",content:htmlstrmin,placement:"top"};
			var option1 = {html:true,trigger:"hover",content:htmlstrmin1,placement:"top"};
			$("#max").popover(option1);
			$("#min").popover(option3);
			$("#maxup").popover(option);
			$("#minup").popover(option2);
		}
		if (data.max==3)
		{
			$("#minup").popover("destroy") ;
			$("#max").popover('destroy') ;
			$("#min").popover('destroy') ;
			$("#maxup").popover('destroy') ;
			$("#max").html(i18n_heatmap_max);
			$("#max").css("color","purple");
			$("#maxup").html(i18n_heatmap_maxup);
			$("#maxup").css("color","purple");
			$("#minup").html(i18n_heatmap_minup);
			$("#minup").css("color","purple");
			$("#min").html(i18n_heatmap_min);
			$("#min").css("color","purple");
			var htmlstrmin3 = '<i style="color:rgba(0,0,255,1);" class="icon-male"></i>'+i18n_heatmap_min1+data.min;
			var option3 = {html:true,trigger:"hover",content:htmlstrmin3,placement:"top"};
			var htmlstrmin2 = '<i style="color:rgba(73,255,0,1);" class="icon-male"></i><i style="color:rgba(73,255,0,1);" class="icon-male"></i>'+i18n_heatmap_minup2+Math.round(((data.max-data.min)/3)+1);
			var option2 = {html:true,trigger:"hover",content:htmlstrmin2,placement:"top"};
			var htmlstrmin = '<i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i>'+i18n_heatmap_maxup4+data.max;
			var htmlstrmin1 = '<i style="color:rgba(251,255,0,1);" class="icon-male"></i><i style="color:rgba(251,255,0,1);" class="icon-male"></i><i style="color:rgba(251,255,0,1);" class="icon-male"></i>'+i18n_heatmap_max3+Math.round(((data.max-data.min)/3)*2+1);
			var option = {html:true,trigger:"hover",content:htmlstrmin,placement:"top"};
			var option1 = {html:true,trigger:"hover",content:htmlstrmin1,placement:"top"};
			$("#max").popover(option1);
			$("#min").popover(option3);
			$("#maxup").popover(option);
			$("#minup").popover(option2);
		}
		if (data.max>3) {
			$("#minup").popover("destroy") ;
			$("#max").popover('destroy') ;
			$("#min").popover('destroy') ;
			$("#maxup").popover('destroy') ;
			$("#max").html(i18n_heatmap_max);
			$("#max").css("color","purple");
			$("#maxup").html(i18n_heatmap_maxup);
			$("#maxup").css("color","purple");
			$("#minup").html(i18n_heatmap_minup);
			$("#minup").css("color","purple");
			$("#min").html(i18n_heatmap_min);
			$("#min").css("color","purple");
			var htmlstrmin3 = '<i style="color:rgba(0,0,255,1);" class="icon-male"></i>'+i18n_heatmap_min1+data.min;
			var option3 = {html:true,trigger:"hover",content:htmlstrmin3,placement:"top"};
			var htmlstrmin2 = '<i style="color:rgba(73,255,0,1);" class="icon-male"></i><i style="color:rgba(73,255,0,1);" class="icon-male"></i>'+i18n_heatmap_minup2+Math.round(((data.max-data.min)/3)+1);
			var option2 = {html:true,trigger:"hover",content:htmlstrmin2,placement:"top"};
			var htmlstrmin = '<i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i><i style="color:rgba(255,40,0,1);" class="icon-male"></i>'+i18n_heatmap_maxup4+data.max;
			var htmlstrmin1 = '<i style="color:rgba(251,255,0,1);" class="icon-male"></i><i style="color:rgba(251,255,0,1);" class="icon-male"></i><i style="color:rgba(251,255,0,1);" class="icon-male"></i>'+i18n_heatmap_max3+Math.round(((data.max-data.min)/3)*2+1);
			var option = {html:true,trigger:"hover",content:htmlstrmin,placement:"top"};
			var option1 = {html:true,trigger:"hover",content:htmlstrmin1,placement:"top"};
			$("#max").popover(option1);
			$("#min").popover(option3);
			$("#maxup").popover(option);
			$("#minup").popover(option2);
		}
//			$("#min").html(data.min)  ;
//			$("#minup").html(Math.round((data.max-data.min)/3)+1)  ;
//			$("#max").html(Math.round(((data.max-data.min)/3)*2+1))  ;
//			$("#maxup").html(data.max)  ;
//			document.getElementById("maxup").setAttribute("title",data.max);
//		}
		// the onExtremaChange callback gives us min, max, and the
		// gradientConfig
		// so we can update the legend
//		min.innerHTML = data.min;
//		max.innerHTML = data.max;
		// regenerate gradient image
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
	};
	/* legend code end */

	var updateTooltip = function(x, y, value) {
		// + 15 for distance to cursor
		var transform = 'translate(' + (x + 15) + 'px, ' + (y + 15) + 'px)';
		tooltip.style.MozTransform = transform; /* Firefox */
		tooltip.style.msTransform = transform; /*
												 * IE (9+) - note ms is
												 * lowercase
												 */
		tooltip.style.OTransform = transform; /* Opera */
		tooltip.style.WebkitTransform = transform; /* Safari and Chrome */
		tooltip.style.transform = transform; /* One day, my pretty */
		tooltip.innerHTML = value;
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
		// 初始化下拉列表
		initDropdown : function() {
			$("#marketSel").chosen();
			$("#floorSel").chosen();
			$("#densitySel").chosen({
				width : 80
			});
			$("#radiusSel").chosen({
				width : 80
			});
		
			var placeId = $.cookie("place");
			var floorNo = $.cookie("floor");
			var t = $.cookie("times");
			if (t==undefined) {
				var times = $("#time").val();
				t = times;
				$.cookie("times", times, { expires: 30});
			}
			
			//var place = $("#marketSel").val();
			var array=new Array();
			var arrayfloor=new Array();
			$.get("/sva/store/api/getData?t="+Math.random(), function(data) {
				if (!data.error) {
					var storeList = data.data;
					updateList("marketSel", storeList,placeId, function() {
						$('#marketSel').trigger("liszt:updated");
							$("#marketSel option").each(function(){ //遍历全部option 
								//	var txt = $(this).text(); //获取单个text
								var val = $(this).val(); //获取单个value
								var node =val;
								array.push(node);
								
							}); 
							
							
							for(var i= 0;i<array.length;i++){
								if(array[i] == placeId){
									changeFloor(placeId,floorNo,function(){
										$("#floorSel option").each(function(){ //遍历全部option 
											//	var txt = $(this).text(); //获取单个text
											var val = $(this).val(); //获取单个value
											var node =val;
											arrayfloor.push(node);
											
										}); 
										for(var i= 0;i<arrayfloor.length;i++){
											if(arrayfloor[i] == floorNo){
												 $("#time").val(t);
												$('#confirm').click();
											}
											
										}
									});							
								}
							}
						//$("#marketSel").change();
						//setTimeout(function(){$('#floorSel').val(floorNo);$('#floorSel').trigger("liszt:updated");},1000);
						//$('#floorSel').val(floorNo);
						//$('#floorSel').trigger("liszt:updated");
					});
				}
			});
		},

		bindClickEvent : function() {
			// 地点下拉列表修改 触发楼层下拉列表变化
			$("#marketSel").chosen().change(function() {
				var placeId = $("#marketSel").val();
				changeFloor(placeId);
			});
			
			// 确认按钮点击 触发热力图刷新
			$('#confirm').click(function(e) {
				var placeId = $("#marketSel").val();
				var floorNo = $("select[name='floorSelName']").find("option:selected").val();
			    var floor = $("select[name='floorSelName']").find("option:selected").text();
			    floorLoop = floorNo;
			    var time = $("#time").val();
			   
			    var reg = /^[1-9]\d*$/;
			    if(!reg.test(time)){
			    	$("#time").blur();
			    	$("#mainContent").hide();
			    	return false;
			    	
			    }
			    if(time == ""){
			    	$("#time").blur();
			    	return false;
			    }
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
				$.cookie("times", time, { expires: 30});

			});

			// 密度改变后，热力图刷新
			$("#densitySel").chosen().change(function(e) {
				pointVal = parseInt($("#densitySel").val());
				clearTimeout(timer);
				var floorNo = $("select[name='floorSelName']").find("option:selected").val();
				initHeatmap(floorNo);
			});

			// 扩散度改变后，热力图刷新
			$("#radiusSel").chosen().change(function(e) {
				configObj.radius = parseInt($("#radiusSel").val());
				clearTimeout(timer);
				var floorNo = $("select[name='floorSelName']").find("option:selected").val();
				initHeatmap(floorNo);
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
		}

	};

}();