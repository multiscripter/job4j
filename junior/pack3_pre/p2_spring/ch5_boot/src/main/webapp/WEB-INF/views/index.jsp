<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Площадка продаж машин с фильтрами</title>
    <jsp:include page="head.jsp" />
</head>
<body class="index">
    <div class="container">
        <div class="row">
            <div class="col col-xs-12 col-sm-12 col-md-10 offset-md-1 col-lg-8 offset-lg-2 col-xl-6 offset-xl-3">
                <h1 class="text-center">Boot. Площадка продаж машин.</h1>
                <h4 class="text-center">junior.pack3.p4.ch5.task1</h4>
                <div class="text-center mb-2">
                    <a class="btn btn-primary" href="${offerCreate}">Подать объявление</a>
                </div>
                <form class="mb-2 filters">
                    <c:if test="${brands.size() > 0}">
                    <select name="brand" class="custom-select">
                        <option value="0">Все брэнды</option>
                    <c:forEach items="${brands}" var="brand">
                        <option value="${brand.id}">${brand.name}</option>
                    </c:forEach>
                    </select>
                    </c:if>
                    <label>
                        <span>с фото</span>
                        <input type="checkbox" name="wfoto" value="1" />
                    </label>
                    <input type="submit" class="btn btn-primary btn-submit" value="Отфильтровать" />
                </form>
                <c:choose>
                    <c:when test="${items != null && items.size() > 0}">
                <ul class="offer-list">
                    <c:forEach items="${items}" var="item">
                    <li class="offer-item" id="offer-${item.id}">
                        <ul class="offer-details">
                            <li class="offer-pic">
                                <ul class="foto-list">
                                <c:forEach items="${item.fotos}" var="foto">
                                    <li class="foto-item">
                                        <a class="foto-ref" data-fancybox="gallery-${item.id}" href="${refRoot}resources/fotos/${item.id}/${foto}">
                                            <img class="foto-pic" src="${refRoot}resources/fotos/${item.id}/${foto}" alt="" />
                                        </a>
                                    </li>
                                </c:forEach>
                                </ul>
                            </li>
                            <li class="offer-car">
                                <h5 class="offer-head">
                                    ${item.car.brand.name}
                                    ${item.car.name}
                                </h5>
                                <ul class="offer-ttx-list">
                                    <li class="offer-ttx-item">
                                        <span class="offer-ttx-name">
                                            Кузов:
                                        </span>
                                        <span class="offer-ttx-val">
                                            ${item.body.name}
                                        </span>
                                    </li>
                                </ul>
                            </li>
                            <li class="offer-actions">
                                <c:if test="${user != null && user.id == item.user.id}">
                                    <a class="btn btn-primary" href="${offerUpdate}?id=${item.id}">Изменить</a>
                                    <a class="btn btn-primary" href="${offerDelete}?id=${item.id}">Удалить</a>
                                </c:if>
                            </li>
                            <li class="offer-data">
                                <span class="price">${item.price} р.</span>
                            </li>
                        </ul>
                    </li>
                    </c:forEach>
                </ul>
                    </c:when>
                    <c:otherwise>
                <span class="d-block">Объявлений нет.</span>
                    </c:otherwise>
                </c:choose>
                <div class="text-center">
                    <div><c:out value="${pageContext.request.remoteUser}" /></div>
                    <c:choose>
                        <c:when test="${user != null}">
                    <a class="btn btn-primary" href="${refRoot}logout/">Выход</a>
                        </c:when>
                        <c:otherwise>
                    <a class="btn btn-primary" href="${refRoot}login/">Вход</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</body>
</html>