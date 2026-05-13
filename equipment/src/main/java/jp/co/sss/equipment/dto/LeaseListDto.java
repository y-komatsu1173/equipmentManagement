package jp.co.sss.equipment.dto;

import lombok.Data;

/**
 * リース一覧表示用DTO
 */

@Data
public class LeaseListDto {
	
	/**シリアル*/
	private String stockCode;
	
	/**リース返却日*/
	private String leaseReturnDate;
	
	/**製品名*/
	private String productName;
	
	/**カテゴリ*/
	private String categoryName;
	
	/**貸出状態*/
    private String statusName;
	
	/**返却期限*/
	private String returnDate;
	
	/**使用者*/
	private String staffName;
}
