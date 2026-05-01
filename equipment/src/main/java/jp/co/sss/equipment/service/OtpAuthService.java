package jp.co.sss.equipment.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import jp.co.sss.equipment.entity.OtpAuth;
import jp.co.sss.equipment.mapper.OtpAuthMapper;

/**
 * ワンタイムパスコードサービス
 */

@Service
public class OtpAuthService {


	@Autowired
	private OtpAuthMapper otpAuthMapper;
	
	@Autowired
	private MailService mailService;
	
	/**６桁の数字を生成*/
	private static final SecureRandom rand = new SecureRandom();
	
	/** OPT有効期間 10分間*/
	private static final int OTP_EXPIRE_MINUTES = 10;
	
	/**最大試行回数 5回*/
	private static final int MAX_ATTEMPT_COUNT = 5;

	
	/**
	 * OTPを生成しDBへ保存
	 * 再送時は上書き更新
	 */
	public void issueOtp(Integer staffNo, String mail) {
		
		//6桁のOTPを生成
		String otp = String.valueOf(100000 + rand.nextInt(900000));
		
		//OTPをハッシュ化　DBに保存
		String otpHash = BCrypt.hashpw(otp, BCrypt.gensalt());
		
		OtpAuth otpAuth = new OtpAuth();
		otpAuth.setStaffNo(staffNo);
		otpAuth.setOtpHash(otpHash);
		otpAuth.setExpireAt(LocalDateTime.now().plusMinutes(OTP_EXPIRE_MINUTES));
		otpAuth.setAttemptCount(0);
		
		//既存OTPがあれば上書きする　なしの場合新規登録
		OtpAuth exists = otpAuthMapper.findByStaffNo(staffNo);
		
		if(exists == null) {
			otpAuthMapper.insert(otpAuth);
		}else {
			otpAuthMapper.update(otpAuth);
		}
		
		//OTPメール送信
		mailService.sendMail(
		        "*****@bg.co.jp",   // 送信元（固定でOK）
		        mail,                      // 宛先（ユーザー）
		        "ワンタイムパスコードのお知らせ",
		        "ログイン用のワンタイムパスコードは以下です。\n\n"
		                + otp + "\n\n"
		                + "有効期限は10分です。"
		);
		System.out.println("【生OTP】" + otp);
		System.out.println("【ハッシュ】" + otpHash);
	}
	
	/**
	 *入力されたOTPを検証
	 */
	public boolean verifyOtp(Integer integer, String inputOtp) {
		
		//OTPの取得
		OtpAuth otpAuth = otpAuthMapper.findByStaffNo(integer);
		
		if(otpAuth == null) {
			return false;
		}
		
		//回数チェック
		if(otpAuth.getAttemptCount() >= MAX_ATTEMPT_COUNT) {
			return false;
		}
		
		//有効期限チェック
		if(LocalDateTime.now().isAfter(otpAuth.getExpireAt())) {
			return false;
		}
		
		//OTP照合
		boolean match = BCrypt.checkpw(inputOtp, otpAuth.getOtpHash());
		
		
		if(match) {
			//認証成功後は再利用防止のため削除
			otpAuthMapper.delete(integer);
			return true;
		}
		
		//認証失敗時は回数を増やす
		otpAuthMapper.incrementAttempt(integer);
		return false;
	}
	
}
