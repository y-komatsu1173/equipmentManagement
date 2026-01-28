package jp.co.sss.equipment.dto;

import java.util.List;
import java.util.Set;

public class BorrowingValidationResult {

	/* エラーメッセージ一覧*/
	private List<String> errorMessages;

	/* 備品ID一覧（エラー）*/
	private Set<Integer> errorEquipmentIds;

	/* 備品ID一覧（警告） */
	private Set<Integer> warningEquipmentIds;

	/* 備品ID一覧（正常） */
	private Set<Integer> normalEquipmentIds;

	// バリデーション処理の結果をまとめて保持するためのコンストラクタ。
	// 必須項目をすべて受け取ることで、DTOを常に整合性の取れた状態で生成する。
	public BorrowingValidationResult(
			List<String> errorMessages,
			Set<Integer> errorEquipmentIds,
			Set<Integer> warningEquipmentIds,
			Set<Integer> normalEquipmentIds) {
		this.errorMessages = errorMessages;
		this.errorEquipmentIds = errorEquipmentIds;
		this.warningEquipmentIds = warningEquipmentIds;
		this.normalEquipmentIds = normalEquipmentIds;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public Set<Integer> getErrorEquipmentIds() {
		return errorEquipmentIds;
	}

	public Set<Integer> getWarningEquipmentIds() {
		return warningEquipmentIds;
	}

	public Set<Integer> getNormalEquipmentIds() {
		return normalEquipmentIds;
	}
}
