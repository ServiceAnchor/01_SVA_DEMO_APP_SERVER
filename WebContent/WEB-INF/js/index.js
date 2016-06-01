var Index = function () {
	
	var dataFilter = function(data){
		var list = [];
		for(var i in data){
			var point ={
					x:data[i].x,
					y:data[i].y,
					value:1
			};
			list.push(point);
		}
		
		return list;
	};
	
	/**
	* 将对应信息填充到对应的select
	* @ param renderId [string] 标签id
	* @ param data [array] 列表数据
	*/
	var updateList = function(renderId,data,callback){
	    var sortData = data.sort();
	    var len = sortData.length;
	    var options = '';
	    for(var i=0;i<len;i++){
	        options += '<option class="addoption" value="'+sortData[i]+'">' + sortData[i] +'</option>';
	    }
	    removeOption(renderId);
	    $('#' + renderId).append(options);
	    if(callback){
	        callback();
	    }
	    return;
	}


    return {

        //main function
        initHeatmap: function () {
        	$.get("app/heatmap/getData",function(data){
    			if(!data.error){
    				var heatmap = h337.create({
    	                container: document.getElementById("heatmap"),
    	                maxOpacity: .6,
    	                radius: 20,
    	                blur: .90,
    	                backgroundColor: 'rgba(0, 0, 58, 0.1)'
    	            });
    				//var points = {max:1,data:dataFilter(data)};
    				var points = dataFilter(data.data);
    				/***** begin********/
    	            var heatmapContainer = document.getElementById('mapContainer');
    	            heatmapContainer.onclick = function (e) {
    	                var x = e.layerX;
    	                var y = e.layerY;
    	                heatmap.addData({ x: x, y: y, value: 1 });
    	            };
    	            /******end  此为点击区域加热点 根据情况删除或保留*******/
    				heatmap.addData(points);
    			}
    		});
        },
        
        initMapTable:function(){
        	$.get("app/mapMng/getTableData",function(data){
        		if(!data.error){
        			var list = data.data;
        			for(var i in list){
        				var rData = list[i];
	        			//添加一行
	        			var newTr = document.getElementById("table").insertRow();        
	        			//添加六列        
	        			var newTd0 = newTr.insertCell(),
	        				  newTd1 = newTr.insertCell(),
	        				  newTd2 = newTr.insertCell(),
	        				  newTd3 = newTr.insertCell(),
	        				  newTd4 = newTr.insertCell(),
	        				  newTd5 = newTr.insertCell(), 
	        				  newTd6 = newTr.insertCell(),
	        				  newTd7 = newTr.insertCell();
	        			//设置列内容和属性        
	        			newTd0.innerText = rData.place;
	        			newTd0.width = "15%";
	        			newTd1.innerText = rData.floor;
	        			newTd1.width = "10%";
	        			newTd2.innerText = rData.floorNo;
	        			newTd2.width = "10%";
	        			newTd3.innerText = rData.y;
	        			newTd3.width = "10%";
	        			newTd4.innerText = rData.y;
	        			newTd4.width = "10%";
	        			newTd5.innerText = rData.scale;
	        			newTd5.width = "10%";
	        			newTd6.innerText = rData.path;
	        			newTd7.innerHTML = '<input type=button data-type="del" data-floor="'+rData.floor+'" data-place="'+rData.place+'" value="删除" class="btn btn-mini" id="'+rData.floor+rData.place+'" />';
	        			newTd7.width = "15%";
        			}
        		}        		
        	});
        },
        
        deleteMap:function(floor, place){
        	$.get("app/mapMng/deleteByFloor",{floor:floor, place:place},function(data){
        		if(!data.error){
	        		var obj = document.getElementById(floor+place);
	        		obj=obj.parentNode;
	        		obj=obj.parentNode;
	        		obj.parentNode.removeChild(obj);
        		}
        	});
        }
    };

}();