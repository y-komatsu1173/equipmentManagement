package jp.co.sss.equipment.test;

import java.util.Scanner;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * パスワードハッシュ生成テスト
 */
public class PasswordHashTest {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		/**
		 * BCryptハッシュ化クラス
		 */
		BCryptPasswordEncoder encoder =
				new BCryptPasswordEncoder();

		System.out.println("ハッシュ化したいパスワードを入力してください");
		System.out.println("終了する場合は exit を入力");

		while (true) {

			System.out.print("パスワード > ");

			// コンソール入力
			String password = sc.nextLine();

			// 終了判定
			if (password.equals("exit")) {
				System.out.println("終了します");
				break;
			}

			// ハッシュ化
			String hash = encoder.encode(password);

			// 結果表示
			System.out.println("元パスワード : " + password);
			System.out.println("ハッシュ    : " + hash);
			System.out.println("-------------------------");
		}

		// Scanner終了
		sc.close();
	}
}