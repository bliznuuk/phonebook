<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Contact</title>
</head>

<body>
<h1>Edit Contact</h1><br/><br/>

<a href="<%=request.getContextPath()%>">Main Page</a><br/>

<form action="<%=request.getContextPath()%>/<%=request.getAttribute("action")%>" method="post">
	<input type="hidden" name="edited" value="<%=request.getAttribute("edit.name")%>"/>
	<table> <%!	private String getStr(Object attribute){
					return attribute!=null ? (String)attribute : "";
				}
				%>
		<tr><td>Name</td><td><input type="text" name="name" value="<%=getStr(request.getAttribute("edit.name"))%>"/></td></tr>
		<tr><td>Number</td><td><input type="text" name="number" value="<%=getStr(request.getAttribute("edit.number"))%>"/></td></tr>
		<tr><td>Comment</td><td><input type="text" name="comment" value="<%=getStr(request.getAttribute("edit.comment"))%>"/></td></tr>
		<tr><td colspan="2"><span style="color:green;"><%=(request!=null & request.getAttribute("message")!=null)? request.getAttribute("message"):"" %></span><br/>
		<tr><td colspan="2" align="center"><input type="submit" name="Send"/></td></tr>
	</table>
</form>

</body>
</html>