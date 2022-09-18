<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!-- 파일 업로드 import -->

<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.io.File" %> 

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
<title>Mysite</title>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		
		<div id="content">
			<div id="board">
				<form class="board-form" method="post" action="/mysite/BoardfileServlet" enctype="multipart/form-data">
					<table class="tbl-ex" witdh="600" cellpadding="3" align="center">
						<tr>
							<th colspan="2">글쓰기</th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td><input type="text" name="title" value=""></td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<textarea id="content" name="content"></textarea><br><br>
							</td>
						<br>
						</tr>
						<tr>
						<br>
							<td class="label">파일찾기</td>
     						<td><input type="file" name="filename" size="50" maxlength="50"></td>
     					</tr>
     					<tr>
     					<br>
     						<td class="label">파일찾기</td>
     						<td><input type="file" name="filename1" size="50" maxlength="50"></td>
     					</tr>
					</table>
					<div class="bottom">
						<a href="/mysite/board?a=list&nowPage=${param.nowPage }">취소</a>
						<input type="submit" value="등록">
						
					</div>
				</form>				
			</div>
		</div>

		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
	</div><!-- /container -->
</body>
</html>		
		
