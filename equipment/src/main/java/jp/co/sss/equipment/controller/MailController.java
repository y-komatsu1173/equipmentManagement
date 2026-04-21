package jp.co.sss.equipment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.sss.equipment.service.MailService;

/**
 * メール送信コントローラ
 */
@Controller
public class MailController {

	@Autowired
	private MailService mailService;
	
	/*
	 * テスト
	 */
	@GetMapping("/mail/test")
	public String sendTestMail() {	
		mailService.sendMail(
				"test@examle.com",
				"テストメール",
				"MailHog動作確認"
				);
		return "送信しました";
	}
	
	/**
	 * 返却日時がすぎた時
	 */
	@Scheduled
	public void returnOver() {
		mailService.sendMail(
				"test@examle.com",
				"履歴が開かれました",
				"MailHog動作確認"
				);
	}
}
