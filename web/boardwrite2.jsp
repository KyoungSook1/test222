<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%String bno = request.getParameter("bno"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1> 게시판</h1>
<form method="post" action="boardwrite2.kh">
<input type="hidden" name="bno" value="<%= bno %>" readonly>
제목  <input type="text" name="title" required> <br>
내용  <input type="text" name="content" required> <br>
<input type="submit" value="등록">
<input type="reset" value="취소">


</form>
</body>
</html>