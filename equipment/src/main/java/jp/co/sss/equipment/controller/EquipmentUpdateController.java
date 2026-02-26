package jp.co.sss.equipment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sss.equipment.entity.StockMaster;
import jp.co.sss.equipment.entity.StockTypeMaster;
import jp.co.sss.equipment.form.EquipmentForm;
import jp.co.sss.equipment.service.EquimentUpdateService;
import jp.co.sss.equipment.service.EquipmentCommonService;
import jp.co.sss.equipment.util.BeanCopy;
import jp.co.sss.equipment.util.EquipmentInputCheck;

/**
 * 備品編集
 */
@Controller
public class EquipmentUpdateController {
	@Autowired
	EquimentUpdateService equimentUpdateService;
	
	@Autowired
	EquipmentCommonService equipmentCommonService;

	@Autowired
	EquipmentInputCheck equipmentInputCheck;

	/*
	 * 備品編集入力画面表示
	 */
	@GetMapping("/equipment/update/input")
	public String equimentUpdateInput(Model model, String serialNo) {
		//カテゴリーのリストを取得
		List<StockTypeMaster> categoryList = equipmentCommonService.categoryFind();
		//シリアルナンバーから備品情報を取得
		StockMaster stockMaster = equimentUpdateService.equimentFind(serialNo);
		//取得した備品情報をフォームクラスにコピー
		EquipmentForm form = BeanCopy.copyDtoToUpdateForm(stockMaster);

		model.addAttribute("categoryList", categoryList);
		model.addAttribute("equipmentForm", form);
		model.addAttribute("detailName", stockMaster);
		StockMaster updateform = equimentUpdateService.equimentFind(serialNo);
		//ログイン機能追加後は、セッションチェックを実装
		return "equipmentUpdate/equipmentUpdateInput";
	}

	/*
	 * 備品編集確認画面
	 */
	@PostMapping("/equipment/update/check")
	public String equipmentUpdateCheck(@ModelAttribute("equipmentForm") EquipmentForm updateform,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model) {

		StockTypeMaster stockTypeMaster = null;
		//カテゴリIDからカテゴリ情報を取得
		if (updateform.getCategoryId() != null) {
			stockTypeMaster = equipmentCommonService.categoryFindCheck(updateform.getCategoryId());

			//入力タイプをフォームにセット
			updateform.setInputType(stockTypeMaster.getInputType());
		}

		//入力チェック
		equimentUpdateService.registCheck(updateform, result);

		if (updateform.getCategoryId() == null || stockTypeMaster == null) {
			result.rejectValue("categoryId", "error.categoryId", "カテゴリが未選択です");
		}

		//エラーがある場合、入力画面へ戻る
		if (result.hasErrors()) {
			List<StockTypeMaster> categoryList = equipmentCommonService.categoryFind();
			model.addAttribute("categoryList", categoryList);
			return "equipmentUpdate/equipmentUpdateInput";
		}

		//カテゴリ名をモデルにセット
		model.addAttribute("categoryName", stockTypeMaster.getName());
		model.addAttribute("equipmentForm", updateform);
		return "equipmentUpdate/equipmentUpdateCheck";
	}

	/**
	 * 備品登録入力画面へ戻る
	 */
	@PostMapping("/equipmentUpdateBack")
	public String equipmentUpdateBack(@ModelAttribute EquipmentForm updateform, Model model) {
		//カテゴリの取得
		List<StockTypeMaster> categoryList = equipmentCommonService.categoryFind();
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("equipmentForm", updateform);
		return "equipmentUpdate/equipmentUpdateInput";
	}

	/**
	 *備品編集完了
	 */
	@PostMapping("/equipment/update/complete")
	public String equipmentUpdateComplete(EquipmentForm updateform, Model model) {
		// リース返却日のNULLチェック
		equipmentInputCheck.leaseNullCheck(updateform);

		// stockCode 取得
		String serialNo = updateform.getStockCode();

		// 更新処理を Service に委譲
		equimentUpdateService.equipmentUpdate(updateform, serialNo);

		model.addAttribute("parentStockCode", updateform.getStockCode());
		return "equipmentUpdate/equipmentUpdateComplete";
	}
}