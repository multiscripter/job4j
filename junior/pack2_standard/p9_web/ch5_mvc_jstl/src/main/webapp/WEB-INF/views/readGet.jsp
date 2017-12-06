<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
HttpServletRequest req = request;
String enc = (String) req.getAttribute("encoding");
%>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=${encoding}" />
    <title>Хранилище пользователей</title>
</head>
<body>
    <h1>Хранилище пользователей</h1>
    <div>var1 <c:out value="${encoding}" /></div>
    <div>var2 <c:out value="${enc}" /></div>
    <div>var3 ${enc}</div>
    <div>var4 <%=enc %></div>
    <c:choose>
        <c:when test="${users != null && users.size() > 0}">
        <table>
            <tr>
                <th>ИД</th>
                <th>Имя</th>
                <th>Логин</th>
                <th>Емэил</th>
                <th>Дата создания</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach items="${users}" var="user">
            <tr>
                <td><c:out value="${user.id}" /></td>
                <td><c:out value="${user.name}" /></td>
                <td><c:out value="${user.login}" /></td>
                <td><c:out value="${user.email}" /></td>
                <td><c:out value="${user.dateStr}" /></td>
                <c:url value="${refUpdate}" var="refUpdateUrl">
                    <c:param name="id" value="${user.id}" />
                </c:url>
                <td><a href="${refUpdateUrl}">редактировать</a></td>
                <c:url value="${refDelete}" var="refDeleteUrl">
                    <c:param name="id" value="${user.id}" />
                </c:url>
                <td><a href="${refDeleteUrl}">удалить</a></td>
            </tr>
            </c:forEach>
        </table>
        </c:when>
        <c:otherwise>
        <p>Никого нет</p>
        </c:otherwise>
    </c:choose>
    <a href="${refCreate}">Добавить нового пользователя</a>
</body>
</html>
