<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=${encoding}" />
    <title>Удаление пользователя</title>
</head>
<body>
	<h1>Удаление пользователя</h1>
    <c:choose>
        <c:when test="${user != null}">
        <table>
            <tr>
                <th>ИД</th>
                <th>Имя</th>
                <th>Логин</th>
                <th>Емэил</th>
                <th>Дата создания</th>
            </tr>
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.login}</td>
                <td>${user.email}</td>
                <td>${user.dateStr}</td>
            </tr>
        </table>
        <form method="POST" action="${action}">
            <table>
                <tr>
                    <td>
                        <input type="submit" value="Удалить" />
                    </td>
                </tr>
            </table>
            <input type="hidden" name="id" value="${user.id}" />
        </form>
        </c:when>
        <c:otherwise>
        <p>Нет такого пользователя</p>
        </c:otherwise>
    </c:choose>
    <a href="${refBack}">Вернуться назад</a>
</body>
</html>