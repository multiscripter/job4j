<%@ page import="ru.job4j.jsp.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% 
HttpServletRequest req = request;
String enc = (String) req.getAttribute("encoding");
boolean addUser = (boolean) req.getAttribute("addUser");
User user = (User) req.getAttribute("user");
String str = "";
if (addUser) {
	str = String.format("<p>Пользователь %s добавлен. ID: %s</p><br />\n", user.getName(), user.getId());
} else {
	str = String.format("<p>Ошибка при добавлении пользователя %s.</p><br />\n", user.getName());
}
%>
<!DOCTYPE html>
<html lang='ru' xmlns='http://www.w3.org/1999/xhtml'>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset="<%=enc %>"' />
    <title>Создание пользователя</title>
</head>
<body>
	<h1>Создание пользователя</h1>
	<%=str %>
	<%=String.format("<a href='%s://%s:%s%s/'>Вернуться назад</a>\n", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()) %>
</body>
</html>