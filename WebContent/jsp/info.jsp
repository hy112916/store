<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import= "com.itheima.domain.User"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>WEB01</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
	</head>
<script>
function readonly(){
	document.getElementById("uid").readOnly=true;
	document.getElementById("username").readOnly=true;
	document.getElementById("name").readOnly=true;
	document.getElementById("telephone").readOnly=true;
	document.getElementById("email").readOnly=true;
	document.getElementById("birthday").readOnly=true;
	document.getElementById("sex").readOnly=true;
	document.getElementById("money").readOnly=true;
}
function writeable(){
	document.getElementById("name").readOnly=false;
	document.getElementById("telephone").readOnly=false;
	document.getElementById("email").readOnly=false;
}
</script>
	<body>
		<div class="container-fluid">
			
			<%@include file="/jsp/head.jsp" %>
			<%
response.setContentType("text/html;charest=utf-8");
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
User user = (User)request.getSession().getAttribute("user");
int state = user.getState();
String uid = user.getUid();
String username = user.getUsername();
String name = user.getName();
String email = user.getEmail();
String telephone=user.getTelephone();
String birthday=user.getBirthday();
String sex=user.getSex();
Double money=user.getMoney();

%>
			<div>
				<hr/>
				<form action="${pageContext.request.contextPath }/user" id="infoForm" method="post" class="form-horizontal" style="margin-top:5px;margin-left:150px;">
					
					<input type="hidden" name="method" value="update">
					<div class="form-group">
						<label for="uid" class="col-sm-1 control-label">用户id</label>
						<div class="col-sm-5">
							<input type="text" name="uid" class="form-control" readonly="readonly" value=<%=uid%>>
						</div>
					</div>
					<div class="form-group">
						<label for="username" class="col-sm-1 control-label">用户名</label>
						<div class="col-sm-5">
							<input type="text" name="name" class="form-control" readonly="readonly" value=<%=username%>>
						</div>
					</div>
					<div class="form-group">
						<label for="name" class="col-sm-1 control-label">昵称</label>
						<div class="col-sm-5">
							<input type="text" name="name" class="form-control"  value=<%=name%>>
						</div>
					</div>
					<div class="form-group">
						<label for="email" class="col-sm-1 control-label">邮件地址</label>
						<div class="col-sm-5">
							<input type="text" name="email" class="form-control"  value=<%=email%>>
						</div>
					</div><div class="form-group">
						<label for="telephone" class="col-sm-1 control-label">电话</label>
						<div class="col-sm-5">
							<input type="text" name="telephone" class="form-control"  value=<%=telephone%>>
						</div>
					</div>
					<div class="form-group">
						<label for="birthday" class="col-sm-1 control-label">生日</label>
						<div class="col-sm-5">
							<input type="text" name="birthday" class="form-control"  readonly="readonly" value=<%=birthday%>>
						</div>
					</div>
					<div class="form-group">
						<label for="sex" class="col-sm-1 control-label">性别</label>
						<div class="col-sm-5">
							<input type="text" name="sex" class="form-control" readonly="readonly" value=<%=sex%>>
						</div>
					</div>
					<div class="form-group">
						<label for="money" class="col-sm-1 control-label">余额</label>
						<div class="col-sm-5">
							<input type="text" name="money" class="form-control" readonly="readonly" value=<%=money%>>
						</div>
					</div>
				
				<button type="button" id="editablebutton" onclick="writeable()">编辑</button>
                <button type="submit" id="keepupdatebutton" onclick="readonly()">保存修改</button>
			</div>
				</form>
		</div>
			<div class="container-fluid">
				<div style="margin-top:50px;">
					<img src="${pageContext.request.contextPath}/img/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
				</div>
		
				<div style="text-align: center;margin-top: 5px;">
					<ul class="list-inline">
						<li><a href="https://github.com/hy112916/store">Github项目</a></li>
						<li><a href="https://www.csdn.net/">CSDN</a></li>
					</ul>
				</div>
				<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
					Copyright &copy; 2019 颍君建安 版权所有
				</div>
			</div>
		</div>
	</body>

</html>