package jp.co.sss.equipment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.equipment.entity.StaffData;
import jp.co.sss.equipment.form.LoginForm;
import jp.co.sss.equipment.mapper.EmployeeMapper;
import jp.co.sss.equipment.util.Constant;
import jp.co.sss.equipment.util.LoginErrorType;

/**
 * ログイン処理
 */
@Service
public class LoginService {
	@Autowired
	private EmployeeMapper employMapper;

	/**
	 * ログイン処理
	 * @param loginForm
	 * @return
	 */
	public LoginResult excute(LoginForm loginForm) {

		StaffData staffData = employMapper.findByEmpIdAndEmpPass(loginForm.getEmpId(), loginForm.getEmpPass());

		if (staffData == null) {
			return LoginResult.failLogin(Constant.LOGIN_ERR_MSG,
					LoginErrorType.USER_NOT_FOUND);
		}
		return LoginResult.succeedLogin(staffData);
	}
}