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

import jp.co.sss.equipment.entity.AuthMaster;
import jp.co.sss.equipment.form.UserForm;
import jp.co.sss.equipment.service.StaffCommonService;
import jp.co.sss.equipment.service.UserRegistService;

/**
 * ユーザー登録
 */
@Controller
public class UserRegistController {
	@Autowired
	StaffCommonService staffCommonService;
	
	@Autowired
	UserRegistService userRegistService;

	/**
	 * ユーザー登録画面
	 */
	@GetMapping("user/regist/input")
	public String registInput(Model model) {
		//権限情報の取得
		List<AuthMaster> authList = staffCommonService.authFind();
		model.addAttribute("authList", authList);
		model.addAttribute("userRegistForm", new UserForm());
		return "userRegist/userRegistInput";
	}
	
	/**
	 * 入力確認画面
	 */
	@PostMapping("user/regist/check")
	public String registCheck(
			@Valid @ModelAttribute("userRegistForm") UserForm registform,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model) {
		//権限情報の取得
		List<AuthMaster> authList = staffCommonService.authFind();
		model.addAttribute("authList", authList);

		// 入力チェックエラーがある場合
		if (result.hasErrors()) {
			return "userRegist/userRegistInput";
		}

		// ID重複チェック
		if (staffCommonService.idCheck(registform.getStaffNo())) {
			result.rejectValue("staffNo", null, "このIDはすでに使用されています");
			return "userRegist/userRegistInput";
		}
		
		
		//権限IDから権限情報を取得
		AuthMaster authMaster = null;

		//権限IDがnullでない場合、権限情報を取得
		if (registform.getAuth() != null) {
			authMaster = staffCommonService.authFindById(registform.getAuth());
			model.addAttribute("authMaster", authMaster);
		}
		//ユーザー登録情報をセッションに保存
		model.addAttribute("userRegistForm", registform);

		return "userRegist/userRegistCheck";
	}
	
	/*
	 * 登録処理(完了画面)
	 */
	@PostMapping("user/regist/complete")
	public String registComplete(UserForm registform) {
		//ユーザー登録処理
		userRegistService.userRegistInsert(registform);
		return "userRegist/userRegistComplete";
	}
	
}
