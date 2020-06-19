<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<h1>Create a new forum</h1>
<c:choose>
    <c:when test="${forum != null}">
        <form method="post" action="createforum?forumId=${forum.forumId}">
        <br />
    </c:when>
    <c:otherwise>
        <form method="post" action="createforum">
        <br />
    </c:otherwise>
</c:choose>
    <label for="name">Name:</label><br>
    <input type="text" id = "name" name = "name" value="${forum.name}"><br>
    <label for="description">Description:</label><br>
    <input type="text" id="description" name="description" value="${forum.description}">
    <input type="submit" value="Create Forum">
</form>