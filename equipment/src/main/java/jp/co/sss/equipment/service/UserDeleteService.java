package jp.co.sss.equipment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.equipment.mapper.UserDeleteMapper;

/**
 * ユーザー削除サービス
 */

@Service
public class UserDeleteService {
	
@Autowired
UserDeleteMapper userDeleteMapper;
	
}
