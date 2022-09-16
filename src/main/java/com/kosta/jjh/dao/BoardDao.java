package com.kosta.jjh.dao;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import com.kosta.jjh.vo.BoardVo;



public interface BoardDao {
	public List<BoardVo> getList();  // 게시물 전체 목록 조회
	public BoardVo getBoard(int no); // 게시물 상세 조회
	public int insert(BoardVo vo);   // 게시물 등록
	public int delete(int no);       // 게시물 삭제
	public int update(BoardVo vo);   // 게시물 수정
	public int getTotalCount(String keyField, String keyWord); //총 게시물 수 
	public Vector<BoardVo> getBoardList(String keyField, String keyWord,int start, int end);//start = 시작값 / end = 끝값
	public void upCount(BoardVo vo); //조회 수 증가
	public void replyBoard(BoardVo vo); //게시물 답변
	public void replyUpBoard(int ref, int pos); //답변에 위치값 증가
	
	
}
