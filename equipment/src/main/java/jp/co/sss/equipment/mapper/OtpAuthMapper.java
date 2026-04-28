package jp.co.sss.equipment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.sss.equipment.entity.OtpAuth;

/**
 * ワンタイムパスードマッパー
 */
@Mapper
public interface OtpAuthMapper {

	/**
	 * ユーザーIDで取得
	 */
	OtpAuth findByStaffNo(@Param("staffNo")String staffNo);
	
	/**
	 * 登録
	 */
	int insert(OtpAuth otpAuth);
	
	/**
	 * 上書き更新
	 */
	int update(OtpAuth otpAuth);
	
	/**
	 * 削除
	 */
	int delete(@Param("staffNo")String staffNo);
	
	/**
	 * 始業回数インクリメント
	 */
	int incrementAttempt(@Param("staffNo")String staffNo);
}
