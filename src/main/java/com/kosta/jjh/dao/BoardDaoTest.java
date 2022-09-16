package com.kosta.jjh.dao;

import java.util.Scanner;

import com.kosta.jjh.vo.BoardVo;
import com.kosta.jjh.vo.UserVo;

public class BoardDaoTest {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		BoardDao dao = new BoardDaoImpl();
		UserVo voo = new UserVo();
		//insert Test
		BoardVo vo = new BoardVo();
//		vo.setTitle("테스트");
//		vo.setContent("테스트");				
//		vo.setUserNo(1);
//		vo.setPos(1);
//		vo.setRef(0);
//		vo.setDepth(0);
//		vo.setFilename("테스트.txt");
//		vo.setFilename1("테스트1.txt");
//		vo.setFilesize((long)1000);
//		vo.setFilesize1((long)2000);
//		vo.setPass("1234");
		
//		int count = dao.insert(vo);
//		System.out.println(count + "건 insert 완료");
		
		//list
//		System.out.println(dao.getBoardList("", "", 0, 3));
		
		//delete Test
//		int count = 0;
//		count = dao.delete(58);
		
//		//update Test
//		vo.setNo(70);
//		vo.setTitle("테스트1");
//		vo.setContent("테스트1");
//		dao.update(vo);
		
		//search Test
		

		
		
	}

}
