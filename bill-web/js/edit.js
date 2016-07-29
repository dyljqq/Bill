Lay.package(function(using, L) {
  using("./common.js")

  var $F = using("LL.Util.Format");
  var $ = using("LL.Mobile.Base");
  using("../plugins/formvalidation/js/formValidation.min")($);
  using("../plugins/formvalidation/js/framework/bootstrap.min")($);

  $(function() {
    $("#formBillEdit").formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields:{
            txtName:{
                validators:{
                    notEmpty:{
                        message: '请输入借款人姓名'
                    }
                }
            },
            txtMoney:{
                validators:{
                    notEmpty:{
                        message: '请输入借款人金额'
                    }
                }
            },
            txtRate:{
                validators:{
                    notEmpty:{
                        message: '请输入借款利率'
                    }
                }
            },
            txtDays:{
                validators:{
                    notEmpty:{
                        message: '请输入借款时间'
                    }
                }
            }
        }
    }).on('success.form.fv', function(){
        console.log("success");
        var name = $("#txtName").val()
        var money = $("#txtMoney").val()
        var rate = $("#txtRate").val()
        var startTime = $("#dateTimePicker").val()
        var days = $("#txtDays").val()
        var note = $("#txtNote").val()
        var timeStamp = Date.parse(new Date(startTime))
        L.call(L.infs.bill.add, {
            name: name,
            money: money,
            rate: rate,
            startTime: timeStamp,
            days: days,
            note: note
        }, function(code, data, message){
            if (code == 0)
                location.href = "index.html";
            else
                $(".js_error").text(message).removeClass("hidden");
        });
    });
  })

  $("#btnSave").click(function() {
    $(".js_error").addClass("hidden");
    $("#formBillEdit").formValidation('validate');
  })

})
