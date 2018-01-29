<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=${encoding}" />
    <title>Удаление меломана</title>
</head>
<body class="delete">
    <c:if test="${auth != null}">
        <div>Вы вошли как: ${auth.login}</div>
    </c:if>
    <h1>Удаление меломана</h1>
    <section>
        <a class="ref ref-logout" href="${refLogin}?auth=logout">Выйти</a>
        <c:choose>
            <c:when test="${user != null}">
        <table>
            <tr>
                <th>ИД</th>
                <th>Логин</th>
                <th>Роль</th>
                <th>Страна</th>
                <th>Город</th>
                <th>Адрес</th>
                <th>Муз. стили</th>
            </tr>
            <tr>
                <td>${user.id}</td>
                <td>${user.login}</td>
                <td>${user.roleName}</td>
                <td>${user.country}</td>
                <td>${user.city}</td>
                <td>${user.addr}</td>
                <td>${user.musicTypeNamesAsString}</td>
            </tr>
        </table>
        <form class="form" method="POST" action="${action}">
            <ul class="form-list">
                <li class="form-item">
                    <input class="form-submit" type="submit" value="Удалить" />
                </li>
            </ul>
            <input type="hidden" name="id" value="${user.id}" />
        </form>
            </c:when>
            <c:otherwise>
        <p class="message">${message}</p>
            </c:otherwise>
        </c:choose>
        <a class="ref ref-back" href="${refBack}">Вернуться назад</a>
    </section>
</body>
</html>