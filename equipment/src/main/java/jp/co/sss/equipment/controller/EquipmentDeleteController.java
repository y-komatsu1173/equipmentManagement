package jp.co.sss.equipment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sss.equipment.dto.DetailListViewDto;
import jp.co.sss.equipment.service.EquimentDeletervice;
import jp.co.sss.equipment.service.IndexService;

/**
 * 備品削除
 */
@Controller
public class EquipmentDeleteController {
	@Autowired
	IndexService indexService;

	@Autowired
	EquimentDeletervice equimentDeletervice;

	/**
	 * 削除確認画面
	 */
	@GetMapping("/equipment/delete/check")
	public String equipmentDeleteCheck(
			@RequestParam("serialNo") String serialNo,
			@RequestParam("name") String name,
			Model model) {

		DetailListViewDto detailList = indexService.serialNoFind(serialNo);
		model.addAttribute("detailName", detailList);
		model.addAttribute("itemDetail", detailList);
		model.addAttribute("categoryName", name); 
		model.addAttribute("parentStockCode", detailList.getParentStockCode());
		return "equipmentDelete/equipmentDeleteCheck";
	}

	/**
	 * 削除処理　削除完了画面へ遷移
	 */
	@PostMapping("/equipment/delete/complete")
	public String equipmentDeleteComplete(
			@RequestParam("serialNo") String serialNo,
			@RequestParam("name") String name,
			Model model) {

		model.addAttribute("categoryName", name);

		equimentDeletervice.deleteBySerialNo(serialNo);

		return "equipmentDelete/equipmentDeleteComplete";
	}
}