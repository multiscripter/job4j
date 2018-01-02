<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=${encoding}" />
    <title>Хранилище пользователей</title>
    <jsp:include page="head.jsp" />
</head>
<body class="read">
    <c:if test="${auth != null}">
    <div>Вы вошли как: ${auth.name}</div>
    </c:if>
    <h1>Хранилище пользователей</h1>
    <c:choose>
        <c:when test="${auth == null}">
        <a href="${refLogin}">Войти</a>
        </c:when>
        <c:otherwise>
        <a class="ref ref-logout" href="${refLogin}?auth=logout">Выйти</a>
        </c:otherwise>
    </c:choose>
    <div style="display: none">
        <div>encoding: <c:out value="${encoding}" /></div>
    </div>
    <c:choose>
        <c:when test="${users != null && users.size() > 0}">
        <table class="users">
            <tr>
                <th>ИД</th>
                <th>Имя</th>
                <th>Логин</th>
                <th>Емэил</th>
                <th>Дата</th>
                <th>Роль</th>
                <th>Страна</th>
                <th>Город</th>
                <c:if test="${auth != null}">
                <th></th>
                <th></th>
                </c:if>
            </tr>
            <c:forEach items="${users}" var="user">
            <tr>
                <td><c:out value="${user.id}" /></td>
                <td><c:out value="${user.name}" /></td>
                <td><c:out value="${user.login}" /></td>
                <td><c:out value="${user.email}" /></td>
                <td><span class="date"><c:out value="${user.dateStr}" /></span></td>
                <td><c:out value="${user.roleName}" /></td>
                <td><c:out value="${user.countryName}" /></td>
                <td><c:out value="${user.cityName}" /></td>
                <c:if test="${auth != null}">
                <td>
                <c:if test="${auth.id == user.id || auth.roleId == adminRole.id}">
                    <c:url value="${refUpdate}" var="refUpdateUrl">
                        <c:param name="id" value="${user.id}" />
                    </c:url>
                    <a href="${refUpdateUrl}">редактировать</a>
                </c:if>
                </td>
                <td>
                <c:if test="${auth.roleId == adminRole.id}">
                    <c:url value="${refDelete}" var="refDeleteUrl">
                        <c:param name="id" value="${user.id}" />
                    </c:url>
                    <a href="${refDeleteUrl}">удалить</a>
                    </c:if>
                </c:if>
                </td>
            </tr>
            </c:forEach>
        </table>
        </c:when>
        <c:otherwise>
        <p>Никого нет</p>
        </c:otherwise>
    </c:choose>
    <c:if test="${auth != null && auth.roleId == adminRole.id}">
    <br />
    <a href="${refCreate}">Добавить нового пользователя</a>
    </c:if>
</body>
</html>
