<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=${encoding}" />
    <title>Создание меломана</title>
</head>
<body class="create">
    <c:if test="${auth != null}">
    <div>Вы вошли как: ${auth.login}</div>
    </c:if>
    <h1>Создание меломана</h1>
    <section>
        <a class="ref ref-logout" href="${refLogin}?auth=logout">Выйти</a>
        <form class="form js-form" method="POST" action="${action}">
            <ul class="form-list">
                <li class="form-item">
                    <label class="form-label">
                        <span class="form-label-text">Логин:</span>
                        <input class="form-textfield" type="text" name="login" required="required" value="<c:if test='${login != null}'>${login}</c:if>" />
                    </label>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="form-label-text">Пароль:</span>
                        <input class="form-textfield" type="password" name="pass" required="required" />
                    </label>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="form-label-text">Роль:</span>
                        <select class="chosen-select form-select" name="role" required="required">
                        <c:forEach items="${roles}" var="roleItem">
                            <c:choose>
                                <c:when test="${role != null && role == roleItem.id}">
                            <option value="${roleItem.id}" selected="selected">${roleItem.name}</option>
                                </c:when>
                                <c:otherwise>
                            <option value="${roleItem.id}">${roleItem.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        </select>
                    </label>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="form-label-text">Страна:</span>
                        <input class="form-textfield" type="text" name="country" required="required" value="<c:if test='${country != null}'>${country}</c:if>" />
                    </label>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="form-label-text">Город:</span>
                        <input class="form-textfield" type="text" name="city" required="required" value="<c:if test='${city != null}'>${city}</c:if>" />
                    </label>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="form-label-text">Адрес:</span>
                        <input class="form-textfield" type="text" name="addr" required="required" value="<c:if test='${addr != null}'>${addr}</c:if>" />
                    </label>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="form-label-text">Муз. стиль:</span>
                        <select class="chosen-select form-select" name="mtypes" required="required" multiple="multiple">
                        <c:forEach items="${mtypes}" var="mtypeItem">
                            <c:choose>
                                <c:when test="${mtype != null && mtype == mtypeItem.id}">
                            <option value="${mtypeItem.id}" selected="selected">${mtypeItem.name}</option>
                                </c:when>
                                <c:otherwise>
                            <option value="${mtypeItem.id}">${mtypeItem.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        </select>
                    </label>
                </li>
                <li class="form-item">
                    <input class="form-submit" type="submit" value="Создать" />
                </li>
            </ul>
        </form>
        <a class="ref ref-home" href="${refHome}">Вернуться на Главную</a>
    </section>
</body>
</html>
