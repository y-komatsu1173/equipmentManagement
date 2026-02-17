package jp.co.sss.equipment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.equipment.entity.StockMaster;
import jp.co.sss.equipment.mapper.EquimentUpdateMapper;

/**
 * 備品編集サービス
 * @author 
 *
 */
@Service
public class EquimentUpdateService {

    @Autowired
    private EquimentUpdateMapper equimentUpdateMapper;

    /*
     * 備品編集のためのシリアルナンバーで検索
     */
    public StockMaster equimentFind(String serialNo) {
        return equimentUpdateMapper.equimentFind(serialNo);
    }
}

