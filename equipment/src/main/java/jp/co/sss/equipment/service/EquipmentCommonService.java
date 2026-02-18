package jp.co.sss.equipment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.equipment.entity.StockTypeMaster;
import jp.co.sss.equipment.mapper.EquipmentCommonMapper;

/**
 * 備品登録・備品編集共通サービス
 */
@Service
public class EquipmentCommonService {
	@Autowired
	private EquipmentCommonMapper equipmentCommonMapper;
	
	/*
	 * 備品登録・編集に使用するDB操作
	 * カテゴリ検索
	 */
	public List<StockTypeMaster> categoryFind() {
		return equipmentCommonMapper.categoryFind();
	}
	
	/*
	 * 備品登録確認時に使用するDB操作
	 * カテゴリIDからカテゴリ情報を取得
	 */
	public StockTypeMaster categoryFindCheck(Integer categoryId) {
		return equipmentCommonMapper.findByCategoryId(categoryId);
	}
}
