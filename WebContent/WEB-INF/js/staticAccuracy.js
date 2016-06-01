$("#exportButton").click(function(e){
	
	window.location.href = basePath +'staticAccuracy/api/staticExportCodeTemplate';
	})
	
var staticAccuracy = function () {
	return {
		initTable:function(){
			oTable = $('#table').dataTable( {
				"bProcessing": true,
				"sAjaxSource": "/sva/staticAccuracy/api/getTableData",
				"sServerMethod": "POST",
				"sDom": 'rt<"toolbar"lp<"clearer">>',
				"sPaginationType": "full_numbers",
				"bServerSide": true,
				"bStateSave": true,
				"fnServerParams": function(aoData) {
					console.log(aoData);
				},
				"fnCookieCallback": function (sName, oData, sExpires, sPath) {      
					// Customise oData or sName or whatever else here     
					var newObj = {iLength:oData.iLength};
					return sName + "="+JSON.stringify(newObj)+"; expires=" + sExpires +"; path=" + sPath;    
				},
				"fnServerData": function ( sSource, aoData, fnCallback, oSettings ) {
					var newObj={};
					for(var i in aoData){
						var key = aoData[i].name;
						var val = aoData[i].value;
						switch(key){
							case "sEcho":
								newObj["sEcho"] = val;
								break;
							case "iDisplayStart":
								newObj["iDisplayStart"] = val;
								break;
							case "iDisplayLength":
								newObj["iDisplayLength"] = val;
								break;
							case "iSortingCols":
								newObj["iSortingCols"] = val;
								break;
							case "iSortCol_0":
								newObj["iSortCol0"] = val;
								break;
							case "sSortDir_0":
								newObj["sSortDir0"] = val;
								break;
							case "mDataProp_0":
								newObj["mDataProp0"] = val;
								break;
							case "mDataProp_1":
								newObj["mDataProp1"] = val;
								break;
							case "mDataProp_2":
								newObj["mDataProp2"] = val;
								break;
							case "mDataProp_3":
								newObj["mDataProp3"] = val;
								break;
							case "mDataProp_4":
								newObj["mDataProp4"] = val;
								break;
							case "mDataProp_5":
								newObj["mDataProp5"] = val;
								break;
							case "mDataProp_6":
								newObj["mDataProp6"] = val;
								break;
							case "mDataProp_7":
								newObj["mDataProp7"] = val;
								break;
							case "mDataProp_8":
								newObj["mDataProp8"] = val;
								break;
							case "mDataProp_9":
								newObj["mDataProp9"] = val;
								break;
							case "mDataProp_10":
								newObj["mDataProp10"] = val;
								break;
							case "mDataProp_11":
								newObj["mDataProp11"] = val;
								break;
							case "mDataProp_12":
								newObj["mDataProp12"] = val;
								break;
							case "iColumns":
								newObj["iColumns"] = val;
								break;
							default:
								break;
						}
					}
					oSettings.jqXHR = $.ajax({
						"dataType": 'json', 
						"contentType" : "application/json",
						"type": "POST", 
						"url": sSource, 
						"data": JSON.stringify(newObj), 
						"success": fnCallback
					});
				},
				"aoColumnDefs": [
					{ 
						"aTargets": [0],
						"bVisible": false,
						"mData": "id" 
					},
					{ 
						"aTargets": [1],
						"mData": "place",
						
					},
					{ 
						"aTargets": [2],
						"mData": "floor"
					},
					{ 
						"aTargets": [3],
						"mData": "origin"
					},
					{ 
						"aTargets": [4],
						"mData": "destination",
						"mRender": function ( data, type, full ) {
							if(!data){
								return "-";
							}else{
								return data;
							}
						}
					},
					{ 
						"aTargets": [5],
						"mData": "startdate",
						"mRender": function ( data, type, full ) {
							var date = new Date();
							date.setTime(data);
							return dateFormat(date,"yyyy/MM/dd HH:mm:ss");
						}
					},
					{ 
						"aTargets": [6],
						"mData": "enddate",
						"mRender": function ( data, type, full ) {
							var date = new Date();
							date.setTime(data);
							return dateFormat(date,"yyyy/MM/dd HH:mm:ss");
						}
					},
					{ 
						"aTargets": [7],
						"mData": "triggerIp"
					},
					{ 
						"aTargets": [8],
						"mData": "avgeOffset"
					},
					{ 
						"aTargets": [9],
						"mData": "maxOffset"
					},
					{ 
						"aTargets": [10],
						"mData": "staicAccuracy"
					},
					{ 
						"aTargets": [11],
						"mData": "offsetCenter"
					},
					{ 
						"aTargets": [12],
						"mData": "offsetNumber"
						
					},
					{ 
						"aTargets": [13],
						"mData": "stability"
						
					},
					{
	                    "aTargets": [14],
	                    "bSortable": false,
	                    "bFilter": false,
	                    "mData": function(source, type, val) {
	                        return "";
	                    },
	                    "mRender": function ( data, type, full ) {
	                    	console.log(full);
	                        return '<a data-type="detail" data-toggle="modal" href="#modal" data-id="'+full.id+'">'+i18n_detail+'</a>';
	                      }
	                }
				]
			} );
        },
        initPopupText: function(data){
        	if(data){
				var val = '';
				if (data.offset<=data.deviation) {
					 val = i18n_Valid;
				}else{
					val = i18n_Invalid;
				}
	        	$("#place").text(data.place);
	        	$("#floor").text(data.floor);
	        	$("#x").text(data.origin);
	        	$("#y").text(data.destination);
		        	var startTime = data.startdate;
		        	var date = new Date();
		        	date.setTime(startTime);
		        	startTime =  dateFormat(date,"yyyy/MM/dd HH:mm:ss");
		        $("#startTime").text(startTime);
			        var endTime = data.enddate;
		        	var date = new Date();
		        	date.setTime(endTime);
		        	endTime =  dateFormat(date,"yyyy/MM/dd HH:mm:ss");
	        	$("#endTime").text(endTime);
	        	$("#trigger").text(data.triggerIp);
	        	$("#avgeOffset").text(data.avgeOffset);
				$("#maxOffset").text(data.maxOffset);
				$("#staicAccuracy").text(data.staicAccuracy);
				$("#offsetCenter").text(data.offsetCenter);
				$("#offsetNumber").text(data.offsetNumber);
				$("#stability").text(data.stability);
				$("#result").text(val);
        	}
        },
        
        initPopupChart: function(data){
        	var dataObj = [{
	        		name:"3m "+i18n_minus,value:data.count3
	        	},{
	        		name:"3m-5m",value:data.count5
	        	},{
	        		name:"5m-10m",value:data.count10
	        	},{
	        		name:"10m "+i18n_plus,value:data.count10p
	        	}
        	];
        	
        	var myChart = echarts.init(document.getElementById("chart")); 
        	var val = '';
        	if (data.offset<data.variance+1) {
				 val = i18n_Valid;
			}else
				{
				val = i18n_Invalid;
				}
        	var option = {
        		customData:data,
    		    title : {
    		        text: i18n_chart_title,
    		        x:'center'
    		    },
    		    tooltip : {
    		        trigger: 'item',
    		        formatter: "{a} <br/>{b} : {c} ({d}%)"
    		    },
    		    legend: {
    		        orient : 'vertical',
    		        x : 'left',
    		        data:['3m '+i18n_minus,'3m-5m','5m-10m','10m '+i18n_plus]
    		    },
    		    toolbox: {
    		        show : true,
    		        feature : {
    		            dataView : {
    		            	show: true, 
    		            	title:i18n_dataview,
    		            	readOnly: true,
    		            	lang:[i18n_dataview,i18n_close],
    		            	optionToContent: function(opt) {
    		            		var dataStr = opt.customData.detail;
    		            	//	var xo = opt.customData.x;
    		            	//	var yo = opt.customData.y;
    		            		var dataObj = JSON.parse(dataStr);
    		            		var table = '<table style="width:100%;text-align:center"><tbody><tr>'
                                    + '<td>'+i18n_common_info+'</td>'
                                    + '</tr>';
    		            		for (var i = 0, l = dataObj.length; i < l; i++) {
    		            			//var offset = Math.sqrt(Math.pow(Math.abs(dataObj[i].x-xo),2)+Math.pow(Math.abs(dataObj[i].y-yo),2));
    		            			table += '<tr><td>'
		                                  + i + '; '
		                                  + dataObj[i].x + '; '
		                                  + dataObj[i].y + '; '
		                                  + dataObj[i].timestamp + '; '
		                                 // + offset.toFixed(2)
		                                  + '</td></tr>';
    		            		}
    		            		table += '</tbody></table>';
                       
    		            		return table;
    		            	}
    		            },
    		            magicType : {
    		                show: false, 
    		                type: ['pie', 'funnel'],
    		                option: {
    		                    funnel: {
    		                        x: '25%',
    		                        width: '50%',
    		                        funnelAlign: 'left',
    		                        max: 1548
    		                    }
    		                }
    		            },
    		            restore : {show: false},
    		            saveAsImage : {show: true,title:i18n_saveimg}
    		        }
    		    },
    		    series : [
    		        {
    		            name:i18n_chart_tip,
    		            type:'pie',
    		            radius : '55%',
    		            center: ['50%', '60%'],
    		            data:dataObj
    		        }
    		    ]
    		};
        	
        	myChart.setOption(option);
        		                    
        }
    
    };

}();