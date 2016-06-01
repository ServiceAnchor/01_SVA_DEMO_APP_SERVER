(function($) {
    $.fn.toolbarLite = function(options) {
        var settings = $.extend
        (
            {
                items: null,
                /*Ê¹ÓÃ·¶Àý
                [
                { link: true, display: "ÔÊÐí", css: "enabled", showIcon: true, url: "<%= Url.Action("Allow", new {condition="{0}" }).UrlDecode() %>", 
                selector: "#account_grid .check_row", mustSelect: "Please select some one!", singleSelect: "Single select only!"  },
                { splitter: true },
                { link: true, display: "Delete", css: "delete", showIcon: true, url: "#" },
                { pager: true, slide: true },
                { pager: true }
                ]*/
                toolbarCss: "toolbar",
                //toolbarStartFixCss: "tool_s_fix",
                toolboxCss: "tool_box",
                tooliconCss: "tool_icon",
                toollinkCss: "tool_link",
                splitterCss: "tool_splitter",
                toolpagerCss: "tool_pager",
                toolpagerSlideCss: "tool_slide_pager",
                pagernumberCss: "page_num",
                pageSelectorCss: "page_selector",
                pagerInfoCss: "page_info",
                pagerTemplate: "#{0}",
                urlsplitter: ",",
                pagerSelector: [10, 20, 30, 50, 100],
                pagerInfoTemplate: "{0}{1}{2}",
                ajax: null
            },
            options
		);
        return this.each
		(
			function() {
			    $.extend($.toolbarLite, settings);
			    $.toolbarLite.$selector = $(this);
			    $.toolbarLite.initializeTools();
			}
		);
    };

    $.toolbarLite = {
        initializeTools: function() {
            var base = this;
            base.drawToolbar();
            if (base.items)
                for (var i = 0; i < base.items.length; i++) {
                base.drawToolItem(base.items[i]);
            }
        },
        drawToolbar: function(i) {
            var base = this;
            //base.$selector.append($("<div>").addClass(base.toolbarCss).append($("<span>").addClass(base.toolbarStartFixCss)));
			base.$selector.append($("<div>").addClass(base.toolbarCss));
        },
        drawToolItem: function(item) {
            var base = this;
            var $toolbar = base.$selector.find(".toolbar");
            if (item) {
                //var $toolitem = $("<div>").addClass(base.toolboxCss).addClass(base.toolboxCss + "_" + item.css);
				var $toolitem = $("<div>").addClass(base.toolboxCss);
                if (item.link) {
                    if (item.showIcon != false) {
                        //var $toolicon = $("<span>").addClass(base.tooliconCss).addClass(base.tooliconCss + "_" + item.css);
						var $toolicon = $("<i>").addClass(item.css);
                        $toolitem.append($toolicon);
                    }
                    //var $lnk = $("<a>").attr("href", item.url).addClass(base.toollinkCss + " " + base.toollinkCss + "_" + item.css).html(item.display);
					var $lnk = $("<a>").attr("href", item.url).addClass(base.toollinkCss).html(item.display);
                    if (item.selector) {
                        var validateSelect = function() {
                            var length = 0;
                            var selected = [];
                            var index = 0;
                            $(item.selector).each(function() {
                                if ($(this).prop("checked"))
                                    selected[index++] = $(this).val();
                            });
                            selected = $.unique(selected);
                            length = selected.length;
                            if (length == 0) {
                                alert(item.mustSelect);
                                return;
                            }
                            else if (item.singleSelect && length > 1)
                                alert(item.singleSelect);
                        };
                        if (item.mustSelect) {
                            $lnk.attr("href", "javascript:;");
                            $lnk.bind("mousedown", validateSelect);
                            $lnk.bind("keydown", validateSelect);
                        }
                    }
                    if (item.click && $.isFunction(item.click))
                        $lnk.bind("click", item.click);
                    else
                        $lnk.bind("click", function() {
                            if (item.confirm)
                                if (!confirm(item.confirm)) return false;
                            var selected = [];
                            var index = 0;
                            $(item.selector).each(function() {
                                if ($(this).prop("checked"))
                                    selected[index++] = $(this).val();
                            });
                            location = item.url.replace("{0}", $.unique(selected).join(base.urlsplitter));
                            return false;
                        });
                    $toolitem.append($lnk);
                }
                else if (item.splitter)
                    $toolitem = $("<div>").addClass(base.splitterCss);
                else if (item.pager) {
                    if (item.slide) {
                        if (item.pageCount > 1) {
                            var $pager = $("<div>").addClass(base.toolpagerSlideCss);
                            var $pagenum = $("<input type='text'>").addClass(base.pagernumberCss).attr("name", base.pagernumberCss);
                            $pagenum.val(item.pageIndex + 1);
                            $pager.append($pagenum);
                            $toolitem.append($pager);
                            $pagenum.slider({ from: 1, to: item.pageCount, step: 1, smooth: false, round: 0, dimension: item.dimension, skin: "blue", callback: function(i) {
                                var url = base.pagerTemplate.replace("{0}", i).replace("{1}", "");
                                if (base.ajax)
                                    $.ajax($.extend(base.ajax, url));
                                else
                                    location = url;
                            }});
                            $toolitem.append($pager);
                        }
                    }
                    else {
                        var pageIndex = parseInt(item.pageIndex) + 1;
                        var $pager = $("<div>");
                        var $pagerbox;
                        var $pagerlnk;
                        var $pagericon;
                        var $pagenum = $("<input type='text'>").addClass(base.pagernumberCss).attr("name", base.pagernumberCss).keypress(function(event) {
                            if (event.keyCode == 13) {
                                var url = base.pagerTemplate.replace("{0}", $(this).val()).replace("{1}", "");
                                if (base.ajax)
                                    $.ajax($.extend(base.ajax, { url: url }));
                                else
                                    location = url;
                            }
                        });
                        $pagenum.val(pageIndex);
                        var $pagerCountSelector = $("<select>").addClass(base.pageSelectorCss).change(function() {
                            var url = base.pagerTemplate.replace("{0}", pageIndex).replace("{1}", $(this).val());
                            if (base.ajax)
                                $.ajax($.extend(base.ajax, { url: url }));
                            else
                                location = url;
                        });
                        for (var i = 0; i < base.pagerSelector.length; i++) {
                            if (item.pageSize == base.pagerSelector[i])
                                $pagerCountSelector.append($("<option>").val(base.pagerSelector[i]).html(base.pagerSelector[i]).attr("selected", "selected"));
                            else
                                $pagerCountSelector.append($("<option>").val(base.pagerSelector[i]).html(base.pagerSelector[i]));
                        }

                        $pagerbox = $("<div>").addClass(base.toolboxCss);
                        $pager.append($pagerbox.append($pagerCountSelector));
                        $pager.append($("<div>").addClass(base.splitterCss));
                        $pagerbox = $("<div>").addClass(base.toolboxCss).addClass(base.toolboxCss + "_page_first");
                        $pagerlnk = $("<a>").attr("href", base.pagerTemplate.replace("{0}", "1").replace("{1}", ""));
                        $pagericon = $("<span>").addClass(base.tooliconCss).addClass(base.tooliconCss + "_page_first");
                        $pager.append($pagerbox.append($pagerlnk.append($pagericon)));
                        $pagerbox = $("<div>").addClass(base.toolboxCss).addClass(base.toolboxCss + "_page_prev");
                        $pagerlnk = $("<a>").attr("href", base.pagerTemplate.replace("{0}", pageIndex - 1 < 1 ? 1 : pageIndex - 1).replace("{1}", ""));
                        $pagericon = $("<span>").addClass(base.tooliconCss).addClass(base.tooliconCss + "_page_prev");
                        $pager.append($pagerbox.append($pagerlnk.append($pagericon)));
                        $pager.append($("<div>").addClass(base.splitterCss));
                        $pagerbox = $("<div>").addClass(base.toolboxCss);
                        $pager.append($pagerbox.append($pagenum));
                        $pager.append($("<div>").addClass(base.splitterCss));
                        $pagerbox = $("<div>").addClass(base.toolboxCss).addClass(base.toolboxCss + "_page_next");
                        $pagerlnk = $("<a>").attr("href", base.pagerTemplate.replace("{0}", pageIndex < item.pageCount ? pageIndex + 1 : item.pageCount).replace("{1}", ""));
                        $pagericon = $("<span>").addClass(base.tooliconCss).addClass(base.tooliconCss + "_page_next");
                        $pager.append($pagerbox.append($pagerlnk.append($pagericon)));
                        $pagerbox = $("<div>").addClass(base.toolboxCss).addClass(base.toolboxCss + "_page_last");
                        $pagerlnk = $("<a>").attr("href", base.pagerTemplate.replace("{0}", item.pageCount).replace("{1}", ""));
                        $pagericon = $("<span>").addClass(base.tooliconCss).addClass(base.tooliconCss + "_page_last");
                        $pager.append($pagerbox.append($pagerlnk.append($pagericon)));
                        $pager.append($("<div>").addClass(base.splitterCss));
                        $pagerbox = $("<div>").addClass(base.toolboxCss).addClass(base.toolboxCss + "_refresh");
                        $pagerlnk = $("<a>").attr("href", "javascript:;").click(function() { if (base.ajax) $.ajax($.extend(base.ajax, { url: base.pagerTemplate.replace("{0}", item.pageIndex + 1) })); else document.location.reload(); });
                        $pagericon = $("<span>").addClass(base.tooliconCss).addClass(base.tooliconCss + "_refresh");
                        $pager.append($pagerbox.append($pagerlnk.append($pagericon)));

                        var $pagerInfo = $("<div>").addClass(base.pagerInfoCss);
                        var info = base.pagerInfoTemplate.replace("{0}", parseInt((item.pageIndex * item.pageSize) + 1)).replace("{1}", parseInt((item.pageIndex * item.pageSize) + item.visibleCount)).replace("{2}", item.count).replace("{3}", item.pageCount);
                        $pagerInfo.html(info);
                        $pager.append($pagerInfo);
                        $toolitem = $pager;

                        if (base.ajax)
                            $pager.find("a").each(function() {
                                var url = $(this).attr("href");
                                if (url.indexOf("javascript:") > -1 || url.indexOf("#") == 0)
                                    return;
                                else {
                                    $(this).click(function() {
                                        $.ajax($.extend(base.ajax, { url: url }));
                                        return false;
                                    });
                                }
                            });
                    }
                }
                $toolbar.append($toolitem);
            }
        }
    };
})(jQuery);