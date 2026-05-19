package jp.co.sss.equipment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jp.co.sss.equipment.dto.LeaseListDto;
import jp.co.sss.equipment.form.LeaseSearchForm;
import jp.co.sss.equipment.service.EquipmentCommonService;
import jp.co.sss.equipment.service.LeaseService;

/**
 * リース製品表示コントローラ
 */
@Controller
public class LeaseController {

	@Autowired
	LeaseService leaseService;

	@Autowired
	EquipmentCommonService equipmentCommonService;

	/**
	 * リース製品表示遷移
	 */
	@GetMapping("/leaseList")
	public String leaseListView(@ModelAttribute("searchForm") LeaseSearchForm form, Model model) {

		//カテゴリ一覧取得
		model.addAttribute("categoryList", equipmentCommonService.categoryFind());

		//リース製品一覧取得
		List<LeaseListDto> leaseList = leaseService.leaseListSertch(form);

		model.addAttribute("leaseList", leaseList);
		
		 System.out.println("==========リース備品一覧==========");
		    for (LeaseListDto dto : leaseList) {
		        System.out.printf(
		            "シリアル：%s / リース返却日：%s / 備品名：%s / カテゴリ：%s / 状態：%s / 返却期限：%s / 使用者：%s%n",
		            dto.getStockCode(),
		            dto.getLeaseReturnDate(),
		            dto.getProductName(),
		            dto.getCategoryName(),
		            dto.getStatusName(),
		            dto.getReturnDate(),
		            dto.getStaffName()
		        );
		    }
		return "index/leaseList";
	}
}
