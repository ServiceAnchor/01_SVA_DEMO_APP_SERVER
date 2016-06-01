var swiper;
$(function(){
	swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        paginationClickable: true,
        autoHeight: true, //enable auto height
    });
	
	//var html = '<div class="bar-slide1-listItem"><ul><li>Open ROADS</li><li><span class="slide1-bar"style="background-color:#028be9;width:30%"><span>30</span></span></li><li><span class="slide1-bar"style="background-color:#028be9;width:30%"><span>30</span></span></li><li>9</li></ul></div>';
	
	if(BarJsonData){
		$.each(BarJsonData.item, function(i, item) {
			var _current = item.current/BarJsonData.max.maxcurrent * 100;
			var _cumulative = item.cumulative/BarJsonData.max.maxcumulative * 100;
            $("#bar-slide1-list").append(
			       '<div class="bar-slide1-listItem"><ul><li>' + item.name + '</li>' + 
				   '<li><span class="slide1-bar"style="background-color:' + item.barcolor + ";width:" + _current + '%"><span>'+ item.current +'</span></span></li>'+
                 '<li><span class="slide1-bar"style="background-color:' + item.barcolor2 + ";width:" + _cumulative + '%"><span>'+ item.cumulative +'</span></span></li>'+
				  '<li>'+item.average +'</li></ul></div>'
				  );
        });
	}
});