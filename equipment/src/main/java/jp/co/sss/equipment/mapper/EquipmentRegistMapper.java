package jp.co.sss.equipment.mapper;

import org.apache.ibatis.annotations.Mapper;

import jp.co.sss.equipment.entity.StockData;
import jp.co.sss.equipment.entity.StockMaster;

/**
 * 備品登録マッパー
 *
 * @author 小松原
 */
@Mapper
public interface EquipmentRegistMapper {

	/**
	 * 備品登録挿入（idの追加）
	 */
	void equipmentRegistInsert(StockMaster stockMaster);	
	
	/**
	 * 備品登録挿入
	 * @param stockMaster
	 */
	void equipmentRegistUpdate(StockMaster stockMaster);

	/**
	 * 貸出開始処理挿入
	 * @param stockCode
	 */
	void insertForId(StockData stockData);
	
	/**
	 * 備品貸出開始挿入
	 * @param stockData
	 */
	void equimentBorrowingStartInsert(StockData stockData);


}
