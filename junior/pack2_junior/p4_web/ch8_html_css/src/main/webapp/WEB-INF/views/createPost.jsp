<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="${encoding}" />
    <title>Создание пользователя</title>
    <jsp:include page="head.jsp" />
</head>
<body class="create">
    <c:if test="${auth != null}">
    <div>Вы вошли как: ${auth.name}</div>
    </c:if>
    <h1>Создание пользователя</h1>
    <section>
        <a class="ref ref-logout" href="${refLogin}?auth=logout">Выйти</a>
        <p class="message">${message}</p>
        <a class="ref ref-back" href="${refBack}">Вернуться назад</a>
        <br /><br />
        <a class="ref ref-home" href="${refHome}">Вернуться на Главную</a>
    </section>
</body>
</html>