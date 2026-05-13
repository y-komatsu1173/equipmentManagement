package jp.co.sss.equipment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.equipment.dto.LeaseListDto;
import jp.co.sss.equipment.form.LeaseSearchForm;
import jp.co.sss.equipment.mapper.LeaseMapper;

/**
 * リース一覧表示用サービス
 */
@Service
public class LeaseService {
	
	@Autowired
	LeaseMapper leaseMapper;
	
	/**
	 * リース製品取得
	 */
	public List<LeaseListDto> leaseListSertch(LeaseSearchForm form){
		return leaseMapper.leaseListSertch(form);
	}

}
