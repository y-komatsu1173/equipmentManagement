package jp.co.sss.equipment.service;


import jp.co.sss.equipment.entity.StaffData;
import jp.co.sss.equipment.util.LoginErrorType;

public class LoginResult {

	private boolean isLogin;
	private StaffData loginUser;
	private String errorMsg;
	private LoginErrorType loginErrorType;

	private LoginResult(StaffData loginUser) {
		this.loginUser = loginUser;
		this.isLogin = true;

	}

	private LoginResult(String errorMsg, LoginErrorType loginErrorType) {
		this.errorMsg = errorMsg;
		this.loginErrorType = loginErrorType;
	}

	public static LoginResult succeedLogin(StaffData loginUser) {
		return new LoginResult(loginUser);
	}

	public static LoginResult failLogin(String errorMsg, LoginErrorType loginErrorType) {
		return new LoginResult(errorMsg, loginErrorType);
	}

	public boolean isLogin() {
		return isLogin;
	}

	public StaffData getLoginUser() {
		return loginUser;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public LoginErrorType getLoginErrorType() {
		return loginErrorType;
	}

}
