package jp.co.sss.equipment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jp.co.sss.equipment.service.OtpAuthService;

@Component
public class OtpTestRunner implements CommandLineRunner {

    private final OtpAuthService otpAuthService;

    public OtpTestRunner(OtpAuthService otpAuthService) {
        this.otpAuthService = otpAuthService;
    }

    @Override
    public void run(String... args) throws Exception {

        // テスト実行
        otpAuthService.issueOtp("test001", "自分のメールアドレス");

    }
}