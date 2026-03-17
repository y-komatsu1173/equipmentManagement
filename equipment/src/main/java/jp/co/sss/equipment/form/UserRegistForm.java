package jp.co.sss.equipment.form;

import lombok.Data;

/**
 * ユーザー登録フォーム
 */
@Data
public class UserRegistForm {

	/**名前*/
	private String name;

	/**権限*/
	private Integer auth;

	/**パスワード*/
	private String password;
}
