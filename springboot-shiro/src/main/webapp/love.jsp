<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${pageContext.request.contextPath}/bootstrap.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/bootstrap.min.js"></script>
<html lang="en" >
<head>
  <meta charset="UTF-8">
  <title>Cat Illustration with animation - Pure CSS🐈 </title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/catstyle.css">

</head>
<body>
<%--<script language="javascript">
  function logout () {
    $.ajax({
      type: "Post",
      url: "/logout",
      data: {  },
      success: function (data) {
        alert("注销成功");
      }
    });
  }
</script>--%>
<style>
.m
bottom{
position:fixed; 
width:100%;
height:50px;
bottom:0px; 
left:0px;
}
</style>
<!-- partial:index.partial.html -->
<div class="mbottom">
<center><h3>You will always be my friend, Miss Y. <small>--Kana</small></h3></center>
</div>
<div class="container">
  <div class="cat">
    <div class="face">
      <div class="ear-left">
        <div class="inner-l"></div>
      </div>
      <div class="ear-right">
        <div class="inner-r"></div>
      </div>
      <div class="eye-left">
        <div class="eye-ball">
          <div class="eye-light"></div>
          <div class="shadow-light"></div>
        </div>
      </div>
      <div class="eye-right">
        <div class="eye-ball">
          <div class="eye-light"></div>
          <div class="shadow-light"></div>
        </div>
      </div>
      <div class="nose">
        <div class="l1"></div>
        <div class="l2"></div>
      </div>
    </div>
    <div class="cat-body">
      <div class="paw1"></div>
      <div class="paw2"></div>
      <div class="tail"></div>

    </div>
  </div>
  <div class="heart">
    <div class="heart-l"></div>
    <div class="heart-r"></div>
  </div>
</div>

<!-- partial -->
<div class="pull-left">
<form action="${pageContext.request.contextPath}/logout" method="post">
  <button type="submit" class="btn btn-danger">注销</button>
</form>
</div>
</body>
</html>