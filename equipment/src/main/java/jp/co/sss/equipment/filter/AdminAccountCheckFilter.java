package jp.co.sss.equipment.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import jp.co.sss.equipment.entity.StaffData;

/**
 * 権限認証用フィルタ
 */
public class AdminAccountCheckFilter extends HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		//URIと送信方法を取得
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = requestURI.substring(contextPath.length());
		String requestMethod = request.getMethod();

		//完了画面はフィルターを追加
		if (requestURI.contains("/complete") && requestMethod.equals("GET")) {
			chain.doFilter(request, response);
			return;
		}

		//完了画面はフィルターを追加
		if (requestURI.contains("/complete") && requestMethod.equals("GET")) {
		    chain.doFilter(request, response);
		    return;

		}

	
		if (path.equals("/")
		        || path.equals("/login")
		        || path.equals("/logout")
		        || path.equals("/oneTime")
		        || path.equals("/otpCheck")
		        || path.startsWith("/css/")
		        || path.startsWith("/js/")
		        || path.startsWith("/images/")) {
		    chain.doFilter(request, response);
		    return;

		}
		//セッションユーザー情報を取得
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect(contextPath + "/");
			return;
		}

		StaffData staffData = (StaffData) session.getAttribute("user");
		Integer loginStaffNo = staffData.getStaffNo();
		Integer authNo = staffData.getAuthNo();

		Integer targetStaffNo = null;

		// POSTのform送信時
		String oldStaffNoParam = request.getParameter("oldStaffNo");
		if (oldStaffNoParam != null && !oldStaffNoParam.isBlank()) {
			targetStaffNo = Integer.valueOf(oldStaffNoParam);
		} else {
			String staffNoParam = request.getParameter("staffNo");
			if (staffNoParam != null && !staffNoParam.isBlank()) {
				targetStaffNo = Integer.valueOf(staffNoParam);
			}
		}
		// GET /user/detail/{staffNo} のとき
		if (targetStaffNo == null && path.startsWith("/user/detail/")) {
			String[] parts = path.split("/");
			targetStaffNo = Integer.valueOf(parts[3]);
		}
		// GET /user/update/input/{staffNo}
		if (targetStaffNo == null && path.startsWith("/user/update/input/")) {
			String[] parts = path.split("/");
			targetStaffNo = Integer.valueOf(parts[4]);
		}

		boolean accessFlg = false;

		// 管理者なら許可
		if (authNo != null && (authNo == 1 || authNo == 2)) {
			accessFlg = true;
		}
		// 本人なら許可
		else if (loginStaffNo != null && targetStaffNo != null && loginStaffNo.equals(targetStaffNo)) {
			accessFlg = true;
		}

		if (!accessFlg) {
			response.sendRedirect(contextPath + "/");
			return;
		}
	

		chain.doFilter(request, response);
	}
}
