package jp.co.sss.equipment.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sss.equipment.dto.StaffViewDto;
import jp.co.sss.equipment.entity.StaffData;
import jp.co.sss.equipment.service.StaffCommonService;
import jp.co.sss.equipment.service.UserDeleteService;
/**
 * ユーザー削除コントローラ
 */

@Controller
public class UserDeleteController {

	@Autowired
	UserDeleteService userDeleteService;
	
	@Autowired
	StaffCommonService staffCommonService;
	
	/*
	 * ユーザー削除確認画面
	 */
	@GetMapping("/user/delete/view/{staffNo}")
	public String userDeleteView(@PathVariable Integer staffNo, Model model) {
		//ユーザーの個別詳細を取得
		StaffViewDto staff = staffCommonService.staffDetail(staffNo);
		model.addAttribute("staff", staff);
		return "userDelete/userDeleteView";
	}
	
	/*
	 * ユーザー削除処理（論理削除）削除完了画面
	 */
	@PostMapping("/user/delete/complete")
	public String userDeleteComplete(@RequestParam Integer staffNo, HttpSession session) {
		
		// ログインユーザー取得
		StaffData loginUser = (StaffData) session.getAttribute("user");

		// 自分自身の削除は禁止
		if (loginUser != null && loginUser.getStaffNo().equals(staffNo)) {
			return "redirect:/user/detail/" + staffNo;
		}
		
		//ユーザー削除
		userDeleteService.userDelete(staffNo);
		return "userDelete/userDeleteComplete";
	}
	
}
