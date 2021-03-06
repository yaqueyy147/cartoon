/**
 * Created by suyx on 2017/1/12.
 */
$(function () {
    $("#province4Search").val("");
    $("#province4Search").change();
    $("#province4Search2").val("");
    $("#province4Search2").change();

    $("#doSearch").click(function () {
        var params = {};
        params.loginName = $("#loginName4Search").val();
        params.userName = $("#userName4Search").val();
        params.province = $("#province4Search").val();
        params.city = $("#city4Search").val();
        params.district = $("#district4Search").val();
        loadDataGrid(params);
    });

    $("#doSearch2").click(function () {
        var params = {};
        params.familyName = $("#familyName4Search2").val();
        params.province = $("#province4Search2").val();
        params.city = $("#city4Search2").val();
        params.district = $("#district4Search2").val();
        loadFamilyList(params);
    });

    var params = {};
    loadDataGrid(params);

    $("#userDialog").dialog({
        width: 800,
        height: 400,
        closed: true,
        cache: false,
        modal: true,
        "buttons":[
            {
                "text":"提交",
                handler:function(){
                    var loginName = $("#loginName").val();
                    var userName = $("#userName").val();
                    var password = $("#password").val();
                    var passwordAffirm = $("#passwordAffirm").val();
                    var idCard = $("#idCard").val();
                    var idCardPhoto = $("#idCardPhoto").val();
                    var phone = $("#phone").val();
                    var province = $("#province").val();
                    var city = $("#city").val();
                    var district = $("#district").val();

                    var checkCode = $("#checkCode").val();

                    if($.trim(loginName).length <= 0){
                        alert("登录名不能为空！");
                        // $("#loginName").parent().addClass("has-error");
                        return ;
                    }
                    if($.trim(userName).length <= 0){
                        alert("用户名不能为空！");
                        // $("#userName").parent().addClass("has-error");
                        return ;
                    }
                    if($.trim(password).length <= 0){
                        alert("密码不能为空！");
                        return ;
                    }
                    if($.trim(passwordAffirm).length <= 0){
                        alert("确认密码不能为空！");
                        return ;
                    }
                    if(password != passwordAffirm){
                        alert("密码输入不一致！");
                        return;
                    }
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
                    var formData = {};
                    var postUrl = projectUrl + "/consoles/saveUserBase";
                    var testData = $("#userInfoForm").serializeArray();

                    for (var item in testData) {
                        formData["" + testData[item].name + ""] = testData[item].value;
                    }
                    $.ajax({
                        type:'post',
                        url: postUrl,
                        // async:false,
                        dataType:'json',
                        data:formData,
                        success:function (data) {
                            $.messager.alert('提示',data.msg);
                            if(data.code >= 1){
                                var params = {};
                                loadDataGrid(params);
                                $("#userInfoForm").form('clear');
                                closeDialog("userDialog");
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
            },
            {
                "text":"取消",
                handler:function () {
                    $("#userInfoForm").form('clear');
                    closeDialog("userDialog");
                }
            }
        ]
    });

    $("#resourceDialog").dialog({
        width: 400,
        height: 400,
        closed: true,
        cache: false,
        modal: true,
        "buttons":[
            {
                "text":"提交",
                handler:function(){
                    var userId = $("#userId4Tree").val();
                    var treeObj = $.fn.zTree.getZTreeObj("resourceTree");
                    var nodes = treeObj.getCheckedNodes(true);
                    var selectIds = "";
                    for(var i=0;i<nodes.length;i++){
                        var ii = nodes[i];
                        selectIds += "," + ii.id;
                    }
                    if($.trim(selectIds).length > 1){
                        selectIds = selectIds.substring(1);
                    }
                    $.ajax({
                        type:'post',
                        url: projectUrl + "/consoles/saveAuth",
                        // async:false,
                        dataType:'json',
                        data:{userId:userId,sourceIds:selectIds},
                        success:function (data) {

                            alert(data.msg);
                            if(data.code >= 1){
                                $("#resourceDialog").dialog("close");
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
            },
            {
                "text":"取消",
                handler:function () {
                    $("#resourceDialog").dialog("close");
                }
            }
        ]
    });

    $("#modifyPasswordDialog").dialog({
        width: 400,
        height: 200,
        closed: true,
        cache: false,
        modal: true,
        "buttons":[
            {
                "text":"提交",
                handler:function(){

                    var newPassword = $("#newPassword").val();
                    var newPasswordAffirm = $("#newPasswordAffirm").val();
                    if(newPassword != newPasswordAffirm){
                        alert("密码输入不一致!");
                        return;
                    }

                    var postUrl = projectUrl + "/consoles/modifyPassword";
                    var params = {};
                    params.userId = $("#userIdForModify").val();
                    params.newPassword = newPassword;
                    params.isAdmin = 1;
                    $.ajax({
                        type:'post',
                        url: postUrl,
                        // async:false,
                        dataType:'json',
                        data:params,
                        success:function (data) {
                            alert(data.msg);
                            if(data.code >= 1){

                                var params2 = {};
                                loadDataGrid(params2);
                                $("#modifyPasswordForm").form('clear');
                                $("#modifyPasswordDialog").dialog("close");
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
            },
            {
                "text":"取消",
                handler:function () {
                    $("#modifyPasswordForm").form('clear');
                    $("#modifyPasswordDialog").dialog("close");
                }
            }
        ]
    });

    $("#toAdd").click(function () {

        $("#userInfoForm").form('clear');
        $("#state").combobox("setValue",1);
        $("#isFront").combobox("setValue",1);
        $("#isConsole").combobox("setValue",1);
        $("#userId").val(0);
        $("#passwordTr").removeAttr("style");
        $("#userDialog").dialog('open');
        $("#loginName").removeAttr("readonly");
    });
    var setting = {
        data: {
            simpleData: {
                enable:true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: ""
            }
        },
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: { "Y": "ps", "N": "ps" }
        }
    };
    $("#toSetAuth").click(function () {
        var selectRows = $("#userList").datagrid('getSelections');
        if(selectRows.length > 1){
            alert("只能编辑一条数据!");
            return;
        }
        if(selectRows.length < 1){
            alert("请选择一条数据!");
            return;
        }
        if(selectRows[0].isconsole != 1){
            alert("该用户不是后台用户，不能授权！");
            return;
        }
        $("#userId4Tree").val(selectRows[0].id);
        var params = {userId:selectRows[0].id};
        var dataList = getData("/consoles/userResourceList",params).resourceList;
        $.fn.zTree.init($("#resourceTree"), setting, dataList);

        $("#resourceDialog").dialog('open');
    });

    $("#toEdit").click(function () {
        $("#userInfoForm").form('clear');
        $("#passwordTr").hide();
        var selectRows = $("#userList").datagrid('getSelections');
        if(selectRows.length > 1){
            alert("只能编辑一条数据!");
            return;
        }
        if(selectRows.length < 1){
            alert("请选择一条数据!");
            return;
        }
        loadDataToForm(selectRows[0]);
        $("#userDialog").dialog('open');
    });

    $("#toModifyPassword").click(function () {
        $("#modifyPasswordForm").form('clear');
        var selectRows = $("#userList").datagrid('getSelections');
        if(selectRows.length > 1){
            alert("只能编辑一条数据!");
            return;
        }
        if(selectRows.length < 1){
            alert("请选择一条数据!");
            return;
        }
        $("#userIdForModify").val(selectRows[0].id);
        $("#modifyPasswordDialog").dialog('open');
    });

    $("#toDel").click(function () {
        var selectRows = $("#userList").datagrid('getSelections');
        if(selectRows.length < 1){
            alert("请至少选择一条数据!");
            return;
        }
        var selectIds = "";
        var selectNames = [];
        for(var i=0;i<selectRows.length;i++){
            var ii = selectRows[i];
            selectIds += "," + ii.id;
            selectNames.push(ii.userName);
        }
        selectIds = selectIds.substring(1);
        $.messager.confirm('提示','确定要删除用户(' + selectNames + ')  吗?',function(r){
            if (r){
                $.ajax({
                    type:'post',
                    url: projectUrl + "/consoles/deleteUser",
                    // async:false,
                    dataType:'json',
                    data:{ids:selectIds},
                    success:function (data) {
                        alert(data.msg);
                        var params = {};
                        loadDataGrid(params);
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
    });



});

function closeDialog(dialogId){
    $("#userInfoForm").form('clear');
    $("#" + dialogId).dialog("close");
}

function loadDataGrid(params) {
    var dataList = getData("/consoles/userList",params).dataList;
    // dataList = formatDataList(dataList);
    $("#userList").datagrid({
        data:dataList,
        loadMsg:"加载中...",
        selectOnCheck:true,
        singleSelect:false,
        columns:[[
            {field:"ck",checkbox:"true"},
            {field:"id",title:"用户Id",width:"80",hidden:true},
            {field:"loginname",title:"用户账号",width:"120"},
            {field:"username",title:"用户昵称",width:"120"},
            {field:"password",title:"密码",width:"80",hidden:true},
            {field:"phone",title:"电话",width:"120"},
            {field:"qqnum",title:"QQ",width:"100"},
            {field:"wechart",title:"微信",width:"100"},
            {field:"address",title:"所在地",width:"150",
                formatter: function(value,row,index){
                    var address = "";
                    if($.trim(row.province).length > 0){
                        address += row.province;
                    }
                    if($.trim(row.city).length > 0){
                        address += row.city;
                    }
                    if($.trim(row.district).length > 0){
                        address += row.district;
                    }
                    return '<span title='+ address + '>'+address+'</span>';
                }},
            {field:"createname",title:"创建人",width:"120"},
            {field:"createdate",title:"创建时间",width:"150",
                formatter: function(value,row,index){
                    if($.trim(value).length > 0){
                        return value;
                    }
                    return '';
                }},
            {field:"state",title:"状态",width:"80",
                formatter: function(value,row,index){
                    if(value == 1){
                        return "可用";
                    }
                    return '不可用';
                }},
            {field:"state",title:"状态",width:"180",hidden:true},
            {field:"userfrom",title:"用户来源",width:"120",
                formatter: function(value,row,index){
                    if(value == 1){
                        return "前台注册";
                    }
                    return '后台管理员设置';
                }},
            {field:"isfront",title:"是否可登录前台",width:"100",
                formatter: function(value,row,index){
                    if(value == 1){
                        return "是";
                    }
                    return '否';
                }},
            {field:"isconsole",title:"是否可登录后台",width:"100",
                formatter: function(value,row,index){
                    if(value == 1){
                        return "是";
                    }
                    return '否';
                }}
            // {field:"operate",title:"操作",width:"120"}
        ]],
        loadFilter:pagerFilter
    });
}

function loadDataToForm(data){
    $("#userId").val(data.id);
    $("#userName").val(data.username);
    $("#loginName").val(data.loginname);
    $("#loginName").attr("readonly","readonly");
    $("#password").val(data.password);
    $("#passwordAffirm").val(data.password);
    $("#idCard").val(data.idnard);
    $("#phone").val(data.phone);
    $("#qqNum").val(data.qqnum);
    $("#wechart").val(data.wechart);
    $("#province").val(data.province);
    $("#province").change();
    $("#city").val(data.city);
    $("#city").change();
    $("#district").val(data.district);

    $("#state").combobox("select",data.state);
    $("#isFront").combobox("select",data.isfront);
    $("#isConsole").combobox("select",data.isconsole);
    $("#userFrom").val(data.usefrom);
    $("#userPhoto").val(data.userphoto);
}
