package jp.co.sss.equipment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.equipment.dto.StaffViewDto;
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

	/**
	 * IDの重複チェック
	 * @param staffNo
	 * @return
	 */
	public boolean idCheck(Integer staffNo) {
		//ユーザーの取得
		List<StaffData> staffData = staffCommonMapper.staffFind();
		//idの一致検索
		for (StaffData staff : staffData) {
			if (staff.getStaffNo().equals(staffNo)) {
				return true; // 重複あり
			}
		}
		return false; // 重複なし
	}

	/**
	 * ユーザー情報をDTOにコピーして表示（一覧）
	 */
	public List<StaffViewDto> staffViewList() {
		List<StaffData> staffList = staffCommonMapper.staffFind();
		List<AuthMaster> authList = staffCommonMapper.authFind();

		//DTOのリストを作成
		List<StaffViewDto> result = new ArrayList<>();

		//スタッフデータをDTOにコピーしてリストに追加
		for (StaffData staff : staffList) {
			StaffViewDto dto = new StaffViewDto();
			dto.setStaffNo(staff.getStaffNo());
			dto.setName(staff.getName());
			dto.setAuthNo(staff.getAuthNo());

			//権限名を取得してDTOにセット
			for (AuthMaster auth : authList) {
				//スタッフの権限番号と一致する権限名を検索
				if (staff.getAuthNo().equals(auth.getAuthNo())) {
					dto.setAuthName(auth.getAuthName());
					break;
				}
			}
			result.add(dto);
		}
		return result;
	}
	
	/**
	 * ユーザー情報をDTOにコピーして表示（詳細）
	 */
	public StaffViewDto staffDetail(Integer staffNo) {
		//スタッフの個別情報を取得
		StaffData staff = staffCommonMapper.staffFindIndividual(staffNo);
		List<AuthMaster> authList = staffCommonMapper.authFind();
		
		//スタッフデータをDTOにコピー
		StaffViewDto dto = new StaffViewDto();
		dto.setStaffNo(staff.getStaffNo());
		dto.setName(staff.getName());
		dto.setAuthNo(staff.getAuthNo());
		
		//権限名を取得してDTOにセット
		for (AuthMaster auth : authList) {
			if (staff.getAuthNo().equals(auth.getAuthNo())) {
				dto.setAuthName(auth.getAuthName());
				break;
			}
		}

		return dto;
	}
	
	/**
	 * ID変更時に重複しているか判定
	 * @return 重複していたらtrue
	 */
	public boolean isDuplicateStaffNo(Integer oldStaffNo, Integer newStaffNo) {
		
		if (oldStaffNo == null || newStaffNo == null) {
			return false;
		}

		// ID変更していないなら重複チェック不要
		if (oldStaffNo.equals(newStaffNo)) {
			return false;
		}

		// 変更していて、かつそのIDが既に存在するなら重複
		return idCheck(newStaffNo);
	}
}
