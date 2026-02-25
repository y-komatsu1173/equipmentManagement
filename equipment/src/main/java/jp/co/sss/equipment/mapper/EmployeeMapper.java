package jp.co.sss.equipment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.sss.equipment.entity.StaffData;
/**
 * 社員情報テーブルマッパー
 * 
 */ 
@Mapper
public interface EmployeeMapper {
	/**
	 * ログインのためのID、パスワード検索
	 * 
	 * @param empId
	 * @param empPass
	 * @return ログインユーザーエンティティ
	 */
	StaffData findByEmpIdAndEmpPass(@Param(value = "empId") Integer empId, @Param(value = "empPass") String empPass);

}
