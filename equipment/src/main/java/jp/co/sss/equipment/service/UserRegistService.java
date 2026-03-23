package jp.co.sss.equipment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sss.equipment.entity.StaffData;
import jp.co.sss.equipment.form.UserForm;
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
	public void userRegistInsert(UserForm registform) {
		//formからentityへコピー
		StaffData staffData = BeanCopy.userCopyEntity(registform);
		//論理削除フラグを0に
		staffData.setDel("0");
		//登録
		userRegistMapper.userRegistInsert(staffData);
	}

}
