<%@ page import="ru.job4j.jsp.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% 
HttpServletRequest req = request;
String enc = (String) req.getAttribute("encoding");
User user = (User) req.getAttribute("user");
%>
<!DOCTYPE html>
<html lang='ru' xmlns='http://www.w3.org/1999/xhtml'>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset="<%=enc %>"' />
    <title>Удаление пользователя</title>
</head>
<body>
	<h1>Удаление пользователя</h1>
    <% if (user != null) { %>
    <table>
        <tr>
            <th>ИД</th>
            <th>Имя</th>
            <th>Логин</th>
            <th>Емэил</th>
            <th>Дата создания</th>
        </tr>
        <tr>
            <td><%=user.getId() %></td>
            <td><%=user.getName() %></td>
            <td><%=user.getLogin() %></td>
            <td><%=user.getEmail() %></td>
            <td><%=user.getDateStr() %></td>
        </tr>
    </table>
    <form method='POST' action='<%=String.format("%s://%s:%s%s%s", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath(), req.getServletPath()) %>'>
        <table>
            <tr>
                <td>
                    <input type='submit' value='Удалить' />
                </td>
            </tr>
        </table>
        <input type='hidden' name='id' value='<%=user.getId() %>' />
    </form>
    <% } else { %>
    <p>Нет такого пользователя</p>
    <% } %>
    <%=String.format("<a href='%s://%s:%s%s/'>Вернуться назад</a>\n", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()) %>
</body>
</html>