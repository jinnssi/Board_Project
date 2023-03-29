package board.model;

import java.util.*;
import java.util.Map.Entry;

public class BoardDAO implements InterBoardDAO {
	private static LinkedHashMap<String,BoardDTO> map = new LinkedHashMap<>();
	// 게시물 추가(add)해주는 메소드
	@Override
	public void boardAdd(BoardDTO dto){ 
		String yn = dto.getMarkyn();
		String boardseq="";
		int seq = map.size(); // 현재 맵에 담긴 갯수
		
		// 중요 표시를 한 게시물 일 경우 m+숫자
		boardseq = ("marky".equals(yn))? "m"+String.valueOf(++seq):String.valueOf(++seq);
		dto.setSeq(boardseq);
		map.put(boardseq , dto);
//		System.out.println("map.size()=>"+map.size());
	}

	
	// 모든 행들을 select 해주는 메소드
	@Override
	public List<BoardDTO> selectAll(Map<String,String> paraMap) throws NullPointerException {
		List<BoardDTO> boardList  = new ArrayList<>();
		List<BoardDTO> keyList = new ArrayList<>();
		int startRno = Integer.parseInt(paraMap.get("startRno"));// 시작 행번호
		int endRno = Integer.parseInt(paraMap.get("endRno"));// 끝 행번호
		endRno = (map.size()<endRno)? map.size():endRno;

		System.out.println("startRno=>"+startRno);
		System.out.println("endRno=>"+endRno );
		String word = paraMap.get("searchWord");
		String searchType = paraMap.get("searchType");
		// 검색 내용에 맞는 key를 구해서 그에 맞는 boarddto를 keylist에 넣어두기  
		Set<Entry<String, BoardDTO>> entrySet = map.entrySet();
		for(Entry<String, BoardDTO> entry : entrySet){
			if(word!="") {// 검색어 있을 경우 
				if("name".equals(searchType)) {// 이름으로 검색한경우 
					if( entry.getValue().getName().contains(word) ){
						keyList.add(entry.getValue());
					}
				}else if("title".equals(searchType)) {// 제목으로 검색한경우
					if( entry.getValue().getTitle().contains(word) ){
						keyList.add(entry.getValue());
					}
				}
			}else { // 검색어 없을 경우 
				keyList.add(entry.getValue());
			}
		}
		if(word!="") {// 검색어 있을 경우
			System.out.println("keyList.size()=>"+keyList.size());
			for(int i=startRno-1; i<endRno;i++) {
				boardList.add(keyList.get(i)); // startrno 부터 endrno 에 해당하는 boarddto 를 boardlist에 넣기   
			}
		}else {
			System.out.println("map.size()=>"+map.size());
			System.out.println("keyList.size()=>"+keyList.size());
			for(int i=startRno-1; i<endRno;i++) {
				boardList.add(keyList.get(i)); // startrno 부터 endrno 에 해당하는 boarddto 를 boardlist에 넣기   
			}
			
		}
		
		return boardList;
	}

	// (검색포함)총게시물건
	@Override
	public int getTotalCount(Map<String,String> paraMap) {
		String word = paraMap.get("searchWord");
		String type = paraMap.get("searchType");
		System.out.println("map.size()=>"+map.size());
		int cnt = 0;
		// value로 key 찾기
		if(word!="") {
			Set<Entry<String, BoardDTO>> entrySet = map.entrySet();
			for(Entry<String, BoardDTO> entry : entrySet){
				if("name".equals(type)) {// 이름으로 검색한경우 
					if( entry.getValue().getName().contains(word) ){
						++cnt;
					}
					
				}else if("title".equals(type)) {
					if( entry.getValue().getTitle().contains(word) ){
						++cnt;
					}
				}
			}
	   }else {
		   cnt=map.size();
	   }
		System.out.println("cnt=>"+cnt);
 	   return cnt;
	}
	
	
	// 특정 행을 select 해주는 메소드
	@Override
	public BoardDTO selectOne(String seq) {
		BoardDTO dto = null;   
		dto = (BoardDTO) map.get(seq);
		return dto; // 없으면 null
	}// end of public boardDTO_02 selectOne(String seq) throws SQLException {}------------

	
	// 특정 1개행을 update 해주는 메소드
	@Override
	public void updateboard(BoardDTO dto) {
		
		String yn = dto.getMarkyn();
		String boardseq="";
		String seq = dto.getSeq(); 
		if("marky".equals(yn)) {// 중요 표시한 게시물일경우
			boardseq = "m"+String.valueOf(seq);
			dto.setSeq(boardseq);
		}else {// 아닐 경우
			boardseq = String.valueOf(seq);
			dto.setSeq(boardseq);
		}
		map.put(boardseq , dto);

	}// end of public int updateboard(boardDTO_02 dto) throws SQLException {}------------

	// 특정 행 맵에서삭제 
	@Override
	public void deleteboard(String seq) {
		map.remove(seq);// 해당 키값의 맵 삭제
	}

	// 검색어 입력시 자동글 
	@Override
	public List<String> wordSearchShow(Map<String, String> paraMap) {
		String type = paraMap.get("searchType");
		String word = paraMap.get("searchWord");
		List<String> wordList = new ArrayList<>();
		// value로 key 찾기
		Set<Entry<String, BoardDTO>> entrySet = map.entrySet();
 	   boolean bool = false;
		for(Entry<String, BoardDTO> entry : entrySet){
		   
		   if("name".equals(type)) {// 이름으로 검색한경우 
			   for(String str:wordList) {
				   if(entry.getValue().getName().equals(str)) bool=true;
			   }
			   if(!bool) {
				   if( entry.getValue().getName().contains(word) ){
					   wordList.add(entry.getValue().getName());
				   }
			   }
		   }
		   else if("title".equals(type)) {
			   for(String str:wordList) {
				   if(entry.getValue().getTitle().equals(str)) bool=true;
			   }
			   
			   if(!bool) {
				   if( entry.getValue().getTitle().contains(word) ){
					   wordList.add(entry.getValue().getTitle());
				   }
			   }
		   }
	   
		   
	   }
 	   
 	  
		return wordList;
	}

	
}
