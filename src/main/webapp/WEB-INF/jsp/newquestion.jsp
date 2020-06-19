<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<h1>Ask a new question</h1>
<c:choose>
    <c:when test="${question != null}">
        <form method="post" action="createquestion?forumId=${forum.forumId}&questId=${question.questId}">
    </c:when>
    <c:otherwise>
        <form method="post" action="createquestion?forumId=${forum.forumId}">
    </c:otherwise>
</c:choose>
    <label for="title">title:</label><br>
    <input type="text" id = "title" name = "title" value="${question.title}"><br>
    <label for="text">Question text:</label><br>
    <input type="text" id="text" name="text" value="${question.text}">
    <input type="submit" value="Ask Question">
</form>