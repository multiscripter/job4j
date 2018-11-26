<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=${encoding}" />
    <title>Удаление пользователя</title>
    <jsp:include page="head.jsp" />
</head>
<body class=""delete>
    <c:if test="${auth != null}">
    <div>Вы вошли как: ${auth.name}</div>
    </c:if>
	<h1>Удаление пользователя</h1>
    <section>
        <a class="ref ref-logout" href="${refLogin}?auth=logout">Выйти</a>
        <p class="message">${message}</p>
        <a class="ref ref-home" href="${refHome}">Вернуться на Главную</a>
    </section>
</body>
</html>