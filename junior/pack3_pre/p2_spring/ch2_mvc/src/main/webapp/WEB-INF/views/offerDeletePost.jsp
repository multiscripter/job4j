<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Удалить объявление</title>
    <jsp:include page="head.jsp" />
</head>
<body class="offer-delete offer-delete-post">
    <div class="container">
        <div class="row">
            <div class="col col-xs-12 col-sm-12 col-md-10 offset-md-1 col-lg-8 offset-lg-2 col-xl-6 offset-xl-3">
                <h1 class="text-center">Удаление объявление</h1>
                <p>${msg}</p>
                <div class="text-center mb-2">
                    <a class="btn btn-primary" href="${refRoot}">На главную</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>