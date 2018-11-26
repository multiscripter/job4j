<%@ page import="ru.job4j.jsp.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% 
HttpServletRequest req = request;
String enc = (String) req.getAttribute("encoding");
boolean editUser = (boolean) req.getAttribute("editUser");
User user = (User) req.getAttribute("user");
String str = "";
if (editUser) {
    str = String.format("<p>Пользователь id:%d отредактирован.</p><br />\n", user.getId());
} else {
    str = String.format("<p>Ошибка при редактировании пользователя id:%d.</p><br />\n", user.getId());
}
%>
<!DOCTYPE html>
<html lang='ru' xmlns='http://www.w3.org/1999/xhtml'>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset="<%=enc %>"' />
    <title>Редактирование пользователя</title>
</head>
<body>
	<h1>Редактирование пользователя</h1>
	<%=str %>
	<%=String.format("<a href='%s://%s:%s%s/'>Вернуться назад</a>\n", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()) %>
</body>
</html>