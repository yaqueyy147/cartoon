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
<body class="playbody">
<div class="loading">
    <div>查询中,请稍后...</div>
</div>
<%@include file="common/header.jsp" %>
<div class="container" style="margin-top: 50px;margin-bottom: 50px">
    <c:choose>
        <c:when test="${not empty cartooninfo}">
            <video controls="controls" width="100%" height="800">
                <source src="${cartooninfo.cartoonurl}"/>
                浏览器不支持
            </video>
        </c:when>
        <c:when test="${not empty cartoonseries}">
            <video controls="controls" width="100%" height="800">
                <source src="${cartoonseries.seriesurl}"/>
                浏览器不支持
            </video>
        </c:when>
        <c:otherwise>
            没有视频
        </c:otherwise>
    </c:choose>
    <%--<video controls="controls" width="100%" height="800">--%>
        <%--<source src="${cartoonseries.seriesurl}"/>--%>
        <%--浏览器不支持--%>
    <%--</video>--%>
</div>

<%@include file="common/springUrl.jsp"%>
<%@include file="common/footer.jsp" %>
<%@include file="common/commonJS.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/frontJs/index.js"></script>
</body>
</html>
