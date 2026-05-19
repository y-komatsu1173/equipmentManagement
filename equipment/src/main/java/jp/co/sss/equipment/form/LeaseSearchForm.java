package jp.co.sss.equipment.form;

import lombok.Data;

/**
 * リース一覧表示　条件検索用
 */
@Data
public class LeaseSearchForm {

	/**カテゴリ*/
	private Integer categoryId;
	
	/**状態*/
	private String status;
	
	/**並び順*/
	private String sortType;
}