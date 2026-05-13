package jp.co.sss.equipment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.sss.equipment.dto.OverdueListDto;
import jp.co.sss.equipment.service.OverdueService;

/**
 * 返却遅延画コントローラ
 */

@Controller
public class OverdueController {

	@Autowired
	OverdueService overService;

	/**
	 * 返却遅延画面に遷移
	 */
	@GetMapping("/overdue")
	public String overdue(Model model) {
		List<OverdueListDto> overList = overService.returnExpiredList();
		model.addAttribute("overList", overList);
		
		System.out.println(overList);
		return "overdue";
	}
}
