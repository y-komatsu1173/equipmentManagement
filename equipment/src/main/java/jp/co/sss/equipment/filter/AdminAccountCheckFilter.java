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
		String requestMethod = request.getMethod();

		//完了画面はフィルターを追加
		if (requestURI.contains("/complete") && requestMethod.equals("GET")) {
			chain.doFilter(request, response);
			return;
		}

		//セッションユーザー情報を取得
		HttpSession session = request.getSession();
		StaffData staffData = (StaffData) session.getAttribute("user");

		//セッションユーザーのIDと権限の変数を初期化
		Integer staffNo = null;
		Integer authNo = null;

		//セッションユーザーがnullでない場合
		if (staffData != null) {
			//セッションユーザからID、権限を取得して変数に代入
			staffNo = staffData.getStaffNo();
			authNo = staffData.getAuthNo();
		}

		//更新対象の社員IDをリクエストから取得
		String upEmpIdStr = request.getParameter("staffNo");
		Integer upEmpId = null;

		//社員IDがnullでない場合
		if (upEmpIdStr != null) {
			//社員IDを整数型に変換
			upEmpId = Integer.valueOf(upEmpIdStr);
		}
		//フィルター通過のフラグを初期化 true:フィルター通過 false:ログイン画面へ戻す
				boolean accessFlg = false;

				//TODO  管理者(セッションユーザーのIDが2)の場合、アクセス許可
				if (authNo != null && authNo == 2) {
					accessFlg = true;
					//TODO  ログインユーザ自身(セッションユーザのIDと変更リクエストの社員IDが一致)の画面はアクセス許可
				} else if (staffNo != null && upEmpId != null && staffNo.equals(upEmpId)) {
					accessFlg = true;
				}

				//TODO  accessFlgが立っていない場合はログイン画面へリダイレクトし、処理を終了する
				if (!accessFlg) {
					//TODO  レスポンス情報を取得
					String contextPth = request.getContextPath();

					//TODO  ログイン画面へリダイレクト
					response.sendRedirect(contextPth + "/");

					//処理を終了
					return;
				}

				chain.doFilter(request, response);
				return;

			}
}
