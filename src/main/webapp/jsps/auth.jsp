<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>


<body>
<a href="<%=request.getContextPath()%>">Main page</a><br/>
<span style="color:red;"><%=(request!=null & request.getAttribute("message")!=null)? request.getAttribute("message"):"" %></span><br/>

<form action="<%=request.getContextPath()%>/auth" method="post">
<table>
	<tr><td>Login</td><td><input type="text" name="login"/></td></tr>
	<tr><td>Password</td><td><input type="password" name="password"/></td></tr>
	<tr><td colspan="2" align="center"><input type="submit" value="Login"/></td></tr>
</table>
</form>

</body>
</html>