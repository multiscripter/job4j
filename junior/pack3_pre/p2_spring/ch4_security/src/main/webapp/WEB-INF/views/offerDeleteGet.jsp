<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Удалить объявление</title>
    <jsp:include page="head.jsp" />
</head>
<body class="offer-delete offer-delete-get">
<div class="container">
    <div class="row">
        <div class="col col-xs-12 col-sm-12 col-md-10 offset-md-1 col-lg-8 offset-lg-2 col-xl-6 offset-xl-3">
            <h1 class="text-center">Удалить объявление</h1>
            <div class="text-center mb-2">
                <a class="btn btn-primary" href="${refRoot}">На главную</a>
            </div>
            <c:choose>
                <c:when test="${offer != null && user.id == offer.user.id}">
            <ul class="offer-list">
                <li class="offer-item">
                    <ul class="offer-details">
                        <li class="offer-pic">
                            <ul class="foto-list">
                                <c:forEach items="${offer.fotos}" var="foto">
                                    <li class="foto-item">
                                        <a class="foto-ref" data-fancybox="gallery-${offer.id}" href="${refRoot}fotos/${offer.id}/${foto}">
                                            <img class="foto-pic" src="${refRoot}fotos/${offer.id}/${foto}" alt="" />
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
                        <li class="offer-car">
                            <h5 class="offer-head">
                                ${offer.car.brand.name}
                                ${offer.car.name}
                            </h5>
                            <ul class="offer-ttx-list">
                                <li class="offer-ttx-item">
                                    <span class="offer-ttx-name">
                                        Кузов:
                                    </span>
                                    <span class="offer-ttx-val">
                                        ${offer.body.name}
                                    </span>
                                </li>
                            </ul>
                        </li>
                        <li class="offer-data">
                            <span class="price">${offer.price} р.</span>
                        </li>
                    </ul>
                </li>
            </ul>
            <form class="pb-4" method="post">
                <input type="hidden" name="id" value="${offer.id}" />
                <table class="table offer-table offer-delete-table">
                    <tr>
                        <td>
                            <label for="name">Ваше имя:</label>
                        </td>
                        <td>
                            <input id="name" name="name" type="text" class="form-control" value="${name}" />
                        </td>
                        <td class="text-center">
                            <input type="submit" class="btn btn-primary btn-submit" value="Удалить" />
                        </td>
                    </tr>
                </table>
            </form>
                </c:when>
                <c:when test="${offer != null && user.id != offer.user.id}">
            <div>У&nbsp;вас нет прав для&nbsp;удаления объявления.</div>
                </c:when>
                <c:otherwise>
            <div>Объявление с id: ${id} не существует.</div>
                </c:otherwise>
            </c:choose>
            <div class="text-center">
                <div><c:out value="${pageContext.request.remoteUser}" /></div>
                <a class="btn btn-primary" href="${refRoot}logout/">Выход</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>