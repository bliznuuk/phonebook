<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Address Book</title>
</head>

<body>
<h1>Contact List</h1>
<%	String login = (String)session.getAttribute("auth");%>

<a href="<%=request.getContextPath()%>">Main Page</a><br/>

<span style="color:green;"><%=(request!=null & request.getAttribute("message")!=null)? request.getAttribute("message"):"" %></span><br/>

<table border="1">
<tr><td width="100">Name</td><td width="100">Number</td><td width="100">Comment</td><%if(login!=null){ %><td></td><%}%></tr>
<%	Map<String, String> numbers = (Map<String, String>)request.getAttribute("numbers");
	Map<String, String> comments = (Map<String, String>)request.getAttribute("comments");

	for(Object entry : numbers.entrySet()){
		String name = (String)((Map.Entry)entry).getKey();
		String number = (String)numbers.get(name);
		String comment = (String)comments.get(name);
		%>
		<tr>
			<td class="name"><%=name%></td>
			<td class="number"><%=number%></td>
			<td class="comment"><%=comment%></td>
			<%if(login!=null){ %>
				<td class="edit"><a href="<%=request.getContextPath()%>/remove?name=<%=name%>">Delete</a>
					<a href="<%=request.getContextPath()%>/edit?name=<%=name%>">Edit</a></td>
			<%}%>
		</tr>
<%	}
%>
</table>

</body>
</html>