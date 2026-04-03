package jp.co.sss.equipment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sss.equipment.mapper.UserDeleteMapper;

/**
 * ユーザー削除サービス
 */

@Service
public class UserDeleteService {

	@Autowired
	UserDeleteMapper userDeleteMapper;

	//ユーザー削除（論理削除）
	@Transactional
	public void userDelete(Integer staffNo) {
		userDeleteMapper.userDelete(staffNo);

	}

}
