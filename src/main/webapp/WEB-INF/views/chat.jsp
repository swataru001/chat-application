<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form"  uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>投稿フォーム</h2>
<form:form modelAttribute="articleForm" action="${pageContext.request.contextPath}/article/insertArticle">
名前:
<form:errors path="name" cssStyle="color:red" element="div"/>
<form:input path="name"/><br>
<br>
記事投稿:
<form:errors path="content" cssStyle="color:red" element="div"/>
<form:textarea path="content" cols="30" rows="3"/>
<input type="submit" value="投稿">
</form:form>

<h2>投稿一覧</h2>
<c:forEach var="article" items="${articleList}">

<p>投稿</p>
ID:<c:out value="${article.id}"/><br>
<p>name:</p>
<c:out value="${article.name}"/><br>
<p>article:</p>
<c:out value="${article.content}"/><br>
<form:form modelAttribute="commentForm" action="${pageContext.request.contextPath}/article/insertComment">
<h2>コメント投稿</h2>
名前:
<c:if test="${article.id==commentForm.articleId}">
<form:errors path="name" cssStyle="color:red" element="div"/>
</c:if>
<form:input path="name"/><br>
<br>
コメント投稿:
<c:if test="${article.id==commentForm.articleId}">
<form:errors path="content" cssStyle="color:red" element="div"/>
</c:if>
<form:textarea path="content" cols="30" rows="3"/>
<input type="hidden" name="articleId" value="${article.id}">
<input type="submit" value="コメント">
</form:form>
<p>comments</p>
<c:forEach var="comment" items="${article.commentList}"><br>
<p>name<p>
<c:out value="${comment.name}"/><br>
<p>comment<p>
<c:out value="${comment.content}"/><br>
</c:forEach>

<form action="${pageContext.request.contextPath}/article/deleteById">
<input type="hidden" name="articleId" value="${article.id}">
<input type="submit" value="削除">
</form>
<hr>
</c:forEach>
</body>
</html>