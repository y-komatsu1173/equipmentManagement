package jp.co.sss.equipment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.co.sss.equipment.entity.AuthMaster;
import jp.co.sss.equipment.entity.StaffData;

/**
 *社員共通マッパー
 */
@Mapper
public interface StaffCommonMapper {

	/*
	 * 使用者・社員名の取得
	 */
	List<StaffData> staffFind();

	/*
	 * 個別詳細検索
	 */
	StaffData staffFindIndividual(Integer staffNo);

	/**
	 * 権限取得
	 * @return
	 */
	List<AuthMaster> authFind();

	/**
	 * 権限の取得１つのカテゴリ
	 * @return
	 */
	AuthMaster authFindById(Integer authNo);
	
	/**
	 * ユーザーの登録処理
	 * @param staffData
	 */
	void userRegistInsert(StaffData staffData);
}