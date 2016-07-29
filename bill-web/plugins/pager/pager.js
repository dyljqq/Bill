Lay.package(function(){
    return function($){
        var Pager = function(selector, options){
            if (arguments.length == 1)
            {
                options = selector;
                selector = "._pager";
            }

            options = options || {};

            if (options.domID)
                this.$Pager = $(options.domID);
            else
                this.$Pager = $("._pager");

            this._domID = selector;

            this._pageIndex = 0;
            this._startPage = options.startPage || 1;
            this._pageCount = 0;
            this._pageSize = 20;

            this._dataBinder = options.dataBinder || function(){};
        };

        Pager.prototype = {
            init:function(){
                var domID = this._domID;
                var that = this;

                this.build();

                $(document).on("click", domID + ' ._page_index', function(){
                    that.$Pager.find("li").removeClass("active");
                    $(this).addClass("active");
                    var page = $(this).text();
                    if (page == "...")
                        page = parseInt($(this).prev().text()) + 1;
                    that.toPage(page);
                    return false;
                });

                this.$PageList = $(domID + " ul");

                $(document).on("click", "._page_control", function(){
                    if ($(this).hasClass("disabled"))
                        return false;

                    var control = $(this).data("ctrl");
                    that[control]();
                    return false;
                });

                this.toPage(this._startPage);
            },
            prePage:function(){
                if (this._pageIndex > 1)
                    this.toPage(this._pageIndex - 1);
            },
            nextPage:function(){
                if (this._pageIndex < this._pageCount)
                    this.toPage(this._pageIndex + 1);
            },
            firstPage:function(){
                if (this._pageIndex > 1)
                    this.toPage(1);
            },
            lastPage:function(){
                if (this._pageIndex < this._pageCount)
                    this.toPage(this._pageCount);
            },
            toPage:function(pageIndex){

                this._pageIndex = parseInt(pageIndex);
                var that = this;
                this._dataBinder(pageIndex, function(total){
                    that.update(total);
                });
            },
            build:function(){
                var HTML = '<ul class="pagination pagination-sm no-margin pull-right"></ul>';
                this.$Pager.append(HTML);
            },
            update:function(total){
                // 计算页码总数
                var pageCount = parseInt(total / this._pageSize);
                if (total % this._pageSize > 0)
                    pageCount++;

                this._pageCount = pageCount;

                // 构造HTML
                var HTML = '';

                HTML += '<li><a href="#" class="_page_control" data-ctrl="firstPage"><i class="glyphicon glyphicon-step-backward"></i></a></li>';
                HTML += '<li><a href="#" class="_page_control" data-ctrl="prePage"><i class="glyphicon glyphicon-chevron-left"></i></a></li>';

                if (pageCount < 10)
                {
                    for (var i = 1; i <= pageCount; i++)
                    {
                        HTML += '<li';
                        if (this._pageIndex == i)
                            HTML += ' class="active"';
                        HTML += '><a href="#" class="_page_index">' + i + "</a></li>";
                    }
                }
                else
                {
                    var startIndex = 1;
                    if (this._pageIndex > 5)
                        startIndex = this._pageIndex - 4;
                    var endIndex = startIndex + 7;
                    var extendHTML = '';

                    if (this._pageCount - this._pageIndex > 5)
                    {
                        extendHTML += '<li><a href="#" class="_page_index">...</a></li>';
                        extendHTML += '<li><a href="#"class="_page_index">' + this._pageCount + '</a></li>';
                    }
                    else
                    {
                        startIndex = this._pageCount - 9;
                        endIndex = this._pageCount;
                    }

                    for (var i = startIndex; i <= endIndex; i++)
                    {
                        HTML += '<li';
                        if (this._pageIndex == i)
                            HTML += ' class="active"';
                        HTML += '><a href="#" class="_page_index">' + i + "</a></li>";
                    }

                    if (extendHTML != '')
                        HTML += extendHTML;
                }

                HTML += '<li><a href="#" class="_page_control" data-ctrl="nextPage"><i class="glyphicon glyphicon-chevron-right"></i></a></li>';
                HTML += '<li><a href="#" class="_page_control" data-ctrl="lastPage"><i class="glyphicon glyphicon-step-forward"></i></a></li>';

                this.$PageList.children().remove();
                this.$PageList.append(HTML);

                // 设置按钮
                var $PRE = $("._page_control[data-ctrl='prePage']").parent();
                var $FIRST = $("._page_control[data-ctrl='firstPage']").parent();
                var $NEXT = $("._page_control[data-ctrl='nextPage']").parent();
                var $LAST = $("._page_control[data-ctrl='lastPage']").parent();

                if (this._pageIndex > 1)
                {
                    $PRE.removeClass("disabled");
                    $FIRST.removeClass("disabled");
                }
                else
                {
                    $PRE.addClass("disabled");
                    $FIRST.addClass("disabled");
                }

                if (this._pageIndex < this._pageCount)
                {
                    $NEXT.removeClass("disabled");
                    $LAST.removeClass("disabled");
                }
                else
                {
                    $NEXT.addClass("disabled");
                    $LAST.addClass("disabled");
                }
            },
            getPageIndex:function(){
                return this._pageIndex;
            },
            show:function(){
                this.$Pager.show();
            },
            hide:function(){
                this.$Pager.hide();
            },
            toggle:function(){
                this.$Pager.toggle();
            }
        };

        return Pager;
    };
});