package jp.co.sss.equipment.dto;

import lombok.Data;

/**
 * 備品詳細一覧画面DTO
 * 
 * @author yuhakomatsubara
 */
@Data
public class DetailListViewDto {
	/**備品名*/
	private String equipmentName;
	
	/**追加機能
	 * カテゴリ番号 */
	private String stockType;
	
	/**シリアルナンバー*/
	private String parentStockCode;
	
	/**使用者*/
	private String staffName;
	
	/**貸出可否*/
	private String rentFlg;
	
	/**貸出開始日*/
	private String startDate;
	
	/**返却予定日*/
	private String limitDate;
	
	/**最終所在確認*/
	private String confirmedDate;
	
	/**追加機能
	 * 親シリアル（接続先PCシリアルナンバー）*/
	private String destinationSerial;
	
	/**追加機能
	 * 製品名*/
	private String productName;
	
	/**追加機能
	 * メーカー名*/
	 private String maker;
	 
	 /**追加機能
	  *分類（社内保有・リース品）*/
	 private String ownershipType;
	 
	 /**追加機能
	  * リース返却予定日*/
	 private String leaseReturnDate;
	
	/**備考*/
	private String remarks;
	
	/**シーケンスid*/
	private Integer equipmentId;
	
	/**スタッフid*/
	private Integer staffNo;
	
	/**貸出可否*/
	private String rentFlag;
}
