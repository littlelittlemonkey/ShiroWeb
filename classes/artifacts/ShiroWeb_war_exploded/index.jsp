<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="${pageContext.request.contextPath }/user/login.do" method="post">
	userName:<input type="text" name="userName" value="${user.userName }"/><br/>
	password:<input type="password" name="password" value="${user.password }"><br/>
	<input type="submit" value="login"/><font color="red">${errorMsg }</font>
</form>
</body>
</html>