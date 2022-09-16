package com.kosta.jjh.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.kosta.jjh.dao.BoardDao;
import com.kosta.jjh.dao.BoardDaoImpl;
import com.kosta.jjh.util.WebUtil;
import com.kosta.jjh.vo.BoardVo;
import com.kosta.jjh.vo.UserVo;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/BoardfileServlet")
public class BoardfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String CHARSET = "utf-8";
	//private static final String ATTACHES_DIR = "C:\\Users\\chall\\eclipse-workspace\\mysite\\src\\main\\webapp\\WEB-INF\\views\\fileupload";
	private static final String ATTACHES_DIR = "C:\\Users\\KOSTA\\eclipse-workspace\\mysite\\src\\main\\webapp\\WEB-INF\\views\\fileupload";
	private static final int LIMIT_SIZE_BYTES = 5 * 1024 * 1024;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding(CHARSET);
		PrintWriter out = response.getWriter();
		
		File attachesDir = new File(ATTACHES_DIR);
		
		String fileName="";
		MultipartRequest multi = new MultipartRequest(request, ATTACHES_DIR,LIMIT_SIZE_BYTES , "utf-8", new DefaultFileRenamePolicy());
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		fileItemFactory.setRepository(attachesDir);
		fileItemFactory.setSizeThreshold(LIMIT_SIZE_BYTES);
		ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
		
		
		
		try {
			List<FileItem> items = fileUpload.parseRequest(request);
			for(FileItem item : items) {
				if(item.isFormField()) {//텍스트 파일(true)
					System.out.printf("파라미터 명1 : %s, 파라미터 값 :  %s \n", item.getFieldName(), item.getString(CHARSET));
				}else {// 파일데이터이다.(false)
					System.out.printf("파라미터 명2 : %s, 파일 명 :  %s \n", item.getFieldName(), item.getName(), item.getSize());
					if(item.getSize() > 0) { //items크기가 0보다 크다면
						String separator = File.separator;
					    int index = item.getName().lastIndexOf(separator);
						fileName = item.getName().substring(index + 1);
						File uploadFile = new File(ATTACHES_DIR );
						item.write(uploadFile);
					}
				}
			}
			
			UserVo authUser = getAuthUser(request);
			int userNo = authUser.getNo();
			
			
			String title = multi.getParameter("title");
			String content = multi.getParameter("content");
			
			
			//파일 이름 크기 가져오기.

			
			Enumeration formNames = multi.getFileNames();

			String fileInput = "";
            String fileName1 = "";
            File fileObj1 = null;
            File fileObj2 = null;
            long fileSize = 0;
            long fileSize1 = 0;
       
            while(formNames.hasMoreElements()) {
               if(fileName=="") {
            	   fileInput = (String)formNames.nextElement();
                   fileName = multi.getFilesystemName(fileInput);
                   fileObj1 = multi.getFile(fileInput);
                   if(fileObj1!=null) {
                      fileSize = Long.parseLong(String.valueOf(fileObj1.length()));
                   }
                  
               } else {
                 
                   fileInput = (String)formNames.nextElement();
                   fileName1 = multi.getFilesystemName(fileInput);
                   fileObj2 = multi.getFile(fileInput);
                   if(fileObj2!=null) {
                      fileSize1 = Long.parseLong(String.valueOf(fileObj2.length()));
                   }
               }
               
            }
        	System.out.println("filename : ["+fileName+"]");
			System.out.println("filename1 : ["+fileName1+"]");
            BoardVo vo = new BoardVo(title, content, userNo,fileName ,fileSize,fileName1,fileSize1);
            BoardDao dao = new BoardDaoImpl();
            dao.insert(vo);
			System.out.println("파일업로드완료");
			
            
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		WebUtil.redirect(request, response, "/mysite/board?a=list");
	}
	// 로그인 되어 있는 정보를 가져온다.
	protected UserVo getAuthUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		return authUser;
	}

}
