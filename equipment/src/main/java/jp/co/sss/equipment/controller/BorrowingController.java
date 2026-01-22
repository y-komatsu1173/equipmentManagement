package jp.co.sss.equipment.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	@GetMapping("/borrowingView")
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

		//シーケンスidが取得できていない
		//デバッグ
		//		int num = 0;
		//		for (DetailListViewDto i : detailNameList) {
		//			num++;
		//			System.out.println("===========" + num + "===========");
		//			System.out.println("===========" + "貸出" + "===========");
		//			System.out.println("備品名:" + i.getEquipmentName());
		//			System.out.println("シリアル:" + i.getParentStockCode());
		//			System.out.println("使用者:" + i.getStaffName());
		//			System.out.println("貸出可否:" + i.getRentFlg());
		//			System.out.println("貸出開始日:" + i.getStartDate());
		//			System.out.println("返却予定日:" + i.getLimitDate());
		//			System.out.println("最終所在確認" + i.getConfirmedDate());
		//			System.out.println("備考:" + i.getRemarks());
		//			System.out.println("シーケンスID: " + i.getEquipmentId());
		//			System.out.println("スタッフID:" + i.getStaffNo());
		//		}
		return "borrowing/borrowingView";
	}

	/**
	 * 貸出処理
	 * @param equipmentIdList
	 * @param name
	 * @param model
	 * @return
	 */
	//ListからMapに変更
	//エラーあり01.02に未入力があると03のデータが合っていても処理されない
	@PostMapping("/borrowingProcess")
	public String borrowingProcess(
			@RequestParam(value = "equipmentIdList", required = false) List<Integer> equipmentIdList,
			@RequestParam Map<String, String> serialMap,
			@RequestParam Map<String, String> staffNoMap,
			@RequestParam Map<String, String> startDateMap,
			@RequestParam Map<String, String> limitDateMap,
			@RequestParam String name,
			RedirectAttributes redirectAttributes) {
		
		  List<String> errorMessages = new ArrayList<>();

		    if (equipmentIdList == null || equipmentIdList.isEmpty()) {
		        errorMessages.add("貸出対象が選択されていません");
		    }

		    if (!errorMessages.isEmpty()) {
		        redirectAttributes.addFlashAttribute("errorMessages", errorMessages);
		        redirectAttributes.addAttribute("name", name);
		        return "redirect:/borrowingView";
		    }

		    
		    for (Integer id : equipmentIdList) {
		        List<String> rowErrors = new ArrayList<>();
		        String serial = serialMap.get("serialMap[" + id + "]");
		        String staffStr = staffNoMap.get("staffNoMap[" + id + "]");
		        String startStr = startDateMap.get("startDateMap[" + id + "]");
		        String limitStr = limitDateMap.get("limitDateMap[" + id + "]");

		        if (staffStr == null || staffStr.isBlank()) {
		            rowErrors.add("使用者未選択");
		        }
		        if (startStr == null || startStr.isBlank()) {
		            rowErrors.add("貸出開始日未入力");
		        }
		        if (limitStr == null || limitStr.isBlank()) {
		            rowErrors.add("返却予定日未入力");
		        }

		        if (startStr != null && !startStr.isBlank()
		        		 && limitStr != null && !limitStr.isBlank()) {

		        		    LocalDate today = DateUtil.getToday();
		        		    LocalDate startDate = LocalDate.parse(startStr);
		        		    LocalDate limitDate = LocalDate.parse(limitStr);

		        		    // 貸出開始日が今日より前
//		        		    if (today.isAfter(startDate)) {
//		        		        rowErrors.add("貸出開始日が本日より前です");
//		        		    }

		        		 // 返却予定日が今日より前
		        		    if (limitDate.isBefore(today)) {
		        		        rowErrors.add("返却予定日が本日より前です");
		        		    }
		        		    // 返却予定日が貸出開始日より前
		        		    else if (limitDate.isBefore(startDate)) {
		        		        rowErrors.add("返却予定日が貸出開始日より前です");
		        		    }
		        		}

		        // このIDにエラーが1つでもあればまとめて追加
		        if (!rowErrors.isEmpty()) {
		        	errorMessages.add("【シリアル：" + serial + "】");
		            for (String msg : rowErrors) {
		                errorMessages.add(msg);
		            }
		        }
		    }

		    // エラーが1件でもあれば戻す
		    if (!errorMessages.isEmpty()) {
		        redirectAttributes.addFlashAttribute("errorMessages", errorMessages);
		        redirectAttributes.addAttribute("name", name);
		        return "redirect:/borrowingView";
		    }
		borrowingService.borrowingEquipment(
		        equipmentIdList,
		        staffNoMap,
		        startDateMap,
		        limitDateMap);

		System.out.println("備品名 : " + name);
		System.out.println("===== END =====");

		redirectAttributes.addAttribute("name", name);
		return "redirect:/borrowingView";
	}
}