package jp.co.sss.equipment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.equipment.dto.EquipmentSearchDto;
import jp.co.sss.equipment.form.EquipmentSearchForm;
import jp.co.sss.equipment.mapper.EquipmentSearchMapper;

/**
 * 備品検索サービス
 */

@Service
public class EquipmentSearchService {

	@Autowired
	EquipmentSearchMapper equipmentSearchMapper;

	public List<EquipmentSearchDto> search(EquipmentSearchForm form) {
		return equipmentSearchMapper.search(form);
	}

	// 検索条件が1つでも入力されているか判定
	public boolean hasSearchCondition(EquipmentSearchForm form) {
		return (form.getName() != null && !form.getName().isBlank()) ||
				form.getStockType() != null ||
				(form.getStatus() != null && !form.getStatus().isBlank()) ||
				form.getOwnershipType() != null;
	}
}