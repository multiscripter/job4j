<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Создать объявление</title>
    <jsp:include page="head.jsp" />
    <style type="text/css" rel="stylesheet" media="all">
        .custom-file-label::after {
            content: 'Обзор';
        }
    </style>
</head>
<body class="offer-create offer-create-get">
    <div class="container">
        <div class="row">
            <div class="col col-xs-12 col-sm-12 col-md-10 offset-md-1 col-lg-8 offset-lg-2 col-xl-6 offset-xl-3">
                <h1 class="text-center">Создать объявление</h1>
                <div class="text-center mb-2">
                    <a class="btn btn-primary" href="${refRoot}">На главную</a>
                </div>
                <form class="pb-4" method="post" enctype="multipart/form-data">
                    <table class="table offer-table offer-create-table">
                        <tr>
                            <td>
                                <label for="name">Ваше имя:</label>
                            </td>
                            <td>
                                <input id="name" name="name" type="text" class="form-control" value="${offer.user.name}" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="brand">Брэнд:</label>
                            </td>
                            <td>
                                <select id="brand" name="brand" class="custom-select" required="required">
                                <c:forEach items="${brands}" var="brandItem">
                                    <c:choose>
                                        <c:when test="${offer.car.brand != null && offer.car.brand.id == brandItem.id}">
                                    <option value="${brandItem.id}" selected="selected">${brandItem.name}</option>
                                        </c:when>
                                        <c:otherwise>
                                    <option value="${brandItem.id}">${brandItem.name}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="car">Модель:</label>
                            </td>
                            <td>
                                <select id="car" name="car" class="custom-select" required="required">
                                <c:forEach items="${cars}" var="carItem">
                                    <c:choose>
                                        <c:when test="${offer.car.id == carItem.id}">
                                    <option value="${carItem.id}" selected="selected">${carItem.name}</option>
                                        </c:when>
                                        <c:otherwise>
                                    <option value="${carItem.id}">${carItem.name}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="body">Кузов:</label>
                            </td>
                            <td>
                                <select id="body" name="body" class="custom-select" required="required">
                                <c:forEach items="${bodies}" var="bodyItem">
                                    <c:choose>
                                        <c:when test="${offer.body.id == bodyItem.id}">
                                    <option value="${bodyItem.id}" selected="selected">${bodyItem.name}</option>
                                        </c:when>
                                        <c:otherwise>
                                    <option value="${bodyItem.id}">${bodyItem.name}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="price">Цена:</label>
                            </td>
                            <td>
                                <input id="price" name="price" type="text" class="form-control" value="${offer.price}" />
                            </td>
                        </tr>
                        <tr class="status-box">
                            <td>
                                <label for="status-true">Продано:</label>
                            </td>
                            <td>
                            <c:choose>
                                <c:when test="${offer.status}">
                                <div class="custom-control custom-radio">
                                    <input type="radio" id="status-true" name="status" class="custom-control-input" value="true" checked="checked" />
                                    <label class="custom-control-label" for="status-true">Продано</label>
                                </div>
                                <div class="custom-control custom-radio">
                                    <input type="radio" id="status-false" name="status" class="custom-control-input" value="false" />
                                    <label class="custom-control-label" for="status-false">Не продано</label>
                                </div>
                                </c:when>
                                <c:otherwise>
                                <div class="custom-control custom-radio">
                                    <input type="radio" id="status-true" name="status" class="custom-control-input" value="true" />
                                    <label class="custom-control-label" for="status-true">Продано</label>
                                </div>
                                <div class="custom-control custom-radio">
                                    <input type="radio" id="status-false" name="status" class="custom-control-input" value="false" checked="checked" />
                                    <label class="custom-control-label" for="status-false">Не продано</label>
                                </div>
                                </c:otherwise>
                            </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="foto">Фото:</label>
                            </td>
                            <td>
                                <div class="custom-file">
                                    <input id="foto" name="foto" type="file" class="custom-file-input" multiple="multiple" />
                                    <label class="custom-file-label" for="foto">Выберите файлы</label>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <input type="submit" class="btn btn-primary btn-submit" value="Создать" />
                </form>
            </div>
        </div>
    </div>
</body>
</html>