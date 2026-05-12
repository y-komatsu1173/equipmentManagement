package jp.co.sss.equipment.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * ログイン認証フィルター
 * 
 * ログインしていないユーザーが
 * 認証必須画面へアクセスできないように制御する
 */
@WebFilter("/*")
public class LoginCheckFilter extends HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		//URI・コンテキストパス・パス部分の取得
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		String path = uri.substring(ctx.length());

		//ログインページを外す
		if (path.equals("/")
				|| path.equals("/login")
				|| path.equals("/logout")
				|| path.equals("/oneTime")
				|| path.equals("/otpCheck")
				|| path.equals("/user/logout")
				|| path.startsWith("/css/")
				|| path.startsWith("/js/")
				|| path.startsWith("/images/")) {

			chain.doFilter(request, response);
			return;
		}

		//セッションの取得
		HttpSession session = request.getSession(false);

		//未ログインチェック
		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect("/");
			return;
		}

		//ログイン済みの場合次へ
		chain.doFilter(request, response);
	}
}