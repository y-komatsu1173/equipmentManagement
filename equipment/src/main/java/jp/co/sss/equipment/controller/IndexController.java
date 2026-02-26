package jp.co.sss.equipment.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sss.equipment.dto.DetailListViewDto;
import jp.co.sss.equipment.dto.EquipListViewDto;
import jp.co.sss.equipment.entity.StaffData;
import jp.co.sss.equipment.service.IndexService;

/**
 * 備品一覧
 * @author 小松原 2025/12/04
 */
@Controller
public class IndexController {
	@Autowired
	IndexService indexService;

	/**
	 * 一覧画面
	 * @author 小松原
	 * @param model
	 * @param name
	 * @return　templates/index/index 一覧画面
	 */
	@GetMapping("/index") //右のURLを入力するとページが遷移される
	public String index(Model model, HttpSession session) {//メソッド　model→viewに渡すデータ
		// 未ログインならログイン画面へ
		if (session.getAttribute("user") == null) {
			return "redirect:/";
		}

		List<EquipListViewDto> indexlist = indexService.indexFind();//サービス層のindexFindメソッドを呼び出し値をリストに返す
		model.addAttribute("items", indexlist);//indexに情報を渡す　”items”をindex.htmlのth:eactに一致させる
		StaffData user = (StaffData) session.getAttribute("user");
		model.addAttribute("loginUser", user);

		return "index/index"; //tenmplatsファルダーのindexのindex.htmlが呼出される
	}

	/**
	 * 詳細画面
	 * @author 小松原
	 * @param model
	 * @param name
	 * @return　templates/index/detail 詳細画面
	 */
	@GetMapping("/detail")
	public String detail(@RequestParam("name") String name, Model model) { //String name HTMLから検索したい値を引数として用意
		List<DetailListViewDto> detailList = indexService.detailFind(name);//サービス層のdetailFindメソッドを呼び出し値をリストに返す
		model.addAttribute("detailName", detailList.get(0)); //備品名が複数取得されるため１つ目だけ採取しHTMLに反映（１つだけのため）
		model.addAttribute("itemDetail", detailList);
		model.addAttribute("categoryName", name);
		return "index/detail";
	}

	/**
	 * 「貸出」「返却」の画面から戻るボタンを押下したとき
	 */
	@GetMapping("/detail/back")
	public String detailBack(Model model, String name) { //String name HTMLから検索したい値を引数として用意
		List<DetailListViewDto> detailList = indexService.detailFind(name);//サービス層のdetailFindメソッドを呼び出し値をリストに返す
		model.addAttribute("detailName", detailList.get(0)); //備品名が複数取得されるため１つ目だけ採取しHTMLに反映（１つだけのため）
		model.addAttribute("itemDetail", detailList);
		return "index/detail";
	}

	/**
	 * 個別詳細画面
	 */
	@GetMapping("/individual/detail")
	public String individualDetail(Model model, @RequestParam("serialNo") String serialNo) {
		DetailListViewDto detail = indexService.serialNoFind(serialNo);

		model.addAttribute("detailName", detail);
		model.addAttribute("itemDetail", detail);

		// 戻る用に備品名をセット
		model.addAttribute("nameForBack", detail.getEquipmentName());

		return "index/individualDetail";
	}
}