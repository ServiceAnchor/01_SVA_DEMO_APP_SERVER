
var Rangemap = function() {
	/**
	* 将对应信息填充到对应的select
	* @ param renderId [string] 标签id
	* @ param data [array] 列表数据
	*/
	var updateList = function(renderId,data,selectTxt,callback){
		var sortData = data.sort(function(a,b){return a.areaName - b.areaName;});
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
	
	/**
	* 将对应信息填充到对应的select
	* @ param renderId [string] 标签id
	* @ param data [array] 列表数据
	*/
	
	var removeOption = function(renderId) {
		$('#' + renderId + ' .addoption').remove().trigger("liszt:updated");
	};
	
	var showChart = function(param){
		$.post("/sva/areamap/api/getChartData",param,function(data){
			if(!data.error){
//				if (data.data==null||data.data=="") 
//				{
//				$("#total").text(0);
//				$("#max").text();
//				$("#min").text();
//				}else
//				{
//				var max = _.max(data.data,function(d){return d.number;});
//				var min = _.min(data.data,function(d){return d.number;});
//				$("#max").text(max.time);
//				$("#min").text(min.time);
//				}
				var resJson = {};
				var time = $("#select_time_begin_tab1").val();
				var dayTime = time.replace(/-/g,"/");
//				for(var i=0; i<24; i++){
//					if (i<10) 
//					{
//						var key = dayTime+" 0"+i+":00:00";
//						resJson[key] = 0;	
//					}else
//						{
//						var key = dayTime+" "+i+":00:00";
//						resJson[key] = 0;
//						}
//				}
//				var dataRes= data.data;
//				for(var d in dataRes){
//					if(resJson[dataRes[d].time] === 0){
//						resJson[dataRes[d].time] = dataRes[d].number;
//					}
//				}
//				var xTitle= _.keys(resJson);
//				var yVal = _.values(resJson);
//				console.log(xTitle);
//				console.log(yVal);
			//	var xTitle = _.pluck(data.data);
			//	var yVal = _.pluck(data.data);
				var xTitle= _.keys(data.data);
				var yVal = _.values(data.data);
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
			$.get("/sva/areamap/api/getAreaNames",function(data){
				if(!data.error){
					updateList("areaName",data.data);
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
				dateFmt : 'yyyy-MM-dd HH:00:00',
				maxDate : '%y-%M-%d 23:00:00',
				skin : "twoer"
			});
		},
		
		bindClickEvent: function(){
			
			//  确认按钮点击  触发热力图刷新    		
			$('#confirm').click(function(e){
    			if ($("#areaName").val()=="") {
    				$(".demoform").submit();
//    				$("#areaName").blur();
    				return false;
				}
    			if ($("#select_time_begin_tab1").val()=="") {
    				$(".demoform").submit();
//    				$("#select_time_begin_tab1").blur();
    				return false;
    			}
    			if ($("#select_time_end_tab1").val()=="") {
    				$(".demoform").submit();
//    				$("#select_time_end_tab1").blur();
    				return false;
    			}
			var param = {
					areaName : $("#areaName").val(),
					timeBegin : $("#select_time_begin_tab1").val(),
					timeEnd : $("#select_time_end_tab1").val()
				};
			//	var floor = $("select[name='floorSelName']").find("option:selected").text();
//			if (param.areaName == "" ||param.timeBegin==""||param.timeEnd==="") {
//					alert(i18n_info);
//					return false;
//				}
			if(param.timeBegin >= param.timeEnd)
			{
				$("#msgdemo2").removeClass("Validform_right");
				$("#msgdemo2").addClass("Validform_wrong");
				$("#msgdemo2").text(i18n_time);
				return false;
			}
			$("#msgdemo2").removeClass("Validform_wrong");
			$("#msgdemo2").addClass("Validform_right");
			$("#msgdemo2").text(pa);
			if(param.areaName && param.timeBegin && param.timeEnd){
				$(".chartArea").show();
				showChart(param);
			//	showInfo(param);
			}
		});
		}
	};

}();