//return array's max
Array.prototype.max = function(){

    return Math.max.apply({}, this);

};

//return array's min

Array.prototype.min = function(){

    return Math.min.apply({}, this);
};


var options={

    cssClickClass:'point',//点击时生成点的类

    svgCtnCls:'svgContainer',//svg容器的类

    wapper:'#preview'//地图容器

};

var Ploy={
	paper:null,
	square:null,

    getData:function(){

        var arrayTop=[];

        var arrayLeft=[];

        var el=$('div.'+options.cssClickClass);

        $.each(el,function(i,n){

            var pointTop=$(n).position().top;

            var pointLeft=$(n).position().left;

            arrayTop.push(pointTop);

            arrayLeft.push(pointLeft);

        });

        var minTop=arrayTop.min();

        var maxTop=arrayTop.max();

        var minLeft=arrayLeft.min();

        var maxLeft=arrayLeft.max();

        var svgWidth=maxLeft-minLeft+el.width();

        var svgHeight=maxTop-minTop+el.height();

        return {

            w:svgWidth,

            h:svgHeight,

            minTop:minTop,

            minLeft:minLeft,

            elWidth:el.width(),

            elHeight:el.height(),
            
            length: el.length

        };

    },

    makeSvgContainer:function(){

        var s=this;

        var datas=s.getData();

        var div=$('#mapPloy').is('div');

        if(!div){

            var svgContainer=$('<div/>')

                         .attr('id','mapPloy')

                         .addClass(options.svgCtnCls)

                         .css({

                            width:datas.w,

                            height:datas.h,

                            top:datas.minTop+datas.elHeight/2,

                            left:datas.minLeft+datas.elWidth/2

            }).prependTo($(options.wapper));

        }else{

            $('#mapPloy').css({

                width:datas.w,

                height:datas.h,

                top:datas.minTop+datas.elHeight/2,

                left:datas.minLeft+datas.elWidth/2

            });

        }

    },

    //添加点在地图区域中
    addPoint:function(top,left){

        var wapper=$(options.wapper);

        var t=wapper.offset().top;

        var l=wapper.offset().left;

        var pt=top-t;

        var pl=left-l;

        var point=$('<div/>').addClass(options.cssClickClass)

                             .css({

                                top:pt,

                                left:pl,

                                poisiton:'absolute'

                             })

                             .appendTo(wapper);

    },
    
    //添加点在地图区域中
    addPoints:function(datas,obj){
    	var scale = parseFloat(obj.scale);
    	var imgScale = obj.imgScale;
    	var oX = parseFloat(obj.x);
    	var oY = parseFloat(obj.y);
    	var wapper=$(options.wapper);
    	var result = [];
    	
    	for(var i=0;i<datas.length;i++){
    		var data = datas[i];
    		var x = (data.x/10+oX)*scale/imgScale;
    		var y = (data.y/10+oY)*scale/imgScale;
    		switch (obj.coordinate){
    		case "ul":
    			break;
    		case "ll":
    			y = obj.height - y;
    			break;
    		case "ur":
    			x = obj.width - x;
    			break;
    		case "lr":
    			x = obj.width - x;
    			y = obj.height - y;
    			break;
    		}
    		
    		var id = "point_"+i;
    		if(data.neCode){
    			id = data.neCode;
    		}
    		
    		$('<div/>').addClass(options.cssClickClass).attr({"id":id}).css({
               top:y,
               left:x,
               poisiton:'absolute'
            }).appendTo(wapper);
    		result.push({x:x,y:y});
    	}
    	
    	return result;
    },
    
    //清空画布
    clearPaper: function(){
    	this.square = null;
    	if(this.paper){
    		this.paper.remove();
    	}
    	$('.'+options.svgCtnCls).remove();
    	$('.'+options.cssClickClass).remove();
    },

    makePoly:function(el,o){

        var s=this;

        s.addPoint(o.top,o.left);

        s.makeSvgContainer();

        //清空svg，重新画图

        $(el).find('div.'+options.svgCtnCls).empty();

        //遍历已经有的点，做出路线

        var points=$('div.'+options.cssClickClass);

        var datas=s.getData();

        //生成路径

        var path="";

        $.each(points,function(i,n){

            if(i==0){

                path+="M";

            }else{

                path+="L";

            }

            var leftInSvg=$(n).position().left-datas.minLeft;

            var TopInSvg=$(n).position().top-datas.minTop;

            path += leftInSvg;

            path += ",";

            path += TopInSvg;

            path += " ";

        });

        var ploy = Raphael('mapPloy', datas.w, datas.h);
        this.paper = ploy;

        var pathslength=ploy.path(path).attr({

            stroke:'#1791fc', 

            'stroke-width':3,

            opacity:.7, 

            fill:"none"});

        //计算距离

        return pathslength.getTotalLength();

    },
    
    makeRect:function(el,o){

        var s=this;

        s.addPoint(o.top,o.left);

        s.makeSvgContainer();

        //清空svg，重新画图

        //$(el).find('div.'+options.svgCtnCls).empty();

        //遍历已经有的点，做出路线

        var points=$('div.'+options.cssClickClass);

        var datas=s.getData();
        if(datas.length == 2){
        	var pointTop1=$(points[0]).position().top-datas.minTop,
        		pointLeft1=$(points[0]).position().left-datas.minLeft,
        		pointTop2=$(points[1]).position().top-datas.minTop,
        		pointLeft2=$(points[1]).position().left-datas.minLeft;


            //生成路径
        	var path = "M"+pointLeft1+","+pointTop1
        				+" L"+pointLeft2+","+pointTop1
        				+" L"+pointLeft2+","+pointTop2
        				+" L"+pointLeft1+","+pointTop2
        				+" L"+pointLeft1+","+pointTop1;

            var ploy = Raphael('mapPloy', datas.w, datas.h);
            this.paper = ploy;

            ploy.path(path).attr({

                stroke:'#1791fc', 

                'stroke-width':3,

                opacity:.7, 

                fill:"none"
            });
        	
        }

    },
    
    makeFoldLine:function(el,dataList,obj){

        var s=this;       
        var datas = s.addPoints(dataList,obj);
        s.makeSvgContainer();
        var dataObj=s.getData();
      
        //生成路径
        var path="";
        for(var i= 0;i<datas.length;i++){
        	data = datas[i];
        	if(i==0){
                path+="M";
            }else{
                path+="L";
            }
        	
        	var leftInSvg = data.x-dataObj.minLeft;
    		var TopInSvg = data.y-dataObj.minTop;
    		
    		path += leftInSvg;
            path += ",";
            path += TopInSvg;
            path += " ";
        	
        }

        var ploy = Raphael('mapPloy', dataObj.w, dataObj.h);
        this.paper = ploy;

        ploy.path(path).attr({

            stroke:'#1791fc', 

            'stroke-width':2,

            opacity:.7, 

            fill:"none"
        });

    },
    
    makeSquare:function(x,y,r,W,H,id){
    	var xlt = x-r,
    		ylt = y-r,
    		square = Raphael(id, W, H);
    	this.paper = square;

		var a = square.rect(xlt, ylt, 2*r, 2*r)
			.attr({"stroke-width":2,"stroke":"#000","stroke-opacity":0.5,"cursor":"e-resize","stroke-dasharray":"--"})
			.drag(function (dx, dy, x, y) {
				var newX,newY,newWidthHeight;
				var tempx = this.xlt+this.rectWidthHeight/2,
					tempy = this.ylt+this.rectWidthHeight/2;
				var ol = $("#"+id).offset().left;

				if((x-ol)>tempx){
					newX = this.xlt-dx;
					newY = this.ylt -dx;
					newWidthHeight = this.rectWidthHeight+2*dx;
				}else{
					newX = this.xlt+dx;
					newY = this.ylt +dx;
					newWidthHeight = this.rectWidthHeight-2*dx;
				}
				if(tempx>0 && tempy>0 && tempx<W && tempy<H){
					a.attr({"x":newX,"y":newY,"width":newWidthHeight,"height":newWidthHeight});
				}
			}, function (x, y) {				
					this.xlt = a.attr("x");
					this.ylt = a.attr("y");
					this.rectWidthHeight = a.attr("width");
			});
		
		var p = square.circle(x, y, 3)
			.attr({"fill":"#000","cursor":"move"})
			.drag(function(dx, dy, x, y){
				var oswh = a.attr("width");
				if((this.xc+dx)>0 && (this.yc+dy)>0 && (this.xc+dx)<W &&(this.yc+dy)<H){
					p.attr({"cx":this.xc+dx,"cy":this.yc+dy});
					a.attr({"x":this.xc+dx-oswh/2,"y":this.yc+dy-oswh/2});
				}
			},function(){
				this.xc = p.attr("cx");
				this.yc = p.attr("cy");
			});
		
		var obj = {
				point:p,
				square:a
		};
		this.square = obj;
		return obj;
    },
    
    makeRectange:function(x,y,rt,rl,W,H,id){
    	var xlt = x-rl,
    		ylt = y-rt,
    		square = Raphael(id, W, H);
    	this.paper = square;

		var a = square.rect(xlt, ylt, 2*rl, 2*rt)
			.attr({"stroke-width":2,"stroke":"#000","stroke-opacity":0.5,"cursor":"move","stroke-dasharray":"--"})
			.drag(function (dx, dy, x, y) {
				var newX,newY,newWidth,newHeight;
				var tempx = this.xlt+this.rectWidth/2,
					tempy = this.ylt+this.rectHeight/2;
				var ol = $("#"+id).offset().left,
					ot = $("#"+id).offset().top;

				if((x-ol)>tempx && (y-ot)>tempy){
					newX = this.xlt-dx;
					newY = this.ylt-dy;
					newWidth = this.rectWidth+2*dx;
					newHeight = this.rectHeight+2*dy;
				}else if((x-ol)>tempx && (y-ot)<=tempy){
					newX = this.xlt-dx;
					newY = this.ylt+dy;
					newWidth = this.rectWidth+2*dx;
					newHeight = this.rectHeight-2*dy;
				}else if((x-ol)<=tempx && (y-ot)<=tempy){
					newX = this.xlt+dx;
					newY = this.ylt+dy;
					newWidth = this.rectWidth-2*dx;
					newHeight = this.rectHeight-2*dy;
				}else if((x-ol)<=tempx && (y-ot)>tempy){
					newX = this.xlt+dx;
					newY = this.ylt-dy;
					newWidth = this.rectWidth-2*dx;
					newHeight = this.rectHeight+2*dy;
				}
				if(tempx>0 && tempy>0 && tempx<W && tempy<H){
					a.attr({"x":newX,"y":newY,"width":newWidth,"height":newHeight});
				}
			}, function (x, y) {				
					this.xlt = a.attr("x");
					this.ylt = a.attr("y");
					this.rectWidth = a.attr("width");
					this.rectHeight = a.attr("height");
			});
		
		var p = square.circle(x, y, 3)
			.attr({"fill":"#000","cursor":"move"})
			.drag(function(dx, dy, x, y){
				var osw = a.attr("width"),
					osh = a.attr("height");
				if((this.xc+dx)>0 && (this.yc+dy)>0 && (this.xc+dx)<W &&(this.yc+dy)<H){
					p.attr({"cx":this.xc+dx,"cy":this.yc+dy});
					a.attr({"x":this.xc+dx-osw/2,"y":this.yc+dy-osh/2});
				}
			},function(){
				this.xc = p.attr("cx");
				this.yc = p.attr("cy");
			});
		
		var obj = {
				point:p,
				square:a
		};
		this.square = obj;
		return obj;
    }

};