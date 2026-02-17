package jp.co.sss.equipment.util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import jp.co.sss.equipment.dto.EquipmentInputValidationResult;
import jp.co.sss.equipment.form.EquipmentForm;

@Component
public class EquipmentInputCheck {
	/*
	 * 日付nullチェック
	 */
	public void leaseNullCheck(EquipmentForm registform) {
		if (registform.getLeaseReturnDate().isEmpty()) {
			registform.setLeaseReturnDate(null);
		}

	}

	/**
	 * カテゴリ入力チェック
	 * @param registform
	 * @param result
	 */
	public void categoryCheck(@Valid EquipmentForm registform, BindingResult result) {
	    String inputType = registform.getInputType();

	    // エラーメッセージ
	    List<String> rowErrors = new ArrayList<>();

	    if ("SIMPLE".equals(inputType)) {
	        validateSimple(registform, rowErrors);
	    } else if ("FULL".equals(inputType)) {
	        validateFull(registform, rowErrors);
	    }

	    // ← ここで重複排除を追加
	    rowErrors = new ArrayList<>(new LinkedHashSet<>(rowErrors));

	    // エラーをBindingResultへ
	    for (String err : rowErrors) {
	    	 result.rejectValue("equipmentName", "error.equipmentName", err);
	    }
	}

	/**
	 * SIMPLE入力チェック
	 */
	private EquipmentInputValidationResult validateSimple(EquipmentForm registform, List<String> rowErrors) {

		Integer categoryId = registform.getCategoryId();
		String equipmentName = registform.getEquipmentName();
		String rentFlag = registform.getRentFlag();

		if (categoryId == null || categoryId == 0) {
			rowErrors.add("カテゴリが未選択です");
		}
		if (equipmentName == null || equipmentName.isBlank()) {
			rowErrors.add("備品名が未入力です");
		}
		if (rentFlag == null || rentFlag.isBlank()) {
			rowErrors.add("貸出可否が未入力です");
		}

		// SIMPLEでは不要項目を初期化
		registform.setModel(null);
		registform.setMaker(null);
		registform.setOwnershipType("1");
		registform.setLeaseReturnDate(null);
		return new EquipmentInputValidationResult(rowErrors);
	}

	/**
	 * FULL入力チェック
	 */
	private EquipmentInputValidationResult validateFull(EquipmentForm registform, List<String> rowErrors) {

		Integer categoryId = registform.getCategoryId();
		String equipmentName = registform.getEquipmentName();
		String model = registform.getModel();
		String maker = registform.getMaker();
		String ownershipType = registform.getOwnershipType();
		String leaseReturnDate = registform.getLeaseReturnDate();
		String rentFlag = registform.getRentFlag();

		if (categoryId == null) {
			rowErrors.add("カテゴリが未選択です");
		}
		if (equipmentName == null || equipmentName.isBlank()) {
			rowErrors.add("備品名が未入力です");
		}
		if (model == null || model.isBlank()) {
			rowErrors.add("モデルが未入力です");
		}
		if (maker == null || maker.isBlank()) {
			rowErrors.add("メーカーが未入力です");
		}
		if (ownershipType == null || ownershipType.isBlank()) {
			rowErrors.add("分類が未入力です");
		}
		if (rentFlag == null || rentFlag.isBlank()) {
			rowErrors.add("貸出可否が未入力です");
		}

		// リース(2)のとき返却日必須
		if ("2".equals(ownershipType)
				&& (leaseReturnDate == null || leaseReturnDate.isBlank())) {
			rowErrors.add("リースの場合は返却日が必須です");
		}
		return new EquipmentInputValidationResult(rowErrors);
	}
}