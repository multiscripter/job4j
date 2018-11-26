<%@ page import="java.util.LinkedList" %>
<%@ page import="ru.job4j.jsp.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% 
HttpServletRequest req = request;
String enc = (String) req.getAttribute("encoding");
LinkedList<User> users = (LinkedList<User>) req.getAttribute("users");
%>
<!DOCTYPE html>
<html lang='ru' xmlns='http://www.w3.org/1999/xhtml'>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset="<%=enc %>"' />
    <title>Хранилище пользователей</title>
</head>
<body>
    <h1>Хранилище пользователей</h1>
    <div>${encoding}</div>
    <div><%=enc %></div>
    <% if (users != null && users.size() > 0) { %>
    <table>
        <tr>
            <th>ИД</th>
            <th>Имя</th>
            <th>Логин</th>
            <th>Емэил</th>
            <th>Дата создания</th>
            <th></th>
            <th></th>
        </tr><% 
        for (User user : users) { %>
        <tr>
            <td><%=user.getId() %></td>
            <td><%=user.getName() %></td>
            <td><%=user.getLogin() %></td>
            <td><%=user.getEmail() %></td>
            <td><%=user.getDateStr() %></td>
            <td><%=String.format("<a href='%s://%s:%s%s/update/?id=%d'>редактировать</a>\n", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath(), user.getId()) %></td>
            <td><%=String.format("<a href='%s://%s:%s%s/delete/?id=%d'>удалить</a>\n", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath(), user.getId()) %></td>
        </tr><% 
        } %>
    </table><% 
    } else { %>
    <p>Никого нет</p><% 
    } %>
    <%=String.format("<a href='%s://%s:%s%s/create/'>Добавить нового пользователя</a>\n", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath()) %>
</body>
</html>
