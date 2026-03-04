package jp.co.sss.equipment.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * 貸出履歴検索フォーム
 * 画面の絞り込み・並べ替え条件をまとめる
 */
@Data
public class BorrowingHistorySearchForm {

	/*シリアルナンバー*/
	private String stockCode;
	
	/*カテゴリ名*/
	private String categoryName;

	/*製品名*/
	private String productName;
	
	/*使用者(社員番号)*/
	private Integer staffNo;
	
	/*貸出日*/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fromDate;
	
	/* 貸出日To*/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate toDate;
	
	/*カテゴリ*/
	private String categoryId;
	
	/*分類(会社所有 / リース)*/
	private String ownershipType;
	
	/*削除済み　分別*/
	private Boolean includeDeleted;
	
	/*並び替え*/
	private String sortKey = "startDate";
	
	/*並び順*/
	private String sortDir = "desc";
	
}
