<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=${encoding}" />
    <title>Удаление пользователя</title>
    <jsp:include page="head.jsp" />
</head>
<body class="delete">
    <c:if test="${auth != null}">
        <div>Вы вошли как: ${auth.name}</div>
    </c:if>
    <h1>Удаление пользователя</h1>
    <section>
        <a class="ref ref-logout" href="${refLogin}?auth=logout">Выйти</a>
        <c:choose>
            <c:when test="${user != null}">
        <table>
            <tr>
                <th>ИД</th>
                <th>Имя</th>
                <th>Логин</th>
                <th>Емэил</th>
                <th>Дата создания</th>
                <th>Страна</th>
                <th>Город</th>
            </tr>
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.login}</td>
                <td>${user.email}</td>
                <td>${user.dateStr}</td>
                <td>${user.countryName}</td>
                <td>${user.cityName}</td>
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