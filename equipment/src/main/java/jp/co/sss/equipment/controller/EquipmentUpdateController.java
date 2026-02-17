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
import jp.co.sss.equipment.service.EquipmentRegistService;
import jp.co.sss.equipment.util.BeanCopy;

/**
 * 備品編集
 */
@Controller
public class EquipmentUpdateController {
	@Autowired
	EquimentUpdateService equimentUpdateService;
	
	@Autowired
	EquipmentRegistService equipmentRegistService;

	/*
	 * 備品編集入力画面表示
	 */
	@GetMapping("/equipmentUpdateInput")
	public String equimentUpdateInput(Model model, String serialNo) {
		//カテゴリーのリストを取得
		List<StockTypeMaster> categoryList = equipmentRegistService.categoryFind();
		for (StockTypeMaster category : categoryList) {
			System.out.println(category);
		}
		//シリアルナンバーから備品情報を取得
		StockMaster stockMaster = equimentUpdateService.equimentFind(serialNo);
		//取得した備品情報をフォームクラスにコピー
		EquipmentForm form = BeanCopy.copyDtoToUpdateForm(stockMaster);

		model.addAttribute("categoryList", categoryList);
	    model.addAttribute("equipmentForm", form);
	    StockMaster updateform =equimentUpdateService.equimentFind(serialNo);
	    System.out.println(stockMaster);
		//ログイン機能追加後は、セッションチェックを実装
		return "equipmentUpdate/equipmentUpdateInput";
	}
	
	/*
	 * 備品編集確認画面
	 */
	@PostMapping("/equipmentUpdateCheck")
	public String equipmentUpdateCheck(@ModelAttribute ("equipmentForm") EquipmentForm updateform,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model) {
		
		StockTypeMaster stockTypeMaster = null;
		//カテゴリIDからカテゴリ情報を取得
		if (updateform.getCategoryId() != null) {
			stockTypeMaster = equipmentRegistService.categoryFindCheck(updateform.getCategoryId());

			//入力タイプをフォームにセット
			updateform.setInputType(stockTypeMaster.getInputType());
		}

		//入力チェック
		equimentUpdateService.registCheck(updateform, result);
		
		//エラーがある場合、入力画面へ戻る
				if (result.hasErrors()) {
				    List<StockTypeMaster> categoryList = equipmentRegistService.categoryFind();
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
	    List<StockTypeMaster> categoryList = equipmentRegistService.categoryFind();
	    model.addAttribute("categoryList", categoryList);
	    model.addAttribute("equipmentForm", updateform);
	    return "equipmentUpdate/equipmentUpdateInput";
	}
}
