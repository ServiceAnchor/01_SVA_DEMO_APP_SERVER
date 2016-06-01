/**
*
*tableManager 
*auth:liukemng
*data:2014-04-04
*remark:使用时需要添加jquery.uniform
*
**/
(function($){ 
	$.fn.tableManaged = function(options){ 
		var defaults = { 
			allCheckboxClass:"group-checkable",
			preCheckboxClass:"checkboxes"
		} 
		var options = $.extend(defaults, options); 
		this.each(function(){ 
			var managedTable=$(this); 

			$(managedTable).find("tbody tr ."+options.preCheckboxClass).change(function(){
                 $(this).parents('tr').toggleClass("active");
            });

			$(managedTable).find("."+options.allCheckboxClass).change(function () {
                var set = $(managedTable).find("."+options.preCheckboxClass);
                var checked = $(this).is(":checked");
                $(set).each(function () {
					$(this).attr("checked", checked);
                    $(this).parents('tr').toggleClass("active");
                });
                $.uniform.update(set);
            });
		}); 
	}; 
})(jQuery); 