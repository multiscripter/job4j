<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=${encoding}" />
    <title>Представься, дарагой!</title>
</head>
<body>
    <div>${auth}</div>
    <c:choose>
        <c:when test="${auth == null}">
        <h1>Представься, дарагой!</h1>
        <form method="POST" action="${action}">
            <table>
                <tr>
                    <td>
                        <label>Логин:<br />
                            <input type="text" name="login" required="required" />
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
                <tr>
                    <td>
                        <input type="submit" value="Войти" />
                    </td>
                </tr>
            </table>
        <form>
        </c:when>
        <c:otherwise>
        <h1>Ты уже зашёл, дарагой ${auth.name}!</h1>
        <a href="${refLogout}">Выйти</a>
        </c:otherwise>
    </c:choose>
    <br />
    <a href="${refBack}">Вернуться назад</a>
</body>
</html>