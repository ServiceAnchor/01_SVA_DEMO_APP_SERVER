<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" 
	import="org.apache.taglibs.standard.tag.common.core.UrlSupport"%>

<%
	String propertyName = request.getParameter("propertyName");
	String propertyValue = request.getParameter("propertyValue");
	String propertyNameTree = propertyName+"_tree";
	String propertyNameText = propertyName+"_text";	
	String treeDataSourceName = request.getParameter("treeDataSourceName");
	String treeDataSource = (String)request.getAttribute(treeDataSourceName);
	boolean dropdown = (request.getParameter("dropdown")!=null && !request.getParameter("dropdown").isEmpty())? Boolean.parseBoolean(request.getParameter("dropdown")):false;
	boolean checkbox = (request.getParameter("checkbox")!=null && !request.getParameter("checkbox").isEmpty())? Boolean.parseBoolean(request.getParameter("checkbox")):false;
%>

<div>
<%
	if(propertyName==null || propertyName.isEmpty()){%>
		未获取到树的属性名称！
	<%}
	else if(treeDataSourceName==null ||treeDataSourceName.isEmpty()){%>
		未获取到树的数据源名称！
	<%}
	else{
		if(dropdown){%>
			<input id="<%= propertyName + "_text" %>" type="text" readonly="readonly" />
		<%}
	} %>
	<input type="hidden" id="<%= propertyName %>" name="<%= propertyName %>" value="<%= propertyValue %>" />
	<div id="<%= propertyNameTree %>" <% if (dropdown){%> style="display: none; z-index: 99999;overflow:auto;"<%} %>>
	</div>
	
	<script type="text/javascript">
	    $(function() {
	        $("#<%= propertyNameTree %>").treeLite({
	        <% if (checkbox){%>	        
	            checkbox:true,
	            checkedChanged: function(chks){$("#<%= propertyName %>").val($("#<%= propertyNameTree %>").treeLite("selectedIds"));<% if(dropdown){ %>$("#<%= propertyNameText %>").val($("#<%= propertyNameTree %>").treeLite("selectedTexts"));<%} %>},
            <%} else {%>
	            /* dynamic: false, */
	            selectedChanged: function(id){if(id == null) id="";$("#<%= propertyName %>").val(id);<% if(dropdown){ %>$("#<%= propertyNameText %>").val($("#<%= propertyNameTree %>").treeLite("selectedText"));<%} %>},
            <%} %>
            items: <%= treeDataSource %>
	        });
	        $("#<%= propertyNameTree %>").treeLite('showSelected');
	        <% if(dropdown){ %>
		        <% if(checkbox){%>
		        	$("#<%= propertyNameText %>").val($("#<%= propertyNameTree %>").treeLite("selectedTexts", $("#<%= propertyNameTree %>").data("treeLite").items));
		        <%} else {%>
		        	$("#<%= propertyNameText %>").val($("#<%= propertyNameTree %>").treeLite("selectedText", $("#<%= propertyNameTree %>").data("treeLite").items));
		        <%} %>
		        setTimeout(function(){
		            $("#<%= propertyNameText %>").focus(function() {
		                $("#<%= propertyNameTree %>").css("position", "absolute").css("left", $("#<%= propertyNameText %>").offset().left)
		                .css("maxHeight", $(document).height()-$("#<%= propertyNameText %>").offset().top - $("#<%= propertyNameText %>").outerHeight())
		                .css("top", $("#<%= propertyNameText %>").offset().top + $("#<%= propertyNameText %>").outerHeight()).show();
		            });
		            $("#<%= propertyNameText %>").click(function(event) {
		                event.stopPropagation();
		            });
		            $("body").click(function(event) {
		                $("#<%= propertyNameTree %>").hide();
		            });
		        }, 0);
	        <%}%>
	    });    
	</script>
</div>
