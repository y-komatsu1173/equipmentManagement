package jp.co.sss.equipment.entity;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * ワンタイムパスコード認証情報
 */

@Data
public class OtpAuth {

	/** ユーザーID(社員番号) */
	private String staffNo;
	
	/** OPTのハッシュ値 */
	private String optHash;
	
	/** 有効期限 */
	private LocalDateTime expireAt;
	
	/** 認証試行回数 */
	private int attemptCount;
	
	/** 作成日時 */
	private LocalDateTime createdAt;
	
	/** 更新日時 */
	private LocalDateTime updateAt;
}
