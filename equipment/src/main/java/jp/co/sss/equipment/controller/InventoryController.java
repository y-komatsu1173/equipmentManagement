package jp.co.sss.equipment.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.sss.equipment.dto.EquipmentSearchDto;
import jp.co.sss.equipment.form.EquipmentSearchForm;
import jp.co.sss.equipment.form.InventoryForm;
import jp.co.sss.equipment.service.EquipmentCommonService;
import jp.co.sss.equipment.service.EquipmentSearchService;
import jp.co.sss.equipment.service.InventoryService;

/**
 * 棚卸しコントローラ
 */

@Controller
public class InventoryController {
	@Autowired
	EquipmentCommonService equipmentCommonService;

	@Autowired
	EquipmentSearchService equipmentSearchService;
	
	@Autowired
	InventoryService inventoryService;

	/**
	 * 棚卸し画面遷移
	 */
	@GetMapping("equipment/inventory")
	public String inventoryView(@ModelAttribute("searchForm") EquipmentSearchForm form,
	        Model model) {

	    // カテゴリ一覧を取得
	    model.addAttribute("categoryList", equipmentCommonService.categoryFind());

	    // 最初は全件
	    List<EquipmentSearchDto> resultList = equipmentCommonService.equipmentAllFind();

	    // 検索条件が1つでも入力されているか判定
	    boolean hasCondition = (form.getStockCode() != null && !form.getStockCode().isBlank()) ||
	            (form.getName() != null && !form.getName().isBlank()) ||
	            form.getStockType() != null ||
	            (form.getStatus() != null && !form.getStatus().isBlank()) ||
	            form.getOwnershipType() != null;

	    // 条件がある場合のみ検索結果で上書き
	    if (hasCondition) {
	        resultList = equipmentSearchService.search(form);
	    }

	    // 棚卸し用フォーム生成
	    InventoryForm inventoryForm = new InventoryForm();
	    inventoryForm.setInventoryList(resultList);

	    model.addAttribute("resultList", resultList);
	    model.addAttribute("inventoryForm", inventoryForm);

	    System.out.println(resultList);

	    return "inventory/inventoryView";
	}

	/*
	 * 棚卸し確認
	 */
	@PostMapping("/inventory/check")
	public String inventoryCheck(@ModelAttribute InventoryForm inventoryForm, Model model) {

		List<EquipmentSearchDto> checkedList = new ArrayList<>();

		for (EquipmentSearchDto dto : inventoryForm.getInventoryList()) {
			if (dto.isChecked()) {
				checkedList.add(dto);
			}
		}
		
		//空の場合
		if (checkedList.isEmpty()) {
			model.addAttribute("message", "対象が選択されていません");
			model.addAttribute("categoryList", equipmentCommonService.categoryFind());
			model.addAttribute("inventoryForm", inventoryForm);
			model.addAttribute("searchForm", new EquipmentSearchForm());
			return "inventory/inventoryView";
		}

		model.addAttribute("checkedList", checkedList);
		model.addAttribute("inventoryForm", inventoryForm);

		return "inventory/inventoryCheck";
	}
	
	/**
	 * 棚卸し処理
	 */
	@PostMapping("/inventory/complete")
	public String inventoryComplete(@ModelAttribute InventoryForm form) {

	    // チェックされた一覧をServiceへ渡す
	    inventoryService.inventoryUpdate(form.getInventoryList());

	    return "inventory/inventoryComplete";
	}
}
