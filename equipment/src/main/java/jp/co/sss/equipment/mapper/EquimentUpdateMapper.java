package jp.co.sss.equipment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.sss.equipment.entity.StockMaster;

/**
 * 備品削除マッパー
 * @author 小松原
 *
 */
@Mapper
public interface EquimentUpdateMapper {

	/*
	 * 備品編集のためのシリアルナンバーで検索
	 */
	StockMaster equimentFind(@Param("serialNo") String serialNo);
}
