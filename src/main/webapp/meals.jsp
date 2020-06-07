<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link href="https://cdn.jsdelivr.net/npm/@mdi/font@4.x/css/materialdesignicons.min.css" rel="stylesheet">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<div class="w3-container">
    <a class="w3-button w3-hover-gray w3-round" href="${pageContext.request.contextPath}/meals?action=new">New
        meal</a>
</div>

<div class="w3-responsive">
    <table class="w3-table-all w3-hoverable">
        <tr>
            <th>Id</th>
            <th>DateTime</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
        </tr>

        <jsp:useBean id="meals" scope="request" type="java.util.List"/>
        <c:forEach var="meal" items="${meals}">
            <tr class="${meal.isExcess() ? "w3-text-red": "w3-text-green"}">
                <td>${meal.getId()}</td>
                <td>${meal.getDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}</td>
                <td>${meal.getDescription()}</td>
                <td>${meal.getCalories()}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/meals?action=edit&mealId=${meal.getId()}"
                       class="w3-button w3-round w3-hover-blue material-icons">edit</a>
                    <a href="${pageContext.request.contextPath}/meals?action=delete&mealId=${meal.getId()}"
                       class="w3-button w3-round w3-hover-blue material-icons">delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
