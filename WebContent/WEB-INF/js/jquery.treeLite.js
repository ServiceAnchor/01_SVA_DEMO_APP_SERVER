/**
*
* TreeLite
* 
*/
(function ($) {
    $.fn.treeLite = function (options) {
        var settings = $.extend
        (
            {
                items: null,
                treeBoxCss: "treelite_treebox",
                treeRootNodeBoxCss: "treelite_treerootnodebox",
                treeNodeBoxCss: "treelite_treenodebox",
                treeNodeCss: "treelite_treenode",
				treeCollapseOpenCss: "icon-chevron-down treelite-collapse",//treeCollapseCss
				treeCollapseCloseCss: "icon-chevron-right treelite-collapse",//
				treeItemOpenCss: "icon-folder-open treelite-item",
				treeItemCloseCss: "icon-folder-close treelite-item",
				treeItemChildCss: "icon-file treelite-item",
                state_collpase: "collpase",
                name: "",
                nodeId: "node-id",
                nodeValue: "node-value",
                selected: "selected",//'selected'
                checked: "checked",//'checked'
                hasChild: "hasChild",
                loaded: "loaded",
                cascadeCheck: true,
                checkbox: false,
                animate: true,
                //dynamic: true,
                selectedChanged: function () { },
                checkedChanged: function () { }
            },
            options
		);
        function init() {
            return this.each(function () {
                settings.$selector = $(this);
                $.extend(settings, $.treeLite);
                $(this).data("treeLite", settings);
                settings._setParents.call(settings, settings.items);
                settings.initializeTools.call(settings, settings.items);
            });
        }
        if ($.treeLite[options])
            return $.treeLite[options].apply(this, Array.prototype.slice.call(arguments, 1));
        else if (typeof options === 'object' || !options)
            return init.apply(this, arguments);
    };

    $.treeLite = {
        prepareTreeHtml: function (items) {
            var base = this;
            var nodeBox = $("<ul>");
            for (var i = 0; i < items.length; i++) {
                var item = items[i];
                var node = $("<li>").html("<span>" + item.text + "</span>");
                if (item.value != undefined)
                    node.attr(base.nodeValue, item.value);
                if (item.id != undefined)
                    node.attr(base.nodeId, item.id);
                if (item.checked)
                    node.attr(base.checked, item.checked);
                if (item.selected)
                    node.attr(base.selected, item.selected).addClass(base.selected);
                if (item.children != null && item.children.length > 0) {
					if (item.collpase)
						node.attr(base.state_collpase, true);
                    /*if (base.dynamic)
                        node.attr(base.hasChild, true);
                    else*/
                        node.append(base.prepareTreeHtml(item.children));
                }
                nodeBox.append(node);
            }
            return nodeBox;
        },
        initializeTools: function (citems) {
            var base = this;
            if (citems)
                base.$selector.append(base.prepareTreeHtml.call(base, citems));
            base.setTreeFeature.call(base, base.$selector, true);
        },
        setTreeFeature: function ($selector, first) {
            var base = this;
            $selector.addClass(base.treeBoxCss);
            if (first)
                $selector.find("ul:first").nextAll().andSelf().addClass(base.treeRootNodeBoxCss);
            $selector.find("ul").addClass(base.treeNodeBoxCss);
            var nodelist = $selector.find("li").addClass(base.treeNodeCss);
            var selectedChanged = base.selectedChanged;
            nodelist.click(function (event) {
                event.stopPropagation();
                $(this).parents("." + base.treeRootNodeBoxCss).find("li").attr(base.selected, false).removeClass(base.selected);
                $(this).attr(base.selected, true).addClass(base.selected);
                if (selectedChanged) 
					selectedChanged($(this).attr(base.nodeId));
            });
            nodelist.each(function () {
                $this = $(this);
                var html = $this.html();
				var collapser = $("<i>");
                var icon = $("<i>");
                if ($this.children("ul").length > 0 || $this.attr(base.hasChild) == "true") {
                    if ($this.attr(base.hasChild)) 
						$this.addClass(base.treeBoxCss);
                    collapser.click(function (event) {
                        event.stopPropagation();
                        if ($(this).parent().attr(base.state_collpase) == "true")
                            base.expand.call(base, $(this));
                        else
                            base.collpase.call(base, $(this));
                    });
                    if ($this.attr(base.state_collpase) != "true") {
						collapser.addClass(base.treeCollapseOpenCss);
                        icon.addClass(base.treeItemOpenCss);
                    }
                    else {
						collapser.addClass(base.treeCollapseCloseCss);
                        icon.addClass(base.treeItemCloseCss);
                    }
                }
                else
                    icon = icon.addClass(base.treeItemChildCss);
                if (base.checkbox) {
                    var cascadeCheck = base.cascadeCheck;
                    var chk = $("<input type='checkbox'></input>").attr(base.nodeId, $this.attr(base.nodeId) != null ? $this.attr(base.nodeId) : "").val($this.attr(base.nodeValue) != null ? $this.attr(base.nodeValue) : $this.attr(base.nodeId))
                    .change(function (event) {
                        event.stopPropagation();
                        /*if (base.dynamic)
                            base.dynamicInitial($(this).parent());*/
                        if (cascadeCheck){
							var childrenChks = $(this).parents("." + base.treeNodeCss).first().find(":checkbox");
							var checked = $(this).is(":checked");
							$(childrenChks).each(function () {
								$(this).attr(base.checked, checked);
							});
							if (jQuery().uniform)
								$.uniform.update(childrenChks);
						}
                        if (base.checkedChanged)
                            base.checkedChanged($(this).parents("." + base.treeRootNodeBoxCss).find(":checked"));
                    });
                    if (base.name)
                        chk.attr("name", base.name);
                    $this.prepend(chk);
					chk.attr(base.checked, $this.attr(base.checked)=="checked"? true:false);

					if (jQuery().uniform) {
						chk.uniform();
						$.uniform.update(chk);
					}
                }
                $this.prepend(icon).prepend(collapser);
                /*if (base.dynamic && !$this.attr(base.state_collpase))
					base.expand.call(base, $this.find("> .icon-chevron-down"));*/
                if ($this.attr(base.state_collpase) && $this.children("ul").length > 0) {
                    collapser.nextAll("ul").hide();
                    collapser.next().addClass(base.treeItemCloseCss);
					collapser.removeClass(base.treeCollapseOpenCss);
					collapser.addClass(base.treeCollapseCloseCss);
                }
            });
        },
        findChildItems: function (items, id) {
            var base = this;
            if (items && items.length > 0) {
                for (var i = 0; i < items.length; i++) {
                    var item = items[i];
                    if (item.id == id)
                        return item.children;
                    if (item.children != null && item.children.length > 0) {
                        var found = base.findChildItems(item.children, id);
                        if (found)
                            return found;
                    }
                }
            }
        },
        /*dynamicInitial: function ($selector) {
            var base = this;
            $selector.each(function () {
                var $this = $(this);
                if ($this.attr(base.hasChild) == "true") {
                    if (!$this.attr(base.loaded)) {
                        var items = base.findChildItems(base.items, $this.attr(base.nodeId));
                        if (items) {
                            $this.append(base.prepareTreeHtml(items));
                            base.setTreeFeature($this, false);
                        }
                        $this.find("ul").hide();
                        $this.attr(base.loaded, true);
                        base.dynamicInitial.call(base, $this.find("." + base.treeNodeCss));
                    }
                    else
                        base.dynamicInitial.call(base, $this.find("." + base.treeNodeCss));
                }
            });
        },*/
        expand: function ($selector) {
            var base = this;
            if ($selector.nextAll("ul").length > 0 || $selector.parent().attr(base.hasChild) == "true") {
                /*if (base.dynamic && !$selector.parent().attr(base.loaded)) {
                    var items = base.findChildItems(base.items, $selector.parent().attr(base.nodeId));
                    if (items) {
                        $selector.parent().append(base.prepareTreeHtml(items));
                        base.setTreeFeature($selector.parent(), false);
                    }
                    $selector.nextAll("ul").hide();
                    $selector.parent().attr(base.loaded, true);
                }*/
                $selector.parent().attr(base.state_collpase, false);
                if (base.animate)
                    $selector.nextAll("ul").slideDown();
                else
                    $selector.nextAll("ul").show();
                $selector.next().removeClass(base.treeItemCloseCss);
				$selector.next().addClass(base.treeItemOpenCss);
				$selector.removeClass(base.treeCollapseCloseCss);
				$selector.addClass(base.treeCollapseOpenCss);
            }
        },
        collpase: function ($selector) {
            var base = this;
            $selector.parent().attr(base.state_collpase, true);
            if (base.animate)
                $selector.nextAll("ul").slideUp();
            else
                $selector.nextAll("ul").hide();
			$selector.next().removeClass(base.treeItemOpenCss);
            $selector.next().addClass(base.treeItemCloseCss);
			$selector.removeClass(base.treeCollapseOpenCss);
			$selector.addClass(base.treeCollapseCloseCss);
        },
        selectedTexts: function (items) {
            var base = $(this).data("treeLite");
            var returnValue = "";
            if (items) {
                for (var i = 0; i < items.length; i++) {
                    if (items[i].checked)
                        returnValue += items[i].text + ",";
                    if (items[i].children) {
                        var ret = base.selectedTexts.call(this, items[i].children);
                        returnValue += ret;
                        if (ret)
                            returnValue += ",";
                    }
                }
            } 
			else {
                base.$selector.find(":checked").each(function () {
                    if ($(this).next().text())
                        returnValue += $(this).next().text() + ",";
                });
			}
            if (returnValue.length > 0)
                returnValue = returnValue.substring(0, returnValue.length - 1);
            return returnValue;
        },
        selectedValues: function (items) {
            var base = $(this).data("treeLite");
            var returnValue = "";
            if (items) {
                for (var i = 0; i < items.length; i++) {
                    if (items[i].checked)
                        returnValue += items[i].value + ",";
                    if (items[i].children) {
                        var ret = base.selectedTexts.call(this, items[i].children);
                        returnValue += ret;
                        if (ret)
                            returnValue += ",";
                    }
                }
            } 
			else{
                base.$selector.find(":checked").each(function () {
                    if ($(this).val())
                        returnValue += $(this).val() + ",";
                });
			}
            if (returnValue.length > 0)
                returnValue = returnValue.substring(0, returnValue.length - 1);
            return returnValue;
        },
        selectedIds: function (items) {
            var base = $(this).data("treeLite");
            var returnValue = "";
            if (items) {
                for (var i = 0; i < items.length; i++) {
                    if (items[i].checked)
                        returnValue += items[i].value + ",";
                    if (items[i].children) {
                        var ret = base.selectedTexts.call(this, items[i].children);
                        returnValue += ret;
                        if (ret)
                            returnValue += ",";
                    }
                }
            } 
			else{
                base.$selector.find(":checked").each(function () {
                    if ($(this).attr(base.nodeId))
                        returnValue += $(this).attr(base.nodeId) + ",";
                });
			}
            if (returnValue.length > 0)
                returnValue = returnValue.substring(0, returnValue.length - 1);
            return returnValue;
        },
        selectedText: function () {
            var base = $(this).data("treeLite");
            return base.$selector.find("li[" + base.selected + "='selected'] > span").text();
        },
        selectedValue: function () {
            var base = $(this).data("treeLite");
            return base.$selector.find("li[" + base.selected + "='selected']").attr(base.nodeValue);
        },
        selectedId: function () {
            var base = $(this).data("treeLite");
            return base.$selector.find("li[" + base.selected + "='selected']").attr(base.nodeId);
        },
        showSelected: function () {
            var base = $(this).data("treeLite");
            /*if (base.dynamic) {
                var selectedItem = base.getSelectedItem.call(this, null);
                var parentItems = new Array();
                base.getSelectedParentItems.call(this, selectedItem, parentItems);
                parentItems.reverse(); //翻转顺序，以便自上而下的进行dynamicInitial
                for (var i = 0; i < parentItems.length; i++) {
                    base.dynamicInitial(base.$selector.find("li[" + base.nodeId + "='" + parentItems[i].id + "']"));
                }
            }*/
            var $selectedNode = base.$selector.find("li[" + base.selected + "='selected']");
            $selectedNode.parents("." + base.treeNodeCss).each(function () {
				base.expand.call(base, $(this).find("> .icon-chevron-right"));
            });
        },
        getSelectedParentItems: function (item, parentItems) {
            var base = $(this).data("treeLite");
            if (item == null) return new Array();
            if (!parentItems) parentItems = new Array();
            if (item.parent != undefined) {
                parentItems.push(item.parent);
                base.getSelectedParentItems.call(this, item.parent, parentItems);
            }
        },
        getItemById: function (id, items) {
            var base = $(this).data("treeLite");
            if (!items) items = base.items;
            for (var i = 0; i < items.length; i++) {
                if (items[i].id == id)
                    return items[i];
                else if (items[i].children) {
                    var found = base.getItemById.call(this, id, items[i].children);
                    if (found) return found;
                }
            }
        },
        getSelectedItem: function (items) {
            var base = $(this).data("treeLite");
            if (!items) items = base.items;
            for (var i = 0; i < items.length; i++) {
                if (items[i].selected)
                    return items[i];
                else if (items[i].children) {
                    var found = base.getSelectedItem.call(this, items[i].children);
                    if (found) 
						return found;
                }
            }
        },
        _setParents: function (items) {
            for (var i = 0; i < items.length; i++) {
                if (items[i].children) {
                    for (var j = 0; j < items[i].children.length; j++) {
                        items[i].children[j].parent = items[i];
                    }
                    this._setParents(items[i].children);
                }
            }
        },
        parentValue: function () {
            var base = $(this).data("treeLite");
            return base.$selector.find("li[" + base.selected + "='selected']").parent().parents("li").attr(base.nodeValue);
        },
        parentId: function () {
            var base = $(this).data("treeLite");
            return base.$selector.find("li[" + base.selected + "='selected']").parent().parents("li").attr(base.nodeId);
        },
        collpasedValues: function () {
            var base = $(this).data("treeLite");
            var returnValue = "";
            base.$selector.find("li[" + base.state_collpase + "='true']").each(function () {
                returnValue += $(this).attr(base.nodeValue) + ",";
            });
            if (returnValue.length > 0)
                returnValue = returnValue.substring(0, returnValue.length - 1);
            return returnValue;
        },
        collpasedIds: function () {
            var base = $(this).data("treeLite");
            var returnValue = "";
            base.$selector.find("li[" + base.state_collpase + "='true']").each(function () {
                returnValue += $(this).attr(base.nodeId) + ",";
            });
            if (returnValue.length > 0)
                returnValue = returnValue.substring(0, returnValue.length - 1);
            return returnValue;
        },
        expandedValues: function () {
            var base = $(this).data("treeLite");
            var returnValue = "";
            base.$selector.find("li[" + base.state_collpase + "!='true']").each(function () {
                if ($(this).attr(base.nodeValue))
                    returnValue += $(this).attr(base.nodeValue) + ",";
            });
            if (returnValue.length > 0)
                returnValue = returnValue.substring(0, returnValue.length - 1);
            return returnValue;
        },
        expandedIds: function () {
            var base = $(this).data("treeLite");
            var returnValue = "";
            base.$selector.find("li[" + base.state_collpase + "!='true']").each(function () {
                if ($(this).attr(base.nodeId))
                    returnValue += $(this).attr(base.nodeId) + ",";
            });
            if (returnValue.length > 0)
                returnValue = returnValue.substring(0, returnValue.length - 1);
            return returnValue;
        }
    };
})(jQuery);

