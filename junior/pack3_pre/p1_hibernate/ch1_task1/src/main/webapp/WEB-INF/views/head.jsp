<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no" />
<link href="./static/css/bootstrap.css" rel="stylesheet" />
<link href="./static/css/styles.css" rel="stylesheet" />
<script type="text/javascript" src="./static/js/jquery.js"></script>
<script type="text/javascript">
    var messages = {};
    <c:forEach items="${messages}" var="message">
    messages['${message.name}'] = '${message.value}';
    </c:forEach>
</script>
<script type="text/javascript" src="./static/js/core.js"></script>