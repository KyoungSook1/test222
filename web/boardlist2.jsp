<%@page import="kh.my.board.board.model.vo.Board"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
	ArrayList<Board> volist = (ArrayList<Board>)request.getAttribute("boardvolist"); 
	int startPage = (int)request.getAttribute("startPage");
	int endPage = (int)request.getAttribute("endPage");
	int pageCount = (int)request.getAttribute("pageCount");
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>게시판</h1>
<table border="2">
		<tr>
			<td>번호</td>
			<td>제목</td>
			<td>작성자</td>
			<td>날짜</td>
		</tr>
<% 		if(volist != null) {
		for(Board vo : volist) { 
%>		
		<tr>
			<td><a href="boardcontent2?no=<%=vo.getBno()%>"><%=vo.getBno()%></a></td>
			<td>
				<%
					// 답글 몇단계애 따라서 Re:붙여주기
					for(int i=0; i<vo.getBreLevel(); i++){
				%>
						Re:
				<% 		
					}
				%>	
			
				<%=vo.getTitle()%></td>
			<td><%=vo.getWriter()%></td>
			<td><%=vo.getCreateDate()%></td>
		</tr>
<% 		
		}}
%>			
	</table>

	<%
		if (startPage > 1){
	%>
		이전
	<%
		}
		for (int i = startPage; i <= endPage; i++) { 
	%>
	<a href="./boardlist2?pagenum=<%=i%>"> <%=i%>  </a>
	<%
		if (i != endPage) {
	%>
	,
	<%
		}
	}
	if (endPage < pageCount) {
	%>
		다음
	<%
	}
	%>
<br>
<a href="boardwrite2">글쓰기</a> 

</body>
</html>