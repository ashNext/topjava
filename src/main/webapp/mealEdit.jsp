<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>MealEdit</title>
</head>
<body>

<div class="w3-container">

    <div class="w3-container">

        <div>
            <p>
                ${param.containsKey("mealId") ? "Edit meal": "New meal"}
            </p>
        </div>

        <div>
            <form class="w3-container" method="post">
                <p>
                    <label for="DateTime">Date&Time</label>
                    <input class="w3-input" id="DateTime" type="datetime-local" name="dtl"
                           value="${meal.getDateTime()}">
                    <label for="description">Description</label>
                    <input class="w3-input" id="description" type="text" name="descr"
                           value="${meal.getDescription()}">
                    <label for="calories">Calories</label>
                    <input class="w3-input" id="calories" type="number" name="cal" value="${meal.getCalories()}">
                </p>
                <p>
                    <input class="w3-button w3-hover-gray w3-round" type="submit" value="${param.containsKey("mealId") ? "Save": "Add"}">
                    <a class="w3-button w3-hover-gray w3-round"
                       href="${pageContext.request.contextPath}/meals">Cancel</a>
                </p>
            </form>
        </div>
    </div>

</div>

</body>
</html>
