<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=${encoding}" />
    <c:choose>
        <c:when test="${auth != null}">
    <title>${auth.name}</title>
        </c:when>
        <c:otherwise>
    <title>${title}</title>
        </c:otherwise>
    </c:choose>
</head>
<body>
    <c:if test="${auth != null}">
    <div>${auth}</div>
    </c:if>
    <c:choose>
        <c:when test="${auth != null}">
    <h1>${title}</h1>
        </c:when>
        <c:otherwise>
    <h1>${title}</h1>
        </c:otherwise>
    </c:choose>
	<p>${message}</p>
    <a href="${refBack}">Вернуться назад</a>
    <br />
    <a href="${refMain}">На главную</a>
</body>
</html>