package board.model;

import java.util.*;

public interface InterBoardDAO {

	void boardAdd(BoardDTO dto);

	List<BoardDTO> selectAll(Map<String,String> paraMap);

	BoardDTO selectOne(String seq);

	void updateboard(BoardDTO dto);

	void deleteboard(String seq);

	int getTotalCount(Map<String, String> paraMap);

	List<String> wordSearchShow(Map<String, String> paraMap);
	
}
