<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=${encoding}" />
    <title>Редактирование пользователя</title>
</head>
<body>
	<h1>Редактирование пользователя</h1>
    <c:choose>
        <c:when test="${user != null}">
        <form method="POST" action="${action}">
            <table>
                <tr>
                    <td>
                        <label>Иия:<br />
                            <input type="text" name="name" required="required" value="${user.name}" />
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Логин:<br />
                            <input type="text" name="login" required="required" value="${user.login}" />
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Емэил:<br />
                            <input type="email" name="email" required="required" value="${user.email}" />
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Пароль:<br />
                            <input type="password" name="pass" required="required" />
                        </label>
                    </td>
                </tr>
                <c:if test="${auth.roleId == adminRole.id}">
                <tr>
                    <td>
                        <label>Роль:<br />
                            <select name="role" required="required">
                    <c:forEach items="${roles}" var="role">
                        <c:choose>
                            <c:when test="${user.roleId == role.id}">
                                <option selected="selected" value="${role.id}">${role.name}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${role.id}">${role.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                            </select>
                        </label>
                    </td>
                </tr>
                </c:if>
                <tr>
                    <td>
                        <input type="submit" value="Отредактировать" />
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