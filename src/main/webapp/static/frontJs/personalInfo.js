/**
 * Created by suyx on 2016/12/18.
 */
$(function () {

    $("#regedit").click(function () {
        var phone = $("#phone").val();
        var province = $("#province").val();
        var city = $("#city").val();
        var district = $("#district").val();

        if($.trim(province).length <= 0){
            alert("请选择省！");
            return ;
        }
        if($.trim(city).length <= 0){
            alert("请选择市！");
            return ;
        }
        if($.trim(district).length <= 0){
            alert("请选择区！");
            return ;
        }
        if($.trim(phone).length != 11){
            alert("手机号输入有误！如果是固定电话，请加上区号！");
            return ;
        }
        var formData = $("#personalForm").serializeArray();
        var testData = {};
        for (var item in formData) {
            testData["" + formData[item].name + ""] = formData[item].value;
        }
        $.ajax({
            type:'post',
            url: projectUrl + '/sign/modifyPersonalInfo',
            dataType: 'json',
            data:testData,
            // async:false,
            success:function (data) {
                if(data.code >= 1){
                    alert(data.msg);
                }

            },
            error:function (data) {
                var responseText = data.responseText;
                if(responseText.indexOf("登出跳转页面") >= 0){
                    ajaxErrorToLogin();
                }else{
                    alert(JSON.stringify(data));
                }
            }
        });
    });

    $("#toModify-pwd").on({
        click:function () {
            var oldP = $("#oldPassword").val();
            var newP = $("#newPassword").val()
            var newPA = $("#newPasswordAffirm").val();
            if(newP != newPA){
                alert("两次新密码输入不一致！");
                return;
            }

            $.ajax({
                type:'post',
                url: projectUrl + '/sign/modifyPassword',
                dataType: 'json',
                data:{userId:userId,newPassword:newP,oldPassword:oldP},
                // async:false,
                success:function (data) {
                    if(data.code >= 1){
                        alert(data.msg);
                    }
                    if(data.code == 1){
                        $("#oldPassword").val("");
                        $("#newPassword").val("")
                        $("#newPasswordAffirm").val("");
                        $("#password").val(newP);
                        $("#modifyModal").modal('hide');
                    }

                },
                error:function (data) {
                    var responseText = data.responseText;
                    if(responseText.indexOf("登出跳转页面") >= 0){
                        ajaxErrorToLogin();
                    }else{
                        alert(JSON.stringify(data));
                    }
                }
            });
        }
    });

    $("#toModify-photo").click(function () {
        var photoPath = $("#photoUrl").val();
        $.ajax({
            type:'post',
            url: projectUrl + '/family/modifyPhoto',
            dataType: 'json',
            data:{photoPath:photoPath},
            // async:false,
            success:function (data) {
                if(data.code >= 1){
                    alert(data.msg);
                    $("#userPhotoBox img").attr("src",photoPath);
                    $("#userphoto").val(photoPath);
                    $("#photoModal").modal('hide');
                }
            },
            error:function (data) {
                var responseText = data.responseText;
                if(responseText.indexOf("登出跳转页面") >= 0){
                    ajaxErrorToLogin();
                }else{
                    alert(JSON.stringify(data));
                }
            }
        });
    });
});
