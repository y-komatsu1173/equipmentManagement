package jp.co.sss.equipment.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.equipment.form.LoginForm;
import jp.co.sss.equipment.service.IndexService;
import jp.co.sss.equipment.service.LoginResult;
import jp.co.sss.equipment.service.LoginService;

/**
 * ログイン処理
 */
@Controller
public class LoginController {
	@Autowired
	HttpSession session;

	@Autowired
	LoginService loginService;

	@Autowired
	IndexService indexService;

	/**
	 * ログイン画面の表示
	 * @author 小松原
	 * @return　templates/index/index ログイン画面
	 */
	@GetMapping("/")
	public String loginForm(LoginForm loginForm) {
	    session.invalidate();
	    return "login";
	}

	/**
	 * ログイン処理
	 */
	@PostMapping("/login")
	public String login(@ModelAttribute LoginForm loginForm, BindingResult result, HttpSession session, Model model) {
		System.out.println("POST /login 来たで！ username=" + loginForm.getEmpId());
	    
		if (result.hasErrors()) {
			return "login";
		}

		LoginResult loginResult = loginService.excute(loginForm);
		//TODO loginResult.isLoginの結果がtrueの場合、ログイン成功でセッションに"user"という名前でセッションにユーザーの情報を登録する
		if (loginResult.isLogin()) {
			System.out.println("==== ログインユーザー ====");
			System.out.println(loginResult.getLoginUser());
			System.out.println("name: " + loginResult.getLoginUser().getName());
			System.out.println("=========================");

			//TODO セッションにuser登録
			session.setAttribute("user", loginResult.getLoginUser());

			// 一覧へリダイレクト
			return "redirect:/index";
			//TODO loginResult.isLoginの結果がfalseの場合、loginResult.getErrorMsgメソッドを呼びだし、modelスコープに登録する
		} else {//ログイン失敗時
			//TODO loginResult.getErrorMsgを呼び出し、メッセージをmodelスコープに登録
			model.addAttribute("errMessage", loginResult.getErrorMsg());
			return "login";
		}
	}

	/**
	 * ログアウト処理
	 * @return
	 */
	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	public String logout() {
		//TODO セッションの破棄
		session.invalidate();

		//index.htmlへ遷移
		return "redirect:/";
	}
}
