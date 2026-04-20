package jp.co.sss.equipment.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.sss.equipment.dto.PasswordCheckDto;
import jp.co.sss.equipment.entity.AuthMaster;
import jp.co.sss.equipment.entity.StaffData;
import jp.co.sss.equipment.form.UserForm;
import jp.co.sss.equipment.service.StaffCommonService;
import jp.co.sss.equipment.service.UserRegistService;
import jp.co.sss.equipment.service.UserUpdateService;
import jp.co.sss.equipment.util.BeanCopy;

/**
 * ユーザー情報編集コントローラ
 */

@Controller
public class UserUpdateController {

	@Autowired
	StaffCommonService staffCommonService;

	@Autowired
	UserRegistService userRegistService;

	@Autowired
	UserUpdateService userUpdateService;

	/**
	 * 編集画面遷移
	 */
	@GetMapping("/user/update/input/{staffNo}")
	public String updateInput(@PathVariable Integer staffNo, Model model) {
		List<AuthMaster> authList = staffCommonService.authFind();
		model.addAttribute("authList", authList);

		// ユーザーの個別詳細を取得
		StaffData staffData = staffCommonService.staffFindIndividual(staffNo);

		// entity → form にコピー
		UserForm form = BeanCopy.userCopyForm(staffData);
		model.addAttribute("userForm", form);

		return "userUpdate/userUpdateInput";
	}

	/**
	 * 入力画面に戻る
	 */
	@PostMapping("/user/update/input")
	public String updateBack(
			@ModelAttribute("userForm") UserForm userForm,
			Model model) {

		model.addAttribute("userForm", userForm);
		model.addAttribute("authList", staffCommonService.authFind());

		return "userUpdate/userUpdateInput";
	}

	/**
	 * 編集確認画面
	 */
	@PostMapping("/user/update/check")
	public String updateCheck(
			@Valid @ModelAttribute("userForm") UserForm updateForm,
			BindingResult result,
			Model model,
			HttpSession session) {

		// 権限情報の取得
		List<AuthMaster> authList = staffCommonService.authFind();
		model.addAttribute("authList", authList);

		// 入力エラー
		if (result.hasErrors()) {
			return "userUpdate/userUpdateInput";
		}

		// ID変更チェック
		if (!updateForm.getOldStaffNo().equals(updateForm.getStaffNo())) {
		    result.rejectValue("staffNo", null, "IDは変更できません");
		    return "userUpdate/userUpdateInput";
		}
		
		// ログインユーザー取得
		StaffData loginUser = (StaffData) session.getAttribute("user");

		// パスワード更新チェック
		PasswordCheckDto passwordCheck = userUpdateService.checkPasswordUpdate(updateForm, loginUser);

		// パスワード変更不可チェック
		if (passwordCheck.isPasswordChangeNotAllowed()) {
			result.rejectValue("password", null, "自分以外のパスワードは変更できません");
			return "userUpdate/userUpdateInput";
		}

		// 現在のパスワード未入力チェック
		if (passwordCheck.isCurrentPasswordRequired()) {
			result.rejectValue("currentPassword", null, "現在のパスワードを入力してください");
			return "userUpdate/userUpdateInput";
		}

		// 現在のパスワード一致チェック
		if (passwordCheck.isCurrentPasswordInvalid()) {
			result.rejectValue("currentPassword", null, "現在のパスワードが違います");
			return "userUpdate/userUpdateInput";
		}

		// 権限IDから権限情報を取得
		if (updateForm.getAuth() != null) {
			AuthMaster authMaster = staffCommonService.authFindById(updateForm.getAuth());
			model.addAttribute("authMaster", authMaster);
		}

		model.addAttribute("userForm", updateForm);

		System.out.println("完了確認画面");
		System.out.println(updateForm);
		return "userUpdate/userUpdateCheck";
	}

	/**
	 * 完了画面（UPDATE 処理）
	 */
	@PostMapping("/user/update/complete")
	public String updateComplete(@ModelAttribute("userForm") UserForm updateForm,
			HttpSession session) {
		//更新処理
		userUpdateService.userUpdate(updateForm);

		//ログインユーザー取得
		StaffData loginUser = (StaffData) session.getAttribute("user");

		System.out.println("完了画面");
		System.out.println(updateForm);

		// 自分自身を更新したならセッション更新
		if (loginUser != null && loginUser.getStaffNo().equals(updateForm.getOldStaffNo())) {
			StaffData newLoginUser = staffCommonService.staffFindIndividual(updateForm.getStaffNo());
			session.setAttribute("user", newLoginUser);
		}
		return "userUpdate/userUpdateComplete";
	}
}
