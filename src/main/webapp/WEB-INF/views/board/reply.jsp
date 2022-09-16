<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.kosta.jjh.vo.BoardVo" %>
<%@ page import="com.kosta.jjh.dao.BoardDao" %>
<%@ page import="com.kosta.jjh.dao.BoardDaoImpl" %>
<%@ page import="com.kosta.jjh.vo.UserVo" %>
<%

//조회 수 증가
	
	BoardVo vo = (BoardVo)session.getAttribute("vo");
	String nowPage = (String)request.getAttribute("nowPage"); //현재 페이지 받아오기
	String ref = (String)request.getAttribute("ref"); // 부모글 다음 작성하기 위해
	String pos = (String)request.getAttribute("pos"); //위치값 받아오기
	String depth = (String)request.getAttribute("depth"); //깊이 받아오기
	
	String.valueOf(request.getAttribute("no")); //번호를 받아오기 	
	UserVo vo1 = (UserVo)session.getAttribute("authUser"); //답변 작성 시 해당 글쓴이를 보여주기 위해!
 	int userNo = vo1.getNo();
	String pass = vo.getPass();
		
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script>    
function inNumber(){ //숫자만 입력받기 
    if(event.keyCode<48 || event.keyCode>57){
       event.returnValue=false;
    }
}

</script>

    <center>
        <h2>답변글 입력하기</h2>
 
        <form action="/mysite/board" method="post">
         <input type ="hidden" name="a" value="boardreply">
            <!-- 답변글 처리 페이지로 자료를 전달 -->
            <table width="600" border="1" bordercolor="gray" bgcolor="lightgray" style="border-collapse:collapse">
 
 				
                <tr height="40">
                    <td width="150" align="center">제목</td>
                    <td width="450"><input type="text" name="title" value="[답변] : <%=vo.getTitle() %> "
                        size="60"></td>
                </tr>
 
                <tr height="40">
                    <td width="150" align="center">비밀번호</td>
                    <td width="450"><input type="password" name="pass" size="60" placeholder="숫자만 가능합니다." onkeypress="inNumber();"></td>
                </tr>
 
                <tr height="40">
                    <td width="150" align="center">글내용</td>
                    <td width="450"><textarea rows="10" cols="60" name="content">
 >> <%=vo.getContent() %>

<==아래에 답변을 작성해주세요==>
                    </textarea></td>
                </tr>
                <tr>
                	<td>
                <!-- form에서 사용자로부터 입력 받지 않고 데이터를 넘김 -->
	                <input type="hidden" name="nowPage" value="<%=nowPage%>">
					 <input type="hidden" name="ref" value="<%=ref%>">
					 <input type="hidden" name="pos" value="<%=pos%>">
					 <input type="hidden" name="depth" value="<%=depth%>">
					 <input type="hidden" name="userNo" value="<%=userNo%>">
					 <input type="hidden" name="pass" value="<%=pass %>">
					 <td>
				 </tr>
                <tr height="40">
                	<td></td>
                	<td style="text-align:center">
<!-- 					<a href="/mysite/board">목록</a>-->
						<input type="button" value="뒤로가기" onClick="history.go(-1) ">
						<input type="submit" value="답변등록" >
						<input type=reset value=다시쓰기 >
					</td>
				</tr>
            </table>
			 </form>
    </center>
</body>
</html>
