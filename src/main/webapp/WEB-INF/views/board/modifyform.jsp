<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.kosta.jjh.vo.BoardVo" %>
<%@ page import="com.kosta.jjh.dao.BoardDao" %>
<%@ page import="com.kosta.jjh.dao.BoardDaoImpl" %>

<%
	BoardVo vo = new BoardVo();	
	String no = String.valueOf(request.getAttribute("no"));
	String nowPage = String.valueOf(request.getParameter("nowPage"));		
	String pass = request.getParameter("pass");	
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
<title>Mysite</title>
</head>
<body>
<script>
   function check(){
      var password = document.getElementById("pass");
      if(<%=pass%>!=password.value){
    	  alert("비밀번호가 틀렸습니다. 다시 입력해 주세요(숫자만 가능합니다.)");
         password.focus();
         return false;
      }
   }
</script>
	<div id="container">
		
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		
		<div id="content">
			<div id="board">
				<form class="board-form" method="post" action="/mysite/board">
					<input type="hidden" name="a" value="modify" />
					<input type="hidden" name="no" value=<%=no %> />
				
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글수정</th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td><input type="text" name="title" value="${boardVo.title}"></td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<textarea id="content" name="content">${boardVo.content}</textarea>
							</td>
						</tr>
						<c:if test="${boardVo.pos >0}">
						<tr height="40">
                    		<td width="150" align="center" size="10">비밀번호</td>
	                    <td width="450"><input type="password" id= "pass" name="pass" size="17" placeholder="숫자만 가능합니다."></td>
                		</tr>
                		</c:if>
					</table>
				
					<div class="bottom">
						<a href="/mysite/board?a=list&nowPage=<%=nowPage%>">취소</a>
						<input type="submit" value="수정" onClick="return check()" onKeyup="onlyNumber(this)">
					</div>
				</form>				
			</div>
		</div>

		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
	</div><!-- /container -->
</body>
</html>		
		
