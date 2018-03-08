<%--
  Created by IntelliJ IDEA.
  User: suyx
  Date: 2016/12/18
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>世纪动漫</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link href="<%=request.getContextPath()%>/static/css/fronts/detail.css" rel="stylesheet" type="text/css" />
    <%@include file="common/commonCss.jsp"%>
    <style>
        .loading{
            z-index: 8888;
            width: 100%;
            height: 100%;
            background-color: #999999;
            opacity: 0.5;
            text-align: center;
            position: fixed;
            margin-top: -50px;
            display: none;
        }
        .loading div{
            z-index: 9999;
            width: 200px;
            height:200px;
            margin-left: auto;
            margin-right: auto;
            margin-top: 10%;
            color: #ff0000;
            font-size: 16px;
        }
    </style>
</head>
<body>
<div class="loading">
    <div>查询中,请稍后...</div>
</div>
<%@include file="common/header.jsp" %>
<div class="container" style="margin-top: 50px;margin-bottom: 50px">
    <div class="cartooninfo">
        <div class="cartoonimg">
            <a href="${cartooninfo.cartoonurl}" style="float: none;width: 100%;" target="_blank">
                <img src="${cartooninfo.cartoonpic}"/>
            </a>
        </div>
        <div>
            <div class="cartoonname">
                <span class="name01">${cartooninfo.cartoonname}</span>
                <span class="name02">${cartooninfo.cartoonyear}</span>
            </div>
            <div>
                <table>
                    <tr>
                        <td class="td01">地区：</td>
                        <td class="td02">${cartooninfo.cartoonarea}</td>
                        <td class="td01">类型：</td>
                        <td class="td02">${cartooninfo.cartoontypedesc}</td>
                    </tr>
                    <tr>
                        <td class="td01">语言：</td>
                        <td class="td02">${cartooninfo.cartoonlangue}</td>
                        <td class="td01">版本：</td>
                        <td class="td02">${cartooninfo.cartoonversion}</td>
                    </tr>
                    <tr>
                        <td class="td01">配音：</td>
                        <td class="td02">${cartooninfo.cartoondub}</td>
                        <td class="td01">集数：</td>
                        <td class="td02">更新至第${cartooninfo.cartoonseriesnum}集</td>
                    </tr>
                    <tr style="height: 20px"></tr>
                    <tr>
                        <td>简介：</td>
                        <td colspan="3">${cartooninfo.cartooninfo}</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <div class="gailan">
        <div class="gailantt">概览</div>
        <div class="cartoonseries row">
            <c:forEach var="series" items="${serieslist}">

                <div class="col-sm-3 col-md-2 familyDiv">
                    <div class="thumbnail">
                        <a href="${series.seriesurl}" style="float: none;width: 100%;" target="_blank">
                            <img class="familyImgFF" src="${series.seriespic}" class="img-thumbnail"/></a>
                        <div class="caption">
                            <h6 style="text-align: center">${series.seriestitle}</h6>
                        </div>
                    </div>
                </div>

            </c:forEach>
        </div>
    </div>
</div>

<%@include file="common/springUrl.jsp"%>
<%@include file="common/footer.jsp" %>
<%@include file="common/commonJS.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/frontJs/index.js"></script>
</body>
</html>
