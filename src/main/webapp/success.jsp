<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Insert title here</title>
</head>
<body>
<%--${info }--%>
欢迎你!<br/>
<shiro:hasRole name="admin">
    欢迎有admin角色的用户！<shiro:principal/><br/>
</shiro:hasRole>
<shiro:hasPermission name="student:create">
    欢迎有student:create权限的用户！<shiro:principal/><br/>
</shiro:hasPermission>
</body>
</html>