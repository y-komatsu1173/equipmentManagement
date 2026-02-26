package jp.co.sss.equipment.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import jp.co.sss.equipment.entity.StaffData;

public class AuthorityCheckFilter extends HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain chain)
			throws IOException, ServletException {

		//URIと送信方法を取得
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		String path = uri.substring(ctx.length());
		
		System.out.println("uri==" + uri);
		System.out.println("ctx==" + ctx);
		System.out.println("path==" + path);
		System.out.println("method==" + request.getMethod());

		// ログインページと静的リソースはフィルターを通過させる
		if (path.equals("/") || path.equals("/login")
				|| path.startsWith("/css/")
				|| path.startsWith("/js/")
				|| path.startsWith("/images/")) {
			chain.doFilter(request, response);
			return;
		}

		// セッションからユーザー情報を取得
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect(ctx + "/");
			return;
		}

		// ユーザーの権限を取得
		StaffData user = (StaffData) session.getAttribute("user");
		// 権限番号を取得
		int authNo = user.getAuthNo();

		// 管理者URLと操作URLの定義
		boolean isAdminUrl = path.startsWith("/equipment/regist") ||
				path.startsWith("/equipment/update") ||
				path.startsWith("/equipment/delete");

		// 操作URLの定義
		boolean isOperateUrl = path.startsWith("/borrowing/view") ||
				path.startsWith("/return/view");

		// 権限チェック
		if (authNo == 99) {
			if (isOperateUrl || isAdminUrl) {
				response.sendRedirect(ctx + "/index");
				return;
			}
		}

		// 一般ユーザーは操作URLと管理者URLにアクセスできない
		if (authNo == 4) {
			if (isAdminUrl) {
				response.sendRedirect(ctx + "/index");
				return;
			}
		}

		// フィルターを通過させる
		chain.doFilter(request, response);
	}
}