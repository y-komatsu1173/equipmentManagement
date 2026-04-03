package jp.co.sss.equipment.dto;

import lombok.Data;

/**
 * パスワード更新時の入力チェック結果を保持するDTOクラス
 */

@Data
public class PasswordCheckDto {
	
	/**パスワード変更可否*/
	private boolean passwordChangeNotAllowed;
	
	/**現在のパスワード未入力チェック*/
	private boolean currentPasswordRequired;
	
	/**現在のパスワードとの一致チェック*/
	private boolean currentPasswordInvalid;
	
	/** 確認パスワード未入力 */
	private boolean checkPasswordRequired;
	
	/** 確認パスワード不一致 */
	private boolean checkPasswordInvalid;

}