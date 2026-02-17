package jp.co.sss.equipment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import jp.co.sss.equipment.entity.StockMaster;
import jp.co.sss.equipment.form.EquipmentForm;
import jp.co.sss.equipment.mapper.EquimentUpdateMapper;
import jp.co.sss.equipment.util.EquipmentInputCheck;

/**
 * 備品編集サービス
 * @author 
 *
 */
@Service
public class EquimentUpdateService {

    @Autowired
    private EquimentUpdateMapper equimentUpdateMapper;
    
    @Autowired
	EquipmentInputCheck equipmentInputCheck;

    /*
     * 備品編集のためのシリアルナンバーで検索
     */
    public StockMaster equimentFind(String serialNo) {
        return equimentUpdateMapper.equimentFind(serialNo);
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
}

