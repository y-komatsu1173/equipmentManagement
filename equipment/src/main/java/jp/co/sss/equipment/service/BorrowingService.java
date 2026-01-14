package jp.co.sss.equipment.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sss.equipment.dto.DetailListViewDto;
import jp.co.sss.equipment.entity.StaffData;
import jp.co.sss.equipment.mapper.BorrowingMapper;

/**
 *備品管理「貸出」サービス
 * @author 小松原
 */
@Service
public class BorrowingService {
	@Autowired
	BorrowingMapper borrowingMapper;

	/**
	 * 貸出画面に現在貸出可能（手元にある）の備品を渡す
	 * @param name
	 * @return ReturnController
	 */
	public List<DetailListViewDto> borrowingFindView(String name) {
		return borrowingMapper.borrowingFind(name); //マッパーの処理を返す
	}

	/**
	 * スタッフデータを取得
	 * @return
	 */
	public List<StaffData> staffDataFind() {
		return borrowingMapper.staffFind();//マッパーの処理を返す
	}

	/**
	 * 貸出更新
	 */
	@Transactional
	public void borrowingEquipment(
	        List<Integer> equipmentIdList,
	        Map<String, String> staffNoMap,
	        Map<String, String> startDateMap,
	        Map<String, String> limitDateMap) {

	    for (Integer id : equipmentIdList) {
	        // HTML で生成される name に合わせて Map から値を取得
	        Integer staffNo = Integer.valueOf(staffNoMap.get("staffNoMap[" + id + "]"));
	        LocalDate startDate = LocalDate.parse(startDateMap.get("startDateMap[" + id + "]"));
	        LocalDate limitDate = LocalDate.parse(limitDateMap.get("limitDateMap[" + id + "]"));

	        borrowingMapper.borrowingUpdate(id, staffNo, startDate, limitDate);
	    }
	}
}