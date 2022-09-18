package com.kosta.jjh.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kosta.jjh.dao.BoardDao;
import com.kosta.jjh.dao.BoardDaoImpl;
import com.kosta.jjh.util.WebUtil;
import com.kosta.jjh.vo.BoardVo;
import com.kosta.jjh.vo.UserVo;


@MultipartConfig(
)
@WebServlet("/board")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//파일 위치
		
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			request.setCharacterEncoding("UTF-8");
			HttpSession session = request.getSession();
			String actionName = request.getParameter("a");
			System.out.println("board:" + actionName);
			
		if ("list".equals(actionName)) {
			// 리스트 가져오기
			
			BoardDao dao = new BoardDaoImpl();
			
			int totalRecord = 0; //전체 레코드 수
			int numPerPage = 3; //페이지당 레코드 수
			int pagePerBlock = 5; //블럭당 페이지 수 
			
			int totalPage=0;
			int totalBlock=0;
			
			int nowPage = 1;   // 현재페이지
			int nowBlock = 1;  // 현재블럭
			
			int start = 0; // 디비의 select 시작번호
			int end = 10;  //시작번호로 부터 가져올 select 갯수
			  
			int listSize=0;
			
			//키워드 검색 시작 keyWord = 검색할 문자열  / keyField = 찾기 value값  
		  	String keyWord = "", keyField = "";
		  	//컬럼이 널이 아니라면~
		  	if (request.getParameter("keyWord") != null) {
		  		keyWord = request.getParameter("keyWord");
		  		keyField = request.getParameter("keyField");
		  	}
		  	if (request.getParameter("reload") != null){
		  		if(request.getParameter("reload").equals("true")) {
		  			keyWord = "";
		  			keyField = "";
		  		}
		  	}
		  	
		  	if (request.getParameter("nowPage") != null) {
	            nowPage = Integer.parseInt(request.getParameter("nowPage"));
	         }
		  	
		  	start = (nowPage * numPerPage)-numPerPage;
			//만약에 현재 페이지가 11이라면 11 = (11*7) - 7 = 70
			//한 페이지당 레코드 수가 7이기 때문에 현재 페이지가 11이라면 11은 70~80에 들어간다.
		  	end = numPerPage;			
		  	
		  	
		  	
		  	totalRecord = dao.getTotalCount(keyField, keyWord);           //전체 게시물 건수 -> BoardDaoImpl의 getTotalCount메서드로 이동
		  	totalPage = (int)Math.ceil((double)totalRecord / numPerPage);  //전체페이지수 (ceil란 정수 올림 ))
		  	totalBlock = (int)Math.ceil((double)totalPage / pagePerBlock); //전체블럭계산	
		  	
			
			
			nowBlock = (int)Math.ceil((double)nowPage/pagePerBlock);       //현재블럭 계산  100/15 = 6.6666->7로 계산  
			
			
			int pageStart = (nowBlock -1)*pagePerBlock + 1 ; //하단 페이지 시작번호
			int pageEnd = ((pageStart + pagePerBlock ) <= totalPage) ?  (pageStart + pagePerBlock): totalPage+1;
			Vector<BoardVo> list = dao.getBoardList(keyField, keyWord, start, end);
			listSize = list.size();
			request.setAttribute("listSize", listSize);
			
		  	
		  	
		  	
		  	
		  //기준값이 7이기 때문에 sql문에서 출력해줄 범위의 블럭수를 작성해줘서 수행한다.
			
			
			// 리스트 화면에 보내기
			request.setAttribute("list", list);
			//WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			System.out.println("서블릿 list : " + list.toString());
			request.setAttribute("keyWord", keyWord);
		  	request.setAttribute("keyField", keyField);
			//현재 페이지를 받아온다. nowPage에 담아둔다.
		  	
			//
		  	request.setAttribute("totalRecord", totalRecord);
			request.setAttribute("numPerPage", numPerPage);
			request.setAttribute("pagePerBlock", pagePerBlock);
			
			request.setAttribute("totalPage", totalPage);
		  	request.setAttribute("totalBlock", totalBlock);
			request.setAttribute("nowPage", nowPage);
			request.setAttribute("nowBlock", nowBlock);
			request.setAttribute("pageStart", pageStart);
			request.setAttribute("pageEnd", pageEnd);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
			rd.forward(request, response);
	    
		} else if ("read".equals(actionName)) {
			// 게시물 가져오기
//			 HttpSession session = request.getSession(); //reply.jsp에 값을 전달해 title / content 내용을 불러오기 위한 세션
	         
			 int no = Integer.parseInt(request.getParameter("no"));
			 request.setAttribute("pass", request.getParameter("pass"));
	         request.setAttribute("no", no);
	         
	         BoardDao dao = new BoardDaoImpl();
	         BoardVo boardVo = dao.getBoard(no);
	         session.setAttribute("boardVo", boardVo);
	         System.out.println("서블릿 :ㅣ " + boardVo.toString());
			 dao.upCount(boardVo); //게시물 조회 시 조회증가 +1

			String nowPage = request.getParameter("nowPage");
			if (request.getParameter("nowPage") != null) {
	            nowPage = request.getParameter("nowPage");
	         }
			request.setAttribute("nowPage", nowPage);
			// 게시물 화면에 보내기
			request.setAttribute("boardVo", boardVo);
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
		} else if ("modifyform".equals(actionName)) {
			// 게시물 수정하기
			int no = Integer.parseInt(request.getParameter("no"));
			BoardDao dao = new BoardDaoImpl();
			BoardVo boardVo = dao.getBoard(no);
			session.setAttribute("boardVo", boardVo);
			request.setAttribute("no", no);

			// 게시물 화면에 보내기
			request.setAttribute("boardVo", boardVo);
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyform.jsp");
		} else if ("modify".equals(actionName)) {
			// 게시물 수정하기(1)
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardVo vo = new BoardVo(no, title, content);
			BoardDao dao = new BoardDaoImpl();
			//수정 서블릿->daoImpl
			dao.update(vo);
			
			WebUtil.redirect(request, response, "/mysite/board?a=list");
		} else if ("writeform".equals(actionName)) {
			// 글 쓰기 
			String nowPage = request.getParameter("nowPage");
			if (request.getParameter("nowPage") != null) {
	            nowPage = request.getParameter("nowPage");
	         }
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			request.setAttribute("title", title);
			request.setAttribute("content", content);
			request.setAttribute("nowPage", nowPage);
			
			UserVo authUser = getAuthUser(request);
			if (authUser != null) { // 로그인했으면 작성페이지로
				WebUtil.forward(request, response, "/WEB-INF/views/board/writeform.jsp");
			} else { // 로그인 안했으면 리스트로
				WebUtil.redirect(request, response, "/mysite/board?a=list");
			}

		} else if ("download".equals(actionName)) {
				//파일 다운로드 
			     String fileName = request.getParameter("filename");
		         
		         // 서버에 올라간 경로를 가져옴
		         ServletContext context = getServletContext();
		         //String uploadFilePath = "C:\\Users\\chall\\eclipse-workspace\\mysite\\src\\main\\webapp\\WEB-INF\\views\\fileupload";
		         String uploadFilePath = "C:\\Users\\chall\\eclipse-workspace\\mysite\\src\\main\\webapp\\WEB-INF\\views\\fileupload";
		         String filePath = uploadFilePath + File.separator + fileName;
		         		         		   		         
		         byte[] b = new byte[4096]; //바이트배열로 저장소만들어주기
		         FileInputStream fileInputStream = new FileInputStream(filePath);
		         
		         String mimeType = getServletContext().getMimeType(filePath);
		         if(mimeType == null) {
		            mimeType = "application/octet-stream";
		         }
		         response.setContentType(mimeType);
		         
		           // 파일명 UTF-8로 인코딩(한글일 경우를 대비)
		         String sEncoding = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
		         response.setHeader("Content-Disposition", "attachment; filename=\"" + sEncoding);
		        
		        // 파일 쓰기 OutputStream
		         ServletOutputStream servletOutStream = response.getOutputStream();
		           
		        int read;
		        while((read = fileInputStream.read(b,0,b.length))!= -1){
		               servletOutStream.write(b,0,read);            
		           }
		           
		        servletOutStream.flush();
		        servletOutStream.close();
		        fileInputStream.close();
//			
		} else if ("delete".equals(actionName)) { 
			//파일 삭제하기
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao dao = new BoardDaoImpl();
			//삭제 서블릿->daoImpl
			dao.delete(no);

			WebUtil.redirect(request, response, "/mysite/board?a=list");

		}else if("reply".equals(actionName)) {
			//게시물 답변 달기위한 값들 넘겨주는 역할(답변에 필요한 값들)
			 String nowPage = request.getParameter("nowPage");
	         request.setAttribute("nowPage", nowPage);
	         String ref = request.getParameter("ref");
	         request.setAttribute("ref", ref);
	         String pos = request.getParameter("pos");
	         request.setAttribute("pos", pos);
	         String depth = request.getParameter("depth");
	         request.setAttribute("depth", depth);
	         String userNo = request.getParameter("userNo");
	         request.setAttribute("userNo", userNo);	   
	         String pass = request.getParameter("pass");
	         request.setAttribute("pass", pass);
	         
			WebUtil.forward(request, response, "/WEB-INF/views/board/reply.jsp");
		}else if("boardreply".equals(actionName)) {
			//게시물 답변 달기
			BoardDao dao = new BoardDaoImpl();
			BoardVo vo = new BoardVo();
			//게시물 등록 및 답변의 위치값을 해주기 위해 값들을 set해준다.
			vo.setUserNo(Integer.parseInt(request.getParameter("userNo")));
			vo.setUserName(request.getParameter("username"));
			vo.setTitle(request.getParameter("title"));
			vo.setContent(request.getParameter("content"));
			vo.setPass(request.getParameter("pass"));
			vo.setRef(Integer.parseInt(request.getParameter("ref")));
			vo.setPos(Integer.parseInt(request.getParameter("pos")));
			vo.setDepth(Integer.parseInt(request.getParameter("depth")));
			String pass= request.getParameter("pass");
			//답변 위치값 증가 메서드
			dao.replyUpBoard(vo.getRef(), vo.getPos());
			//답변의 대한 값들 입력 메서드
			dao.replyBoard(vo);
			

			
			
			WebUtil.redirect(request, response, "/mysite/board?a=list&pass="+pass);
		}
		else {
			WebUtil.redirect(request, response, "/mysite/board?a=list");
		}
	}

	
	//dopost
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doGet(request,response);
	}
	// 로그인 되어 있는 정보를 가져온다.(UserVo)
	protected UserVo getAuthUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		return authUser;
	}

}
