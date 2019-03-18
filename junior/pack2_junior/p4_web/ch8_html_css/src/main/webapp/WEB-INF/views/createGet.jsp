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
        <div style="display: none">
            <div>errors      :${errors}</div>
            <div>errors.name :${errors.name}</div>
            <c:if test="${errors != null && errors.name != null}">
            <div>if:1 ${errors.name}</div>
            </c:if>
        </div>
        <form class="form js-form" method="POST" action="${action}">
            <ul class="form-list">
                <li class="form-item<c:if test="${errors != null && errors.name != null}"> error</c:if>">
                    <label class="form-label">
                        <span class="form-label-text">Имя:</span>
                        <input class="form-textfield" type="text" name="name" required="required" value="<c:if test='${name != null}'>${name}</c:if>" />
                    </label>
                    <span class="form-label-msg"><c:if test="${errors != null && errors.name != null}">
                        ${errors.name}
                    </c:if></span>
                </li>
                <li class="form-item<c:if test="${errors != null && errors.login != null}"> error</c:if>">
                    <label class="form-label">
                        <span class="form-label-text">Логин:</span>
                        <input class="form-textfield" type="text" name="login" required="required" value="<c:if test='${login != null}'>${login}</c:if>" />
                    </label>
                    <span class="form-label-msg"><c:if test="${errors != null && errors.login != null}">
                        ${errors.login}
                    </c:if></span>
                </li>
                <li class="form-item<c:if test="${errors != null && errors.email != null}"> error</c:if>">
                    <label class="form-label">
                        <span class="form-label-text">Емэил:</span>
                        <input class="form-textfield" type="email" name="email" required="required" value="<c:if test='${email != null}'>${email}</c:if>" />
                    </label>
                    <span class="form-label-msg"><c:if test="${errors != null && errors.email != null}">
                        ${errors.email}
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
                <li class="form-item<c:if test="${errors != null && errors.role != null}"> error</c:if>">
                    <label class="form-label">
                        <span class="form-label-text">Роль:</span>
                        <select class="chosen-select form-select" name="role" required="required">
                        <c:forEach items="${roles}" var="roleItem">
                            <c:choose>
                                <c:when test="${errors.role == null && role != null && role == roleItem.id}">
                            <option value="${roleItem.id}" selected="selected">${roleItem.name}</option>
                                </c:when>
                                <c:otherwise>
                            <option value="${roleItem.id}">${roleItem.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        </select>
                    </label>
                    <span class="form-label-msg"><c:if test="${errors != null && errors.role != null}">
                        ${errors.role}
                    </c:if></span>
                </li>
                <li class="form-item<c:if test="${errors != null && errors.country != null}"> error</c:if>">
                    <label class="form-label">
                        <span class="form-label-text">Страна:</span>
                        <select class="chosen-select form-select js-filter-countries" name="country" required="required">
                        <c:forEach items="${countries}" var="countryItem">
                            <c:choose>
                                <c:when test="${errors.country == null && country != null && country == countryItem.id}">
                            <option value="${countryItem.id}" selected="selected">${countryItem.name}</option>
                                </c:when>
                                <c:otherwise>
                            <option value="${countryItem.id}">${countryItem.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        </select>
                    </label>
                    <span class="form-label-msg"><c:if test="${errors != null && errors.country != null}">
                        ${errors.country}
                    </c:if></span>
                </li>
                <li class="form-item<c:if test="${errors != null && errors.city != null}"> error</c:if>">
                    <label class="form-label">
                        <span class="form-label-text">Город:</span>
                        <select class="chosen-select form-select js-filter-cities" name="city" required="required">
                        <c:forEach items="${cities}" var="cityItem">
                            <c:choose>
                                <c:when test="${errors.city == null && city != null && city == cityItem.id}">
                            <option value="${cityItem.id}" selected="selected" data-countries="${cityItem.countriesIds}">${cityItem.name}</option>
                                </c:when>
                                <c:otherwise>
                            <option value="${cityItem.id}" data-countries="${cityItem.countriesIds}">${cityItem.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        </select>
                    </label>
                    <span class="form-label-msg"><c:if test="${errors != null && errors.city != null}">
                        ${errors.city}
                    </c:if></span>
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
