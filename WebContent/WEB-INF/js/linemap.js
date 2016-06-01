var Linemap = function() {
	/**
	 * 将对应信息填充到对应的select @ param renderId [string] 标签id @ param data [array]
	 *                   列表数据
	 */
	var updateList = function(renderId, data, callback) {
		var sortData = data.sort(function(a,b){return a.name - b.name;});
	    var len = sortData.length;
	    var options = '';
	    for(var i=0;i<len;i++){
	    	var info = sortData[i];
    		options += '<option class="addoption" value="'+info.id+'">' + HtmlDecode3(info.name) +'</option>';
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    if(callback){
	        callback();
	    }
	    return;
	};

	var removeOption = function(renderId) {
		$('#' + renderId + ' .addoption').remove().trigger("liszt:updated");
	};
	
	var showInfo = function(placeId, startTime, endTime){
		$.post("/sva/linemap/api/getInfoData",{placeId:placeId,startTime:startTime,endTime:endTime},function(data){
			if(!data.error){
				if (data.total==0){
					$("#total").text(0);
					$("#maxDay").text("");
					$("#maxTime").text("");		
				}else
					{
					var md = _.pluck(data.maxDay,"time");
					var mt = _.pluck(data.maxHour,"time");
					if(md!=""){
					var	md1 = md[0].split(" ")[0];
					$("#maxDay").text(md1);
					}else
						{
						$("#maxDay").text("");
						}
					if(mt!=""){
					var	mt1 = mt[0].split(".")[0];
					$("#maxTime").text(mt1);		
					}else{
						$("#maxTime").text("");
					}
					$("#total").text(data.total);
//					$("#maxDay").text(md);
//					$("#maxTime").text(mt);				
					}
					
			}
		});
	};
	
	var showChart = function(placeId, startTime, endTime, dim){
		console.log(dim);
		$.post("/sva/linemap/api/getChartData",{placeId:placeId,startTime:startTime,endTime:endTime,dim:dim},function(data){
			if(!data.error){
				var startTime = $("#select_time_begin_tab1").val();
				var endTime = $("#select_time_end_tab1").val();
				var xTitle = _.pluck(data.data,"time");
				var yVal = _.pluck(data.data,"number");
//				console.log(xTitle);
//				console.log(yVal);
				//$("#chart").empty();
				
				var myChart = echarts.init(document.getElementById("chart"));
				var option = {
//				    title : {
//				        text: i18n_title
//				    },
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
	
	var showInfo1 = function(placeId, startTime, endTime){
		$.post("/sva/barmap/api/getInfoData",{placeId:placeId,startTime:startTime,endTime:endTime},function(data){
			if(!data.error){
				if (data.data==""||data.data==null) {
//					$("#total").text(0);
//					$("#max").text("");
//					$("#min").text("");	
				}else
					{
//					var max = _.max(data.data,function(d){return d.number;});
//					var min = _.min(data.data,function(d){return d.number;});
//					$("#total").text(data.total);
//					$("#max").text(max.floor);
//					$("#min").text(min.floor);				
					}
			}
		});
	};
	
	var showChart1 = function(placeId, startTime, endTime){
		$.post("/sva/barmap/api/getChartData",{placeId:placeId,startTime:startTime,endTime:endTime},function(data){
			if(!data.error){

				var xTitle = _.pluck(data.data,"floor");
				var yVal = _.pluck(data.data,"number");
				var option = [];
				var option1 = [];
				if (data.data!="") {
					option = [{type : 'max', name: i18n_max},
				                {type : 'min', name: i18n_min}];
					option1 = [{type : 'average', name: i18n_avg}];
					console.log(xTitle);
				}
				console.log(yVal);
				//$("#chart").empty();
				
				var myChart = echarts.init(document.getElementById("chart1"));
				
				var option = {
//				    title : {
//				    	x: 'center',
//				        text: i18n_title1
//				    },
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
				    yAxis : [
				        {
				            type : 'category',
				            data : xTitle
				        }
				    ],
				    xAxis : [
				        {
				            type : 'value',
				            boundaryGap : [0, 0.01]
				        }
				    ],
				    series : [
				        {
				        	barMaxWidth:45,
				            name:i18n_tag,
				            type:'bar',
				            data:yVal,
				            markPoint : {
				                data : option
				            },
				            markLine : {
				                data : option1
				            }
				        }
				    ]
				};
					                    
				
				myChart.setOption(option);

			}
		});
	};
	
	var getAreaName = function(param)
	{
	
		$.post("/sva/areamap/api/getAreaName",param,function(data){
			if(!data.error){
				var resJson = {};
				var time = $("#select_time_begin_tab1").val();
				var dayTime = time.replace(/-/g,"/");
				var xTitle= _.keys(data.data);
				var yVal = _.values(data.data);
				var myChart = echarts.init(document.getElementById("chart2"));
				var option = {
//				    title : {
//				        text: i18n_title
//				    },
				    legend: {
				        data:data.name
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
				            data : data.time
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
				    series : data.data
				};                  
				
				myChart.setOption(option);
				
				
				}
			
		});
	};
	
	var getVisitTime = function(param)
	{
		$.post("/sva/areamap/api/getVisitTime",param,function(data){
			if(!data.error){
				var myVisitChart = echarts.init(document.getElementById("chart3"));
				var option = {
//				    title : {
//				        text: i18n_title
//				    },
				    legend: {
				        data:data.name
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
				            data : data.time
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            axisLabel : {
				                formatter: '{value} '+i18n_visitTime
				            }
				        }
				    ],
				    series : data.visitData
				};                  
				
				myVisitChart.setOption(option);
				
				
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
				dateFmt : 'yyyy-MM-dd HH:00:00',
				maxDate : '%y-%M-%d 23:00:00',
				skin : "twoer"
			});
		},
		
		bindClickEvent: function(){
    		
    		//  确认按钮点击  触发热力图刷新    		
    		$('#confirm').click(function(e){
    			placeId = $("#marketSel").val();
    			startTime = $("#select_time_begin_tab1").val();
    			endTime = $("#select_time_end_tab1").val();
      			if (placeId=="") 
    			{
      				$(".demoform").submit();
//      				$("#marketSel").blur();
    				return false;
    			}
      			if (startTime=="") 
      			{
      				$(".demoform").submit();
//      				$("#select_time_begin_tab1").blur();
      				return false;
      			}
      			if (endTime=="") 
      			{
      				$(".demoform").submit();
//      				$("#select_time_end_tab1").blur();
      				return false;
      			}
    			if(startTime>=endTime)
    			{
//    				$(".demoform").submit();
    				$("#msgdemo2").removeClass("Validform_right");
    				$("#msgdemo2").addClass("Validform_wrong");
    				$("#msgdemo2").text(i18n_time);
    				return false;
    			}
				$("#msgdemo2").removeClass("Validform_wrong");
    			$("#msgdemo2").addClass("Validform_right");
    			$("#msgdemo2").text(pa);
    			if(placeId && startTime && endTime){
    				$(".chartArea").show();
    				showInfo(placeId, startTime, endTime);
    				showChart(placeId, startTime, endTime,1);
    				$(".chartArea1").show();
    				showInfo1(placeId, startTime, endTime);
    				showChart1(placeId, startTime, endTime);
    				$(".chartArea2").show();
//    				getAreaName(param);
    				
    			}
    			var param = {
    					placeId :placeId,
    					timeBegin :startTime ,
    					timeEnd :endTime 
    			};
    			$("#hour").click();
    			$("#hour1").click();
    			getVisitTime(param);
    			
    		});
    		
    		$("#hour").click(function(e){
    			placeId = $("#marketSel").val();
    			startTime = $("#select_time_begin_tab1").val();
    			endTime = $("#select_time_end_tab1").val();
    			
				showChart(placeId, startTime, endTime, 1);
    		});
    		
    		$("#day").click(function(e){
    			placeId = $("#marketSel").val();
    			startTime = $("#select_time_begin_tab1").val();
    			endTime = $("#select_time_end_tab1").val();
    			
				showChart(placeId, startTime, endTime, 2);
    		});
    		$("#hour1").click(function(e){
    			var param = {
    					id:1,
    					placeId :placeId,
    					timeBegin :startTime ,
    					timeEnd :endTime 
    			};
    			getAreaName(param);
    		});
    		
    		$("#day1").click(function(e){
    			var param = {
    					id:2,
    					placeId :placeId,
    					timeBegin :startTime ,
    					timeEnd :endTime 
    			};
    			
    			getAreaName(param);
    			
    		});
		}
	};

}();