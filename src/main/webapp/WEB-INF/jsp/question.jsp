<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<h1>${forum.name}</h1>
<h2>${question.title}</h2>
<p>${question.text}</p>
<a href="newquestion?forumId=${forum.forumId}&questId=${question.questId}">
    <p>Edit</p>
</a>
<a href="deletequestion?questId=${question.questId}">
    <p>Delete</p>
</a>
<h3>Answers:</h3>
<hr>
<c:forEach items="${answers}" var="answer">
    <p>${answer.text}</p>
    <a href="deleteanswer?forumId=${forum.forumId}&questId=${question.questId}&answId=${answer.answId}">
        <p>Delete</p>
    </a>
    <hr>
</c:forEach>
<form method="post" action="createanswer?forumId=${forum.forumId}&questId=${question.questId}">
    <label for="text">Answer text:</label><br>
    <input type="text" id="text" name="text">
    <input type="submit" value="Post">
</form>