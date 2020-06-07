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
                ${isEdit ? "Edit meal": "New meal"}
            </p>
        </div>

        <c:if test="${errText!=null}">
            <div class="w3-panel w3-red w3-display-container">
                <span onclick="this.parentElement.style.display='none'"
                      class="w3-button w3-red w3-large w3-display-topright">x</span>
                <p>${errText}</p>
            </div>
        </c:if>

        <div>
            <form class="w3-container" method="post">
                <p>
                    <label for="DateTime">Date&Time</label>
                    <input class="w3-input" id="DateTime" type="datetime-local" name="dtl"
                           value="${datetime}">
                    <label for="description">Description</label>
                    <input class="w3-input" id="description" type="text" name="descr"
                           value="${description}">
                    <label for="calories">Calories</label>
                    <input class="w3-input" id="calories" type="number" name="cal" value="${calories}">
                </p>
                <p>
                    <input class="w3-button w3-hover-gray w3-round" type="submit" value="${isEdit ? "Save": "Add"}">
                    <a class="w3-button w3-hover-gray w3-round"
                       href="${pageContext.request.contextPath}/meals">Cancel</a>
                </p>
            </form>
        </div>
    </div>

</div>

</body>
</html>
