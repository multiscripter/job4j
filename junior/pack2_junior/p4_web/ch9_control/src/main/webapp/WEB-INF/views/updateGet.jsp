<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=${encoding}" />
    <title>Редактирование меломана</title>
</head>
<body class="update">
    <c:if test="${auth != null}">
    <div>Вы вошли как: ${auth.login}</div>
    </c:if>
    <h1>Редактирование меломана</h1>
    <section>
        <a class="ref ref-logout" href="${refLogin}?auth=logout">Выйти</a>
        <c:choose>
            <c:when test="${user != null}">
        <form class="form js-form" method="POST" action="${action}">
            <ul class="form-list">
                <li class="form-item">
                    <label class="form-label">
                        <span class="form-label-text">Логин:</span>
                        <input class="form-textfield" type="text" name="login" required="required" value="${user.login}" />
                    </label>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="form-label-text">Пароль:</span>
                        <input class="form-textfield" type="password" name="pass" required="required" />
                    </label>
                </li>
                <c:if test="${auth.roleId == adminRole.id}">
                <li class="form-item">
                    <label class="form-label">
                        <span class="form-label-text">Роль:</span>
                        <select class="chosen-select form-select" name="role" required="required">
                        <c:forEach items="${roles}" var="roleItem">
                            <c:choose>
                                <c:when test="${user.role != null && user.roleId == roleItem.id}">
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
                        <input class="form-textfield" type="text" name="country" required="required" value="${user.country}" />
                    </label>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="form-label-text">Город:</span>
                        <input class="form-textfield" type="text" name="city" required="required" value="${user.city}" />
                    </label>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="form-label-text">Адрес:</span>
                        <input class="form-textfield" type="text" name="addr" required="required" value="${user.addr}" />
                    </label>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="form-label-text">Муз. стиль:</span>
                        <select class="chosen-select form-select" name="mtypes" required="required" multiple="multiple">
                        <c:forEach items="${mtypes}" var="mtypeItem">
                            <c:set var="mtypeMatch" scope="request" value="false"/>
                            <c:choose>
                                <c:when test="${user.musicTypes != null}">
                                    <c:forEach items="${user.musicTypes}" var="umtype">
                                        <c:if test="${!mtypeMatch && umtype.id == mtypeItem.id}">
                            <option value="${mtypeItem.id}" selected="selected">${mtypeItem.name}</option>
                                            <c:set var="mtypeMatch" scope="request" value="true"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${!mtypeMatch}">
                                        <option value="${mtypeItem.id}">${mtypeItem.name}</option>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                            <option value="${mtypeItem.id}">${mtypeItem.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        </select>
                    </label>
                </li>
                </c:if>
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