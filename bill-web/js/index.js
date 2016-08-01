Lay.package(function (using, L) {
  using("./common.js")
  var $ = using("LL.Mobile.Base");
  var Pager = using("../plugins/pager/pager")($);
  var $M = using("LL.Extend.Mustache");
  var $F = using("LL.Util.Format");

  var STATUS = {
    ALREADY_PAY: 1,
    WAITING_PAY: 2
  }

  var pager
  var status = STATUS.WAITING_PAY
  $(function() {
    query(status)

    $("#btnSwitch").click(function() {
      var text = $("#btnSwitch").html()
      if(text == "待还款") {
        $("#btnSwitch").html("已还款");
        status = STATUS.ALREADY_PAY
      } else {
        $("#btnSwitch").html("待还款");
        status = STATUS.WAITING_PAY
      }
      query(status)
    })

    $("#btnAdd").click(function() {
      location.href = "edit.html"
    })

  })

  function add() {
    // TODO
  }

  function query(status) {
    pager = new Pager({
      dataBinder: function(pageIndex, completed) {
        L.call(L.infs.bill.query, {
          "status": status,
          "pageIndex": pageIndex
        }, function(code, data) {
          if(code == 0) {
            if (data.list.length == 0)
                $("#trNone").removeClass("hide");
            else {
                $("#trNone").addClass("hide");
            }
            completed(data.total)
            bind(data.list)
          } else {
            console.log(L.infs.bill.query + "error");
          }
        })
      }
    })
    pager.init()
  }

  $(document).on('click', '.js_delete', function() {
    var uid = $(this).data('id')
    L.call(L.infs.bill.delete, {
      "uid": uid
    }, function(code, data) {
      if(code == 0) {
        query(status)
      } else {
        console.log("删除失败");
      }
    })
  })

  function bind(data) {
    var tempRow = $("#tempRow").html()
    $("#tbList tbody").children(":not('#trNone')").remove();
    $("#tbList tbody").prepend($M.render(tempRow, {
      list: data,
      startTime: function() {
        return $F.date(this.begin_time,'yyyy-MM-dd');
      },
      endTime: function() {
        return $F.date(this.end_time,'yyyy-MM-dd');
      }
    }))
  }
})
