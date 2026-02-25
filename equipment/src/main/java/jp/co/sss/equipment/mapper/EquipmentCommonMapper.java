package jp.co.sss.equipment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.sss.equipment.entity.StockTypeMaster;

/**
 * 備品登録・備品編集共通マッパー
 */
@Mapper
public interface EquipmentCommonMapper {
	/**
	 * カテゴリ取得
	 * @return
	 */
	List<StockTypeMaster> categoryFind();
	
	/**
	 * カテゴリIDからカテゴリ情報を取得
	 * @param categoryId
	 * @return
	 */
	StockTypeMaster findByCategoryId(@Param("categoryId") Integer categoryId);
}
