package jp.co.sss.equipment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.equipment.dto.PasswordCheckDto;
import jp.co.sss.equipment.entity.StaffData;
import jp.co.sss.equipment.form.UserForm;
import jp.co.sss.equipment.mapper.StaffCommonMapper;
import jp.co.sss.equipment.mapper.UserUpdateMapper;

/**
 * ユーザー情報編集サービス
 */
@Service
public class UserUpdateService {

	@Autowired
	UserUpdateMapper userUpdateMapper;
	
	@Autowired
	StaffCommonMapper staffCommonMapper;
	/**
	 * 更新処理
	 * @param updateForm
	 */
	public int userUpdate(UserForm updateForm) {
		return userUpdateMapper.userUpdate(updateForm);
	}
	
	/**
	 * パスワードチェック
	 * @param form
	 * @param loginUser
	 * @return
	 */
	public PasswordCheckDto checkPasswordUpdate(UserForm form, StaffData loginUser) {

		PasswordCheckDto dto = new PasswordCheckDto();

		// 新しいパスワード未入力ならチェック不要
		if (form.getPassword() == null || form.getPassword().isEmpty()) {
			return dto;
		}

		// 自分以外のパスワード変更は禁止
		if (!loginUser.getStaffNo().equals(form.getOldStaffNo())) {
			dto.setPasswordChangeNotAllowed(true);
			return dto;
		}

		// 現在のパスワード未入力
		if (form.getCurrentPassword() == null || form.getCurrentPassword().isEmpty()) {
			dto.setCurrentPasswordRequired(true);
			return dto;
		}

		// DBのパスワード取得
		StaffData dbUser = staffCommonMapper.staffFindIndividual(form.getOldStaffNo());

		// 現在のパスワード不一致
		if (!dbUser.getPassword().equals(form.getCurrentPassword())) {
			dto.setCurrentPasswordInvalid(true);
		}

		// 確認パスワード未入力
				if (form.getCheckPassword() == null || form.getCheckPassword().isEmpty()) {
					dto.setCheckPasswordRequired(true);
				}

				// 確認パスワード不一致
				else if (!form.getPassword().equals(form.getCheckPassword())) {
					dto.setCheckPasswordInvalid(true);
				}

				return dto;
	}
	
	

}
