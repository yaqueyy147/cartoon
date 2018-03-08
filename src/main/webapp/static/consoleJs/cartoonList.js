/**
 * Created by suyx on 2017/1/12.
 */
$(function () {

    $("#province4Search").val("");
    $("#province4Search").change();

    $("input[name='visitStatus']").click(function () {
        var status = $(this).val();
        if(status == 0){
            $("#visitPwdTitle").show();
            $("#visitPwd").show();
        }else{
            $("#visitPwdTitle").hide();
            $("#visitPwd").hide();
            $("#visitPassword").val("");
        }
    });

    $("#cartoonDialog").dialog({
        width: 800,
        height: 600,
        closed: true,
        cache: false,
        modal: true,
        "buttons":[
            {
                "text":"提交",
                handler:function(){
                    if($.trim($("#cartoonname")).length <= 0){
                        $.messager.alert('提示',"请输入动漫名称！");
                        return;
                    }
                    var chkcartoontype = $("input[name='cartoontype']:checked");
                    if(chkcartoontype.length <= 0){
                        $.messager.alert('提示',"请至少选择一个类型！");
                        return;
                    }
                    var typeidstr = "";
                    var typedescstr = "";
                    for(var i=0;i<chkcartoontype.length;i++){
                        var ii = chkcartoontype[i];
                        typeidstr += "," + $(ii).attr("data-typeid");
                        typedescstr += "," + $(ii).attr("data-typename");
                    }
                    typeidstr = typeidstr.substring(1);
                    typedescstr = typedescstr.substring(1);
                    var formData = {};
                    var postUrl = projectUrl + "/consoles/saveCartoon";
                    var testData = $("#cartoonForm").serializeArray();
                    for (var item in testData) {
                        formData["" + testData[item].name + ""] = testData[item].value;
                    }
                    formData["typeidstr"] = typeidstr;
                    formData["typedescstr"] = typedescstr;
                    $.ajax({
                        type:'post',
                        url: postUrl,
                        dataType:'json',
                        data:formData,
                        // async:false,
                        success:function (data) {

                            $.messager.alert('提示',data.msg);
                            if(data.code >= 1){
                                dosearch();
                                $("#cartoonForm").form('clear');
                                closeDialog("cartoonDialog");
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
                    $("#cartoonForm").form('clear');
                    closeDialog("cartoonDialog");
                }
            }
        ]
    });

    $("#cartoonseriesListDialog").dialog({
        width: 1080,
        height: 600,
        closed: true,
        cache: false,
        modal: true,
        "buttons":[
            {
                "text":"关闭",
                handler:function () {
                    closeDialog("cartoonseriesListDialog");
                }
            }
        ]
    });

    $("#cartoonseriesInfoDialog").dialog({
        width: 800,
        height: 600,
        closed: true,
        cache: false,
        modal: true,
        "buttons":[
            {
                "text":"提交",
                handler:function(){
                    var cartoonid=$("#cartoonId4seriesbase").val();
                    if($.trim($("#seriestitle")).length <= 0){
                        $.messager.alert('提示',"请输入剧集标题！");
                        return;
                    }
                    var formData = {};
                    var postUrl = projectUrl + "/consoles/saveseries";
                    var testData = $("#cartoonseriesForm").serializeArray();
                    for (var item in testData) {
                        formData["" + testData[item].name + ""] = testData[item].value;
                    }
                    $.ajax({
                        type:'post',
                        url: postUrl,
                        dataType:'json',
                        data:formData,
                        // async:false,
                        success:function (data) {

                            $.messager.alert('提示',data.msg);
                            if(data.code >= 1){
                                var params = {cartoonid:cartoonid};
                                loadcartoonseriesList(params);
                                $("#cartoonseriesForm").form('clear');
                                closeDialog("cartoonseriesInfoDialog");
                                dosearch();
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
                    $("#cartoonseriesForm").form('clear');
                    closeDialog("cartoonseriesInfoDialog");
                }
            }
        ]
    });

    $("#doSearch").click(function () {
        dosearch();
    });

    $("#toAdd").click(function () {
        $("#cartoonForm").form('clear');
        $("#cartoonForm")[0].reset();
        $("#cartoonId").val(0);

        $("#result_img").attr('src', "");
        $("#result_img").hide();
        $("#resultimg-div").hide();
        $("#imgFile-pp").show();
        $("#cartoonpic").attr('value', "");
        $("#upload-div").show();

        // $("#result_img").hide();
        // $("#imgFile").show();
        // $("#cartoonpic").removeAttr('value');
        // $("#show_img").unbind('mouseover');
        // $("#show_img").unbind('mouseout');

        $("#cartoonDialog").dialog('open');
    });

    $("#toAdd01").click(function () {
        $("#cartoonseriesForm").form('clear');
        $("#cartoonseriesForm")[0].reset();
        $("#cartoonseriesId").val("");
        $("#cartoonId4series").val($("#cartoonId4seriesbase").val());
        $("#result_img01").attr('src', "");
        $("#result_img01").hide();
        $("#resultimg-div01").hide();
        $("#seriespic").attr('value', "");
        $("#upload-div01").show();

        var rows=$('#cartoonseriesList').datagrid("getRows");
        var seriesnum = 1;
        if(rows.length > 0){
            seriesnum = parseInt(rows[0].seriesnum) + 1;
        }
        $("#seriesnum").val(seriesnum);

        $("#cartoonseriesInfoDialog").dialog('open');
    });

    $("#toEdit").click(function () {
        $("#cartoonForm").form('clear');
        var selectRows = $("#cartoonList").datagrid('getSelections');
        if(selectRows.length > 1){
            alert("只能编辑一条数据!");
            return;
        }
        if(selectRows.length < 1){
            alert("请选择一条数据!");
            return;
        }
        loadDataToForm(selectRows[0]);
        $("#cartoonDialog").dialog('open');
    });

    $("#toEdit01").click(function () {
        $("#cartoonseriesForm").form('clear');
        var selectRows = $("#cartoonseriesList").datagrid('getSelections');
        if(selectRows.length > 1){
            alert("只能编辑一条数据!");
            return;
        }
        if(selectRows.length < 1){
            alert("请选择一条数据!");
            return;
        }
        loadDataToForm01(selectRows[0]);
        $("#cartoonseriesInfoDialog").dialog('open');
    });

    $("#toDel").click(function () {
        var selectRows = $("#cartoonList").datagrid('getSelections');
        if(selectRows.length < 1){
            alert("请至少选择一条数据!");
            return;
        }
        var selectIds = "";
        var selectNames = [];
        for(var i=0;i<selectRows.length;i++){
            var ii = selectRows[i];
            selectIds += "," + ii.id;
            selectNames.push(ii.cartoonname);

        }
        selectIds = selectIds.substring(1);
        $.messager.confirm('提示','确定要删除(' + selectNames + ')  吗?',function(r){
            if (r){
                $.ajax({
                    type:'post',
                    url: projectUrl + "/consoles/deleteCartoon",
                    // async:false,
                    dataType:'json',
                    data:{ids:selectIds},
                    success:function (data) {
                        $.messager.alert('提示',data.msg);
                        dosearch();
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

    $("#toDel01").click(function () {
        var selectRows = $("#cartoonseriesList").datagrid('getSelections');
        if(selectRows.length < 1){
            alert("请至少选择一条数据!");
            return;
        }
        var selectIds = "";
        var selectNames = [];
        for(var i=0;i<selectRows.length;i++){
            var ii = selectRows[i];
            selectIds += "," + ii.id;
            selectNames.push(ii.seriestitle);

        }
        selectIds = selectIds.substring(1);
        var cartoonid=$("#cartoonId4seriesbase").val();
        $.messager.confirm('提示','确定要删除(' + selectNames + ')  吗?',function(r){
            if (r){
                $.ajax({
                    type:'post',
                    url: projectUrl + "/consoles/deleteseries",
                    // async:false,
                    dataType:'json',
                    data:{ids:selectIds,cartoonid:cartoonid},
                    success:function (data) {
                        $.messager.alert('提示',data.msg);
                        var params = {cartoonid:cartoonid};
                        loadcartoonseriesList(params);
                        $("#cartoonseriesForm").form('clear');
                        dosearch();
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
    loadcartoonList(params);

    $("#deleteImg").click(function () {
        var cartoonpic = $("#cartoonpic").val();
        if($.trim(cartoonpic).length <= 0){
            $.messager.alert('提示',"没有图片可以删除");
            return;
        }
        $.messager.confirm("提示", "确定要删除该图片吗？",function () {
            $.ajax({
                type 		: "POST",
                dataType 	: "json",
                url 		: projectUrl + "/upload/deleteimg",
                data		: {cartoonpic : cartoonpic,tablename:'cartooninfo',columnname:'cartoonpic'},
                success		: function( result ) {
                    if(result.code){
                        $.messager.alert("提示", "删除成功");
                        $("#result_img").attr('src', "");
                        $("#cenopath").val("");
                        $("#resultimg-div").hide();
                        $("#upload-div").show();

                    }else{
                        $.messager.alert("提示", "网络请求出错,请联系管理员");
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

});

function dosearch() {
    var params = {};
    params.cartoonname = $("#cartoonname4Search").val();
    params.cartoontypedesc = $("#cartoontype4Search").val();
    loadcartoonList(params);
}

function loadcartoonList(params){
    var dataList = getData("/consoles/cartoonList",params).dataList;
    // dataList = formatDataList(dataList);
    $("#cartoonList").datagrid({
        data:dataList,
        loadMsg:"加载中...",
        selectOnCheck:true,
        singleSelect:false,
        nowrap: true,
        columns:[[
            {field:"ck",checkbox:"true"},
            {field:"id",title:"动漫Id",width:"80",hidden:true},
            {field:"cartoonname",title:"动漫名称",width:"200"},
                // ,formatter: function(value,row,index){
                //     return "<a href=\"javascript:void 0;\" onclick='' title='" + value + "'>" + value +" </a>";
                // }},
            {field:"cartoonpic",title:"展示图",width:"150",
                formatter: function(value,row,index){
                    return "<img src=\"" + value + "\" width=\"100px\" height=\"50px\" />";// onclick=\"viewpic('" + value + "')\"
                }},
            {field:"cartoontypedesc",title:"类型",width:"120"},
            {field:"cartoonauthor",title:"作者",width:"80"},
            {field:"cartoonduration",title:"时长",width:"80"},
            {field:"cartoonlangue",title:"语言",width:"80"},
            {field:"cartoonseriesnum",title:"总集数",width:"80",
                formatter: function(value,row,index){
                    var html = "<a href=\"javascript:void 0\" onclick=\"viewseries('" + row.id + "')\" title='" + value + "'>" + value + " </a>";
                    return html;
                }},
            {field:"playtimes",title:"播放次数",width:"80"},
            {field:"cartooninfo",title:"简介",width:"250",
                formatter: function(value,row,index){
                    if($.trim(value).length > 0){
                        return '<span title='+ value + '>'+value+'</span>';
                    }
                    return '';
                }}
            //     ,
            // {field:"export",title:"操作",width:"300",
            //     formatter: function(value,row,index){
            //         var html = "<a href=\"" + projectUrl + "/output/exportfamily?familyId=" + row.id + "&familyname=" + row.familyName + "\" title='" + value + "' target='_blank'>导出 </a>";
            //         html += "&nbsp;&nbsp;<a href=\"javascript:void 0;\" onclick=\"loadTab('','族谱合并','" + projectUrl + "/consoles/familyJoint?familyId=" + row.id + "&familyname=" + row.familyName + "')\" title='" + value + "'>合并 </a>";
            //         return html;
            //     }}
        ]],
        loadFilter:pagerFilter
    });
}

function closeDialog(dialogId){
    $("#" + dialogId).dialog("close");
}

function loadDataToForm(data) {

    $("#cartoonId").val(data.id);
    $("#cartoonname").val(data.cartoonname);
    $("#cartoonauthor").val(data.cartoonauthor);
    $("#cartoonlangue").val(data.cartoonlangue);
    $("#cartoonarea").val(data.cartoonarea);
    $("#cartoonduration").val(data.cartoonduration);
    $("#cartoondub").val(data.cartoondub);
    $("#cartoonversion").val(data.cartoonversion);
    $("#cartoonurl").val(data.cartoonurl);
    $("#cartooninfo").val(data.cartooninfo);
    $("#cartoonpic").val(data.cartoonpic);

    var imgPath = data.cartoonpic;
    $("#result_img").attr('src', projectUrl + imgPath);
    $("#result_img").show();
    $("#resultimg-div").show();
    $("#cartoonpic").attr('value', projectUrl + imgPath);
    $("#upload-div").hide();

    var cartoontypedesc = data.cartoontypedesc;

    var chkcartoontype = $("input[name='cartoontype']");
    for(var i=0;i<chkcartoontype.length;i++){
        var ii = chkcartoontype[i];
        $(ii).prop("checked",false);
        var typedescstr = $(ii).attr("data-typename");
        if(cartoontypedesc.indexOf(typedescstr) >= 0){
            $(ii).prop("checked",true);
        }

    }
}

function loadDataToForm01(data) {
    $("#cartoonseriesId").val(data.id);
    $("#cartoonId4series").val(data.cartoonid);
    $("#seriestitle").val(data.seriestitle);
    $("#seriesnum").val(data.seriesnum);
    $("#seriesduration").val(data.seriesduration);
    $("#seriesurl").val(data.seriesurl);
    $("#seriesinfo").val(data.seriesinfo);
    $("#seriespic").val(data.seriespic);

    var imgPath = data.seriespic;
    $("#result_img01").attr('src', projectUrl + imgPath);
    $("#result_img01").show();
    $("#resultimg-div01").show();
    $("#seriespic").attr('value', projectUrl + imgPath);
    $("#upload-div01").hide();
}

function loadTab(tabId,tabTitle,tabUrl) {
    parent.loadTab(tabId,tabTitle,tabUrl);
}

function viewseries(id) {
    var params = {cartoonid:id};
    loadcartoonseriesList(params);
    $("#cartoonseriesListDialog").dialog('open');
    $("#cartoonId4seriesbase").val(id);
}

function loadcartoonseriesList(params){
    var dataList = getData("/consoles/serieslist",params);
    $("#cartoonseriesList").datagrid({
        data:dataList,
        loadMsg:"加载中...",
        selectOnCheck:true,
        singleSelect:false,
        nowrap: true,
        columns:[[
            {field:"ck",checkbox:"true"},
            {field:"id",title:"id",width:"80",hidden:true},
            {field:"seriesnum",title:"剧集",width:"200"
                ,formatter: function(value,row,index){
                    if($.trim(value).length > 0){
                        return "第" + value + "集";
                    }
                    return "";
            }},
            {field:"seriestitle",title:"标题",width:"80"},
            {field:"seriespic",title:"展示图",width:"150",
                formatter: function(value,row,index){
                    return "<img src=\"" + value + "\" width=\"100px\" height=\"50px\" />";// onclick=\"viewpic('" + value + "')\"
                }},
            {field:"seriesduration",title:"时长",width:"80"},
            {field:"seriesurl",title:"链接",width:"150"},
            {field:"playtimes",title:"播放次数",width:"80"},
            {field:"seriesinfo",title:"简介",width:"180",
                formatter: function(value,row,index){
                    if($.trim(value).length > 0){
                        if($.trim(value).length > 20){
                            return '<span title='+ value + '>'+value.substring(0,20)+'...</span>';
                        }
                        return '<span title='+ value + '>'+value+'</span>';
                    }
                    return '';
                }}
        ]],
        loadFilter:pagerFilter
    });
}