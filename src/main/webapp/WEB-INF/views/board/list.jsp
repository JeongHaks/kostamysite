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


<!-- 페이징 변수 계산 -->
<%
	request.setCharacterEncoding("UTF-8");
	int no=0;
	int totalRecord = 0; //전체 레코드 수
	int numPerPage = 7; //페이지당 레코드 수 
	int pagePerBlock = 5; //블럭당 페이지 수 
	
	int totalPage = 0;   //전체 페이지 수
	int totalBlock = 0;  //전체 블럭수 

	int nowPage = 1;   // 현재페이지
	int nowBlock = 1;  // 현재블럭
	
	int start = 0; // 디비의 select 시작번호
	int end = 10;  //시작번호로 부터 가져올 select 갯수
	  
	int listSize = 0; //현재 읽어온 게시물의 수
	
  	
	//키워드 검색 시작 
	String keyWord = "", keyField = "";//keyWord = 검색할 문자열과 keyField = 찾기옵션의 이름,제목,일시,내용 
	
	
	//content 내용을 가져오기 위한 변수
	String content = request.getParameter("content");
	//키워드(컬럼이름)이 널이 아니라면 값을 넣어줘라
	if (request.getParameter("keyWord") != null) {
  		 keyWord = request.getParameter("keyWord");
  		keyField = request.getParameter("keyField");
  	}
	//키워드 검색 끝 
  	if (request.getParameter("reload") != null){
  		if(request.getParameter("reload").equals("true")) {
  			keyWord = "";
  			keyField = "";
  		}
  	}
  	
	//블럭처리를 위한 추가 코드
	//현재 페이지가 null이 아니라면 nowPage변수에 값을 넣어준다.
	if(request.getParameter("nowPage") != null){
		nowPage = Integer.parseInt(request.getParameter("nowPage"));
	}
	start = (nowPage * numPerPage)-numPerPage; 
	//만약에 현재 페이지가 11이라면 11 = (11*7) - 7 = 70
	//한 페이지당 레코드 수가 7이기 때문에 현재 페이지가 11이라면 11은 70~80에 들어간다.
  	
	end = numPerPage;
  	//기준값이 7이기 때문에 sql문에서 출력해줄 범위의 블럭수를 작성해줘서 수행한다.
  	
  	
  	totalRecord = Bmgr.getTotalCount(keyField, keyWord);           //전체 게시물 건수 -> BoardDaoImpl의 getTotalCount메서드로 이동
  	String pass=null;
  	totalPage = (int)Math.ceil((double)totalRecord / numPerPage);  //전체페이지수 (ceil란 정수 올림 ))
  	nowBlock = (int)Math.ceil((double)nowPage/pagePerBlock);       //현재블럭 계산  100/15 = 6.6666->7로 계산  
  	totalBlock = (int)Math.ceil((double)totalPage / pagePerBlock); //전체블럭계산	
  	Vector<BoardVo> blist = null; //list 사용을 하기위한 정의
  	if(request.getParameter("pass") !=null){
  		 pass = request.getParameter("pass");
  	}  	
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
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
			<!-- 검색 기능 form  -->
				<form id="searchform" name= "searchform" action="/mysite/board" method="post">
				<!-- 클릭 시 boardservlet의 list(equal)로 넘어가서 실행 action=a  -->
				 	<input type ="hidden" name="a" value="list"> 
					<!-- 찾기 form/table  -->
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
				
				
				<!-- 검색 기능 ~ -->
				
				<!-- 게시물에 게시글 출력 -->
				<!-- 상단 바 테이블 -->
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
					<!-- 게시판 리스트 보여줄 for문 -->
					<c:forEach var="vo" items="${list }" begin="0" step="1" end="${numPerPage }">
						<tr>
							<td>vo.no</td>
							<td>
								<c:if test="${vo.depth > 0}">
									<c:forEach var="cur" begin="0" end="${vo.depth}">
											&nbsp;
									</c:forEach>
								</c:if>
								<c:if test="${vo.depth != 0}">
									┗
								</c:if>
								<a href="/mysite/board?a=read&no=${vo.no}&ref=${vo.ref}&pos=${vo.pos}">${vo.title}</a>
							</td>
							<td>${vo.userName}</td>
							<td>${vo.hit}</td>
							<td>${vo.regDate}</td>
							<td><c:if test="${authUser.no == vo.user_no}">
									<a href="/mysite/board?a=delete&no=${vo.no}" class="del">삭제</a>
								</c:if>
							</td>
						</tr>
						
						
					</c:forEach>
					<!-- 페이징 -->
					<div class="pager">
					
					</div>
					<!-- 값들이 등록되어있지 않다면 -->
					<c:if test="${authUser != null }">
						<div class="bottom">
						<align="right">
						<!-- 글쓰기 버튼을 클릭 시 writeform.jsp 넣어가서 수행 -->
						<a href="/mysite/board?a=writeform&nowPage=${param.nowPage }&no=${boardVo.no}" id="new-book">글쓰기</a>
						</div>
					</c:if>				
				</div>
				<!-- 페이징 처리를 위한 form (action지정이 안되어있어서)현재위치 페이지로.. -->
				<form name="readFrm" method="get">
					<input type="hidden" name="num"> 
					<input type="hidden" name="nowPage" value="${param.nowPage }"> 
					<input type="hidden" name="keyField" value="${boardVo.keyField }"> 
					<input type="hidden" name="keyWord" value="${boardVo.keyWord }">
				</form>
			</div>
		
			<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
	  </div><!-- /container -->
</body>
</html>		