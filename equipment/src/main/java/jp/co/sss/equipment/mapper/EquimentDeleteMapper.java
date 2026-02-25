package jp.co.sss.equipment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 備品削除マッパー
 * @author 小松原
 *
 */
@Mapper
public interface EquimentDeleteMapper {
	/**
	 * 削除処理(論理削除)
	 */
	void deleteBySerialNo(@Param("serialNo") String serialNo);
}
