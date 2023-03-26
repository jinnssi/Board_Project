package board.model;

public class BoardDTO {
	
	private String seq; // 고유 번호
	private String name; // 닉네임
	private String title; // 제목
	private String content; // 내용
	private String markyn; // 강조 유무 
	
	public BoardDTO() {}
	
	public BoardDTO(String seq, String name, String title, String content) {
		super();
		this.seq = seq;
		this.name = name;
		this.title = title;
		this.content = content;
	}
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getMarkyn() {
		return markyn;
	}

	public void setMarkyn(String markyn) {
		this.markyn = markyn;
	}

	
}
