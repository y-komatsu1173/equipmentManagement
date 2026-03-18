package jp.co.sss.equipment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sss.equipment.entity.StaffData;
import jp.co.sss.equipment.form.UserRegistForm;
import jp.co.sss.equipment.mapper.StaffCommonMapper;
import jp.co.sss.equipment.mapper.UserRegistMapper;
import jp.co.sss.equipment.util.BeanCopy;

/**
 * ユーザー情報サービス
 */
@Service
public class UserRegistService {
	@Autowired
	UserRegistMapper userRegistMapper;

	@Autowired
	StaffCommonMapper staffCommonMapper;

	/**
	 *ユーザー登録処理
	 */
	@Transactional
	public void userRegistInsert(UserRegistForm registform) {
		//formからentityへコピー
		StaffData staffData = BeanCopy.userCopyForm(registform);
		//論理削除フラグを0に
		staffData.setDel("0");
		//登録
		userRegistMapper.userRegistInsert(staffData);
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
}
