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

	/**
	 * リース1ヶ月前
	 */
	List<LeaseListDto> findLeaseReturnAfterOneMonth();

	/**
	 * リース返却1週間前
	 */
	List<LeaseListDto> findLeaseReturnAfterOneWeek();

	/**
	 * リース返却1週間前
	 */
	List<LeaseListDto> findLeaseReturnAfterOneDay();

	/**
	 * リース通知の取得
	 */
	List<LeaseListDto> findLeaseAlerts();
	
	/**
	 * リース通知の取得
	 */
	List<LeaseListDto> findLeaseAlerts(Integer staffNo);
	
	/**
	 * 一般ユーザー通知
	 */
	List<LeaseListDto> findUserLeaseAlerts(Integer staffNo);
}
