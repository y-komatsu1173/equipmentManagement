package jp.co.sss.equipment.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sss.equipment.dto.BorrowingValidationResult;
import jp.co.sss.equipment.dto.DetailListViewDto;
import jp.co.sss.equipment.entity.StaffData;
import jp.co.sss.equipment.mapper.BorrowingMapper;
import jp.co.sss.equipment.util.DateUtil;

/**
 *備品管理「貸出」サービス
 * @author 小松原
 */
@Service
public class BorrowingService {
	@Autowired
	BorrowingMapper borrowingMapper;

	//メール送信
	@Autowired
	MailService mailService;

	@Autowired
	HttpSession session;

	/**
	 * 貸出画面に現在貸出可能（手元にある）の備品を渡す
	 * @param name
	 * @return ReturnController
	 */
	public List<DetailListViewDto> borrowingFindView(String name) {
		return borrowingMapper.borrowingFind(name); //マッパーの処理を返す
	}

	/**
	 * 入力チェック
	 * @param equipmentIdList
	 * @param serialMap
	 * @param staffNoMap
	 * @param startDateMap
	 * @param limitDateMap
	 * @return
	 */
	public BorrowingValidationResult validateBorrowing(
			List<Integer> equipmentIdList,
			Map<String, String> serialMap,
			Map<String, String> staffNoMap,
			Map<String, String> startDateMap,
			Map<String, String> limitDateMap,
			Map<String, String> leaseReturnDateMap) {

		List<String> errorMessages = new ArrayList<>(); // エラーメッセージ格納用
		Set<Integer> errorEquipmentIds = new HashSet<>(); // エラー備品ID格納用
		Set<Integer> warningEquipmentIds = new HashSet<>(); // 警告備品ID格納用
		Set<Integer> normalEquipmentIds = new HashSet<>(); // 正常備品ID格納用
		LocalDate today = DateUtil.getToday();

		// 貸出対象選択チェック
		if (equipmentIdList == null || equipmentIdList.isEmpty()) {
			errorMessages.add("　貸出対象が選択されていません");

			//BorrowingValidationResultにインスタンス生成して格納する
			return new BorrowingValidationResult(errorMessages, errorEquipmentIds, warningEquipmentIds,
					normalEquipmentIds);
		}

		// 各備品ごとのチェック
		for (Integer id : equipmentIdList) {

			String key = id.toString();
			String serial = serialMap.get(key);
			String staffStr = staffNoMap.get(key);
			String startStr = startDateMap.get(key);
			String limitStr = limitDateMap.get(key);

			List<String> rowErrors = new ArrayList<>();

			// --- 未入力チェック ---
			if (staffStr == null || staffStr.isBlank()) {
				rowErrors.add("　使用者未選択");
			}
			if (startStr == null || startStr.isBlank()) {
				rowErrors.add("　貸出開始日未入力");
			}
			if (limitStr == null || limitStr.isBlank()) {
				rowErrors.add("　返却予定日未入力");
			}

			// --- 日付パース ---
			LocalDate startDate = null;
			LocalDate limitDate = null;

			if (startStr != null && !startStr.isBlank()) {
				startDate = LocalDate.parse(startStr);
			}
			if (limitStr != null && !limitStr.isBlank()) {
				limitDate = LocalDate.parse(limitStr);
			}

			// --- 日付相関チェック ---
			if (startDate != null && limitDate != null) {
				if (limitDate.isBefore(startDate)) {
					rowErrors.add("　返却予定日が開始日より前です");
				}
			}

			// リース返却日チェック
			String leaseReturnStr = leaseReturnDateMap.get(key);

			if (limitDate != null && leaseReturnStr != null && !leaseReturnStr.isBlank()) {

				//日付チェック　返却日がリース返却日を超えていないか
				LocalDate leaseReturnDate = LocalDate.parse(leaseReturnStr);

				if (limitDate.isAfter(leaseReturnDate)) {
					rowErrors.add("　リース返却日を超える返却予定日は選択できません");
				}
			}

			// --- エラーまとめ ---
			if (!rowErrors.isEmpty()) {
				errorEquipmentIds.add(id);
				errorMessages.add("【シリアル：" + (serial != null ? serial : "不明") + "】");
				errorMessages.addAll(rowErrors);
				continue;
			}

			// --- 警告／正常判定 ---
			if (startDate.isAfter(today)) {
				warningEquipmentIds.add(id);
			} else {
				normalEquipmentIds.add(id);
			}
		}
		return new BorrowingValidationResult(errorMessages, errorEquipmentIds, warningEquipmentIds, normalEquipmentIds);
	}

	/**
	 * 貸出更新
	 */
	@Transactional
	public void borrowingEquipment(
			List<Integer> equipmentIdList,
			Map<String, String> staffNoMap,
			Map<String, String> startDateMap,
			Map<String, String> limitDateMap,
			Map<String, String> leaseReturnDateMap) {
		for (Integer id : equipmentIdList) {
			// キーは ID そのもの（文字列）になっているはず
			String key = id.toString();

			// 備品IDをキーにしてMapから値を取得
			String staffStr = staffNoMap.get(key);
			String startStr = startDateMap.get(key);
			String limitStr = limitDateMap.get(key);
			String leaseReturnStr = leaseReturnDateMap.get(key);

			// 未入力がある場合弾く
			if (staffStr == null || staffStr.isBlank()
					|| startStr == null || startStr.isBlank()
					|| limitStr == null || limitStr.isBlank()) {
				continue;
			}

			LocalDate start = LocalDate.parse(startStr);
			LocalDate limit = LocalDate.parse(limitStr);

			// リース返却日チェック

			if (leaseReturnStr != null && !leaseReturnStr.isBlank()) {
				LocalDate leaseReturnDate = LocalDate.parse(leaseReturnStr);
				if (limit.isAfter(leaseReturnDate)) {
					throw new IllegalArgumentException("リース返却日を超える返却予定日は設定できません");
				}
			}

			if (!start.isAfter(limit)) {
				int updateCount = borrowingMapper.borrowingUpdate(
						id,
						Integer.valueOf(staffStr),
						start,
						limit);
				if (updateCount == 0) {
					throw new IllegalStateException("他のブラウザで更新済み");
				}
			}
		}
		//貸出時のメール送信処理
		StaffData user = (StaffData) session.getAttribute("user");
		if (user != null) {
			mailService.sendMail(
					"******@bg.co.jp",
					user.getMail(),
					"貸出が完了しました",
					"貸出処理が完了しました");
		}
	}

	/**
	 * キー洗浄す補助メソッド[]をとる
	 * @RequestParam Map<String, String> allParamsで受け取ったパラメータの中から、特定のプレフィックスを持つキーを抽出し、ID部分だけをキーとして新しいマップを作成するため
	 */
	public Map<String, String> extractIdMap(Map<String, String> params, String prefix) {
		// 結果を格納するマップ
		Map<String, String> resultMap = new HashMap<>();
		// 全てのパラメータをループ
		params.forEach((k, v) -> {
			// prefix + "[" で始まるキーを探す
			if (k.startsWith(prefix + "[")) {
				// キーからID部分を抽出して新しいマップに追加
				String id = k.substring(prefix.length() + 1, k.length() - 1);
				resultMap.put(id, v);
			}
		});
		return resultMap;
	}
}