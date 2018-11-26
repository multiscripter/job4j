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
    <title>Редактирование пользователя</title>
</head>
<body>
	<h1>Редактирование пользователя</h1>
    <% if (user != null) { %>
    <form method='POST' action='<%=String.format("%s://%s:%s%s%s", req.getScheme(), req.getServerName(), req.getServerPort(), req.getContextPath(), req.getServletPath()) %>'>
        <table>
            <tr>
                <td>
                    <label>Иия:<br />
                        <input type='text' name='name' required='required' value='<%=user.getName() %>' />
                    </label>
                </td>
            </tr>
            <tr>
                <td>
                    <label>Логин:<br />
                        <input type='text' name='login' required='required' value='<%=user.getLogin() %>' />
                    </label>
                </td>
            </tr>
            <tr>
                <td>
                    <label>Емэил:<br />
                        <input type='email' name='email' required='required' value='<%=user.getEmail() %>' />
                    </label>
                </td>
            </tr>
            <tr>
                <td>
                    <input type='submit' value='Отредактировать' />
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