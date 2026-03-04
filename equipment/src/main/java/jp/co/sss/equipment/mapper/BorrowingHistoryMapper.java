package jp.co.sss.equipment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.co.sss.equipment.dto.BorrowingHistoryDto;
import jp.co.sss.equipment.form.BorrowingHistorySearchForm;

/**
 * 貸出履歴を表示するマッパー
 */
@Mapper
public interface BorrowingHistoryMapper {
	
	/**
	 * 貸出履歴を呼び出す
	 * @return
	 */
	List<BorrowingHistoryDto> findBorrowingHistory(BorrowingHistorySearchForm form);

}
