var swiper;
var timerFunctionTimer;
var BarJsonData;
function timerFunction()
{	
	clearTimeout(timerFunctionTimer);
	
	$.post("/sva/api/getBaShowData", function(item){
		if(!item.error){
			//隐藏热力图
//			if(item.floorflag==0)
//				$("#huawei").hide();
//			if(item.floorflag2==0)
//				$("#vdf").hide();
//			
//			if(item.floorflag3==0)
//				$("#other").hide();
			BarJsonData = item.item;
//			if(item.allUser1 == null){
//				
//				$("#number1").text(0);
//			}else{
//				$("#number1").text(item.allUser1);
//			}
//			if(item.allUser2 == null){
//				
//				$("#number2").text(0);
//			}else{
//				$("#number2").text(item.allUser2);
//			}
//			if(item.allUser3 == null){
//				
//				$("#number3").text(0);
//			}else{
//				$("#number3").text(item.allUser3);
//			}
//			if(item.allUser4 == null){
//				
//				$("#number4").text(0);
//			}else{
//				$("#number4").text(item.allUser4);
//			}
//			if(item.allUser5 == null){
//				
//				$("#number5").text(0);
//			}else{
//				$("#number5").text(item.allUser5);
//			}
//			if(item.allUser6 == null){
//				
//				$("#number6").text(0);
//			}else{
//				$("#number6").text(item.allUser6);
//			}
//			if(item.allUser7 == null){
//				
//				$("#number7").text(0);
//			}else{
//				$("#number7").text(item.allUser7);
//			}
//			if(item.allUser8 == null){
//				
//				$("#number8").text(0);
//			}else{
//				$("#number8").text(item.allUser8);
//			}
	
			
			var arr = new Array();
			arr[0] = "#028be9";
			arr[1] = "#01aaed";
			arr[2] = "#02c8eb";
			arr[3] = "#196ecd";
			arr[4] = "#06dfd2";
			currentArr = _.pluck(BarJsonData, 'current');
			currentMax = _.max(currentArr,function(d){return parseInt(d);});
			var intcurrentMax = parseInt(currentMax)*1.5;
			
			maxcumulativeArr = _.pluck(BarJsonData, 'cumulative');
			cumulativeMax = _.max(maxcumulativeArr,function(d){return parseInt(d);});
			var maxcumulativeMax = parseInt(cumulativeMax)*1.5;
			if(BarJsonData){
				$("#bar-slide1-list").html("");
				$.each(BarJsonData, function(i, item) {
					var _current = 0;
					if(intcurrentMax > 0){
						_current = item.current/intcurrentMax * 100;
					}
					var _cumulative = 0;
					if(maxcumulativeMax > 0){
						_cumulative = item.cumulative/maxcumulativeMax * 100;
					}
		            $("#bar-slide1-list").append(
					       '<div class="bar-slide1-listItem"><ul><li>' + item.name + '</li>' + 
						   '<li><span class="slide1-bar"style="background-color:#d38a23;width:' + _current + '%"><span>'+ item.current +'</span></span></li>'+
		                 '<li><span class="slide1-bar"style="background-color:#e9643b;width:' + _cumulative + '%"><span>'+ item.cumulative +'</span></span></li>'+
						  '<li>'+item.average +'</li></ul></div>'
						  );
		        });
			}
		}
		timerFunctionTimer = setTimeout("timerFunction();", 60000);
	});
	
}
$(function(){
	
	swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        paginationClickable: true,
		keyboardControl: true,
        autoHeight: true//enable auto height
    });
	timerFunction();
});