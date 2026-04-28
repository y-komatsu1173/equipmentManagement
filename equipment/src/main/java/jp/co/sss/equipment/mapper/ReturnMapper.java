package jp.co.sss.equipment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.sss.equipment.dto.DetailListViewDto;

/**
 * 返却マッパー
 */
@Mapper
public interface ReturnMapper {
	//返却画面時に表示させる(貸出中のみ)
	List<DetailListViewDto> returnFind(@Param("name") String name);

//	//返却の更新 stock_master （故障時）
//	int rentFlagUpdate(List<String> stockCodes);
	
	//返却時の更新　stock_data
	int stockDataUpdate(List<Integer> stockCodes);
	
	//貸出可能のする 返却されたシリアルナンバーを新しくid生成
	void insertRebornStock(Integer id);
	
	//期限切れの備品を取得
	List<DetailListViewDto> equipmentReturnOver();
}
