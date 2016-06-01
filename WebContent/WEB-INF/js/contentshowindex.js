var responeData = "";
var responeData2= "";
var responeData3 = "";
var responeData4 = "";
var responeData5 = "";
var responeData6 = "";
var responeData7 = "";
var responeData8 = "";
var floorNo = "";
var floorNo2 = "";
var floorNo3 = "";
var floorNo4 = "";
var floorNo5 = "";
var floorNo6 = "";
var floorNo7 = "";
var floorNo8 = "";
var radiusSel1 = "";
var radiusSel2 = "";
var radiusSel = "";
var radiusSel3 = "";
var radiusSel4 = "";
var radiusSel5 = "";
var radiusSel6 = "";
var radiusSel7 = "";
var maxValue = "";
var maxValue1 = "";
var maxValue2 = "";
var maxValue3 = "";
var maxValue4 = "";
var maxValue5 = "";
var maxValue6 = "";
var maxValue7 = "";
//var floorNo3sp = "";
var period  = "";
var timer;
var timer2;
var timer3;
var timer4;
var timer5;
var timer6;
var timer7;
var timer8;
var currentWidth = screen.width;
var coefficient;
var refreshHeatmapData = function() {
	clearTimeout(timer);
	$.post("/sva/heatmap/api/getData5?t="+Math.random(), {floorNo :floorNo,period:period}, function(data) {
		if (!data.error) {
			if (data.data && data.data.length > 0) {
				// var points = {max:1,data:dataFilter(data)};
				var points = dataFilter(data.data, origX, origY, scale,
						bgImgWidth, bgImgHeight, coordinate, imgScale);
				var dataObj = {
					max : maxValue,
					min : 1,
					data : points
				};
				heatmap.setData(dataObj);
			}else{
				var canvas=$("#heatmap canvas")[0];
				var ctx=canvas.getContext('2d');
				ctx.clearRect(0,0,bgImgWidth,bgImgHeight);
			}
			responeData = data;
			$("#number1").text(data.data.length*coefficient);
			//$("#numbern-data-item1").text(data.data.length);
		}
		timer = setTimeout("refreshHeatmapData();", 4000);
	});
};
var refreshHeatmapData2 = function() {
	clearTimeout(timer2);
	$.post("/sva/heatmap/api/getData5?t="+Math.random(), {floorNo :floorNo2,period:period}, function(data) {
		if (!data.error) {
			if (data.data && data.data.length > 0) {
				// var points = {max:1,data:dataFilter(data)};
				var points2= dataFilter2(data.data, origX2, origY2, scale2,
						bgImgWidth2, bgImgHeight2, coordinate2, imgScale2);
				var dataObj2 = {
					max : maxValue1,
					min : 1,
					data : points2
				};
				heatmap2.setData(dataObj2);
			}else{
				var canvas=$("#heatmap2 canvas")[0];
				var ctx=canvas.getContext('2d');
				ctx.clearRect(0,0,bgImgWidth2,bgImgHeight2);
			}
			responeData2 = data;
			$("#number2").text(data.data.length*coefficient);
			//$("#numbern-data-item1").text(data.data.length);
		}
		 timer2 = setTimeout("refreshHeatmapData2();", 4000);
	});
};
//var refreshHeatmapData2sp = function() {
//	clearTimeout(timer2);
//	$.post("/sva/heatmap/api/getData5?t="+Math.random(), {floorNo :floorNo2,period:period}, function(data) {
//		if (!data.error) {
//			if (data.data && data.data.length > 0) {
//				// var points = {max:1,data:dataFilter(data)};
//				var points2= dataFilter2sp(data.data, origX2sp, origY2sp, scale2sp,
//						bgImgWidth2sp, bgImgHeight2sp,coordinate2sp, imgScale2sp);
//				var dataObj2 = {
//						max : maxValue1,
//						min : 1,
//						data : points2
//				};
//				heatmap2sp.setData(dataObj2);
//			}else{
//				var canvas=$("#heatmap2 canvas")[0];
//				var ctx=canvas.getContext('2d');
//				ctx.clearRect(0,0,bgImgWidth2sp,bgImgHeight2sp);
//			}
//			
//			responeData2 = data;
//			//$("#numbern-data-item1").text(data.data.length);
//		}
//		timer2 = setTimeout("refreshHeatmapData2sp();", 4000);
//	});
//};
var refreshHeatmapData3 = function() {
	clearTimeout(timer3);
	$.post("/sva/heatmap/api/getData5?t="+Math.random(), {floorNo :floorNo3,period:period}, function(data) {
		if (!data.error) {
			if (data.data && data.data.length > 0) {
				// var points = {max:1,data:dataFilter(data)};
				var points3 = dataFilter3(data.data, origX3, origY3, scale3,
						bgImgWidth3, bgImgHeight3, coordinate3, imgScale3);
				var dataObj3 = {
					max : maxValue2,
					min : 1,
					data : points3
				};
				heatmap3.setData(dataObj3);
			}else{
				var canvas=$("#heatmap3 canvas")[0];
				var ctx=canvas.getContext('2d');
				ctx.clearRect(0,0,bgImgWidth3,bgImgHeight3);
			}
			responeData3 = data;
			$("#number3").text(data.data.length*coefficient);
			//$("#numbern-data-item1").text(data.data.length);
		}
		 timer3 = setTimeout("refreshHeatmapData3();", 4000);
	});
};

var refreshHeatmapData4 = function() {
	clearTimeout(timer4);
	$.post("/sva/heatmap/api/getData5?t="+Math.random(), {floorNo :floorNo4,period:period}, function(data) {
		if (!data.error) {
			if (data.data && data.data.length > 0) {
				// var points = {max:1,data:dataFilter(data)};
				var points3 = dataFilter3(data.data, origX3, origY3, scale3,
						bgImgWidth3, bgImgHeight3, coordinate3, imgScale3);
				var dataObj3 = {
					max : maxValue3,
					min : 1,
					data : points3
				};
				heatmap4.setData(dataObj3);
			}else{
				var canvas=$("#heatmap4 canvas")[0];
				var ctx=canvas.getContext('2d');
				ctx.clearRect(0,0,bgImgWidth3,bgImgHeight3);
			}
			responeData4 = data;
			$("#number4").text(data.data.length*coefficient);
			//$("#numbern-data-item1").text(data.data.length);
		}
		 timer4 = setTimeout("refreshHeatmapData4();", 4000);
	});
};
var refreshHeatmapData5 = function() {
	clearTimeout(timer5);
	$.post("/sva/heatmap/api/getData5?t="+Math.random(), {floorNo :floorNo5,period:period}, function(data) {
		if (!data.error) {
			if (data.data && data.data.length > 0) {
				// var points = {max:1,data:dataFilter(data)};
				var points3 = dataFilter3(data.data, origX3, origY3, scale3,
						bgImgWidth3, bgImgHeight3, coordinate3, imgScale3);
				var dataObj3 = {
					max : maxValue4,
					min : 1,
					data : points3
				};
				heatmap5.setData(dataObj3);
			}else{
				var canvas=$("#heatmap5 canvas")[0];
				var ctx=canvas.getContext('2d');
				ctx.clearRect(0,0,bgImgWidth3,bgImgHeight3);
			}
			responeData5 = data;
			$("#number5").text(data.data.length*coefficient);
			//$("#numbern-data-item1").text(data.data.length);
		}
		 timer5 = setTimeout("refreshHeatmapData5();", 4000);
	});
};
var refreshHeatmapData6 = function() {
	clearTimeout(timer6);
	$.post("/sva/heatmap/api/getData5?t="+Math.random(), {floorNo :floorNo6,period:period}, function(data) {
		if (!data.error) {
			if (data.data && data.data.length > 0) {
				// var points = {max:1,data:dataFilter(data)};
				var points3 = dataFilter3(data.data, origX3, origY3, scale3,
						bgImgWidth3, bgImgHeight3, coordinate3, imgScale3);
				var dataObj3 = {
					max : maxValue5,
					min : 1,
					data : points3
				};
				heatmap6.setData(dataObj3);
			}else{
				var canvas=$("#heatmap6 canvas")[0];
				var ctx=canvas.getContext('2d');
				ctx.clearRect(0,0,bgImgWidth3,bgImgHeight3);
			}
			responeData6 = data;
			$("#number6").text(data.data.length*coefficient);
			//$("#numbern-data-item1").text(data.data.length);
		}
		 timer6 = setTimeout("refreshHeatmapData6();", 4000);
	});
};
var refreshHeatmapData7 = function() {
	clearTimeout(timer7);
	$.post("/sva/heatmap/api/getData5?t="+Math.random(), {floorNo :floorNo7,period:period}, function(data) {
		if (!data.error) {
			if (data.data && data.data.length > 0) {
				// var points = {max:1,data:dataFilter(data)};
				var points3 = dataFilter3(data.data, origX3, origY3, scale3,
						bgImgWidth3, bgImgHeight3, coordinate3, imgScale3);
				var dataObj3 = {
					max : maxValue6,
					min : 1,
					data : points3
				};
				heatmap7.setData(dataObj3);
			}else{
				var canvas=$("#heatmap7 canvas")[0];
				var ctx=canvas.getContext('2d');
				ctx.clearRect(0,0,bgImgWidth3,bgImgHeight3);
			}
			responeData7 = data;
			$("#number7").text(data.data.length*coefficient);
			//$("#numbern-data-item1").text(data.data.length);
		}
		 timer7 = setTimeout("refreshHeatmapData7();", 4000);
	});
};
var refreshHeatmapData8 = function() {
	clearTimeout(timer8);
	$.post("/sva/heatmap/api/getData5?t="+Math.random(), {floorNo :floorNo8,period:period}, function(data) {
		if (!data.error) {
			if (data.data && data.data.length > 0) {
				// var points = {max:1,data:dataFilter(data)};
				var points3 = dataFilter3(data.data, origX3, origY3, scale3,
						bgImgWidth3, bgImgHeight3, coordinate3, imgScale3);
				var dataObj3 = {
					max : maxValue7,
					min : 1,
					data : points3
				};
				heatmap8.setData(dataObj3);
			}else{
				var canvas=$("#heatmap8 canvas")[0];
				var ctx=canvas.getContext('2d');
				ctx.clearRect(0,0,bgImgWidth3,bgImgHeight3);
			}
			responeData8 = data;
			$("#number8").text(data.data.length*coefficient);
			//$("#numbern-data-item1").text(data.data.length);
		}
		 timer8 = setTimeout("refreshHeatmapData8();", 4000);
	});
};
//var refreshHeatmapData3sp= function() {
//	clearTimeout(timer3);
//	$.post("/sva/heatmap/api/getData5?t="+Math.random(), {floorNo :floorNo3,period:period}, function(data) {
//		if (!data.error) {
//			if (data.data && data.data.length > 0) {
//				// var points = {max:1,data:dataFilter(data)};
//				var points3 = dataFilter3sp(data.data, origX3sp, origY3sp, scale3sp,
//						bgImgWidth3sp, bgImgWidth3sp, coordinate3sp, imgScale3sp);
//				var dataObj3 = {
//						max : maxValue2,
//						min : 1,
//						data : points3
//				};
//				heatmap3sp.setData(dataObj3);
//			}else{
//				var canvas=$("#heatmap3 canvas")[0];
//				var ctx=canvas.getContext('2d');
//				ctx.clearRect(0,0,bgImgWidth3sp,bgImgWidth3sp);
//			}
//			responeData3 = data;
//			//$("#numbern-data-item1").text(data.data.length);
//		}
//		timer3 = setTimeout("refreshHeatmapData3sp();", 4000);
//	});
//};
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
				x : (data[i].x / 10 * scale + xo * scale)+Math.random()/10,
				y : (data[i].y / 10 * scale + yo * scale) +Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "ll":
		for ( var i in data) {
			var point = {
				x : (data[i].x / 10 * scale + xo * scale) +Math.random()/10,
				y : height - (data[i].y / 10 * scale + yo * scale) +Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "ur":
		for ( var i in data) {
			var point = {
				x : width - (data[i].x / 10 * scale + xo * scale) +Math.random()/10,
				y : (data[i].y / 10 * scale + yo * scale) + Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "lr":
		for ( var i in data) {
			var point = {
				x : width - (data[i].x / 10 * scale + xo * scale) +Math.random()/10,
				y : height - (data[i].y / 10 * scale + yo * scale) +Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	}

	return list;
};
var dataFilter2 = function(data, xo, yo, scale, width2, height2, coordinate,
		imgScale) {
	var list = [];
	xo2 = parseFloat(xo);
	yo2 = parseFloat(yo);
	scale2 = parseFloat(scale);
	switch (coordinate){
	case "ul":
		for ( var i in data) {
			var point = {
				x : (data[i].x / 10 * scale2 + xo2 * scale2) +Math.random()/10,
				y : (data[i].y / 10 * scale2 + yo2 * scale2) +Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "ll":
		for ( var i in data) {
			var point = {
				x : (data[i].x / 10 * scale2 + xo2 * scale2) +Math.random()/10,
				y : height2 - (data[i].y / 10 * scale2 + yo2 * scale2) +Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "ur":
		for ( var i in data) {
			var point = {
				x : width2 - (data[i].x / 10 * scale2 + xo2 * scale2) +Math.random()/10,
				y : (data[i].y / 10 * scale2 + yo2 * scale2) +Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "lr":
		for ( var i in data) {
			var point = {
				x : width2 - (data[i].x / 10 * scale2 + xo2 * scale2) +Math.random()/10,
				y : height2 - (data[i].y / 10 * scale2 + yo2 * scale2) +Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	}

	return list;
};
var dataFilter2sp = function(data, xo, yo, scale, width2sp, height2sp, coordinate,
imgScale) {
	var list = [];
	var xo2 = parseFloat(xo);
	var yo2= parseFloat(yo);
	var scale2 = parseFloat(scale);
	switch (coordinate){
	case "ul":
		for ( var i in data) {
			var point = {
				x : width2sp - (data[i].y / 10 * scale2 + yo2 * scale2) +Math.random()/10,
				y :(data[i].x / 10 * scale2 + xo2 * scale2) +Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "ll":
		for ( var i in data) {
			var point = {
				x : (data[i].y / 10 * scale2 + yo2 * scale2) +Math.random()/10,
				y : (data[i].x / 10 * scale2 + xo2 * scale2) +Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "ur":
		for ( var i in data) {
			var point = {
				x : width2sp - (data[i].y / 10 * scale2 + yo2 * scale2) +Math.random()/10,
				y : height2sp - (data[i].x / 10 * scale2 + xo2 * scale2) +Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "lr":
		for ( var i in data) {
			var point = {
				x : (data[i].y / 10 * scale2 + yo2 * scale2) +Math.random()/10,
				y : height2sp - (data[i].x / 10 * scale2 + xo2 * scale2) +Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	}

	return list;
};
var dataFilter3 = function(data, xo, yo, scale, width3, height3, coordinate,
		imgScale) {
	var list = [];
	var xo3 = parseFloat(xo);
	var yo3 = parseFloat(yo);
	var scale3 = parseFloat(scale);
	switch (coordinate){
	case "ul":
		for ( var i in data) {
			var point = {
				x : (data[i].x / 10 * scale3 + xo3 * scale3) +Math.random()/10,
				y : (data[i].y / 10 * scale3 + yo3 * scale3) +Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "ll":
		for ( var i in data) {
			var point = {
				x : (data[i].x / 10 * scale3 + xo3 * scale3) +Math.random()/10,
				y : height3 - (data[i].y / 10 * scale3 + yo3 * scale3) +Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "ur":
		for ( var i in data) {
			var point = {
				x : width3 - (data[i].x / 10 * scale3 + xo3 * scale3) +Math.random()/10,
				y : (data[i].y / 10 * scale3 + yo3 * scale3) +Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "lr":
		for ( var i in data) {
			var point = {
				x : width3 - (data[i].x / 10 * scale3 + xo3 * scale3) +Math.random()/10,
				y : height3 - (data[i].y / 10 * scale3 + yo3 * scale3) +Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	}

	return list;
};


var dataFilter3sp = function(data, xo, yo, scale, width3sp, height3sp, coordinate,
		imgScale) {
	var list = [];
	var xo3 = parseFloat(xo);
	var yo3 = parseFloat(yo);
	var scale3 = parseFloat(scale);
	switch (coordinate){
	case "ul":
		for ( var i in data) {
			var point = {
				x : width3sp - (data[i].y / 10 * scale3 + yo3 * scale3) +Math.random()/10,
				y :(data[i].x / 10 * scale3 + xo3 * scale3) +Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "ll":
		for ( var i in data) {
			var point = {
				x : (data[i].y / 10 * scale3 + yo3 * scale3) +Math.random()/10,
				y : (data[i].x / 10 * scale3 + xo3 * scale3) +Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "ur":
		for ( var i in data) {
			var point = {
				x : width3sp - (data[i].y / 10 * scale3 + yo3 * scale3) +Math.random()/10,
				y : height3sp - (data[i].x / 10 * scale3 + xo3 * scale3) +Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "lr":
		for ( var i in data) {
			var point = {
				x : (data[i].y / 10 * scale3 + yo3 * scale3) +Math.random()/10,
				y : height3sp- (data[i].x / 10 * scale3 + xo3 * scale3) +Math.random()/10,
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
	var divWidth = parseInt($("#heatmap-slide1-graphic").css("width").slice(0, -2));

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
var calImgSize2 = function(width, height) {
	var newWidth, newHeight, imgScale;
	var divWidth = parseInt($("#heatmap-slide2-graphic").css("width").slice(0, -2));
	
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
var calImgSize3 = function(width, height) {
	var newWidth, newHeight, imgScale;
	var divWidth = parseInt($("#heatmap-slide3-graphic").css("width").slice(0, -2));
	
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

var countTime;
var countTime2;
var countTime3;
var countTime4;
var countTime5;
var countTime6;
var countTime7;
var countTime8;
function count(coefficient){
	clearTimeout(countTime);
	if(coefficient == 0){
		coefficient = coefficient + 1;
	}
	if(responeData == ""){
		countTime = setTimeout("count(coefficient)",1000);
	}else{
		$("#numbern-data-item1").text(Math.ceil((responeData.data.length)*coefficient));
		countTime = setTimeout("count(coefficient)",60000);
	}
}
function count2(){
	clearTimeout(countTime2);
	if(responeData2 == ""){
		countTime2 = setTimeout("count2()",1000);
	}else{
		$("#numbern-data-item1-slide2").text(responeData2.data.length);
		countTime2 = setTimeout("count2()",60000);
	}
}
function count3(){
	clearTimeout(countTime3);
	if(responeData3 == ""){
		countTime3 = setTimeout("count3()",1000);
	}else{
		$("#numbern-data-item1-slide3").text(responeData3.data.length);
		countTime3 = setTimeout("count3()",60000);
	}
}
function count4(){
	clearTimeout(countTime4);
	if(responeData4 == ""){
		countTime4 = setTimeout("count4()",1000);
	}else{
		$("#numbern-data-item1-slide4").text(responeData4.data.length);
		countTime4 = setTimeout("count4()",60000);
	}
}
function count5(){
	clearTimeout(countTime5);
	if(responeData5 == ""){
		countTime5 = setTimeout("count5()",1000);
	}else{
		$("#numbern-data-item1-slide5").text(responeData5.data.length);
		countTime5 = setTimeout("count5()",60000);
	}
}
function count6(){
	clearTimeout(countTime6);
	if(responeData6 == ""){
		countTime6 = setTimeout("count6()",1000);
	}else{
		$("#numbern-data-item1-slide6").text(responeData6.data.length);
		countTime6 = setTimeout("count6()",60000);
	}
}
function count7(){
	clearTimeout(countTime7);
	if(responeData7 == ""){
		countTime7 == setTimeout("count7()",1000);
	}else{
		$("#numbern-data-item1-slide7").text(responeData7.data.length);
		countTime7 = setTimeout("count7()",60000);
	}
}
function count8(){
	clearTimeout(countTime8);
	if(responeData8 == ""){
		countTime8 = setTimeout("count8()",1000);
	}else{
		$("#numbern-data-item1-slide8").text(responeData8.data.length);
		countTime8 = setTimeout("count8()",60000);
	}
}

var Heatmap = function() {

	/**
	 * 将对应信息填充到对应的select @ param renderId [string] 标签id @ param data [array]
	 *                   列表数据
	 */
	var initHeatmap = function(floorNo,periodSel) {
		$("#heatmap-slide1-graphic").css("background-image","");
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
					$("#heatmap-slide1-graphic").css({
						"width" : bgImgWidth + "px",
						"height" : bgImgHeight + "px",
						"background-image" : bgImgStr,
						"background-size" : bgImgWidth + "px " + bgImgHeight + "px",
						"margin" : "0% auto"
					});
					
					configObj.onExtremaChange = function(data) {
						updateLegend(data);
					};
					
					heatmap = h337.create(configObj);
					$.post("/sva/heatmap/api/getData5?t="+Math.random(), {
						floorNo : floorNo,period:periodSel
					}, function(data) {
						if (!data.error) {
							if (data.data && data.data.length > 0) {
								// var points = {max:1,data:dataFilter(data)};
								var points = dataFilter(data.data, origX,
										origY, scale, bgImgWidth, bgImgHeight,
										coordinate, imgInfo[0]);
								var dataObj = {
									max : maxValue,
									min : 1,
									data : points
								};
								heatmap.setData(dataObj);
								$("#legend").show();
							}
							$(".countInfo").show();
							responeData = data;
						}
						$("#number1").text(data.data.length*coefficient);
					});
					clearTimeout(timer);
					timer = setTimeout("refreshHeatmapData();", 4000);
					// refreshHeatmapData();
				}
			}
		});
	};
	var initHeatmap2 = function(floorNo2,periodSel) {
		$("#heatmap-slide2-graphic").css("background-image","");
		$("#heatmap2").empty();
		$.post("/sva/heatmap/api/getMapInfoByPosition", {
			floorNo : floorNo2
		}, function(data) {
			if (!data.error) {
				if (data.bg) {
					// 全局变量赋值
					origX2 = data.xo;
					origY2 = data.yo;
					bgImg2 = data.bg;
					bgImgWidth2 = data.bgWidth;
					bgImgHeight2 = data.bgHeight;
					scale2 = data.scale;
					coordinate2 = data.coordinate;
					// 设置背景图片
					var bgImgStr2 = "url(../upload/" + bgImg2 + ")";
					var imgInfo = calImgSize2(bgImgWidth2, bgImgHeight2);
					imgScale2= imgInfo[0];
					imgWidth2 = imgInfo[1];
					imgHeight2 = imgInfo[2];
					$("#heatmap-slide2-graphic").css({
						"width" : bgImgWidth2 + "px",
						"height" : bgImgHeight2 + "px",
						"background-image" : bgImgStr2,
						"background-size" : bgImgWidth2 + "px " + bgImgHeight2 + "px",
						"margin" : "0% auto"
					});
//					$(".slide-box .slide-box-tip.slide-box-tip-slide3").css({
//						"width" : bgImgWidth2 + "px",
//					    "margin-left": "-28%"
//					});
//					
					heatmap2 = h337.create(configObj2);
					$.post("/sva/heatmap/api/getData5?t="+Math.random(), {
						floorNo : floorNo2,period:periodSel
					}, function(data) {
						if (!data.error) {
							if (data.data && data.data.length > 0) {
								// var points = {max:1,data:dataFilter(data)};
								var points = dataFilter2(data.data, origX2,
										origY2, scale2, bgImgWidth2, bgImgHeight2,
										coordinate2, imgInfo[0]);
								var dataObj2 = {
										max : maxValue1,
										min : 1,
										data : points
								};
								heatmap2.setData(dataObj2);
							}
							responeData2 = data;
							$("#number2").text(data.data.length*coefficient);
						}
					});
					clearTimeout(timer2);
					timer2 = setTimeout("refreshHeatmapData2();", 4000);
					// refreshHeatmapData();
				}
			}
		});
	};
//	var initHeatmap2sp = function(floorNo2sp,periodSel) {
//		$("#heatmap-slide2-graphic").css("background-image","");
//		$("#heatmap2").empty();
//		$.post("/sva/heatmap/api/getMapInfoByPosition", {
//			floorNo : floorNo2sp
//		}, function(data) {
//			if (!data.error) {
//				if (data.bg) {
//					 origX2sp = data.xo;
//					 origY2sp = data.yo;
//					 bgImg2sp = data.bg;
//					 bgImgWidth2sp = data.bgWidth;
//					 bgImgHeight2sp = data.bgHeight;
//					 scale2sp = data.scale;
//					 coordinate2sp = data.coordinate;
//					// 设置背景图片
//					
//					 bgImgStr2sp = "url(../upload/" + bgImg2sp + ")";
//					 imgInfo = calImgSize2(bgImgWidth2sp, bgImgHeight2sp);
//					 imgScale2sp= imgInfo[0];
//					 imgWidth2sp = imgInfo[1];
//					 imgHeight2sp = imgInfo[2];
//					$("#heatmap-slide2-graphic").css({
//						"width" : bgImgWidth2sp + "px",
//						"height" : bgImgHeight2sp + "px",
//						"background-image" : bgImgStr2sp,
//						"background-size" : bgImgWidth2sp + "px " + bgImgHeight2sp + "px",
//						"margin" : "0 auto"
//					});
//					
//					heatmap2sp = h337.create(configObj2);
//					$.post("/sva/heatmap/api/getData5?t="+Math.random(), {
//						floorNo : floorNo2,period:periodSel
//					}, function(data) {
//						if (!data.error) {
//							if (data.data && data.data.length > 0) {
//								// var points = {max:1,data:dataFilter(data)};
//								var points = dataFilter2sp(data.data, origX2sp,
//										origY2sp, scale2sp, bgImgWidth2sp, bgImgHeight2sp,
//										coordinate2sp, imgInfo[0]);
//								var dataObj2 = {
//										max : maxValue1,
//										min : 1,
//										data : points
//								};
//								heatmap2sp.setData(dataObj2);
//							}
//							responeData2 = data;
//						}
//					});
//					clearTimeout(timer2);
//					timer2 = setTimeout("refreshHeatmapData2sp();", 4000);
//					// refreshHeatmapData();
//				}
//			}
//		});
//	};
//	
	var initHeatmap3 = function(floorNo3,periodSel) {
		$("#heatmap-slide3-graphic").css("background-image","");
		$("#heatmap3").empty();
		$.post("/sva/heatmap/api/getMapInfoByPosition", {
			floorNo : floorNo3
		}, function(data) {
			if (!data.error) {
				if (data.bg) {
					// 全局变量赋值
					origX3 = data.xo;
					origY3 = data.yo;
					bgImg3 = data.bg;
					bgImgWidth3 = data.bgWidth;
					bgImgHeight3 = data.bgHeight;
					scale3 = data.scale;
					coordinate3 = data.coordinate;
					// 设置背景图片
					var bgImgStr3 = "url(../upload/" + bgImg3 + ")";
					var imgInfo = calImgSize3(bgImgWidth3, bgImgHeight3);
					imgScale3 = imgInfo[0];
					imgWidth3 = imgInfo[1];
					imgHeight3 = imgInfo[2];
					$("#heatmap-slide3-graphic").css({
						"width" : bgImgWidth3 + "px",
						"height" : bgImgHeight3 + "px",
						"background-image" : bgImgStr3,
						"background-size" : bgImgWidth3 + "px " + bgImgHeight3 + "px",
						"margin" : "0% auto"
					});
					configObj3.onExtremaChange = function(data) {
						updateLegend(data);
					};
					
					heatmap3 = h337.create(configObj3);
					$.post("/sva/heatmap/api/getData5?t="+Math.random(), {
						floorNo : floorNo3,period:periodSel
					}, function(data) {
						if (!data.error) {
							if (data.data && data.data.length > 0) {
								// var points = {max:1,data:dataFilter(data)};
								var points = dataFilter3(data.data, origX3,
										origY3, scale3, bgImgWidth3, bgImgHeight3,
										coordinate3, imgInfo[0]);
								var dataObj3 = {
										max : maxValue2,
										min : 1,
										data : points
								};
								heatmap3.setData(dataObj3);
								$("#legend").show();
							}
							$(".countInfo").show();
							responeData3 = data;
							$("#number3").text(data.data.length*coefficient);
						}
					});
					clearTimeout(timer3);
					timer3 = setTimeout("refreshHeatmapData3();", 4000);
					// refreshHeatmapData();
				}
			}
		});
	};
	
	var initHeatmap4 = function(floorNo3,periodSel) {
		$("#heatmap-slide4-graphic").css("background-image","");
		$("#heatmap4").empty();
		$.post("/sva/heatmap/api/getMapInfoByPosition", {
			floorNo : floorNo3
		}, function(data) {
			if (!data.error) {
				if (data.bg) {
					// 全局变量赋值
					origX3 = data.xo;
					origY3 = data.yo;
					bgImg3 = data.bg;
					bgImgWidth3 = data.bgWidth;
					bgImgHeight3 = data.bgHeight;
					scale3 = data.scale;
					coordinate3 = data.coordinate;
					// 设置背景图片
					var bgImgStr3 = "url(../upload/" + bgImg3 + ")";
					var imgInfo = calImgSize3(bgImgWidth3, bgImgHeight3);
					imgScale3 = imgInfo[0];
					imgWidth3 = imgInfo[1];
					imgHeight3 = imgInfo[2];
					$("#heatmap-slide4-graphic").css({
						"width" : bgImgWidth3 + "px",
						"height" : bgImgHeight3 + "px",
						"background-image" : bgImgStr3,
						"background-size" : bgImgWidth3 + "px " + bgImgHeight3 + "px",
						"margin" : "0% auto"
					});
					configObj4.onExtremaChange = function(data) {
						updateLegend(data);
					};
					
					heatmap4 = h337.create(configObj4);
					$.post("/sva/heatmap/api/getData5?t="+Math.random(), {
						floorNo : floorNo3,period:periodSel
					}, function(data) {
						if (!data.error) {
							if (data.data && data.data.length > 0) {
								// var points = {max:1,data:dataFilter(data)};
								var points = dataFilter3(data.data, origX3,
										origY3, scale3, bgImgWidth3, bgImgHeight3,
										coordinate3, imgInfo[0]);
								var dataObj3 = {
										max : maxValue3,
										min : 1,
										data : points
								};
								heatmap4.setData(dataObj3);
								$("#legend").show();
							}
							$(".countInfo").show();
							responeData4 = data;
							$("#number4").text(data.data.length*coefficient);
						}
					});
					clearTimeout(timer4);
					timer4 = setTimeout("refreshHeatmapData4();", 4000);
					// refreshHeatmapData();
				}
			}
		});
	};
	var initHeatmap5 = function(floorNo3,periodSel) {
		$("#heatmap-slide5-graphic").css("background-image","");
		$("#heatmap5").empty();
		$.post("/sva/heatmap/api/getMapInfoByPosition", {
			floorNo : floorNo3
		}, function(data) {
			if (!data.error) {
				if (data.bg) {
					// 全局变量赋值
					origX3 = data.xo;
					origY3 = data.yo;
					bgImg3 = data.bg;
					bgImgWidth3 = data.bgWidth;
					bgImgHeight3 = data.bgHeight;
					scale3 = data.scale;
					coordinate3 = data.coordinate;
					// 设置背景图片
					var bgImgStr3 = "url(../upload/" + bgImg3 + ")";
					var imgInfo = calImgSize3(bgImgWidth3, bgImgHeight3);
					imgScale3 = imgInfo[0];
					imgWidth3 = imgInfo[1];
					imgHeight3 = imgInfo[2];
					$("#heatmap-slide5-graphic").css({
						"width" : bgImgWidth3 + "px",
						"height" : bgImgHeight3 + "px",
						"background-image" : bgImgStr3,
						"background-size" : bgImgWidth3 + "px " + bgImgHeight3 + "px",
						"margin" : "0% auto"
					});
					configObj5.onExtremaChange = function(data) {
						updateLegend(data);
					};
					
					heatmap5 = h337.create(configObj5);
					$.post("/sva/heatmap/api/getData5?t="+Math.random(), {
						floorNo : floorNo3,period:periodSel
					}, function(data) {
						if (!data.error) {
							if (data.data && data.data.length > 0) {
								// var points = {max:1,data:dataFilter(data)};
								var points = dataFilter3(data.data, origX3,
										origY3, scale3, bgImgWidth3, bgImgHeight3,
										coordinate3, imgInfo[0]);
								var dataObj3 = {
										max : maxValue4,
										min : 1,
										data : points
								};
								heatmap5.setData(dataObj3);
								$("#legend").show();
							}
							$(".countInfo").show();
							responeData5 = data;
							$("#number5").text(data.data.length*coefficient);
						}
					});
					clearTimeout(timer5);
					timer5 = setTimeout("refreshHeatmapData5();", 4000);
					// refreshHeatmapData();
				}
			}
		});
	};
	var initHeatmap6 = function(floorNo3,periodSel) {
		$("#heatmap-slide6-graphic").css("background-image","");
		$("#heatmap6").empty();
		$.post("/sva/heatmap/api/getMapInfoByPosition", {
			floorNo : floorNo3
		}, function(data) {
			if (!data.error) {
				if (data.bg) {
					// 全局变量赋值
					origX3 = data.xo;
					origY3 = data.yo;
					bgImg3 = data.bg;
					bgImgWidth3 = data.bgWidth;
					bgImgHeight3 = data.bgHeight;
					scale3 = data.scale;
					coordinate3 = data.coordinate;
					// 设置背景图片
					var bgImgStr3 = "url(../upload/" + bgImg3 + ")";
					var imgInfo = calImgSize3(bgImgWidth3, bgImgHeight3);
					imgScale3 = imgInfo[0];
					imgWidth3 = imgInfo[1];
					imgHeight3 = imgInfo[2];
					$("#heatmap-slide6-graphic").css({
						"width" : bgImgWidth3 + "px",
						"height" : bgImgHeight3 + "px",
						"background-image" : bgImgStr3,
						"background-size" : bgImgWidth3 + "px " + bgImgHeight3 + "px",
						"margin" : "0% auto"
					});
					configObj6.onExtremaChange = function(data) {
						updateLegend(data);
					};
					
					heatmap6 = h337.create(configObj6);
					$.post("/sva/heatmap/api/getData5?t="+Math.random(), {
						floorNo : floorNo3,period:periodSel
					}, function(data) {
						if (!data.error) {
							if (data.data && data.data.length > 0) {
								// var points = {max:1,data:dataFilter(data)};
								var points = dataFilter3(data.data, origX3,
										origY3, scale3, bgImgWidth3, bgImgHeight3,
										coordinate3, imgInfo[0]);
								var dataObj3 = {
										max : maxValue5,
										min : 1,
										data : points
								};
								heatmap6.setData(dataObj3);
								$("#legend").show();
							}
							$(".countInfo").show();
							responeData6 = data;
							$("#number6").text(data.data.length*coefficient);
						}
					});
					clearTimeout(timer6);
					timer6 = setTimeout("refreshHeatmapData6();", 4000);
					// refreshHeatmapData();
				}
			}
		});
	};
	var initHeatmap7 = function(floorNo3,periodSel) {
		$("#heatmap-slide7-graphic").css("background-image","");
		$("#heatmap7").empty();
		$.post("/sva/heatmap/api/getMapInfoByPosition", {
			floorNo : floorNo3
		}, function(data) {
			if (!data.error) {
				if (data.bg) {
					// 全局变量赋值
					origX3 = data.xo;
					origY3 = data.yo;
					bgImg3 = data.bg;
					bgImgWidth3 = data.bgWidth;
					bgImgHeight3 = data.bgHeight;
					scale3 = data.scale;
					coordinate3 = data.coordinate;
					// 设置背景图片
					var bgImgStr3 = "url(../upload/" + bgImg3 + ")";
					var imgInfo = calImgSize3(bgImgWidth3, bgImgHeight3);
					imgScale3 = imgInfo[0];
					imgWidth3 = imgInfo[1];
					imgHeight3 = imgInfo[2];
					$("#heatmap-slide7-graphic").css({
						"width" : bgImgWidth3 + "px",
						"height" : bgImgHeight3 + "px",
						"background-image" : bgImgStr3,
						"background-size" : bgImgWidth3 + "px " + bgImgHeight3 + "px",
						"margin" : "0% auto"
					});
					configObj7.onExtremaChange = function(data) {
						updateLegend(data);
					};
					
					heatmap7 = h337.create(configObj7);
					$.post("/sva/heatmap/api/getData5?t="+Math.random(), {
						floorNo : floorNo3,period:periodSel
					}, function(data) {
						if (!data.error) {
							if (data.data && data.data.length > 0) {
								// var points = {max:1,data:dataFilter(data)};
								var points = dataFilter3(data.data, origX3,
										origY3, scale3, bgImgWidth3, bgImgHeight3,
										coordinate3, imgInfo[0]);
								var dataObj3 = {
										max : maxValue6,
										min : 1,
										data : points
								};
								heatmap7.setData(dataObj3);
								$("#legend").show();
							}
							$(".countInfo").show();
							responeData7 = data;
							$("#number7").text(data.data.length*coefficient);
						}
					});
					clearTimeout(timer7);
					timer7 = setTimeout("refreshHeatmapData7();", 4000);
					// refreshHeatmapData();
				}
			}
		});
	};
	var initHeatmap8 = function(floorNo3,periodSel) {
		$("#heatmap-slide8-graphic").css("background-image","");
		$("#heatmap8").empty();
		$.post("/sva/heatmap/api/getMapInfoByPosition", {
			floorNo : floorNo3
		}, function(data) {
			if (!data.error) {
				if (data.bg) {
					// 全局变量赋值
					origX3 = data.xo;
					origY3 = data.yo;
					bgImg3 = data.bg;
					bgImgWidth3 = data.bgWidth;
					bgImgHeight3 = data.bgHeight;
					scale3 = data.scale;
					coordinate3 = data.coordinate;
					// 设置背景图片
					var bgImgStr3 = "url(../upload/" + bgImg3 + ")";
					var imgInfo = calImgSize3(bgImgWidth3, bgImgHeight3);
					imgScale3 = imgInfo[0];
					imgWidth3 = imgInfo[1];
					imgHeight3 = imgInfo[2];
					$("#heatmap-slide8-graphic").css({
						"width" : bgImgWidth3 + "px",
						"height" : bgImgHeight3 + "px",
						"background-image" : bgImgStr3,
						"background-size" : bgImgWidth3 + "px " + bgImgHeight3 + "px",
						"margin" : "0% auto"
					});
					configObj8.onExtremaChange = function(data) {
						updateLegend(data);
					};
					
					heatmap8 = h337.create(configObj8);
					$.post("/sva/heatmap/api/getData5?t="+Math.random(), {
						floorNo : floorNo3,period:periodSel
					}, function(data) {
						if (!data.error) {
							if (data.data && data.data.length > 0) {
								// var points = {max:1,data:dataFilter(data)};
								var points = dataFilter3(data.data, origX3,
										origY3, scale3, bgImgWidth3, bgImgHeight3,
										coordinate3, imgInfo[0]);
								var dataObj3 = {
										max : maxValue7,
										min : 1,
										data : points
								};
								heatmap8.setData(dataObj3);
								$("#legend").show();
							}
							$(".countInfo").show();
							responeData8 = data;
							$("#number8").text(data.data.length*coefficient);
						}
					});
					clearTimeout(timer8);
					timer8 = setTimeout("refreshHeatmapData8();", 4000);
					// refreshHeatmapData();
				}
			}
		});
	};	
//	var initHeatmap3sp = function(floorNo3sp,periodSel) {
//		$("#heatmap-slide3-graphic").css("background-image","");
//		$("#heatmap3").empty();
//		$.post("/sva/heatmap/api/getMapInfoByPosition", {
//			floorNo : floorNo3sp
//		}, function(data) {
//			if (!data.error) {
//				if (data.bg) {
//					// 全局变量赋值
//					origX3sp = data.xo;
//					origY3sp = data.yo;
//					bgImg3sp = data.bg;
//					bgImgWidth3sp = data.bgWidth;
//					bgImgHeight3sp = data.bgHeight;
//					scale3sp = data.scale;
//					coordinate3sp = data.coordinate;
//					// 设置背景图片
//					var bgImgStr3sp = "url(../upload/" + bgImg3sp + ")";
//					var imgInfo = calImgSize3(bgImgWidth3sp, bgImgHeight3sp);
//					imgScale3sp = imgInfo[0];
//					imgWidth3sp = imgInfo[1];
//					imgHeight3sp = imgInfo[2];
//					$("#heatmap-slide3-graphic").css({
//						"width" : bgImgWidth3sp + "px",
//						"height" : bgImgHeight3sp + "px",
//						"background-image" : bgImgStr3sp,
//						"background-size" : bgImgWidth3sp + "px " + bgImgHeight3sp + "px",
//						"margin" : "0 auto"
//					});
//					configObj3.onExtremaChange = function(data) {
//						updateLegend(data);
//					};
//					
//					heatmap3sp = h337.create(configObj3);
//					$.post("/sva/heatmap/api/getData5?t="+Math.random(), {
//						floorNo : floorNo3,period:periodSel
//					}, function(data) {
//						if (!data.error) {
//							if (data.data && data.data.length > 0) {
//								// var points = {max:1,data:dataFilter(data)};
//								var points = dataFilter3sp(data.data, origX3sp,
//										origY3sp, scale3sp, bgImgWidth3sp, bgImgHeight3sp,
//										coordinate3sp, imgInfo[0]);
//								var dataObj3 = {
//										max : maxValue2,
//										min : 1,
//										data : points
//								};
//								heatmap3sp.setData(dataObj3);
//								$("#legend").show();
//							}
//							$(".countInfo").show();
//							responeData3 = data;
//						}
//					});
//					clearTimeout(timer3);
//					timer3 = setTimeout("refreshHeatmapData3sp();", 4000);
//					// refreshHeatmapData();
//				}
//			}
//		});
//	};
 
	/* legend code */
	var updateLegend = function(data) {
	};
	
	
	return {
		// 初始化下拉列表
		initDropdown : function() {
			$.get("/sva/content/api/getData?t="+Math.random(),function(data){
				if(!data.error){
					maxValue =  data.data[0].densitySel;
					maxValue1 =  data.data[0].densitySel1;
					maxValue2 =  data.data[0].densitySel2;
					maxValue3 =  data.data[0].densitySel3;
					maxValue4 =  data.data[0].densitySel4;
					maxValue5 =  data.data[0].densitySel5;
					maxValue6 =  data.data[0].densitySel6;
					maxValue7 =  data.data[0].densitySel7;
					radiusSel =  data.data[0].radiusSel;
					radiusSel1 =  data.data[0].radiusSel1;
					radiusSel2 =  data.data[0].radiusSel2;
					radiusSel3 =  data.data[0].radiusSel3;
					radiusSel4 =  data.data[0].radiusSel4;
					radiusSel5 =  data.data[0].radiusSel5;
					radiusSel6 =  data.data[0].radiusSel6;
					radiusSel7 =  data.data[0].radiusSel7;
					
					configObj.radius = parseInt(radiusSel); 
					configObj2.radius = parseInt(radiusSel1); 
					configObj3.radius = parseInt(radiusSel2); 
					configObj4.radius = parseInt(radiusSel3); 
					configObj5.radius = parseInt(radiusSel4); 
					configObj6.radius = parseInt(radiusSel5); 
					configObj7.radius = parseInt(radiusSel6); 
					configObj8.radius = parseInt(radiusSel7); 
					floorNo = data.data[0].floorNo;
					floorNo2 = data.data[0].floorNo2;
					floorNo3 = data.data[0].floorNo3;
					floorNo4 = data.data[0].floorNo4;
					floorNo5 = data.data[0].floorNo5;
					floorNo6 = data.data[0].floorNo6;
					floorNo7 = data.data[0].floorNo7;
					floorNo8 = data.data[0].floorNo8;
					period = data.data[0].periodSel;
					coefficient = data.data[0].coefficient;
					if (coefficient==0) {
						coefficient = 1;
					}
					initHeatmap(floorNo, period);
					initHeatmap4(floorNo4, period);
					initHeatmap5(floorNo5, period);
					initHeatmap6(floorNo6, period);
					initHeatmap7(floorNo7, period);
					initHeatmap8(floorNo8, period);
//					if(currentWidth > 1400){
						initHeatmap2(floorNo2, period);
//					}else{
//						initHeatmap2sp(floorNo2SP, period);
//					}
//					if(currentWidth > 1400){
						initHeatmap3(floorNo3, period);
//					}else{
//						initHeatmap3sp(floorNo3sp, period);
//					}
					
//					 
//					 heatmap = h337.create(configObj);
//					 var dataObj = {
//						max : 1,
//						min : 1,
//						data : [{"x":10,"y":10,value:1},{"x":20,"y":20,value:1}]
//					};
//					heatmap.setData(dataObj);
//					
//					 heatmap2 = h337.create(configObj2);
//					 var dataObj2 = {
//								max : 1,
//								min : 1,
//								data : [{"x":10,"y":10,value:1},{"x":20,"y":20,value:1}]
//							};
//							heatmap2.setData(dataObj2);
					 
					 count();
					 count2();
					 count3();
					 count4();
					 count5();
					 count6();
					 count7();
					 count8();
				}
			});
			
		
		}

	};

}();