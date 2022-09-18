package com.kosta.jjh.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import com.kosta.jjh.vo.BoardVo;






public class BoardDaoImpl implements BoardDao {
	
	
	//데이터베이스 연동
  private Connection getConnection() throws SQLException {
    Connection conn = null;
    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
      conn = DriverManager.getConnection(dburl, "hr", "hr");
      //conn = DriverManager.getConnection(dburl, "webdb", "1234");
    } catch (ClassNotFoundException e) {
      System.err.println("JDBC 드라이버 로드 실패!");
    }
    return conn;
  }
  
  //게시물 리스트
	public List<BoardVo> getList() {

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVo> list = new ArrayList<BoardVo>();

		try {
			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select b.no, b.title, b.hit, b.reg_date, b.user_no, u.name "
					     + " from board b, users u "
					     + " where b.user_no = u.no "
					     + " order by no desc";
			
			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				int userNo = rs.getInt("user_no");
				String userName = rs.getString("name");
				
				BoardVo vo = new BoardVo(no, title, hit, regDate, userNo, userName);
				list.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
		
		return list;

	}

	//모든 정보를 다 가져온다.
	public BoardVo getBoard(int no) {		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVo boardVo = null;
		
		try {
		  conn = getConnection();
		  	
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = " select b.no, b.title, b.content, b.hit, b.reg_date, b.user_no, b.pos,b.ref,b.depth, u.name ,b.filename, b.filename2,b.filesize, b.filesize1 ,b.pass "
					     + " from board b, users u "
					     + " where b.user_no = u.no "
					     + " and b.no = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) {
				no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				int userNo = rs.getInt("user_no");
				int pos = rs.getInt("pos");
				int ref = rs.getInt("ref");
				int depth = rs.getInt("depth");
				String userName = rs.getString("name");
				String filename = rs.getString("filename");				
				String filename1 = rs.getString("filename2");
				long filesize = rs.getLong("filesize");
				long filesize1 = rs.getLong("filesize1");
				String pass = rs.getString("pass");
				
				boardVo = new BoardVo(no, title, content, hit, regDate, userNo, userName ,pos,ref,depth,filename, filename1,filesize,filesize1,pass);
			}			

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}		
		return boardVo;

	}
	//게시물 등록
	public int insert(BoardVo vo) {
		// 0. import java.sql.*;
				System.out.println("파일쓰기 3번입니다");
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				int count = 0;
				int ref = 0;
		        int pos = 0;// 새글이기에
		        int depth = 0;//

				try {
				  conn = getConnection();

			 
			        
			        String refsql = "select max(ref) from board";
			          pstmt = conn.prepareStatement(refsql);
			          // 쿼리 실행후 결과를 리턴
			          rs = pstmt.executeQuery();
			          if (rs.next()) {
			              ref = rs.getInt(1) + 1; // ref가장 큰 값에 1을 더해줌
			              // 최신글은 글번호가 가장 크기 때문에 원래 있던 글에서 1을 더해줌

			          }
				  
		      
					// 3. SQL문 준비 / 바인딩 / 실행
					String query = "insert into board values (seq_board_no.nextval, ?, ?, 0, sysdate, ? ,?,?,?, ?,?,?,?,?)";
					pstmt = conn.prepareStatement(query);

					pstmt.setString(1, vo.getTitle());
					pstmt.setString(2, vo.getContent());
					pstmt.setInt(3, vo.getUserNo());					
					pstmt.setInt(4, pos);
					pstmt.setInt(5, ref);
					pstmt.setInt(6, depth);
					pstmt.setString(7, vo.getFilename());					
					pstmt.setString(8, vo.getFilename1());
					pstmt.setLong(9, vo.getFilesize());
					pstmt.setLong(10, vo.getFilesize1());
					pstmt.setString(11, vo.getPass());
					
					
					
		      
					count = pstmt.executeUpdate();
					System.out.println("insert : " + vo.toString());
					// 4.결과처리
					System.out.println(count + "건 등록");
				} catch (SQLException e) {
					System.out.println("error:" + e);
				} finally {
					// 5. 자원정리
					try {
						if (pstmt != null) {
							pstmt.close();
						}
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException e) {
						System.out.println("error:" + e);
					}

				}

				return count;
	}
	
	//게시물 삭제
	public int delete(int no) {
		// 0. import java.sql.*;
		//DB 연동 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//처리 갯수 세기 위한 변수
		int count = 0;

		try {
			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			
			String query1 = "delete from board where no = ?";
			pstmt = conn.prepareStatement(query1);
			pstmt.setInt(1, no);
			
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 삭제");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return count;
	}
	
	//게시물 수정
	public int update(BoardVo vo) {
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
		  conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "update board set title = ?, content = ? where no = ? ";
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getNo());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 수정");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return count;
	}

	//총 게시물 수 
	@Override
	public int getTotalCount(String keyField, String keyWord) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int totalCount = 0;
		try {
			conn = getConnection();
			//키워드 값이 null이거나 비워져있다면 no의 값들
			if (keyWord.equals("null") || keyWord.equals("")) {
				sql = "select count(no) from board";
				pstmt = conn.prepareStatement(sql);
			} else {//아니라면 검색된 값의 전체 값들
				sql = "select count(*) from  board b , users u where b.user_no = u.no and " + keyField + " like ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + keyWord + "%");
			}
			rs = pstmt.executeQuery();
			if (rs.next()) {
				totalCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return totalCount;
	}
	
	
//게시물 리스트 및 검색 (페이징 메소드) 
	@Override
	public Vector<BoardVo> getBoardList(String keyField, String keyWord, int start, int end) {
		// 0. import java.sql.*;
			//데이터베이스 연동
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				Vector<BoardVo> vlist = new Vector<BoardVo>();
				try {
					conn = getConnection();
					System.out.println("!!!!" +keyField);
					System.out.println("@@@@" +keyWord);
					//키워드 널이거나 비웠으면 ~
					if (keyWord.equals("null") || keyWord.equals("")) {						
			            String query = "SELECT * \r\n" + 
			                     "        FROM(\r\n" + 
			                     "              SELECT ROWNUM AS RNUM, A.*\r\n" + 
			                     "                  FROM ( select b.no, b.title,b.content, b.hit, to_char(b.reg_date,'YYYY-MM-DD HH24:MI') as reg_date, b.user_no, b.pos,  b.ref, b.depth, b.filename, b.filename2, b.filesize,b.filesize1, b.pass ,u.name from board b, users u where b.user_no = u.no order by ref desc , pos asc ) A\r\n" + 
			                     "               WHERE ROWNUM <= ?+?\r\n" + 
			                     "            )\r\n" + 
			                     "       WHERE RNUM > ?";
			            pstmt = conn.prepareStatement(query);
			            pstmt.setInt(1, start);
			            pstmt.setInt(2, end);
			            pstmt.setInt(3, start);
			         } else {			        	 
			        	 //아니라면 입력한 검색값의 페이지를 출력
			            String query = "SELECT * \r\n"
			            		+ "			                        FROM(\r\n"
			            		+ "			                          SELECT ROWNUM AS RNUM, A.*\r\n"
			            		+ "			                          FROM ( select b.no, b.title, b.content, b.hit, to_char(b.reg_date,'YYYY-MM-DD HH24:MI') as reg_date, b.user_no, b.pos,  b.ref, b.depth,b.filename, b.filename2,b.filesize,b.filesize1,b.pass , u.name from board b, users u where b.user_no = u.no and " + keyField +" like ?  order by ref desc , pos asc ) A \r\n"
			            		+ "			                          WHERE ROWNUM <= ?+? \r\n"
			            		+ "			                          ) \r\n"
			            		+ "			                        WHERE RNUM > ?";
			            pstmt = conn.prepareStatement(query);
			            pstmt.setString(1, "%" + keyWord + "%");
			            pstmt.setInt(2, start);
			            pstmt.setInt(3, end);
			            pstmt.setInt(4, start);
			            
			            
			         }
//			         String query = "select b.no, b.title, b.hit, to_char(b.reg_date,'YYYY-MM-DD HH24:MI') as reg_date, b.user_no, u.name "
//			                    + " from board b, users u "
//			                    + " where b.user_no = u.no "
//			                    + " order by no desc";
			         rs = pstmt.executeQuery();
			         while (rs.next()) {
			            BoardVo vo = new BoardVo();
			            vo.setNo(rs.getInt("no"));
			            vo.setUserName(rs.getString("name"));
			            vo.setTitle(rs.getString("title"));
			            vo.setPos(rs.getInt("pos"));//답변위치값
			            vo.setRef(rs.getInt("ref"));//답변
			            vo.setDepth(rs.getInt("depth"));
			            vo.setHit(rs.getInt("hit"));
			            vo.setUserNo(rs.getInt("user_no"));
			            vo.setRegDate(rs.getString("reg_date"));
			            vo.setContent(rs.getString("content"));
			            vo.setFilename(rs.getString("filename"));
			            vo.setFilesize(rs.getInt("filesize"));
			            vo.setFilename1(rs.getString("filename2"));
			            vo.setFilesize1(rs.getInt("filesize1"));
			            vo.setPass(rs.getString("pass"));
			            
			           
			            vlist.add(vo);
				}
				}catch (SQLException e) {
					e.printStackTrace();
				}
				
				return vlist;
				
			}

	//조회 수 증가
	@Override
	public void upCount(BoardVo vo) {
		System.out.println("메인/답변 조회 수 " + vo + vo.getNo() +"마무리");
		System.out.println("");
		System.out.println("");
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			
			
			sql = "update board set hit=hit+1 where no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getNo());
			pstmt.executeUpdate();
			
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	//답변글 등록
	@Override
	public void replyBoard(BoardVo vo) {		
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      String sql = null;
	      try {
	         conn = getConnection();
	         //sql = "insert into board values (seq_board_no.nextval, ?, ?, 0, sysdate, 0,?,?,?,0,0,0,0,?)";
	         sql="insert into board(no,title,content,hit,user_no, pos,ref,depth,reg_date,pass)";
	         sql+=" values(seq_board_no.nextval,?,?,0,?,?,?,?,sysdate,?)";
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, vo.getTitle());
	         pstmt.setString(2, vo.getContent());	         
	         pstmt.setInt(3, vo.getUserNo());	         
	         pstmt.setInt(4, vo.getPos()+1);
	         pstmt.setInt(5, vo.getRef());
	         pstmt.setInt(6, vo.getDepth()+1);
	         pstmt.setString(7, vo.getPass());
	         
	         pstmt.executeUpdate();
	      } catch (Exception e) {
	         e.printStackTrace();
	      } 
	}
	//답변의 위치값 
	@Override
	public void replyUpBoard(int ref, int pos) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = getConnection();
			sql = "update Board set pos = pos + 1 where ref = ? and pos > ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ref);
			pstmt.setInt(2, pos);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
	
		
	
	
		
		
	

