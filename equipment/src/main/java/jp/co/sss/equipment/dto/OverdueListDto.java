package jp.co.sss.equipment.dto;

import lombok.Data;

/**
 * 返却遅延Dto
 */

@Data
public class OverdueListDto {

	/**シリアルナンバー*/
	private String stockCode;
	
	/**製品名*/
	private String productName;
	
	/**カテゴリ*/
	private String categoryName;
	
	/**所有区分*/
	private String ownershipType;
	
	/**返却期限*/
	private String returnDate;
	
	/**使用者*/
	private String staffName;
}