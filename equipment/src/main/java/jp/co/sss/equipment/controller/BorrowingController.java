package jp.co.sss.equipment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sss.equipment.dto.BorrowingValidationResult;
import jp.co.sss.equipment.dto.DetailListViewDto;
import jp.co.sss.equipment.entity.StaffData;
import jp.co.sss.equipment.service.BorrowingService;
import jp.co.sss.equipment.service.IndexService;
import jp.co.sss.equipment.util.DateUtil;

/**
 *備品管理「貸出」
 * @author 小松原
 */
@Controller
public class BorrowingController {
	@Autowired //DIの導入
	BorrowingService borrowingService;

	@Autowired
	IndexService indexService;

	/**
	 * 貸出画面
	 * @author 小松原
	 * @param model
	 * @param name
	 * @return　templates/index/returnView 貸出画面
	 */
	@GetMapping("/borrowing/view")
	public String borrowingView(Model model, String name) {
		List<DetailListViewDto> detailNameList = indexService.detailFind(name);
		DetailListViewDto detailName = detailNameList.get(0);
		; //備品名の取得
		model.addAttribute("detailName", detailName); //画面に渡す

		List<DetailListViewDto> borrowingList = borrowingService.borrowingFindView(name);
		List<StaffData> staffList = borrowingService.staffDataFind();

		model.addAttribute("itemDetail", borrowingList); //貸出リストの取得
		model.addAttribute("staffName", staffList); //使用者名の取得
		model.addAttribute("today", DateUtil.getToday().toString()); //今日の日付
		model.addAttribute("defaultLimit", DateUtil.getDefaultLimitDate().toString()); //デフォルト返却予定日
		return "borrowing/borrowingView";
	}

	/**
	 * 貸出処理
	 * @param equipmentIdList
	 * @param name
	 * @param model
	 * @return
	 */
	@PostMapping("/borrowing/process")
	public String borrowingProcess(
	        @RequestParam(value = "equipmentIdList", required = false) List<Integer> equipmentIdList,
	        @RequestParam Map<String, String> allParams, // 全てのパラメータを受け取る
	        @RequestParam String name,
	        RedirectAttributes redirectAttributes) {

	    // キーを "prefix[ID]" から "ID" だけに変換する
	    Map<String, String> staffNoMap = extractIdMap(allParams, "staffNoMap");
	    Map<String, String> startDateMap = extractIdMap(allParams, "startDateMap");
	    Map<String, String> limitDateMap = extractIdMap(allParams, "limitDateMap");
	    Map<String, String> serialMap = extractIdMap(allParams, "serialMap");

	    //サービス層でのバリデーション
	    BorrowingValidationResult result = borrowingService.validateBorrowing(
	            equipmentIdList, serialMap, staffNoMap, startDateMap, limitDateMap);

	    // エラーがある場合、リダイレクトしてエラーメッセージを表示
	    if (!result.getErrorMessages().isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessages", result.getErrorMessages());
	        redirectAttributes.addFlashAttribute("errorEquipmentIds", result.getErrorEquipmentIds());
	        redirectAttributes.addFlashAttribute("normalEquipmentIds", result.getNormalEquipmentIds());
	        redirectAttributes.addFlashAttribute("warningEquipmentIds", result.getWarningEquipmentIds());
	        // 入力値を保持
	        redirectAttributes.addFlashAttribute("prevStaffNoMap", staffNoMap);
	        redirectAttributes.addFlashAttribute("prevStartDateMap", startDateMap);
	        redirectAttributes.addFlashAttribute("prevLimitDateMap", limitDateMap);
	        redirectAttributes.addFlashAttribute("prevEquipmentIdList", equipmentIdList);

	        redirectAttributes.addAttribute("name", name);
	        return "redirect:/borrowing/view";
	    }

	    try {
	        borrowingService.borrowingEquipment(equipmentIdList, staffNoMap, startDateMap, limitDateMap);
	        redirectAttributes.addFlashAttribute("updateMessage", "貸出処理が完了しました。");
	    } catch (IllegalStateException e) { 
	        redirectAttributes.addFlashAttribute("errorMessages", List.of("他のブラウザで更新されました。"));
	        redirectAttributes.addAttribute("name", name);
	        return "redirect:/borrowing/view";
	    }

	    redirectAttributes.addAttribute("name", name);
	    return "redirect:/borrowing/view";
	}

	// キーを洗浄する補助メソッド[]をとる
	//→@RequestParam Map<String, String> allParamsで受け取ったパラメータの中から、特定のプレフィックスを持つキーを抽出し、ID部分だけをキーとして新しいマップを作成するため
	private Map<String, String> extractIdMap(Map<String, String> params, String prefix) {
	    // 結果を格納するマップ
		Map<String, String> resultMap = new HashMap<>();
	    // 全てのパラメータをループ
	    params.forEach((k, v) -> {
	    	// prefix + "[" で始まるキーを探す
	        if (k.startsWith(prefix + "[")) {
	        	// キーからID部分を抽出して新しいマップに追加
	            String id = k.substring(prefix.length() + 1, k.length() - 1);
	            resultMap.put(id, v);
	        }
	    });
	    return resultMap;
	}
}