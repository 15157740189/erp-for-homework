<!DOCTYPE html>
<script src="${pageContext.request.contextPath}/jquery.min.js"></script>
<link href="${pageContext.request.contextPath}/bootstrap.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/bootstrap.min.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%--<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">--%>
</head>
<div class="container" >
    <div class="carousel slide" id="slidershow" data-ride="carousel" data-interval="2000">
        <!--计数器-->
        <ol class="carousel-indicators">
            <li class="active" data-target="#slidershow" data-slide-to="0"></li>
            <li data-target="#slidershow" data-slide-to="1"></li>
            <li data-target="#slidershow" data-slide-to="2"></li>
        </ol>
        <!--图片容器-->
        <div class="carousel-inner">
            <div class="item active">
                <img src="${pageContext.request.contextPath}/01.jpg"/>
                <!--添加对应标题和内容-->
                <div class="carousel-caption">
                    <h4>图一</h4>
                    <span>内容</span>
                </div>
            </div>
            <div class="item">
                <img src="${pageContext.request.contextPath}/02.jpg"/>
                <!--添加对应标题和内容-->
                <div class="carousel-caption">
                    <h4>图二</h4>
                </div>
            </div>
            <div class="item">
                <img src="${pageContext.request.contextPath}/03.jpg"/>
                <!--添加对应标题和内容-->
                <div class="carousel-caption">
                    <h4>图三</h4>
                </div>
            </div>
        </div>
    </div>
</div>

<%--<div id="myCarousel" class="carousel slide">
    <!-- 轮播（Carousel）指标 -->
    <ol class="carousel-indicators">
        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
        <li data-target="#myCarousel" data-slide-to="1"></li>
        <li data-target="#myCarousel" data-slide-to="2"></li>
    </ol>
    <!-- 轮播（Carousel）项目 -->
    <div class="carousel-inner">
        <div class="item active">
            <img src="${pageContext.request.contextPath}/01.jpg"  height=200px alt="First slide">
            <div class="carousel-caption">first</div>
        </div>
        <div class="item">
            <img src="${pageContext.request.contextPath}/02.jpg"  height=200px alt="Second slide">
            <div class="carousel-caption">second</div>
        </div>
        <div class="item">
            <img src="${pageContext.request.contextPath}/03.jpg" height=200px alt="Third slide">
            <div class="carousel-caption">third</div>
        </div>
    </div>
    <!-- 轮播（Carousel）导航 -->
    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>--%>

<form action="${pageContext.request.contextPath}/form" method="get">
    <table width="1300px" border="3px" class="table table-hover">
        <caption>个人信息</caption>
        <thead>
        <tr>
        <th>姓名：</th>
        <th>位置：</th>
        </tr>
<c:forEach items="${User_data}" var="user">
        <tbody>
    <tr>
    <td>${user.userName}</td>
    <td>${user.password}</td>
    </tr>
    </tbody>
        </c:forEach>
    </table>
</form>

</body>
</html>
