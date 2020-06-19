<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<a href="newforum">Create new forum</a>
<h1>Forums:</h1>
<c:forEach items="${forums}" var="forum">
    <a href="forum?id=${forum.forumId}"><h2>${forum.name}</h2></a>
        <p>${forum.description}</p>
</c:forEach>