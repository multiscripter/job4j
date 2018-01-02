<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=${encoding}" />
    <title>Представься, дарагой!</title>
    <jsp:include page="head.jsp" />
</head>
<body class="login">
    <c:choose>
        <c:when test="${auth == null}">
    <h1>Представься, дарагой!</h1>
    <section>
        <form class="js-form" method="POST" action="${action}">
            <ul class="form-list">
                <li class="form-item<c:if test="${errors != null && errors.login != null}"> error</c:if>">
                    <label class="form-label">
                        <span class="form-label-text">Логин:</span>
                        <input class="form-textfield" type="text" name="login" required="required" value="<c:if test='${login != null}'>${login}</c:if>" />
                    </label>
                    <span class="form-label-msg"><c:if test="${errors != null && errors.login != null}">
                    ${errors.login}
                </c:if></span>
                </li>
                <li class="form-item<c:if test="${errors != null && errors.pass != null}"> error</c:if>">
                    <label class="form-label">
                        <span class="form-label-text">Пароль:</span>
                        <input class="form-textfield" type="password" name="pass" required="required" />
                    </label>
                    <span class="form-label-msg"><c:if test="${errors != null && errors.pass != null}">
                        ${errors.pass}
                    </c:if></span>
                </li>
                <li class="form-item">
                    <input class="form-submit" type="submit" value="Войти" />
                </li>
            </ul>
        </form>
        </c:when>
        <c:otherwise>
        <h1>Ты уже зашёл, дарагой ${auth.name}!</h1>
    <section>
        <a class="ref ref-logout" href="${refLogin}?auth=logout">Выйти</a>
        <br />
        </c:otherwise>
    </c:choose>
        <br />
        <a class="ref ref-home" href="${refHome}">Вернуться на Главную</a>
    </section>
</body>
</html>