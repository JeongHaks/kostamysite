<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page import="com.kosta.jjh.vo.BoardVo" %>
<%@ page import="com.kosta.jjh.dao.BoardDao" %>
<%@ page import="com.kosta.jjh.dao.BoardDaoImpl" %>

<%
pageContext.setAttribute( "newLine", "\n" );
%>
<jsp:useBean id="Bmgr" class="com.kosta.jjh.dao.BoardDaoImpl" />
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.io.*" %>

<%
	//조회 수 증가
	 
	 BoardDao dao = new BoardDaoImpl();	
	 int no = Integer.parseInt(String.valueOf(request.getAttribute("no")));
	 
	 
	//파일 		
	  BoardVo vo = dao.getBoard(no); 	 	 
	  String filename = vo.getFilename();
	  long filesize = vo.getFilesize();
	  long filesize1 = vo.getFilesize1();
	  String filename1 = vo.getFilename1();
	  //답변
	  String keyField = request.getParameter("keyField");
	  String keyWord = request.getParameter("keyWord");
	  String nowPage = request.getParameter("nowPage");
	  String pass = vo.getPass();
	  String password = (String)request.getAttribute("pass");
	  
      session.setAttribute("vo", vo); //답변에 원글 제목,내용값을 가져오기 위해 vo에 값들을 담아놓는다.
      
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
<title>Mysite</title>
</head>
<script>
    function list()
    {
        document.listForm.submit();    
    }
</script>
<form name=listForm method=post action=List.jsp>
    <input type="hidden" name="nowPage" value="<%=nowPage%>">
    <input type="hidden" name="keyField" value="<%=keyField%>">
    <input type="hidden" name="keyWord" value="<%=keyWord%>">
</form>
<br><br>
<body>
	<div id="container">
		
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>	
					<tr>
						<td class="label">제목</td>
						<td>${boardVo.title }</td>
					</tr>
					<tr>
		                  <td class="label">파일1</td>
		                  <td>
		                     <% if( filename !=null && !filename.equals("")) {%>
		                        <a href="/mysite/board?a=download&filename=${boardVo.filename }">${boardVo.filename }</a>
		                     &nbsp;&nbsp;<font color="blue">(${boardVo.filesize }KBytes)</font>  
		                        <%} else{%> 등록된 파일이 없습니다.<%}%>
		                  </td>
               		</tr>
               		<tr>
		                  <td class="label">파일2</td>
		                  <td>
		                     <% if( filename1 !=null && !filename1.equals("")) {%>
		                        <a href="/mysite/board?a=download&filename=${boardVo.filename1 }">${boardVo.filename1 }</a>
		                     &nbsp;&nbsp;<font color="blue">(${boardVo.filesize1 }KBytes)</font>  
		                        <%} else{%> 등록된 파일이 없습니다.<%}%>
		                  </td>
               		</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${fn:replace(boardVo.content, newLine, "<br>")}
							</div>
						</td>
					</tr>
				</table>
				<div class="bottom">
					<a href="/mysite/board">글목록</a>
					
					<c:if test="${authUser.no == boardVo.userNo }">
						<a href="/mysite/board?a=modifyform&no=<%=no%>&nowPage=<%=nowPage%>&pass=${boardVo.pass}">글수정</a>
					</c:if>
					<c:if test="${authUser.no !=null }">
					<a href="/mysite/board?a=reply&nowPage=<%=nowPage%>&ref=${boardVo.ref}&depth=${boardVo.depth}&pos=${boardVo.pos}&userNo=${boardVo.userNo}" >답 변</a>
					</c:if> 
				</div>
			</div>
		</div>

		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
	</div><!-- /container -->
</body>
</html>		
		
