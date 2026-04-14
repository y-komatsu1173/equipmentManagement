package jp.co.sss.equipment.dto;

import lombok.Data;

/**
 * 備品検索表示DTO
 */

@Data
public class EquipmentSearchDto {

	/**シリアル*/
    private String stockCode;
    
    /**備品名*/
    private String name;
    
    /**カテゴリ*/
    private String categoryName;
    
    /**貸出画面*/
    private String statusName;
    
    /**リース分別*/
    private String ownershipTypeName;
    
    /**棚卸しで使用*/
    /**棚卸し用確認フラグ*/
    private boolean checked;
    
}