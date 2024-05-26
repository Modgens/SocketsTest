<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <title>Statistic Page</title>
</head>
<body>
<h1>Messages Statistic</h1>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Text</th>
        <th>Client ID</th>
        <th>Delivered Time</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="message" items="${list}">
        <tr>
            <td>${message.id}</td>
            <td>${message.text}</td>
            <td>${message.client_id}</td>
            <td>${message.deliveredTime}</td>
            <td>
                <c:choose>
                    <c:when test="${empty message.deliveredTime}">Undelivered</c:when>
                    <c:otherwise>Delivered</c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
