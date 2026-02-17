package jp.co.sss.equipment.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sss.equipment.entity.StockTypeMaster;
import jp.co.sss.equipment.form.EquipmentForm;
import jp.co.sss.equipment.service.EquipmentRegistService;
import jp.co.sss.equipment.util.EquipmentInputCheck;

/**
 * 備品登録コントローラー 
 *
 * @author 小松原
 */
@Controller
public class EquipmentRegistController {
	@Autowired
	EquipmentRegistService equipmentRegistService;

	@Autowired
	EquipmentInputCheck equipmentInputCheck;

	/**
	 * 備品入力画面表示
	 */
	@GetMapping("/equipmentRegistInput")
	public String equipmentRegistInput(Model model, @ModelAttribute EquipmentForm form) {
		//ログイン機能追加後は、セッションチェックを実装
		//カテゴリの取得
		List<StockTypeMaster> categoryList = equipmentRegistService.categoryFind();
		for (StockTypeMaster category : categoryList) {
			System.out.println(category);
		}
		model.addAttribute("categoryList", categoryList);
		return "equipmentRegist/equipmentRegistInput";
	}

	/**
	 * 備品登録確認画面
	 */
	@PostMapping("/equipmentRegistCheck")
	public String equipmentRegistCheck(
			@Valid @ModelAttribute EquipmentForm registform,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model) {
		
		StockTypeMaster stockTypeMaster = null;
		//カテゴリIDからカテゴリ情報を取得
		if (registform.getCategoryId() != null) {
			stockTypeMaster = equipmentRegistService.categoryFindCheck(registform.getCategoryId());

			//入力タイプをフォームにセット
			registform.setInputType(stockTypeMaster.getInputType());
		}

		//入力チェック
		equipmentRegistService.registCheck(registform, result);


		//エラーがある場合、入力画面へ戻る
		if (result.hasErrors()) {
		    List<StockTypeMaster> categoryList = equipmentRegistService.categoryFind();
		    model.addAttribute("categoryList", categoryList);
		    return "equipmentRegist/equipmentRegistInput";
		}
		
		//カテゴリ名をモデルにセット
		model.addAttribute("categoryName", stockTypeMaster.getName());
		model.addAttribute("equipmentForm", registform);

		return "equipmentRegist/EquipmentRegistCheck";
	}

	/**
	 * 備品登録入力画面へ戻る
	 */
	@PostMapping("/equipmentRegistBack")
	public String equipmentRegistBack(@ModelAttribute EquipmentForm registform, Model model) {
	    //カテゴリの取得
	    List<StockTypeMaster> categoryList = equipmentRegistService.categoryFind();
	    model.addAttribute("categoryList", categoryList);
	    model.addAttribute("equipmentForm", registform);
	    return "equipmentRegist/equipmentRegistInput";
	}

	/**
	 * 備品登録処理
	 */
	@PostMapping("/equipmentRegistComplete")
	public String equipmentRegistComplete(EquipmentForm registform) {
		//リース返却日のNULLチェック
		equipmentInputCheck.leaseNullCheck(registform);
		//備品登録挿入
		equipmentRegistService.equipmentRegistInsert(registform);
		return "equipmentRegist/equipmentRegistComplete";
	}
}
