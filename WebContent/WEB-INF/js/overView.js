



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
		refreshHeatmapData();
		return;
	};



	var removeOption = function(renderId) {
		$('#' + renderId + ' .addoption').remove().trigger("liszt:updated");
	};
	
	var refreshHeatmapData = function() {
		var placeId = $("#marketSel").val();
		$.post("/sva/heatmap/api/getOverData", {
			store : placeId
		}, function(data) {
			if (!data.error) {
				$(".chartArea").show();
				$("#total").text(data.cout);
				$("#maxDay").text(data.allcount);
				var val;
				if (data.yesterdayAllcount>0&&data.allcount>0) {
					val= (data.allcount/data.yesterdayAllcount)*100;	
				}else
					{
					val=0;
					}

				$("#maxTime").text(val.toFixed(2)+"%");
				showChart(data.number,data.names,data.Max);
				showChart1(data.visitList,data.names);
//				showChart1(data.number,data.names,data.Max);
//				showChart2(data.number,data.names,data.Max);
			}
			 
		});
	};

	var showChart = function(numbers, names,max){
				var myChart = echarts.init(document.getElementById("chart"));
				var option = {
//					    title: {
//					        x: 'center',
//					        text: i18n_info_title
//					    },
//						backgroundColor :'rgb(81,103,127)',
					    tooltip: {
					        trigger: 'item'
					    },
					    toolbox: {
					        show: true,
					        feature: {
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
					    calculable: false,
//					    grid:{
//					    	y:0,
//					    	x2:10,
//					    	y2:25
//					    	},
					    xAxis: [
						        {
						            type: 'value',
						            show: true
						        }
					    ],
					    yAxis: [
						        {
						            type: 'category',
						            show: true,
						            data: names
						        }
					    ],
					    series: [
					        {
					        	barMaxWidth:45,
					            name: i18n_info_title,
					            type: 'bar',
					            itemStyle: {
					                normal: {
//					                    color: function(params) {
//					                        // build a color map as your need.
//					                        var colorList = [
//					                          '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
//					                           '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
//					                           '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0',
//					                           '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
//					                           '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
//					                           '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0',
//					                           '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
//					                           '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
//					                           '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
//					                        ];
//					                        return colorList[params.dataIndex]
//					                    },
					                    label: {
					                        show: true,
					                        position: 'right',
					                        formatter: '{c}'
					                    },
					                    position:'right'
					                }
					            },
					            data: numbers,
					            markPoint: {
					                tooltip: {
					                    trigger: 'item',
					                    backgroundColor: 'rgba(255,110,110,111)'

					                },
					                data: [

					                ]
					            }
					        }
					    ]
					};
					         
				
				myChart.setOption(option);
	};
	
	
	var showChart1 = function(numbers, names){
		var myChart = echarts.init(document.getElementById("chart1"));
		var option = {
			    tooltip: {
			        trigger: 'item'
			    },
			    toolbox: {
			        show: true,
			        feature: {
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
			    calculable: false,
			    xAxis: [
				        {
				            type: 'value',
				            show: true
				        }
			    ],
			    yAxis: [
				        {
				            type: 'category',
				            show: true,
				            data: names
				        }
			    ],
			    series: [
			        {
			        	barMaxWidth:45,
			            name: i18n_info_title,
			            type: 'bar',
			            itemStyle: {
			                normal: {
			                    label: {
			                        show: true,
			                        position: 'right',
			                        formatter: '{c}'
			                    },
			                    position:'right'
			                }
			            },
			            data: numbers,
			            markPoint: {
			                tooltip: {
			                    trigger: 'item',
			                    backgroundColor: 'rgba(255,110,110,111)'

			                },
			                data: [

			                ]
			            }
			        }
			    ]
			};
			         
		
		myChart.setOption(option);
};
	
	
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
							

					});
				}
			});
		},
		bindClickEvent : function() {
		$('#marketSel').change(function(e) {
			var placeId = $("#marketSel").val();
			$.post("/sva/heatmap/api/getOverData", {
				store : placeId
			}, function(data) {
				if (!data.error) {
					$(".chartArea").show();
					$("#total").text(data.cout);
					$("#maxDay").text(data.allcount);
					var val;
					if (data.yesterdayAllcount>0&&data.allcount>0) {
						val= (data.allcount/data.yesterdayAllcount)*100;	
					}else
						{
						val=0;
						}
					$("#maxTime").text(val.toFixed(2)+"%");
					showChart(data.number,data.names,data.Max);
					showChart1(data.visitList,data.names);

				}
			});
			 
			});
		}

	};

}();