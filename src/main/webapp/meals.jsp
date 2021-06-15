<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <hr/>
    <form method="get" id="filters" action="meals" target="_self">
        <input type="hidden" name="action" value="filter">
        <table border="0" cellpadding="8">
            <thead>
            <tr>
                <th>From date (inclusive)</th>
                <th width="240">To date (inclusive)</th>
                <th width="240">From time (inclusive)</th>
                <th>To time (exclusive)</th>
            </tr>
            </thead>
            <tr>
                <td align="center"><input type="date" name="fromDate" value="<%= request.getAttribute("fromDate") %>"></td>
                <td align="center"><input type="date" name="toDate" value="<%= request.getAttribute("toDate") %>"></td>
                <td align="center"><input type="time" name="fromTime" value="<%= request.getAttribute("fromTime") %>"></td>
                <td align="center"><input type="time" name="toTime" value="<%= request.getAttribute("toTime") %>"></td>
            </tr>
            <tr>
                <td border="0"></td>
                <td></td>
                <td></td>
                <td align="center"><button type="submit">Add Filter</button></td>
            </tr>
        </table>
    </form>
    <hr/>
    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
<%--                        ${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
<%--                        <%=TimeUtil.toString(meal.getDateTime())%>--%>
<%--                        ${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>