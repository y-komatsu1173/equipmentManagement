package jp.co.sss.equipment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.co.sss.equipment.entity.StockTypeMaster;

/**
 * 備品登録マッパー
 *
 * @author 小松原
 */
@Mapper
public interface EquipmentRegistMapper {

	/**
	 * カテゴリ取得
	 * @return
	 */
	public List<StockTypeMaster> categoryFind();
}
