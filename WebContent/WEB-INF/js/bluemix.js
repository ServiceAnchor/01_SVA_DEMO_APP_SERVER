var Bluemix = function () {
	
	var refreshTable = function(){
		$.get("/sva/bluemix/api/getData?t="+Math.random(),function(data){
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
						"mData": "ip"
					},
					{ 
							"aTargets": [2],
							"mData": "svaUser",
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
						"aTargets": [3],
						"bVisible": false,
						"mData": "svaPassword"
						},
						{ 
							"aTargets": [4],
						"mData": "tokenProt"
						},
						{ 
							"aTargets": [5],
						"mData": "brokerProt"
						},
						{ 
							"aTargets": [6],
						"mData": "url",
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
							"aTargets": [7],
							"mData": "site",
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
							"aTargets": [8],
							"mData": "ibmUser",
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
							"aTargets": [9],
							"bVisible": false,
							"mData": "ibmPassword"
						},
						{ 
							"aTargets": [10],
							"mData": "status",
							"mRender": function ( data, type, full ) {
								if(data==1){
									return i18n_open;
								}else{
									return i18n_close;
								}
							}
						},
						{ 
							"aTargets": [11],
							"mData": "updateTime",
							"mRender": function ( data, type, full ) {
								var date = new Date();
								date.setTime(data);
								return dateFormat(date,"yyyy/MM/dd HH:mm:ss");
							}
						},
						{ 
							"aTargets": [12],
							"mData": "createTime",
							"mRender": function ( data, type, full ) {
								var date = new Date();
								date.setTime(data);
								return dateFormat(date,"yyyy/MM/dd HH:mm:ss");
							}
						},
						{
							"aTargets": [13],
							"bSortable": false,
							"bFilter": false,
							"mData": function(source, type, val) {
								return "";
							},
							"mRender": function ( data, type, full ) {
								var html = "" +
									'<input type="button" data-type="edit" data-id="'+full.id+'" value="'+i18n_edit+'">' +
									'<input type="button" data-type="del" data-id="'+full.id+'" value="'+i18n_delete+'">';
								if(full.status == 1){
									html += '<input type="button" data-type="disable" data-id="'+full.id+'" value="'+i18n_disable+'">';
								}else{
									html += '<input type="button" data-type="enable" data-id="'+full.id+'" value="'+i18n_enable+'">';
								}
									return html;
							}
						}
					]
				});
			}
		});
	};
	
	var clearInput = function(){
		$("#inputId").val("");
		$("#inputIP").val("");
		$("#inputSvaUser").val("");
		$("#inputSvaPassword").val("");
		$("#inputUrl").val("");
		$("#inputSite").val("");
		$("#inputIbmUser").val("");
		$("#inputIbmPassword").val("");
		$("#tokenId").val("");
		$("#brokerId").val("");
	};

	return {	
		bindClickEvent: function(){
			$("input[data-type='edit']").live("click",function(e){
            	$(".demoform").Validform().resetForm();
				var row = $(this).parent().parent();
				var data = oTable.fnGetData(row[0]);
				$("#inputId").val(data.id);
    			$("#inputIP").val(data.ip);
    			$("#inputSvaUser").val(data.svaUser);
    			$("#inputSvaPassword").val(data.svaPassword);
    			$("#inputUrl").val(data.url);
    			$("#inputSite").val(data.site);
    			$("#inputIbmUser").val(data.ibmUser);
    			$("#inputIbmPassword").val(data.ibmPassword);
    			$("#tokenId").val(data.tokenProt);
    			$("#brokerId").val(data.brokerProt);
    			$("#editBox").show();
            });
            
            $("input[data-type='del']").live("click",function(e){
            	if(confirm(i18n_deleteInfo)){
	            	var id = $(this).data("id");	           	 
	        		$.post("/sva/bluemix/api/deleteData",{id:id},function(data){
	        			if(data.error){
//	        				$("#info").text(data.error);
//	            			$(".alert").removeClass("alert-info");
//	            			$(".alert").addClass("alert-error");
//	            			$("#alertBox").show();
		           		}else{
//	            			$("#info").text("Operation Succeeded");
//	            			$(".alert").removeClass("alert-error");
//	            			$(".alert").addClass("alert-info");
//	            			$("#alertBox").show();
	            			refreshTable();
		           		}
	        		});
            	}
            });
            
            $("input[data-type='disable']").live("click",function(e){
            	var id = $(this).data("id");
        		$.post("/sva/bluemix/api/changeStatus",{id:id,status:0},function(data){
        			if(data.error){
//        				$("#info").text(data.error);
            			$(".alert").removeClass("alert-info");
            			$(".alert").addClass("alert-error");
//						$("#alertBox").show();
        			}else{
//            			$("#info").text("Operation Succeeded");
            			$(".alert").removeClass("alert-error");
            			$(".alert").addClass("alert-info");
//            			$("#alertBox").show();
            			refreshTable();
	           		}
        		});
            });
            
            $("input[data-type='enable']").live("click",function(e){
            	var id = $(this).data("id");
	           	 
        		$.post("/sva/bluemix/api/changeStatus",{id:id,status:1},function(data){
        			if(data.error){
        				$("#info").text(data.error);
            			$(".alert").removeClass("alert-info");
            			$(".alert").addClass("alert-error");
//            			$("#alertBox").show();
	           		}else{
//            			$("#info").text("Operation Succeeded");
            			$(".alert").removeClass("alert-error");
            			$(".alert").addClass("alert-info");
//            			$("#alertBox").show();
            			refreshTable();
	           		}
        		});
            });
            
            $("#confirm").on("click",function(e){
            	$("#inputIP").blur();
            	$("#inputSvaUser").blur();
            	$("#inputSvaPassword").blur();
            	$("#inputUrl").blur();
            	$("#inputSite").blur();
            	$("#inputIbmUser").blur();
            	$("#inputIbmPassword").blur();
    			$("#tokenId").blur();
    			$("#brokerId").blur();
    			var che = validForm.check(false);
    			if (!che) {
					return false;
				}
            	var ip =  $("#inputIP").val();
            	var svaUser = $("#inputSvaUser").val();
            	var svaPassword = $("#inputSvaPassword").val();
            	var url = $("#inputUrl").val();
            	var site = $("#inputSite").val();
            	var ibmUser = $("#inputIbmUser").val();
            	var ibmPassword = $("#inputIbmPassword").val();
            	if(ip ==""||svaUser == "" || svaPassword == ""|| url=="" || site ==""||ibmUser==""||ibmPassword==""){
            		return false;
            	}
            	var regIp =/^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/;
            	if(!ip.match(regIp)){
            		return false;
            	}
                for(var i = 0 ;i<ip.length;i++)
                {
                if (ip[i]>255) 
                {
                  return false;
                 }
              }
            	var param ={
        			id : $("#inputId").val(),
        			ip : $("#inputIP").val(),
        			svaUser : $("#inputSvaUser").val(),
        			svaPassword : $("#inputSvaPassword").val(),
        			url : $("#inputUrl").val(),
        			site : $("#inputSite").val(),
        			ibmUser : $("#inputIbmUser").val(),
        			ibmPassword : $("#inputIbmPassword").val(),
        			tokenProt : $("#tokenId").val(),
        			brokerProt : $("#brokerId").val()
            	};
            	
            	$.ajax({
		              "dataType": 'json', 
		              "contentType" : "application/json",
		              "type": "POST", 
		              "url": "/sva/bluemix/api/saveData", 
		              "data": JSON.stringify(param), 
		              "success": function(data){
		            	  if(data.error){
//		            		  $("#info").text(data.error);
//		            		  $(".alert").removeClass("alert-info");
//		            		  $(".alert").addClass("alert-error");
//		            		  $("#alertBox").show();
		            	  }else{
		            		  $("#editBox").hide();
//		            		  $("#info").text("Operation Succeeded");
//		            		  $(".alert").removeClass("alert-error");
//		            		  $(".alert").addClass("alert-info");
//		            		  $("#alertBox").show();
		            		  clearInput();
		            		  refreshTable();
		            	  }
		              }
		        });
            	
//            	$.post("/sva/bluemix/api/saveData",param,function(data){
//            		if(data.error){
//            			$("#info").text(data.error);
//            			$(".alert").removeClass("alert-info");
//            			$(".alert").addClass("alert-error");
//            			$("#alertBox").show();
//            		}else{
//                    	$("#editBox").hide();
//            			$("#info").text("Operation Succeeded");
//            			$(".alert").removeClass("alert-error");
//            			$(".alert").addClass("alert-info");
//            			$("#alertBox").show();
//                    	clearInput();
//            		}
//            	});
            	
            });
            
            $("#cancel").on("click",function(e){
            	$("#editBox").hide();
            	$(".demoform").Validform().resetForm();
            	clearInput();
            });
            
            $("#add").on("click",function(e){
               	$(".demoform").Validform().resetForm();
            	$("#editBox").show();
            	$("#inputId").val("");
            });
    	},
        
    	initTable:function(){
    		refreshTable();
        }
    };

}();
function checkMsg()
{
var enableSel =$("#enableSel").val();
var ip=$("input[name='ip']").val();
var name=$("select[name='name']").val();
var username=$("input[name='username']").val();
var password=$("input[name='password']").val();
if (enableSel==""||ip==""||name==""||username==""||password=="") {
	alert(i18n_info);
	return false;
}
}
function HtmlDecode2(str) {
	var str1 = str.replace(/&lt;/g,"<");
	var str2 = str1.replace(/&gt;/g,">");
	var str3 = str2.replace(/&amp;/g,"&");
	var str4 = str3.replace(/&quot;/g,"\"");
	var str5 = str4.replace(/&apos;/g,"\'");
    return str5;
     }