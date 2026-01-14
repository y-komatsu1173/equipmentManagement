package jp.co.sss.equipment.service;

import java.time.LocalDate;
import java.util.List;

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
	    List<Integer> staffNoList,
	    List<LocalDate> startDateList,
	    List<LocalDate> limitDateList //コントローラからの引数(HTMLからのデータ)
	) {
	    for (int i = 0; i < equipmentIdList.size(); i++) {//チェックの数だけ繰り返す
	        borrowingMapper.borrowingUpdate(//Mapperを呼び出す
	            equipmentIdList.get(i), //id
	            staffNoList.get(i),//社員id
	            startDateList.get(i),//貸出開始日
	            limitDateList.get(i)//貸出期限
	        );  //Listのi番目を一斉に渡しマッパーで処理をする
	    }
	}
}