/**
 * Created by suyx on 2017/1/12.
 */
$(function () {

    $("#typeDialog").dialog({
        width: 800,
        height: 200,
        closed: true,
        cache: false,
        modal: true,
        "buttons":[
            {
                "text":"提交",
                handler:function(){
                    var formData = {};
                    var postUrl = projectUrl + "/consoles/savetype";
                    var testData = $("#typeForm").serializeArray();

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
                                $("#typeForm").form('clear');
                                closeDialog("typeDialog");
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
                    $("#typeForm").form('clear');
                    closeDialog("typeDialog");
                }
            }
        ]
    });

    $("#toAdd").click(function () {
        $("#typeForm").form('clear');
        $("#typeId").val("");
        $("#typeDialog").dialog('open');
    });

    $("#toEdit").click(function () {
        $("#typeForm").form('clear');
        var selectRows = $("#typeList").datagrid('getSelections');
        if(selectRows.length > 1){
            alert("只能编辑一条数据!");
            return;
        }
        if(selectRows.length < 1){
            alert("请选择一条数据!");
            return;
        }
        loadDataToForm(selectRows[0]);
        $("#typeDialog").dialog('open');
    });

    $("#toDel").click(function () {
        var selectRows = $("#meritorcatAttrList").datagrid('getSelections');
        if(selectRows.length < 1){
            alert("请至少选择一条数据!");
            return;
        }
        var selectIds = "";
        var selectNames = [];
        for(var i=0;i<selectRows.length;i++){
            var ii = selectRows[i];
            selectIds += "," + ii.id;
            selectNames.push(ii.cartoontype);
        }
        selectIds = selectIds.substring(1);
        $.messager.confirm('提示','确定要删除属性(' + selectNames + ')  吗?',function(r){
            if (r){
                $.ajax({
                    type:'post',
                    url: projectUrl + "/consoles/deleteMeritorcatAttr",
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

    var params = {};
    loadDataGrid(params);
});

function closeDialog(dialogId){
    $("#typeForm").form('clear');
    $("#" + dialogId).dialog("close");
}

function loadDataGrid(params) {
    var dataList = getData("/consoles/typelist",params);
    $("#typeList").datagrid({
        data:dataList,
        loadMsg:"加载中...",
        selectOnCheck:true,
        singleSelect:false,
        nowrap: true,
        columns:[[
            {field:"ck",checkbox:"true"},
            {field:"id",title:"id",width:"120",hidden:true},
            {field:"cartoontype",title:"类型名称",width:"120"},
            {field:"typedesc",title:"类型说明",width:"120"},
        ]],
        loadFilter:pagerFilter
    });
}


function loadDataToForm(data){
    $("#typeId").val(data.id);
    $("#cartoontype").val(data.cartoontype);
    $("#typedesc").val(data.typedesc);
}