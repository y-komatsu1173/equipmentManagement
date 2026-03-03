package jp.co.sss.equipment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.sss.equipment.dto.BorrowingHistoryDto;
import jp.co.sss.equipment.service.BorrowingHistoryService;

/**
 * 備品管理貸出履歴
 */

@Controller
public class BorrowingHistoryController {
	@Autowired
	BorrowingHistoryService borrowingHistoryService;

	/**
	 * 履歴画面への遷移
	 */
	@GetMapping("/equipment/history")
	public String equipmentHistoryView(Model model) {
		List<BorrowingHistoryDto> list =
	            borrowingHistoryService.findBorrowingHistory();

	    //履歴確認
	    for (BorrowingHistoryDto dto : list) {
	        System.out.println(dto);
	    }

	    model.addAttribute("historyList", list);

		return "history/borrowingHistory";
	}
}
