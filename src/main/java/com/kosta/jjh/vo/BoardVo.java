package com.kosta.jjh.vo;

public class BoardVo {

	private int no;
	private String title;
	private String content;
	private int hit;
	private String regDate;
	private int userNo;
	private String userName;
	private int ref;
	private int pos;
	private int depth;
	private String pass;
	//
	private String keyField;
	private String keyWord;
	//
	private String filename;
	private String filename1;
	private long filesize;
	private long filesize1;
	
	
	
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public long getFilesize1() {
		return filesize1;
	}
	public void setFilesize1(long filesize1) {
		this.filesize1 = filesize1;
	}
	public String getFilename1() {
		return filename1;
	}
	public void setFilename1(String filename1) {
		this.filename1 = filename1;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public long getFilesize() {
		return filesize;
	}
	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
	public String getKeyField() {
		return keyField;
	}
	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public int getRef() {
		return ref;
	}
	public void setRef(int ref) {
		this.ref = ref;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public BoardVo(int no, String title, String content, int hit, String regDate, int userNo, String userName) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
		this.userName = userName;
	}
	public BoardVo() {
		super();
	}

	
	public BoardVo(int no, String title, int hit, String regDate, int userNo, String userName) {
		super();
		this.no = no;
		this.title = title;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
		this.userName = userName;
	}
	public BoardVo(int no, String title, String content) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
	}
	public BoardVo(String title, String content, int userNo) {
		super();
		this.title = title;
		this.content = content;
		this.userNo = userNo;
	}
	
	//boardfilservlet 
	public BoardVo(String title, String content, int userNo ,String filename, long filesize ,String filename1 ,long filesize1) {
		super();
		this.title = title;
		this.content = content;
		this.userNo = userNo;
		this.filename = filename;
		this.filesize = filesize;
		this.filename1 = filename1;
		this.filesize1= filesize1;
	}

	
	
	//getboard
	public BoardVo(int no,String title,String content, int hit,String regDate,int userNo,String userName ,int pos, int ref, int depth,String filename ,String filename1,long filesize, long filesize1, String pass) {
		super();
		this.no=no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
		this.userName = userName;	
		this.pos = pos;
		this.ref= ref;
		this.depth = depth;
		this.filename = filename;		
		this.filename1= filename1;
		this.filesize= filesize;
		this.filesize1 = filesize1;
		this.pass = pass;
	}
	public BoardVo(String title, String content, int userNo, int ref, int pos, int depth) {
		super();
		this.title = title;
		this.content = content;
		this.userNo = userNo;
		this.ref = ref;
		this.pos = pos;
		this.depth = depth;
	}
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", regDate="
				+ regDate + ", userNo=" + userNo + ", userName=" + userName + ", ref=" + ref + ", pos=" + pos
				+ ", depth=" + depth + ", pass=" + pass + ", keyField=" + keyField + ", keyWord=" + keyWord
				+ ", filename=" + filename + ", filename1=" + filename1 + ", filesize=" + filesize + ", filesize1="
				+ filesize1 + "]";
	}
	
	//답변에 대한 활용 
	
	

	
	
	
	
}
