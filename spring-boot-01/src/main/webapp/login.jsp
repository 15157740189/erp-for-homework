<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<script src="${pageContext.request.contextPath}/jquery.min.js"></script>
<link href="${pageContext.request.contextPath}/bootstrap.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/bootstrap.min.js"></script>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
</head>
<p>
    <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingOne">
                <h4 class="panel-title">
                    <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
<p class="bg-info">Hello!</p>
</a>
</h4>
</div>
<div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
    <div class="panel-body">
       欢迎登录！
    </div>
</div>
</div>
</div>
<div class="pull-left">
    <form action="${pageContext.request.contextPath}/login" method="post">
        <input type="text"  id="id"  value="1"
                   class="hidden"> <div class="show">
    用户名：<input type="text"  id="userName" name="userName"
               class="form-control"> <div class="show">
</div>
    密 码:<input type="password"  id="password" name="password" class="form-control"><br>
        <input type="checkbox" name="rememberMe"
                   > 记住我<div class="show">
           <button type="submit" class="btn btn-primary">登录</button>
    </form>
</div>

</body>
</html>
