package jp.co.sss.equipment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.equipment.dto.BorrowingHistoryDto;
import jp.co.sss.equipment.form.BorrowingHistorySearchForm;
import jp.co.sss.equipment.mapper.BorrowingHistoryMapper;

/**
 * 貸出履歴を操作するサービス
 */
@Service
public class BorrowingHistoryService {
    @Autowired
    BorrowingHistoryMapper borrowingHistoryMapper;

    /**
     * 貸出履歴を表示する
     * @return
     */
    public List<BorrowingHistoryDto> findBorrowingHistory(BorrowingHistorySearchForm form) {
        return borrowingHistoryMapper.findBorrowingHistory(form);
    }

}
