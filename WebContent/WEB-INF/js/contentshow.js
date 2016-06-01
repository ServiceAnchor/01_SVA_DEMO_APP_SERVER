var contentshow = function() {
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
	var updateFloorList1 = function(renderId,data,selectTxt,callback){
		$("#floorSel").find("option").remove(); 
	    var sortData = data.sort(function(a,b){return a.floor - b.floor;});
	    var len = sortData.length;
	    var options = '';
	    for(var i=0;i<len;i++){
	    //	var info = sortData[i];
	        if(selectTxt == sortData[i].floor){
	    		options += '<option class="addoption" selected=true value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
	    	}else{
	    		options += '<option class="addoption"  value="'+sortData[i].floorNo+'">' + sortData[i].floor +'</option>';
	    	}
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    return;
	};

	var removeOption = function(renderId) {
		$('#' + renderId + ' .addoption').remove().trigger("liszt:updated");
	};
	
	return {
		updateFloorList1: function(renderId,data,selectTxt,callback){
			updateFloorList1(renderId,data,selectTxt,callback);
		},
		initDropdown : function() {
			$("#editBox").show();
			$("#add").hide();
			$.get("/sva/store/api/getData?t="+Math.random(),function(data){
				if(!data.error){
					updateList("placeSel",data.data);
				}
			});
			$.get("/sva/store/api/getData?t="+Math.random(),function(data){
				if(!data.error){
					updateList("placeSel2",data.data);
				}
			});
			$.get("/sva/store/api/getData?t="+Math.random(),function(data){
				if(!data.error){
					updateList("placeSel3",data.data);
				}
			});
			$.get("/sva/store/api/getData?t="+Math.random(),function(data){
				if(!data.error){
					updateList("placeSel4",data.data);
				}
			});
			$.get("/sva/store/api/getData?t="+Math.random(),function(data){
				if(!data.error){
					updateList("placeSel5",data.data);
				}
			});
			$.get("/sva/store/api/getData?t="+Math.random(),function(data){
				if(!data.error){
					updateList("placeSel6",data.data);
				}
			});
			$.get("/sva/store/api/getData?t="+Math.random(),function(data){
				if(!data.error){
					updateList("placeSel7",data.data);
				}
			});
			$.get("/sva/store/api/getData?t="+Math.random(),function(data){
				if(!data.error){
					updateList("placeSel8",data.data);
				}
			});
			var options = '';
    		for ( var i = 1; i <= 60; i++) {
    			if(i==5){
        			options += '<option class="addoption" selected value="' + i + '">' + i + '</option>';    				
    			}else{
        			options += '<option class="addoption" value="' + i + '">' + i + '</option>';    				
    			}
    		}
    		$('#periodSel').append(options);
    		$('#periodSel').trigger("liszt:updated");
    		paramValue();
		},
		
		
		showDate: function(id){
			WdatePicker({
				el : document.getElementById(id),
				lang : i18n_language,
				isShowClear : false,
				isShowToday:false,
				readOnly : true,
				dateFmt : 'HH:00:00',
				maxDate : '23:00:00',
				skin : "twoer"
			});
		},
		
		bindClickEvent: function(){
			// 地点下拉列表修改 触发楼层下拉列表变化
    		$("#placeSel").on("change", function(){
    			var placeId = $("#placeSel").val();
				$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeId}, function(data){
					if(!data.error){
						updateFloorList1("zSel",data.data);
						
					}
				});
	    		$('a[href="#myModal1"]').attr("disabled","disabled");
	    		$("#search").hide();
			});
    		$("#placeSel2").on("change", function(){
    			var placeId = $("#placeSel2").val();
    			$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeId}, function(data){
    				if(!data.error){
    					updateFloorList1("zSel2",data.data);
    				}
    			});
    			$('a[href="#myModal1"]').attr("disabled","disabled");
    			$("#search").hide();
    		});
    		$("#placeSel3").on("change", function(){
    			var placeIdSp = $("#placeSel3").val();
    			$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeIdSp}, function(data){
    				if(!data.error){
    					updateFloorList1("zSel3",data.data);
    				}
    			});
    		});
    		$("#placeSel4").on("change", function(){
    			var placeId = $("#placeSel4").val();
    			$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeId}, function(data){
    				if(!data.error){
    					updateFloorList1("zSel4",data.data);
    				}
    			});
    			$('a[href="#myModal1"]').attr("disabled","disabled");
    			$("#search").hide();
    		});
    		$("#placeSel5").on("change", function(){
    			var placeId = $("#placeSel5").val();
    			$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeId}, function(data){
    				if(!data.error){
    					updateFloorList1("zSel5",data.data);
    				}
    			});
    		});
    		$("#placeSel6").on("change", function(){
    			var placeId = $("#placeSel6").val();
    			$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeId}, function(data){
    				if(!data.error){
    					updateFloorList1("zSel6",data.data);
    				}
    			});
    		});
    		$("#placeSel7").on("change", function(){
    			var placeId = $("#placeSel7").val();
    			$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeId}, function(data){
    				if(!data.error){
    					updateFloorList1("zSel7",data.data);
    				}
    			});
    		});
    		$("#placeSel8").on("change", function(){
    			var placeId = $("#placeSel8").val();
    			$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeId}, function(data){
    				if(!data.error){
    					updateFloorList1("zSel8",data.data);
    				}
    			});
    		});
    		
    		//  确认按钮点击  触发热力图刷新    		
    		$('#confirm').click(function(e){
    			var check = validForm.check(false);
              	if (!check) {
					return false;
				}
//    			var placeId = $("#placeSel").val();
//    			var placeId2 = $("#placeSel2").val();
//    			var placeId3 = $("#placeSel3").val();
//    			var placeId4 = $("#placeSel4").val();
//    			var placeId5 = $("#placeSel5").val();
//    			var placeId6 = $("#placeSel6").val();
//    			var placeId7 = $("#placeSel7").val();
//    			var placeId8 = $("#placeSel8").val();
    			var floorSel = $("#zSel").val();
    			var floorSel2 = $("#zSel2").val();
    			var floorSel3 = $("#zSel3").val();
    			var floorSel4 = $("#zSel4").val();
    			var floorSel5 = $("#zSel5").val();
    			var floorSel6 = $("#zSel6").val();
    			var floorSel7 = $("#zSel7").val();
    			var floorSel8 = $("#zSel8").val();
    			var periodSel = $("#periodSel").val();
    			var	coefficient = $("#coefficient").val();
    			
    			startTime = $("#select_time_begin_tab1").val();
    			var densitySel = $("#densitySel").val();
    			var radiusSel = $("#radiusSel").val();
    			var densitySel1 = $("#densitySel1").val();
    			var radiusSel1 = $("#radiusSel1").val();
    			var densitySel2 = $("#densitySel2").val();
    			var radiusSel2 = $("#radiusSel2").val();
    			var densitySel3 = $("#densitySel3").val();
    			var radiusSel3 = $("#radiusSel3").val();
    			var densitySel4 = $("#densitySel4").val();
    			var radiusSel4 = $("#radiusSel4").val();
    			var densitySel5 = $("#densitySel5").val();
    			var radiusSel5 = $("#radiusSel5").val();
    			var densitySel6 = $("#densitySel6").val();
    			var radiusSel6 = $("#radiusSel6").val();
    			var densitySel7 = $("#densitySel7").val();
    			var radiusSel7 = $("#radiusSel7").val();
    			//floor全部空为不提交
//    			if (floorSel==" " && floorSel2==" " && floorSel2Sp==" " && floorSel3==" " && floorSel3Sp==" ")
//    			{
//    				return false;
//    			}
//    			//空值默认转0
//    			if (placeId==" ")	//1
//    			{
//    				placeId=0;
//    			}
//    			if (placeId2==" ")	//2
//    			{
//    				placeId2=0;
//    			}
//    			if (placeId2Sp==" ")	//3
//    			{
//    				placeId2Sp=0;
//    			}
//    			if (placeId3==" ")	//4
//    			{
//    				placeId3=0;
//    			}
//    			if (placeId3Sp==" ")	//5
//    			{
//    				placeId3Sp=0;
//    			}
//    			if (floorSel==" ")	//6
//    			{
//    				floorSel=0;
//    			}
//    			if (floorSel2==" ")	//7
//    			{
//    				floorSel2=0;
//    			}
//    			if (floorSel2Sp==" "||floorSel2Sp==null)//8
//    			{
//    				floorSel2Sp=0;
//    			}
//    			if (floorSel3==" ")	//9
//    			{
//    				floorSel3=0;
//    			}
//    			if (floorSel3Sp==" ")//10
//    			{
//    				floorSel3Sp=0;
//    			}
    			//修改参数更新数据库
    			var param ={
    					densitySel:densitySel,
    					radiusSel:radiusSel,
    					densitySel1:densitySel1,
    					radiusSel1:radiusSel1,
    					densitySel2:densitySel2,
    					radiusSel2:radiusSel2,
    					densitySel3:densitySel3,
    					radiusSel3:radiusSel3,
    					densitySel4:densitySel4,
    					radiusSel4:radiusSel4,
    					densitySel5:densitySel5,
    					radiusSel5:radiusSel5,
    					densitySel6:densitySel6,
    					radiusSel6:radiusSel6,
    					densitySel7:densitySel7,
    					radiusSel7:radiusSel7,
    					floorNo : floorSel,
    					floorNo2 :floorSel2,
    					floorNo3 : floorSel3,
    					floorNo4 : floorSel4,
    					floorNo5 : floorSel5,
    					floorNo6 : floorSel6,
    					floorNo7 : floorSel7,
    					floorNo8 : floorSel8,
    					periodSel : periodSel,
    					coefficient : coefficient,
    					startTime : $("#select_time_begin_tab1").val()
    					//startTime :"2016-00-00 00:00:00"
                	};
    			$.ajax({
		              "dataType": 'json', 
		              "type": "POST", 
		              "url": "/sva/content/api/saveData", 
		              "data": param, 
		              "success": function(data){
		            	  if(data.error){
		            		  
		            		 // $("#alertBox").show();
		            	  }else{
//		            		  $("#editBox").hide();
		            		  alert("success!");
//		            		  clearinfo();
//		            		  refreshTable();
		            	  }
		              }
		        });
    		});

    		
		}
	};

}();
$("#add").click(function(e){
	
	$(".demoform").Validform().resetForm();
	$(".sameInfo").removeClass("Validform_wrong");
	$(".sameInfo").text("");
	clearinfo();
	$("#editBox").show();	
});
function hideAdd()
{
	$(".demoform").Validform().resetForm();
	clearinfo();
	$("#editBox").show();
}
function clearinfo()
{
	 $("#densitySel").val("");
	 $("#densitySel1").val("");
	 $("#densitySel2").val("");
	 $("#densitySel3").val("");
	 $("#densitySel4").val("");
	 $("#densitySel5").val("");
	 $("#densitySel6").val("");
	 $("#densitySel7").val("");
	 $("#radiusSel").val("");
	 $("#radiusSel1").val("");
	 $("#radiusSel2").val("");
	 $("#radiusSel3").val("");
	 $("#radiusSel4").val("");
	 $("#radiusSel5").val("");
	 $("#radiusSel6").val("");
	 $("#radiusSel7").val("");
	$("#placeSel").val("");
	$("#placeSel2").val("");
	$("#placeSel3").val("");
	$("#placeSel4").val("");
	$("#placeSel5").val("");
	$("#placeSel6").val("");
	$("#placeSel7").val("");
	$("#placeSel8").val("");
	$("#zSel").val("");	
	$("#zSel2").val("");	
	$("#zSel3").val("");	
	$("#zSel4").val("");	
	$("#zSel5").val("");	
	$("#zSel6").val("");	
	$("#zSel7").val("");	
	$("#zSel8").val("");	
	$("#periodSel").val("");	
	$("#coefficient").val("");	
	//$("#select_time_begin_tab1").val("");	
	$("#idid").val("");	

}
function paramValue(){
	$.get("/sva/content/api/getData?t="+Math.random(),function(data){
		if(!data.error){
			var a = data.data;
			$("#placeSel").val(Math.floor(a[0].floorNo/10000));
    		var placeId = $("#placeSel").val();
			$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeId}, function(data){
				if(!data.error){
					contentshow.updateFloorList1("zSel",data.data);
					$("#zSel").val(a[0].floorNo);	
					}
			});
			$("#placeSel2").val(Math.floor(a[0].floorNo2/10000));
			var placeId2 = $("#placeSel2").val();
			$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeId2}, function(data){
				if(!data.error){
					contentshow.updateFloorList1("zSel2",data.data);
					$("#zSel2").val(a[0].floorNo2);	
				}
			});
			$("#placeSel3").val(Math.floor(a[0].floorNo3/10000));
			var placeId3 = $("#placeSel3").val();
			$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeId3}, function(data){
				if(!data.error){
					contentshow.updateFloorList1("zSel3",data.data);
					$("#zSel3").val(a[0].floorNo3);	
				}
			});
			$("#placeSel4").val(Math.floor(a[0].floorNo4/10000));
			var placeSel4 = $("#placeSel4").val();
			$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeSel4}, function(data){
				if(!data.error){
					contentshow.updateFloorList1("zSel4",data.data);
					$("#zSel4").val(a[0].floorNo4);		
				}
			});
			$("#placeSel5").val(Math.floor(a[0].floorNo5/10000));
			var placeSel5 = $("#placeSel5").val();
			$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeSel5}, function(data){
				if(!data.error){
					contentshow.updateFloorList1("zSel5",data.data);
					$("#zSel5").val(a[0].floorNo5);		
				}
			});	
			$("#placeSel6").val(Math.floor(a[0].floorNo6/10000));
			var placeSel6 = $("#placeSel6").val();
			$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeSel6}, function(data){
				if(!data.error){
					contentshow.updateFloorList1("zSel6",data.data);
					$("#zSel6").val(a[0].floorNo6);		
				}
			});	
			$("#placeSel7").val(Math.floor(a[0].floorNo7/10000));
			var placeSel7 = $("#placeSel7").val();
			$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeSel7}, function(data){
				if(!data.error){
					contentshow.updateFloorList1("zSel7",data.data);
					$("#zSel7").val(a[0].floorNo7);		
				}
			});	
			$("#placeSel8").val(Math.floor(a[0].floorNo8/10000));
			var placeSel8 = $("#placeSel8").val();
			$.post("/sva/heatmap/api/getFloorsByMarket",{placeId:placeSel8}, function(data){
				if(!data.error){
					contentshow.updateFloorList1("zSel8",data.data);
					$("#zSel8").val(a[0].floorNo8);		
				}
			});	
			$("#radiusSel").val(a[0].radiusSel);
			$("#radiusSel1").val(a[0].radiusSel1);
			$("#radiusSel2").val(a[0].radiusSel2);
			$("#radiusSel3").val(a[0].radiusSel3);
			$("#radiusSel4").val(a[0].radiusSel4);
			$("#radiusSel5").val(a[0].radiusSel5);
			$("#radiusSel6").val(a[0].radiusSel6);
			$("#radiusSel7").val(a[0].radiusSel7);
			$("#densitySel").val(a[0].densitySel);
			$("#densitySel1").val(a[0].densitySel1);
			$("#densitySel2").val(a[0].densitySel2);
			$("#densitySel3").val(a[0].densitySel3);
			$("#densitySel4").val(a[0].densitySel4);
			$("#densitySel5").val(a[0].densitySel5);
			$("#densitySel6").val(a[0].densitySel6);
			$("#densitySel7").val(a[0].densitySel7);

			$("#periodSel").val(a[0].periodSel);
			$("#coefficient").val(a[0].coefficient);
			$("#select_time_begin_tab1").val(a[0].startTimeEmp);
			
		}
	});
}

function closeModel()
{
	$("#myModal").modal('hide');
	//$("#movie").empty();
}