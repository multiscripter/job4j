<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=${encoding}" />
    <title>TODO-лист</title>
    <jsp:include page="head.jsp" />
</head>
<body class="read">
    <div class="container-fluid">
        <div class="row">
            <div class="col col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <h1 class="text-center">TODO-лист</h1>
                <div class="custom-control custom-checkbox">
                    <input type="checkbox" class="custom-control-input js-show-all" id="show-all">
                    <label class="custom-control-label" for="show-all">Показать все</label>
                </div>
                <div class="table-responsive">
                    <form class="js-form" method="post" action="${action}">
                        <input class="form-control js-form-item-id" type="hidden" name="id" value="" />
                        <table class="table js-items">
                            <thead class="thead-dark text-center">
                                <tr>
                                    <th>ИД</th>
                                    <th>Заголовок</th>
                                    <th>Описание</th>
                                    <th>Дата создания</th>
                                    <th>Выполнено</th>
                                    <th>Действия</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr class="js-curTr">
                                    <th scope="row"></th>
                                    <td>
                                        <input class="form-control js-field-name" type="text" name="name" required="required" />
                                    </td>
                                    <td>
                                        <textarea class="form-control js-field-descr" name="descr" required="required" rows="1"></textarea>
                                    </td>
                                    <td>
                                        <input class="form-control js-field-created" type="text" name="created" required="required" placeholder="2018-01-01 12:34:56" />
                                    </td>
                                    <td data-done="false">
                                        <div class="js-field-done">
                                            <div class="custom-control custom-radio">
                                                <input type="radio" id="done-true" name="done" class="custom-control-input" value="true" />
                                                <label class="custom-control-label" for="done-true">Да</label>
                                            </div>
                                            <div class="custom-control custom-radio">
                                                <input type="radio" id="done-false" name="done" class="custom-control-input" value="false" checked="checked" />
                                                <label class="custom-control-label" for="done-false">Нет</label>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="col-btns">
                                        <a class="btn btn-primary btn-update js-form-action d-none" data-action="create" role="button" href="#">Создать</a>
                                        <input type="submit" class="btn btn-primary js-form-submit" value="Создать" />
                                    </td>
                                </tr>
                                <c:choose>
                                    <c:when test="${items != null && items.size() > 0}">
                                        <c:forEach items="${items}" var="item">
                                <tr>
                                    <th scope="row">${item.id}</th>
                                    <td>${item.item}</td>
                                    <td>${item.descr}</td>
                                    <td>${item.createdStr}</td>
                                    <c:choose>
                                        <c:when test="${item.done == true}">
                                    <td data-done="${item.done}">Да</td>
                                        </c:when>
                                        <c:otherwise>
                                    <td data-done="${item.done}">Нет</td>
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="col-btns">
                                        <a class="btn btn-primary btn-update js-form-action" data-action="update" role="button" href="#">Изменить</a>
                                        <a class="btn btn-primary btn-del js-form-action" data-action="delete" role="button" href="#">Удалить</a>
                                    </td>
                                </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                <tr>
                                    <td colspan="6" class="text-center">Записей нет</td>
                                </tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>