<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome to contento3 - CMS</title>
</head>
<body>
<div align="center">
<form action="/cms/j_spring_security_check" method="post">
<table style="height:250px;">
<tr>
<td colspan="2">

</td>
</tr>
</table>
<table>
<tr>
<td>
User
</td>
<td>
<input name="j_username" value="guest">
</td>
</tr>
<tr>
<td>
Password
</td>
<td>
<input type="password" value="" name="j_password"/>
</td>
</tr>
<tr>
<td>
<input type="submit" value="login">
<td>
</tr>
</table>
</form>
</div>
</body>
</html>