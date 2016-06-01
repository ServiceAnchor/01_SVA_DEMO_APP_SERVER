$("#exportButton").click(function(e){
	
	window.location.href = basePath +'push/api/exportCodeTemplate';
	});
	
var Accuracy = function () {
	return {
		initTable:function(){
        	$.get("/sva/push/api/getTableData?t="+Math.random(),function(data){
        		if(!data.error){
        			if(oTable){oTable.fnDestroy();};
        			oTable = $('#table').dataTable({
        				"bProcessing": true,
        				"sDom": 'rt<"toolbar"lp<"clearer">>',
        				"sPaginationType": "full_numbers",
        				"aaData":data.data,
        				"bStateSave": true,
        				"aoColumnDefs": [
        									{ 
        										"aTargets": [0],
        										"bVisible": false,
        										"mData": "id" 
        									},
        									{ 
        										"aTargets": [1],
        										"mData": "place",
        										"mRender": function ( data, type, full ) {
        											if(data.length > 20){
        												var html = data.substring(0,20)+"...";
        												html = '<span title="'+data+'">'+html+'</span>';
        													return html;
        											}
        											return data;
        										}
        									},
        									{ 
        										"aTargets": [2],
        										"mData": "floor"
        									},
        									{ 
        										"aTargets": [3],
        										"mData": "pushRight"
        									},
        									{ 
        										"aTargets": [4],
        										"mData": "pushWrong"
        									},
        									{ 
        										"aTargets": [5],
        										"mData": "notPush"
        									},
        									{ 
        										"aTargets": [6],
        										"mData": "centerRadius"
        									},
        									{ 
        										"aTargets": [7],
        										"mData": "centerReality"
        									},
        									{ 
        										"aTargets": [8],
        										"mData": "isRigth",
        										"mRender": function ( data, type, full ) {
        											var html;
        											if(data=="0"){
        												html = i18n_fou;
        												
        													return html;
        											}else
        												{
        												html =i18n_shi;
        												}
        											return html;
        										}
        									},
        									
        									{
        					                    "aTargets": [9],
        					                    "bSortable": false,
        										"mData": "updateTime",
        										"mRender": function ( data, type, full ) {
        											var date = new Date();
        											date.setTime(data);
        											return dateFormat(date,"yyyy/MM/dd HH:mm:ss");
        										}
        					                }
        								],
        				"fnCookieCallback": function (sName, oData, sExpires, sPath) {      
        					// Customise oData or sName or whatever else here     
        					var newObj = {iLength:oData.iLength};
        					return sName + "="+JSON.stringify(newObj)+"; expires=" + sExpires +"; path=" + sPath;    
        				}
        			});
        		}        		
        	});
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
				$("#offset").text(data.offset);
				$("#estimate").text(data.deviation);
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
		                                  + i + ';'
		                                  + dataObj[i].x + ';'
		                                  + dataObj[i].y + ';'
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