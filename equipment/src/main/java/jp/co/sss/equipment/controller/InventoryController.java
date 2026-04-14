package jp.co.sss.equipment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jp.co.sss.equipment.dto.EquipmentSearchDto;
import jp.co.sss.equipment.form.EquipmentSearchForm;
import jp.co.sss.equipment.service.EquipmentCommonService;
import jp.co.sss.equipment.service.EquipmentSearchService;

/**
 * 棚卸しコントローラ
 */

@Controller
public class InventoryController {
	@Autowired
	EquipmentCommonService equipmentCommonService;
	
	@Autowired
	EquipmentSearchService equipmentSearchService;

	/**
	 * 棚卸し画面遷移
	 */
	@GetMapping("equipment/inventory")
	public String inventoryView(@ModelAttribute("searchForm") EquipmentSearchForm form,
	        Model model) {
		
		// カテゴリのリストを取得してモデルに追加
	    model.addAttribute("categoryList", equipmentCommonService.categoryFind());

	    // いずれかの検索条件が入力されているかをチェック
	    boolean hasCondition =
	            (form.getStockCode() != null && !form.getStockCode().isBlank()) ||
	            (form.getName() != null && !form.getName().isBlank()) ||
	            form.getStockType() != null ||
	            (form.getStatus() != null && !form.getStatus().isBlank()) ||
	            form.getOwnershipType() != null;

	    // いずれかの検索条件が入力されている場合に検索を実行
	    if (hasCondition) {
	        List<EquipmentSearchDto> resultList = equipmentSearchService.search(form);
	        model.addAttribute("resultList", resultList);
	    }

	    return "inventory/inventoryView";
	}
}
