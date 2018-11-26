<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% 
HttpServletRequest req = request;
String enc = (String) req.getAttribute("encoding");
int id = (int) req.getAttribute("id");
boolean deleteUser = (boolean) req.getAttribute("deleteUser");
String str = "";
if (deleteUser) {
    str = String.format("<p>Пользователь id:%d удалён.</p><br />\n", id);
} else {
    str = String.format("<p>Ошибка при удалении пользователя id:%d.</p><br />\n", id);
}
%>
<!DOCTYPE html>
<html lang='ru' xmlns='http://www.w3.org/1999/xhtml'>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset="<%=enc %>"' />
    <title>Удаление пользователя</title>
</head>
<body>
	<h1>Удаление пользователя</h1>
    <%=str %>
    <%=String.format("<a href='%s://%s:%s%s/'>Вернуться назад</a>\n", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()) %>
</body>
</html>