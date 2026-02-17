package jp.co.sss.equipment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import jp.co.sss.equipment.entity.StockData;
import jp.co.sss.equipment.entity.StockMaster;
import jp.co.sss.equipment.entity.StockTypeMaster;
import jp.co.sss.equipment.form.EquipmentForm;
import jp.co.sss.equipment.mapper.EquipmentRegistMapper;
import jp.co.sss.equipment.util.BeanCopy;
import jp.co.sss.equipment.util.EquipmentInputCheck;

/**
 * 備品登録サービス
 *
 * @author 小松原
 */
@Service
public class EquipmentRegistService {
	@Autowired
	EquipmentRegistMapper equipmentRegistMapper;

	@Autowired
	EquipmentInputCheck equipmentInputCheck;
	/*
	 * 備品登録時に使用するDB操作
	 * カテゴリ検索
	 */
	public List<StockTypeMaster> categoryFind() {
		return equipmentRegistMapper.categoryFind();
	}

	/*
	 * 備品登録確認時に使用するDB操作
	 * カテゴリIDからカテゴリ情報を取得
	 */
	public StockTypeMaster categoryFindCheck(Integer categoryId) {
		return equipmentRegistMapper.findByCategoryId(categoryId);
	}
	
	/**
	 * 入力チェック
	 * @param form
	 * @param result
	 */
	public void registCheck(EquipmentForm form, BindingResult result) {

        // 入力タイプ別チェック
		equipmentInputCheck.categoryCheck(form, result);

        // ここでエラーあったら
        if (result.hasErrors()) {
            return;
        }
    }

	/*
	 * 備品登録挿入
	 */
	@Transactional
	public void equipmentRegistInsert(EquipmentForm registform) {
		// formからentityへコピー
		StockMaster stockMaster = BeanCopy.copyFormToStockMaster(registform);
		// 論理削除フラグを0に設定
		stockMaster.setDel("0");
		// 備品登録挿入
		equipmentRegistMapper.equipmentRegistInsert(stockMaster);
		// 登録後のIDを使って備品コードを生成・更新
		String stockCode = "S" + String.format("%09d", stockMaster.getId());
		stockMaster.setStockCode(stockCode);
		// 備品登録更新
		equipmentRegistMapper.equipmentRegistUpdate(stockMaster);
		//貸出開始処理
		StockData stockData = new StockData();
		stockData.setDel("0");
		equipmentRegistMapper.insertForId(stockData);
		stockData.setStockCode(stockCode);
		equipmentRegistMapper.equimentBorrowingStartInsert(stockData);
	}
}
