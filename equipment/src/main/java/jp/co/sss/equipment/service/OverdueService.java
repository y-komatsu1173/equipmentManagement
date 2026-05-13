package jp.co.sss.equipment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.equipment.dto.OverdueListDto;
import jp.co.sss.equipment.mapper.OverdueMapper;

/**
 * 返却遅延サービス
 */

@Service
public class OverdueService {

	@Autowired
	OverdueMapper overdueMapper;

	/**
	 * 返却期限切れ一覧取得
	 * @return
	 */
	public List<OverdueListDto> returnExpiredList() {
		return overdueMapper.returnExpiredList();
	}
}
