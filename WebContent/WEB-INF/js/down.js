var MsgMng = function () {
    return {
        initMsgTable:function(){
        	$.get("/sva/down/api/getData?t="+Math.random(),function(data){
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
//        						"bVisible": false,
        						"mData": "name" 
        					},
        					{ 
        						"aTargets": [1],
        						"mData": "creatTime"
        	                },
        					{
        	                    "aTargets": [2],
        	                    "bSortable": false,
        	                    "bFilter": false,
        	                    "mData": function(source, type, val) {
        	                        return "";
        	                    },
        	                    "mRender": function ( data, type, full ) {
        	                    	var html = "" +
        	                    		'<a href=/sva/version/WAR/'+full.name+'>'+i18n_down+'</a>';
        	                        return html;
        	                      }
        	                }
        				]
        			});
        		}        		
        	});
        }
    };

}();


