package jp.co.sss.equipment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sss.equipment.dto.DetailListViewDto;
import jp.co.sss.equipment.service.IndexService;
import jp.co.sss.equipment.service.ReturnService;

/**
 *備品管理「返却」
 * @author 小松原
 */
@Controller
public class ReturnController {
	@Autowired
	ReturnService returnService;

	@Autowired
	IndexService indexService;

	/**
	 * 返却画面
	 * @author 小松原
	 * @param model
	 * @param name
	 * @return　templates/index/returnViw 返却画面
	 */
	@GetMapping("/return/view")
	public String returnView(Model model, String name) {
		List<DetailListViewDto> returnList = returnService.returnFindView(name); //備品名を取得する　サービス層で処理
		List<DetailListViewDto> detailName = indexService.detailFind(name);//貸出中の備品を取得する
		model.addAttribute("detailName", detailName.get(0));//備品名をひとつ取得し、HTMLに表示させる
		model.addAttribute("itemDetail", returnList);//貸出中の備品をHTMLのテーブルに表示させる
		return "return/returnView"; //templatesフォルダーのhtmlを表示させる
	}

	/**
	 * 返却処理
	 */
	@PostMapping("/return/process")
	public String returnProcess(
	        @RequestParam(value = "equipmentIdList", required = false) List<Integer> equipmentIdList,
	        @RequestParam(value = "name", required = false) String name,
	        RedirectAttributes redirectAttributes) {

	    if (equipmentIdList == null || equipmentIdList.isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "チェックが入っていません。");
	        redirectAttributes.addAttribute("name", name);
	        return "redirect:/returnView";
	    }
	    
	    // 返却処理の実行
	    try {
	        returnService.returnEquipment(equipmentIdList);
	        redirectAttributes.addFlashAttribute("updateMessage", "返却処理が完了しました。");
	        // 失敗時のメッセージ
	    } catch (IllegalStateException e) {
	        redirectAttributes.addFlashAttribute( "errorMessage", "他のブラウザで更新されています。返却処理は行えませんでした。");
	        redirectAttributes.addAttribute("name", name);
	        return "redirect:/returnView";
	    }

	    redirectAttributes.addAttribute("name", name);
	    return "redirect:/returnView";
	}
}