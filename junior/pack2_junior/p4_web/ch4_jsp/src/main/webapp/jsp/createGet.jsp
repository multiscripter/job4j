<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% 
HttpServletRequest req = request;
String enc = (String) req.getAttribute("encoding");
%>
<!DOCTYPE html>
<html lang='ru' xmlns='http://www.w3.org/1999/xhtml'>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset="<%=enc %>"' />
    <title>Создание пользователя</title>
</head>
<body>
	<h1>Создание пользователя</h1>
	<form method='POST' action='<%=String.format("%s://%s:%s%s%s", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath(), req.getServletPath()) %>'>
		<table>
			<tr>
				<td>
					<label>Иия:<br />
						<input type='text' name='name' required='required' />
					</label>
				</td>
			</tr>
			<tr>
				<td>
					<label>Логин:<br />
						<input type='text' name='login' required='required' />
					</label>
				</td>
			</tr>
			<tr>
				<td>
					<label>Емэил:<br />
						<input type='email' name='email' required='required' />
					</label>
				</td>
			</tr>
			<tr>
				<td>
					<input type='submit' value='Создать' />
				</td>
			</tr>
		</table>
	<form>
	<%=String.format("<a href='%s://%s:%s%s/'>Вернуться назад</a>\n", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()) %>
</body>
</html>
