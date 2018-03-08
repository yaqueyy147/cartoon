<%@ page import="java.util.Date" %>
<%@ page import="com.ljy.cartoon.util.CommonUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: suyx
  Date: 2017/1/11
  Time: 下午9:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>族谱</title>
    <%--<link rel="stylesheet" href="<%=request.getContextPath()%>/static/uploadify/uploadify.css" />--%>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/consoles/cartoonList.css" />
    <%@include file="common/commonCss.jsp"%>
</head>
<body>

<table id="cartoonList" class="easyui-datagrid" style="width:100%;height:98%"
       title="动漫列表" toolbar="#tb" data-options="
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				pageSize:10">
</table>
<div id="tb">
    <span>动漫名:</span>
    <input id="cartoonname4Search" name="cartoonname" style="line-height:26px;border:1px solid #ccc;height: 23px;">
    <span>动漫类型:</span>
    <input id="cartoontype4Search" name="cartoontype" style="line-height:26px;border:1px solid #ccc;height: 23px;">
    <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="doSearch">查询</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="toAdd">添加</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="toEdit" >编辑</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="toDel" >删除</a>
</div>

<div id="cartoonDialog" class="easyui-dialog" title="动漫信息" style="width:400px;height:200px;padding:10px;top: 0;left: 20%;">
    <div style="padding:10px 40px 20px 40px">
        <form id="cartoonForm" method="post">
            <input type="hidden" id="cartoonId" name="id" value="0" />
            <table cellpadding="5" style="width: 100%">
                <tr>
                    <td>动漫名/标题:</td>
                    <td><input type="text" id="cartoonname" name="cartoonname" data-options="required:true" /></td>
                    <td>作者:</td>
                    <td><input type="text" id="cartoonauthor" name="cartoonauthor" /></td>
                </tr>
                <tr>
                    <td>动漫类型:</td>
                    <td colspan="3">
                        <c:forEach var="type" items="${typelist}">
                            <label><input type="checkbox" name="cartoontype" data-typeid="${type.id}" data-typename="${type.cartoontype}"/>&nbsp;${type.cartoontype}</label>&nbsp;
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td>语言:</td>
                    <td><input type="text" id="cartoonlangue" name="cartoonlangue" /></td>
                    <td>属地:</td>
                    <td><input type="text" id="cartoonarea" name="cartoonarea" /></td>
                </tr>
                <tr>
                    <td>配音:</td>
                    <td><input type="text" id="cartoondub" name="cartoondub" /></td>
                    <td>版本:</td>
                    <td><input type="text" id="cartoonversion" name="cartoonversion" /></td>
                </tr>
                <tr>
                    <td>时长:</td>
                    <td><input type="text" id="cartoonduration" name="cartoonduration" placeholder="xx分xx秒" /></td>
                </tr>
                <tr>
                    <td>链接:</td>
                    <td colspan="3">
                        <input type="text" id="cartoonurl" name="cartoonurl" style="width:420px;" />
                    </td>
                </tr>
                <tr>
                    <td>简介:</td>
                    <td colspan="3">
                        <textarea class="text-area" id="cartooninfo" name="cartooninfo" rows="5" cols="57"></textarea>
                    </td>
                </tr>
                <tr>
                    <td>展示图片:</td>
                    <td colspan="3">
                        <div class="touxiang">
                            <input id="cartoonpic" name="cartoonpic" type="hidden"/>
                            <div class="photoID" id="resultimg-div" style="display: none;">
                                <label class="Mgetphoto">
                                    <img id="result_img" src="" width="260" height="160">
                                    <em><img src="/static/images/delete.png" id="deleteImg">删除照片 </em> </label>
                            </div>
                            <div class="photoID" id="upload-div">
                                <label class="Mupdata"><span>点击上传</span>
                                    <input type="file" name="uploadFile" id="imgFile" onchange="uploadPP(this,'result_img','resultimg-div','upload-div','cartoonpic','/upload/cartoonimg')" accept="image/png,image/jpeg" />
                                </label>
                            </div>
                        </div>
                        <%--<div id="progress_bar" style="display: none"></div>--%>
                        <%--<input id="cartoonpic" name="cartoonpic" type="hidden" />--%>
                        <%--<div class="row">--%>
                            <%--<div style="width: 150px">--%>
                                <%--<input type="file" name="imgFile" id="imgFile" />--%>
                                <%--<a id="show_img"><img style="display: none;width: 150px;height: 150px;" id="result_img" /></a>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    </td>
                </tr>

            </table>
        </form>
    </div>
</div>
<div id="cartoonseriesListDialog" class="easyui-dialog" title="动漫剧集列表" style="width:400px;height:200px;padding:10px;top: 0;left: 20%;">
    <input type="hidden" id="cartoonId4seriesbase" name="cartoonid" value="0" />
    <table id="cartoonseriesList" class="easyui-datagrid" style="width:100%;height:98%"
           title="动漫剧集列表" toolbar="#tb01" data-options="
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				pageSize:10">
    </table>
</div>
<div id="tb01">
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="toAdd01">添加</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="toEdit01" >编辑</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="toDel01" >删除</a>
</div>

<div id="cartoonseriesInfoDialog" class="easyui-dialog" title="动漫剧集信息" style="width:400px;height:200px;padding:10px;top: 0;left: 20%;">
    <div style="padding:10px 40px 20px 40px">
        <form id="cartoonseriesForm" method="post">
            <input type="hidden" id="cartoonseriesId" name="id" value="0" />
            <input type="hidden" id="cartoonId4series" name="cartoonid" value="0" />
            <table cellpadding="5" style="width: 100%">
                <tr>
                    <td>剧集标题:</td>
                    <td><input type="text" id="seriestitle" name="seriestitle" data-options="required:true" /></td>
                    <td>集数:</td>
                    <td><input type="text" id="seriesnum" name="seriesnum" readonly /></td>
                </tr>
                <tr>
                    <td>时长:</td>
                    <td><input type="text" id="seriesduration" name="seriesduration" placeholder="xx分xx秒" /></td>
                </tr>
                <tr>
                    <td>链接:</td>
                    <td colspan="3">
                        <input type="text" id="seriesurl" name="seriesurl" style="width:420px;" />
                    </td>
                </tr>
                <tr>
                    <td>简介:</td>
                    <td colspan="3">
                        <textarea class="text-area" id="seriesinfo" name="seriesinfo" rows="5" cols="57"></textarea>
                    </td>
                </tr>
                <tr>
                    <td>展示图片:</td>
                    <td colspan="3">
                        <div class="touxiang">
                            <input id="seriespic" name="seriespic" type="hidden"/>
                            <div class="photoID" id="resultimg-div01" style="display: none;">
                                <label class="Mgetphoto">
                                    <img id="result_img01" src="" width="260" height="160">
                                    <em><img src="/static/images/delete.png" id="deleteImg01">删除照片 </em> </label>
                            </div>
                            <div class="photoID" id="upload-div01">
                                <label class="Mupdata"><span>点击上传</span>
                                    <input type="file" name="uploadFile" id="imgFile01" onchange="uploadPP(this,'result_img01','resultimg-div01','upload-div01','seriespic','/upload/cartoonseriesimg')" accept="image/png,image/jpeg" />
                                </label>
                            </div>
                        </div>
                    </td>
                </tr>

            </table>
        </form>
    </div>
</div>
<%@include file="common/springUrl.jsp"%>
<%@include file="common/commonJs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.data.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/distpicker.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/consoleJs/cartoonList.js"></script>
<script>
    $(function () {
//        setTimeout(function() {
//            $('#imgFile').uploadify({
//                'swf': projectUrl + '/static/uploadify/uploadify.swf',
//                'uploader': projectUrl + '/upload/uploadImg',
//                'cancelImg': projectUrl + '/static/uploadify/cancel.png',
//                'auto': true,
//                "formData": {targetFile: '/upload/cartoonimg'},
//                'queueID': 'progress_bar',
//                'fileObjName': 'uploadFile',
//                "buttonCursor": "hand",
//                "buttonText": "选择图片",
//                'fileDesc': '支持格式:jpg,jpeg,gif,png,bmp', //如果配置了以下的'fileExt'属性，那么这个属性是必须的
//                'fileExt': '*.jpg;*.jpeg;*.gif;*.png;*.bmp',//允许的格式
//                'onUploadSuccess': function (file, data, response) {
//                    var result = eval('(' + data + ')');
//                    var imgPath = result.filePath;
//                    $("#result_img").attr('src', imgPath);
//                    $("#result_img").show();
//                    $("#imgFile").hide();
//                    $("#cartoonpic").attr('value', imgPath);
//                    $("#show_img").mouseover(function () {
//                        $("#result_img").attr('src', projectUrl + "/static/images/deleteImg.png");
//                    });
//                    $("#show_img").mouseout(function () {
//                        $("#result_img").attr('src', imgPath);
//                    });
//                    $("#result_img").click(function () {
//                        $("#result_img").hide();
//                        $("#imgFile").show();
//                        $("#cartoonpic").removeAttr('value');
//                        $("#show_img").unbind('mouseover');
//                        $("#show_img").unbind('mouseout');
//
//                    });
//                },
//                onUploadError: function (file, errorCode, errorMsg, errorString) {
//                    alert("error-->" + errorString);
//                }
//            });
//        },10);
    });

    function uploadPP(obj,resultimg,resultimgdiv,uploaddiv,picinput,targetFile) {
        var filexx = $(obj).val();
        var suffix = filexx.substring(filexx.lastIndexOf("."));
        if(suffix != ".jpg" && suffix != ".png" && suffix != ".jpeg"){
            $.messager.alert('提示',"请上传正确的jpg或者png格式的图片!");
            return;
        }

        var fileSize = obj.files[0].size;
        if(fileSize > 1 * 1024 * 1024){
            $.messager.alert('提示',"请将图片大小限制在1M以内!");
            return;
        }

        var fileElementId = $(obj).attr("id");

        $.ajaxFileUpload
        (
            {
                url: projectUrl + '/upload/uploadImg?jsonCallBack=?', //用于文件上传的服务器端请求地址
                type: 'post',
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: fileElementId, //文件上传域的ID
                dataType: 'JSON', //返回值类型 一般设置为json
                data:{targetFile: targetFile},
                success: function (data, status)  //服务器成功响应处理函数
                {
                    data = eval('(' + data + ')');
                    if(data.code == 1){
                        var imgPath = data.filePath;
                        $("#" + resultimg).attr('src', projectUrl + imgPath);
                        $("#" + resultimg).show();
                        $("#" + resultimgdiv).show();
                        $("#" + picinput).attr('value', projectUrl + imgPath);
                        $("#" + uploaddiv).hide();
                        $.messager.alert('提示',"上传成功!");
                    }else{
                        $.messager.alert('提示',"上传失败!");
                    }
                },
                error: function (data, status, e)//服务器响应失败处理函数
                {
                    alert(e);
                }
            }
        )
    }
</script>
</body>
</html>
