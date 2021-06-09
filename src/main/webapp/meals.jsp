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
    <p><a href="meals?action=insert">Add Meal</a></p>
    <hr>
    <table border=1>
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <%--@elvariable id="mealTo" type="java.util.List"--%>
        <c:forEach items="${mealTo}" var="mealsTo">
            <tr style="color:${mealsTo.excess ? 'red' : 'green'}">
                <td>${mealsTo.dateTime.toLocalDate()} ${mealsTo.dateTime.toLocalTime()}</td>
                <td>${mealsTo.description}</td>
                <td>${mealsTo.calories}</td>
                <td><a href="meals?action=edit&id=${mealsTo.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${mealsTo.id}">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
