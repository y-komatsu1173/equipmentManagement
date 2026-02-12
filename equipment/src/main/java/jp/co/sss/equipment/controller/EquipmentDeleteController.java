package jp.co.sss.equipment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.sss.equipment.dto.DetailListViewDto;
import jp.co.sss.equipment.service.EquimentDeletervice;
import jp.co.sss.equipment.service.IndexService;

/**
 * 備品削除
 */
@Controller
public class EquipmentDeleteController {
	@Autowired
	IndexService indexService;
	
	@Autowired
	EquimentDeletervice equimentDeletervice;

	/**
	 * 削除確認画面
	 */
	@GetMapping("/equipmentDeleteCheck")
	public String equipmentDeleteCheck(Model model, String serialNo) {
		DetailListViewDto detailList = indexService.serialNoFind(serialNo);
		model.addAttribute("detailName", detailList); //備品名が複数取得されるため１つ目だけ採取しHTMLに反映（１つだけのため）
		model.addAttribute("itemDetail", detailList);

		//値チェック
		DetailListViewDto detail = detailList;
		System.out.println("=========削除確認画面=========");
		System.out.println("備品名:" + detail.getEquipmentName());
		System.out.println("カテゴリ番号:" + detail.getStockType());
		System.out.println("シリアルNo:" + detail.getParentStockCode());
		System.out.println("使用者：" + detail.getStaffName());
		System.out.println("貸出可否：" + detail.getRentFlg());
		System.out.println("貸出開始日" + detail.getStartDate());
		System.out.println("返却予定日:" + detail.getLimitDate());
		System.out.println("最終所在確認日：" + detail.getConfirmedDate());
		System.out.println("親シリアル：" + detail.getConfirmedDate());
		System.out.println("製品名：" + detail.getProductName());
		System.out.println("メーカ名：" + detail.getMaker());
		System.out.println("分類：" + detail.getOwnershipType());
		System.out.println("リース返却日：" + detail.getLeaseReturnDate());
		System.out.println("備考：" + detail.getRemarks());
		System.out.println("シーケンスID" + detail.getEquipmentId());
		System.out.println("スタッフID" + detail.getStaffNo());
		System.out.println("貸出可否:" + detail.getRentFlag());
		return "equipmentDelete/equipmentDeleteCheck";
	}
	
	/**
	 * 削除処理　削除完了画面へ遷移
	 */
	@PostMapping("/equipmentDeleteComplete")
	public String equipmentDeleteComplete(String serialNo) {
		//論理削除処理
	    equimentDeletervice.deleteBySerialNo(serialNo);
	    return "equipmentDelete/equipmentDeleteComplete";
	}
}