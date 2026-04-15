package jp.co.sss.equipment.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * 棚卸し用サービス
 */
@Mapper
public interface InventoryMapper {

	/**
	 * 最終確認日の更新
	 * @param stockCode
	 */
	void updateConfirmDate(String stockCode);

}
