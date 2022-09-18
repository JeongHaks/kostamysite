<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.kosta.jjh.dao.BoardDao" %>
<%@ page import="com.kosta.jjh.vo.BoardVo" %>
<%@ page import="com.kosta.jjh.dao.BoardDaoImpl" %>
<%@ page import="java.util.*" %>
<%@ page import="com.kosta.jjh.vo.UserVo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="Bmgr" class="com.kosta.jjh.dao.BoardDaoImpl" />



<%
	request.setCharacterEncoding("UTF-8");
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">
function list() {
	document.listFrm.action = "list.jsp";
	document.listFrm.submit();
}

function pageing(page) {
	document.readFrm.nowPage.value = page;
	if ('${kewWord}' != "") {
		document.readFrm.keyWord.value = '${kewWord}';
		document.readFrm.keyField.value = '${keyField}';
	}
	document.readFrm.submit();
}

function block(value){
	var param = '${pagePerBlock}';
	document.readFrm.nowPage.value = parseInt(param) * (value - 1) + 1; // 5 x (2 - 1) + 1 = 6
	if ('${kewWord}' != "") {
		document.searchform.keyWord.value = '${kewWord}';
		document.searchform.keyField.value = '${keyField}';
	}
	 document.readFrm.submit();
} 

function read(num){
	document.readFrm.num.value=num;
	document.readFrm.action="read.jsp";
	document.readFrm.submit();
}

function check() {
     if (document.searchform.keyWord.value == "") {
		alert("검색어를 입력하세요.");
		document.searchform.keyWord.focus();
		return;
     
	document.searchform.submit();
	}

 }


</script>
<title>Mysite</title>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		<div id="content">
			<div id="board">
			<%-- 검색 기능 form  --%>
				<form id="searchform" name= "searchform" action="/mysite/board" method="post">
				<%-- 클릭 시 boardservlet의 list(equal)로 넘어가서 실행 action=a  --%>
				 	<input type ="hidden" name="a" value="list"> 
					<%-- 찾기 form/table  --%>
					<table width="600" cellpadding="4" cellspacing="0">
					
						<tr>
							<td align="center" valign="bottom">
								<select id = "keyField" name="keyField" size="1">
								<option value="0">선택</option>
								<option value= "name"> 이 름 </option>
								<option value= "title"> 제 목 </option>
								<option value= "content"> 내 용 </option>
				   				<option value= "reg_date">일자 (ex)13) </option>
								<option value= "filename"> 파일1 </option>
								<option value= "filename2"> 파일2 </option>
								</select>
								<input type="text"  name="keyWord" placeholder="검색어를 입력하세요">
								<input type="submit" value="찾기" onClick="javascript:check()">
								<input type="hidden" name="nowPage" value="1">
							</td>
						</tr>
					</table>
				</form>
				
				
				<form name="listFrm" method="post">
					<input type="hidden" name="reload" value="true"> 
					<input type="hidden" name="nowPage" value="1">
				</form>
				
				
				<%-- 검색 기능 ~ --%>
				
				<%-- 게시물에 게시글 출력 --%>
				<%-- 상단 바 테이블 --%>
				<table class="tbl-ex" cellpadding="2" cellspacing="0">
					<tr align="center" bgcolor="#D0D0D0" height="120%">
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>		
					<tr>
						<c:if test="${fn:length(list) == 0}">
							<td colspan="6"> 등록된 게시물이 없습니다. </td>
						</c:if>
					</tr>	
					<%-- 게시판 리스트 보여줄 for문 --%>
					<c:set var="done_loop" value="false" />
					<c:forEach var="vo" items="${list }" begin="0" step="1" end="${numPerPage-1 }" varStatus="status">
						<c:if test="${not done_loop}">
                           <c:if test="${status.index eq requstScope.listSize}">
                              <c:set var="done_loop" value="true"/>
                           </c:if>
						<tr>
							<td>${vo.no}</td>
							<td>
								<c:if test="${vo.depth > 0}">
									<c:forEach var="cur" begin="0" end="${vo.depth}">
											&nbsp;
									</c:forEach>
								</c:if>
								<c:if test="${vo.depth != 0}">
									┗
								</c:if>
								<a href="/mysite/board?a=read&no=${vo.no}&ref=${vo.ref}&pos=${vo.pos}&nowPage=${requestScope.nowPage}">${vo.title}</a>
							</td>
							<td>${vo.userName}</td>
							<td>${vo.hit}</td>
							<td>${vo.regDate}</td>
							<td><c:if test="${authUser.no == vo.userNo}">
									<a href="/mysite/board?a=delete&no=${vo.no}" class="del">삭제</a>
								</c:if>
							</td>
						</tr>
						
						</c:if>
					</c:forEach>
					</table>
					<%-- 페이징 시작--%>
					<div class="pager">
						<c:if test="${ requestScope.totalPage ne 0 }">
						<%-- nowPage > 1 시작 --%>
							<c:if test="${requestScope.nowPage > 1 }">
								<c:choose>
									<c:when test="${ empty requestScope.keyWord }">
										<a href="/mysite/board?a=list&nowPage=${requestScope.nowPage-1 }">[이전]</a>&nbsp; 
									</c:when>
									<c:otherwise>
										<a href="/mysite/board?a=list&nowPage=${requestScope.nowPage-1 }&keyWord=${requestScope.keyWord }&keyField=${requestScope.keyField }">[이전]</a>
									</c:otherwise>
								</c:choose>
							</c:if> <%-- nowPage > 1 끝--%>
							
							<%-- for문 시작 --%>
							<c:set var="doneLoop" value="false"/>
							<c:set var="pageStart" value="${requestScope.pageStart }"/>
							<c:forEach var="cnt" begin="0" step="1" end="${requestScope.pageEnd}">
								<c:if test="${not doneLoop }">
								<c:choose>
									<c:when test="${ empty requestScope.keyWord }">
										<a href="/mysite/board?a=list&nowPage=${pageStart }">
											<c:if test="${pageStart eq requestScope.nowPage }">
												<font color="blue">
											</c:if>
											[${pageStart }]
											<c:if test="${pageStart eq requestScope.nowPage }">										
												</font>
											</c:if></a>
									</c:when>
									<c:otherwise>
										<a href="/mysite/board?a=list&nowPage=${pageStart }&keyWord=${requestScope.keyWord }&keyField=${requestScope.keyFiled}">
											<c:if test="${pageStart eq requestScope.nowPage }">
												<font color="blue">
											</c:if>
											[${pageStart }]
											<c:if test="${pageStart eq requestScope.nowPage }">										
												</font>
											</c:if></a>
									</c:otherwise>
								</c:choose>
								<c:if test="${pageStart ge requestScope.pageEnd }">
									<c:set var="doneLoop" value="true"/>
								</c:if>
								<c:set var="pageStart" value="${pageStart+1 }"/>
								</c:if>
							</c:forEach> <%-- for문 끝 --%>
							
							<%--다음 페이지  --%>
								<c:if test="${requestScope.totalPage > requestScope.nowPage }">
									<c:choose>
										<c:when test="${empty requestScope.keyWord }">
											<a href="/mysite/board?a=list&nowPage=${requestScope.nowPage+1 }">[다음]</a>
										</c:when>
										<c:otherwise>
											<a href="/mysite/board?a=list&nowPage=${requestScope.nowPage+1 }&keyWord=${requestScope.keyWord }&keyField=${requestScope.keyField }">[다음]</a>
										</c:otherwise>
									</c:choose>
								</c:if>
							
							
						</c:if> 
					</div>
					<%-- 페이징 끝 --%>
					<%-- 값들이 등록되어있지 않다면 --%>
					<c:if test="${authUser != null }">
						<div class="bottom">
						<align="right">
						<%-- 글쓰기 버튼을 클릭 시 writeform.jsp 넣어가서 수행 --%>
						<a href="/mysite/board?a=writeform&nowPage=${requestScope.nowPage }" id="new-book">글쓰기</a>
						</div>
					</c:if>				
				</div>
				<%-- 페이징 처리를 위한 form (action지정이 안되어있어서)현재위치 페이지로.. --%>
				<form name="readFrm" method="get">
					<input type="hidden" name="num"> 
					<input type="hidden" name="nowPage" value="${param.nowPage }"> 
					<input type="hidden" name="keyField" value="${boardVo.keyField }"> 
					<input type="hidden" name="keyWord" value="${boardVo.keyWord }">
				</form>
			</div>
		
			<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
	  </div><%-- /container --%>
</body>
</html>		