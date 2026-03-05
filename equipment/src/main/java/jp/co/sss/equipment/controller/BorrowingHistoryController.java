package jp.co.sss.equipment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.sss.equipment.dto.BorrowingHistoryDto;
import jp.co.sss.equipment.entity.StockTypeMaster;
import jp.co.sss.equipment.form.BorrowingHistorySearchForm;
import jp.co.sss.equipment.service.BorrowingHistoryService;
import jp.co.sss.equipment.service.EquipmentCommonService;

/**
 * 備品管理貸出履歴
 */

@Controller
public class BorrowingHistoryController {
	@Autowired
	BorrowingHistoryService borrowingHistoryService;
	
	@Autowired
	EquipmentCommonService equipmentCommonService;

	/**
	 * 履歴画面への遷移
	 */
	@GetMapping("/equipment/history")
	public String equipmentHistoryView(BorrowingHistorySearchForm form, Model model) {
		List<BorrowingHistoryDto> historyList = borrowingHistoryService.findBorrowingHistory(form);

		//カテゴリの取得
		List<StockTypeMaster> categoryList = equipmentCommonService.categoryFind();

		//履歴確認
		for (BorrowingHistoryDto dto : historyList) {
			System.out.println(dto);
		}

		model.addAttribute("historyList", historyList);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("form", form);
		
		System.out.println("categoryList=" + categoryList);

		return "history/borrowingHistory";
	}
}
