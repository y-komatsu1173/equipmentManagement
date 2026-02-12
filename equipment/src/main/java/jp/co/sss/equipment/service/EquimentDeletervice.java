package jp.co.sss.equipment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.equipment.mapper.EquimentDeleteMapper;

/**
 * 備品削除サービス
 * @author 
 *
 */
@Service
public class EquimentDeletervice {
	@Autowired
	EquimentDeleteMapper equimentDeleteMapper;

	public void deleteBySerialNo(String serialNo) {
		equimentDeleteMapper.deleteBySerialNo(serialNo);		
	}

}
