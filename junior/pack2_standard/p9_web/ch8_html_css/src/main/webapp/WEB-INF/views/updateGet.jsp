<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=${encoding}" />
    <title>Редактирование пользователя</title>
    <jsp:include page="head.jsp" />
</head>
<body class="update">
    <c:if test="${auth != null}">
    <div>Вы вошли как: ${auth.name}</div>
    </c:if>
    <h1>Редактирование пользователя</h1>
    <section>
        <a class="ref ref-logout" href="${refLogin}?auth=logout">Выйти</a>
        <c:choose>
            <c:when test="${user != null}">
        <form class="form js-form" method="POST" action="${action}">
            <ul class="form-list">
                <li class="form-item<c:if test="${errors != null && errors.name != null}"> error</c:if>">
                    <label class="form-label">
                        <span class="form-label-text">Имя:</span>
                        <input class="form-textfield" type="text" name="name" required="required" value="${user.name}" />
                    </label>
                    <span class="form-label-msg"><c:if test="${errors != null && errors.name != null}">
                        ${errors.name}
                    </c:if></span>
                </li>
                <li class="form-item<c:if test="${errors != null && errors.login != null}"> error</c:if>">
                    <label class="form-label">
                        <span class="form-label-text">Логин:</span>
                        <input class="form-textfield" type="text" name="login" required="required" value="${user.login}" />
                    </label>
                    <span class="form-label-msg"><c:if test="${errors != null && errors.login != null}">
                        ${errors.login}
                    </c:if></span>
                </li>
                <li class="form-item<c:if test="${errors != null && errors.email != null}"> error</c:if>">
                    <label class="form-label">
                        <span class="form-label-text">Емэил:</span>
                        <input class="form-textfield" type="email" name="email" required="required" value="${user.email}" />
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
                <c:if test="${auth.roleId == adminRole.id}">
                <li class="form-item<c:if test="${errors != null && errors.role != null}"> error</c:if>">
                    <label class="form-label">
                        <span class="form-label-text">Роль:</span>
                        <select class="chosen-select form-select" name="role" required="required">
                        <c:forEach items="${roles}" var="roleItem">
                            <c:choose>
                                <c:when test="${errors.role == null && user.role != null && user.roleId == roleItem.id}">
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
                </c:if>
                <li class="form-item<c:if test="${errors != null && errors.country != null}"> error</c:if>">
                    <label class="form-label">
                        <span class="form-label-text">Страна:</span>
                        <select class="chosen-select form-select js-filter-countries" name="country" required="required">
                        <c:forEach items="${countries}" var="countryItem">
                            <c:choose>
                                <c:when test="${errors.country == null && user.country != null && user.countryId == countryItem.id}">
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
                                <c:when test="${errors.city == null && user.city != null && user.cityId == cityItem.id}">
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
                    <input class="form-submit" type="submit" value="Отредактировать" />
                </li>
            </ul>
            <input type="hidden" name="id" value="${user.id}" />
        </form>
            </c:when>
            <c:otherwise>
        <p class="message">${message}</p>
            </c:otherwise>
        </c:choose>
        <c:if test="${refBack != null}">
        <a class="ref ref-back" href="${refBack}">Вернуться назад</a>
        </c:if>
        <c:if test="${refBack != null && refHome != null}}">
        <br /><br />
        </c:if>
        <c:if test="${refHome != null}">
        <a class="ref ref-home" href="${refHome}">Вернуться на Главную</a>
        </c:if>
    </section>
</body>
</html>