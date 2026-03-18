package jp.co.sss.equipment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jp.co.sss.equipment.entity.StaffData;
import jp.co.sss.equipment.service.StaffCommonService;

/**
 * ユーザー情報を表示
 */
@Controller
public class UserIndexController {
	@Autowired
	StaffCommonService staffCommonService;
	
//	@Autowired
//	UserIndexService userIndexService;
	
	/**
	 * ユーザー一覧を表示
	 */
	@GetMapping("/user/index")
	public String userView(Model model) {
		//ユーザー情報の取得
		List<StaffData> staffList = staffCommonService.staffDataFind();
		model.addAttribute("staffList", staffList);
		return "user/userView";
	}
	
	/**
	 * ユーザー詳細画面
	 */
	@GetMapping("/user/detail/{staffNo}")
	public String userDetail(@PathVariable Integer staffNo, Model model) {
		//ユーザーの個別詳細を取得
	    StaffData staffData = staffCommonService.staffFindIndividual(staffNo);
	    model.addAttribute("staff", staffData);
	    return "user/detail";
	}

}
