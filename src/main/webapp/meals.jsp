<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.UserMealWithExceed" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <style>
        .exceeded{
            color: red;
        }
        .normal{
            color: green;
        }
    </style>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h3>Meal List</h3>
<a href="meals?action=create">Add Meal</a>
<table align="center" border="1">
    <tr><td>date</td><td>description</td><td>calories</td></tr>
    <c:forEach items="${userMeals}" var="userMeal">
        <%--
        <javatime:parseLocalDateTime value="${userMeal.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" />
        --%>
        <jsp:useBean id="userMeal" scope="page" type="ru.javawebinar.topjava.model.UserMealWithExceed"/>

        <tr class="${userMeal.excess == true ? 'exceeded' : 'normal'}">
            <%--<td><javatime:format pattern="yyyy-MM-dd HH:mm" value="${parsedDate}" style="--"/></td>--%>
            <td><%=TimeUtil.toString(userMeal.getDateTime())%></td>
            <td>${userMeal.description}</td>
            <td>${userMeal.calories}</td>
            <td><a href="meals?action=update&id=${userMeal.id}">Update</a></td>
            <td><a href="meals?action=delete&id=${userMeal.id}">Delete</a></td>
            <td>${userMeal.id}</td>
        </tr>
        <%--    <c:out value="Excess = ${userMeal.isExcess()}"/>--%>
    </c:forEach>
</table>

</body>
</html>