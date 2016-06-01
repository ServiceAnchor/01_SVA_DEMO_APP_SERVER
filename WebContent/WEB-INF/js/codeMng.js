var CodeMng = function () {

    return {
        
        initTable:function(){
        	$.get("/sva/code/api/getTableData?t="+Math.random(),function(data){
        		if(!data.error){
        			var list = data.data;
        			for(var i in list){
        				var rData = list[i];
	        				        			
	        			var tableObj = document.getElementById("table");  
	        			var rowObj = document.createElement("tr");  
	        			var cellObj = document.createElement("td");  
	        			cellObj.innerHTML = rData.name;
	        			cellObj.width = "33.333333%";
	        			rowObj.appendChild(cellObj);  
	        			cellObj = document.createElement("td");  
	        			cellObj.innerHTML = rData.password;  
	        			cellObj.width = "33.333333%";
	        			rowObj.appendChild(cellObj); 
	        			cellObj = document.createElement("td");  
	        			cellObj.innerHTML = '<input type=button data-type="del" data-name="'+rData.name+'" data-password="'+rData.password+'" value="'+i18n_delete+'" class="btn btn-mini" id="'+rData.name+rData.password+'" />';
	        			cellObj.width = "33.333333%";
	        			rowObj.appendChild(cellObj);
	        			tableObj.appendChild(rowObj);
        			}
        		}        		
        	});
        	
            $("#confirm").on("click",function(e){
//          		$("#sameInfo").removeClass("Validform_wrong");
//          		$("#sameInfo").text("");
            	var param = {name:$("#nameId").val(),password:$("#passwordId").val()};
            	if($("#nameId").val() == "" ){
            		$("#nameId").blur();
            		return false;
            	}
            	if($("#passwordId").val() == "" ){
            		$("#passwordId").blur();
            		return false;
            	}
            //	var check = validForm.check(false);
            	
            	//if (check) {
            		  $.post("/sva/code/api/checkName",param,function(data){
  		            	  if(data.data){
  		            		$(".demoform").submit();
  		            	  }else{
  		            		$("#sameInfo").addClass("Validform_wrong");
  		            		$("#sameInfo").text(i18n_same);
  		            	  }
  		        });
				//}
            });
        },
        
        deleteRow:function(name, password){
        	if (confirm(i18n_codedelete)) {
        	$.post("/sva/code/api/deleteData",{name:name, password:password},function(data){
        		if(!data.error){
	        		var obj = document.getElementById(name+""+password);
	        		obj=obj.parentNode;
	        		obj=obj.parentNode;
	        		obj.parentNode.removeChild(obj);
        		}
        	});
        	}
        }
    };

}();

function checkCode()
{
var name=$("input[name='name']").val();	
var password=$("input[name='password']").val();	
if (name.trim()==""||password.trim()=="") 
{
	alert(i18n_info);
	return false;
}
if (name.length<4||name.length>20||password.length<4||password.length>20)
{
	alert(i18n_codelength);
	return false;
}
//var reg = /^.*['"\/;\\<>]+.*$/;
//if (reg.test(name)||reg.test(password))
//{
//	alert("sda");
//	return false;
//}
}