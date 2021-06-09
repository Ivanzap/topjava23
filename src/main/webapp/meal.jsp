<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>Edit Meal</h2>
    <hr>
    <form method="POST" action='meals' name="formAddMeal">
        Date Time:
        <input type="datetime-local" name="dateTime" value="${meal.dateTime}"/>
        <br />
        Description:
        <input type="text" name="description" value="${meal.description}"/>
        <br />
        Calories:
        <input type="text" name="calories" value="${meal.calories}"/>
        <br />
        <input type="submit" value="save"/>
        <input type="submit" value="cancel"/>
    </form>
</body>
</html>
