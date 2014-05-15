<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Address Book</title>
</head>

<body>
<h1>Address Book</h1>
<%	String login = (String)session.getAttribute("auth");
 	if(login!=null){%>
 		<form action="<%=request.getContextPath()%>/logout">
 			<span>Login as:</span><span style="green"><%= login%></span>
 			<input type="submit" value="Logout">
 		</form>
 	<%}else{%>
 		<a href="<%=request.getContextPath()%>/auth">Login</a>
 	<%}%>

<span style="color:green;"><%=(request!=null & request.getAttribute("message")!=null)? request.getAttribute("message"):"" %></span><br/>

<%if(login!=null){ %>
	<a href="<%=request.getContextPath()%>/add">Add new Contact</a>
	<%} %>
<a href="<%=request.getContextPath()%>/view">View List</a>
</body>
</html>