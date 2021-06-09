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
        <input type="hidden" name="id" value="${meal.id}"/>
        <table border=0 cellspacing="10">
            <tr>
                <td>Date Time:</td>
                <td><input type="datetime-local" name="dateTime" value="${meal.dateTime}"/></td>
            </tr>
            <tr>
                <td>Description:</td>
                <td><input type="text" name="description" value="${meal.description}"/></td>
            </tr>
            <tr>
                <td>Calories:</td>
                <td><input type="text" name="calories" value="${meal.calories}"/></td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="save"/>
                    <input type="button" value="cancel" onclick='location.href = "meals?action=meals"'/>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
