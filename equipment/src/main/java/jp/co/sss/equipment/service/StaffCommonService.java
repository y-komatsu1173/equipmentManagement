package jp.co.sss.equipment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.equipment.entity.AuthMaster;
import jp.co.sss.equipment.entity.StaffData;
import jp.co.sss.equipment.mapper.StaffCommonMapper;

/**
 *社員共通サービス
 */
@Service
public class StaffCommonService {
	@Autowired
	private StaffCommonMapper staffCommonMapper;
	
	/**
	 * スタッフデータを取得
	 * @return
	 */
	public List<StaffData> staffDataFind() {
		return staffCommonMapper.staffFind();//マッパーの処理を返す
	}
	
	/*
	 * スタッフ個別詳細
	 */
	public StaffData staffFindIndividual(Integer staffNo) {
		return staffCommonMapper.staffFindIndividual(staffNo);
	}

	/**
	 * 権限の取得
	 * @return
	 */
	public List<AuthMaster> authFind() {
		return staffCommonMapper.authFind();
	}

	/**
	 * 権限のカテゴリ一個を取得
	 * @param auth
	 * @return
	 */
	public AuthMaster authFindById(Integer authNo) {
		return staffCommonMapper.authFindById(authNo);
	}
	
}