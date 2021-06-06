<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>Meals</h2>
    <hr>
    <table border=1>
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${mealTo}" var="mealTo">
            <tr style="color:${mealTo.excess ? 'red' : 'green'}">
                <td><c:out value="${mealTo.formatDateTime}" /></td>
                <td><c:out value="${mealTo.description}" /></td>
                <td><c:out value="${mealTo.calories}" /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
