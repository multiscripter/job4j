<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <title>Вход</title>
    <jsp:include page="head.jsp" />
</head>
<body class="login">
    <div class="container">
        <div class="row">
            <div class="col-4 offset-4 text-center">
                <h1>Вход</h1>
                <div class="text-center mb-2">
                    <a class="btn btn-primary" href="${refRoot}">На главную</a>
                </div>
                <form method="POST" action="${refRoot}login/">
                    <div class="form-group">
                        <input class="form-control" name="name" placeholder="Имя" />
                    </div>
                    <div class="form-group">
                        <input class="form-control" name="pass" type="password" placeholder="Пароль" />
                    </div>
                    <div class="form-group">
                        <input class="btn btn-primary" type="submit" value="Войти" />
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
