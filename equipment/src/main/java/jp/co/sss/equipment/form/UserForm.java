package jp.co.sss.equipment.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

/**
 * ユーザー登録フォーム
 */
@Data
public class UserForm {

    /** 社員番号 */
    @NotNull(message = "IDを入力してください")
    private Integer staffNo;

    /** 名前 */
    @NotBlank(message = "社員名を入力してください")
    @Size(max = 30, message = "社員名は30文字以内で入力してください")
    private String name;

    /** 権限 */
    @NotNull(message = "権限を選択してください")
    private Integer auth;

    /** パスワード */
    @NotBlank(message = "パスワードを入力してください")
    @Size(min = 8, max = 16, message = "パスワードは8〜16文字で入力してください")
    private String password;
    
    /**編集時の元のID*/
    private Integer oldStaffNo;
}