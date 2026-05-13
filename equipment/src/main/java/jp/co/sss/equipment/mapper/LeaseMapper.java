package jp.co.sss.equipment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.co.sss.equipment.dto.LeaseListDto;
import jp.co.sss.equipment.form.LeaseSearchForm;

/**
 * リース一覧表示用マッパー
 */

@Mapper
public interface LeaseMapper {

	/**
	 * リース製品一覧取得
	 */
	public List<LeaseListDto> leaseListSertch(LeaseSearchForm form);
}
