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
                这里是bootstrap!
            </div>
        </div>
    </div>
</div>
<p class="text-center">
${session}
</p><br>
<div class="pull-left">
    用户名：<input type="text" userName="username" id="username"
               class="form-control"> <div class="show"><span id="nameTipMsg"  style="font-size: 12px">
            </span>
</div>
    密 码:<input type="password" userName="password" id="password" class="form-control"><br>
</div>
<div class="pull-left">
    管理员：<input type="text" userName="admin"  readonly="readonly" value="${mModel}"
               class="form-control">
    <div class="show"></div>
    地 址:<input type="text" readonly="readonly" userName="password"  value="${mRequest}" class="form-control"><br>

</div>
</body>
</html>
