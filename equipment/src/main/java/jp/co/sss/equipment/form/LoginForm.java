package jp.co.sss.equipment.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

/**
 * ログインフォーム
 */

@Data
public class LoginForm {
	
	/** 社員ID */
	@NotNull(message = "IDを入力してください")
	//@Max(value = 99999, message = "IDは5桁以内で入力してください")
	private Integer empId;
	
	/** パスワード */
	@NotBlank(message = "パスワードを入力してください")
	@Size(max = 16, message = "パスワードは16文字以内で入力してください")
	private String empPass;

}
