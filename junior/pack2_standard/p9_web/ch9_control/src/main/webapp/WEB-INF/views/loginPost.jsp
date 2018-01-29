<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=${encoding}" />
    <c:choose>
        <c:when test="${auth != null}">
    <title>${auth.login}</title>
        </c:when>
        <c:otherwise>
    <title>${title}</title>
        </c:otherwise>
    </c:choose>
</head>
<body class="login">
    <c:choose>
        <c:when test="${auth != null}">
    <h1>${title}</h1>
    <section>
        </c:when>
        <c:otherwise>
    <h1>${title}</h1>
    <section>
        </c:otherwise>
    </c:choose>
    <c:if test="${auth != null}">
        <a class="ref ref-logout" href="${refLogin}?auth=logout">Выйти</a>
    </c:if>
        <p class="message">${message}</p>
        <c:if test="${auth == null}">
        <a class="ref ref-back" href="${refBack}">Вернуться назад</a>
        <br /><br />
        </c:if>
        <a class="ref ref-home" href="${refHome}">Вернуться на Главную</a>
    </section>
</body>
</html>