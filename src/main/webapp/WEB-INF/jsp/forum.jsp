<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<h1>${forum.name}</h1>
<a href="newforum?forumId=${forum.forumId}"><p>Edit Forum</p></a>
<a href="deleteforum?forumId=${forum.forumId}"><p>Delete Forum</p></a>
<a href="newquestion?forumId=${forum.forumId}"><p>Ask a question</p></a>
<c:forEach items="${questions}" var="question">
    <a href="question?forumId=${forum.forumId}&questId=${question.questId}"><h2>${question.title}:</h2></a>
    <p>${question.text}</p>
</c:forEach>