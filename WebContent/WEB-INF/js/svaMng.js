var SvaMng = function () {
	
	var refreshTable = function(){
//		$("#table").empty();
		$.get("/sva/svalist/api/getTableData?t="+Math.random(),function(data){
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
//    						"bVisible": false,
    						"mData": "ip" 
    					},
    					{ 
    						"aTargets": [1],
    						"mData": "name",
    						"mRender": function ( data, type, full ) {
								if(data.length > 15){
									var html = data.substring(0,10)+"...";
									html = '<span title="'+HtmlDecode3(data)+'">'+HtmlDecode3(html)+'</span>';
										return html;
								}
								return '<span title="'+HtmlDecode3(data)+'">'+HtmlDecode3(data)+'</span>';
							}
    					},
    					{ 
    						"aTargets": [2],
    						"mData": "position",
    						"mRender": function ( data, type, full ) {
    							if (data!='') {
    								if (data[0].length>10) {
    									var html = data[0].substring(0,10)+"...";
    									html = '<span title="'+data+'">'+HtmlDecode3(html)+'</span>';
    										return html;
									}
    								return '<span title="'+HtmlDecode3(data[0])+'">'+HtmlDecode3(data[0])+'</span>';
								}
								return '';
							}
    					},
    					{ 
    						"aTargets": [3],
    						"mData": "tokenProt"
    					},
    					{ 
    						"aTargets": [4],
    						"mData": "brokerProt"
    					},
    					{ 
    						"aTargets": [5],
    						"mData": "username",
    						"mRender": function ( data, type, full ) {
								if (data.length>10) {
									var html = data.substring(0,10)+"...";
									html = '<span title="'+data+'">'+HtmlDecode3(html)+'</span>';
										return html;
								}
								return '<span title="'+HtmlDecode3(data)+'">'+HtmlDecode3(data)+'</span>';
							}
    					},
    					{ 
    						"aTargets": [6],
    						"bVisible": false,
    						"mData": "password"
    					},
    					{
    	                    "aTargets": [7],
    	                    "bSortable": false,
    	                    "bFilter": false,
    	                    "mData": function(source, type, val) {
    	                        return "";
    	                    },
    	                    "mRender": function ( data, type, full ) {
    	                    	var html;
    	            		if(full.type == "1"){
    	            			html = i18n_type;
    	        			}
    	            		if(full.type == "0"){
    	            			html = i18n_type1;
    	            		}
    	            		if(full.type == "2"){
    	            			html = i18n_type2;
    	            		}
    	                        return html;
    	                      }
    	                },
    					{
    	                    "aTargets": [8],
    	                    "bSortable": false,
    	                    "bFilter": false,
    	                    "mData": function(source, type, val) {
    	                        return "";
    	                    },
    	                    "mRender": function ( data, type, full ) {
    	                    	var html;
    	            		if(full.status == "1"){
    	            			html = i18n_open;
    	        			}else{
    	        				html = i18n_close ;
    	        			}
    	                        return html;
    	                      }
    	                },
    					{
    	                    "aTargets": [9],
    	                    "bSortable": false,
    	                    "bFilter": false,
    	                    "mData": function(source, type, val) {
    	                        return "";
    	                    },
    	                    "mRender": function ( data, type, full ) {
    	                    	var html;
	    	            		if(full.status == "1"){
	    	            				html = '<input type=button data-type="stop" style="width:62px;height:32px;font-size: 13px;font-family:inherit;" data-id="'+full.id+'" value="'+i18n_disable+'" class="btn btn-mini" />' +
	    	        				'&nbsp;<input type=button data-type="edt" style="width: 62px;height:32px;font-size: 13px;font-family:inherit;" data-id="'+full.id+'" value="'+i18n_edit+'" class="btn btn-mini" />';
	    	        			}else{
	    	        				html = '<input type=button data-type="recover" style="width: 62px;height:32px;font-size: 13px;font-family:inherit;" data-id="'+full.id+'" value="'+i18n_enable+'" class="btn btn-mini" />'+
	    	        				'&nbsp;<input type=button data-type="edt" style="width: 62px;height:32px;font-size: 13px;font-family:inherit;" data-id="'+full.id+'" value="'+i18n_edit+'" class="btn btn-mini" />';
	    	        			}
	    	            		html+='<input type=button data-type="del" style="width: 62px;height:32px;font-size: 13px;font-family:inherit;" data-id="'+full.id+'" value="'+i18n_delete+'" class="btn btn-mini" />';
    	                        return html;
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
	};
	
	var updatePosition = function(renderId,data,selectTxt,callback){
	    var sortData = data.sort(function(a,b){return a.name - b.name;});
	    var len = sortData.length;
	    var options = '';
	    for(var i=0;i<len;i++){
	    	var info = sortData[i];
	        if(selectTxt && _.indexOf(selectTxt, sortData[i].name)>=0){
	    		options += '<option class="addoption" selected=true value="'+info.id+'">' + HtmlDecode3(info.name) +'</option>';
	    	}else{
	    		options += '<option class="addoption" value="'+info.id+'">' + HtmlDecode3(info.name) +'</option>';
	    	}
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

    return {
    	
    	bindClickEvent: function(){

            $("input[data-type='stop']").live("click",function(e){
            	if (confirm(i18n_status)) {
            		var id = $(this).data("id");
            		$.post("/sva/svalist/api/disableData",{id:id},function(data){
            			if(!data.error){
            				refreshTable();
            			}
            		});
				}
            });
            
            $("input[data-type='recover']").live("click",function(e){
            	if (confirm(i18n_status))
            	{
            		var id = $(this).data("id");
            		$.post("/sva/svalist/api/enableData",{id:id},function(data){
            			if(!data.error){
            				refreshTable();
            			}
            		});
				}
           });
//            $("#subscriptionId").live("click",function(e){
//            	var va =  $("#subscriptionId").val();
//            	if (va==0) {
//					$("#typenameId").val("");
//					$("#typepasswordId").val("");
//					$("#typename1").show();
//					$("#typename2").show();
//				}else
//					{
//					$("#typename1").hide();
//					$("#typename2").hide();
//					}
//
//           });
            
            $("input[data-type='edt']").live("click",function(e){
            	clearinfo();
            	$(".demoform").Validform().resetForm();
            	var id = $(this).data("id");
	           	var rowObj = $(this)[0].parentNode.parentNode;
	           	var row = $(this).parent().parent();
	        	var data1 = oTable.fnGetData(row[0]);
	           	var ip = rowObj.childNodes[0].innerHTML,
	           		   name =  $(rowObj.childNodes[1].childNodes[0]).attr("title"),
	           		   position =$(rowObj.childNodes[2].childNodes[0]).attr("title"),
	           		   token = rowObj.childNodes[3].innerHTML,
	           		   broker = rowObj.childNodes[4].innerHTML,
	           	 	   username = $(rowObj.childNodes[5].childNodes[0]).attr("title"),
	           	 	 //  password = rowObj.childNodes[].innerHTML,
	           	 	   status = rowObj.childNodes[7].innerHTML;
	           	       type = rowObj.childNodes[6].innerHTML;
				$("input[name='ip']").val(ip);
				$("#SVAPosition").val(HtmlDecode2(position));
	           	$("input[name='name']").val(HtmlDecode2(name));
	           	$("input[name='username']").val(HtmlDecode2(username));
	           	$("input[name='password']").val(data1.password);
	           	$("#tokenId").val(token);
	           	$("#brokerId").val(broker);
	           	$("input[name='id']").val(id);
	           	if (type==i18n_type) {
		           	$("#subscriptionId").val(1);
					$("#typename1").hide();
					$("#typename2").hide();
				}
	           	if (type==i18n_type1) {
		           	$("#subscriptionId").val(0);
					$("#typename1").show();
					$("#typename2").show();
					$("#typenameId").val(data1.typename);
					$("#typepasswordId").val(data1.typepassword);
	           	}
	           	if(status==i18n_open)
	            {
	           		$("#enableSel").val(1);           	 
	           	 }
	           	if (status==i18n_close) {
					
	           		$("#enableSel").val(0);           	 
				}
	           	$.get("/sva/store/api/getData?t="+Math.random(),function(data){
	        		if(!data.error){
	        			$.post("/sva/store/api/getStoreBySvaId",{svaId:id},function(data2){
	        				if(!data2.error){
			        			updatePosition("SVAPosition",data.data,data2.data);
	        				}	        				
	        			});
	        		}
	        	});
	        	$("#editBox").show();
            });
            
            $("input[data-type='del']").live("click",function(e){
            	if (confirm(i18n_deleteInfo))
            	{
            		var id = $(this).data("id");
            		$.post("/sva/svalist/api/deleteData",{id:id},function(data){
            			if(!data.error){
            				refreshTable();
            			}
            		});
				}
           });
            
            $("#confirm").on("click",function(e){
//            	$("#jiazai").show();
            	$(".sameInfo").html("");
            	$(".sameInfo").removeClass("Validform_wrong");
            	var param = {id:$("#idid").val(),svaName:$("#SVAId").val(),ip:$("#IpId").val(),userName:$("#usernameId").val(),password:$("#passwordId").val(),token:$("#tokenId").val()};
            	var check = validForm.check(false);
            	
            	if(check){
//            	$("#jiazai").show();
              	  $.post("/sva/svalist/api/checkName",param,function(data){
              		  var b = data.data;
              		  if (data.ip) 
              		  {
  			              $(".sameInfo").addClass("Validform_wrong");
  			              $(".sameInfo").text(i18n_wuxiao);
  			              return false;
					   }
             		  if (data.error) 
              		  {
  			              $(".sameInfo").addClass("Validform_wrong");
  			              $(".sameInfo").text(i18n_user);
  			              return false;
					   }
                  		if (b)
                  		{
                  			$(".demoform").submit();
      					}else
      						{
      			              $(".sameInfo").addClass("Validform_wrong");
      			              $(".sameInfo").text(i18n_svaSame);
      						}
              		});

              		
              	}
            });
    	},
        
    	initTable:function(){
    		refreshTable();
        },
        
        initPosition:function(){
        	$.get("/sva/store/api/getData?t="+Math.random(),function(data){
        		if(!data.error){
        			updatePosition("SVAPosition",data.data);
        		}
        	});
        }
    };

}();
function checkMsg()
{
	var enableSel =$("#enableSel").val();
	var ip=$("input[name='ip']").val();
	var name=$("input[name='name']").val();
	var username=$("input[name='username']").val();
	var password=$("input[name='password']").val();
	var place=$("select[name='position']").val();
	var id = $("input[name='id']").val(); 
	var er ;
	var param ={
			id : id,
			name :name 
    	};
	$.ajax({
        "dataType": 'json', 
        "type": "POST", 
        "url": "/sva/svalist/api/checkName", 
        "data": param, 
        "success": function(data){
      	  if(data.error){
              $("#editBox").show();
              $(".sameInfo").addClass("Validform_wrong");
              $(".sameInfo").text(i18n_svaSame);
      	  }else{
      		  $("#editBox").hide();
      		  clearinfo();
      	  }
      	er = data.error;
        }
  });
	if (er=="same") 
	{
	 return false;
	}
}

function estimateOnkeyup(str)
{
if (isNaN(str.value)&&!isNaN(str.value.substring(0,str.value.length-1))) {
		str.value = str.value.substring(0,str.value.length-1);
		str.focus();
		return false;
	}
if(isNaN(str.value)&&isNaN(str.value.substring(0,str.value.length-1)))
	{
	str.value = "";
	str.focus();
	return false;
	}
	if (str.value.split(".").length<2) {
		var a = parseInt(str.value.substring(str.value.length-1,str.value.length));
		if (a<0)
		{
			str.value = str.value.substring(0,str.value.length-1);
			str.focus();
			return false;
		}
	}else
		{
		var c = str.value.split(".")[1];
		if (c.length>2) 
		{
			str.value = str.value.substring(0,str.value.length-1);
			str.focus();
			return false;	
		}else
			{
			if (isNaN(str.value))
			{
				str.value = str.value.substring(0,str.value.length-1);
				str.focus();
				return false;
			}else
				{
				if(str.value.split(".")[1]!=""&&str.value.split(".")[1]!="0"&&str.value.split(".")[1]!="00")
					{
					str.value = parseFloat(str.value);
					str.focus();
					return false;
					}
				return false;
				}
			
			
			}
		}
	}
function addMap()
{
	$(".demoform").Validform().resetForm();
	clearinfo();
	$("#editBox").show();	
}
function hideAdd()
{	
	$(".demoform").Validform().resetForm();
	clearinfo();
	$("#editBox").hide();
}
function clearinfo()
{
	$("input[name='ip']").val("");
   	$("input[name='name']").val("");
   	$("input[name='username']").val("");
   	$("input[name='password']").val("");
   	$("input[name='id']").val("");
   	$("#enableSel").val("");
   	$("select[name='position']").val("");
   	$(".sameInfo").removeClass("Validform_wrong");
   	$(".sameInfo").text("");
   	$("#tokenId").val("9001");
   	$("#brokerId").val("4703");

}
